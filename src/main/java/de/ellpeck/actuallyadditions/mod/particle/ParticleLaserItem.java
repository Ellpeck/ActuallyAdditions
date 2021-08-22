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

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL14;

public class ParticleLaserItem extends Particle {
    private final double otherX;
    private final double otherY;
    private final double otherZ;

    private final ItemStack stack;

    private ParticleLaserItem(ClientWorld world, double posX, double posY, double posZ, ItemStack stack, double motionY) {
        this(world, posX, posY, posZ, stack, motionY, 0, 0, 0);
    }

    public ParticleLaserItem(ClientWorld world, double posX, double posY, double posZ, ItemStack stack, double motionY, double otherX, double otherY, double otherZ) {
        super(world, posX + (world.random.nextDouble() - 0.5) / 8, posY, posZ + (world.random.nextDouble() - 0.5) / 8);
        this.stack = stack;
        this.otherX = otherX;
        this.otherY = otherY;
        this.otherZ = otherZ;

        this.xd = 0;
        this.yd = motionY;
        this.zd = 0;

        this.lifetime = 10;
        this.hasPhysics = false;
    }

    @Override
    public void remove() {
        super.remove();

        if (this.otherX != 0 || this.otherY != 0 || this.otherZ != 0) {
            Particle fx = new ParticleLaserItem(this.level, this.otherX, this.otherY, this.otherZ, this.stack, -0.025);
            Minecraft.getInstance().particleEngine.add(fx);
        }
    }

    @Override
    public void render(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        RenderSystem.pushMatrix();
        RenderHelper.turnBackOn();

        Vector3d cam = renderInfo.getPosition();
        RenderSystem.translated(this.x - cam.x(), this.y - cam.y(), this.z - cam.z());
        RenderSystem.scalef(0.3F, 0.3F, 0.3F);

        double boop = Util.getMillis() / 600D;
        RenderSystem.rotatef((float) (boop * 40D % 360), 0, 1, 0);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SourceFactor.ONE.value, GlStateManager.DestFactor.ZERO.value);

        float ageRatio = (float) this.age / (float) this.lifetime;
        float color = this.yd < 0
            ? 1F - ageRatio
            : ageRatio;
        GL14.glBlendColor(color, color, color, color);

        AssetUtil.renderItemWithoutScrewingWithColors(this.stack);

        RenderHelper.turnOff();
        RenderSystem.popMatrix();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
