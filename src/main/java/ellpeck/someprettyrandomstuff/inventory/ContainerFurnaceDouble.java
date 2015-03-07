package ellpeck.someprettyrandomstuff.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.inventory.slot.SlotOutput;
import ellpeck.someprettyrandomstuff.tile.TileEntityBase;
import ellpeck.someprettyrandomstuff.tile.TileEntityFurnaceDouble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFurnaceDouble extends Container{

    private TileEntityFurnaceDouble tileFurnace;

    private int lastCoalTime;
    private int lastCoalTimeLeft;
    private int lastFirstCrushTime;
    private int lastSecondCrushTime;

    public ContainerFurnaceDouble(InventoryPlayer inventory, TileEntityBase tile){
        this.tileFurnace = (TileEntityFurnaceDouble)tile;

        this.addSlotToContainer(new Slot(this.tileFurnace, TileEntityFurnaceDouble.SLOT_COAL, 80, 21));

        this.addSlotToContainer(new Slot(this.tileFurnace, TileEntityFurnaceDouble.SLOT_INPUT_1, 51, 21));
        this.addSlotToContainer(new Slot(this.tileFurnace, TileEntityFurnaceDouble.SLOT_INPUT_2, 109, 21));
        this.addSlotToContainer(new SlotOutput(this.tileFurnace, TileEntityFurnaceDouble.SLOT_OUTPUT_1, 51, 69));
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
        iCraft.sendProgressBarUpdate(this, 0, this.tileFurnace.coalTime);
        iCraft.sendProgressBarUpdate(this, 1, this.tileFurnace.coalTimeLeft);
        iCraft.sendProgressBarUpdate(this, 2, this.tileFurnace.firstSmeltTime);
        iCraft.sendProgressBarUpdate(this, 3, this.tileFurnace.secondSmeltTime);
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;

            if(this.lastCoalTime != this.tileFurnace.coalTime) iCraft.sendProgressBarUpdate(this, 0, this.tileFurnace.coalTime);
            if(this.lastCoalTimeLeft != this.tileFurnace.coalTimeLeft) iCraft.sendProgressBarUpdate(this, 1, this.tileFurnace.coalTimeLeft);
            if(this.lastFirstCrushTime != this.tileFurnace.firstSmeltTime) iCraft.sendProgressBarUpdate(this, 2, this.tileFurnace.firstSmeltTime);
            if(this.lastSecondCrushTime != this.tileFurnace.secondSmeltTime) iCraft.sendProgressBarUpdate(this, 3, this.tileFurnace.secondSmeltTime);
        }

        this.lastCoalTime = this.tileFurnace.coalTime;
        this.lastCoalTimeLeft = this.tileFurnace.coalTimeLeft;
        this.lastFirstCrushTime = this.tileFurnace.firstSmeltTime;
        this.lastSecondCrushTime = this.tileFurnace.secondSmeltTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.tileFurnace.coalTime = par2;
        if(par1 == 1) this.tileFurnace.coalTimeLeft = par2;
        if(par1 == 2) this.tileFurnace.firstSmeltTime = par2;
        if(par1 == 3) this.tileFurnace.secondSmeltTime = par2;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.tileFurnace.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        return null;
    }
}