/*
 * This file ("TheMiscBlocks.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.metalists;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.IStringSerializable;

public enum TheMiscBlocks implements IStringSerializable{

    QUARTZ_PILLAR("black_quartz_pillar", EnumRarity.RARE),
    QUARTZ_CHISELED("black_quartz_chiseled", EnumRarity.RARE),
    QUARTZ("black_quartz", EnumRarity.RARE),
    ORE_QUARTZ("ore_black_quartz", EnumRarity.EPIC),
    WOOD_CASING("wood_casing", EnumRarity.COMMON),
    CHARCOAL_BLOCK("charcoal", EnumRarity.COMMON),
    ENDERPEARL_BLOCK("enderpearl", EnumRarity.RARE),
    LAVA_FACTORY_CASE("lava_factory_case", EnumRarity.UNCOMMON),
    ENDER_CASING("ender_casing", EnumRarity.EPIC),
    IRON_CASING("iron_casing", EnumRarity.RARE);

    public final String name;
    public final EnumRarity rarity;

    TheMiscBlocks(String name, EnumRarity rarity){
        this.name = name;
        this.rarity = rarity;
    }

    @Override
    public String getName(){
        return this.name;
    }
}