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
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemSeed /* extends ItemSeeds*/ {

    public final Block plant;
    public final String oredictName;

    public ItemSeed(String oredictName, Block plant, Item returnItem, int returnMeta) {
        //super(plant, Blocks.FARMLAND);
        this.oredictName = oredictName;
        this.plant = plant;

        if (plant instanceof BlockPlant) {
            //((BlockPlant) plant).doStuff(this, returnItem, returnMeta); //TODO
        }
    }

    //@Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return this.plant.defaultBlockState();
    }
}
