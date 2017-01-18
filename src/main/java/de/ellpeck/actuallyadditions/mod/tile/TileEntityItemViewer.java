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

import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import java.util.*;

public class TileEntityItemViewer extends TileEntityInventoryBase{

    private final List<GenericItemHandlerInfo> genericInfos = new ArrayList<GenericItemHandlerInfo>();
    private final Map<Integer, SpecificItemHandlerInfo> specificInfos = new HashMap<Integer, SpecificItemHandlerInfo>();
    public TileEntityLaserRelayItem connectedRelay;

    private int lastNetworkChangeAmount = -1;

    public TileEntityItemViewer(){
        super(0, "itemViewer");
    }

    @Override
    protected void getInvWrappers(SidedInvWrapper[] wrappers){
        for(int i = 0; i < wrappers.length; i++){
            final EnumFacing direction = EnumFacing.values()[i];
            wrappers[i] = new SidedInvWrapper(this, direction){
                @Override
                public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
                    if(TileEntityItemViewer.this.canInsertItem(slot, stack, direction)){
                        SpecificItemHandlerInfo info = TileEntityItemViewer.this.getSwitchedIndexHandler(slot);
                        if(info != null){
                            return info.handler.insertItem(info.switchedIndex, stack, simulate);
                        }
                    }
                    return super.insertItem(slot, stack, simulate);
                }

                @Override
                public ItemStack extractItem(int slot, int amount, boolean simulate){
                    ItemStack stackIn = TileEntityItemViewer.this.getStackInSlot(slot);
                    if(StackUtil.isValid(stackIn)){
                        if(TileEntityItemViewer.this.canExtractItem(slot, stackIn, direction)){
                            SpecificItemHandlerInfo info = TileEntityItemViewer.this.getSwitchedIndexHandler(slot);
                            if(info != null){
                                return info.handler.extractItem(info.switchedIndex, amount, simulate);
                            }
                        }
                    }
                    return super.extractItem(slot, amount, simulate);
                }
            };
        }
    }

    private List<GenericItemHandlerInfo> getItemHandlerInfos(){
        this.queryAndSaveData();
        return this.genericInfos;
    }

    private void queryAndSaveData(){
        if(this.connectedRelay != null){
            Network network = this.connectedRelay.getNetwork();
            if(network != null){
                if(this.lastNetworkChangeAmount != network.changeAmount){
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

                    this.lastNetworkChangeAmount = network.changeAmount;
                }

                return;
            }
        }

        this.clearInfos();
        this.lastNetworkChangeAmount = -1;
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
        if(this.worldObj != null){ //Why is that even possible..?
            for(int i = 0; i <= 5; i++){
                EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
                BlockPos pos = this.getPos().offset(side);
                if(this.worldObj.isBlockLoaded(pos)){
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
        if(handler != null && handler.isLoaded()){
            if(this.isWhitelisted(handler, stack, true)){
                if(ItemStack.areItemsEqual(handler.handler.getStackInSlot(handler.switchedIndex), stack)){
                    ItemStack gaveBack = handler.handler.extractItem(handler.switchedIndex, StackUtil.getStackSize(stack), true);
                    return StackUtil.isValid(gaveBack);
                }
            }
        }
        return false;
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

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(index);
        if(handler != null && handler.isLoaded()){
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
        if(StackUtil.isValid(stack)){
            SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(i);
            if(handler != null && handler.isLoaded()){
                ItemStack toInsert = stack.copy();
                ItemStack inSlot = handler.handler.getStackInSlot(handler.switchedIndex);
                if(StackUtil.isValid(inSlot)){
                    toInsert = StackUtil.addStackSize(toInsert, -StackUtil.getStackSize(inSlot));
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
                if(info.isLoaded()){
                    for(IItemHandler handler : info.handlers){
                        size += handler.getSlots();
                    }
                }
            }
        }
        return size;
    }

    @Override
    public ItemStack getStackInSlot(int i){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(i);
        if(handler != null && handler.isLoaded()){
            return handler.handler.getStackInSlot(handler.switchedIndex);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(i);
        if(handler != null && handler.isLoaded()){
            return handler.handler.extractItem(handler.switchedIndex, j, false);
        }
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(index);
        if(handler != null && handler.isLoaded()){
            ItemStack stackInSlot = handler.handler.getStackInSlot(handler.switchedIndex);
            if(StackUtil.isValid(stackInSlot)){
                handler.handler.extractItem(handler.switchedIndex, StackUtil.getStackSize(stackInSlot), false);
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

        public boolean isLoaded(){
            return this.relayInQuestion.hasWorldObj() && this.relayInQuestion.getWorld().isBlockLoaded(this.relayInQuestion.getPos());
        }
    }

    public static class GenericItemHandlerInfo implements Comparable<GenericItemHandlerInfo>{

        public final List<IItemHandler> handlers = new ArrayList<IItemHandler>();
        public final TileEntityLaserRelayItem relayInQuestion;

        public GenericItemHandlerInfo(TileEntityLaserRelayItem relayInQuestion){
            this.relayInQuestion = relayInQuestion;
        }

        public boolean isLoaded(){
            return this.relayInQuestion.hasWorldObj() && this.relayInQuestion.getWorld().isBlockLoaded(this.relayInQuestion.getPos());
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
