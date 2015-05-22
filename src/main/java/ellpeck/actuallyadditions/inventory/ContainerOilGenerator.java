package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityOilGenerator;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

@InventoryContainer
public class ContainerOilGenerator extends Container{

    private TileEntityOilGenerator generator;

    private int lastEnergyStored;
    private int lastBurnTime;
    private int lastTankAmount;

    public ContainerOilGenerator(InventoryPlayer inventory, TileEntityBase tile){
        this.generator = (TileEntityOilGenerator)tile;

        this.addSlotToContainer(new Slot(this.generator, 0, 98, 74));
        this.addSlotToContainer(new Slot(this.generator, 1, 98, 43));

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
        return this.generator.isUseableByPlayer(player);
    }

    @Override
    public void addCraftingToCrafters(ICrafting iCraft){
        super.addCraftingToCrafters(iCraft);
        iCraft.sendProgressBarUpdate(this, 0, this.generator.getEnergyStored(ForgeDirection.UNKNOWN));
        iCraft.sendProgressBarUpdate(this, 1, this.generator.currentBurnTime);
        iCraft.sendProgressBarUpdate(this, 2, this.generator.tank.getFluidAmount());
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;

            if(this.lastEnergyStored != this.generator.getEnergyStored(ForgeDirection.UNKNOWN)) iCraft.sendProgressBarUpdate(this, 0, this.generator.getEnergyStored(ForgeDirection.UNKNOWN));
            if(this.lastBurnTime != this.generator.currentBurnTime) iCraft.sendProgressBarUpdate(this, 1, this.generator.currentBurnTime);
            if(this.lastTankAmount != this.generator.tank.getFluidAmount()) iCraft.sendProgressBarUpdate(this, 2, this.generator.tank.getFluidAmount());
        }

        this.lastEnergyStored = this.generator.getEnergyStored(ForgeDirection.UNKNOWN);
        this.lastBurnTime = this.generator.currentBurnTime;
        this.lastTankAmount = this.generator.tank.getFluidAmount();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.generator.storage.setEnergyStored(par2);
        if(par1 == 1) this.generator.currentBurnTime = par2;
        if(par1 == 2) this.generator.tank.setFluid(new FluidStack(InitBlocks.fluidOil, par2));
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

            if(currentStack.getItem() != null){
                if(slot <= hotbarEnd && slot >= inventoryStart){
                    if(currentStack.getItem() == InitItems.itemBucketOil){
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
        }
        return null;
    }
}