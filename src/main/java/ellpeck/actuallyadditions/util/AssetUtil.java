package ellpeck.actuallyadditions.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class AssetUtil{

    public static final ResourceLocation GUI_INVENTORY_LOCATION = getGuiLocation("guiInventory");

    public static ResourceLocation getGuiLocation(String file){
        return new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/gui/" + file + ".png");
    }

    public static void displayNameString(FontRenderer font, int xSize, int yPositionOfMachineText, String machineName){
        String localMachineName = StringUtil.localize(machineName+".name");
        font.drawString(localMachineName, xSize/2 - font.getStringWidth(localMachineName)/2, yPositionOfMachineText, StringUtil.DECIMAL_COLOR_WHITE);
    }
}
