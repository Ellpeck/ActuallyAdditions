/*
 * This file ("TileEntityPhantomEnergyface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityPhantomEnergyface extends TileEntityPhantomface implements ICustomEnergyReceiver, ISharingEnergyProvider{

    public TileEntityPhantomEnergyface(){
        super("energyface");
        this.type = BlockPhantom.Type.ENERGYFACE;
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        if(this.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null && !(tile instanceof TileEntityLaserRelayEnergy)){
                if(tile instanceof IEnergyReceiver){
                    return ((IEnergyReceiver)tile).receiveEnergy(from, maxReceive, simulate);
                }
                else if(ActuallyAdditions.teslaLoaded && tile.hasCapability(TeslaUtil.teslaConsumer, from)){
                    ITeslaConsumer cap = tile.getCapability(TeslaUtil.teslaConsumer, from);
                    if(cap != null){
                        return (int)cap.givePower(maxReceive, simulate);
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate){
        if(this.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof IEnergyProvider){
                    return ((IEnergyProvider)tile).extractEnergy(from, maxExtract, simulate);
                }
                else if(ActuallyAdditions.teslaLoaded && tile.hasCapability(TeslaUtil.teslaProducer, from)){
                    ITeslaProducer cap = tile.getCapability(TeslaUtil.teslaProducer, from);
                    if(cap != null){
                        return (int)cap.takePower(maxExtract, simulate);
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        if(this.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof IEnergyHandler){
                    return ((IEnergyHandler)tile).getEnergyStored(from);
                }
                else if(ActuallyAdditions.teslaLoaded && tile.hasCapability(TeslaUtil.teslaHolder, from)){
                    ITeslaHolder cap = tile.getCapability(TeslaUtil.teslaHolder, from);
                    if(cap != null){
                        return (int)cap.getStoredPower();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        if(this.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof IEnergyHandler){
                    return ((IEnergyHandler)tile).getMaxEnergyStored(from);
                }
                else if(ActuallyAdditions.teslaLoaded && tile.hasCapability(TeslaUtil.teslaHolder, from)){
                    ITeslaHolder cap = tile.getCapability(TeslaUtil.teslaHolder, from);
                    if(cap != null){
                        return (int)cap.getCapacity();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public boolean isBoundThingInRange(){
        if(super.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof IEnergyHandler){
                    return true;
                }
                else if(ActuallyAdditions.teslaLoaded){
                    for(EnumFacing facing : EnumFacing.values()){
                        if(tile.hasCapability(TeslaUtil.teslaHolder, facing)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        if(this.isBoundThingInRange()){
            TileEntity tile = this.worldObj.getTileEntity(this.boundPosition);
            if(tile != null){
                if(tile instanceof IEnergyConnection){
                    return ((IEnergyConnection)tile).canConnectEnergy(from);
                }
                else{
                    return ActuallyAdditions.teslaLoaded && tile.hasCapability(TeslaUtil.teslaHolder, from);
                }
            }
        }
        return false;
    }

    @Override
    public int getEnergyToSplitShare(){
        return Integer.MAX_VALUE;
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
