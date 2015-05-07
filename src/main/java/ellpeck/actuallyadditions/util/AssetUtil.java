package ellpeck.actuallyadditions.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class AssetUtil{

    public static final ResourceLocation GUI_INVENTORY_LOCATION = getGuiLocation("guiInventory");

    public static ResourceLocation getGuiLocation(String file){
        return new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/gui/" + file + ".png");
    }

    public static void displayNameAndInventoryString(FontRenderer font, int xSize, int yPositionOfInventoryText, int yPositionOfMachineText, String machineName){
        String localMachineName = StatCollector.translateToLocal(machineName + ".name");
        String inventoryName = StatCollector.translateToLocal("container.inventory");
        font.drawString(localMachineName, xSize/2 - font.getStringWidth(localMachineName)/2, yPositionOfMachineText, StringUtil.DECIMAL_COLOR_WHITE);
        font.drawString(inventoryName, xSize/2 - font.getStringWidth(inventoryName)/2, yPositionOfInventoryText-1, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
    }
}
