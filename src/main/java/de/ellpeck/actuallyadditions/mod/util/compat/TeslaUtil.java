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

    private static final Map<TileEntityBase, TeslaHandler[]> TESLA_MAP = new HashMap<TileEntityBase, TeslaHandler[]>();
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

    public static void doWrappedTeslaRFInteraction(TileEntity tile, TileEntity otherTile, EnumFacing side){
        ITeslaConsumer handlerTo = null;
        ITeslaProducer handlerFrom = null;

        for(int i = 0; i < 2; i++){
            if(handlerFrom == null && handlerTo == null){
                handlerFrom = (i == 0 ? tile : otherTile).getCapability(teslaProducer, i == 0 ? side : side.getOpposite());
                handlerTo = (i == 0 ? otherTile : tile).getCapability(teslaConsumer, i == 0 ? side.getOpposite() : side);
            }
        }

        if(handlerFrom != null && handlerTo != null){
            long drain = handlerFrom.takePower(Long.MAX_VALUE, true);
            if(drain > 0){
                long filled = handlerTo.givePower(drain, false);
                handlerFrom.takePower(filled, false);
            }
        }
    }

    private static TeslaHandler getHandler(TileEntityBase tile, EnumFacing facing){
        TeslaHandler[] handlers = TESLA_MAP.get(tile);
        if(handlers == null || handlers.length != 6){
            handlers = new TeslaHandler[6];
            TESLA_MAP.put(tile, handlers);
        }

        int side = facing.ordinal();
        if(handlers[side] == null){
            handlers[side] = new TeslaHandler(tile, facing);
        }
        return handlers[side];
    }

}
