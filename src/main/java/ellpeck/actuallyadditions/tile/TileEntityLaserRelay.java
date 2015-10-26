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
import ellpeck.actuallyadditions.misc.LaserRelayConnectionHandler;
import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class TileEntityLaserRelay extends TileEntityBase implements IEnergyReceiver{

    private GLColor laserColor = GLColor.RED_PURE;

    @Override
    public void invalidate(){
        super.invalidate();
        if(!worldObj.isRemote){
            LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(new WorldPos(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
        }
    }

    @Override
    public boolean canUpdate(){
        return false;
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

    public void drawLine(WorldPos firstPos, WorldPos secondPos){
        double x = firstPos.getX() - secondPos.getX();
        double y = firstPos.getY() - secondPos.getY() + 1;
        double z = -(firstPos.getZ() - secondPos.getZ());
        double relativePlayerBlockLocation = Minecraft.getMinecraft().thePlayer.posY - firstPos.getY();
        float f;
        if(relativePlayerBlockLocation < 10) f=5;
        else if(relativePlayerBlockLocation < 20 && relativePlayerBlockLocation > 10) f = 4;
        else if(relativePlayerBlockLocation < 30 && relativePlayerBlockLocation > 20) f = 3;
        else if(relativePlayerBlockLocation < 40 && relativePlayerBlockLocation > 30) f = 2;
        else if(relativePlayerBlockLocation < 50 && relativePlayerBlockLocation > 40) f = 1;
        else f=1;
        GL11.glPushMatrix();
        GL11.glLineWidth(f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            GL11.glColor3f(this.laserColor.getRed(), this.laserColor.getGreen(), this.laserColor.getBlue());
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(0, 1, 0);
        }
        GL11.glEnd();
        GL11.glLineWidth(1);
        GL11.glPopMatrix();
    }

    public void changeLineColor(GLColor color){this.laserColor = color;}


    //Colors for the Laser:
    public enum GLColor{

        RED_PURE(1.0F, 0, 0),
        GREEN_PURE(0, 1.0F, 0),
        BLUE_PURE(0, 0, 1.0F),
        DARK_YELLOW(1, 1, 0);

        private float red, green, blue;
        GLColor(float red, float green, float blue){
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public float getRed() {
            return red;
        }

        public float getGreen() {
            return green;
        }

        public float getBlue() {
            return blue;
        }
    }
}
