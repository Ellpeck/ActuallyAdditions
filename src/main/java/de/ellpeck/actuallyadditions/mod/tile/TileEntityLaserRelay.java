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
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
    }

    @Override
    public NBTTagCompound getSyncCompound(){
        NBTTagCompound compound = new NBTTagCompound();

        BlockPos thisPos = this.pos;
        ConcurrentSet<LaserRelayConnectionHandler.ConnectionPair> connections = LaserRelayConnectionHandler.getInstance().getConnectionsFor(thisPos);

        if(connections != null){
            NBTTagList list = new NBTTagList();
            for(LaserRelayConnectionHandler.ConnectionPair pair : connections){
                list.appendTag(pair.writeToNBT());
            }
            compound.setTag("Connections", list);
            return compound;
        }
        return null;
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
        if(Util.RANDOM.nextInt(ConfigValues.lessParticles ? 8 : 3) == 0){
            BlockPos thisPos = this.pos;
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(thisPos);
            if(network != null){
                for(LaserRelayConnectionHandler.ConnectionPair aPair : network.connections){
                    if(aPair.contains(thisPos) && PosUtil.areSamePos(thisPos, aPair.firstRelay)){
                        PacketParticle.renderParticlesFromAToB(aPair.firstRelay.getX(), aPair.firstRelay.getY(), aPair.firstRelay.getZ(), aPair.secondRelay.getX(), aPair.secondRelay.getY(), aPair.secondRelay.getZ(), ConfigValues.lessParticles ? 2 : Util.RANDOM.nextInt(6)+1, 0.6F, this.isItem ? COLOR_ITEM : COLOR, 1F);
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

        public TileEntityLaserRelayItem(){
            super("laserRelayItem", true);
        }

        public List<IItemHandler> getItemHandlersInNetwork(LaserRelayConnectionHandler.Network network){
            List<IItemHandler> handlers = new ArrayList<IItemHandler>();
            for(LaserRelayConnectionHandler.ConnectionPair pair : network.connections){
                BlockPos[] relays = new BlockPos[]{pair.firstRelay, pair.secondRelay};
                for(BlockPos relay : relays){
                    if(relay != null){
                        for(int i = 0; i <= 5; i++){
                            EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
                            BlockPos pos = WorldUtil.getCoordsFromSide(side, relay, 0);
                            TileEntity tile = this.worldObj.getTileEntity(pos);
                            if(tile != null && !(tile instanceof TileEntityItemViewer)){
                                IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
                                if(handler != null && !handlers.contains(handler)){
                                    handlers.add(handler);
                                }
                            }
                        }
                    }
                }
            }
            return handlers;
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
