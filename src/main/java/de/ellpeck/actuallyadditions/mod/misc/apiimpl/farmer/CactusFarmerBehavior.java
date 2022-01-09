/*
 * This file ("CactusFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class CactusFarmerBehavior implements IFarmerBehavior {

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer) {
        int use = 250;
        if (farmer.getEnergy() >= use) {
            Item item = seed.getItem();
            if (item instanceof BlockItem) {
                Block block = Block.byItem(item);
                if (block == Blocks.CACTUS) {
                    if (block.defaultBlockState().canSurvive(world, pos)) {
                        BlockState state = block.defaultBlockState();
                        world.setBlock(pos, state, 2);
                        world.levelEvent(2001, pos, Block.getId(state));

                        farmer.extractEnergy(use);
                        return FarmerResult.SUCCESS;
                    }
                    return FarmerResult.FAIL;
                }
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(ServerWorld world, BlockPos pos, IFarmer farmer) {
        int use = 250;
        if (farmer.getEnergy() >= use) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == Blocks.CACTUS) {
                FarmerResult result = FarmerResult.STOP_PROCESSING;

                for (int i = 2; i >= 1; i--) {
                    if (farmer.getEnergy() >= use) {
                        BlockPos up = pos.above(i);
                        BlockState upState = world.getBlockState(up);
                        if (upState.getBlock() == Blocks.CACTUS) {
                            List<ItemStack> drops = state.getDrops(new LootContext.Builder(world)
                                .withParameter(LootParameters.ORIGIN, new Vector3d(pos.getX(), pos.getY(), pos.getZ()))
                                .withParameter(LootParameters.TOOL, ItemStack.EMPTY));

                            if (!drops.isEmpty()) {
                                if (farmer.canAddToOutput(drops)) {
                                    world.levelEvent(2001, up, Block.getId(upState));
                                    world.setBlockAndUpdate(up, Blocks.AIR.defaultBlockState());

                                    farmer.extractEnergy(use);
                                    farmer.addToOutput(drops);

                                    result = FarmerResult.SUCCESS;
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
        return 4;
    }
}
