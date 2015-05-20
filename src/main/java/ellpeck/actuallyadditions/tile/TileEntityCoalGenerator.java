package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoalGenerator extends TileEntityInventoryBase implements IEnergyReceiver{

    public EnergyStorage storage = new EnergyStorage(30000, energyProducedPerTick+50);

    public static int energyProducedPerTick = ConfigIntValues.COAL_GEN_ENERGY_PRODUCED.getValue();

    public int maxBurnTime;
    public int currentBurnTime;

    public TileEntityCoalGenerator(){
        super(1, "coalGenerator");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){

            if(this.currentBurnTime > 0){
                this.currentBurnTime--;
                this.storage.receiveEnergy(energyProducedPerTick, false);
            }

            if(energyProducedPerTick <= this.getMaxEnergyStored(ForgeDirection.UNKNOWN)-this.getEnergyStored(ForgeDirection.UNKNOWN)){
                if(this.currentBurnTime <= 0 && this.slots[0] != null && TileEntityFurnace.getItemBurnTime(this.slots[0]) > 0){
                    this.maxBurnTime = TileEntityFurnace.getItemBurnTime(this.slots[0]);
                    this.currentBurnTime = this.maxBurnTime;
                    this.slots[0].stackSize--;
                    if(this.slots[0].stackSize == 0) this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
                }
            }

            if(this.getEnergyStored(ForgeDirection.UNKNOWN) > 0){
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, storage);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.getEnergyStored(ForgeDirection.UNKNOWN) * i / this.getMaxEnergyStored(ForgeDirection.UNKNOWN);
    }

    @SideOnly(Side.CLIENT)
    public int getBurningScaled(int i){
        return this.currentBurnTime * i / this.maxBurnTime;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        compound.setInteger("BurnTime", this.currentBurnTime);
        compound.setInteger("MaxBurnTime", this.maxBurnTime);
        this.storage.writeToNBT(compound);
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.currentBurnTime = compound.getInteger("BurnTime");
        this.maxBurnTime = compound.getInteger("MaxBurnTime");
        this.storage.readFromNBT(compound);
        super.readFromNBT(compound);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return TileEntityFurnace.getItemBurnTime(stack) > 0;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return false;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }
}
