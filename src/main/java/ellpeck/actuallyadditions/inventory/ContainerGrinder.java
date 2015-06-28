package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.slot.SlotOutput;
import ellpeck.actuallyadditions.recipe.GrinderRecipeManualRegistry;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityGrinder;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

@InventoryContainer
public class ContainerGrinder extends Container{

    private TileEntityGrinder tileGrinder;
    private boolean isDouble;

    private int lastFirstCrushTime;
    private int lastSecondCrushTime;
    private int lastMaxCrushTime;
    private int lastEnergyStored;

    public ContainerGrinder(InventoryPlayer inventory, TileEntityBase tile, boolean isDouble){
        this.tileGrinder = (TileEntityGrinder)tile;
        this.isDouble = isDouble;

        this.addSlotToContainer(new Slot(this.tileGrinder, TileEntityGrinder.SLOT_INPUT_1, this.isDouble ? 51 : 80, 21));
        this.addSlotToContainer(new SlotOutput(this.tileGrinder, TileEntityGrinder.SLOT_OUTPUT_1_1, this.isDouble ? 37 : 66, 69));
        this.addSlotToContainer(new SlotOutput(this.tileGrinder, TileEntityGrinder.SLOT_OUTPUT_1_2, this.isDouble ? 64 : 92, 69));
        if(this.isDouble){
            this.addSlotToContainer(new Slot(this.tileGrinder, TileEntityGrinder.SLOT_INPUT_2, 109, 21));
            this.addSlotToContainer(new SlotOutput(this.tileGrinder, TileEntityGrinder.SLOT_OUTPUT_2_1, 96, 69));
            this.addSlotToContainer(new SlotOutput(this.tileGrinder, TileEntityGrinder.SLOT_OUTPUT_2_2, 121, 69));
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
        iCraft.sendProgressBarUpdate(this, 0, this.tileGrinder.firstCrushTime);
        iCraft.sendProgressBarUpdate(this, 1, this.tileGrinder.maxCrushTime);
        if(this.isDouble) iCraft.sendProgressBarUpdate(this, 2, this.tileGrinder.secondCrushTime);
        iCraft.sendProgressBarUpdate(this, 3, this.tileGrinder.getEnergyStored(ForgeDirection.UNKNOWN));
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;

            if(this.lastFirstCrushTime != this.tileGrinder.firstCrushTime) iCraft.sendProgressBarUpdate(this, 0, this.tileGrinder.firstCrushTime);
            if(this.lastMaxCrushTime != this.tileGrinder.maxCrushTime) iCraft.sendProgressBarUpdate(this, 1, this.tileGrinder.maxCrushTime);
            if(this.isDouble) if(this.lastSecondCrushTime != this.tileGrinder.secondCrushTime) iCraft.sendProgressBarUpdate(this, 2, this.tileGrinder.secondCrushTime);
            if(this.lastEnergyStored != this.tileGrinder.getEnergyStored(ForgeDirection.UNKNOWN)) iCraft.sendProgressBarUpdate(this, 3, this.tileGrinder.getEnergyStored(ForgeDirection.UNKNOWN));
        }

        this.lastFirstCrushTime = this.tileGrinder.firstCrushTime;
        this.lastMaxCrushTime = this.tileGrinder.maxCrushTime;
        if(this.isDouble) this.lastSecondCrushTime = this.tileGrinder.secondCrushTime;
        this.lastEnergyStored = this.tileGrinder.getEnergyStored(ForgeDirection.UNKNOWN);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.tileGrinder.firstCrushTime = par2;
        if(par1 == 1) this.tileGrinder.maxCrushTime = par2;
        if(this.isDouble && par1 == 2) this.tileGrinder.secondCrushTime = par2;
        if(par1 == 3) this.tileGrinder.storage.setEnergyStored(par2);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.tileGrinder.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = this.isDouble ? 6 : 3;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);
        if(theSlot.getHasStack()){
            ItemStack currentStack = theSlot.getStack();
            ItemStack newStack = currentStack.copy();

            if(currentStack.getItem() != null){
                if(slot <= hotbarEnd && slot >= inventoryStart){
                    if(GrinderRecipeManualRegistry.getOutput(currentStack, false) != null){
                        this.mergeItemStack(newStack, TileEntityGrinder.SLOT_INPUT_1, TileEntityGrinder.SLOT_INPUT_1+1, false);
                        if(this.isDouble) this.mergeItemStack(newStack, TileEntityGrinder.SLOT_INPUT_2, TileEntityGrinder.SLOT_INPUT_2+1, false);
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