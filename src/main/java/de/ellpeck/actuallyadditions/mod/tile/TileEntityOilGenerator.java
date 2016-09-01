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
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityOilGenerator extends TileEntityBase implements ISharingEnergyProvider, ISharingFluidHandler{

    private static final int BURN_TIME = 100;
    public final EnergyStorage storage = new EnergyStorage(50000);
    public final FluidTank tank = new FluidTank(2*Util.BUCKET){
        @Override
        public boolean canDrain(){
            return false;
        }

        @Override
        public boolean canFillFluidType(FluidStack stack){
            Fluid fluid = stack.getFluid();
            return fluid != null && ActuallyAdditionsAPI.OIL_GENERATOR_RECIPES.containsKey(fluid.getName());
        }
    };
    public int currentEnergyProduce;
    public int currentBurnTime;
    private int lastEnergy;
    private int lastTank;
    private int lastBurnTime;
    private int lastEnergyProduce;

    public TileEntityOilGenerator(){
        super("oilGenerator");
    }

    @SideOnly(Side.CLIENT)
    public int getBurningScaled(int i){
        return this.currentBurnTime*i/BURN_TIME;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("BurnTime", this.currentBurnTime);
            compound.setInteger("CurrentEnergy", this.currentEnergyProduce);
        }
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            this.currentBurnTime = compound.getInteger("BurnTime");
            this.currentEnergyProduce = compound.getInteger("CurrentEnergy");
        }
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            boolean flag = this.currentBurnTime > 0;

            if(this.currentBurnTime > 0 && this.currentEnergyProduce > 0){
                this.currentBurnTime--;
                this.storage.receiveEnergy(this.currentEnergyProduce, false);
            }
            else{
                this.currentEnergyProduce = this.getEnergyForCurrentFluid();

                int fuelUsed = 50;
                if(this.storage.getEnergyStored() < this.storage.getMaxEnergyStored() && this.tank.getFluidAmount() >= fuelUsed){
                    this.currentBurnTime = BURN_TIME;
                    this.tank.drainInternal(fuelUsed, true);
                }
                else{
                    this.currentBurnTime = 0;
                }
            }

            if(flag != this.currentBurnTime > 0){
                this.markDirty();
            }

            if((this.storage.getEnergyStored() != this.lastEnergy || this.tank.getFluidAmount() != this.lastTank || this.lastBurnTime != this.currentBurnTime || this.lastEnergyProduce != this.currentEnergyProduce) && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastTank = this.tank.getFluidAmount();
                this.lastBurnTime = this.currentBurnTime;
                this.lastEnergyProduce = this.currentEnergyProduce;
            }
        }
    }

    private int getEnergyForCurrentFluid(){
        FluidStack stack = this.tank.getFluid();
        if(stack != null){
            Fluid fluid = stack.getFluid();
            if(fluid != null){
                return ActuallyAdditionsAPI.OIL_GENERATOR_RECIPES.get(fluid.getName());
            }
        }
        return 0;
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
    public IFluidHandler getFluidHandler(EnumFacing facing){
        return facing != EnumFacing.DOWN ? this.tank : null;
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill){
        IFluidHandler handler = this.getFluidHandler(from);
        return handler == null ? 0 : handler.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain){
        IFluidHandler handler = this.getFluidHandler(from);
        return handler == null ? null : handler.drain(resource, doDrain);
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain){
        IFluidHandler handler = this.getFluidHandler(from);
        return handler == null ? null : handler.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid){
        IFluidHandler handler = this.getFluidHandler(from);
        if(handler != null){
            for(IFluidTankProperties prop : handler.getTankProperties()){
                if(prop != null && prop.canFillFluidType(new FluidStack(fluid, Integer.MAX_VALUE))){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid){
        IFluidHandler handler = this.getFluidHandler(from);
        if(handler != null){
            for(IFluidTankProperties prop : handler.getTankProperties()){
                if(prop != null && prop.canDrainFluidType(new FluidStack(fluid, Integer.MAX_VALUE))){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from){
        IFluidHandler handler = this.getFluidHandler(from);
        if(handler instanceof IFluidTank){
            return new FluidTankInfo[]{((IFluidTank)handler).getInfo()};
        }
        else{
            return null;
        }
    }

    @Override
    public int getFluidAmountToSplitShare(){
        return 0;
    }

    @Override
    public boolean doesShareFluid(){
        return false;
    }

    @Override
    public EnumFacing[] getFluidShareSides(){
        return null;
    }

    @Override
    public int getEnergyToSplitShare(){
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean doesShareEnergy(){
        return true;
    }

    @Override
    public EnumFacing[] getEnergyShareSides(){
        return EnumFacing.values();
    }
}
