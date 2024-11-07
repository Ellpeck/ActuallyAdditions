package de.ellpeck.actuallyadditions.mod.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec CLIENT_CONFIG;
    public static ModConfigSpec.BooleanValue HIDE_ENERGY_OVERLAY;


    static {
        HIDE_ENERGY_OVERLAY = BUILDER.comment("If true, when looking at blocks the Energy Overlay will be hidden.")
                .define("hideEnergyOverlay", false);

        CLIENT_CONFIG = BUILDER.build();
    }
}
