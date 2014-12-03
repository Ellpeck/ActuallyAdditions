package ellpeck.gemification.container;

import cpw.mods.fml.common.network.IGuiHandler;
import ellpeck.gemification.Gemification;
import ellpeck.gemification.tile.TileEntityCrucible;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch (id) {
            case Gemification.guiCrucible:
                TileEntityCrucible tileCrucible = (TileEntityCrucible) world.getTileEntity(x, y, z);
                return new ContainerCrucible(entityPlayer.inventory, tileCrucible);

            default:
                return null;
        }
    }

    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch (id) {
            case Gemification.guiCrucible:
                TileEntityCrucible tileCrucible = (TileEntityCrucible) world.getTileEntity(x, y, z);
                return new GuiCrucible(entityPlayer.inventory, tileCrucible);

            default:
                return null;
        }
    }
}
