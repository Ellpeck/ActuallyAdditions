/*
 * This file ("TileEntityItemViewer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileEntityItemViewer extends TileEntityInventoryBase{

    public TileEntityLaserRelayItem connectedRelay;
    private boolean hasCheckedRelayOnLoad;

    public TileEntityItemViewer(){
        super(0, "itemViewer");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.worldObj.isRemote && !this.hasCheckedRelayOnLoad){
            this.saveConnectedRelay();
            this.hasCheckedRelayOnLoad = true;

            System.out.println("------------Saving relay on load " + this.connectedRelay);
        }
    }

    private List<GenericItemHandlerInfo> getItemHandlerInfos(){
        TileEntityLaserRelayItem relay = this.connectedRelay;
        if(relay != null){
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getNetworkFor(relay.getPos(), this.worldObj);
            if(network != null){
                return relay.getItemHandlersInNetwork(network);
            }
        }
        return null;
    }

    private SpecificItemHandlerInfo getSwitchedIndexHandler(int i){
        List<GenericItemHandlerInfo> infos = this.getItemHandlerInfos();
        if(infos != null && !infos.isEmpty()){
            Collections.sort(infos);
            int currentI = 0;
            if(!infos.isEmpty()){
                for(GenericItemHandlerInfo info : infos){
                    for(IItemHandler handler : info.handlers){
                        int slotAmount = handler.getSlots();
                        if(currentI+slotAmount > i){
                            return new SpecificItemHandlerInfo(handler, i-currentI, info.relayInQuestion);
                        }
                        else{
                            currentI += slotAmount;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void saveConnectedRelay(){
        TileEntityLaserRelayItem tileFound = null;
        if(this.worldObj != null){ //Why is that even possible..?
            for(int i = 0; i <= 5; i++){
                EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
                BlockPos pos = WorldUtil.getCoordsFromSide(side, this.getPos(), 0);
                TileEntity tile = this.worldObj.getTileEntity(pos);

                if(tile instanceof TileEntityLaserRelayItem){
                    if(tileFound != null){
                        this.connectedRelay = null;
                        return;
                    }
                    else{
                        tileFound = (TileEntityLaserRelayItem)tile;
                    }
                }
            }
        }
        this.connectedRelay = tileFound;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction){
        return this.isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(index);
        if(handler != null){
            if(this.isWhitelisted(handler, stack, true)){
                if(ItemStack.areItemsEqual(handler.handler.getStackInSlot(handler.switchedIndex), stack)){
                    ItemStack gaveBack = handler.handler.extractItem(handler.switchedIndex, stack.stackSize, true);
                    return gaveBack != null;
                }
            }
        }
        return false;
    }

    private boolean isWhitelisted(SpecificItemHandlerInfo handler, ItemStack stack, boolean output){
        boolean whitelisted = handler.relayInQuestion.isWhitelisted(stack, output);
        TileEntityLaserRelayItem connected = this.connectedRelay;
        if(connected != null && connected != handler.relayInQuestion){
            return whitelisted && connected.isWhitelisted(stack, !output);
        }
        else{
            return whitelisted;
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(index);
        if(handler != null){
            if(this.isWhitelisted(handler, stack, false)){
                ItemStack gaveBack = handler.handler.insertItem(handler.switchedIndex, stack, true);
                return !ItemStack.areItemStacksEqual(gaveBack, stack);
            }
        }
        return false;
    }

    @Override
    public void clear(){
        for(int i = 0; i < this.getSizeInventory(); i++){
            this.removeStackFromSlot(i);
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack){
        if(stack != null){
            SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(i);
            if(handler != null){
                ItemStack toInsert = stack.copy();
                ItemStack inSlot = handler.handler.getStackInSlot(handler.switchedIndex);
                if(inSlot != null){
                    toInsert.stackSize -= inSlot.stackSize;
                }
                handler.handler.insertItem(handler.switchedIndex, toInsert, false);
            }
        }
        else{
            this.removeStackFromSlot(i);
        }
    }

    @Override
    public int getSizeInventory(){
        int size = 0;
        List<GenericItemHandlerInfo> infos = this.getItemHandlerInfos();
        if(infos != null){
            for(GenericItemHandlerInfo info : infos){
                for(IItemHandler handler : info.handlers){
                    size += handler.getSlots();
                }
            }
        }
        return size;
    }

    @Override
    public ItemStack getStackInSlot(int i){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(i);
        if(handler != null){
            return handler.handler.getStackInSlot(handler.switchedIndex);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(i);
        if(handler != null){
            return handler.handler.extractItem(handler.switchedIndex, j, false);
        }
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(index);
        if(handler != null){
            ItemStack stackInSlot = handler.handler.getStackInSlot(handler.switchedIndex);
            if(stackInSlot != null){
                handler.handler.extractItem(handler.switchedIndex, stackInSlot.stackSize, false);
            }
            return stackInSlot;
        }
        return null;
    }

    private static class SpecificItemHandlerInfo{

        public final IItemHandler handler;
        public final int switchedIndex;
        public final TileEntityLaserRelayItem relayInQuestion;

        public SpecificItemHandlerInfo(IItemHandler handler, int switchedIndex, TileEntityLaserRelayItem relayInQuestion){
            this.handler = handler;
            this.switchedIndex = switchedIndex;
            this.relayInQuestion = relayInQuestion;
        }
    }

    public static class GenericItemHandlerInfo implements Comparable<GenericItemHandlerInfo>{

        public final List<IItemHandler> handlers = new ArrayList<IItemHandler>();
        public final TileEntityLaserRelayItem relayInQuestion;

        public GenericItemHandlerInfo(TileEntityLaserRelayItem relayInQuestion){
            this.relayInQuestion = relayInQuestion;
        }

        public static boolean containsHandler(List<GenericItemHandlerInfo> infos, IItemHandler handler){
            for(GenericItemHandlerInfo info : infos){
                if(info.handlers.contains(handler)){
                    return true;
                }
            }
            return false;
        }

        public static boolean containsTile(List<GenericItemHandlerInfo> infos, TileEntityLaserRelayItem tile){
            for(GenericItemHandlerInfo info : infos){
                if(info.relayInQuestion == tile){
                    return true;
                }
            }
            return false;
        }

        @Override
        public int compareTo(GenericItemHandlerInfo other){
            boolean thisWhitelist = this.relayInQuestion instanceof TileEntityLaserRelayItemWhitelist;
            boolean otherWhitelist = other.relayInQuestion instanceof TileEntityLaserRelayItemWhitelist;

            if(!thisWhitelist && otherWhitelist){
                return 1;
            }
            else if(thisWhitelist && !otherWhitelist){
                return -1;
            }
            else{
                return 0;
            }
        }
    }
}
