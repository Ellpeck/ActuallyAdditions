package ellpeck.gemification.inventory.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.gemification.tile.TileEntityCrucibleFire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerCrucibleFire extends Container {

    private TileEntityCrucibleFire tileCrucibleFire;

    private int lastBurnTime;
    private int lastBurnTimeOfItem;

    public ContainerCrucibleFire(InventoryPlayer inventoryPlayer, TileEntityCrucibleFire tileCrucibleFire) {
        this.tileCrucibleFire = tileCrucibleFire;

        this.addSlotToContainer(new Slot(this.tileCrucibleFire, 0, 70, 9));

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 34 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i){
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 92));
        }
    }

    public boolean canInteractWith(EntityPlayer player) {
        return tileCrucibleFire.isUseableByPlayer(player);
    }

    public void addCraftingToCrafters(ICrafting iCraft){
        super.addCraftingToCrafters(iCraft);

        iCraft.sendProgressBarUpdate(this, 0, this.tileCrucibleFire.burnTime);
        iCraft.sendProgressBarUpdate(this, 1, this.tileCrucibleFire.burnTimeOfItem);
    }

    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (Object crafter : this.crafters) {
            ICrafting iCraft = (ICrafting) crafter;

            if (this.lastBurnTime != this.tileCrucibleFire.burnTime) iCraft.sendProgressBarUpdate(this, 0, this.tileCrucibleFire.burnTime);
            if (this.lastBurnTimeOfItem != this.tileCrucibleFire.burnTimeOfItem) iCraft.sendProgressBarUpdate(this, 1, this.tileCrucibleFire.burnTimeOfItem);
        }

        this.lastBurnTime = tileCrucibleFire.burnTime;
        this.lastBurnTimeOfItem = tileCrucibleFire.burnTimeOfItem;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if (par1 == 0) this.tileCrucibleFire.burnTime = par2;
        if (par1 == 1) this.tileCrucibleFire.burnTimeOfItem = par2;
    }
}
