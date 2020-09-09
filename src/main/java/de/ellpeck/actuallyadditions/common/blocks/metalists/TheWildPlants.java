package de.ellpeck.actuallyadditions.blocks.metalists;

import com.google.common.base.Preconditions;
import de.ellpeck.actuallyadditions.blocks.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Rarity;
import net.minecraft.util.IStringSerializable;

public enum TheWildPlants implements IStringSerializable {

    CANOLA("canola", Rarity.RARE, InitBlocks.blockCanola),
    FLAX("flax", Rarity.RARE, InitBlocks.blockFlax),
    RICE("rice", Rarity.RARE, InitBlocks.blockRice),
    COFFEE("coffee", Rarity.RARE, InitBlocks.blockCoffee);

    final String name;
    final Rarity rarity;
    final Block normal;

    TheWildPlants(String name, Rarity rarity, Block normal) {
        this.name = name;
        this.rarity = rarity;
        this.normal = Preconditions.checkNotNull(normal, "TheWildPlants was loaded before block init!");
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    public Block getNormalVersion() {
        return this.normal;
    }
}