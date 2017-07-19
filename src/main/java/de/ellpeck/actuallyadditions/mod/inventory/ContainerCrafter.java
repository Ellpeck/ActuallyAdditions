/*
 * This file ("ContainerCrafter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;


import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;

@InventoryContainer
public class ContainerCrafter extends ContainerWorkbench{
	
    public ContainerCrafter(EntityPlayer player){
    	super(player.inventory, player.world, player.getPosition());
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player){
        return true;
    }
}