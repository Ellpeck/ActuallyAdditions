/*
 * This file ("TeslaUtil.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.HashMap;
import java.util.Map;

public final class TeslaUtil{

    private static final Map<TileEntityBase, TileTeslaWrapper[]> TESLA_MAP = new HashMap<TileEntityBase, TileTeslaWrapper[]>();
    @CapabilityInject(ITeslaConsumer.class)
    public static Capability<ITeslaConsumer> teslaConsumer;
    @CapabilityInject(ITeslaProducer.class)
    public static Capability<ITeslaProducer> teslaProducer;
    @CapabilityInject(ITeslaHolder.class)
    public static Capability<ITeslaHolder> teslaHolder;

    public static <T> T wrapTeslaToRF(TileEntityBase tile, Capability<T> capability, EnumFacing facing){
        boolean receive = tile instanceof IEnergyReceiver && capability == teslaConsumer;
        boolean provide = tile instanceof IEnergyProvider && capability == teslaProducer;
        boolean hold = tile instanceof IEnergyHandler && capability == teslaHolder;
        if(receive || provide || hold){
            return (T)getHandler(tile, facing);
        }
        else{
            return null;
        }
    }

    public static boolean doWrappedTeslaRFInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing side, int maxTransfer){
        if(tileTo.hasCapability(teslaConsumer, side.getOpposite()) && tileFrom.hasCapability(teslaProducer, side)){
            ITeslaConsumer handlerTo = tileTo.getCapability(teslaConsumer, side.getOpposite());
            ITeslaProducer handlerFrom = tileFrom.getCapability(teslaProducer, side);

            if(handlerTo != null && handlerFrom != null){
                long drain = handlerFrom.takePower(maxTransfer, true);
                if(drain > 0){
                    long filled = handlerTo.givePower(drain, false);
                    handlerFrom.takePower(filled, false);
                    return true;
                }
            }
        }
        return false;
    }

    private static TileTeslaWrapper getHandler(TileEntityBase tile, EnumFacing facing){
        TileTeslaWrapper[] handlers = TESLA_MAP.get(tile);
        if(handlers == null || handlers.length != 6){
            handlers = new TileTeslaWrapper[6];
            TESLA_MAP.put(tile, handlers);
        }

        int side = facing == null ? 0 : facing.ordinal();
        if(handlers[side] == null){
            handlers[side] = new TileTeslaWrapper(tile, facing);
        }
        return handlers[side];
    }

}
