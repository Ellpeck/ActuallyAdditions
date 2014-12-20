package ellpeck.gemification.inventory;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import ellpeck.gemification.Gemification;
import ellpeck.gemification.booklet.ContainerInfoBook;
import ellpeck.gemification.booklet.GuiInfoBook;
import ellpeck.gemification.inventory.container.ContainerCrucible;
import ellpeck.gemification.inventory.container.ContainerCrucibleFire;
import ellpeck.gemification.inventory.gui.GuiCrucible;
import ellpeck.gemification.inventory.gui.GuiCrucibleFire;
import ellpeck.gemification.tile.TileEntityBase;
import ellpeck.gemification.tile.TileEntityCrucible;
import ellpeck.gemification.tile.TileEntityCrucibleFire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public static final int guiCrucible = 0;
    public static final int guiCrucibleFire = 1;
    public static final int guiInfoBook = 2;

    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        TileEntityBase tile = (TileEntityBase)world.getTileEntity(x, y, z);
        switch (id) {
            case guiCrucible:
                return new ContainerCrucible(entityPlayer.inventory, (TileEntityCrucible)tile);
            case guiCrucibleFire:
                return new ContainerCrucibleFire(entityPlayer.inventory, (TileEntityCrucibleFire)tile);
            case guiInfoBook:
                return new ContainerInfoBook(entityPlayer);

            default:
                return null;
        }
    }

    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        TileEntityBase tile = (TileEntityBase)world.getTileEntity(x, y, z);
        switch (id) {
            case guiCrucible:
                return new GuiCrucible(entityPlayer.inventory, (TileEntityCrucible)tile);
            case guiCrucibleFire:
                return new GuiCrucibleFire(entityPlayer.inventory, (TileEntityCrucibleFire)tile);
            case guiInfoBook:
                return new GuiInfoBook(entityPlayer);

            default:
                return null;
        }
    }

    public static void init(){
        NetworkRegistry.INSTANCE.registerGuiHandler(Gemification.instance, new GuiHandler());
    }
}
