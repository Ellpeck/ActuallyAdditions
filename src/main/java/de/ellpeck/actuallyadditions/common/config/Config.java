package de.ellpeck.actuallyadditions.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

import static net.minecraftforge.common.ForgeConfigSpec.Builder;

public class Config {
    protected static final Builder SERVER_BUILDER = new Builder();
    protected static final Builder CLIENT_BUILDER = new Builder();
    protected static final Builder COMMON_BUILDER = new Builder();

    public static final GeneralConfig GENERAL = new GeneralConfig();
    public static final ItemConfig ITEM_CONFIG = new ItemConfig();

    public static final ForgeConfigSpec SERVER_CONFIG = SERVER_BUILDER.build();
    public static final ForgeConfigSpec CLIENT_CONFIG = CLIENT_BUILDER.build();
    public static final ForgeConfigSpec COMMON_CONFIG = COMMON_BUILDER.build();
}
