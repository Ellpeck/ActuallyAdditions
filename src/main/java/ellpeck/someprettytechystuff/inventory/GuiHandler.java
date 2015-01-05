package ellpeck.someprettytechystuff.inventory;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import ellpeck.someprettytechystuff.SPTS;
import ellpeck.someprettytechystuff.tile.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        TileEntityBase tile = (TileEntityBase)world.getTileEntity(x, y, z);
        switch (id) {
            default:
                return null;
        }
    }

    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        TileEntityBase tile = (TileEntityBase)world.getTileEntity(x, y, z);
        switch (id) {
            default:
                return null;
        }
    }

    public static void init(){
        NetworkRegistry.INSTANCE.registerGuiHandler(SPTS.instance, new GuiHandler());
    }
}
