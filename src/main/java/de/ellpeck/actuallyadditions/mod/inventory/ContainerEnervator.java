/*
 * This file ("ContainerEnervator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotOutput;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnervator;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ContainerEnervator extends Container{

    private final TileEntityEnervator enervator;

    public ContainerEnervator(final EntityPlayer player, TileEntityBase tile){
        this.enervator = (TileEntityEnervator)tile;
        InventoryPlayer inventory = player.inventory;

        this.addSlotToContainer(new SlotItemHandlerUnconditioned(this.enervator.slots, 0, 76, 73));
        this.addSlotToContainer(new SlotOutput(this.enervator.slots, 1, 76, 42));

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 97+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 155));
        }

        for(int k = 0; k < 4; ++k){
            final EntityEquipmentSlot slot = ContainerEnergizer.VALID_EQUIPMENT_SLOTS[k];
            this.addSlotToContainer(new Slot(player.inventory, 36+(3-k), 102, 19+k*18){
                @Override
                public int getSlotStackLimit(){
                    return 1;
                }

                @Override
                public boolean isItemValid(ItemStack stack){
                    return StackUtil.isValid(stack) && stack.getItem().isValidArmor(stack, slot, player);
                }

                @Override
                @SideOnly(Side.CLIENT)
                public String getSlotTexture(){
                    return ItemArmor.EMPTY_SLOT_NAMES[slot.getIndex()];
                }
            });
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        int inventoryStart = 2;
        int inventoryEnd = inventoryStart+26;
        int hotbarStart = inventoryEnd+1;
        int hotbarEnd = hotbarStart+8;

        Slot theSlot = this.inventorySlots.get(slot);

        if(theSlot != null && theSlot.getHasStack()){
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if(slot == 1){
                if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, true)){
                    return StackUtil.getNull();
                }
                theSlot.onSlotChange(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if(slot >= inventoryStart){
                //Shift from Inventory
                if((ActuallyAdditions.teslaLoaded && newStack.hasCapability(TeslaUtil.teslaProducer, null)) || newStack.hasCapability(CapabilityEnergy.ENERGY, null)){
                    if(!this.mergeItemStack(newStack, 0, 1, false)){
                        return StackUtil.getNull();
                    }
                }
                //

                else if(slot >= inventoryStart && slot <= inventoryEnd){
                    if(!this.mergeItemStack(newStack, hotbarStart, hotbarEnd+1, false)){
                        return StackUtil.getNull();
                    }
                }
                else if(slot >= inventoryEnd+1 && slot < hotbarEnd+1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd+1, false)){
                    return StackUtil.getNull();
                }
            }
            else if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, false)){
                return StackUtil.getNull();
            }

            if(!StackUtil.isValid(newStack)){
                theSlot.putStack(StackUtil.getNull());
            }
            else{
                theSlot.onSlotChanged();
            }

            if(StackUtil.getStackSize(newStack) == StackUtil.getStackSize(currentStack)){
                return StackUtil.getNull();
            }
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        return StackUtil.getNull();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.enervator.canPlayerUse(player);
    }
}