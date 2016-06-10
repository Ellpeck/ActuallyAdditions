/*
 * This file ("TileEntityPhantomLiquiface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityPhantomLiquiface extends TileEntityPhantomface implements IFluidHandler{

    public TileEntityPhantomLiquiface(){
        super("liquiface");
        this.type = BlockPhantom.Type.LIQUIFACE;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote){
            if(this.isRedstonePowered && this.isBoundThingInRange()){
                for(EnumFacing side : EnumFacing.values()){
                    WorldUtil.pushFluid(this, side);
                }
            }
        }
    }

    @Override
    public boolean isBoundThingInRange(){
        if(super.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                return tile instanceof IFluidHandler || tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
            }
        }
        return false;
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill){
        if(this.isBoundThingInRange()){
            return this.getHandler().fill(from, resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain){
        if(this.isBoundThingInRange()){
            return this.getHandler().drain(from, resource, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain){
        if(this.isBoundThingInRange()){
            return this.getHandler().drain(from, maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid){
        return this.isBoundThingInRange() && this.getHandler().canFill(from, fluid);
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid){
        return this.isBoundThingInRange() && this.getHandler().canDrain(from, fluid);
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from){
        if(this.isBoundThingInRange()){
            return this.getHandler().getTankInfo(from);
        }
        return new FluidTankInfo[0];
    }

    public IFluidHandler getHandler(){
        if(this.boundPosition != null){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile instanceof IFluidHandler){
                return (IFluidHandler)tile;
            }
        }
        return null;
    }
}
