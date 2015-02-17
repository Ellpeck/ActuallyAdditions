package ellpeck.someprettyrandomstuff.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class TileEntityBase extends TileEntity{

    public Packet getDescriptionPacket(){
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), compound);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet){
        super.onDataPacket(net, packet);
        this.readFromNBT(packet.func_148857_g());
    }

    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z){
        return false;
    }

    public static void init(){
        Util.logInfo("Registering TileEntities...");
        GameRegistry.registerTileEntity(TileEntityCompost.class, Util.MOD_ID_LOWER + ":tileEntityCompost");
        GameRegistry.registerTileEntity(TileEntityFeeder.class, Util.MOD_ID_LOWER + ":tileEntityFeeder");
    }
}
