package de.ellpeck.actuallyadditions.common.utilities;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.util.ResourceLocation;

public final class ScreenHelper {
    public static final ResourceLocation INVENTORY_GUI = getGuiLocation("gui_inventory");

    public static ResourceLocation getGuiLocation(String file) {
        return new ResourceLocation(ActuallyAdditions.MOD_ID, "textures/gui/" + file + ".png");
    }
}
