package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityFeeder;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@InventoryContainer
public class ContainerFeeder extends Container{

    private TileEntityFeeder tileFeeder;

    private int lastCurrentTimer;
    private int lastCurrentAnimalAmount;

    public ContainerFeeder(InventoryPlayer inventory, TileEntityBase tile){
        this.tileFeeder = (TileEntityFeeder)tile;
        this.addSlotToContainer(new Slot(this.tileFeeder, 0, 80, 45));

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 74 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 132));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting iCraft){
        super.addCraftingToCrafters(iCraft);
        iCraft.sendProgressBarUpdate(this, 0, this.tileFeeder.currentTimer);
        iCraft.sendProgressBarUpdate(this, 1, this.tileFeeder.currentAnimalAmount);
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;

            if(this.lastCurrentTimer != this.tileFeeder.currentTimer) iCraft.sendProgressBarUpdate(this, 0, this.tileFeeder.currentTimer);
            if(this.lastCurrentAnimalAmount != this.tileFeeder.currentAnimalAmount) iCraft.sendProgressBarUpdate(this, 1, this.tileFeeder.currentAnimalAmount);
        }

        this.lastCurrentTimer = this.tileFeeder.currentTimer;
        this.lastCurrentAnimalAmount = this.tileFeeder.currentAnimalAmount;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.tileFeeder.currentTimer = par2;
        if(par1 == 1) this.tileFeeder.currentAnimalAmount = par2;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.tileFeeder.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = 1;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);
        if(theSlot.getHasStack()){
            ItemStack currentStack = theSlot.getStack();
            ItemStack newStack = currentStack.copy();

            if(slot <= hotbarEnd && slot >= inventoryStart){
                this.mergeItemStack(newStack, 0, 1, false);
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