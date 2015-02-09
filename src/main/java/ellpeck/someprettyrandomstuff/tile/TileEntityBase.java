package ellpeck.someprettyrandomstuff.tile;

import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class TileEntityBase extends TileEntity{

    public Packet getDescriptionPacket(){
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), compound);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet){
        super.onDataPacket(net, packet);
        this.readFromNBT(packet.getNbtCompound());
    }

    public static void init(){
        Util.logInfo("Registering TileEntities...");
        GameRegistry.registerTileEntity(TileEntityCompost.class, Util.MOD_ID + ":tileEntityCompost");
    }
}
