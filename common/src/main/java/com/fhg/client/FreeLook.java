package com.fhg.client;

import com.fhg.FhgConfig;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;

/**
 * Hold a key while riding a Happy Ghast to look around without steering it.
 *
 * <p>The ghast flies wherever its rider is facing: {@code HappyGhast#tickRidden} turns towards the
 * rider's yaw and {@code HappyGhast#getRiddenInput} climbs or dives from the rider's pitch. So the
 * rider's own rotation is left frozen while the key is held, and mouse movement goes into a
 * separate camera angle here instead. Releasing the key eases the view back to the flight direction.
 *
 * <p>Client-only. Each platform registers {@link #KEY} with its own key mapping API.
 */
public final class FreeLook {
	public static final KeyMapping KEY = new KeyMapping(
		"key.fhg.free_look", InputConstants.Type.KEYBOARD, InputConstants.KEY_LALT, KeyMapping.Category.MOVEMENT);

	/** Same scale {@code Entity#turn} applies to raw mouse deltas. */
	private static final float MOUSE_SCALE = 0.15F;

	/** How long the view takes to swing back to the flight direction after the key is released. */
	private static final long RETURN_NANOS = 250_000_000L;

	private static final long NOT_RETURNING = -1L;

	private static boolean active;
	private static float yRot;
	private static float xRot;

	private static long returnStart = NOT_RETURNING;
	private static float returnFromYRot;
	private static float returnFromXRot;

	private FreeLook() {
	}

	/**
	 * Whether mouse movement currently goes to the free look camera instead of the rider.
	 *
	 * <p>Starting free look captures the angle the camera is showing, so the view never jumps when
	 * the key goes down, even partway through the return animation. Releasing it starts the return.
	 */
	public static boolean isActive() {
		LocalPlayer player = Minecraft.getInstance().player;
		boolean wanted = player != null
			&& FhgConfig.get().enabled
			&& KEY.isDown()
			&& player.getVehicle() instanceof HappyGhast;

		if (wanted && !active) {
			yRot = returning(returnFromYRot, player.getYRot(), true);
			xRot = returning(returnFromXRot, player.getXRot(), false);
			returnStart = NOT_RETURNING;
		} else if (!wanted && active) {
			returnFromYRot = yRot;
			returnFromXRot = xRot;
			returnStart = player == null ? NOT_RETURNING : System.nanoTime();
		}

		active = wanted;
		return active;
	}

	/** Mirrors {@code Entity#turn}, but moves the free look camera instead of the rider. */
	public static void turn(double xo, double yo) {
		yRot += (float) xo * MOUSE_SCALE;
		xRot = Mth.clamp(xRot + (float) yo * MOUSE_SCALE, -90.0F, 90.0F);
	}

	/** The yaw the camera should show, given the rider's own. */
	public static float cameraYRot(float riderYRot) {
		return isActive() ? yRot : returning(returnFromYRot, riderYRot, true);
	}

	/** The pitch the camera should show, given the rider's own. */
	public static float cameraXRot(float riderXRot) {
		return isActive() ? xRot : returning(returnFromXRot, riderXRot, false);
	}

	/**
	 * Eases from where free look left the camera towards the rider's rotation. The target is read
	 * fresh every frame, so the rider can keep turning during the animation and the view follows.
	 */
	private static float returning(float from, float riderRot, boolean wrap) {
		if (returnStart == NOT_RETURNING) {
			return riderRot;
		}

		float t = (System.nanoTime() - returnStart) / (float) RETURN_NANOS;

		if (t >= 1.0F) {
			returnStart = NOT_RETURNING;
			return riderRot;
		}

		// Ease-out cubic: swings back quickly, then settles gently onto the flight direction.
		float eased = 1.0F - (1.0F - t) * (1.0F - t) * (1.0F - t);

		// Free look yaw is unbounded, so take the short way round rather than unwinding full turns.
		float delta = wrap ? Mth.wrapDegrees(riderRot - from) : riderRot - from;
		return from + delta * eased;
	}
}
