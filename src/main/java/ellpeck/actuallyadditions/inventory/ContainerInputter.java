package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityInputter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerInputter extends Container{

    private TileEntityInputter tileInputter;

    private int lastSideToPut;
    private int lastSlotToPut;
    private int lastSideToPull;
    private int lastSlotToPull;
    private int lastPlaceToPutSlotAmount;
    private int lastPlaceToPullSlotAmount;

    public ContainerInputter(InventoryPlayer inventory, TileEntityBase tile){
        this.tileInputter = (TileEntityInputter)tile;

        this.addSlotToContainer(new Slot(this.tileInputter, 0, 80, 21));

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
    public void addCraftingToCrafters(ICrafting iCraft){
        super.addCraftingToCrafters(iCraft);
        iCraft.sendProgressBarUpdate(this, 0, this.tileInputter.sideToPut);
        iCraft.sendProgressBarUpdate(this, 1, this.tileInputter.slotToPut);
        iCraft.sendProgressBarUpdate(this, 2, this.tileInputter.sideToPull);
        iCraft.sendProgressBarUpdate(this, 3, this.tileInputter.slotToPull);
        iCraft.sendProgressBarUpdate(this, 4, this.tileInputter.placeToPullSlotAmount);
        iCraft.sendProgressBarUpdate(this, 5, this.tileInputter.placeToPutSlotAmount);
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;
            if(this.lastSideToPut != this.tileInputter.sideToPut) iCraft.sendProgressBarUpdate(this, 0, this.tileInputter.sideToPut);
            if(this.lastSlotToPut != this.tileInputter.slotToPut) iCraft.sendProgressBarUpdate(this, 1, this.tileInputter.slotToPut);
            if(this.lastSideToPull != this.tileInputter.sideToPull) iCraft.sendProgressBarUpdate(this, 2, this.tileInputter.sideToPull);
            if(this.lastSlotToPull != this.tileInputter.slotToPull) iCraft.sendProgressBarUpdate(this, 3, this.tileInputter.slotToPull);
            if(this.lastPlaceToPullSlotAmount != this.tileInputter.placeToPullSlotAmount) iCraft.sendProgressBarUpdate(this, 4, this.tileInputter.placeToPullSlotAmount);
            if(this.lastPlaceToPutSlotAmount != this.tileInputter.placeToPutSlotAmount) iCraft.sendProgressBarUpdate(this, 5, this.tileInputter.placeToPutSlotAmount);
        }
        this.lastSideToPut = this.tileInputter.sideToPut;
        this.lastSlotToPut = this.tileInputter.slotToPut;
        this.lastSideToPull = this.tileInputter.sideToPull;
        this.lastSlotToPull = this.tileInputter.slotToPull;
        this.lastPlaceToPullSlotAmount = this.tileInputter.placeToPullSlotAmount;
        this.lastPlaceToPutSlotAmount = this.tileInputter.placeToPutSlotAmount;

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.tileInputter.sideToPut = par2;
        if(par1 == 1) this.tileInputter.slotToPut = par2;
        if(par1 == 2) this.tileInputter.sideToPull = par2;
        if(par1 == 3) this.tileInputter.slotToPull = par2;
        if(par1 == 4) this.tileInputter.placeToPullSlotAmount = par2;
        if(par1 == 5) this.tileInputter.placeToPutSlotAmount = par2;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.tileInputter.isUseableByPlayer(player);
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
                this.mergeItemStack(newStack, inventoryStart, inventoryEnd, false);
            }

            else if(slot <= inventoryEnd && slot >= inventoryStart){
                this.mergeItemStack(newStack, hotbarStart, hotbarEnd, false);
            }

            else if(slot < inventoryStart){
                    this.mergeItemStack(newStack, inventoryStart, hotbarEnd, false);
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