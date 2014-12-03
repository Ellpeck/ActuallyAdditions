package ellpeck.thingycraft.container;

import cpw.mods.fml.common.network.IGuiHandler;
import ellpeck.thingycraft.ThingyCraft;
import ellpeck.thingycraft.tile.TileEntityCrucible;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch (id) {
            case ThingyCraft.guiCrucible:
                TileEntityCrucible tileCrucible = (TileEntityCrucible) world.getTileEntity(x, y, z);
                return new ContainerCrucible(entityPlayer.inventory, tileCrucible);

            default:
                return null;
        }
    }

    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        switch (id) {
            case ThingyCraft.guiCrucible:
                TileEntityCrucible tileCrucible = (TileEntityCrucible) world.getTileEntity(x, y, z);
                return new GuiCrucible(entityPlayer.inventory, tileCrucible);

            default:
                return null;
        }
    }
}
