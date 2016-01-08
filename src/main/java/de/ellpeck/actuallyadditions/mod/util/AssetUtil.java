/*
 * This file ("AssetUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.booklet.GuiBooklet;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class AssetUtil{

    public static final ResourceLocation GUI_INVENTORY_LOCATION = getGuiLocation("guiInventory");
    public static final int TESR_RENDER_ID = 2;

    public static ResourceLocation getGuiLocation(String file){
        return new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/gui/"+file+".png");
    }

    public static ResourceLocation getBookletGuiLocation(String file){
        return new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/gui/booklet/"+file+".png");
    }

    public static void displayNameString(FontRenderer font, int xSize, int yPositionOfMachineText, String machineName){
        String localMachineName = StringUtil.localize(machineName+".name");
        font.drawString(localMachineName, xSize/2-font.getStringWidth(localMachineName)/2, yPositionOfMachineText, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemInWorld(ItemStack stack){
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    @SideOnly(Side.CLIENT)
    public static void renderBlockInWorld(Block block, int meta){
        renderItemInWorld(new ItemStack(block, 1, meta));
    }

    @SideOnly(Side.CLIENT)
    public static void renderStackToGui(ItemStack stack, int x, int y, float scale){
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, scale);

        Minecraft mc = Minecraft.getMinecraft();
        boolean flagBefore = mc.fontRendererObj.getUnicodeFlag();
        mc.fontRendererObj.setUnicodeFlag(false);
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
        Minecraft.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, 0, 0, null);
        mc.fontRendererObj.setUnicodeFlag(flagBefore);

        //GL+MC+NEI suck
        if(mc.currentScreen instanceof GuiBooklet || mc.currentScreen == null){
            RenderHelper.disableStandardItemLighting();
        }
        GlStateManager.popMatrix();
    }

    //Copied from Gui.class and changed
    public static void drawHorizontalGradientRect(int left, int top, int right, int bottom, int startColor, int endColor, float zLevel){
        float f = (float)(startColor >> 24 & 255)/255.0F;
        float f1 = (float)(startColor >> 16 & 255)/255.0F;
        float f2 = (float)(startColor >> 8 & 255)/255.0F;
        float f3 = (float)(startColor & 255)/255.0F;
        float f4 = (float)(endColor >> 24 & 255)/255.0F;
        float f5 = (float)(endColor >> 16 & 255)/255.0F;
        float f6 = (float)(endColor >> 8 & 255)/255.0F;
        float f7 = (float)(endColor & 255)/255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double)left, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos((double)left, (double)bottom, (double)zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos((double)right, (double)bottom, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos((double)right, (double)top, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
