package de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer;

import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ReedFarmerBehavior implements IFarmerBehavior{

    @Override
    public boolean tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer){
        int use = 250;
        if(farmer.getEnergy() >= use){
            if(seed.getItem() == Items.REEDS){
                if(Blocks.REEDS.canPlaceBlockAt(world, pos)){
                    world.setBlockState(pos, Blocks.REEDS.getDefaultState(), 2);
                    farmer.extractEnergy(use);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean tryHarvestPlant(World world, BlockPos pos, IFarmer farmer){
        int use = 250;
        if(farmer.getEnergy() >= use){
            IBlockState state = world.getBlockState(pos);
            if(state.getBlock() instanceof BlockReed){
                for(int i = 2; i >= 1; --i){
                    if(farmer.getEnergy() >= use){
                        BlockPos up = pos.up(i);
                        IBlockState upState = world.getBlockState(up);
                        if(upState.getBlock() instanceof BlockReed){
                            List<ItemStack> drops = upState.getBlock().getDrops(world, up, upState, 0);

                            if(drops != null && !drops.isEmpty()){
                                if(farmer.addToOutputInventory(drops, false)){
                                    world.playEvent(2011, up, Block.getStateId(upState));
                                    world.setBlockToAir(up);

                                    farmer.extractEnergy(use);
                                    farmer.addToOutputInventory(drops, true);
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
