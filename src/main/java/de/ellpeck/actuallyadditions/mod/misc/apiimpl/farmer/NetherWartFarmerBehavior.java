/*
 * This file ("NetherWartFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.block.NetherWartBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.List;

public class NetherWartFarmerBehavior implements IFarmerBehavior {

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer) {
        int use = 500;
        if (farmer.getEnergy() >= use) {
            if (seed.getItem() == Items.NETHER_WART) {
                if (world.getBlockState(pos.below()).getBlock().canSustainPlant(world.getBlockState(pos.below()), world, pos.below(), Direction.UP, (IPlantable) Items.NETHER_WART)) {
                    world.setBlock(pos, Blocks.NETHER_WART.defaultBlockState(), 2);
                    farmer.extractEnergy(use);
                    return FarmerResult.SUCCESS;
                }
                return FarmerResult.FAIL;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(ServerWorld world, BlockPos pos, IFarmer farmer) {
        int use = 500;
        if (farmer.getEnergy() >= use) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof NetherWartBlock) {
                if (state.getValue(BlockStateProperties.AGE_3) >= 3) {
                    List<ItemStack> drops = state.getDrops(new LootContext.Builder(world)
                        .withParameter(LootParameters.ORIGIN, new Vector3d(pos.getX(), pos.getY(), pos.getZ()))
                        .withParameter(LootParameters.TOOL, ItemStack.EMPTY));
                    if (!drops.isEmpty()) {
                        boolean toInput = farmer.canAddToSeeds(drops);
                        if (toInput || farmer.canAddToOutput(drops)) {
                            world.levelEvent(2001, pos, Block.getId(state));
                            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

                            if (toInput) {
                                farmer.addToSeeds(drops);
                            } else {
                                farmer.addToOutput(drops);
                            }

                            farmer.extractEnergy(use);
                            return FarmerResult.SUCCESS;
                        }
                    }
                }
                return FarmerResult.FAIL;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
