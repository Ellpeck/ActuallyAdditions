package de.ellpeck.actuallyadditions.client;

import de.ellpeck.actuallyadditions.client.screens.DrillScreen;
import de.ellpeck.actuallyadditions.common.container.ActuallyContainers;
import net.minecraft.client.gui.ScreenManager;

public class ClientSetup {
    public static void setup() {
        setupScreens();
    }

    private static void setupScreens() {
        ScreenManager.registerFactory(ActuallyContainers.DRILL_CONTAINER.get(), DrillScreen::new);
    }
}
