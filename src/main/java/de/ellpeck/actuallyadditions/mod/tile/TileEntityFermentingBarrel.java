/*
 * This file ("TileEntityFermentingBarrel.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFermentingBarrel extends TileEntityBase implements IFluidHandler, IFluidSaver{

    private static final int PROCESS_TIME = 100;
    public FluidTank canolaTank = new FluidTank(2*Util.BUCKET);
    public FluidTank oilTank = new FluidTank(2*Util.BUCKET);
    public int currentProcessTime;
    private int lastCanola;
    private int lastOil;
    private int lastProcessTime;

    public TileEntityFermentingBarrel(){
        super("fermentingBarrel");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        compound.setInteger("ProcessTime", this.currentProcessTime);
        this.canolaTank.writeToNBT(compound);
        NBTTagCompound tag = new NBTTagCompound();
        this.oilTank.writeToNBT(tag);
        compound.setTag("OilTank", tag);
        super.writeSyncableNBT(compound, sync);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        this.currentProcessTime = compound.getInteger("ProcessTime");
        this.canolaTank.readFromNBT(compound);
        this.oilTank.readFromNBT((NBTTagCompound)compound.getTag("OilTank"));
        super.readSyncableNBT(compound, sync);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            int produce = 80;
            if(this.canolaTank.getFluidAmount() >= produce && produce <= this.oilTank.getCapacity()-this.oilTank.getFluidAmount()){
                this.currentProcessTime++;
                if(this.currentProcessTime >= PROCESS_TIME){
                    this.currentProcessTime = 0;

                    this.oilTank.fill(new FluidStack(InitFluids.fluidOil, produce), true);
                    this.canolaTank.drain(produce, true);
                    this.markDirty();
                }
            }
            else{
                this.currentProcessTime = 0;
            }

            if(this.oilTank.getFluidAmount() > 0){
                WorldUtil.pushFluid(this.worldObj, this.pos, EnumFacing.DOWN, this.oilTank);
                if(!this.isRedstonePowered){
                    WorldUtil.pushFluid(this.worldObj, this.pos, EnumFacing.NORTH, this.oilTank);
                    WorldUtil.pushFluid(this.worldObj, this.pos, EnumFacing.EAST, this.oilTank);
                    WorldUtil.pushFluid(this.worldObj, this.pos, EnumFacing.SOUTH, this.oilTank);
                    WorldUtil.pushFluid(this.worldObj, this.pos, EnumFacing.WEST, this.oilTank);
                }
            }

            if((this.canolaTank.getFluidAmount() != this.lastCanola || this.oilTank.getFluidAmount() != this.lastOil || this.currentProcessTime != this.lastProcessTime) && this.sendUpdateWithInterval()){
                this.lastProcessTime = this.currentProcessTime;
                this.lastCanola = this.canolaTank.getFluidAmount();
                this.lastOil = this.oilTank.getFluidAmount();
            }
        }
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
    public int fill(EnumFacing from, FluidStack resource, boolean doFill){
        if(from != EnumFacing.DOWN && resource.getFluid() == InitFluids.fluidCanolaOil){
            return this.canolaTank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain){
        if(resource.getFluid() == InitFluids.fluidOil){
            return this.oilTank.drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain){
        return this.oilTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid){
        return from != EnumFacing.DOWN && fluid == InitFluids.fluidCanolaOil;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid){
        return from != EnumFacing.UP && fluid == InitFluids.fluidOil;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from){
        return new FluidTankInfo[]{this.canolaTank.getInfo(), this.oilTank.getInfo()};
    }

    @Override
    public FluidStack[] getFluids(){
        return new FluidStack[]{this.oilTank.getFluid(), this.canolaTank.getFluid()};
    }

    @Override
    public void setFluids(FluidStack[] fluids){
        this.oilTank.setFluid(fluids[0]);
        this.canolaTank.setFluid(fluids[1]);
    }
}
