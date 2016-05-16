/*
 * This file ("TileEntityLaserRelayItem.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class TileEntityLaserRelayItem extends TileEntityLaserRelay{

    public TileEntityLaserRelayItem(String name){
        super(name, true);
    }

    public TileEntityLaserRelayItem(){
        this("laserRelayItem");
    }

    public boolean isWhitelisted(ItemStack stack){
        return true;
    }

    public List<IItemHandler> getAllHandlersAround(){
        List<IItemHandler> handlers = new ArrayList<IItemHandler>();
        for(int i = 0; i <= 5; i++){
            EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
            BlockPos pos = WorldUtil.getCoordsFromSide(side, this.getPos(), 0);
            TileEntity tile = this.worldObj.getTileEntity(pos);
            if(tile != null && !(tile instanceof TileEntityItemViewer)){
                IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
                if(handler != null){
                    handlers.add(handler);
                }
            }
        }
        return handlers;
    }

    public List<TileEntityItemViewer.GenericItemHandlerInfo> getItemHandlersInNetwork(LaserRelayConnectionHandler.Network network){
        List<TileEntityItemViewer.GenericItemHandlerInfo> handlers = new ArrayList<TileEntityItemViewer.GenericItemHandlerInfo>();
        for(LaserRelayConnectionHandler.ConnectionPair pair : network.connections){
            BlockPos[] relays = new BlockPos[]{pair.firstRelay, pair.secondRelay};
            for(BlockPos relay : relays){
                if(relay != null){
                    TileEntity aRelayTile = this.worldObj.getTileEntity(relay);
                    if(aRelayTile instanceof de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItem){
                        de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItem relayTile = (de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItem)aRelayTile;
                        if(!TileEntityItemViewer.GenericItemHandlerInfo.containsTile(handlers, relayTile)){
                            TileEntityItemViewer.GenericItemHandlerInfo info = new TileEntityItemViewer.GenericItemHandlerInfo(relayTile);

                            List<IItemHandler> handlersAroundTile = relayTile.getAllHandlersAround();
                            for(IItemHandler handler : handlersAroundTile){
                                if(!TileEntityItemViewer.GenericItemHandlerInfo.containsHandler(handlers, handler)){
                                    info.handlers.add(handler);
                                }
                            }

                            handlers.add(info);
                        }
                    }
                }
            }
        }
        return handlers;
    }
}
