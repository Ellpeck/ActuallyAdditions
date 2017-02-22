/*
 * This file ("MelonPumpkinFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MelonPumpkinFarmerBehavior implements IFarmerBehavior{

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer){
        int use = 350;
        if(farmer.getEnergy() >= use*2){
            if(StackUtil.isValid(seed)){
                Item seedItem = seed.getItem();
                boolean isPumpkin = seedItem == Items.PUMPKIN_SEEDS;
                if(isPumpkin || seedItem == Items.MELON_SEEDS){
                    if((pos.getX()%2 == 0) == (pos.getZ()%2 == 0)){
                        IBlockState toPlant = (isPumpkin ? Blocks.PUMPKIN_STEM : Blocks.MELON_STEM).getDefaultState();

                        if(DefaultFarmerBehavior.defaultPlant(world, pos, toPlant, farmer, use)){
                            return FarmerResult.SUCCESS;
                        }
                    }
                    return FarmerResult.STOP_PROCESSING;
                }
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer){
        int use = 500;
        if(farmer.getEnergy() >= use){
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if(block == Blocks.PUMPKIN || block == Blocks.MELON_BLOCK){
                List<ItemStack> drops = state.getBlock().getDrops(world, pos, state, 0);
                if(drops != null && !drops.isEmpty()){
                    if(farmer.addToOutputInventory(drops, false)){
                        world.playEvent(2001, pos, Block.getStateId(state));
                        world.setBlockToAir(pos);

                        farmer.extractEnergy(use);
                        farmer.addToOutputInventory(drops, true);

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
}
