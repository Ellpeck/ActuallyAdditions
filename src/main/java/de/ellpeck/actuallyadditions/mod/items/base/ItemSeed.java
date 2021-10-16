/*
 * This file ("ItemSeed.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockPlant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class ItemSeed extends ItemSeeds {

    public final Block plant;
    public final String oredictName;

    public ItemSeed(String oredictName, Block plant, Item returnItem, int returnMeta) {
        super(plant, Blocks.FARMLAND);
        this.oredictName = oredictName;
        this.plant = plant;

        if (plant instanceof BlockPlant) {
            ((BlockPlant) plant).doStuff(this, returnItem, returnMeta);
        }
    }

    @Override
    public BlockState getPlant(IBlockAccess world, BlockPos pos) {
        return this.plant.defaultBlockState();
    }
}
