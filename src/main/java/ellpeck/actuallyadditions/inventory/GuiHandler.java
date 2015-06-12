package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.inventory.gui.*;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler{

    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z){
        TileEntityBase tile = null;
        if(id != CRAFTER_ID){
            tile = (TileEntityBase)world.getTileEntity(x, y, z);
        }
        switch(id){
            case FEEDER_ID:
                return new ContainerFeeder(entityPlayer.inventory, tile);
            case GIANT_CHEST_ID:
                return new ContainerGiantChest(entityPlayer.inventory, tile);
            case CRAFTER_ID:
                return new ContainerCrafter(entityPlayer);
            case GRINDER_ID:
                return new ContainerGrinder(entityPlayer.inventory, tile, false);
            case GRINDER_DOUBLE_ID:
                return new ContainerGrinder(entityPlayer.inventory, tile, true);
            case FURNACE_DOUBLE_ID:
                return new ContainerFurnaceDouble(entityPlayer.inventory, tile);
            case INPUTTER_ID:
                return new ContainerInputter(entityPlayer.inventory, tile, false);
            case INPUTTER_ADVANCED_ID:
                return new ContainerInputter(entityPlayer.inventory, tile, true);
            case REPAIRER_ID:
                return new ContainerRepairer(entityPlayer.inventory, tile);
            case BREAKER_ID:
                return new ContainerBreaker(entityPlayer.inventory, tile);
            case DROPPER_ID:
                return new ContainerDropper(entityPlayer.inventory, tile);
            case CANOLA_PRESS_ID:
                return new ContainerCanolaPress(entityPlayer.inventory, tile);
            case FERMENTING_BARREL_ID:
                return new ContainerFermentingBarrel(entityPlayer.inventory, tile);
            case COAL_GENERATOR_ID:
                return new ContainerCoalGenerator(entityPlayer.inventory, tile);
            case OIL_GENERATOR_ID:
                return new ContainerOilGenerator(entityPlayer.inventory, tile);
            case PHANTOM_PLACER_ID:
                return new ContainerPhantomPlacer(entityPlayer.inventory, tile);
            case FLUID_COLLECTOR_ID:
                return new ContainerFluidCollector(entityPlayer.inventory, tile);
            case COFFEE_MACHINE_ID:
                return new ContainerCoffeeMachine(entityPlayer.inventory, tile);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z){
        TileEntityBase tile = null;
        if(id != CRAFTER_ID){
            tile = (TileEntityBase)world.getTileEntity(x, y, z);
        }
        switch(id){
            case FEEDER_ID:
                return new GuiFeeder(entityPlayer.inventory, tile);
            case GIANT_CHEST_ID:
                return new GuiGiantChest(entityPlayer.inventory, tile);
            case CRAFTER_ID:
                return new GuiCrafter(entityPlayer);
            case GRINDER_ID:
                return new GuiGrinder(entityPlayer.inventory, tile, false);
            case GRINDER_DOUBLE_ID:
                return new GuiGrinder(entityPlayer.inventory, tile, true);
            case FURNACE_DOUBLE_ID:
                return new GuiFurnaceDouble(entityPlayer.inventory, tile);
            case INPUTTER_ID:
                return new GuiInputter(entityPlayer.inventory, tile, x, y, z, world, false);
            case INPUTTER_ADVANCED_ID:
                return new GuiInputter(entityPlayer.inventory, tile, x, y, z, world, true);
            case REPAIRER_ID:
                return new GuiRepairer(entityPlayer.inventory, tile);
            case BREAKER_ID:
                return new GuiBreaker(entityPlayer.inventory, tile);
            case DROPPER_ID:
                return new GuiDropper(entityPlayer.inventory, tile);
            case CANOLA_PRESS_ID:
                return new GuiCanolaPress(entityPlayer.inventory, tile);
            case FERMENTING_BARREL_ID:
                return new GuiFermentingBarrel(entityPlayer.inventory, tile);
            case COAL_GENERATOR_ID:
                return new GuiCoalGenerator(entityPlayer.inventory, tile);
            case OIL_GENERATOR_ID:
                return new GuiOilGenerator(entityPlayer.inventory, tile);
            case PHANTOM_PLACER_ID:
                return new GuiPhantomPlacer(entityPlayer.inventory, tile);
            case FLUID_COLLECTOR_ID:
                return new GuiFluidCollector(entityPlayer.inventory, tile);
            case COFFEE_MACHINE_ID:
                return new GuiCoffeeMachine(entityPlayer.inventory, tile, x, y, z, world);
            default:
                return null;
        }
    }

    public static final int FEEDER_ID = 0;
    public static final int GIANT_CHEST_ID = 1;
    public static final int CRAFTER_ID = 2;
    public static final int GRINDER_ID = 3;
    public static final int GRINDER_DOUBLE_ID = 4;
    public static final int FURNACE_DOUBLE_ID = 5;
    public static final int INPUTTER_ID = 6;
    public static final int REPAIRER_ID = 7;
    public static final int INPUTTER_ADVANCED_ID = 8;
    public static final int BREAKER_ID = 9;
    public static final int DROPPER_ID = 10;
    public static final int CANOLA_PRESS_ID = 11;
    public static final int FERMENTING_BARREL_ID = 12;
    public static final int COAL_GENERATOR_ID = 13;
    public static final int OIL_GENERATOR_ID = 14;
    public static final int PHANTOM_PLACER_ID = 15;
    public static final int FLUID_COLLECTOR_ID = 16;
    public static final int COFFEE_MACHINE_ID = 17;

    public static void init(){
        Util.logInfo("Initializing GuiHandler...");
        NetworkRegistry.INSTANCE.registerGuiHandler(ActuallyAdditions.instance, new GuiHandler());
    }
}