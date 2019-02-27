/*
 * This file ("TheWildPlants.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.metalists;

import com.google.common.base.Preconditions;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.IStringSerializable;

public enum TheWildPlants implements IStringSerializable {

    CANOLA("canola", EnumRarity.RARE, InitBlocks.blockCanola),
    FLAX("flax", EnumRarity.RARE, InitBlocks.blockFlax),
    RICE("rice", EnumRarity.RARE, InitBlocks.blockRice),
    COFFEE("coffee", EnumRarity.RARE, InitBlocks.blockCoffee);

    final String name;
    final EnumRarity rarity;
    final Block normal;

    TheWildPlants(String name, EnumRarity rarity, Block normal) {
        this.name = name;
        this.rarity = rarity;
        this.normal = Preconditions.checkNotNull(normal, "TheWildPlants was loaded before block init!");
    }

    @Override
    public String getName() {
        return this.name;
    }

    public EnumRarity getRarity() {
        return this.rarity;
    }

    public Block getNormalVersion() {
        return this.normal;
    }
}