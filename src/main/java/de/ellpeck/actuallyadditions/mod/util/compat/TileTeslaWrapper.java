/*
 * This file ("TileTeslaWrapper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util.compat;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.util.EnumFacing;

public class TileTeslaWrapper implements ITeslaProducer, ITeslaHolder, ITeslaConsumer{

    private final EnumFacing side;
    private final TileEntityBase tile;

    public TileTeslaWrapper(TileEntityBase tile, EnumFacing side){
        this.tile = tile;
        this.side = side;
    }

    @Override
    public long givePower(long power, boolean simulated){
        if(this.tile instanceof IEnergyReceiver){
            return ((IEnergyReceiver)this.tile).receiveEnergy(this.side, (int)power, simulated);
        }
        else{
            return 0;
        }
    }

    @Override
    public long getStoredPower(){
        if(this.tile instanceof IEnergyHandler){
            return ((IEnergyHandler)this.tile).getEnergyStored(this.side);
        }
        else{
            return 0;
        }
    }

    @Override
    public long getCapacity(){
        if(this.tile instanceof IEnergyHandler){
            return ((IEnergyHandler)this.tile).getMaxEnergyStored(this.side);
        }
        else{
            return 0;
        }
    }

    @Override
    public long takePower(long power, boolean simulated){
        if(this.tile instanceof IEnergyProvider){
            return ((IEnergyProvider)this.tile).extractEnergy(this.side, (int)power, simulated);
        }
        else{
            return 0;
        }
    }
}
