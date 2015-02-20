package ellpeck.someprettyrandomstuff.inventory;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import ellpeck.someprettyrandomstuff.SomePrettyRandomStuff;
import ellpeck.someprettyrandomstuff.tile.TileEntityBase;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler{

    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z){
        TileEntityBase tile = (TileEntityBase)world.getTileEntity(x, y, z);
        switch (id){
            case FEEDER_ID:
                return new ContainerFeeder(entityPlayer.inventory, tile);
            case GIANT_CHEST_ID:
                return new ContainerGiantChest(entityPlayer.inventory, tile);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z){
        TileEntityBase tile = (TileEntityBase)world.getTileEntity(x, y, z);
        switch (id){
            case FEEDER_ID:
                return new GuiFeeder(entityPlayer.inventory, tile);
            case GIANT_CHEST_ID:
                return new GuiGiantChest(entityPlayer.inventory, tile);
            default:
                return null;
        }
    }

    public static final int FEEDER_ID = 0;
    public static final int GIANT_CHEST_ID = 1;

    public static void init(){
        Util.logInfo("Initializing GuiHandler...");
        NetworkRegistry.INSTANCE.registerGuiHandler(SomePrettyRandomStuff.instance, new GuiHandler());
    }
}