/*
 * This file ("AssetUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.misc.ParticleColored;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketServerToClient;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public final class AssetUtil{
	
	public static int maxLightX = 0xF000F0;
	public static int maxLightY = 0xF000F0;

    public static final ResourceLocation GUI_INVENTORY_LOCATION = getGuiLocation("gui_inventory");

    public static ResourceLocation getGuiLocation(String file){
        return new ResourceLocation(ModUtil.MOD_ID, "textures/gui/"+file+".png");
    }

    public static ResourceLocation getBookletGuiLocation(String file){
        return getGuiLocation("booklet/"+file);
    }

    @SideOnly(Side.CLIENT)
    public static void displayNameString(FontRenderer font, int xSize, int yPositionOfMachineText, String text){
        font.drawString(text, xSize/2-font.getStringWidth(text)/2, yPositionOfMachineText, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @SideOnly(Side.CLIENT)
    public static void displayNameString(FontRenderer font, int xSize, int yPositionOfMachineText, TileEntityBase tile){
        displayNameString(font, xSize, yPositionOfMachineText, tile.getDisplayedName());
    }

    @SideOnly(Side.CLIENT)
    public static void renderBlockInWorld(Block block, int meta){
        renderItemInWorld(new ItemStack(block, 1, meta));
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemInWorld(ItemStack stack){
        if(StackUtil.isValid(stack)){
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

        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    //Copied from Gui.class and changed
    @SideOnly(Side.CLIENT)
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
        VertexBuffer renderer = tessellator.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double)left, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
        renderer.pos((double)left, (double)bottom, (double)zLevel).color(f1, f2, f3, f).endVertex();
        renderer.pos((double)right, (double)bottom, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        renderer.pos((double)right, (double)top, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    @SideOnly(Side.CLIENT)
    public static void renderNameTag(String tag, double x, double y, double z){
        FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
        float f = 1.6F;
        float f1 = 0.016666668F*f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer renderer = tessellator.getBuffer();
        int i = 0;
        int j = fontrenderer.getStringWidth(tag)/2;
        GlStateManager.disableTexture2D();
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double)(-j-1), (double)(-1+i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        renderer.pos((double)(-j-1), (double)(8+i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        renderer.pos((double)(j+1), (double)(8+i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        renderer.pos((double)(j+1), (double)(-1+i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontrenderer.drawString(tag, -fontrenderer.getStringWidth(tag)/2, i, 553648127);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontrenderer.drawString(tag, -fontrenderer.getStringWidth(tag)/2, i, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public static void shootParticles(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, float[] color, int particleAmount, float particleSize, float ageMultiplier){
        if(!world.isRemote){
            NBTTagCompound data = new NBTTagCompound();
            data.setDouble("StartX", startX);
            data.setDouble("StartY", startY);
            data.setDouble("StartZ", startZ);
            data.setDouble("EndX", endX);
            data.setDouble("EndY", endY);
            data.setDouble("EndZ", endZ);
            data.setFloat("Color1", color[0]);
            data.setFloat("Color2", color[1]);
            data.setFloat("Color3", color[2]);
            data.setInteger("ParticleAmount", particleAmount);
            data.setFloat("ParticleSize", particleSize);
            data.setFloat("AgeMultiplier", ageMultiplier);
            PacketHandler.theNetwork.sendToAllAround(new PacketServerToClient(data, PacketHandler.PARTICLE_HANDLER), new NetworkRegistry.TargetPoint(world.provider.getDimension(), startX, startY, startZ, 96));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void renderParticlesFromAToB(double startX, double startY, double startZ, double endX, double endY, double endZ, int particleAmount, float particleSize, float[] color, float ageMultiplier){
        Minecraft mc = Minecraft.getMinecraft();
        int particleSetting = mc.gameSettings.particleSetting;

        if(mc.player.getDistance(startX, startY, startZ) <= 64 || mc.player.getDistance(endX, endY, endZ) <= 64){
            double difX = startX-endX;
            double difY = startY-endY;
            double difZ = startZ-endZ;
            double distance = new Vec3d(startX, startY, startZ).distanceTo(new Vec3d(endX, endY, endZ));

            for(int times = 0; times < Math.max(particleAmount/2, 1); times++){
                for(double i = 0; i <= 1; i += 1/(distance*particleAmount)){
                    if(particleSetting == 0 || (particleSetting == 1 && mc.world.rand.nextFloat() >= 0.8F) || (particleSetting > 1 && mc.world.rand.nextFloat() >= 0.99F)){
                        ParticleColored fx = new ParticleColored(mc.world, (difX*i)+endX+0.5, (difY*i)+endY+0.5, (difZ*i)+endZ+0.5, particleSize, color[0], color[1], color[2], ageMultiplier);
                        mc.effectRenderer.addEffect(fx);
                    }
                }
            }
        }
    }

    //Thanks to feldim2425 for this.
    //I can't do rendering code. Ever.
    @SideOnly(Side.CLIENT)
    public static void renderLaser(double firstX, double firstY, double firstZ, double secondX, double secondY, double secondZ, double rotationTime, float alpha, double beamWidth, float[] color){
        Tessellator tessy = Tessellator.getInstance();
        VertexBuffer render = tessy.getBuffer();
        World world = Minecraft.getMinecraft().world;

        float r = color[0];
        float g = color[1];
        float b = color[2];

        Vec3d vec1 = new Vec3d(firstX, firstY, firstZ);
        Vec3d vec2 = new Vec3d(secondX, secondY, secondZ);
        Vec3d combinedVec = vec2.subtract(vec1);

        double rot = rotationTime > 0 ? (360D*((world.getTotalWorldTime()%rotationTime)/rotationTime)) : 0;
        double pitch = Math.atan2(combinedVec.yCoord, Math.sqrt(combinedVec.xCoord*combinedVec.xCoord+combinedVec.zCoord*combinedVec.zCoord));
        double yaw = Math.atan2(-combinedVec.zCoord, combinedVec.xCoord);

        double length = combinedVec.lengthVector();

        GlStateManager.pushMatrix();

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		int func = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
		float ref = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
		GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);
        GlStateManager.translate(firstX-TileEntityRendererDispatcher.staticPlayerX, firstY-TileEntityRendererDispatcher.staticPlayerY, firstZ-TileEntityRendererDispatcher.staticPlayerZ);
        GlStateManager.rotate((float)(180*yaw/Math.PI), 0, 1, 0);
        GlStateManager.rotate((float)(180*pitch/Math.PI), 0, 0, 1);
        GlStateManager.rotate((float)rot, 1, 0, 0);

        /*if(r != r2 || g != g2 || b != b2){
            render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            Minecraft.getMinecraft().renderEngine.bindTexture(ClientUtil.LIGHT_BEAM_GRADIENT);

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
        for (double i = 0; i < 4; i ++){
        	double width = beamWidth * (i/4.0);
	        render.pos(length, width, width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(0, width, width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(0, -width, width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(length, -width, width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	
	        render.pos(length, -width, -width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(0, -width, -width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(0, width, -width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(length, width, -width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	
	        render.pos(length, width, -width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(0, width, -width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(0, width, width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(length, width, width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	
	        render.pos(length, -width, width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(0, -width, width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(0, -width, -width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
	        render.pos(length, -width, -width).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, alpha).endVertex();
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

    public static float[] getWheelColor(float pos){
        if(pos < 85.0f){
            return new float[]{pos*3.0F, 255.0f-pos*3.0f, 0.0f};
        }
        if(pos < 170.0f){
            return new float[]{255.0f-(pos -= 85.0f)*3.0f, 0.0f, pos*3.0f};
        }
        return new float[]{0.0f, (pos -= 170.0f)*3.0f, 255.0f-pos*3.0f};
    }
}
