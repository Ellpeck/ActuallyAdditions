/*
 * This file ("ContainerEnergizer.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import cofh.api.energy.IEnergyContainerItem;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotOutput;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnergizer;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@InventoryContainer
public class ContainerEnergizer extends Container{

    private TileEntityEnergizer energizer;

    public ContainerEnergizer(EntityPlayer player, TileEntityBase tile){
        this.energizer = (TileEntityEnergizer)tile;
        InventoryPlayer inventory = player.inventory;

        this.addSlotToContainer(new Slot(this.energizer, 0, 76, 73));
        this.addSlotToContainer(new SlotOutput(this.energizer, 1, 76, 42));

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 97+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 155));
        }
        final EntityPlayer finalPlayer = player;
        for(int i = 0; i < 4; ++i){
            final int finalI = i;
            this.addSlotToContainer(new Slot(inventory, inventory.getSizeInventory()-1-i, 102, 19+i*18){
                @Override
                public boolean isItemValid(ItemStack stack){
                    return stack != null && stack.getItem().isValidArmor(stack, ContainerEnervator.ARMOR_SLOTS[finalI], finalPlayer);
                }

                @Override
                public int getSlotStackLimit(){
                    return 1;
                }

                @Override
                @SideOnly(Side.CLIENT)
                public String getSlotTexture(){
                    return ItemArmor.EMPTY_SLOT_NAMES[finalI];
                }
            });
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = 2;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);

        if(theSlot != null && theSlot.getHasStack()){
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if(slot == 1){
                if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, true)){
                    return null;
                }
                theSlot.onSlotChange(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if(slot >= inventoryStart){
                //Shift from Inventory
                if(newStack.getItem() instanceof IEnergyContainerItem){
                    if(!this.mergeItemStack(newStack, 0, 1, false)){
                        return null;
                    }
                }
                //

                else if(slot >= inventoryStart && slot <= inventoryEnd){
                    if(!this.mergeItemStack(newStack, hotbarStart, hotbarEnd+1, false)){
                        return null;
                    }
                }
                else if(slot >= inventoryEnd+1 && slot < hotbarEnd+1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd+1, false)){
                    return null;
                }
            }
            else if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, false)){
                return null;
            }

            if(newStack.stackSize == 0){
                theSlot.putStack(null);
            }
            else{
                theSlot.onSlotChanged();
            }

            if(newStack.stackSize == currentStack.stackSize){
                return null;
            }
            theSlot.onPickupFromSlot(player, newStack);

            return currentStack;
        }
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.energizer.isUseableByPlayer(player);
    }
}