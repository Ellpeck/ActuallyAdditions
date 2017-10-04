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
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntListValues;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CactusFarmerBehavior implements IFarmerBehavior{

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer){
        int use = ConfigIntListValues.FARMER_ENERGY_USE_PER_PLANT.getValue()[1];
        if(farmer.getEnergy() >= use){
            Item item = seed.getItem();
            if(item instanceof ItemBlock){
                Block block = Block.getBlockFromItem(item);
                if(block instanceof BlockCactus){
                    if(block.canPlaceBlockAt(world, pos)){
                        IBlockState state = block.getDefaultState();
                        world.setBlockState(pos, state, 2);
                        world.playEvent(2001, pos, Block.getStateId(state));

                        farmer.extractEnergy(use);
                        return FarmerResult.SUCCESS;
                    }
                    return FarmerResult.STOP_PROCESSING;
                }
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer){
        int use = ConfigIntListValues.FARMER_ENERGY_USE_PER_HARVEST.getValue()[1];
        if(farmer.getEnergy() >= use){
            IBlockState state = world.getBlockState(pos);
            if(state.getBlock() instanceof BlockCactus){
                FarmerResult result = FarmerResult.STOP_PROCESSING;

                for(int i = 2; i >= 1; i--){
                    if(farmer.getEnergy() >= use){
                        BlockPos up = pos.up(i);
                        IBlockState upState = world.getBlockState(up);
                        if(upState.getBlock() instanceof BlockCactus){
                            NonNullList<ItemStack> drops = NonNullList.create();
                            upState.getBlock().getDrops(drops, world, up, upState, 0);

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
