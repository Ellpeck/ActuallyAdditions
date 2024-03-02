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
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ReedFarmerBehavior implements IFarmerBehavior {

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, Level world, BlockPos pos, IFarmer farmer) {
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
    public FarmerResult tryHarvestPlant(ServerLevel world, BlockPos pos, IFarmer farmer) {
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
                            List<ItemStack> drops = state.getDrops(new LootContext.Builder(world)
                                .withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
                                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY));

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
