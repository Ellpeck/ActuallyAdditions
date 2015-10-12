/*
 * This file ("DispenserHandlerFillBucket.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class DispenserHandlerFillBucket extends BehaviorDefaultDispenseItem{

    @Override
    public ItemStack dispenseStack(IBlockSource source, ItemStack emptyBucket){
        EnumFacing facing = BlockDispenser.func_149937_b(source.getBlockMetadata());
        int x = source.getXInt()+facing.getFrontOffsetX();
        int y = source.getYInt()+facing.getFrontOffsetY();
        int z = source.getZInt()+facing.getFrontOffsetZ();

        ItemStack filledBucket = this.tryFillBucket(source, x, y, z, emptyBucket);

        //Bucket couldn't be filled
        if(filledBucket == null){
            return new BehaviorDefaultDispenseItem().dispense(source, emptyBucket);
        }

        emptyBucket.stackSize--;
        //Only one bucket was there -> new bucket gets placed in slot
        if(emptyBucket.stackSize <= 0){
            emptyBucket = filledBucket.copy();
        }
        //Not enough space for the bucket in the inventory?
        else if(((TileEntityDispenser)source.getBlockTileEntity()).func_146019_a(filledBucket.copy()) < 0){
            new BehaviorDefaultDispenseItem().dispense(source, filledBucket.copy());
        }
        //At this point, it is either the filled bucket at the empty bucket was in or the bucket in another slot of the Dispenser
        return emptyBucket;
    }

    private ItemStack tryFillBucket(IBlockSource source, int x, int y, int z, ItemStack bucket){
        Block block = source.getWorld().getBlock(x, y, z);

        if(block == Blocks.water || block == Blocks.flowing_water){
            if(source.getWorld().getBlockMetadata(x, y, z) == 0){
                source.getWorld().setBlockToAir(x, y, z);
                return new ItemStack(Items.water_bucket);
            }
        }
        else if(block == Blocks.lava || block == Blocks.flowing_lava){
            if(source.getWorld().getBlockMetadata(x, y, z) == 0){
                source.getWorld().setBlockToAir(x, y, z);
                return new ItemStack(Items.lava_bucket);
            }
        }
        else if(block instanceof IFluidBlock && ((IFluidBlock)block).canDrain(source.getWorld(), x, y, z)){
            ItemStack stack = FluidContainerRegistry.fillFluidContainer(((IFluidBlock)block).drain(source.getWorld(), x, y, z, false), bucket);
            if(stack != null){
                ((IFluidBlock)block).drain(source.getWorld(), x, y, z, true);
                return stack;
            }
        }
        return null;
    }

}
