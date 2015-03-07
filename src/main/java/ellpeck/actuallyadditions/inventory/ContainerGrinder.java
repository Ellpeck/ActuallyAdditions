package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.slot.SlotOutput;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityGrinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGrinder extends Container{

    private TileEntityGrinder tileGrinder;
    private boolean isDouble;

    private int lastCoalTime;
    private int lastCoalTimeLeft;
    private int lastFirstCrushTime;
    private int lastSecondCrushTime;

    public ContainerGrinder(InventoryPlayer inventory, TileEntityBase tile, boolean isDouble){
        this.tileGrinder = (TileEntityGrinder)tile;
        this.isDouble = isDouble;

        this.addSlotToContainer(new Slot(this.tileGrinder, TileEntityGrinder.SLOT_COAL, this.isDouble ? 80 : 51, 21));

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
        iCraft.sendProgressBarUpdate(this, 0, this.tileGrinder.coalTime);
        iCraft.sendProgressBarUpdate(this, 1, this.tileGrinder.coalTimeLeft);
        iCraft.sendProgressBarUpdate(this, 2, this.tileGrinder.firstCrushTime);
        if(this.isDouble) iCraft.sendProgressBarUpdate(this, 3, this.tileGrinder.secondCrushTime);
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;

            if(this.lastCoalTime != this.tileGrinder.coalTime) iCraft.sendProgressBarUpdate(this, 0, this.tileGrinder.coalTime);
            if(this.lastCoalTimeLeft != this.tileGrinder.coalTimeLeft) iCraft.sendProgressBarUpdate(this, 1, this.tileGrinder.coalTimeLeft);
            if(this.lastFirstCrushTime != this.tileGrinder.firstCrushTime) iCraft.sendProgressBarUpdate(this, 2, this.tileGrinder.firstCrushTime);
            if(this.isDouble) if(this.lastSecondCrushTime != this.tileGrinder.secondCrushTime) iCraft.sendProgressBarUpdate(this, 3, this.tileGrinder.secondCrushTime);
        }

        this.lastCoalTime = this.tileGrinder.coalTime;
        this.lastCoalTimeLeft = this.tileGrinder.coalTimeLeft;
        this.lastFirstCrushTime = this.tileGrinder.firstCrushTime;
        if(this.isDouble) this.lastSecondCrushTime = this.tileGrinder.secondCrushTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.tileGrinder.coalTime = par2;
        if(par1 == 1) this.tileGrinder.coalTimeLeft = par2;
        if(par1 == 2) this.tileGrinder.firstCrushTime = par2;
        if(this.isDouble && par1 == 3) this.tileGrinder.secondCrushTime = par2;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.tileGrinder.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        return null;
    }
}