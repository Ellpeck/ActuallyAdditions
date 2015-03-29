package ellpeck.actuallyadditions.util;

import net.minecraft.util.ResourceLocation;

public class AssetUtil{

    public static final ResourceLocation GUI_INVENTORY_LOCATION = getGuiLocation("guiInventory");

    public static ResourceLocation getGuiLocation(String file){
        return new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/gui/" + file + ".png");
    }

}
