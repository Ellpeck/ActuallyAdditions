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
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ReedFarmerBehavior implements IFarmerBehavior{

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer){
        int use = 250;
        if(farmer.getEnergy() >= use){
            if(seed.getItem() == Items.REEDS){
                if(Blocks.REEDS.canPlaceBlockAt(world, pos)){
                    world.setBlockState(pos, Blocks.REEDS.getDefaultState(), 2);
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
        int use = 250;
        if(farmer.getEnergy() >= use){
            IBlockState state = world.getBlockState(pos);
            if(state.getBlock() instanceof BlockReed){
                FarmerResult result = FarmerResult.STOP_PROCESSING;

                for(int i = 2; i >= 1; --i){
                    if(farmer.getEnergy() >= use){
                        BlockPos up = pos.up(i);
                        IBlockState upState = world.getBlockState(up);
                        if(upState.getBlock() instanceof BlockReed){
                            List<ItemStack> drops = upState.getBlock().getDrops(world, up, upState, 0);

                            if(drops != null && !drops.isEmpty()){
                                if(farmer.addToOutputInventory(drops, false)){
                                    world.playEvent(2001, up, Block.getStateId(upState));
                                    world.setBlockToAir(up);

                                    farmer.extractEnergy(use);
                                    farmer.addToOutputInventory(drops, true);

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
    public int getPriority(){
        return 0;
    }
}
