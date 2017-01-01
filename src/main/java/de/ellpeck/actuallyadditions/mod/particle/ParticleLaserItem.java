/*
 * This file ("ParticleLaserItem.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.particle;

import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

@SideOnly(Side.CLIENT)
public class ParticleLaserItem extends Particle{

    private final double otherX;
    private final double otherY;
    private final double otherZ;

    private final ItemStack stack;

    private ParticleLaserItem(World world, double posX, double posY, double posZ, ItemStack stack, double motionY){
        this(world, posX, posY, posZ, stack, motionY, 0, 0, 0);
    }

    public ParticleLaserItem(World world, double posX, double posY, double posZ, ItemStack stack, double motionY, double otherX, double otherY, double otherZ){
        super(world, posX+(world.rand.nextDouble()-0.5)/8, posY, posZ+(world.rand.nextDouble()-0.5)/8);
        this.stack = stack;
        this.otherX = otherX;
        this.otherY = otherY;
        this.otherZ = otherZ;

        this.motionX = 0;
        this.motionY = motionY;
        this.motionZ = 0;

        this.particleMaxAge = 10;
        this.canCollide = false;
    }

    @Override
    public void setExpired(){
        super.setExpired();

        if(this.otherX != 0 || this.otherY != 0 || this.otherZ != 0){
            Particle fx = new ParticleLaserItem(this.world, this.otherX, this.otherY, this.otherZ, this.stack, -0.025);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }

    @Override
    public void renderParticle(VertexBuffer buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ){
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();

        GlStateManager.translate(this.posX-TileEntityRendererDispatcher.staticPlayerX, this.posY-TileEntityRendererDispatcher.staticPlayerY, this.posZ-TileEntityRendererDispatcher.staticPlayerZ);
        GlStateManager.scale(0.3F, 0.3F, 0.3F);

        double boop = Minecraft.getSystemTime()/600D;
        GlStateManager.rotate((float)((boop*40D)%360), 0, 1, 0);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_CONSTANT_COLOR, GlStateManager.SourceFactor.ONE.factor, GlStateManager.DestFactor.ZERO.factor);

        float ageRatio = (float)this.particleAge/(float)this.particleMaxAge;
        float color = this.motionY < 0 ? 1F-ageRatio : ageRatio;
        GL14.glBlendColor(color, color, color, color);

        AssetUtil.renderItemWithoutScrewingWithColors(this.stack);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    @Override
    public int getFXLayer(){
        return 3;
    }
}
