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
        return null;
    }
}