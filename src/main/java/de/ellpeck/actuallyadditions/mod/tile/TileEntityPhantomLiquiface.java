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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityPhantomLiquiface extends TileEntityPhantomface implements ISharingFluidHandler{

    public TileEntityPhantomLiquiface(){
        super("liquiface");
        this.type = BlockPhantom.Type.LIQUIFACE;
    }

    @Override
    public boolean isBoundThingInRange(){
        if(super.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof net.minecraftforge.fluids.IFluidHandler){
                    return true;
                }
                else{
                    for(EnumFacing facing : EnumFacing.values()){
                        if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill){
        if(this.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof net.minecraftforge.fluids.IFluidHandler){
                    return ((net.minecraftforge.fluids.IFluidHandler)tile).fill(from, resource, doFill);
                }
                else{
                    if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, from)){
                        IFluidHandler cap = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, from);
                        if(cap != null){
                            return cap.fill(resource, doFill);
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain){
        if(this.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof net.minecraftforge.fluids.IFluidHandler){
                    return ((net.minecraftforge.fluids.IFluidHandler)tile).drain(from, resource, doDrain);
                }
                else{
                    if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, from)){
                        IFluidHandler cap = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, from);
                        if(cap != null){
                            return cap.drain(resource, doDrain);
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain){
        if(this.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof net.minecraftforge.fluids.IFluidHandler){
                    return ((net.minecraftforge.fluids.IFluidHandler)tile).drain(from, maxDrain, doDrain);
                }
                else{
                    if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, from)){
                        IFluidHandler cap = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, from);
                        if(cap != null){
                            return cap.drain(maxDrain, doDrain);
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid){
        if(this.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof net.minecraftforge.fluids.IFluidHandler){
                    return ((net.minecraftforge.fluids.IFluidHandler)tile).canFill(from, fluid);
                }
                else{
                    if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, from)){
                        IFluidHandler cap = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, from);
                        if(cap != null){
                            FluidStack stack = new FluidStack(fluid, 1);
                            IFluidTankProperties[] props = cap.getTankProperties();
                            if(props != null){
                                for(IFluidTankProperties prop : props){
                                    if(prop.canFillFluidType(stack)){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid){
        if(this.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof net.minecraftforge.fluids.IFluidHandler){
                    return ((net.minecraftforge.fluids.IFluidHandler)tile).canDrain(from, fluid);
                }
                else{
                    if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, from)){
                        IFluidHandler cap = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, from);
                        if(cap != null){
                            FluidStack stack = new FluidStack(fluid, 1);
                            IFluidTankProperties[] props = cap.getTankProperties();
                            if(props != null){
                                for(IFluidTankProperties prop : props){
                                    if(prop.canDrainFluidType(stack)){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from){
        TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
        if(tile instanceof net.minecraftforge.fluids.IFluidHandler){
            return ((net.minecraftforge.fluids.IFluidHandler)tile).getTankInfo(from);
        }
        else{
            return new FluidTankInfo[0];
        }
    }

    @Override
    public int getFluidAmountToSplitShare(){
        return Integer.MAX_VALUE;
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
