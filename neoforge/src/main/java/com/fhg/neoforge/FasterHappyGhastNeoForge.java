package com.fhg.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

/** NeoForge mod IDs cannot contain hyphens, so this side uses underscores. */
@Mod(FasterHappyGhastNeoForge.MOD_ID)
public class FasterHappyGhastNeoForge {
	public static final String MOD_ID = "faster_happy_ghast";

	public FasterHappyGhastNeoForge(IEventBus modBus, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, FhgNeoConfig.SPEC);

		modBus.addListener(ModConfigEvent.Loading.class, event -> FhgNeoConfig.sync());
		modBus.addListener(ModConfigEvent.Reloading.class, event -> FhgNeoConfig.sync());
	}
}
