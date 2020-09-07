package de.ellpeck.actuallyadditions.mod.misc;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class DispenserHandlerFertilize extends BehaviorDefaultDispenseItem {

    @Override
    public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        EnumFacing facing = source.getBlockState().getValue(BlockDispenser.FACING);
        BlockPos pos = source.getBlockPos().offset(facing);

        if (ItemDye.applyBonemeal(stack, source.getWorld(), pos)) {
            source.getWorld().playEvent(2005, pos, 0);
        }

        return stack;
    }

}
