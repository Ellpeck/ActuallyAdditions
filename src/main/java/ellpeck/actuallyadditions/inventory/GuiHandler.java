/*
 * This file ("GuiHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.inventory.gui.*;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler{

    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z){
        if(id == GuiTypes.BOOK.ordinal()) return null;

        TileEntityBase tile = null;
        if(id != GuiTypes.CRAFTER.ordinal() && id != GuiTypes.DRILL.ordinal()){
            tile = (TileEntityBase)world.getTileEntity(x, y, z);
        }
        switch(GuiTypes.values()[id]){
            case FEEDER:
                return new ContainerFeeder(entityPlayer.inventory, tile);
            case GIANT_CHEST:
                return new ContainerGiantChest(entityPlayer.inventory, tile);
            case CRAFTER:
                return new ContainerCrafter(entityPlayer);
            case GRINDER:
                return new ContainerGrinder(entityPlayer.inventory, tile, false);
            case GRINDER_DOUBLE:
                return new ContainerGrinder(entityPlayer.inventory, tile, true);
            case FURNACE_DOUBLE:
                return new ContainerFurnaceDouble(entityPlayer.inventory, tile);
            case INPUTTER:
                return new ContainerInputter(entityPlayer.inventory, tile, false);
            case INPUTTER_ADVANCED:
                return new ContainerInputter(entityPlayer.inventory, tile, true);
            case REPAIRER:
                return new ContainerRepairer(entityPlayer.inventory, tile);
            case BREAKER:
                return new ContainerBreaker(entityPlayer.inventory, tile);
            case DROPPER:
                return new ContainerDropper(entityPlayer.inventory, tile);
            case CANOLA_PRESS:
                return new ContainerCanolaPress(entityPlayer.inventory, tile);
            case FERMENTING_BARREL:
                return new ContainerFermentingBarrel(entityPlayer.inventory, tile);
            case COAL_GENERATOR:
                return new ContainerCoalGenerator(entityPlayer.inventory, tile);
            case OIL_GENERATOR:
                return new ContainerOilGenerator(entityPlayer.inventory, tile);
            case PHANTOM_PLACER:
                return new ContainerPhantomPlacer(entityPlayer.inventory, tile);
            case FLUID_COLLECTOR:
                return new ContainerFluidCollector(entityPlayer.inventory, tile);
            case COFFEE_MACHINE:
                return new ContainerCoffeeMachine(entityPlayer.inventory, tile);
            case DRILL:
                return new ContainerDrill(entityPlayer.inventory);
            case ENERGIZER:
                return new ContainerEnergizer(entityPlayer, tile);
            case ENERVATOR:
                return new ContainerEnervator(entityPlayer, tile);
            case XP_SOLIDIFIER:
                return new ContainerXPSolidifier(entityPlayer.inventory, tile);
            case ORE_MAGNET:
                return new ContainerOreMagnet(entityPlayer.inventory, tile);
            case CLOUD:
                return new ContainerSmileyCloud();
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z){
        TileEntityBase tile = null;
        if(id != GuiTypes.CRAFTER.ordinal() && id != GuiTypes.DRILL.ordinal()){
            tile = (TileEntityBase)world.getTileEntity(x, y, z);
        }
        switch(GuiTypes.values()[id]){
            case FEEDER:
                return new GuiFeeder(entityPlayer.inventory, tile);
            case GIANT_CHEST:
                return new GuiGiantChest(entityPlayer.inventory, tile);
            case CRAFTER:
                return new GuiCrafter(entityPlayer);
            case GRINDER:
                return new GuiGrinder(entityPlayer.inventory, tile);
            case GRINDER_DOUBLE:
                return new GuiGrinder.GuiGrinderDouble(entityPlayer.inventory, tile);
            case FURNACE_DOUBLE:
                return new GuiFurnaceDouble(entityPlayer.inventory, tile);
            case INPUTTER:
                return new GuiInputter(entityPlayer.inventory, tile, x, y, z, world, false);
            case INPUTTER_ADVANCED:
                return new GuiInputter(entityPlayer.inventory, tile, x, y, z, world, true);
            case REPAIRER:
                return new GuiRepairer(entityPlayer.inventory, tile);
            case BREAKER:
                return new GuiBreaker(entityPlayer.inventory, tile);
            case DROPPER:
                return new GuiDropper(entityPlayer.inventory, tile);
            case CANOLA_PRESS:
                return new GuiCanolaPress(entityPlayer.inventory, tile);
            case FERMENTING_BARREL:
                return new GuiFermentingBarrel(entityPlayer.inventory, tile);
            case COAL_GENERATOR:
                return new GuiCoalGenerator(entityPlayer.inventory, tile);
            case OIL_GENERATOR:
                return new GuiOilGenerator(entityPlayer.inventory, tile);
            case PHANTOM_PLACER:
                return new GuiPhantomPlacer(entityPlayer.inventory, tile);
            case FLUID_COLLECTOR:
                return new GuiFluidCollector(entityPlayer.inventory, tile);
            case COFFEE_MACHINE:
                return new GuiCoffeeMachine(entityPlayer.inventory, tile, x, y, z, world);
            case DRILL:
                return new GuiDrill(entityPlayer.inventory);
            case ENERGIZER:
                return new GuiEnergizer(entityPlayer, tile);
            case ENERVATOR:
                return new GuiEnervator(entityPlayer, tile);
            case XP_SOLIDIFIER:
                return new GuiXPSolidifier(entityPlayer.inventory, tile, x, y, z, world);
            case ORE_MAGNET:
                return new GuiOreMagnet(entityPlayer.inventory, tile);
            case CLOUD:
                return new GuiSmileyCloud(tile, x, y, z, world);
            case BOOK:
                return new GuiBooklet(entityPlayer);
            default:
                return null;
        }
    }

    public enum GuiTypes{
        FEEDER,
        GIANT_CHEST,
        CRAFTER,
        GRINDER,
        GRINDER_DOUBLE,
        FURNACE_DOUBLE,
        INPUTTER,
        REPAIRER,
        INPUTTER_ADVANCED,
        BREAKER,
        DROPPER,
        CANOLA_PRESS,
        FERMENTING_BARREL,
        COAL_GENERATOR,
        OIL_GENERATOR,
        PHANTOM_PLACER,
        FLUID_COLLECTOR,
        COFFEE_MACHINE,
        DRILL,
        ENERGIZER,
        ENERVATOR,
        XP_SOLIDIFIER,
        ORE_MAGNET,
        CLOUD,
        BOOK
    }

    public static void init(){
        ModUtil.LOGGER.info("Initializing GuiHandler...");
        NetworkRegistry.INSTANCE.registerGuiHandler(ActuallyAdditions.instance, new GuiHandler());
    }
}