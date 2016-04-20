/*
 * This file ("DispenserHandlerFillBucket.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;


import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class DispenserHandlerFillBucket extends BehaviorDefaultDispenseItem{

    @Override
    public ItemStack dispenseStack(IBlockSource source, ItemStack emptyBucket){
        EnumFacing facing = BlockDispenser.getFacing(source.getBlockMetadata());
        int x = source.getBlockTileEntity().getPos().getX()+facing.getFrontOffsetX();
        int y = source.getBlockTileEntity().getPos().getY()+facing.getFrontOffsetY();
        int z = source.getBlockTileEntity().getPos().getZ()+facing.getFrontOffsetZ();

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
        else if(((TileEntityDispenser)source.getBlockTileEntity()).addItemStack(filledBucket.copy()) < 0){
            new BehaviorDefaultDispenseItem().dispense(source, filledBucket.copy());
        }
        //Filled Bucket or Empty Buckets because either they weren't filled or the full one was dispensed out because of missing space
        return emptyBucket;
    }

    private ItemStack tryFillBucket(IBlockSource source, int x, int y, int z, ItemStack bucket){
        BlockPos pos = new BlockPos(x, y, z);
        Block block = PosUtil.getBlock(pos, source.getWorld());

        if(block == Blocks.WATER || block == Blocks.FLOWING_WATER){
            if(PosUtil.getMetadata(pos, source.getWorld()) == 0){
                source.getWorld().setBlockToAir(pos);
                return new ItemStack(Items.WATER_BUCKET);
            }
        }
        else if(block == Blocks.LAVA || block == Blocks.FLOWING_LAVA){
            if(PosUtil.getMetadata(pos, source.getWorld()) == 0){
                source.getWorld().setBlockToAir(pos);
                return new ItemStack(Items.LAVA_BUCKET);
            }
        }
        else if(block instanceof IFluidBlock && ((IFluidBlock)block).canDrain(source.getWorld(), pos)){
            ItemStack stack = FluidContainerRegistry.fillFluidContainer(((IFluidBlock)block).drain(source.getWorld(), pos, false), bucket);
            if(stack != null){
                ((IFluidBlock)block).drain(source.getWorld(), pos, true);
                return stack;
            }
        }
        return null;
    }

}
