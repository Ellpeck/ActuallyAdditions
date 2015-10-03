/*
 * This file ("ContainerSmileyCloud.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerSmileyCloud extends Container{

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return true;
    }
}