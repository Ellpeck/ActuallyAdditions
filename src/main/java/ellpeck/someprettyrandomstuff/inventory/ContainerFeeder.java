package ellpeck.someprettyrandomstuff.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.tile.TileEntityBase;
import ellpeck.someprettyrandomstuff.tile.TileEntityFeeder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFeeder extends Container{

    private TileEntityFeeder tileFeeder;

    private int lastCurrentTimer;
    private int lastCurrentAnimalAmount;
    private int lastIsBred;

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

    public void addCraftingToCrafters(ICrafting iCraft){
        super.addCraftingToCrafters(iCraft);
        iCraft.sendProgressBarUpdate(this, 0, this.tileFeeder.currentTimer);
        iCraft.sendProgressBarUpdate(this, 1, this.tileFeeder.currentAnimalAmount);
        iCraft.sendProgressBarUpdate(this, 2, this.tileFeeder.isBred);
    }

    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;

            if(this.lastCurrentTimer != this.tileFeeder.currentTimer) iCraft.sendProgressBarUpdate(this, 0, this.tileFeeder.currentTimer);
            if(this.lastCurrentAnimalAmount != this.tileFeeder.currentAnimalAmount) iCraft.sendProgressBarUpdate(this, 1, this.tileFeeder.currentAnimalAmount);
            if(this.lastIsBred != this.tileFeeder.isBred) iCraft.sendProgressBarUpdate(this, 2, this.tileFeeder.isBred);
        }

        this.lastCurrentTimer = this.tileFeeder.currentTimer;
        this.lastCurrentAnimalAmount = this.tileFeeder.currentAnimalAmount;
        this.lastIsBred = this.tileFeeder.isBred;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.tileFeeder.currentTimer = par2;
        if(par1 == 1) this.tileFeeder.currentAnimalAmount = par2;
        if(par1 == 2) this.tileFeeder.isBred = par2;
    }

    public boolean canInteractWith(EntityPlayer player){
        return this.tileFeeder.isUseableByPlayer(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        /*ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (p_82846_2_ != 1 && p_82846_2_ != 0)
            {
                if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (TileEntityFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (p_82846_2_ >= 3 && p_82846_2_ < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (p_82846_2_ >= 30 && p_82846_2_ < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p_82846_1_, itemstack1);
        }

        return itemstack;*/
        return null;
    }
}