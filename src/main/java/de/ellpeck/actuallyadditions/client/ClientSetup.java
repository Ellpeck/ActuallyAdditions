package de.ellpeck.actuallyadditions.client;

import de.ellpeck.actuallyadditions.client.render.tiles.BatteryBoxTileRender;
import de.ellpeck.actuallyadditions.client.screens.DrillScreen;
import de.ellpeck.actuallyadditions.client.screens.FeederScreen;
import de.ellpeck.actuallyadditions.client.screens.VoidSackScreen;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.container.ActuallyContainers;
import de.ellpeck.actuallyadditions.common.tiles.ActuallyTiles;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientSetup {
    public static void setup() {
        setupScreens();
        setupTileRenders();
    }

    private static void setupScreens() {
        ScreenManager.registerFactory(ActuallyContainers.DRILL_CONTAINER.get(), DrillScreen::new);
        ScreenManager.registerFactory(ActuallyContainers.FEEDER_CONTAINER.get(), FeederScreen::new);
        ScreenManager.registerFactory(ActuallyContainers.VOID_SACK_CONTAINER.get(), VoidSackScreen::new);
    }

    private static void setupTileRenders() {
        ActuallyAdditions.LOGGER.debug("Setting up tile entity renderers");
        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.BATTERY_BOX_TILE.get(), BatteryBoxTileRender::new);
    }
}
