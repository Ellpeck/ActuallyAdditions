/*
 * This file ("TileEntityFermentingBarrel.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerFluidMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFermentingBarrel extends TileEntityBase implements ISharingFluidHandler{

    private static final int PROCESS_TIME = 100;
    public final FluidTank canolaTank = new FluidTank(2*Util.BUCKET){
        @Override
        public boolean canDrain(){
            return false;
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid){
            return fluid.getFluid() == InitFluids.fluidCanolaOil;
        }
    };
    public final FluidTank oilTank = new FluidTank(2*Util.BUCKET){
        @Override
        public boolean canFill(){
            return false;
        }
    };
    private final FluidHandlerFluidMap handlerMap;

    public int currentProcessTime;
    private int lastCanola;
    private int lastOil;
    private int lastProcessTime;
    private int lastCompare;

    public TileEntityFermentingBarrel(){
        super("fermentingBarrel");

        this.handlerMap = new FluidHandlerFluidMap();
        this.handlerMap.addHandler(InitFluids.fluidCanolaOil, this.canolaTank);
        this.handlerMap.addHandler(InitFluids.fluidOil, this.oilTank);
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        compound.setInteger("ProcessTime", this.currentProcessTime);
        this.canolaTank.writeToNBT(compound);
        NBTTagCompound tag = new NBTTagCompound();
        this.oilTank.writeToNBT(tag);
        compound.setTag("OilTank", tag);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        this.currentProcessTime = compound.getInteger("ProcessTime");
        this.canolaTank.readFromNBT(compound);
        NBTTagCompound tag = compound.getCompoundTag("OilTank");
        if(tag != null){
            this.oilTank.readFromNBT(tag);
        }
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            int produce = 80;
            if(this.canolaTank.getFluidAmount() >= produce && produce <= this.oilTank.getCapacity()-this.oilTank.getFluidAmount()){
                this.currentProcessTime++;
                if(this.currentProcessTime >= PROCESS_TIME){
                    this.currentProcessTime = 0;

                    this.oilTank.fillInternal(new FluidStack(InitFluids.fluidOil, produce), true);
                    this.canolaTank.drainInternal(produce, true);
                }
            }
            else{
                this.currentProcessTime = 0;
            }

            int compare = this.getComparatorStrength();
            if(compare != this.lastCompare){
                this.lastCompare = compare;

                this.markDirty();
            }

            if((this.canolaTank.getFluidAmount() != this.lastCanola || this.oilTank.getFluidAmount() != this.lastOil || this.currentProcessTime != this.lastProcessTime) && this.sendUpdateWithInterval()){
                this.lastProcessTime = this.currentProcessTime;
                this.lastCanola = this.canolaTank.getFluidAmount();
                this.lastOil = this.oilTank.getFluidAmount();
            }
        }
    }

    @Override
    public int getComparatorStrength(){
        float calc = ((float)this.oilTank.getFluidAmount()/(float)this.oilTank.getCapacity())*15F;
        return (int)calc;
    }

    @SideOnly(Side.CLIENT)
    public int getProcessScaled(int i){
        return this.currentProcessTime*i/PROCESS_TIME;
    }

    @SideOnly(Side.CLIENT)
    public int getOilTankScaled(int i){
        return this.oilTank.getFluidAmount()*i/this.oilTank.getCapacity();
    }

    @SideOnly(Side.CLIENT)
    public int getCanolaTankScaled(int i){
        return this.canolaTank.getFluidAmount()*i/this.canolaTank.getCapacity();
    }

    @Override
    public IFluidHandler getFluidHandler(EnumFacing facing){
        return this.handlerMap;
    }

    @Override
    public int getMaxFluidAmountToSplitShare(){
        return this.oilTank.getFluidAmount();
    }

    @Override
    public boolean doesShareFluid(){
        return true;
    }

    @Override
    public EnumFacing[] getFluidShareSides(){
        return EnumFacing.values();
    }
}
