/*
 * This file ("DispenserHandlerFertilize.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;


import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class DispenserHandlerFertilize extends BehaviorDefaultDispenseItem{

    @Override
    public ItemStack dispenseStack(IBlockSource source, ItemStack stack){
        EnumFacing facing = BlockDispenser.getFacing(source.getBlockMetadata());
        int x = source.getBlockTileEntity().getPos().getX()+facing.getFrontOffsetX();
        int y = source.getBlockTileEntity().getPos().getY()+facing.getFrontOffsetY();
        int z = source.getBlockTileEntity().getPos().getZ()+facing.getFrontOffsetZ();
        BlockPos pos = new BlockPos(x, y, z);

        if(ItemDye.applyBonemeal(stack, source.getWorld(), pos, null)){
            source.getWorld().playAuxSFX(2005, pos, 0);
        }
        return stack;
    }

}
