package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

public class LootTableUtil {

    public static LootTable getLootFromTable(Level level, ResourceLocation resourceLocation) {

        return level.getServer().reloadableRegistries()
                .getLootTable(ResourceKey
                        .create(Registries.LOOT_TABLE, resourceLocation));

    }

}
