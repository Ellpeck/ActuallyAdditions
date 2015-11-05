/*
 * This file ("TheMiscBlocks.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.metalists;

import net.minecraft.item.EnumRarity;

public enum TheMiscBlocks{

    QUARTZ_PILLAR("BlackQuartzPillar", EnumRarity.rare),
    QUARTZ_CHISELED("BlackQuartzChiseled", EnumRarity.rare),
    QUARTZ("BlackQuartz", EnumRarity.rare),
    ORE_QUARTZ("OreBlackQuartz", EnumRarity.epic),
    WOOD_CASING("WoodCasing", EnumRarity.common),
    STONE_CASING("StoneCasing", EnumRarity.uncommon),
    CHARCOAL_BLOCK("Charcoal", EnumRarity.common),
    ENDERPEARL_BLOCK("Enderpearl", EnumRarity.rare),
    LAVA_FACTORY_CASE("LavaFactoryCase", EnumRarity.uncommon),
    ENDER_CASING("EnderCasing", EnumRarity.epic),
    IRON_CASING("IronCasing", EnumRarity.rare);

    public final String name;
    public final EnumRarity rarity;

    TheMiscBlocks(String name, EnumRarity rarity){
        this.name = name;
        this.rarity = rarity;
    }
}