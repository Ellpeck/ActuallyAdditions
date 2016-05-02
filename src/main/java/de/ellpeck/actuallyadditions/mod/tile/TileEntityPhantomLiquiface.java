/*
 * This file ("TileEntityPhantomLiquiface.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
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

public class TileEntityPhantomLiquiface extends TileEntityPhantomface implements IFluidHandler{

    public TileEntityPhantomLiquiface(){
        super("liquiface");
        this.type = BlockPhantom.Type.LIQUIFACE;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote){
            if(this.isRedstonePowered && this.isBoundThingInRange() && this.getHandler() != null){
                this.pushFluid(EnumFacing.UP);
                this.pushFluid(EnumFacing.DOWN);
                this.pushFluid(EnumFacing.NORTH);
                this.pushFluid(EnumFacing.EAST);
                this.pushFluid(EnumFacing.SOUTH);
                this.pushFluid(EnumFacing.WEST);
            }
        }
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

    private void pushFluid(EnumFacing side){
        TileEntity tile = WorldUtil.getTileEntityFromSide(side, this.worldObj, this.pos);
        if(tile != null && tile instanceof IFluidHandler && this.getTankInfo(side) != null && this.getTankInfo(side).length > 0 && ((IFluidHandler)tile).getTankInfo(side.getOpposite()) != null && ((IFluidHandler)tile).getTankInfo(side.getOpposite()).length > 0){
            for(FluidTankInfo myInfo : this.getTankInfo(side)){
                for(FluidTankInfo hisInfo : ((IFluidHandler)tile).getTankInfo(side.getOpposite())){
                    if(myInfo != null && hisInfo != null && myInfo.fluid != null && myInfo.fluid.getFluid() != null){
                        if(((IFluidHandler)tile).canFill(side.getOpposite(), myInfo.fluid.getFluid()) && this.canDrain(side, myInfo.fluid.getFluid())){
                            FluidStack receive = this.drain(side, Math.min(hisInfo.capacity-(hisInfo.fluid == null ? 0 : hisInfo.fluid.amount), myInfo.fluid.amount), false);
                            if(receive != null){
                                int actualReceive = ((IFluidHandler)tile).fill(side.getOpposite(), receive, true);
                                this.drain(side, new FluidStack(receive.getFluid(), actualReceive), true);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isBoundThingInRange(){
        return super.isBoundThingInRange() && this.worldObj.getTileEntity(this.boundPosition) instanceof IFluidHandler;
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
}
