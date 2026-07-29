package com.fhg;

/**
 * Platform-agnostic holder for the movement tunables.
 *
 * <p>This class deliberately does no file I/O. Fabric backs it with a JSON file read through Cloth
 * Config, NeoForge backs it with a {@code ModConfigSpec}; both push their values into the single
 * instance here, which is what the mixin reads.
 *
 * <p>Multipliers are relative to vanilla, and are linear: a {@link #speedMultiplier} of 3 means
 * three times vanilla's top speed. Vanilla's ridden Happy Ghast tops out at roughly 3.3 blocks per
 * second, which is slower than walking.
 */
public class FhgConfig {
	/** Vanilla's per-tick velocity retention while flying, from {@code LivingEntity#travelFlying}. */
	public static final double VANILLA_AIR_DRAG = 0.91;

	/** Air drag at maximum responsiveness. Less drift, quicker to reach and shed speed. */
	public static final double SNAPPY_AIR_DRAG = 0.7;

	/** Vanilla's steering lerp per tick, from {@code HappyGhast#tickRidden}. */
	public static final double VANILLA_TURN_SPEED = 0.08;

	public static final boolean DEFAULT_ENABLED = true;
	// Tuned to make the ghast practical rather than powerful. At these values it cruises at about
	// 6.6 blocks/sec and sprints to about 8.6 — faster than a sprinting player, slower than a horse.
	// Vanilla is 3.3, which is slower than walking. The ranges below go much further for anyone who
	// wants it; the defaults deliberately do not.
	public static final double DEFAULT_SPEED_MULTIPLIER = 2.0;
	public static final double DEFAULT_VERTICAL_MULTIPLIER = 1.5;
	public static final boolean DEFAULT_SPRINT_BOOST_ENABLED = true;
	public static final double DEFAULT_SPRINT_BOOST = 1.3;
	public static final double DEFAULT_RESPONSIVENESS = 0.5;
	public static final double DEFAULT_TURN_SPEED = 0.15;

	public static final double MIN_SPEED_MULTIPLIER = 1.0;
	public static final double MAX_SPEED_MULTIPLIER = 10.0;
	public static final double MIN_VERTICAL_MULTIPLIER = 0.5;
	public static final double MAX_VERTICAL_MULTIPLIER = 6.0;
	public static final double MIN_SPRINT_BOOST = 1.0;
	public static final double MAX_SPRINT_BOOST = 4.0;
	public static final double MIN_TURN_SPEED = 0.02;
	public static final double MAX_TURN_SPEED = 0.5;

	private static FhgConfig instance = new FhgConfig();

	/** Master switch. When false the ghast behaves exactly like vanilla. */
	public boolean enabled = DEFAULT_ENABLED;

	/** Top speed as a multiple of vanilla. */
	public double speedMultiplier = DEFAULT_SPEED_MULTIPLIER;

	/** Extra multiplier on climb and dive speed, on top of {@link #speedMultiplier}. */
	public double verticalMultiplier = DEFAULT_VERTICAL_MULTIPLIER;

	/** Whether holding the sprint key while riding gives a speed boost. */
	public boolean sprintBoostEnabled = DEFAULT_SPRINT_BOOST_ENABLED;

	/** Top speed multiplier applied while the rider is sprinting. */
	public double sprintBoost = DEFAULT_SPRINT_BOOST;

	/** 0 keeps vanilla's floaty drift, 1 is as responsive as this mod goes. Top speed is unaffected. */
	public double responsiveness = DEFAULT_RESPONSIVENESS;

	/** Fraction of the remaining turn applied each tick. Vanilla is {@value #VANILLA_TURN_SPEED}. */
	public double turnSpeed = DEFAULT_TURN_SPEED;

	public static FhgConfig get() {
		return instance;
	}

	public static void set(FhgConfig config) {
		config.clamp();
		instance = config;
	}

	/**
	 * Keeps hand-edited files inside the ranges the movement code was tuned for. In particular the
	 * multipliers feed a per-tick acceleration, so unbounded values make the ghast uncontrollable.
	 */
	public void clamp() {
		this.speedMultiplier = clamp(this.speedMultiplier, MIN_SPEED_MULTIPLIER, MAX_SPEED_MULTIPLIER);
		this.verticalMultiplier = clamp(this.verticalMultiplier, MIN_VERTICAL_MULTIPLIER, MAX_VERTICAL_MULTIPLIER);
		this.sprintBoost = clamp(this.sprintBoost, MIN_SPRINT_BOOST, MAX_SPRINT_BOOST);
		this.responsiveness = clamp(this.responsiveness, 0.0, 1.0);
		this.turnSpeed = clamp(this.turnSpeed, MIN_TURN_SPEED, MAX_TURN_SPEED);
	}

	private static double clamp(double value, double min, double max) {
		return Math.min(Math.max(value, min), max);
	}
}
