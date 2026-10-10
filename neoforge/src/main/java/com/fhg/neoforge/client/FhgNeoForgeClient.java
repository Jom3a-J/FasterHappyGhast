package com.fhg.neoforge.client;

import com.fhg.client.FreeLook;
import com.fhg.neoforge.FasterHappyGhastNeoForge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
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
	public FhgNeoForgeClient(IEventBus modBus, ModContainer container) {
		container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

		modBus.addListener(RegisterKeyMappingsEvent.class, event -> event.register(FreeLook.KEY));
	}
}
