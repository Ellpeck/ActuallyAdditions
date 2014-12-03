package ellpeck.thingycraft.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.thingycraft.tile.TileEntityCrucible;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;

public class ContainerCrucible extends Container {

    private TileEntityCrucible tileCrucible;

    private int lastCurrentFluidID;
    private int lastProcessTime;
    private int lastProcessTimeNeeded;

    public ContainerCrucible(InventoryPlayer inventoryPlayer, TileEntityCrucible tileCrucible) {
        this.tileCrucible = tileCrucible;

        this.addSlotToContainer(new Slot(this.tileCrucible, tileCrucible.slotMainInput-4, 32, 23));
        this.addSlotToContainer(new Slot(this.tileCrucible, tileCrucible.slotMainInput-3, 57, 18));
        this.addSlotToContainer(new Slot(this.tileCrucible, tileCrucible.slotMainInput-2, 82, 23));
        this.addSlotToContainer(new Slot(this.tileCrucible, tileCrucible.slotMainInput-1, 27, 48));
        this.addSlotToContainer(new Slot(this.tileCrucible, tileCrucible.slotMainInput, 57, 48));
        this.addSlotToContainer(new Slot(this.tileCrucible, tileCrucible.slotMainInput+1, 87, 48));
        this.addSlotToContainer(new Slot(this.tileCrucible, tileCrucible.slotMainInput+2, 32, 73));
        this.addSlotToContainer(new Slot(this.tileCrucible, tileCrucible.slotMainInput+3, 57, 78));
        this.addSlotToContainer(new Slot(this.tileCrucible, tileCrucible.slotMainInput+4, 82, 73));

        this.addSlotToContainer(new Slot(tileCrucible, tileCrucible.slotSmeltGem, 129, 37));
        this.addSlotToContainer(new Slot(tileCrucible, tileCrucible.slotWater, 149, 37));

        this.addSlotToContainer(new SlotFurnace(inventoryPlayer.player, this.tileCrucible, tileCrucible.slotOutput, 146, 85));

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 113 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i){
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 171));
        }
    }

    public boolean canInteractWith(EntityPlayer player) {
        return tileCrucible.isUseableByPlayer(player);
    }

    public void addCraftingToCrafters(ICrafting iCraft){
        super.addCraftingToCrafters(iCraft);

        iCraft.sendProgressBarUpdate(this, 0, this.tileCrucible.currentFluidID);
        iCraft.sendProgressBarUpdate(this, 1, this.tileCrucible.currentProcessTime);
        iCraft.sendProgressBarUpdate(this, 2, this.tileCrucible.processTimeNeeded);
    }

    @SuppressWarnings("all")
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (Object crafter : this.crafters) {
            ICrafting iCraft = (ICrafting) crafter;

            if (this.lastCurrentFluidID != this.tileCrucible.currentFluidID) iCraft.sendProgressBarUpdate(this, 0, this.tileCrucible.currentFluidID);
            if (this.lastProcessTime != this.tileCrucible.currentProcessTime) iCraft.sendProgressBarUpdate(this, 1, this.tileCrucible.currentProcessTime);
            if (this.lastProcessTimeNeeded != this.tileCrucible.processTimeNeeded) iCraft.sendProgressBarUpdate(this, 2, this.tileCrucible.processTimeNeeded);
        }

        this.lastCurrentFluidID = this.tileCrucible.currentFluidID;
        this.lastProcessTime = this.tileCrucible.currentProcessTime;
        this.lastProcessTimeNeeded = this.tileCrucible.processTimeNeeded;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if (par1 == 0) this.tileCrucible.currentFluidID = par2;
        if (par1 == 1) this.tileCrucible.currentProcessTime = par2;
        if (par1 == 2) this.tileCrucible.processTimeNeeded = par2;

    }
}
