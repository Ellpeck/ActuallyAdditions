package ellpeck.someprettyrandomstuff.inventory;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import ellpeck.someprettyrandomstuff.SPRS;
import ellpeck.someprettyrandomstuff.tile.TileEntityBase;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler{

    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z){
        TileEntityBase tile = (TileEntityBase)world.getTileEntity(x, y, z);
        switch (id){
            case FEEDER_ID:
                return new ContainerFeeder(entityPlayer.inventory, tile);
            default:
                return null;
        }
    }

    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z){
        TileEntityBase tile = (TileEntityBase)world.getTileEntity(x, y, z);
        switch (id){
            case FEEDER_ID:
                return new GuiFeeder(entityPlayer.inventory, tile);
            default:
                return null;
        }
    }

    public static final int FEEDER_ID = 0;

    public static void init(){
        Util.logInfo("Initializing GuiHandler...");
        NetworkRegistry.INSTANCE.registerGuiHandler(SPRS.instance, new GuiHandler());
    }
}
