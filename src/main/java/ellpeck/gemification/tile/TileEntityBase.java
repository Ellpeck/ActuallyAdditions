package ellpeck.gemification.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.gemification.util.Util;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBase extends TileEntity{

    public Packet getDescriptionPacket(){
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), compound);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet){
        super.onDataPacket(net, packet);
        this.readFromNBT(packet.func_148857_g());
    }

    public static void init(){
        GameRegistry.registerTileEntity(TileEntityCrucible.class, Util.MOD_ID + "tileEntityCrucible");
        GameRegistry.registerTileEntity(TileEntityCrucibleFire.class, Util.MOD_ID + "tileEntityCrucibleFire");
    }
}
