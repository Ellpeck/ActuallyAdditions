package de.ellpeck.actuallyadditions.common.config;

import static de.ellpeck.actuallyadditions.common.config.Config.*;
import static net.minecraftforge.common.ForgeConfigSpec.*;

public class GeneralConfig {
    public final BooleanValue advancedInfo;
    public final IntValue tileEntityUpdateInterval;

    public GeneralConfig() {
        CLIENT_BUILDER.comment("Actually Additions General Config").push("general");
        SERVER_BUILDER.comment("Actually Additions General Config").push("general");

        advancedInfo = CLIENT_BUILDER
                .comment("Shows advanced item info when holding control on every item")
                .define("Advanced Info", true);

        tileEntityUpdateInterval = CLIENT_BUILDER
                .comment("The amount of ticks waited before a TileEntity sends an additional update to the client")
                .defineInRange("Tile Entities Update Interval", 5, 1, 100);

        CLIENT_BUILDER.pop();
    }
}
