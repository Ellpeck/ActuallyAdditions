/*
 * This file ("TileEntityLaserRelay.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.misc.LaserRelayConnectionHandler;
import ellpeck.actuallyadditions.util.Util;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLaserRelay extends TileEntityBase implements IEnergyReceiver{

    public static final int MAX_DISTANCE = 15;

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(this.worldObj.isRemote){
            this.renderParticles();
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
    }

    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(Util.RANDOM.nextInt(2) == 0){
            WorldPos thisPos = new WorldPos(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord);
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(thisPos);
            if(network != null){
                for(LaserRelayConnectionHandler.ConnectionPair aPair : network.connections){
                    if(aPair.contains(thisPos) && thisPos.isEqual(aPair.firstRelay)){
                        if(Minecraft.getMinecraft().thePlayer.getDistance(aPair.firstRelay.getX(), aPair.firstRelay.getY(), aPair.firstRelay.getZ()) <= 64){
                            int difX = aPair.firstRelay.getX()-aPair.secondRelay.getX();
                            int difY = aPair.firstRelay.getY()-aPair.secondRelay.getY();
                            int difZ = aPair.firstRelay.getZ()-aPair.secondRelay.getZ();

                            double distance = aPair.firstRelay.toVec().distanceTo(aPair.secondRelay.toVec());
                            for(double i = 0; i <= 1; i += 1/(distance*(ConfigBoolValues.LESS_LASER_RELAY_PARTICLES.isEnabled() ? 1 : 5))){
                                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityReddustFX(this.worldObj, (difX*i)+aPair.secondRelay.getX()+0.5, (difY*i)+aPair.secondRelay.getY()+0.5, (difZ*i)+aPair.secondRelay.getZ()+0.5, 0.75F, 0, 0, 0));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound compound = new NBTTagCompound();

        WorldPos thisPos = new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        ConcurrentSet<LaserRelayConnectionHandler.ConnectionPair> connections = LaserRelayConnectionHandler.getInstance().getConnectionsFor(thisPos);

        if(connections != null){
            NBTTagList list = new NBTTagList();
            for(LaserRelayConnectionHandler.ConnectionPair pair : connections){
                list.appendTag(pair.writeToNBT());
            }
            compound.setTag("Connections", list);
            return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, compound);
        }
        return null;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        WorldPos thisPos = new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        if(pkt != null && pkt.func_148857_g() != null){
            LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(thisPos);

            NBTTagList list = pkt.func_148857_g().getTagList("Connections", 10);
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
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.transmitEnergy(WorldUtil.getCoordsFromSide(from, worldObj, xCoord, yCoord, zCoord, 0), maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return 0;
    }

    public int transmitEnergy(WorldPos blockFrom, int maxTransmit, boolean simulate){
        int transmitted = 0;
        if(maxTransmit > 0){
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
            if(network != null){
                transmitted = LaserRelayConnectionHandler.getInstance().transferEnergyToReceiverInNeed(blockFrom, network, Math.min(ConfigIntValues.LASER_RELAY_MAX_TRANSFER.getValue(), maxTransmit), simulate);
            }
        }
        return transmitted;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }
}
