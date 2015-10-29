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
import ellpeck.actuallyadditions.misc.LaserRelayConnectionHandler;
import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityLaserRelay extends TileEntityBase implements IEnergyReceiver{

    @Override
    public void updateEntity(){
        if(this.worldObj.isRemote){
            this.renderParticles();
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(this.worldObj.rand.nextInt(2) == 0){
            WorldPos thisPos = new WorldPos(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord);
            ArrayList<LaserRelayConnectionHandler.ConnectionPair> network = LaserRelayConnectionHandler.getInstance().getNetworkFor(thisPos);
            if(network != null){
                for(int i1 = 0; i1 < network.size(); i1++){
                    LaserRelayConnectionHandler.ConnectionPair aPair = network.get(i1);
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
        ArrayList<LaserRelayConnectionHandler.ConnectionPair> connections = LaserRelayConnectionHandler.getInstance().getConnectionsFor(thisPos);

        if(connections != null){
            compound.setInteger("ConnectionAmount", connections.size());
            for(int i = 0; i < connections.size(); i++){
                connections.get(i).writeToNBT(compound, "Connection"+i);
            }
            return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, compound);
        }
        return null;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        NBTTagCompound compound = pkt.func_148857_g();

        LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord));

        int amount = compound.getInteger("ConnectionAmount");
        for(int i = 0; i < amount; i++){
            LaserRelayConnectionHandler.ConnectionPair pair = LaserRelayConnectionHandler.ConnectionPair.readFromNBT(compound, "Connection"+i);
            LaserRelayConnectionHandler.getInstance().addConnection(pair.firstRelay, pair.secondRelay);
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.transmitEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return 0;
    }

    public int transmitEnergy(int maxTransmit, boolean simulate){
        int transmitted = 0;
        if(maxTransmit > 0){
            ArrayList<LaserRelayConnectionHandler.ConnectionPair> network = LaserRelayConnectionHandler.getInstance().getNetworkFor(new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
            if(network != null){
                transmitted = LaserRelayConnectionHandler.getInstance().transferEnergyToReceiverInNeed(network, maxTransmit, simulate);
            }
        }
        return transmitted;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }
}
