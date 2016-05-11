package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay.TileEntityLaserRelayItem;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class TileEntityItemViewer extends TileEntityInventoryBase{

    public TileEntityItemViewer(){
        super(0, "itemViewer");
    }

    private List<GenericItemHandlerInfo> getItemHandlerInfos(){
        TileEntityLaserRelayItem relay = this.getConnectedRelay();
        if(relay != null){
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(relay.getPos());
            if(network != null){
                return relay.getItemHandlersInNetwork(network);
            }
        }
        return null;
    }

    private SpecificItemHandlerInfo getSwitchedIndexHandler(int i){
        List<GenericItemHandlerInfo> infos = this.getItemHandlerInfos();
        int currentI = 0;
        if(infos != null && !infos.isEmpty()){
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
        return null;
    }

    private TileEntityLaserRelayItem getConnectedRelay(){
        TileEntityLaserRelayItem tileFound = null;
        if(this.worldObj != null){ //Why is that even possible..?
            for(int i = 0; i <= 5; i++){
                EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
                BlockPos pos = WorldUtil.getCoordsFromSide(side, this.getPos(), 0);
                TileEntity tile = this.worldObj.getTileEntity(pos);

                if(tile instanceof TileEntityLaserRelayItem){
                    if(tileFound != null){
                        return null;
                    }
                    else{
                        tileFound = (TileEntityLaserRelayItem)tile;
                    }
                }
            }
        }
        return tileFound;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction){
        return this.isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(index);
        if(handler != null){
            if(this.isWhitelisted(handler, stack)){
                if(ItemStack.areItemsEqual(handler.handler.getStackInSlot(handler.switchedIndex), stack)){
                    ItemStack gaveBack = handler.handler.extractItem(handler.switchedIndex, stack.stackSize, true);
                    return gaveBack != null;
                }
            }
        }
        return false;
    }

    private boolean isWhitelisted(SpecificItemHandlerInfo handler, ItemStack stack){
     return handler.relayInQuestion.isWhitelisted(stack) && this.getConnectedRelay().isWhitelisted(stack);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        SpecificItemHandlerInfo handler = this.getSwitchedIndexHandler(index);
        if(handler != null){
            if(this.isWhitelisted(handler, stack)){
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

        public IItemHandler handler;
        public int switchedIndex;
        public TileEntityLaserRelayItem relayInQuestion;

        public SpecificItemHandlerInfo(IItemHandler handler, int switchedIndex, TileEntityLaserRelayItem relayInQuestion){
            this.handler = handler;
            this.switchedIndex = switchedIndex;
            this.relayInQuestion = relayInQuestion;
        }
    }

    public static class GenericItemHandlerInfo{

        public List<IItemHandler> handlers = new ArrayList<IItemHandler>();
        public TileEntityLaserRelayItem relayInQuestion;

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
    }
}
