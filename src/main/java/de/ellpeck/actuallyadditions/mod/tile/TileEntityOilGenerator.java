/*
 * This file ("TileEntityOilGenerator.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityOilGenerator extends TileEntityInventoryBase implements IEnergyProvider, IFluidHandler, IEnergySaver, IFluidSaver{

    public static final int ENERGY_PRODUCED = 76;
    private static final int BURN_TIME = 100;
    public EnergyStorage storage = new EnergyStorage(50000);
    public FluidTank tank = new FluidTank(2*FluidContainerRegistry.BUCKET_VOLUME);
    public int currentBurnTime;
    private int lastEnergy;
    private int lastTank;
    private int lastBurnTime;

    public TileEntityOilGenerator(){
        super(2, "oilGenerator");
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public int getTankScaled(int i){
        return this.tank.getFluidAmount()*i/this.tank.getCapacity();
    }

    @SideOnly(Side.CLIENT)
    public int getBurningScaled(int i){
        return this.currentBurnTime*i/BURN_TIME;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            boolean flag = this.currentBurnTime > 0;

            if(this.currentBurnTime > 0){
                this.currentBurnTime--;
                this.storage.receiveEnergy(ENERGY_PRODUCED, false);
            }

            int fuelUsed = 50;
            if(ENERGY_PRODUCED*BURN_TIME <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored()){
                if(this.currentBurnTime <= 0 && this.tank.getFluidAmount() >= fuelUsed){
                    this.currentBurnTime = BURN_TIME;
                    this.tank.drain(fuelUsed, true);
                }
            }

            WorldUtil.emptyBucket(tank, slots, 0, 1, InitBlocks.fluidOil);

            if(this.storage.getEnergyStored() > 0){
                WorldUtil.pushEnergyToAllSides(worldObj, Position.fromTileEntity(this), this.storage);
            }

            if(flag != this.currentBurnTime > 0){
                this.markDirty();
                Position thisPos = Position.fromTileEntity(this);
                int meta = thisPos.getMetadata(worldObj);
                if(meta == 1){
                    if(!(ENERGY_PRODUCED*BURN_TIME <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored() && this.currentBurnTime <= 0 && this.tank.getFluidAmount() >= fuelUsed)){
                        thisPos.setMetadata(worldObj, 0, 2);
                    }
                }
                else{
                    thisPos.setMetadata(worldObj, 1, 2);
                }
            }

            if((this.storage.getEnergyStored() != this.lastEnergy || this.tank.getFluidAmount() != this.lastTank || this.lastBurnTime != this.currentBurnTime) && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastTank = this.tank.getFluidAmount();
                this.lastBurnTime = this.currentBurnTime;
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        compound.setInteger("BurnTime", this.currentBurnTime);
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeSyncableNBT(compound, sync);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        this.currentBurnTime = compound.getInteger("BurnTime");
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readSyncableNBT(compound, sync);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return FluidContainerRegistry.containsFluid(stack, new FluidStack(InitBlocks.fluidOil, FluidContainerRegistry.BUCKET_VOLUME)) && i == 0;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return slot == 1;
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate){
        return this.storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return true;
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill){
        if(resource.getFluid() == InitBlocks.fluidOil){
            return this.tank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain){
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain){
        return null;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid){
        return from != EnumFacing.DOWN && fluid == InitBlocks.fluidOil;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid){
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from){
        return new FluidTankInfo[]{this.tank.getInfo()};
    }

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy){
        this.storage.setEnergyStored(energy);
    }

    @Override
    public FluidStack[] getFluids(){
        return new FluidStack[]{this.tank.getFluid()};
    }

    @Override
    public void setFluids(FluidStack[] fluids){
        this.tank.setFluid(fluids[0]);
    }
}
