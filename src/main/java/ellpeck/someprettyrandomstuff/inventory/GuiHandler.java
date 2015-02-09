package ellpeck.someprettyrandomstuff.inventory;

import ellpeck.someprettyrandomstuff.SPRS;
import ellpeck.someprettyrandomstuff.tile.TileEntityBase;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler{

    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        TileEntityBase tile = (TileEntityBase)world.getTileEntity(new BlockPos(x, y, z));
        switch (id) {
            default:
                return null;
        }
    }

    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        TileEntityBase tile = (TileEntityBase)world.getTileEntity(new BlockPos(x, y, z));
        switch (id) {
            default:
                return null;
        }
    }

    public static void init(){
        Util.logInfo("Initializing GuiHandler...");
        NetworkRegistry.INSTANCE.registerGuiHandler(SPRS.instance, new GuiHandler());
    }
}
