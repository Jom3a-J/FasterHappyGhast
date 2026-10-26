package com.fhg.neoforge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.loading.FMLPaths;

/** NeoForge mod IDs cannot contain hyphens, so this side uses underscores. */
@Mod(FasterHappyGhastNeoForge.MOD_ID)
public class FasterHappyGhastNeoForge {
	public static final String MOD_ID = "faster_happy_ghast";

	public FasterHappyGhastNeoForge(IEventBus modBus, ModContainer container) {
		migrateCommonConfig();
		container.registerConfig(ModConfig.Type.LOCAL, FhgNeoConfig.SPEC);

		modBus.addListener(ModConfigEvent.Loading.class, event -> FhgNeoConfig.sync());
		modBus.addListener(ModConfigEvent.Reloading.class, event -> FhgNeoConfig.sync());
	}

	/**
	 * NeoForge 26.3.0.37 renamed the {@code COMMON} config type to {@code LOCAL}, which also moved the
	 * file from {@code -common.toml} to {@code -local.toml}. Carry an existing file across so updating
	 * does not silently reset everyone's settings to the defaults.
	 */
	private static void migrateCommonConfig() {
		Path configDir = FMLPaths.CONFIGDIR.get();
		Path legacy = configDir.resolve(MOD_ID + "-common.toml");
		Path current = configDir.resolve(MOD_ID + "-local.toml");

		if (Files.exists(legacy) && !Files.exists(current)) {
			try {
				Files.move(legacy, current);
			} catch (IOException ignored) {
				// Worst case the defaults load, which is what would have happened anyway.
			}
		}
	}
}
