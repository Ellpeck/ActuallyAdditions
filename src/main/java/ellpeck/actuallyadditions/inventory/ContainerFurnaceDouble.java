package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.slot.SlotOutput;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityFurnaceDouble;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.util.ForgeDirection;

@InventoryContainer
public class ContainerFurnaceDouble extends Container{

    private TileEntityFurnaceDouble tileFurnace;

    private int lastEnergy;
    private int lastFirstCrushTime;
    private int lastSecondCrushTime;
    private int lastBurnTime;

    public ContainerFurnaceDouble(InventoryPlayer inventory, TileEntityBase tile){
        this.tileFurnace = (TileEntityFurnaceDouble)tile;

        this.addSlotToContainer(new Slot(this.tileFurnace, TileEntityFurnaceDouble.SLOT_INPUT_1, 51, 21));
        this.addSlotToContainer(new SlotOutput(this.tileFurnace, TileEntityFurnaceDouble.SLOT_OUTPUT_1, 51, 69));
        this.addSlotToContainer(new Slot(this.tileFurnace, TileEntityFurnaceDouble.SLOT_INPUT_2, 109, 21));
        this.addSlotToContainer(new SlotOutput(this.tileFurnace, TileEntityFurnaceDouble.SLOT_OUTPUT_2, 108, 69));

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
        iCraft.sendProgressBarUpdate(this, 0, this.tileFurnace.firstSmeltTime);
        iCraft.sendProgressBarUpdate(this, 1, this.tileFurnace.secondSmeltTime);
        iCraft.sendProgressBarUpdate(this, 2, this.tileFurnace.maxBurnTime);
        iCraft.sendProgressBarUpdate(this, 3, this.tileFurnace.getEnergyStored(ForgeDirection.UNKNOWN));
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;

            if(this.lastFirstCrushTime != this.tileFurnace.firstSmeltTime) iCraft.sendProgressBarUpdate(this, 0, this.tileFurnace.firstSmeltTime);
            if(this.lastSecondCrushTime != this.tileFurnace.secondSmeltTime) iCraft.sendProgressBarUpdate(this, 1, this.tileFurnace.secondSmeltTime);
            if(this.lastBurnTime != this.tileFurnace.maxBurnTime) iCraft.sendProgressBarUpdate(this, 2, this.tileFurnace.maxBurnTime);
            if(this.lastEnergy != this.tileFurnace.getEnergyStored(ForgeDirection.UNKNOWN)) iCraft.sendProgressBarUpdate(this, 3, this.tileFurnace.getEnergyStored(ForgeDirection.UNKNOWN));
        }

        this.lastFirstCrushTime = this.tileFurnace.firstSmeltTime;
        this.lastSecondCrushTime = this.tileFurnace.secondSmeltTime;
        this.lastBurnTime = this.tileFurnace.maxBurnTime;
        this.lastEnergy = this.tileFurnace.getEnergyStored(ForgeDirection.UNKNOWN);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.tileFurnace.firstSmeltTime = par2;
        if(par1 == 1) this.tileFurnace.secondSmeltTime = par2;
        if(par1 == 2) this.tileFurnace.maxBurnTime = par2;
        if(par1 == 3) this.tileFurnace.storage.setEnergyStored(par2);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.tileFurnace.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = 4;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);
        if(theSlot.getHasStack()){
            ItemStack currentStack = theSlot.getStack();
            ItemStack newStack = currentStack.copy();

            if(currentStack.getItem() != null){
                if(slot <= hotbarEnd && slot >= inventoryStart){
                    if(FurnaceRecipes.smelting().getSmeltingResult(currentStack) != null){
                        this.mergeItemStack(newStack, TileEntityFurnaceDouble.SLOT_INPUT_1, TileEntityFurnaceDouble.SLOT_INPUT_1+1, false);
                        this.mergeItemStack(newStack, TileEntityFurnaceDouble.SLOT_INPUT_2, TileEntityFurnaceDouble.SLOT_INPUT_2+2, false);
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