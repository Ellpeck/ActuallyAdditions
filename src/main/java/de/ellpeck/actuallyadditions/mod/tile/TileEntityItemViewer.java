package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class TileEntityItemViewer extends TileEntityInventoryBase{

    public TileEntityItemViewer(){
        super(0, "itemViewer");
    }

    private List<IItemHandler> getItemHandlers(){
        TileEntityLaserRelay.TileEntityLaserRelayItem relay = this.getConnectedRelay();
        if(relay != null){
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(relay.getPos());
            if(network != null){
                return relay.getItemHandlersInNetwork(network);
            }
        }
        return null;
    }

    private Pair<IItemHandler, Integer> getSwitchedIndexHandler(int i){
        List<IItemHandler> handlers = this.getItemHandlers();
        int currentI = 0;
        if(handlers != null && !handlers.isEmpty()){
            for(IItemHandler handler : handlers){
                int slotAmount = handler.getSlots();
                if(currentI+slotAmount > i){
                    return Pair.of(handler, i-currentI);
                }
                else{
                    currentI += slotAmount;
                }
            }
        }
        return null;
    }

    private TileEntityLaserRelay.TileEntityLaserRelayItem getConnectedRelay(){
        TileEntityLaserRelay.TileEntityLaserRelayItem tileFound = null;
        if(this.worldObj != null){ //Why is that even possible..?
            for(int i = 0; i <= 5; i++){
                EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
                BlockPos pos = WorldUtil.getCoordsFromSide(side, this.getPos(), 0);
                TileEntity tile = this.worldObj.getTileEntity(pos);

                if(tile instanceof TileEntityLaserRelay.TileEntityLaserRelayItem){
                    if(tileFound != null){
                        return null;
                    }
                    else{
                        tileFound = (TileEntityLaserRelay.TileEntityLaserRelayItem)tile;
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
        Pair<IItemHandler, Integer> handler = this.getSwitchedIndexHandler(index);
        if(handler != null){
            if(ItemStack.areItemsEqual(handler.getKey().getStackInSlot(handler.getValue()), stack)){
                ItemStack gaveBack = handler.getKey().extractItem(handler.getValue(), stack.stackSize, true);
                return gaveBack != null;
            }
        }
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack){
        Pair<IItemHandler, Integer> handler = this.getSwitchedIndexHandler(index);
        if(handler != null){
            ItemStack gaveBack = handler.getKey().insertItem(handler.getValue(), stack, true);
            return !ItemStack.areItemStacksEqual(gaveBack, stack);
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
            Pair<IItemHandler, Integer> handler = this.getSwitchedIndexHandler(i);
            if(handler != null){
                ItemStack toInsert = stack.copy();
                ItemStack inSlot = handler.getKey().getStackInSlot(handler.getValue());
                if(inSlot != null){
                    toInsert.stackSize -= inSlot.stackSize;
                }
                handler.getKey().insertItem(handler.getValue(), toInsert, false);
            }
        }
        else{
            this.removeStackFromSlot(i);
        }
    }

    @Override
    public int getSizeInventory(){
        int size = 0;
        List<IItemHandler> handlers = this.getItemHandlers();
        if(handlers != null){
            for(IItemHandler handler : handlers){
                size += handler.getSlots();
            }
        }
        return size;
    }

    @Override
    public ItemStack getStackInSlot(int i){
        Pair<IItemHandler, Integer> handler = this.getSwitchedIndexHandler(i);
        if(handler != null){
            return handler.getKey().getStackInSlot(handler.getValue());
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        Pair<IItemHandler, Integer> handler = this.getSwitchedIndexHandler(i);
        if(handler != null){
            return handler.getKey().extractItem(handler.getValue(), j, false);
        }
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index){
        Pair<IItemHandler, Integer> handler = this.getSwitchedIndexHandler(index);
        if(handler != null){
            ItemStack stackInSlot = handler.getKey().getStackInSlot(handler.getValue());
            if(stackInSlot != null){
                handler.getKey().extractItem(handler.getValue(), stackInSlot.stackSize, false);
            }
            return stackInSlot;
        }
        return null;
    }
}
