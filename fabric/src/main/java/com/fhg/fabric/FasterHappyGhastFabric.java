package com.fhg.fabric;

import com.fhg.FhgConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class FasterHappyGhastFabric implements ModInitializer {
	public static final String MOD_ID = "faster-happy-ghast";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public void onInitialize() {
		load();
	}

	private static Path path() {
		return FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".json");
	}

	public static void load() {
		Path path = path();

		if (!Files.exists(path)) {
			FhgConfig.set(new FhgConfig());
			save();
			return;
		}

		try (Reader reader = Files.newBufferedReader(path)) {
			FhgConfig loaded = GSON.fromJson(reader, FhgConfig.class);
			FhgConfig.set(loaded == null ? new FhgConfig() : loaded);
		} catch (IOException | RuntimeException e) {
			LOGGER.error("Could not read {}, falling back to defaults", path, e);
			FhgConfig.set(new FhgConfig());
		}
	}

	public static void save() {
		FhgConfig.get().clamp();
		Path path = path();

		try {
			Files.createDirectories(path.getParent());

			try (Writer writer = Files.newBufferedWriter(path)) {
				GSON.toJson(FhgConfig.get(), writer);
			}
		} catch (IOException e) {
			LOGGER.error("Could not write {}", path, e);
		}
	}
}
