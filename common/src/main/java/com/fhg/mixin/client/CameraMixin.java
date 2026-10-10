package com.fhg.mixin.client;

import com.fhg.client.FreeLook;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Points the camera along the free look angle instead of the rider's.
 *
 * <p>This swaps the rotation the camera reads from its entity rather than the rotation it ends up
 * with, so the rest of {@code alignWithEntity} still applies on top: third person orbits around the
 * ghast at the right distance, and the front-facing view still flips. It also targets the entity
 * getters rather than {@code setRotation}, because NeoForge reroutes that call through an event.
 */
@Mixin(Camera.class)
public abstract class CameraMixin {
	@WrapOperation(
		method = "alignWithEntity",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewYRot(F)F"))
	private float fhg$freeLookYRot(Entity entity, float partialTicks, Operation<Float> original) {
		float riderYRot = original.call(entity, partialTicks);
		return fhg$isLocalPlayer(entity) ? FreeLook.cameraYRot(riderYRot) : riderYRot;
	}

	@WrapOperation(
		method = "alignWithEntity",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewXRot(F)F"))
	private float fhg$freeLookXRot(Entity entity, float partialTicks, Operation<Float> original) {
		float riderXRot = original.call(entity, partialTicks);
		return fhg$isLocalPlayer(entity) ? FreeLook.cameraXRot(riderXRot) : riderXRot;
	}

	/** Only the local rider's own view, so spectating another entity is never affected. */
	private static boolean fhg$isLocalPlayer(Entity entity) {
		return entity == Minecraft.getInstance().player;
	}
}
