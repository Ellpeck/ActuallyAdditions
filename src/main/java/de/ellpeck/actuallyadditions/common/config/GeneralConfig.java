package de.ellpeck.actuallyadditions.common.config;

import static de.ellpeck.actuallyadditions.common.config.Config.*;
import static net.minecraftforge.common.ForgeConfigSpec.*;

public class GeneralConfig {
    public final BooleanValue advancedInfo;

    public GeneralConfig() {
        CLIENT_BUILDER.comment("Actually Additions General Config").push("general");

        advancedInfo = CLIENT_BUILDER
                .comment("Shows advanced item info when holding control on every item")
                .define("Advanced Info", true);

        CLIENT_BUILDER.pop();
    }
}
