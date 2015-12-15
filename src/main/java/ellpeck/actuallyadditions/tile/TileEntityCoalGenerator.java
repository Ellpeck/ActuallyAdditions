/*
 * This file ("TileEntityCoalGenerator.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoalGenerator extends TileEntityInventoryBase implements IEnergyProvider, IEnergySaver{

    public static final int PRODUCE = 30;
    public EnergyStorage storage = new EnergyStorage(60000);
    public int maxBurnTime;
    public int currentBurnTime;
    private int lastEnergy;
    private int lastBurnTime;
    private int lastCurrentBurnTime;

    public TileEntityCoalGenerator(){
        super(1, "coalGenerator");
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public int getBurningScaled(int i){
        return this.currentBurnTime*i/this.maxBurnTime;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        compound.setInteger("BurnTime", this.currentBurnTime);
        compound.setInteger("MaxBurnTime", this.maxBurnTime);
        this.storage.writeToNBT(compound);
        super.writeSyncableNBT(compound, sync);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        this.currentBurnTime = compound.getInteger("BurnTime");
        this.maxBurnTime = compound.getInteger("MaxBurnTime");
        this.storage.readFromNBT(compound);
        super.readSyncableNBT(compound, sync);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            boolean flag = this.currentBurnTime > 0;

            if(this.currentBurnTime > 0){
                this.currentBurnTime--;
                this.storage.receiveEnergy(PRODUCE, false);
            }

            if(this.currentBurnTime <= 0 && this.slots[0] != null && TileEntityFurnace.getItemBurnTime(this.slots[0]) > 0){
                int burnTime = TileEntityFurnace.getItemBurnTime(this.slots[0]);
                this.maxBurnTime = burnTime;
                this.currentBurnTime = burnTime;

                this.slots[0].stackSize--;
                if(this.slots[0].stackSize == 0){
                    this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
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

            if(flag != this.currentBurnTime > 0){
                this.markDirty();
                int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                if(meta == 1){
                    if(!(this.currentBurnTime <= 0 && this.slots[0] != null && TileEntityFurnace.getItemBurnTime(this.slots[0]) > 0 && PRODUCE*TileEntityFurnace.getItemBurnTime(this.slots[0]) <= this.getMaxEnergyStored(ForgeDirection.UNKNOWN)-this.getEnergyStored(ForgeDirection.UNKNOWN))){
                        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
                    }
                }
                else{
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
                }
            }

            if((this.storage.getEnergyStored() != this.lastEnergy || this.currentBurnTime != this.lastCurrentBurnTime || this.lastBurnTime != this.maxBurnTime) && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastCurrentBurnTime = this.currentBurnTime;
                this.lastBurnTime = this.currentBurnTime;
            }
        }
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
    public int extractEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.storage.extractEnergy(maxReceive, simulate);
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

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy){
        this.storage.setEnergyStored(energy);
    }
}
