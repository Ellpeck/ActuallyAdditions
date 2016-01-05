/*
 * This file ("TheWildPlants.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.metalists;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;

public enum TheWildPlants{

    CANOLA("Canola", EnumRarity.rare, InitBlocks.blockCanola),
    FLAX("Flax", EnumRarity.rare, InitBlocks.blockFlax),
    RICE("Rice", EnumRarity.rare, InitBlocks.blockRice),
    COFFEE("Coffee", EnumRarity.rare, InitBlocks.blockCoffee);

    public final String name;
    public final EnumRarity rarity;
    public final Block wildVersionOf;

    TheWildPlants(String name, EnumRarity rarity, Block wildVersionOf){
        this.name = name;
        this.rarity = rarity;
        this.wildVersionOf = wildVersionOf;
    }
}