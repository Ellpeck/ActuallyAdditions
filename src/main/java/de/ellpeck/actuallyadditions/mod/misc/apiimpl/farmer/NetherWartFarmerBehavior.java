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
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.List;

public class NetherWartFarmerBehavior implements IFarmerBehavior{

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer){
        int use = 500;
        if(farmer.getEnergy() >= use){
            if(seed.getItem() == Items.NETHER_WART){
                if(world.getBlockState(pos.down()).getBlock().canSustainPlant(world.getBlockState(pos.down()), world, pos.down(), EnumFacing.UP, (IPlantable) Items.NETHER_WART)){
                    world.setBlockState(pos, Blocks.NETHER_WART.getDefaultState(), 2);
                    farmer.extractEnergy(use);
                    return FarmerResult.SUCCESS;
                }
                return FarmerResult.STOP_PROCESSING;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer){
        int use = 500;
        if(farmer.getEnergy() >= use){
            IBlockState state = world.getBlockState(pos);
            if(state.getBlock() instanceof BlockNetherWart){
                if(state.getValue(BlockNetherWart.AGE) >= 3){
                    NonNullList<ItemStack> output = NonNullList.create();
                    state.getBlock().getDrops(output, world, pos, state, 0);
                    if(output != null && !output.isEmpty()){
                        boolean toInput = farmer.addToSeedInventory(output, false);
                        if(toInput || farmer.addToOutputInventory(output, false)){
                            world.playEvent(2001, pos, Block.getStateId(state));
                            world.setBlockToAir(pos);

                            if(toInput){
                                farmer.addToSeedInventory(output, true);
                            }
                            else{
                                farmer.addToOutputInventory(output, true);
                            }

                            farmer.extractEnergy(use);
                            return FarmerResult.SUCCESS;
                        }
                    }
                }
                return FarmerResult.STOP_PROCESSING;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public int getPriority(){
        return 0;
    }
}
