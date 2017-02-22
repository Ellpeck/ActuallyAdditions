/*
 * This file ("DefaultFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;

public class DefaultFarmerBehavior implements IFarmerBehavior{

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer){
        int use = 350;
        if(farmer.getEnergy() >= use*2){
            if(defaultPlant(world, pos, this.getPlantablePlantFromStack(seed, world, pos), farmer, use)){
                return FarmerResult.SUCCESS;
            }
        }
        return FarmerResult.FAIL;
    }

    public static boolean defaultPlant(World world, BlockPos pos, IBlockState toPlant, IFarmer farmer, int use){
        if(toPlant != null){
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if(world.isAirBlock(pos) || block.isReplaceable(world, pos)){
                BlockPos farmland = pos.down();
                Block farmlandBlock = world.getBlockState(farmland).getBlock();

                if(tryPlant(toPlant, world, pos)){
                    farmer.extractEnergy(use);
                    return true;
                }
                else{
                    if(farmlandBlock instanceof BlockDirt || farmlandBlock instanceof BlockGrass){
                        world.setBlockState(farmland, Blocks.FARMLAND.getDefaultState(), 2);
                        world.setBlockToAir(pos);
                        world.playSound(null, farmland, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                        farmer.extractEnergy(use);

                        if(tryPlant(toPlant, world, pos)){
                            farmer.extractEnergy(use);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean tryPlant(IBlockState toPlant, World world, BlockPos pos){
        BlockBush plantBlock = (BlockBush)toPlant.getBlock();
        if(plantBlock.canPlaceBlockAt(world, pos) && plantBlock.canBlockStay(world, pos, toPlant)){
            //This fixes a bug with Beetroot being able to be planted anywhere because Minecraft sucks
            if(plantBlock != Blocks.BEETROOTS || Blocks.WHEAT.canPlaceBlockAt(world, pos)){
                world.setBlockState(pos, toPlant, 3);
                return true;
            }
        }
        return false;
    }

    @Override
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer){
        int use = 250;
        if(farmer.getEnergy() >= use){
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if(block instanceof BlockCrops){
                if(((BlockCrops)block).isMaxAge(state)){
                    List<ItemStack> seeds = new ArrayList<ItemStack>();
                    List<ItemStack> other = new ArrayList<ItemStack>();

                    List<ItemStack> drops = block.getDrops(world, pos, state, 0);
                    for(ItemStack stack : drops){
                        if(this.getPlantableFromStack(stack) != null){
                            seeds.add(stack);
                        }
                        else{
                            other.add(stack);
                        }
                    }

                    boolean putSeeds = true;
                    if(!farmer.addToSeedInventory(seeds, false)){
                        other.addAll(seeds);
                        putSeeds = false;
                    }

                    if(farmer.addToOutputInventory(other, false)){
                        farmer.addToOutputInventory(other, true);

                        if(putSeeds){
                            farmer.addToSeedInventory(seeds, true);
                        }

                        world.playEvent(2001, pos, Block.getStateId(state));
                        world.setBlockToAir(pos);

                        farmer.extractEnergy(use);
                        return FarmerResult.SUCCESS;
                    }
                }
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public int getPriority(){
        return 0;
    }

    private IBlockState getPlantablePlantFromStack(ItemStack stack, World world, BlockPos pos){
        if(StackUtil.isValid(stack)){
            IPlantable plantable = this.getPlantableFromStack(stack);
            if(plantable != null){
                IBlockState state = plantable.getPlant(world, pos);
                if(state != null && state.getBlock() instanceof BlockCrops){
                    return state;
                }
            }
        }
        return null;
    }

    private IPlantable getPlantableFromStack(ItemStack stack){
        Item item = stack.getItem();
        if(item instanceof IPlantable){
            return (IPlantable)item;
        }
        else if(item instanceof ItemBlock){
            Block block = Block.getBlockFromItem(item);
            if(block instanceof IPlantable){
                return (IPlantable)block;
            }
        }
        return null;
    }
}
