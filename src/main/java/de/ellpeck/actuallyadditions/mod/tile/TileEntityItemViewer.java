/*
 * This file ("TileEntityItemViewer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketServerToClient;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.CommonCapsUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.IItemHandler;
import org.cyclops.commoncapabilities.api.capability.itemhandler.ISlotlessItemHandler;
import org.cyclops.commoncapabilities.capability.itemhandler.SlotlessItemHandlerConfig;

import java.util.*;

public class TileEntityItemViewer extends TileEntityBase{

    public final List<GenericItemHandlerInfo> genericInfos = new ArrayList<GenericItemHandlerInfo>();
    public final Map<Integer, IItemHandlerInfo> itemHandlerInfos = new HashMap<Integer, IItemHandlerInfo>();
    public final List<SlotlessItemHandlerInfo> slotlessInfos = new ArrayList<SlotlessItemHandlerInfo>();
    protected final SlotlessableItemHandlerWrapper itemHandler;
    public TileEntityLaserRelayItem connectedRelay;
    private int lastNetworkChangeAmount = -1;

    public TileEntityItemViewer(String name){
        super(name);

        IItemHandler normalHandler = new IItemHandler(){
            @Override
            public int getSlots(){
                int size = 0;
                List<GenericItemHandlerInfo> infos = TileEntityItemViewer.this.getItemHandlerInfos();
                if(infos != null){
                    for(GenericItemHandlerInfo info : infos){
                        if(info.isLoaded()){
                            for(SlotlessableItemHandlerWrapper handler : info.handlers){
                                IItemHandler normalHandler = handler.getNormalHandler();
                                if(normalHandler != null){
                                    size += normalHandler.getSlots();
                                }
                            }
                        }
                    }
                }
                return size;
            }

            @Override
            public ItemStack getStackInSlot(int slot){
                IItemHandlerInfo handler = TileEntityItemViewer.this.getSwitchedIndexHandler(slot);
                if(handler != null && handler.isLoaded()){
                    return handler.handler.getStackInSlot(handler.switchedIndex);
                }
                return StackUtil.getNull();
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
                IItemHandlerInfo info = TileEntityItemViewer.this.getSwitchedIndexHandler(slot);
                if(info != null && info.isLoaded() && TileEntityItemViewer.this.isWhitelisted(info, stack, false)){
                    ItemStack remain = info.handler.insertItem(info.switchedIndex, stack, simulate);
                    if(!ItemStack.areItemStacksEqual(remain, stack) && !simulate){
                        TileEntityItemViewer.this.markDirty();
                        TileEntityItemViewer.this.doItemParticle(stack, info.relayInQuestion.getPos(), TileEntityItemViewer.this.connectedRelay.getPos());
                    }
                    return remain;
                }
                return stack;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate){
                ItemStack stackIn = this.getStackInSlot(slot);
                if(StackUtil.isValid(stackIn)){
                    IItemHandlerInfo info = TileEntityItemViewer.this.getSwitchedIndexHandler(slot);
                    if(info != null && info.isLoaded() && TileEntityItemViewer.this.isWhitelisted(info, stackIn, true)){
                        ItemStack extracted = info.handler.extractItem(info.switchedIndex, amount, simulate);
                        if(StackUtil.isValid(extracted) && !simulate){
                            TileEntityItemViewer.this.markDirty();
                            TileEntityItemViewer.this.doItemParticle(extracted, TileEntityItemViewer.this.connectedRelay.getPos(), info.relayInQuestion.getPos());
                        }
                        return extracted;
                    }
                }
                return StackUtil.getNull();
            }

            @Override
            public int getSlotLimit(int slot){
                IItemHandlerInfo info = TileEntityItemViewer.this.getSwitchedIndexHandler(slot);
                if(info != null && info.isLoaded()){
                    return info.handler.getSlotLimit(info.switchedIndex);
                }
                else{
                    return 0;
                }
            }
        };

        Object slotlessHandler = null;
        if(ActuallyAdditions.commonCapsLoaded){
            slotlessHandler = CommonCapsUtil.createSlotlessItemViewerHandler(this, normalHandler);
        }

        this.itemHandler = new SlotlessableItemHandlerWrapper(normalHandler, slotlessHandler);
    }

    public TileEntityItemViewer(){
        this("itemViewer");
    }

    @Override
    public IItemHandler getItemHandler(EnumFacing facing){
        return this.itemHandler.getNormalHandler();
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if(ActuallyAdditions.commonCapsLoaded){
            if(capability == SlotlessItemHandlerConfig.CAPABILITY){
                Object handler = this.itemHandler.getSlotlessHandler();
                if(handler != null){
                    return (T)handler;
                }
            }
        }
        return super.getCapability(capability, facing);
    }

    private List<GenericItemHandlerInfo> getItemHandlerInfos(){
        this.queryAndSaveData();
        return this.genericInfos;
    }

    public void doItemParticle(ItemStack stack, BlockPos input, BlockPos output){
        if(!this.world.isRemote){
            NBTTagCompound compound = new NBTTagCompound();
            stack.writeToNBT(compound);

            compound.setDouble("InX", input.getX());
            compound.setDouble("InY", input.getY());
            compound.setDouble("InZ", input.getZ());

            compound.setDouble("OutX", output.getX());
            compound.setDouble("OutY", output.getY());
            compound.setDouble("OutZ", output.getZ());

            PacketHandler.theNetwork.sendToAllAround(new PacketServerToClient(compound, PacketHandler.LASER_PARTICLE_HANDLER), new TargetPoint(this.world.provider.getDimension(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 16));
        }
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
                            for(SlotlessableItemHandlerWrapper handler : info.handlers){
                                IItemHandler normalHandler = handler.getNormalHandler();
                                if(normalHandler != null){
                                    for(int i = 0; i < normalHandler.getSlots(); i++){
                                        this.itemHandlerInfos.put(slotsQueried, new IItemHandlerInfo(normalHandler, i, info.relayInQuestion));
                                        slotsQueried++;
                                    }
                                }

                                if(ActuallyAdditions.commonCapsLoaded){
                                    Object slotlessHandler = handler.getSlotlessHandler();
                                    if(slotlessHandler instanceof ISlotlessItemHandler){
                                        this.slotlessInfos.add(new SlotlessItemHandlerInfo(slotlessHandler, info.relayInQuestion));
                                    }
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

        if(!this.itemHandlerInfos.isEmpty()){
            this.itemHandlerInfos.clear();
        }

        if(!this.slotlessInfos.isEmpty()){
            this.slotlessInfos.clear();
        }
    }

    private IItemHandlerInfo getSwitchedIndexHandler(int i){
        this.queryAndSaveData();
        return this.itemHandlerInfos.get(i);
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

                if(this.world.isBlockLoaded(pos)){
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
        }
        this.connectedRelay = tileFound;
    }

    public boolean isWhitelisted(SpecificItemHandlerInfo handler, ItemStack stack, boolean output){
        boolean whitelisted = handler.relayInQuestion.isWhitelisted(stack, output);
        TileEntityLaserRelayItem connected = this.connectedRelay;
        if(connected != null && connected != handler.relayInQuestion){
            return whitelisted && connected.isWhitelisted(stack, output);
        }
        else{
            return whitelisted;
        }
    }

    public static class SlotlessItemHandlerInfo extends SpecificItemHandlerInfo{

        public final Object handler;

        public SlotlessItemHandlerInfo(Object handler, TileEntityLaserRelayItem relayInQuestion){
            super(relayInQuestion);
            this.handler = handler;
        }
    }

    private static class IItemHandlerInfo extends SpecificItemHandlerInfo{

        public final IItemHandler handler;
        public final int switchedIndex;

        public IItemHandlerInfo(IItemHandler handler, int switchedIndex, TileEntityLaserRelayItem relayInQuestion){
            super(relayInQuestion);
            this.handler = handler;
            this.switchedIndex = switchedIndex;
        }
    }

    private static class SpecificItemHandlerInfo{

        public final TileEntityLaserRelayItem relayInQuestion;

        public SpecificItemHandlerInfo(TileEntityLaserRelayItem relayInQuestion){
            this.relayInQuestion = relayInQuestion;
        }

        public boolean isLoaded(){
            return this.relayInQuestion.hasWorld() && this.relayInQuestion.getWorld().isBlockLoaded(this.relayInQuestion.getPos());
        }
    }

    public static class GenericItemHandlerInfo implements Comparable<GenericItemHandlerInfo>{

        public final List<SlotlessableItemHandlerWrapper> handlers = new ArrayList<SlotlessableItemHandlerWrapper>();
        public final TileEntityLaserRelayItem relayInQuestion;

        public GenericItemHandlerInfo(TileEntityLaserRelayItem relayInQuestion){
            this.relayInQuestion = relayInQuestion;
        }

        public boolean isLoaded(){
            return this.relayInQuestion.hasWorld() && this.relayInQuestion.getWorld().isBlockLoaded(this.relayInQuestion.getPos());
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
