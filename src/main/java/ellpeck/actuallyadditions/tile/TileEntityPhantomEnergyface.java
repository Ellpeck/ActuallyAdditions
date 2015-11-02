/*
 * This file ("TileEntityPhantomEnergyface.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import ellpeck.actuallyadditions.blocks.BlockPhantom;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPhantomEnergyface extends TileEntityPhantomface implements IEnergyHandler{

    public TileEntityPhantomEnergyface(){
        super("energyface");
        this.type = BlockPhantom.Type.ENERGYFACE;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.isBoundThingInRange() && this.getReceiver() != null ? this.getReceiver().receiveEnergy(from, maxReceive, simulate) : 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
        return this.isBoundThingInRange() && this.getProvider() != null ? this.getProvider().extractEnergy(from, maxExtract, simulate) : 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
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
    public int getMaxEnergyStored(ForgeDirection from){
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
        if(this.boundPosition != null && this.boundPosition.getWorld() != null){
            TileEntity tile = boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
            if(tile instanceof IEnergyProvider){
                return (IEnergyProvider)tile;
            }
        }
        return null;
    }

    public IEnergyReceiver getReceiver(){
        if(this.boundPosition != null && this.boundPosition.getWorld() != null){
            TileEntity tile = boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
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
                this.pushEnergy(ForgeDirection.UP);
                this.pushEnergy(ForgeDirection.DOWN);
                this.pushEnergy(ForgeDirection.NORTH);
                this.pushEnergy(ForgeDirection.EAST);
                this.pushEnergy(ForgeDirection.SOUTH);
                this.pushEnergy(ForgeDirection.WEST);
            }
        }
    }

    @Override
    public boolean isBoundThingInRange(){
        return super.isBoundThingInRange() && (this.boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IEnergyReceiver || this.boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IEnergyProvider);
    }

    private void pushEnergy(ForgeDirection side){
        TileEntity tile = WorldUtil.getTileEntityFromSide(side, worldObj, xCoord, yCoord, zCoord);
        if(tile != null && tile instanceof IEnergyReceiver && this.getProvider().getEnergyStored(ForgeDirection.UNKNOWN) > 0){
            if(((IEnergyReceiver)tile).canConnectEnergy(side.getOpposite()) && this.canConnectEnergy(side)){
                int receive = this.extractEnergy(side, Math.min(((IEnergyReceiver)tile).getMaxEnergyStored(ForgeDirection.UNKNOWN)-((IEnergyReceiver)tile).getEnergyStored(ForgeDirection.UNKNOWN), this.getEnergyStored(ForgeDirection.UNKNOWN)), true);
                int actualReceive = ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), receive, false);
                this.extractEnergy(side, actualReceive, false);
            }
        }
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
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
