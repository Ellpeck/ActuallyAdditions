/*
 * This file ("AssetUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AssetUtil{

    public static final ResourceLocation GUI_INVENTORY_LOCATION = getGuiLocation("guiInventory");
    public static int COMPOST_RENDER_ID;
    public static int FISHING_NET_RENDER_ID;
    public static int FURNACE_SOLAR_RENDER_ID;
    public static int COFFEE_MACHINE_RENDER_ID;
    public static int PHANTOM_BOOSTER_RENDER_ID;
    public static int SMILEY_CLOUD_RENDER_ID;
    public static int LASER_RELAY_RENDER_ID;

    public static ResourceLocation getGuiLocation(String file){
        return new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/gui/"+file+".png");
    }

    public static void displayNameString(FontRenderer font, int xSize, int yPositionOfMachineText, String machineName){
        String localMachineName = StringUtil.localize(machineName+".name");
        font.drawString(localMachineName, xSize/2-font.getStringWidth(localMachineName)/2, yPositionOfMachineText, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @SideOnly(Side.CLIENT)
    public static void renderItem(ItemStack stack, int renderPass){
        IIcon icon = stack.getItem().getIcon(stack, renderPass);
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
        ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F/16F);
    }

    @SideOnly(Side.CLIENT)
    public static void renderBlock(Block block, int meta){
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        RenderBlocks.getInstance().renderBlockAsItem(block, meta, 1F);
    }

    //Copied from Gui.class and changed
    public static void drawHorizontalGradientRect(int startX, int startY, int endX, int endY, int firstColor, int secondColor){
        float f = (float)(firstColor >> 24 & 255)/255.0F;
        float f1 = (float)(firstColor >> 16 & 255)/255.0F;
        float f2 = (float)(firstColor >> 8 & 255)/255.0F;
        float f3 = (float)(firstColor & 255)/255.0F;
        float f4 = (float)(secondColor >> 24 & 255)/255.0F;
        float f5 = (float)(secondColor >> 16 & 255)/255.0F;
        float f6 = (float)(secondColor >> 8 & 255)/255.0F;
        float f7 = (float)(secondColor & 255)/255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double)startX, (double)startY, 0);
        tessellator.addVertex((double)startX, (double)endY, 0);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double)endX, (double)endY, 0);
        tessellator.addVertex((double)endX, (double)startY, 0);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
