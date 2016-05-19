/*
 * This file ("TileEntityOilGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityOilGenerator extends TileEntityBase implements IEnergyProvider, IFluidHandler, IEnergySaver, IFluidSaver{

    public static final int ENERGY_PRODUCED = 76;
    private static final int BURN_TIME = 100;
    public final EnergyStorage storage = new EnergyStorage(50000);
    public final FluidTank tank = new FluidTank(2*Util.BUCKET);
    public int currentBurnTime;
    private int lastEnergy;
    private int lastTank;
    private int lastBurnTime;

    public TileEntityOilGenerator(){
        super("oilGenerator");
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
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
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

            if(this.storage.getEnergyStored() > 0){
                WorldUtil.pushEnergyToAllSides(this.worldObj, this.pos, this.storage);
            }

            if(flag != this.currentBurnTime > 0){
                this.markDirty();
                int meta = PosUtil.getMetadata(this.pos, this.worldObj);
                if(meta == 1){
                    if(!(ENERGY_PRODUCED*BURN_TIME <= this.storage.getMaxEnergyStored()-this.storage.getEnergyStored() && this.currentBurnTime <= 0 && this.tank.getFluidAmount() >= fuelUsed)){
                        PosUtil.setMetadata(this.pos, this.worldObj, 0, 2);
                    }
                }
                else{
                    PosUtil.setMetadata(this.pos, this.worldObj, 1, 2);
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
        if(resource.getFluid() == InitFluids.fluidOil){
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
        return from != EnumFacing.DOWN && fluid == InitFluids.fluidOil;
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
