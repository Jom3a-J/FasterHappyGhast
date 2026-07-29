package com.fhg.fabric;

import com.fhg.FhgConfig;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Cloth Config screen for the movement tunables. The config stores multipliers as fractions, but
 * sliders read much better as percentages, so everything is converted at the boundary.
 */
public final class FhgConfigScreen {
	private FhgConfigScreen() {
	}

	public static Screen create(Screen parent) {
		FhgConfig config = FhgConfig.get();

		ConfigBuilder builder = ConfigBuilder.create()
			.setParentScreen(parent)
			.setTitle(Component.translatable("fhg.config.title"))
			.setSavingRunnable(FasterHappyGhastFabric::save);

		ConfigEntryBuilder entries = builder.entryBuilder();
		ConfigCategory movement = builder.getOrCreateCategory(Component.translatable("fhg.config.category.movement"));

		// Every default below is read from FhgConfig rather than written out again here, so the
		// screen's "reset" values cannot drift away from the shipped defaults or the NeoForge spec.
		movement.addEntry(entries.startBooleanToggle(Component.translatable("fhg.config.enabled"), config.enabled)
			.setDefaultValue(FhgConfig.DEFAULT_ENABLED)
			.setTooltip(Component.translatable("fhg.config.enabled.tooltip"))
			.setSaveConsumer(value -> config.enabled = value)
			.build());

		movement.addEntry(entries.startIntSlider(Component.translatable("fhg.config.speed"), percent(config.speedMultiplier), 100, 1000)
			.setDefaultValue(percent(FhgConfig.DEFAULT_SPEED_MULTIPLIER))
			.setTextGetter(FhgConfigScreen::percentLabel)
			.setTooltip(Component.translatable("fhg.config.speed.tooltip"))
			.setSaveConsumer(value -> config.speedMultiplier = fraction(value))
			.build());

		movement.addEntry(entries.startIntSlider(Component.translatable("fhg.config.vertical"), percent(config.verticalMultiplier), 50, 600)
			.setDefaultValue(percent(FhgConfig.DEFAULT_VERTICAL_MULTIPLIER))
			.setTextGetter(FhgConfigScreen::percentLabel)
			.setTooltip(Component.translatable("fhg.config.vertical.tooltip"))
			.setSaveConsumer(value -> config.verticalMultiplier = fraction(value))
			.build());

		movement.addEntry(entries.startBooleanToggle(Component.translatable("fhg.config.sprint_enabled"), config.sprintBoostEnabled)
			.setDefaultValue(FhgConfig.DEFAULT_SPRINT_BOOST_ENABLED)
			.setTooltip(Component.translatable("fhg.config.sprint_enabled.tooltip"))
			.setSaveConsumer(value -> config.sprintBoostEnabled = value)
			.build());

		movement.addEntry(entries.startIntSlider(Component.translatable("fhg.config.sprint_boost"), percent(config.sprintBoost), 100, 400)
			.setDefaultValue(percent(FhgConfig.DEFAULT_SPRINT_BOOST))
			.setTextGetter(FhgConfigScreen::percentLabel)
			.setTooltip(Component.translatable("fhg.config.sprint_boost.tooltip"))
			.setSaveConsumer(value -> config.sprintBoost = fraction(value))
			.build());

		movement.addEntry(entries.startIntSlider(Component.translatable("fhg.config.responsiveness"), percent(config.responsiveness), 0, 100)
			.setDefaultValue(percent(FhgConfig.DEFAULT_RESPONSIVENESS))
			.setTextGetter(FhgConfigScreen::percentLabel)
			.setTooltip(Component.translatable("fhg.config.responsiveness.tooltip"))
			.setSaveConsumer(value -> config.responsiveness = fraction(value))
			.build());

		movement.addEntry(entries.startIntSlider(Component.translatable("fhg.config.turn_speed"), percent(config.turnSpeed), 2, 50)
			.setDefaultValue(percent(FhgConfig.DEFAULT_TURN_SPEED))
			.setTextGetter(FhgConfigScreen::percentLabel)
			.setTooltip(Component.translatable("fhg.config.turn_speed.tooltip"))
			.setSaveConsumer(value -> config.turnSpeed = fraction(value))
			.build());

		return builder.build();
	}

	private static int percent(double fraction) {
		return (int) Math.round(fraction * 100.0);
	}

	private static double fraction(int percent) {
		return percent / 100.0;
	}

	private static Component percentLabel(int percent) {
		return Component.literal(percent + "%");
	}
}
