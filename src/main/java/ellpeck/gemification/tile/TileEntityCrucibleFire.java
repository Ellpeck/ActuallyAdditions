package ellpeck.gemification.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.gemification.blocks.InitBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityCrucibleFire extends TileEntityInventoryBase{

    public int burnTime;
    public int burnTimeOfItem;

    public TileEntityCrucibleFire(){
        this.slots = new ItemStack[1];
    }

    public void updateEntity(){
        boolean isBurningFlag = this.isBurning();
        if(!worldObj.isRemote){
            this.burnFuel();
        }
        if(isBurningFlag != this.isBurning()){
            this.markDirty();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void burnFuel(){
        if(this.burnTime <= 0){
            if (this.slots[0] != null) {
                this.burnTimeOfItem = this.burnTime = TileEntityFurnace.getItemBurnTime(this.slots[0]);
                this.slots[0].stackSize--;
                if (this.slots[0].stackSize == 0){
                    this.slots[0] = slots[0].getItem().getContainerItem(slots[0]);
                }
            }
        }
        if(this.burnTime > 0){
            this.burnTime--;
            if(this.burnTime <= 0){
                this.burnTimeOfItem = 0;
                this.burnTime = 0;
            }
        }
    }

    public String getInventoryName() {
        return InitBlocks.blockCrucibleFire.getUnlocalizedName().substring(5);
    }

    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", this.burnTime);
        compound.setInteger("BurnTimeOfItem", this.burnTimeOfItem);
    }

    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.burnTime = compound.getInteger("BurnTime");
        this.burnTimeOfItem = compound.getInteger("BurnTimeOfItem");
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int i){
        return this.burnTime * i / this.burnTimeOfItem;
    }

    public boolean isBurning(){
        return this.burnTime > 0;
    }
}
