package com.fhg.mixin;

import com.fhg.FhgConfig;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes a ridden Happy Ghast worth flying. Loose AI ghasts are left alone, so a wild one still
 * drifts around at vanilla speed.
 */
@Mixin(HappyGhast.class)
public abstract class HappyGhastMixin extends Animal {
	protected HappyGhastMixin(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	/**
	 * Vanilla riders can never sprint, because {@code Entity#canSprint} is false and the Happy Ghast
	 * does not override it. Allowing it lets the client's own sprint handling drive the boost, so the
	 * sprint key and the sprint state sync work without a custom keybind or packet.
	 */
	@Override
	public boolean canSprint() {
		FhgConfig config = FhgConfig.get();
		return config.enabled && config.sprintBoostEnabled;
	}

	/**
	 * Scales the climb/dive component of the rider's input. Vanilla builds this from the look pitch
	 * plus a flat 0.5 while jumping.
	 */
	@Inject(method = "getRiddenInput", at = @At("RETURN"), cancellable = true)
	private void fhg$boostVerticalInput(Player controller, Vec3 selfInput, CallbackInfoReturnable<Vec3> cir) {
		FhgConfig config = FhgConfig.get();

		if (!config.enabled || config.verticalMultiplier == 1.0) {
			return;
		}

		Vec3 ridden = cir.getReturnValue();
		cir.setReturnValue(new Vec3(ridden.x, ridden.y * config.verticalMultiplier, ridden.z));
	}

	/**
	 * Replaces vanilla's flight integration while a player is steering.
	 *
	 * <p>Vanilla accelerates by {@code input * speed} each tick and then keeps 91% of the velocity,
	 * which settles at {@code acceleration * 0.91 / 0.09} — about 3.3 blocks/second, slower than
	 * walking. Both the acceleration and the drag are reworked here.
	 */
	@Inject(method = "travel", at = @At("HEAD"), cancellable = true)
	private void fhg$boostedTravel(Vec3 input, CallbackInfo ci) {
		FhgConfig config = FhgConfig.get();

		if (!config.enabled || !(this.getControllingPassenger() instanceof Player rider)) {
			return;
		}

		double topSpeedFactor = config.speedMultiplier;

		if (config.sprintBoostEnabled && rider.isSprinting()) {
			topSpeedFactor *= config.sprintBoost;
		}

		float airDrag = (float) Mth.lerp(config.responsiveness, FhgConfig.VANILLA_AIR_DRAG, FhgConfig.SNAPPY_AIR_DRAG);

		// Less drag settles at a lower top speed for the same acceleration, so scale acceleration back
		// up by the ratio of the two drag curves. Responsiveness then changes only how quickly the
		// ghast reaches and sheds its top speed, not what that top speed is.
		double dragCompensation = ((1.0 - airDrag) / airDrag)
			/ ((1.0 - FhgConfig.VANILLA_AIR_DRAG) / FhgConfig.VANILLA_AIR_DRAG);

		// moveRelative normalises any input vector longer than 1, which would silently swallow the
		// vertical boost. Normalise here instead and fold the length into the speed, which keeps the
		// acceleration linear in the input for any magnitude.
		Vec3 direction = input;
		double lengthCompensation = 1.0;
		double length = input.length();

		if (length > 1.0) {
			direction = input.scale(1.0 / length);
			lengthCompensation = length;
		}

		float vanillaSpeed = (float) this.getAttributeValue(Attributes.FLYING_SPEED) * 5.0F / 3.0F;
		float speed = (float) (vanillaSpeed * topSpeedFactor * dragCompensation * lengthCompensation);

		// Vanilla's water and lava drag are left alone; only the ghast's element gets faster.
		double drag = this.isInWater() ? 0.8 : this.isInLava() ? 0.5 : airDrag;

		this.moveRelative(speed, direction);
		this.move(MoverType.SELF, this.getDeltaMovement());
		this.setDeltaMovement(this.getDeltaMovement().scale(drag));

		ci.cancel();
	}

	/** Vanilla turns 8% of the way towards the rider's yaw each tick, which feels like a barge. */
	@ModifyConstant(method = "tickRidden", constant = @Constant(floatValue = 0.08F))
	private float fhg$turnSpeed(float vanilla) {
		FhgConfig config = FhgConfig.get();
		return config.enabled ? (float) config.turnSpeed : vanilla;
	}
}
