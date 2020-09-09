package de.ellpeck.actuallyadditions.common.inventory;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.booklet.gui.GuiMainPage;
import de.ellpeck.actuallyadditions.booklet.misc.BookletUtils;
import de.ellpeck.actuallyadditions.common.data.PlayerData;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiBag;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiBioReactor;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiBreaker;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiCanolaPress;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiCoalGenerator;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiCoffeeMachine;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiDirectionalBreaker;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiDrill;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiDropper;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiEnergizer;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiEnervator;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiFarmer;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiFeeder;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiFermentingBarrel;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiFilter;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiFluidCollector;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiFurnaceDouble;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiGiantChest;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiGrinder;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiInputter;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiLaserRelayItemWhitelist;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiMiner;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiOilGenerator;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiPhantomPlacer;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiRangedCollector;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiRepairer;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiSmileyCloud;
import de.ellpeck.actuallyadditions.common.inventory.gui.GuiXPSolidifier;
import de.ellpeck.actuallyadditions.common.items.ItemBooklet;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.common.util.compat.CompatUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {

    public static void init() {
        ActuallyAdditions.LOGGER.info("Initializing GuiHandler...");
        NetworkRegistry.INSTANCE.registerGuiHandler(ActuallyAdditions.INSTANCE, new GuiHandler());
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntityBase tile = null;
        if (GuiTypes.values()[id].checkTileEntity) {
            tile = (TileEntityBase) world.getTileEntity(new BlockPos(x, y, z));
        }
        switch (GuiTypes.values()[id]) {
        case FEEDER:
            return new ContainerFeeder(player.inventory, tile);
        case GIANT_CHEST:
            return new ContainerGiantChest(player.inventory, tile, 0);
        case GIANT_CHEST_PAGE_2:
            return new ContainerGiantChest(player.inventory, tile, 1);
        case GIANT_CHEST_PAGE_3:
            return new ContainerGiantChest(player.inventory, tile, 2);
        case CRAFTER:
            return CompatUtil.getCrafterContainerElement(player, world, x, y, z);
        case GRINDER:
            return new ContainerGrinder(player.inventory, tile, false);
        case GRINDER_DOUBLE:
            return new ContainerGrinder(player.inventory, tile, true);
        case FURNACE_DOUBLE:
            return new ContainerFurnaceDouble(player.inventory, tile);
        case INPUTTER:
            return new ContainerInputter(player.inventory, tile, false);
        case INPUTTER_ADVANCED:
            return new ContainerInputter(player.inventory, tile, true);
        case REPAIRER:
            return new ContainerRepairer(player.inventory, tile);
        case BREAKER:
            return new ContainerBreaker(player.inventory, tile);
        case DROPPER:
            return new ContainerDropper(player, tile);
        case CANOLA_PRESS:
            return new ContainerCanolaPress(player.inventory, tile);
        case FERMENTING_BARREL:
            return new ContainerFermentingBarrel(player.inventory, tile);
        case COAL_GENERATOR:
            return new ContainerCoalGenerator(player.inventory, tile);
        case OIL_GENERATOR:
            return new ContainerOilGenerator(player.inventory, tile);
        case PHANTOM_PLACER:
            return new ContainerPhantomPlacer(player.inventory, tile);
        case FLUID_COLLECTOR:
            return new ContainerFluidCollector(player.inventory, tile);
        case COFFEE_MACHINE:
            return new ContainerCoffeeMachine(player.inventory, tile);
        case DRILL:
            return new ContainerDrill(player.inventory);
        case FILTER:
            return new ContainerFilter(player.inventory);
        case ENERGIZER:
            return new ContainerEnergizer(player, tile);
        case ENERVATOR:
            return new ContainerEnervator(player, tile);
        case XP_SOLIDIFIER:
            return new ContainerXPSolidifier(player.inventory, tile);
        case CLOUD:
            return new ContainerSmileyCloud();
        case DIRECTIONAL_BREAKER:
            return new ContainerDirectionalBreaker(player.inventory, tile);
        case RANGED_COLLECTOR:
            return new ContainerRangedCollector(player.inventory, tile);
        case MINER:
            return new ContainerMiner(player.inventory, tile);
        case LASER_RELAY_ITEM_WHITELIST:
            return new ContainerLaserRelayItemWhitelist(player.inventory, tile);
        case BAG:
            return new ContainerBag(player.getHeldItemMainhand(), player.inventory, false);
        case VOID_BAG:
            return new ContainerBag(player.getHeldItemMainhand(), player.inventory, true);
        case BIO_REACTOR:
            return new ContainerBioReactor(player.inventory, tile);
        case FARMER:
            return new ContainerFarmer(player.inventory, tile);
        case FIREWORK_BOX:
            return new ContainerFireworkBox();
        default:
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntityBase tile = null;
        if (GuiTypes.values()[id].checkTileEntity) {
            tile = (TileEntityBase) world.getTileEntity(new BlockPos(x, y, z));
        }
        switch (GuiTypes.values()[id]) {
        case FEEDER:
            return new GuiFeeder(player.inventory, tile);
        case GIANT_CHEST:
            return new GuiGiantChest(player.inventory, tile, 0);
        case GIANT_CHEST_PAGE_2:
            return new GuiGiantChest(player.inventory, tile, 1);
        case GIANT_CHEST_PAGE_3:
            return new GuiGiantChest(player.inventory, tile, 2);
        case CRAFTER:
            return CompatUtil.getCrafterGuiElement(player, world, x, y, z);
        case GRINDER:
            return new GuiGrinder(player.inventory, tile);
        case GRINDER_DOUBLE:
            return new GuiGrinder.GuiGrinderDouble(player.inventory, tile);
        case FURNACE_DOUBLE:
            return new GuiFurnaceDouble(player.inventory, tile);
        case INPUTTER:
            return new GuiInputter(player.inventory, tile, false);
        case INPUTTER_ADVANCED:
            return new GuiInputter(player.inventory, tile, true);
        case REPAIRER:
            return new GuiRepairer(player.inventory, tile);
        case BREAKER:
            return new GuiBreaker(player.inventory, tile);
        case DROPPER:
            return new GuiDropper(player, tile);
        case CANOLA_PRESS:
            return new GuiCanolaPress(player.inventory, tile);
        case FERMENTING_BARREL:
            return new GuiFermentingBarrel(player.inventory, tile);
        case COAL_GENERATOR:
            return new GuiCoalGenerator(player.inventory, tile);
        case OIL_GENERATOR:
            return new GuiOilGenerator(player.inventory, tile);
        case PHANTOM_PLACER:
            return new GuiPhantomPlacer(player.inventory, tile);
        case FLUID_COLLECTOR:
            return new GuiFluidCollector(player.inventory, tile);
        case COFFEE_MACHINE:
            return new GuiCoffeeMachine(player.inventory, tile);
        case DRILL:
            return new GuiDrill(player.inventory);
        case FILTER:
            return new GuiFilter(player.inventory);
        case ENERGIZER:
            return new GuiEnergizer(player, tile);
        case ENERVATOR:
            return new GuiEnervator(player, tile);
        case XP_SOLIDIFIER:
            return new GuiXPSolidifier(player.inventory, tile);
        case CLOUD:
            return new GuiSmileyCloud(tile, x, y, z, world);
        case BOOK:
            if (ItemBooklet.forcedPage != null) {
                GuiBooklet gui = BookletUtils.createBookletGuiFromPage(null, ItemBooklet.forcedPage);
                ItemBooklet.forcedPage = null;
                return gui;
            } else {
                PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
                if (data.lastOpenBooklet != null) {
                    return data.lastOpenBooklet;
                } else {
                    return new GuiMainPage(null);
                }
            }
        case DIRECTIONAL_BREAKER:
            return new GuiDirectionalBreaker(player.inventory, tile);
        case RANGED_COLLECTOR:
            return new GuiRangedCollector(player.inventory, tile);
        case MINER:
            return new GuiMiner(player.inventory, tile);
        case LASER_RELAY_ITEM_WHITELIST:
            return new GuiLaserRelayItemWhitelist(player.inventory, tile);
        case BAG:
            return new GuiBag(player.getHeldItemMainhand(), player.inventory, false);
        case VOID_BAG:
            return new GuiBag(player.getHeldItemMainhand(), player.inventory, true);
        case BIO_REACTOR:
            return new GuiBioReactor(player.inventory, tile);
        case FARMER:
            return new GuiFarmer(player.inventory, tile);
        case FIREWORK_BOX:
            return new GuiFireworkBox(tile);
        default:
            return null;
        }
    }

    public enum GuiTypes {
        FEEDER,
        GIANT_CHEST,
        GIANT_CHEST_PAGE_2,
        GIANT_CHEST_PAGE_3,
        CRAFTER(false),
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
        DRILL(false),
        ENERGIZER,
        ENERVATOR,
        XP_SOLIDIFIER,
        CLOUD,
        BOOK(false),
        DIRECTIONAL_BREAKER,
        RANGED_COLLECTOR,
        MINER,
        LASER_RELAY_ITEM_WHITELIST,
        FILTER(false),
        BAG(false),
        VOID_BAG(false),
        BIO_REACTOR,
        FARMER,
        FIREWORK_BOX;

        public final boolean checkTileEntity;

        GuiTypes() {
            this(true);
        }

        GuiTypes(boolean checkTileEntity) {
            this.checkTileEntity = checkTileEntity;
        }
    }
}