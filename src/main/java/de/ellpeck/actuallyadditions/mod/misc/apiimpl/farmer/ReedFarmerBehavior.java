/*
 * This file ("ReedFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer;

import de.ellpeck.actuallyadditions.api.farmer.FarmerResult;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ReedFarmerBehavior implements IFarmerBehavior {

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer) {
        int use = 250;
        if (farmer.getEnergy() >= use) {
            if (seed.getItem() == Items.SUGAR_CANE) {
                if (Blocks.SUGAR_CANE.defaultBlockState().canSurvive(world, pos)) {
                    world.setBlock(pos, Blocks.SUGAR_CANE.defaultBlockState(), 2);
                    farmer.extractEnergy(use);
                    return FarmerResult.SUCCESS;
                }
                return FarmerResult.STOP_PROCESSING;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer) {
        int use = 250;
        if (farmer.getEnergy() >= use) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof SugarCaneBlock) {
                FarmerResult result = FarmerResult.STOP_PROCESSING;

                for (int i = 2; i >= 1; --i) {
                    if (farmer.getEnergy() >= use) {
                        BlockPos up = pos.above(i);
                        BlockState upState = world.getBlockState(up);
                        if (upState.getBlock() instanceof SugarCaneBlock) {
                            NonNullList<ItemStack> drops = NonNullList.create();
                            //upState.getBlock().getDrops(drops, world, pos, state, 0);

                            if (!drops.isEmpty()) {
                                if (farmer.canAddToOutput(drops)) {
                                    world.levelEvent(2001, up, Block.getId(upState));
                                    world.setBlockAndUpdate(up, Blocks.AIR.defaultBlockState());

                                    farmer.extractEnergy(use);
                                    farmer.addToOutput(drops);

                                    result = FarmerResult.STOP_PROCESSING;
                                }
                            }
                        }
                    }
                }

                return result;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
