package de.ellpeck.actuallyadditions.mod.blocks.metalists;

import net.minecraft.item.Rarity;
import net.minecraft.util.IStringSerializable;

public enum TheMiscBlocks implements IStringSerializable {

    QUARTZ_PILLAR("black_quartz_pillar", Rarity.RARE),
    QUARTZ_CHISELED("black_quartz_chiseled", Rarity.RARE),
    QUARTZ("black_quartz", Rarity.RARE),
    ORE_QUARTZ("ore_black_quartz", Rarity.EPIC),
    WOOD_CASING("wood_casing", Rarity.COMMON),
    CHARCOAL_BLOCK("charcoal", Rarity.COMMON),
    ENDERPEARL_BLOCK("enderpearl", Rarity.RARE),
    LAVA_FACTORY_CASE("lava_factory_case", Rarity.UNCOMMON),
    ENDER_CASING("ender_casing", Rarity.EPIC),
    IRON_CASING("iron_casing", Rarity.RARE);

    public final String name;
    public final Rarity rarity;

    TheMiscBlocks(String name, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    @Override
    public String getName() {
        return this.name;
    }
}