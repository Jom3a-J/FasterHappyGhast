package com.fhg.neoforge;

import com.fhg.FhgConfig;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * NeoForge-native config. NeoForge renders a settings screen for a {@link ModConfigSpec}
 * automatically once {@code IConfigScreenFactory} is registered, so there is no third-party config
 * dependency on this side.
 *
 * <p>Values are stored as integer percentages rather than the doubles the mod actually uses.
 * That is not cosmetic: {@code ConfigurationScreen} only draws a slider for an {@code IntValue}
 * whose range spans fewer than 256 steps, and gives every {@code DoubleValue} a free-text edit box.
 * Percentages keep each range inside that budget and read better in the TOML besides.
 *
 * <p>The spec is the source of truth on disk; {@link #sync()} converts it into the shared
 * {@link FhgConfig} holder that the mixin reads.
 */
public final class FhgNeoConfig {
	public static final ModConfigSpec SPEC;

	private static final ModConfigSpec.BooleanValue ENABLED;
	private static final ModConfigSpec.IntValue SPEED_PERCENT;
	private static final ModConfigSpec.IntValue VERTICAL_PERCENT;
	private static final ModConfigSpec.BooleanValue SPRINT_BOOST_ENABLED;
	private static final ModConfigSpec.IntValue SPRINT_BOOST_PERCENT;
	private static final ModConfigSpec.IntValue RESPONSIVENESS_PERCENT;
	private static final ModConfigSpec.IntValue TURN_SPEED_PERCENT;

	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

		builder.comment("Movement tuning for ridden Happy Ghasts. All values are percentages.").push("movement");

		ENABLED = builder
			.comment("Turn off to fly a completely vanilla Happy Ghast.")
			.define("enabled", FhgConfig.DEFAULT_ENABLED);

		SPEED_PERCENT = builder
			.comment("Top flying speed, as a percentage of vanilla.",
				"Vanilla (100) is about 3.3 blocks/sec, slower than walking. 200 is about 6.6 blocks/sec.")
			.defineInRange("speedPercent", percent(FhgConfig.DEFAULT_SPEED_MULTIPLIER), 100, 350);

		VERTICAL_PERCENT = builder
			.comment("Extra multiplier on climbing and diving, on top of top speed.",
				"100 leaves vertical speed alone.")
			.defineInRange("verticalPercent", percent(FhgConfig.DEFAULT_VERTICAL_MULTIPLIER), 50, 300);

		SPRINT_BOOST_ENABLED = builder
			.comment("Lets you sprint while riding. Vanilla forbids it outright.")
			.define("sprintBoostEnabled", FhgConfig.DEFAULT_SPRINT_BOOST_ENABLED);

		SPRINT_BOOST_PERCENT = builder
			.comment("Top speed while sprinting, as a percentage of your cruising speed.",
				"100 means sprinting gives no extra speed.")
			.defineInRange("sprintBoostPercent", percent(FhgConfig.DEFAULT_SPRINT_BOOST), 100, 350);

		RESPONSIVENESS_PERCENT = builder
			.comment("How quickly the ghast reaches and sheds its top speed.",
				"0 is vanilla's floaty drift, 100 stops on a dime. Does not change top speed.")
			.defineInRange("responsivenessPercent", percent(FhgConfig.DEFAULT_RESPONSIVENESS), 0, 100);

		TURN_SPEED_PERCENT = builder
			.comment("Percentage of the remaining turn applied each tick. Vanilla is 8.")
			.defineInRange("turnSpeedPercent", percent(FhgConfig.DEFAULT_TURN_SPEED), 2, 50);

		builder.pop();

		SPEC = builder.build();
	}

	private FhgNeoConfig() {
	}

	/** Copies the spec's current values into the holder the mixin reads. */
	public static void sync() {
		FhgConfig config = new FhgConfig();
		config.enabled = ENABLED.get();
		config.speedMultiplier = fraction(SPEED_PERCENT.get());
		config.verticalMultiplier = fraction(VERTICAL_PERCENT.get());
		config.sprintBoostEnabled = SPRINT_BOOST_ENABLED.get();
		config.sprintBoost = fraction(SPRINT_BOOST_PERCENT.get());
		config.responsiveness = fraction(RESPONSIVENESS_PERCENT.get());
		config.turnSpeed = fraction(TURN_SPEED_PERCENT.get());
		FhgConfig.set(config);
	}

	private static int percent(double fraction) {
		return (int) Math.round(fraction * 100.0);
	}

	private static double fraction(int percent) {
		return percent / 100.0;
	}
}
