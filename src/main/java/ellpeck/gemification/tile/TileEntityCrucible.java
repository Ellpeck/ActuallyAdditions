package ellpeck.gemification.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.gemification.blocks.InitBlocks;
import ellpeck.gemification.crafting.CrucibleCraftingManager;
import ellpeck.gemification.items.ItemGem;
import ellpeck.gemification.util.GemType;
import ellpeck.gemification.util.Util;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrucible extends TileEntityInventoryBase{

    public final int slotOutput = 11;
    public final int slotMainInput = 4;
    public final int slotWater = 9;
    public final int slotSmeltGem = 10;

    /*
    Variables that need to be saved to and loaded from NBT
     */
    public GemType currentFluid = Util.fluidNone;
    public int currentFluidID;
    public int currentProcessTime;
    public int processTimeNeeded;
    public int burnTime;
    public int burnTimeOfItem;

    public static ItemStack output;

    public TileEntityCrucible(){
        /**
         * 0-3: Inputs
         * 4: Main Input
         * 5-8: Inputs
         * 9: Water
         * 10: Gem
         * 11: Output
         */
        this.slots = new ItemStack[12];
    }

    @SuppressWarnings("static-access")
    public void updateEntity(){
        boolean isCraftingFlag = this.isCrafting();
        if(!worldObj.isRemote){
            if(!this.isCrafting()) this.output = CrucibleCraftingManager.instance.getCraftingResult(slots, 0, 8, currentFluid);
            this.getBurnFromBelow();
            this.craft();
            this.addWaterByWaterSlot();
            this.colorGemWater();
            this.currentFluidID = this.currentFluid.ID;
        }
        if(isCraftingFlag != this.isCrafting()){
            this.markDirty();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public boolean isCrafting(){
        return this.currentProcessTime > 0;
    }

    @SuppressWarnings("static-access")
    public void craft(){
        if(this.burnTime > 0){
            if (!this.isCrafting()){
                if (output != null && (this.slots[slotOutput] == null || this.slots[slotOutput].isItemEqual(output))) {
                    this.processTimeNeeded = this.currentProcessTime = CrucibleCraftingManager.instance.getProcessTimeNeeded(slots, 0, 8);
                    for (int i = 0; i <= 8; i++){
                        this.slots[i].stackSize--;
                        if (this.slots[i].stackSize == 0) {
                            this.slots[i] = slots[i].getItem().getContainerItem(slots[i]);
                        }
                    }
                    this.currentFluid = Util.fluidNone;
                }
            }
            if(this.isCrafting()){
                this.currentProcessTime--;
                if (this.currentProcessTime <= 0){
                    if (this.slots[slotOutput] == null) this.slots[slotOutput] = output.copy();
                    else if (this.slots[slotOutput].getItem() == output.getItem())
                        this.slots[slotOutput].stackSize += output.stackSize;
                    this.output = null;
                    this.currentProcessTime = 0;
                    this.processTimeNeeded = 0;
                }
            }
        }
    }

    public void getBurnFromBelow(){
        TileEntity tileDown = worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if(tileDown instanceof TileEntityCrucibleFire){
            this.burnTime = ((TileEntityCrucibleFire)tileDown).burnTime;
            this.burnTimeOfItem = ((TileEntityCrucibleFire)tileDown).burnTimeOfItem;
        }
        else{
            this.burnTime = 0;
            this.burnTimeOfItem = 0;
        }
    }

    public void colorGemWater(){
        ItemStack stack = this.slots[slotSmeltGem];
        if(stack != null && stack.getItem() instanceof ItemGem){
            if(this.currentFluid == Util.fluidWater) {
                this.currentFluid = Util.gemList.get(stack.getItemDamage());
                stack.stackSize--;
                if(stack.stackSize == 0) this.slots[slotSmeltGem] = stack.getItem().getContainerItem(stack);
            }
        }
    }

    public void addWaterByWaterSlot(){
        if(this.slots[this.slotWater] != null && this.slots[this.slotWater].getItem() == Items.water_bucket && this.currentFluid == Util.fluidNone){
            this.currentFluid = Util.fluidWater;
            this.slots[this.slotWater] = new ItemStack(Items.bucket);
        }
    }

    public String getInventoryName() {
        return InitBlocks.blockCrucible.getUnlocalizedName().substring(5);
    }

    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);

        compound.setInteger("CurrentFluidID", this.currentFluidID);
        compound.setInteger("ProcessTime", this.currentProcessTime);
        compound.setInteger("ProcessTimeNeeded", this.processTimeNeeded);

        if(output != null) {
            NBTTagCompound compoundOutput = new NBTTagCompound();
            compoundOutput = output.writeToNBT(compoundOutput);
            compound.setTag("Output", compoundOutput);
        }
    }

    @SuppressWarnings("static-access")
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);

        this.currentFluidID = compound.getInteger("CurrentFluidID");
        this.currentProcessTime = compound.getInteger("ProcessTime");
        this.processTimeNeeded = compound.getInteger("ProcessTimeNeeded");
        this.output = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Output"));

        if(this.currentFluidID == Util.fluidWater.ID) this.currentFluid = Util.fluidWater;
        else if(this.currentFluidID == Util.fluidNone.ID) this.currentFluid = Util.fluidNone;
        else this.currentFluid = Util.gemList.get(this.currentFluidID);
    }

    @SideOnly(Side.CLIENT)
    public int getCraftProcessScaled(int par1){
        return (this.processTimeNeeded-this.currentProcessTime) * par1 / this.processTimeNeeded;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int i){
        return this.burnTime * i / this.burnTimeOfItem;
    }
}
