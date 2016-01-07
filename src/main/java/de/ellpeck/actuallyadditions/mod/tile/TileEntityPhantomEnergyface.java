/*
 * This file ("TileEntityPhantomEnergyface.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityPhantomEnergyface extends TileEntityPhantomface implements IEnergyHandler{

    public TileEntityPhantomEnergyface(){
        super("energyface");
        this.type = BlockPhantom.Type.ENERGYFACE;
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.isBoundThingInRange() && this.getReceiver() != null ? this.getReceiver().receiveEnergy(from, maxReceive, simulate) : 0;
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate){
        return this.isBoundThingInRange() && this.getProvider() != null ? this.getProvider().extractEnergy(from, maxExtract, simulate) : 0;
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        if(this.isBoundThingInRange()){
            if(this.getProvider() != null){
                return this.getProvider().getEnergyStored(from);
            }
            if(this.getReceiver() != null){
                return this.getReceiver().getEnergyStored(from);
            }
        }
        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        if(this.isBoundThingInRange()){
            if(this.getProvider() != null){
                return this.getProvider().getMaxEnergyStored(from);
            }
            if(this.getReceiver() != null){
                return this.getReceiver().getMaxEnergyStored(from);
            }
        }
        return 0;
    }

    public IEnergyProvider getProvider(){
        if(this.boundPosition != null){
            TileEntity tile = worldObj.getTileEntity(boundPosition.toBlockPos());
            if(tile instanceof IEnergyProvider){
                return (IEnergyProvider)tile;
            }
        }
        return null;
    }

    public IEnergyReceiver getReceiver(){
        if(this.boundPosition != null){
            TileEntity tile = worldObj.getTileEntity(boundPosition.toBlockPos());
            if(tile instanceof IEnergyReceiver){
                return (IEnergyReceiver)tile;
            }
        }
        return null;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!worldObj.isRemote){
            if(this.isBoundThingInRange() && this.getProvider() != null){
                this.pushEnergy(EnumFacing.UP);
                this.pushEnergy(EnumFacing.DOWN);
                this.pushEnergy(EnumFacing.NORTH);
                this.pushEnergy(EnumFacing.EAST);
                this.pushEnergy(EnumFacing.SOUTH);
                this.pushEnergy(EnumFacing.WEST);
            }
        }
    }

    @Override
    public boolean isBoundThingInRange(){
        return super.isBoundThingInRange() && (worldObj.getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IEnergyReceiver || worldObj.getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IEnergyProvider);
    }

    private void pushEnergy(EnumFacing side){
        TileEntity tile = WorldUtil.getTileEntityFromSide(side, worldObj, Position.fromTileEntity(this));
        if(tile != null && tile instanceof IEnergyReceiver && this.getProvider().getEnergyStored(side.getOpposite()) > 0){
            if(((IEnergyReceiver)tile).canConnectEnergy(side.getOpposite()) && this.canConnectEnergy(side)){
                int receive = this.extractEnergy(side, Math.min(((IEnergyReceiver)tile).getMaxEnergyStored(side.getOpposite())-((IEnergyReceiver)tile).getEnergyStored(side.getOpposite()), this.getEnergyStored(side)), true);
                int actualReceive = ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), receive, false);
                this.extractEnergy(side, actualReceive, false);
            }
        }
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        if(this.isBoundThingInRange()){
            if(this.getProvider() != null){
                return this.getProvider().canConnectEnergy(from);
            }
            if(this.getReceiver() != null){
                return this.getReceiver().canConnectEnergy(from);
            }
        }
        return false;
    }
}
