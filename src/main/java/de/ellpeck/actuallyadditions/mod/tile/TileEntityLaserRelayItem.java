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

import de.ellpeck.actuallyadditions.api.laser.ConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemViewer.GenericItemHandlerInfo;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileEntityLaserRelayItem extends TileEntityLaserRelay{

    public final Map<BlockPos, IItemHandler> handlersAround = new HashMap<BlockPos, IItemHandler>();

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
    public void saveAllHandlersAround(){
        this.handlersAround.clear();

        for(int i = 0; i <= 5; i++){
            EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
            BlockPos pos = this.getPos().offset(side);
            TileEntity tile = this.worldObj.getTileEntity(pos);
            if(tile != null && !(tile instanceof TileEntityItemViewer) && !(tile instanceof TileEntityLaserRelay)){
                IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
                if(handler != null){
                    this.handlersAround.put(tile.getPos(), handler);
                }
            }
        }
    }

    public List<GenericItemHandlerInfo> getItemHandlersInNetwork(Network network){
        //Keeps track of all the Laser Relays and Item Handlers that have been checked already to make nothing run multiple times
        List<BlockPos> alreadyChecked = new ArrayList<BlockPos>();

        List<GenericItemHandlerInfo> handlers = new ArrayList<GenericItemHandlerInfo>();
        for(ConnectionPair pair : network.connections){
            for(BlockPos relay : pair.positions){
                if(relay != null && !alreadyChecked.contains(relay)){
                    alreadyChecked.add(relay);
                    TileEntity aRelayTile = this.worldObj.getTileEntity(relay);
                    if(aRelayTile instanceof TileEntityLaserRelayItem){
                        TileEntityLaserRelayItem relayTile = (TileEntityLaserRelayItem)aRelayTile;
                        GenericItemHandlerInfo info = new GenericItemHandlerInfo(relayTile);

                        Map<BlockPos, IItemHandler> handlersAroundTile = relayTile.handlersAround;
                        for(Map.Entry<BlockPos, IItemHandler> handler : handlersAroundTile.entrySet()){
                            if(!alreadyChecked.contains(handler.getKey())){
                                alreadyChecked.add(handler.getKey());

                                info.handlers.add(handler.getValue());
                            }
                        }

                        handlers.add(info);
                    }
                }
            }
        }
        return handlers;
    }
}
