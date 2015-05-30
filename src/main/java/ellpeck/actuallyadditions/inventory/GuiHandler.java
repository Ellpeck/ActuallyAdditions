package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityGrinder;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler{

    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z){
        switch(id){
            case FEEDER_ID:
                TileEntityBase tileFeeder = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerFeeder(entityPlayer.inventory, tileFeeder);
            case GIANT_CHEST_ID:
                TileEntityBase tileChest = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerGiantChest(entityPlayer.inventory, tileChest);
            case CRAFTER_ID:
                return new ContainerCrafter(entityPlayer);
            case GRINDER_ID:
                TileEntityBase tileGrinder = (TileEntityGrinder)world.getTileEntity(x, y, z);
                return new ContainerGrinder(entityPlayer.inventory, tileGrinder, false);
            case GRINDER_DOUBLE_ID:
                TileEntityBase tileGrinderDouble = (TileEntityGrinder)world.getTileEntity(x, y, z);
                return new ContainerGrinder(entityPlayer.inventory, tileGrinderDouble, true);
            case FURNACE_DOUBLE_ID:
                TileEntityBase tileFurnace = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerFurnaceDouble(entityPlayer.inventory, tileFurnace);
            case INPUTTER_ID:
                TileEntityBase tileInputter = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerInputter(entityPlayer.inventory, tileInputter, false);
            case INPUTTER_ADVANCED_ID:
                TileEntityBase tileInputterAdvanced = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerInputter(entityPlayer.inventory, tileInputterAdvanced, true);
            case REPAIRER_ID:
                TileEntityBase tileRepairer = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerRepairer(entityPlayer.inventory, tileRepairer);
            case BREAKER_ID:
                TileEntityBase tileBreaker = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerBreaker(entityPlayer.inventory, tileBreaker);
            case DROPPER_ID:
                TileEntityBase tileDropper = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerDropper(entityPlayer.inventory, tileDropper);
            case CANOLA_PRESS_ID:
                TileEntityBase tilePress = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerCanolaPress(entityPlayer.inventory, tilePress);
            case FERMENTING_BARREL_ID:
                TileEntityBase tileBarrel = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerFermentingBarrel(entityPlayer.inventory, tileBarrel);
            case COAL_GENERATOR_ID:
                TileEntityBase tileGenerator = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerCoalGenerator(entityPlayer.inventory, tileGenerator);
            case OIL_GENERATOR_ID:
                TileEntityBase tileOilGen = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerOilGenerator(entityPlayer.inventory, tileOilGen);
            case PHANTOM_PLACER_ID:
                TileEntityBase tilePlacer = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerPhantomPlacer(entityPlayer.inventory, tilePlacer);
            case FLUID_COLLECTOR_ID:
                TileEntityBase tileCollector = (TileEntityBase)world.getTileEntity(x, y, z);
                return new ContainerFluidCollector(entityPlayer.inventory, tileCollector);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z){
        switch(id){
            case FEEDER_ID:
                TileEntityBase tileFeeder = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiFeeder(entityPlayer.inventory, tileFeeder);
            case GIANT_CHEST_ID:
                TileEntityBase tileChest = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiGiantChest(entityPlayer.inventory, tileChest);
            case CRAFTER_ID:
                return new GuiCrafter(entityPlayer);
            case GRINDER_ID:
                TileEntityBase tileGrinder = (TileEntityGrinder)world.getTileEntity(x, y, z);
                return new GuiGrinder(entityPlayer.inventory, tileGrinder, false);
            case GRINDER_DOUBLE_ID:
                TileEntityBase tileGrinderDouble = (TileEntityGrinder)world.getTileEntity(x, y, z);
                return new GuiGrinder(entityPlayer.inventory, tileGrinderDouble, true);
            case FURNACE_DOUBLE_ID:
                TileEntityBase tileFurnace = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiFurnaceDouble(entityPlayer.inventory, tileFurnace);
            case INPUTTER_ID:
                TileEntityBase tileInputter = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiInputter(entityPlayer.inventory, tileInputter, x, y, z, world, false);
            case INPUTTER_ADVANCED_ID:
                TileEntityBase tileInputterAdvanced = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiInputter(entityPlayer.inventory, tileInputterAdvanced, x, y, z, world, true);
            case REPAIRER_ID:
                TileEntityBase tileRepairer = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiRepairer(entityPlayer.inventory, tileRepairer);
            case BREAKER_ID:
                TileEntityBase tileBreaker = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiBreaker(entityPlayer.inventory, tileBreaker);
            case DROPPER_ID:
                TileEntityBase tileDropper = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiDropper(entityPlayer.inventory, tileDropper);
            case CANOLA_PRESS_ID:
                TileEntityBase tilePress = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiCanolaPress(entityPlayer.inventory, tilePress);
            case FERMENTING_BARREL_ID:
                TileEntityBase tileBarrel = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiFermentingBarrel(entityPlayer.inventory, tileBarrel);
            case COAL_GENERATOR_ID:
                TileEntityBase tileGenerator = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiCoalGenerator(entityPlayer.inventory, tileGenerator);
            case OIL_GENERATOR_ID:
                TileEntityBase tileOilGen = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiOilGenerator(entityPlayer.inventory, tileOilGen);
            case PHANTOM_PLACER_ID:
                TileEntityBase tilePlacer = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiPhantomPlacer(entityPlayer.inventory, tilePlacer);
            case FLUID_COLLECTOR_ID:
                TileEntityBase tileCollector = (TileEntityBase)world.getTileEntity(x, y, z);
                return new GuiFluidCollector(entityPlayer.inventory, tileCollector);
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

    public static void init(){
        Util.logInfo("Initializing GuiHandler...");
        NetworkRegistry.INSTANCE.registerGuiHandler(ActuallyAdditions.instance, new GuiHandler());
    }
}