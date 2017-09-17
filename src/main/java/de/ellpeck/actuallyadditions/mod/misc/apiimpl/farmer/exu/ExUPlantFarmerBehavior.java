/*
 * This file ("ExUPlantFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.exu;

import de.ellpeck.actuallyadditions.api.farmer.FarmerResult;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ExUPlantFarmerBehavior implements IFarmerBehavior{

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer){
        int use = 600;
        if(farmer.getEnergy() >= use){
            if(StackUtil.isValid(seed)){
                Item item = seed.getItem();
                ResourceLocation reg = item.getRegistryName();

                if(reg != null && this.getPlantName().equals(reg.toString())){
                    if(item instanceof ItemBlock){
                        Block block = ((ItemBlock)item).getBlock();
                        if(block != null){
                            IBlockState stateThere = world.getBlockState(pos);
                            Block blockThere = stateThere.getBlock();

                            if(world.isAirBlock(pos) || blockThere.isReplaceable(world, pos)){
                                BlockPos posBelow = pos.down();
                                IBlockState stateBelow = world.getBlockState(posBelow);

                                if(this.canPlaceOn(stateBelow.getBlock())){
                                    world.setBlockState(pos, block.getDefaultState(), 2);
                                    farmer.extractEnergy(use);

                                    return FarmerResult.SUCCESS;
                                }
                            }

                            return FarmerResult.STOP_PROCESSING;
                        }
                    }
                }
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer){
        int use = 600;
        if(farmer.getEnergy() >= use){
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            ResourceLocation reg = block.getRegistryName();

            if(reg != null && this.getPlantName().equals(reg.toString())){
                if(block.getMetaFromState(state) >= this.getMaxStage()){
                	NonNullList<ItemStack> drops = NonNullList.create();
                    block.getDrops(drops, world, pos, state, 0);
                    for(ItemStack stack : drops){
                        if(StackUtil.isValid(stack)){
                            ResourceLocation itemReg = stack.getItem().getRegistryName();
                            if(itemReg != null && this.getPlantName().equals(itemReg.toString())){
                                if(StackUtil.getStackSize(stack) <= 1){
                                    drops.remove(stack);
                                    break;
                                }
                                else{
                                    StackUtil.addStackSize(stack, -1);
                                }
                            }
                        }
                    }

                    if(farmer.addToOutputInventory(drops, false)){
                        farmer.addToOutputInventory(drops, true);

                        world.playEvent(2001, pos, Block.getStateId(state));
                        world.setBlockState(pos, block.getDefaultState(), 2);

                        farmer.extractEnergy(use);

                        return FarmerResult.SUCCESS;
                    }
                }

                return FarmerResult.STOP_PROCESSING;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public int getPriority(){
        return 10;
    }

    protected abstract String getPlantName();

    protected abstract boolean canPlaceOn(Block block);

    protected abstract int getMaxStage();
}
