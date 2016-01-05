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

public class DispenserHandlerFertilize extends BehaviorDefaultDispenseItem{

    @Override
    public ItemStack dispenseStack(IBlockSource source, ItemStack stack){
        EnumFacing facing = BlockDispenser.func_149937_b(source.getBlockMetadata());
        int x = source.getXInt()+facing.getFrontOffsetX();
        int y = source.getYInt()+facing.getFrontOffsetY();
        int z = source.getZInt()+facing.getFrontOffsetZ();

        if(ItemDye.applyBonemeal(stack, source.getWorld(), x, y, z, null)){
            source.getWorld().playAuxSFX(2005, x, y, z, 0);
        }
        return stack;
    }

}
