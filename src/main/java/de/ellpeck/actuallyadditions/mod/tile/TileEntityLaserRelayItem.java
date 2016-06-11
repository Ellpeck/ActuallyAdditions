/*
 * This file ("TileEntityLaserRelayItem.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler.Network;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemViewer.GenericItemHandlerInfo;
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

    public List<IItemHandler> handlersAround = new ArrayList<IItemHandler>();
    private boolean hasCheckedHandlersOnLoad;

    public TileEntityLaserRelayItem(String name){
        super(name, true);
    }

    public TileEntityLaserRelayItem(){
        this("laserRelayItem");
    }

    public boolean isWhitelisted(ItemStack stack, boolean output){
        return true;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote && !this.hasCheckedHandlersOnLoad){
            this.saveAllHandlersAround();
            this.hasCheckedHandlersOnLoad = true;
        }
    }

    public void saveAllHandlersAround(){
        this.handlersAround.clear();

        for(int i = 0; i <= 5; i++){
            EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
            BlockPos pos = WorldUtil.getCoordsFromSide(side, this.getPos(), 0);
            TileEntity tile = this.worldObj.getTileEntity(pos);
            if(tile != null && !(tile instanceof TileEntityItemViewer)){
                IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
                if(handler != null){
                    this.handlersAround.add(handler);
                }
            }
        }
    }

    public List<GenericItemHandlerInfo> getItemHandlersInNetwork(Network network){
        List<GenericItemHandlerInfo> handlers = new ArrayList<GenericItemHandlerInfo>();
        for(LaserRelayConnectionHandler.ConnectionPair pair : network.connections){
            for(BlockPos relay : pair.positions){
                if(relay != null){
                    TileEntity aRelayTile = this.worldObj.getTileEntity(relay);
                    if(aRelayTile instanceof TileEntityLaserRelayItem){
                        TileEntityLaserRelayItem relayTile = (TileEntityLaserRelayItem)aRelayTile;
                        if(!GenericItemHandlerInfo.containsTile(handlers, relayTile)){
                            GenericItemHandlerInfo info = new GenericItemHandlerInfo(relayTile);

                            List<IItemHandler> handlersAroundTile = relayTile.handlersAround;
                            for(IItemHandler handler : handlersAroundTile){
                                if(!GenericItemHandlerInfo.containsHandler(handlers, handler)){
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
