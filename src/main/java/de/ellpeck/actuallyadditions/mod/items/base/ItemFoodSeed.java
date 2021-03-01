/*
 * This file ("ItemFoodSeed.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class ItemFoodSeed extends ItemSeedFood {

    public final Block plant;
    public final String name;
    public final String oredictName;
    private final int maxUseDuration;

    public ItemFoodSeed(String name, String oredictName, Block plant, Item returnItem, int returnMeta, int healAmount, float saturation, int maxUseDuration) {
        super(healAmount, saturation, plant, Blocks.FARMLAND);
        this.name = name;
        this.oredictName = oredictName;
        this.plant = plant;
        this.maxUseDuration = maxUseDuration;

        if (plant instanceof BlockPlant) {
            ((BlockPlant) plant).doStuff(this, returnItem, returnMeta);
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return this.maxUseDuration;
    }

    @Override
    public BlockState getPlant(IBlockAccess world, BlockPos pos) {
        return this.plant.getDefaultState();
    }
}
