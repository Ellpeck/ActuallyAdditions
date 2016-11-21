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
import de.ellpeck.actuallyadditions.api.recipe.OilGenRecipe;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityOilGenerator extends TileEntityBase implements ISharingEnergyProvider, ISharingFluidHandler{

    public final EnergyStorage storage = new EnergyStorage(50000, 150);
    public final FluidTank tank = new FluidTank(2*Util.BUCKET){
        @Override
        public boolean canDrain(){
            return false;
        }

        @Override
        public boolean canFillFluidType(FluidStack stack){
            Fluid fluid = stack.getFluid();
            return fluid != null && getRecipeForFluid(fluid.getName()) != null;
        }
    };
    public int currentEnergyProduce;
    public int currentBurnTime;
    public int maxBurnTime;
    private int lastEnergy;
    private int lastTank;
    private int lastBurnTime;
    private int lastMaxBurnTime;
    private int lastEnergyProduce;

    public TileEntityOilGenerator(){
        super("oilGenerator");
    }

    @SideOnly(Side.CLIENT)
    public int getBurningScaled(int i){
        return this.currentBurnTime*i/this.maxBurnTime;
    }

    private OilGenRecipe getRecipeForCurrentFluid(){
        FluidStack stack = this.tank.getFluid();
        if(stack != null){
            Fluid fluid = stack.getFluid();
            if(fluid != null){
                return getRecipeForFluid(fluid.getName());
            }
        }
        return null;
    }

    private static OilGenRecipe getRecipeForFluid(String fluidName){
        if(fluidName != null){
            for(OilGenRecipe recipe : ActuallyAdditionsAPI.OIL_GENERATOR_RECIPES){
                if(recipe != null && fluidName.equals(recipe.fluidName)){
                    return recipe;
                }
            }
        }
        return null;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("BurnTime", this.currentBurnTime);
            compound.setInteger("CurrentEnergy", this.currentEnergyProduce);
            compound.setInteger("MaxBurnTime", this.maxBurnTime);
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
            this.maxBurnTime = compound.getInteger("MaxBurnTime");
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
                int fuelUsed = 50;

                OilGenRecipe recipe = this.getRecipeForCurrentFluid();
                if(recipe != null && this.storage.getEnergyStored() < this.storage.getMaxEnergyStored() && this.tank.getFluidAmount() >= fuelUsed){
                    this.currentEnergyProduce = recipe.genAmount;
                    this.maxBurnTime = recipe.genTime;
                    this.currentBurnTime = this.maxBurnTime;

                    this.tank.drainInternal(fuelUsed, true);
                }
                else{
                    this.currentEnergyProduce = 0;
                    this.currentBurnTime = 0;
                    this.maxBurnTime = 0;
                }
            }

            if(flag != this.currentBurnTime > 0){
                this.markDirty();
            }

            if((this.storage.getEnergyStored() != this.lastEnergy || this.tank.getFluidAmount() != this.lastTank || this.lastBurnTime != this.currentBurnTime || this.lastEnergyProduce != this.currentEnergyProduce || this.lastMaxBurnTime != this.maxBurnTime) && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastTank = this.tank.getFluidAmount();
                this.lastBurnTime = this.currentBurnTime;
                this.lastEnergyProduce = this.currentEnergyProduce;
                this.lastMaxBurnTime = this.maxBurnTime;
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
    public IFluidHandler getFluidHandler(EnumFacing facing){
        return facing != EnumFacing.DOWN ? this.tank : null;
    }

    @Override
    public int getMaxFluidAmountToSplitShare(){
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
