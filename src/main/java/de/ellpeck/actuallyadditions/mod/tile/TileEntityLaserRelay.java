/*
 * This file ("TileEntityLaserRelay.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketParticle;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemViewer.GenericItemHandlerInfo;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class TileEntityLaserRelay extends TileEntityBase{

    public static final int MAX_DISTANCE = 15;
    private static final float[] COLOR = new float[]{1F, 0F, 0F};
    private static final float[] COLOR_ITEM = new float[]{139F/255F, 94F/255F, 1F};

    public boolean isItem;

    public TileEntityLaserRelay(String name, boolean isItem){
        super(name);
        this.isItem = isItem;
    }

    @Override
    public void receiveSyncCompound(NBTTagCompound compound){
        BlockPos thisPos = this.pos;
        if(compound != null){
            LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(thisPos);

            NBTTagList list = compound.getTagList("Connections", 10);
            for(int i = 0; i < list.tagCount(); i++){
                LaserRelayConnectionHandler.ConnectionPair pair = LaserRelayConnectionHandler.ConnectionPair.readFromNBT(list.getCompoundTagAt(i));
                LaserRelayConnectionHandler.getInstance().addConnection(pair.firstRelay, pair.secondRelay);
            }
        }
        else{
            LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(thisPos);
        }

        super.receiveSyncCompound(compound);
    }

    @Override
    public NBTTagCompound getSyncCompound(){
        NBTTagCompound compound = super.getSyncCompound();

        BlockPos thisPos = this.pos;
        ConcurrentSet<LaserRelayConnectionHandler.ConnectionPair> connections = LaserRelayConnectionHandler.getInstance().getConnectionsFor(thisPos);

        if(connections != null){
            NBTTagList list = new NBTTagList();
            for(LaserRelayConnectionHandler.ConnectionPair pair : connections){
                list.appendTag(pair.writeToNBT());
            }
            compound.setTag("Connections", list);
        }
        return compound;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(this.worldObj.isRemote){
            this.renderParticles();
        }
    }

    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(Util.RANDOM.nextInt(ConfigValues.lessParticles ? 16 : 8) == 0){
            BlockPos thisPos = this.pos;
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(thisPos);
            if(network != null){
                for(LaserRelayConnectionHandler.ConnectionPair aPair : network.connections){
                    if(aPair.contains(thisPos) && PosUtil.areSamePos(thisPos, aPair.firstRelay)){
                        PacketParticle.renderParticlesFromAToB(aPair.firstRelay.getX(), aPair.firstRelay.getY(), aPair.firstRelay.getZ(), aPair.secondRelay.getX(), aPair.secondRelay.getY(), aPair.secondRelay.getZ(), ConfigValues.lessParticles ? 1 : Util.RANDOM.nextInt(3)+1, 0.8F, this.isItem ? COLOR_ITEM : COLOR, 1F);
                    }
                }
            }
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(this.pos);
    }

    public static class TileEntityLaserRelayItem extends TileEntityLaserRelay{

        public TileEntityLaserRelayItem(String name){
            super(name, true);
        }

        public TileEntityLaserRelayItem(){
            this("laserRelayItem");
        }

        public boolean isWhitelisted(ItemStack stack){
            return true;
        }

        public List<GenericItemHandlerInfo> getItemHandlersInNetwork(LaserRelayConnectionHandler.Network network){
            List<GenericItemHandlerInfo> handlers = new ArrayList<GenericItemHandlerInfo>();
            for(LaserRelayConnectionHandler.ConnectionPair pair : network.connections){
                BlockPos[] relays = new BlockPos[]{pair.firstRelay, pair.secondRelay};
                for(BlockPos relay : relays){
                    if(relay != null){
                        TileEntity aRelayTile = this.worldObj.getTileEntity(relay);
                        if(aRelayTile instanceof TileEntityLaserRelayItem){
                            TileEntityLaserRelayItem relayTile = (TileEntityLaserRelayItem)aRelayTile;
                            if(!GenericItemHandlerInfo.containsTile(handlers, relayTile)){
                                GenericItemHandlerInfo info = new GenericItemHandlerInfo(relayTile);
                                for(int i = 0; i <= 5; i++){
                                    EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
                                    BlockPos pos = WorldUtil.getCoordsFromSide(side, relay, 0);
                                    TileEntity tile = this.worldObj.getTileEntity(pos);
                                    if(tile != null && !(tile instanceof TileEntityItemViewer)){
                                        IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
                                        if(handler != null && !GenericItemHandlerInfo.containsHandler(handlers, handler)){
                                            info.handlers.add(handler);
                                        }
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

    public static class TileEntityLaserRelayItemWhitelist extends TileEntityLaserRelayItem implements IButtonReactor{

        private ItemStack[] slots = new ItemStack[24];
        
        public IInventory filterInventory;

        public boolean isLeftWhitelist;
        public boolean isRightWhitelist;

        private boolean lastLeftWhitelist;
        private boolean lastRightWhitelist;

        public TileEntityLaserRelayItemWhitelist(){
            super("laserRelayItemWhitelist");

            this.filterInventory = new IInventory(){

                private TileEntityLaserRelayItemWhitelist tile;

                private IInventory setTile(TileEntityLaserRelayItemWhitelist tile){
                    this.tile = tile;
                    return this;
                }

                @Override
                public String getName(){
                    return this.tile.name;
                }

                @Override
                public int getInventoryStackLimit(){
                    return 64;
                }

                @Override
                public void markDirty(){

                }

                @Override
                public boolean isUseableByPlayer(EntityPlayer player){
                    return this.tile.canPlayerUse(player);
                }

                @Override
                public void openInventory(EntityPlayer player){

                }

                @Override
                public void closeInventory(EntityPlayer player){

                }

                @Override
                public int getField(int id){
                    return 0;
                }

                @Override
                public void setField(int id, int value){

                }

                @Override
                public int getFieldCount(){
                    return 0;
                }

                @Override
                public void clear(){
                    int length = this.tile.slots.length;
                    this.tile.slots = new ItemStack[length];
                }

                @Override
                public void setInventorySlotContents(int i, ItemStack stack){
                    this.tile.slots[i] = stack;
                    this.markDirty();
                }

                @Override
                public int getSizeInventory(){
                    return this.tile.slots.length;
                }

                @Override
                public ItemStack getStackInSlot(int i){
                    if(i < this.getSizeInventory()){
                        return this.tile.slots[i];
                    }
                    return null;
                }

                @Override
                public ItemStack decrStackSize(int i, int j){
                    if(this.tile.slots[i] != null){
                        ItemStack stackAt;
                        if(this.tile.slots[i].stackSize <= j){
                            stackAt = this.tile.slots[i];
                            this.tile.slots[i] = null;
                            this.markDirty();
                            return stackAt;
                        }
                        else{
                            stackAt = this.tile.slots[i].splitStack(j);
                            if(this.tile.slots[i].stackSize <= 0){
                                this.tile.slots[i] = null;
                            }
                            this.markDirty();
                            return stackAt;
                        }
                    }
                    return null;
                }

                @Override
                public ItemStack removeStackFromSlot(int index){
                    ItemStack stack = this.tile.slots[index];
                    this.tile.slots[index] = null;
                    return stack;
                }

                @Override
                public boolean hasCustomName(){
                    return false;
                }

                @Override
                public ITextComponent getDisplayName(){
                    return new TextComponentString(StringUtil.localize(this.getName()));
                }

                @Override
                public boolean isItemValidForSlot(int index, ItemStack stack){
                    return false;
                }
            }.setTile(this);
        }

        @Override
        public boolean isWhitelisted(ItemStack stack){
            return this.checkFilter(stack, true, this.isLeftWhitelist) || this.checkFilter(stack, false, this.isRightWhitelist);
        }

        private boolean checkFilter(ItemStack stack, boolean left, boolean isWhitelist){
            int slotStart = left ? 0 : 12;
            int slotStop = slotStart+12;

            for(int i = slotStart; i < slotStop; i++){
                if(this.slots[i] != null && this.slots[i].isItemEqual(stack)){
                    return isWhitelist;
                }
            }
            return !isWhitelist;
        }

        @Override
        public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
            super.writeSyncableNBT(compound, isForSync);
            if(!isForSync){
                TileEntityInventoryBase.saveSlots(this.slots, compound);
            }
            compound.setBoolean("LeftWhitelist", this.isLeftWhitelist);
            compound.setBoolean("RightWhitelist", this.isRightWhitelist);
        }

        @Override
        public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
            super.readSyncableNBT(compound, isForSync);
            if(!isForSync){
                TileEntityInventoryBase.loadSlots(this.slots, compound);
            }
            this.isLeftWhitelist = compound.getBoolean("LeftWhitelist");
            this.isRightWhitelist = compound.getBoolean("RightWhitelist");
        }

        @Override
        public void onButtonPressed(int buttonID, EntityPlayer player){
            if(buttonID == 0){
                this.isLeftWhitelist = !this.isLeftWhitelist;
            }
            else if(buttonID == 1){
                this.isRightWhitelist = !this.isRightWhitelist;
            }
        }

        @Override
        public void updateEntity(){
            super.updateEntity();

            if(!this.worldObj.isRemote){
                if((this.isLeftWhitelist != this.lastLeftWhitelist || this.isRightWhitelist != this.lastRightWhitelist) && this.sendUpdateWithInterval()){
                    this.lastLeftWhitelist = this.isLeftWhitelist;
                    this.lastRightWhitelist = this.isRightWhitelist;
                }
            }
        }
    }

    public static class TileEntityLaserRelayEnergy extends TileEntityLaserRelay implements IEnergyReceiver{

        public TileEntityLaserRelayEnergy(){
            super("laserRelay", false);
        }

        @Override
        public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
            return this.transmitEnergy(WorldUtil.getCoordsFromSide(from, this.pos, 0), maxReceive, simulate);
        }

        @Override
        public int getEnergyStored(EnumFacing from){
            return 0;
        }

        @Override
        public int getMaxEnergyStored(EnumFacing from){
            return 0;
        }

        public int transmitEnergy(BlockPos blockFrom, int maxTransmit, boolean simulate){
            int transmitted = 0;
            if(maxTransmit > 0){
                LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(this.pos);
                if(network != null){
                    transmitted = this.transferEnergyToReceiverInNeed(blockFrom, network, Math.min(ConfigIntValues.LASER_RELAY_MAX_TRANSFER.getValue(), maxTransmit), simulate);
                }
            }
            return transmitted;
        }

        @Override
        public boolean canConnectEnergy(EnumFacing from){
            return true;
        }

        private int transferEnergyToReceiverInNeed(BlockPos energyGottenFrom, LaserRelayConnectionHandler.Network network, int maxTransfer, boolean simulate){
            int transmitted = 0;
            List<BlockPos> alreadyChecked = new ArrayList<BlockPos>();
            //Go through all of the connections in the network
            for(LaserRelayConnectionHandler.ConnectionPair pair : network.connections){
                BlockPos[] relays = new BlockPos[]{pair.firstRelay, pair.secondRelay};
                //Go through both relays in the connection
                for(BlockPos relay : relays){
                    if(relay != null && !alreadyChecked.contains(relay)){
                        alreadyChecked.add(relay);
                        //Get every side of the relay
                        for(int i = 0; i <= 5; i++){
                            EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
                            //Get the Position at the side
                            BlockPos pos = WorldUtil.getCoordsFromSide(side, relay, 0);
                            if(!PosUtil.areSamePos(pos, energyGottenFrom)){
                                TileEntity tile = this.worldObj.getTileEntity(pos);
                                if(tile instanceof IEnergyReceiver && !(tile instanceof TileEntityLaserRelay)){
                                    IEnergyReceiver receiver = (IEnergyReceiver)tile;
                                    if(receiver.canConnectEnergy(side.getOpposite())){
                                        //Transfer the energy (with the energy loss!)
                                        int theoreticalReceived = ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), maxTransfer-transmitted, true);
                                        //The amount of energy lost during a transfer
                                        int deduct = (int)(theoreticalReceived*((double)ConfigIntValues.LASER_RELAY_LOSS.getValue()/100));

                                        transmitted += ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), theoreticalReceived-deduct, simulate);
                                        transmitted += deduct;

                                        //If everything that could be transmitted was transmitted
                                        if(transmitted >= maxTransfer){
                                            return transmitted;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return transmitted;
        }
    }
}
