/*
 * This file ("DispenserHandlerEmptyBucket.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.misc;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class DispenserHandlerEmptyBucket extends BehaviorDefaultDispenseItem{

    @Override
    public ItemStack dispenseStack(IBlockSource source, ItemStack bucket){
        EnumFacing facing = BlockDispenser.func_149937_b(source.getBlockMetadata());
        int x = source.getXInt()+facing.getFrontOffsetX();
        int y = source.getYInt()+facing.getFrontOffsetY();
        int z = source.getZInt()+facing.getFrontOffsetZ();

        if(source.getWorld().isAirBlock(x, y, z) && !source.getWorld().getBlock(x, y, z).getMaterial().isSolid() && ((ItemBucket)bucket.getItem()).tryPlaceContainedLiquid(source.getWorld(), x, y, z)){
            return new ItemStack(Items.bucket);
        }

        return new BehaviorDefaultDispenseItem().dispense(source, bucket);
    }

}
