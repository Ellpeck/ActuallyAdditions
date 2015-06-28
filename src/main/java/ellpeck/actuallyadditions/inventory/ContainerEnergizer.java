package ellpeck.actuallyadditions.inventory;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.slot.SlotOutput;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityEnergizer;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

@InventoryContainer
public class ContainerEnergizer extends Container{

    private TileEntityEnergizer energizer;

    private int lastEnergyStored;

    public ContainerEnergizer(InventoryPlayer inventory, TileEntityBase tile){
        this.energizer = (TileEntityEnergizer)tile;

        this.addSlotToContainer(new Slot(this.energizer, 0, 76, 73));
        this.addSlotToContainer(new SlotOutput(this.energizer, 1, 76, 42));

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 155));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.energizer.isUseableByPlayer(player);
    }

    @Override
    public void addCraftingToCrafters(ICrafting iCraft){
        super.addCraftingToCrafters(iCraft);
        iCraft.sendProgressBarUpdate(this, 0, this.energizer.getEnergyStored(ForgeDirection.UNKNOWN));
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;

            if(this.lastEnergyStored != this.energizer.getEnergyStored(ForgeDirection.UNKNOWN)) iCraft.sendProgressBarUpdate(this, 0, this.energizer.getEnergyStored(ForgeDirection.UNKNOWN));
        }

        this.lastEnergyStored = this.energizer.getEnergyStored(ForgeDirection.UNKNOWN);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.energizer.storage.setEnergyStored(par2);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = 2;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);
        if(theSlot.getHasStack()){
            ItemStack currentStack = theSlot.getStack();
            ItemStack newStack = currentStack.copy();

            if(slot <= hotbarEnd && slot >= inventoryStart){
                if(currentStack.getItem() instanceof IEnergyContainerItem){
                    this.mergeItemStack(newStack, 0, 1, false);
                }
            }

            if(slot <= hotbarEnd && slot >= hotbarStart){
                this.mergeItemStack(newStack, inventoryStart, inventoryEnd+1, false);
            }
            else if(slot <= inventoryEnd && slot >= inventoryStart){
                this.mergeItemStack(newStack, hotbarStart, hotbarEnd+1, false);
            }
            else if(slot < inventoryStart){
                this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, false);
            }

            if(newStack.stackSize == 0) theSlot.putStack(null);
            else theSlot.onSlotChanged();
            if(newStack.stackSize == currentStack.stackSize) return null;
            theSlot.onPickupFromSlot(player, newStack);

            return currentStack;
        }
        return null;
    }
}