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

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

public class TileEntityItemViewer extends TileEntityBase{

    private final List<GenericItemHandlerInfo> genericInfos = new ArrayList<GenericItemHandlerInfo>();
    private final Map<Integer, SpecificItemHandlerInfo> specificInfos = new HashMap<Integer, SpecificItemHandlerInfo>();
    public TileEntityLaserRelayItem connectedRelay;

    private Network oldNetwork;
    private int lastNetworkChangeAmount = -1;

    protected final IItemHandler itemHandler;

    public TileEntityItemViewer(String name){
        super(name);

        this.itemHandler = new IItemHandler(){
            @Override
            public int getSlots(){
                int size = 0;
                List<GenericItemHandlerInfo> infos = TileEntityItemViewer.this.getItemHandlerInfos();
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
            public ItemStack getStackInSlot(int slot){
                SpecificItemHandlerInfo handler = TileEntityItemViewer.this.getSwitchedIndexHandler(slot);
                if(handler != null){
                    return handler.handler.getStackInSlot(handler.switchedIndex);
                }
                return StackUtil.getNull();
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
                SpecificItemHandlerInfo info = TileEntityItemViewer.this.getSwitchedIndexHandler(slot);
                if(info != null && TileEntityItemViewer.this.isWhitelisted(info, stack, false)){
                    ItemStack inserted = info.handler.insertItem(info.switchedIndex, stack, simulate);
                    if(!ItemStack.areItemStacksEqual(inserted, stack)){
                        TileEntityItemViewer.this.markDirty();
                    }
                    return inserted;
                }
                return stack;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate){
                ItemStack stackIn = this.getStackInSlot(slot);
                if(StackUtil.isValid(stackIn)){
                    SpecificItemHandlerInfo info = TileEntityItemViewer.this.getSwitchedIndexHandler(slot);
                    if(info != null && TileEntityItemViewer.this.isWhitelisted(info, stackIn, true)){
                        ItemStack extracted = info.handler.extractItem(info.switchedIndex, amount, simulate);
                        if(extracted != null){
                            TileEntityItemViewer.this.markDirty();
                        }
                        return extracted;
                    }
                }
                return StackUtil.getNull();
            }

            @Override
            public int getSlotLimit(int slot){
                SpecificItemHandlerInfo info = TileEntityItemViewer.this.getSwitchedIndexHandler(slot);
                if(info != null){
                    return info.handler.getSlotLimit(info.switchedIndex);
                }
                else{
                    return 0;
                }
            }
        };
    }

    public TileEntityItemViewer(){
        this("itemViewer");
    }

    @Override
    public IItemHandler getItemHandler(EnumFacing facing){
        return this.itemHandler;
    }

    private List<GenericItemHandlerInfo> getItemHandlerInfos(){
        this.queryAndSaveData();
        return this.genericInfos;
    }

    private void queryAndSaveData(){
        if(this.connectedRelay != null){
            Network network = ActuallyAdditionsAPI.connectionHandler.getNetworkFor(this.connectedRelay.getPos(), this.world);
            if(network != null){
                if(this.oldNetwork != network || this.lastNetworkChangeAmount != network.changeAmount){
                    this.clearInfos();

                    this.connectedRelay.getItemHandlersInNetwork(network, this.genericInfos);
                    if(!this.genericInfos.isEmpty()){
                        Collections.sort(this.genericInfos);

                        int slotsQueried = 0;
                        for(GenericItemHandlerInfo info : this.genericInfos){
                            for(IItemHandler handler : info.handlers){
                                for(int i = 0; i < handler.getSlots(); i++){
                                    this.specificInfos.put(slotsQueried, new SpecificItemHandlerInfo(handler, i, info.relayInQuestion));
                                    slotsQueried++;
                                }
                            }
                        }
                    }
                    this.oldNetwork = network;
                    this.lastNetworkChangeAmount = network.changeAmount;
                }

                return;
            }
        }

        this.clearInfos();
    }

    private void clearInfos(){
        if(!this.genericInfos.isEmpty()){
            this.genericInfos.clear();
        }

        if(!this.specificInfos.isEmpty()){
            this.specificInfos.clear();
        }
    }

    private SpecificItemHandlerInfo getSwitchedIndexHandler(int i){
        this.queryAndSaveData();
        return this.specificInfos.get(i);
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart(){
        return true;
    }

    @Override
    public void saveDataOnChangeOrWorldStart(){
        TileEntityLaserRelayItem tileFound = null;
        if(this.world != null){ //Why is that even possible..?
            for(int i = 0; i <= 5; i++){
                EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
                BlockPos pos = this.getPos().offset(side);
                TileEntity tile = this.world.getTileEntity(pos);

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

    private boolean isWhitelisted(SpecificItemHandlerInfo handler, ItemStack stack, boolean output){
        boolean whitelisted = handler.relayInQuestion.isWhitelisted(stack, output);
        TileEntityLaserRelayItem connected = this.connectedRelay;
        if(connected != null && connected != handler.relayInQuestion){
            return whitelisted && connected.isWhitelisted(stack, output);
        }
        else{
            return whitelisted;
        }
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

        @Override
        public int compareTo(GenericItemHandlerInfo other){
            int thisPrio = this.relayInQuestion.getPriority();
            int otherPrio = other.relayInQuestion.getPriority();

            if(thisPrio == otherPrio){
                return 0;
            }
            else if(thisPrio > otherPrio){
                return -1;
            }
            else{
                return 1;
            }
        }
    }
}
