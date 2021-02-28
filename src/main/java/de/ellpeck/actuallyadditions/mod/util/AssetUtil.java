/*
 * This file ("AssetUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketServerToClient;
import de.ellpeck.actuallyadditions.mod.particle.ParticleBeam;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.OnlyIn;
import org.lwjgl.opengl.GL11;

public final class AssetUtil {

    public static final int MAX_LIGHT_X = 0xF000F0;
    public static final int MAX_LIGHT_Y = 0xF000F0;

    public static final ResourceLocation GUI_INVENTORY_LOCATION = getGuiLocation("gui_inventory");

    public static ResourceLocation getGuiLocation(String file) {
        return new ResourceLocation(ActuallyAdditions.MODID, "textures/gui/" + file + ".png");
    }

    public static ResourceLocation getBookletGuiLocation(String file) {
        return getGuiLocation("booklet/" + file);
    }

    @OnlyIn(Dist.CLIENT)
    public static void displayNameString(FontRenderer font, int xSize, int yPositionOfMachineText, String text) {
        font.drawString(text, xSize / 2 - font.getStringWidth(text) / 2, yPositionOfMachineText, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @OnlyIn(Dist.CLIENT)
    public static void displayNameString(FontRenderer font, int xSize, int yPositionOfMachineText, TileEntityBase tile) {
        displayNameString(font, xSize, yPositionOfMachineText, StringUtil.localize(tile.getNameForTranslation()));
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderBlockInWorld(Block block, int meta) {
        renderItemInWorld(new ItemStack(block, 1, meta), combinedLightIn, combinedOverlayIn, matrices, buffer);
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderItemInWorld(ItemStack stack, int combinedLight, int combinedOverlay, MatrixStack matrices, IRenderTypeBuffer buffer) {
        if (StackUtil.isValid(stack)) {
            Minecraft.getInstance().getItemRenderer().renderItem(
                stack, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrices, buffer
            );
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderStateInWorld(BlockState state, IBlockAccess world, BlockPos pos, float brightness) {
        Minecraft.getInstance().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        IBakedModel model = Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(state);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        int i = Minecraft.getInstance().getBlockColors().colorMultiplier(state, world, pos, 0);

        float r = (i >> 16 & 255) / 255F;
        float g = (i >> 8 & 255) / 255F;
        float b = (i & 255) / 255F;

        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModelBrightnessColor(state, model, brightness, r, g, b);
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderItemWithoutScrewingWithColors(ItemStack stack) {
        if (StackUtil.isValid(stack)) {
            Minecraft mc = Minecraft.getInstance();
            RenderItem renderer = mc.getRenderItem();
            TextureManager manager = mc.getTextureManager();

            IBakedModel model = renderer.getItemModelWithOverrides(stack, null, null);

            manager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            manager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.pushMatrix();
            model = ForgeHooksClient.handleCameraTransforms(model, TransformType.FIXED, false);
            renderer.renderItem(stack, model);
            GlStateManager.cullFace(GlStateManager.CullFace.BACK);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            manager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            manager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderStackToGui(ItemStack stack, int x, int y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, scale);

        Minecraft mc = Minecraft.getInstance();
        boolean flagBefore = mc.fontRenderer.getUnicodeFlag();
        mc.fontRenderer.setUnicodeFlag(false);
        Minecraft.getInstance().getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
        Minecraft.getInstance().getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, stack, 0, 0, null);
        mc.fontRenderer.setUnicodeFlag(flagBefore);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    //Copied from Gui.class and changed
    @OnlyIn(Dist.CLIENT)
    public static void drawHorizontalGradientRect(int left, int top, int right, int bottom, int startColor, int endColor, float zLevel) {
        float f = (startColor >> 24 & 255) / 255.0F;
        float f1 = (startColor >> 16 & 255) / 255.0F;
        float f2 = (startColor >> 8 & 255) / 255.0F;
        float f3 = (startColor & 255) / 255.0F;
        float f4 = (endColor >> 24 & 255) / 255.0F;
        float f5 = (endColor >> 16 & 255) / 255.0F;
        float f6 = (endColor >> 8 & 255) / 255.0F;
        float f7 = (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos(left, top, zLevel).color(f1, f2, f3, f).endVertex();
        renderer.pos(left, bottom, zLevel).color(f1, f2, f3, f).endVertex();
        renderer.pos(right, bottom, zLevel).color(f5, f6, f7, f4).endVertex();
        renderer.pos(right, top, zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderNameTag(String tag, double x, double y, double z) {
        FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
        float f = 1.6F;
        float f1 = 0.016666668F * f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-Minecraft.getInstance().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getInstance().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();
        int i = 0;
        int j = fontrenderer.getStringWidth(tag) / 2;
        GlStateManager.disableTexture2D();
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos(-j - 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        renderer.pos(-j - 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        renderer.pos(j + 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        renderer.pos(j + 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontrenderer.drawString(tag, -fontrenderer.getStringWidth(tag) / 2, i, 553648127);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontrenderer.drawString(tag, -fontrenderer.getStringWidth(tag) / 2, i, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public static void spawnLaserWithTimeServer(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, float[] color, int maxAge, double rotationTime, float size, float alpha) {
        if (!world.isRemote) {
            CompoundNBT data = new CompoundNBT();
            data.setDouble("StartX", startX);
            data.setDouble("StartY", startY);
            data.setDouble("StartZ", startZ);
            data.setDouble("EndX", endX);
            data.setDouble("EndY", endY);
            data.setDouble("EndZ", endZ);
            data.setFloat("Color1", color[0]);
            data.setFloat("Color2", color[1]);
            data.setFloat("Color3", color[2]);
            data.setDouble("RotationTime", rotationTime);
            data.setFloat("Size", size);
            data.setInteger("MaxAge", maxAge);
            data.setFloat("Alpha", alpha);
            PacketHandler.THE_NETWORK.sendToAllAround(new PacketServerToClient(data, PacketHandler.LASER_HANDLER), new NetworkRegistry.TargetPoint(world.provider.getDimension(), startX, startY, startZ, 96));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void spawnLaserWithTimeClient(double startX, double startY, double startZ, double endX, double endY, double endZ, float[] color, int maxAge, double rotationTime, float size, float alpha) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player.getDistance(startX, startY, startZ) <= 64 || mc.player.getDistance(endX, endY, endZ) <= 64) {
            Particle fx = new ParticleBeam(mc.world, startX, startY, startZ, endX, endY, endZ, color, maxAge, rotationTime, size, alpha);
            mc.effectRenderer.addEffect(fx);
        }
    }

    //Thanks to feldim2425 for this.
    //I can't do rendering code. Ever.
    @OnlyIn(Dist.CLIENT)
    public static void renderLaser(double firstX, double firstY, double firstZ, double secondX, double secondY, double secondZ, double rotationTime, float alpha, double beamWidth, float[] color) {
        Tessellator tessy = Tessellator.getInstance();
        BufferBuilder render = tessy.getBuffer();
        World world = Minecraft.getInstance().world;

        float r = color[0];
        float g = color[1];
        float b = color[2];

        Vec3d vec1 = new Vec3d(firstX, firstY, firstZ);
        Vec3d vec2 = new Vec3d(secondX, secondY, secondZ);
        Vec3d combinedVec = vec2.subtract(vec1);

        double rot = rotationTime > 0
            ? 360D * (world.getTotalWorldTime() % rotationTime / rotationTime)
            : 0;
        double pitch = Math.atan2(combinedVec.y, Math.sqrt(combinedVec.x * combinedVec.x + combinedVec.z * combinedVec.z));
        double yaw = Math.atan2(-combinedVec.z, combinedVec.x);

        double length = combinedVec.length();

        GlStateManager.pushMatrix();

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        int func = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
        float ref = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
        GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);
        GlStateManager.translate(firstX - TileEntityRendererDispatcher.staticPlayerX, firstY - TileEntityRendererDispatcher.staticPlayerY, firstZ - TileEntityRendererDispatcher.staticPlayerZ);
        GlStateManager.rotate((float) (180 * yaw / Math.PI), 0, 1, 0);
        GlStateManager.rotate((float) (180 * pitch / Math.PI), 0, 0, 1);
        GlStateManager.rotate((float) rot, 1, 0, 0);

        /*if(r != r2 || g != g2 || b != b2){
            render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            Minecraft.getInstance().renderEngine.bindTexture(ClientUtil.LIGHT_BEAM_GRADIENT);

            render.pos(length, -beamWidth, beamWidth).tex(0, 0).color(r, g, b, alpha).endVertex();
            render.pos(length, beamWidth, beamWidth).tex(0, 1).color(r, g, b, alpha).endVertex();
            render.pos(0, beamWidth, beamWidth).tex(1, 1).color(r, g, b, alpha).endVertex();
            render.pos(0, -beamWidth, beamWidth).tex(1, 0).color(r, g, b, alpha).endVertex();

            render.pos(length, -beamWidth, beamWidth).tex(1, 0).color(r2, g2, b2, alpha).endVertex();
            render.pos(length, beamWidth, beamWidth).tex(1, 1).color(r2, g2, b2, alpha).endVertex();
            render.pos(0, beamWidth, beamWidth).tex(0, 1).color(r2, g2, b2, alpha).endVertex();
            render.pos(0, -beamWidth, beamWidth).tex(0, 0).color(r2, g2, b2, alpha).endVertex();

            render.pos(length, beamWidth, -beamWidth).tex(0, 0).color(r, g, b, alpha).endVertex();
            render.pos(length, -beamWidth, -beamWidth).tex(0, 1).color(r, g, b, alpha).endVertex();
            render.pos(0, -beamWidth, -beamWidth).tex(1, 1).color(r, g, b, alpha).endVertex();
            render.pos(0, beamWidth, -beamWidth).tex(1, 0).color(r, g, b, alpha).endVertex();

            render.pos(length, beamWidth, -beamWidth).tex(1, 0).color(r2, g2, b2, alpha).endVertex();
            render.pos(length, -beamWidth, -beamWidth).tex(1, 1).color(r2, g2, b2, alpha).endVertex();
            render.pos(0, -beamWidth, -beamWidth).tex(0, 1).color(r2, g2, b2, alpha).endVertex();
            render.pos(0, beamWidth, -beamWidth).tex(0, 0).color(r2, g2, b2, alpha).endVertex();

            render.pos(length, beamWidth, beamWidth).tex(0, 0).color(r, g, b, alpha).endVertex();
            render.pos(length, beamWidth, -beamWidth).tex(0, 1).color(r, g, b, alpha).endVertex();
            render.pos(0, beamWidth, -beamWidth).tex(1, 1).color(r, g, b, alpha).endVertex();
            render.pos(0, beamWidth, beamWidth).tex(1, 0).color(r, g, b, alpha).endVertex();

            render.pos(length, beamWidth, beamWidth).tex(1, 0).color(r2, g2, b2, alpha).endVertex();
            render.pos(length, beamWidth, -beamWidth).tex(1, 1).color(r2, g2, b2, alpha).endVertex();
            render.pos(0, beamWidth, -beamWidth).tex(0, 1).color(r2, g2, b2, alpha).endVertex();
            render.pos(0, beamWidth, beamWidth).tex(0, 0).color(r2, g2, b2, alpha).endVertex();

            render.pos(length, -beamWidth, -beamWidth).tex(0, 0).color(r, g, b, alpha).endVertex();
            render.pos(length, -beamWidth, beamWidth).tex(0, 1).color(r, g, b, alpha).endVertex();
            render.pos(0, -beamWidth, beamWidth).tex(1, 1).color(r, g, b, alpha).endVertex();
            render.pos(0, -beamWidth, -beamWidth).tex(1, 0).color(r, g, b, alpha).endVertex();

            render.pos(length, -beamWidth, -beamWidth).tex(1, 0).color(r2, g2, b2, alpha).endVertex();
            render.pos(length, -beamWidth, beamWidth).tex(1, 1).color(r2, g2, b2, alpha).endVertex();
            render.pos(0, -beamWidth, beamWidth).tex(0, 1).color(r2, g2, b2, alpha).endVertex();
            render.pos(0, -beamWidth, -beamWidth).tex(0, 0).color(r2, g2, b2, alpha).endVertex();
            tessy.draw();
        }
        else{*/
        GlStateManager.disableTexture2D();
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        for (double i = 0; i < 4; i++) {
            double width = beamWidth * (i / 4.0);
            render.pos(length, width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(0, width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(0, -width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(length, -width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();

            render.pos(length, -width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(0, -width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(0, width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(length, width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();

            render.pos(length, width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(0, width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(0, width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(length, width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();

            render.pos(length, -width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(0, -width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(0, -width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
            render.pos(length, -width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        }
        tessy.draw();

        GlStateManager.enableTexture2D();
        //}

        GlStateManager.alphaFunc(func, ref);
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public static float[] getWheelColor(float pos) {
        if (pos < 85.0f) {
            return new float[]{pos * 3.0F, 255.0f - pos * 3.0f, 0.0f};
        }
        if (pos < 170.0f) {
            return new float[]{255.0f - (pos -= 85.0f) * 3.0f, 0.0f, pos * 3.0f};
        }
        return new float[]{0.0f, (pos -= 170.0f) * 3.0f, 255.0f - pos * 3.0f};
    }
}
