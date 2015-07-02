package ellpeck.actuallyadditions.inventory;

import ellpeck.actuallyadditions.inventory.gui.GuiInputter;
import ellpeck.actuallyadditions.inventory.slot.SlotFilter;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityInputter;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@InventoryContainer
public class ContainerInputter extends Container{

    private TileEntityInputter tileInputter;

    private boolean isAdvanced;

    public ContainerInputter(InventoryPlayer inventory, TileEntityBase tile, boolean isAdvanced){
        this.tileInputter = (TileEntityInputter)tile;
        this.isAdvanced = isAdvanced;

        this.addSlotToContainer(new Slot(this.tileInputter, 0, 80, 21 + (isAdvanced ? 12 : 0)));

        if(isAdvanced){
            for(int i = 0; i < 2; i++){
                for(int x = 0; x < 3; x++){
                    for(int y = 0;y < 4; y++){
                        this.addSlotToContainer(new SlotFilter(this.tileInputter, 1+y+x*4+i*12, 20+i*84+x*18, 6+y*18));
                    }
                }
            }
        }

        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18 + (isAdvanced ? GuiInputter.OFFSET_ADVANCED : 0)));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 155 + (isAdvanced ? GuiInputter.OFFSET_ADVANCED : 0)));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.tileInputter.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventory = this.isAdvanced ? 25 : 1;
        final int inventoryEnd = inventory+26;
        final int hotbar = inventoryEnd+1;
        final int hotbarEnd = hotbar+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);
        if(theSlot.getHasStack()){
            ItemStack currentStack = theSlot.getStack();
            ItemStack newStack = currentStack.copy();

            if(currentStack.getItem() != null){
                if(slot <= hotbarEnd && slot >= inventory){
                    this.mergeItemStack(newStack, 0, 1, false);
                }

                if(slot <= hotbarEnd && slot >= hotbar){
                    this.mergeItemStack(newStack, inventory, inventoryEnd, false);
                }

                else if(slot <= inventoryEnd && slot >= inventory){
                    this.mergeItemStack(newStack, hotbar, hotbarEnd, false);
                }

                else if(slot < inventory){
                    this.mergeItemStack(newStack, inventory, hotbarEnd, false);
                }

                if(newStack.stackSize == 0) theSlot.putStack(null);
                else theSlot.onSlotChanged();
                if(newStack.stackSize == currentStack.stackSize) return null;
                theSlot.onPickupFromSlot(player, newStack);

                return currentStack;
            }
        }
        return null;
    }
}