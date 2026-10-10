package com.fhg.mixin.client;

import com.fhg.client.FreeLook;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/** Sends mouse look to the free look camera instead of the rider while free look is held. */
@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
	@WrapOperation(
		method = "turnPlayer",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
	private void fhg$freeLookTurn(LocalPlayer player, double xo, double yo, Operation<Void> original) {
		if (FreeLook.isActive()) {
			FreeLook.turn(xo, yo);
			return;
		}

		original.call(player, xo, yo);
	}
}
