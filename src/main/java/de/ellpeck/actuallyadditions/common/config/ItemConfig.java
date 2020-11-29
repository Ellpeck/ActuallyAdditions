package de.ellpeck.actuallyadditions.common.config;

import static de.ellpeck.actuallyadditions.common.config.Config.COMMON_BUILDER;
import static net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ItemConfig {
    public final IntValue teleportStaffCost;
    public final IntValue teleportStaffMaxEnergy;

    public ItemConfig() {
        COMMON_BUILDER.comment("Item Config Options").push("items");

        teleportStaffCost = COMMON_BUILDER
                .comment(
                        "The base cost of the Teleport Staff (this is used to calculate the cost per distances as well)",
                        "Don't assign this value higher than the Teleport Staffs max energy!"
                )
                .defineInRange("Teleport Staff Base Cost", 200, 0, 100000);

        teleportStaffMaxEnergy = COMMON_BUILDER
                .comment("The max amount of Crystal Flux stored in the Teleport Staff")
                .defineInRange("Teleport Staff Max Energy", 250000, 0, 1000000);
    }
}
