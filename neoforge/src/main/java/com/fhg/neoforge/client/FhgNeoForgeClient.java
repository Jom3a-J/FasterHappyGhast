package com.fhg.neoforge.client;

import com.fhg.neoforge.FasterHappyGhastNeoForge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * Registering a {@code ModConfigSpec} is not enough to get a settings screen — NeoForge only shows
 * one for mods that register this extension point. {@link ConfigurationScreen} is NeoForge's
 * built-in screen generated from the spec, and its constructor already matches
 * {@code IConfigScreenFactory#createScreen}.
 *
 * <p>Separate client-only {@code @Mod} class so a dedicated server never loads client GUI classes.
 */
@Mod(value = FasterHappyGhastNeoForge.MOD_ID, dist = Dist.CLIENT)
public class FhgNeoForgeClient {
	public FhgNeoForgeClient(ModContainer container) {
		container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
	}
}
