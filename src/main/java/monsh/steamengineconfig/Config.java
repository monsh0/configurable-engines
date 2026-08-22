package monsh.steamengineconfig;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final ForgeConfigSpec SPEC;
    public static final Config INSTANCE;

    static {
        Pair<Config, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Config::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }

    public final ForgeConfigSpec.EnumValue<HeatLevel> passiveSourceHeat;
    public final ForgeConfigSpec.EnumValue<HeatLevel> unlitBurnerHeat;
    public final ForgeConfigSpec.IntValue litBurnerHeat;
    public final ForgeConfigSpec.IntValue superheatedBurnerHeat;

    public final ForgeConfigSpec.IntValue waterPerTick;
    public final ForgeConfigSpec.IntValue engineRPM;
    public final ForgeConfigSpec.DoubleValue levelsPerEngine;
    public final ForgeConfigSpec.DoubleValue passiveEfficiency;

    public final ForgeConfigSpec.DoubleValue globalRpmMultiplier;
    public final ForgeConfigSpec.DoubleValue globalStressMultiplier;
    public final ForgeConfigSpec.DoubleValue globalFuelMultiplier;

    public final ForgeConfigSpec.DoubleValue normalRpmMultiplier;
    public final ForgeConfigSpec.DoubleValue normalStressMultiplier;

    public final ForgeConfigSpec.DoubleValue modularRpmMultiplier;
    public final ForgeConfigSpec.DoubleValue modularStressMultiplier;
    public final ForgeConfigSpec.IntValue modularMaxLength;

    public final ForgeConfigSpec.DoubleValue hugeRpmMultiplier;
    public final ForgeConfigSpec.DoubleValue hugeStressMultiplier;

    public final ForgeConfigSpec.DoubleValue turboSpeedMultiplier;
    public final ForgeConfigSpec.DoubleValue turboStressMultiplier;
    public final ForgeConfigSpec.BooleanValue turboEnabled;

    public final ForgeConfigSpec.DoubleValue burnerFuelMultiplier;
    public final ForgeConfigSpec.IntValue burnerTankCapacity;
    public final ForgeConfigSpec.DoubleValue burnerValveSpeedDivisor;
    public final ForgeConfigSpec.IntValue burnerDrainRate;
    public final ForgeConfigSpec.IntValue burnerDrainInterval;
    public final ForgeConfigSpec.IntValue burnerRedstoneValveState;
    public final ForgeConfigSpec.DoubleValue burnerHeatThresholdSeething;
    public final ForgeConfigSpec.DoubleValue burnerHeatThresholdKindled;
    public final ForgeConfigSpec.DoubleValue burnerHeatThresholdFading;
    public final ForgeConfigSpec.DoubleValue burnerHeatThresholdSmouldering;

    Config(ForgeConfigSpec.Builder builder) {
        builder.comment("Some changes may require restart.");

        builder.push("steam_engine");

        builder.push("heat");
        passiveSourceHeat = builder
                .comment("Passive blocks (eg campfire) heat mode")
                .defineEnum("passive_source_heat", HeatLevel.PASSIVE);
        unlitBurnerHeat = builder
                .comment("Unlit blaze burner heat mode")
                .defineEnum("unlit_burner_heat", HeatLevel.PASSIVE);
        litBurnerHeat = builder
                .comment("Lit blaze burner heat level")
                .defineInRange("lit_burner_heat", 1, 1, Integer.MAX_VALUE);
        superheatedBurnerHeat = builder
                .comment("Superheated blaze burner heat level")
                .defineInRange("superheated_burner_heat", 2, 2, Integer.MAX_VALUE);
        builder.pop();

        builder.push("boiler");
        waterPerTick = builder
                .comment("Water consumed per tick per heat level (mB)")
                .defineInRange("water_per_tick", 10, 0, Integer.MAX_VALUE);
        engineRPM = builder
                .comment("Base RPM at full heat")
                .defineInRange("engine_rpm", 64, -Integer.MAX_VALUE, Integer.MAX_VALUE);
        levelsPerEngine = builder
                .comment("Heat levels utilized per engine")
                .defineInRange("levels_per_engine", 1.0, 0.0, Double.MAX_VALUE);
        passiveEfficiency = builder
                .comment("Passive mode efficiency (0.0 = no power, 1.0 = full power)")
                .defineInRange("passive_efficiency", 1/8.0, 0.0, 1.0);
        builder.pop();

        builder.pop();

        builder.push("Create: Diesel Generators");

        builder.push("global");
        globalRpmMultiplier = builder
                .comment("Global RPM multiplier for all diesel engines")
                .defineInRange("global_rpm_multiplier", 1.0, -Double.MAX_VALUE, Double.MAX_VALUE);
        globalStressMultiplier = builder
                .comment("Global stress multiplier for all diesel engines")
                .defineInRange("global_stress_multiplier", 1.0, 0.0, Double.MAX_VALUE);
        globalFuelMultiplier = builder
                .comment("(BROKEN) Global fuel consumption multiplier for all diesel engines")
                .defineInRange("global_fuel_multiplier", 1.0, 0.0, Double.MAX_VALUE);
        builder.pop();

        builder.push("normal");
        normalRpmMultiplier = builder
                .comment("Normal diesel engine RPM multiplier")
                .defineInRange("rpm_multiplier", 1.0, -Double.MAX_VALUE, Double.MAX_VALUE);
        normalStressMultiplier = builder
                .comment("Normal diesel engine stress multiplier")
                .defineInRange("stress_multiplier", 1.0, 0.0, Double.MAX_VALUE);
        builder.pop();

        builder.push("modular");
        modularRpmMultiplier = builder
                .comment("Modular diesel engine RPM multiplier")
                .defineInRange("rpm_multiplier", 1.0, -Double.MAX_VALUE, Double.MAX_VALUE);
        modularStressMultiplier = builder
                .comment("Modular diesel engine stress multiplier")
                .defineInRange("stress_multiplier", 1.0, 0.0, Double.MAX_VALUE);
        modularMaxLength = builder
                .comment("Maximum length of modular diesel engines")
                .defineInRange("max_length", 21, 1, 21);
        builder.pop();

        builder.push("huge");
        hugeRpmMultiplier = builder
                .comment("Huge diesel engine RPM multiplier")
                .defineInRange("rpm_multiplier", 1.0, -Double.MAX_VALUE, Double.MAX_VALUE);
        hugeStressMultiplier = builder
                .comment("Huge diesel engine stress multiplier")
                .defineInRange("stress_multiplier", 1.0, 0.0, Double.MAX_VALUE);
        builder.pop();

        builder.push("turbocharger");
        turboSpeedMultiplier = builder
                .comment("Turbocharger RPM multiplier (overrides CDG's combined multiplier for speed)")
                .defineInRange("speed_multiplier", 2.0, -Double.MAX_VALUE, Double.MAX_VALUE);
        turboStressMultiplier = builder
                .comment("Turbocharger stress capacity multiplier (overrides CDG's combined multiplier for stress)")
                .defineInRange("stress_multiplier", 2.0, 0.0, Double.MAX_VALUE);
        turboEnabled = builder
                .comment("Enable turbocharger upgrades")
                .define("enabled", true);
        builder.pop();


        builder.push("burner");
        burnerFuelMultiplier = builder
                .comment("(BROKEN) Multiplier for burner fuel strength (higher = more heat per fuel)")
                .defineInRange("fuel_multiplier", 1.0, 0.0, Double.MAX_VALUE);
        burnerTankCapacity = builder
                .comment("(BROKEN) Burner fluid tank capacity in mB")
                .defineInRange("tank_capacity", 100, 1, Integer.MAX_VALUE);
        burnerValveSpeedDivisor = builder
                .comment("(BROKEN) Valve speed divisor (lower = faster response to RPM changes)")
                .defineInRange("valve_speed_divisor", 5000.0, 1.0, Double.MAX_VALUE);
        burnerDrainRate = builder
                .comment("Fuel drained per tick when burner is active (mB)")
                .defineInRange("drain_rate", 1, 0, Integer.MAX_VALUE);
        burnerDrainInterval = builder
                .comment("(BROKEN) Drain interval divisor (actual interval = this / valveState)")
                .defineInRange("drain_interval", 10, 1, Integer.MAX_VALUE);
        burnerRedstoneValveState = builder
                .comment("(BROKEN) Valve state when redstone powered")
                .defineInRange("redstone_valve_state", 20, 1, Integer.MAX_VALUE);
        burnerHeatThresholdSeething = builder
                .comment("Minimum heat for SEETHING state (maximum output)")
                .defineInRange("seething_threshold", 1.8, 0.0, Double.MAX_VALUE);
        burnerHeatThresholdKindled = builder
                .comment("Minimum heat for KINDLED state (high output)")
                .defineInRange("kindled_threshold", 1.4, 0.0, Double.MAX_VALUE);
        burnerHeatThresholdFading = builder
                .comment("Minimum heat for FADING state (medium output)")
                .defineInRange("fading_threshold", 1.2, 0.0, Double.MAX_VALUE);
        burnerHeatThresholdSmouldering = builder
                .comment("Minimum heat for SMOULDERING state (low output)")
                .defineInRange("smouldering_threshold", 1.0, 0.0, Double.MAX_VALUE);
        builder.pop();
        builder.pop();

    }
}
