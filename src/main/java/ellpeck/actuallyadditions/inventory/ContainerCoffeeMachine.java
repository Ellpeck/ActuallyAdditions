package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.slot.SlotOutput;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityCoffeeMachine;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@InventoryContainer
public class ContainerCoffeeMachine extends Container{

    private TileEntityCoffeeMachine machine;

    private int lastCoffeeAmount;
    private int lastEnergyAmount;
    private int lastBrewTime;

    public ContainerCoffeeMachine(InventoryPlayer inventory, TileEntityBase tile){
        this.machine = (TileEntityCoffeeMachine)tile;

        this.addSlotToContainer(new Slot(machine, TileEntityCoffeeMachine.SLOT_COFFEE_BEANS, 37, 6));
        this.addSlotToContainer(new Slot(machine, TileEntityCoffeeMachine.SLOT_INPUT, 80, 42));
        this.addSlotToContainer(new SlotOutput(machine, TileEntityCoffeeMachine.SLOT_OUTPUT, 80, 73));

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 2; j++){
                this.addSlotToContainer(new Slot(machine, j+i*2+3, 125+j*18, 6+i*18){
                    @Override
                    public int getSlotStackLimit(){
                        return 1;
                    }

                });
            }
        }

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
        iCraft.sendProgressBarUpdate(this, 0, this.machine.storage.getEnergyStored());
        iCraft.sendProgressBarUpdate(this, 1, this.machine.coffeeCacheAmount);
        iCraft.sendProgressBarUpdate(this, 2, this.machine.brewTime);

    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;

            if(this.lastEnergyAmount != this.machine.storage.getEnergyStored()) iCraft.sendProgressBarUpdate(this, 0, this.machine.storage.getEnergyStored());
            if(this.lastCoffeeAmount != this.machine.coffeeCacheAmount) iCraft.sendProgressBarUpdate(this, 1, this.machine.coffeeCacheAmount);
            if(this.lastBrewTime != this.machine.brewTime) iCraft.sendProgressBarUpdate(this, 2, this.machine.brewTime);
        }

        this.lastEnergyAmount = this.machine.storage.getEnergyStored();
        this.lastCoffeeAmount = this.machine.coffeeCacheAmount;
        this.lastBrewTime = this.machine.brewTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.machine.storage.setEnergyStored(par2);
        if(par1 == 1) this.machine.coffeeCacheAmount = par2;
        if(par1 == 2) this.machine.brewTime = par2;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.machine.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = 11;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);
        if(theSlot.getHasStack()){
            ItemStack currentStack = theSlot.getStack();
            ItemStack newStack = currentStack.copy();

            if(currentStack.getItem() != null){
                if(slot <= hotbarEnd && slot >= inventoryStart){
                    if(currentStack.getItem() == InitItems.itemCoffeeBean){
                        this.mergeItemStack(newStack, TileEntityCoffeeMachine.SLOT_COFFEE_BEANS, TileEntityCoffeeMachine.SLOT_COFFEE_BEANS+1, false);
                    }
                    if(currentStack.getItem() == InitItems.itemMisc && currentStack.getItemDamage() == TheMiscItems.CUP.ordinal()){
                        this.mergeItemStack(newStack, TileEntityCoffeeMachine.SLOT_INPUT, TileEntityCoffeeMachine.SLOT_INPUT+1, false);
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
        }
        return null;
    }
}