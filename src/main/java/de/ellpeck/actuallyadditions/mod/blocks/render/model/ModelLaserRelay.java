/*
 * This file ("ModelLaserRelay.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render.model;

import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Made by Canitzp.
 * Thanks. Seriously. It looks really awesome. I don't think I could do this.
 */
public class ModelLaserRelay extends ModelBaseAA{

    ModelRenderer bottom;
    ModelRenderer laserPillar;
    ModelRenderer laserBase;
    ModelRenderer covering1;
    ModelRenderer covering2;
    ModelRenderer covering3;
    ModelRenderer covering4;
    ModelRenderer covering5;
    ModelRenderer covering6;
    ModelRenderer covering7;
    ModelRenderer covering8;
    ModelRenderer top;
    ModelRenderer covering9;
    ModelRenderer covering10;
    ModelRenderer covering11;
    ModelRenderer covering12;
    ModelRenderer energyBall;

    public ModelLaserRelay(){
        textureWidth = 64;
        textureHeight = 64;

        bottom = new ModelRenderer(this, 0, 0);
        bottom.addBox(0F, 0F, 0F, 8, 1, 8);
        bottom.setRotationPoint(-4F, 23F, -4F);
        bottom.setTextureSize(64, 64);
        bottom.mirror = true;
        setRotation(bottom, 0F, 0F, 0F);
        laserPillar = new ModelRenderer(this, 54, 0);
        laserPillar.addBox(0F, 0F, 0F, 2, 11, 2);
        laserPillar.setRotationPoint(-1F, 10F, -1F);
        laserPillar.setTextureSize(64, 64);
        laserPillar.mirror = true;
        setRotation(laserPillar, 0F, 0F, 0F);
        laserBase = new ModelRenderer(this, 33, 0);
        laserBase.addBox(0F, 0F, 0F, 5, 2, 5);
        laserBase.setRotationPoint(-2.5F, 21F, -2.5F);
        laserBase.setTextureSize(64, 64);
        laserBase.mirror = true;
        setRotation(laserBase, 0F, 0F, 0F);
        covering1 = new ModelRenderer(this, 0, 10);
        covering1.addBox(0F, 0F, 0F, 8, 3, 1);
        covering1.setRotationPoint(-4F, 20F, -5F);
        covering1.setTextureSize(64, 64);
        covering1.mirror = true;
        setRotation(covering1, 0F, 0F, 0F);
        covering2 = new ModelRenderer(this, 0, 10);
        covering2.addBox(0F, 0F, 0F, 8, 3, 1);
        covering2.setRotationPoint(-4F, 20F, 4F);
        covering2.setTextureSize(64, 64);
        covering2.mirror = true;
        setRotation(covering2, 0F, 0F, 0F);
        covering3 = new ModelRenderer(this, 0, 10);
        covering3.addBox(0F, 0F, 0F, 8, 3, 1);
        covering3.setRotationPoint(-5F, 20F, 4F);
        covering3.setTextureSize(64, 64);
        covering3.mirror = true;
        setRotation(covering3, 0F, 1.579523F, 0F);
        covering4 = new ModelRenderer(this, 0, 10);
        covering4.addBox(0F, 0F, 0F, 8, 3, 1);
        covering4.setRotationPoint(4F, 20F, 4F);
        covering4.setTextureSize(64, 64);
        covering4.mirror = true;
        setRotation(covering4, 0F, 1.579523F, 0F);
        covering5 = new ModelRenderer(this, 0, 10);
        covering5.addBox(0F, 0F, 0F, 8, 3, 1);
        covering5.setRotationPoint(-4F, 10F, -5F);
        covering5.setTextureSize(64, 64);
        covering5.mirror = true;
        setRotation(covering5, 0F, 0F, 0F);
        covering6 = new ModelRenderer(this, 0, 10);
        covering6.addBox(0F, 0F, 0F, 8, 3, 1);
        covering6.setRotationPoint(-4F, 10F, 4F);
        covering6.setTextureSize(64, 64);
        covering6.mirror = true;
        setRotation(covering6, 0F, 0F, 0F);
        covering7 = new ModelRenderer(this, 0, 10);
        covering7.addBox(0F, 0F, 0F, 8, 3, 1);
        covering7.setRotationPoint(-5F, 10F, 4F);
        covering7.setTextureSize(64, 64);
        covering7.mirror = true;
        setRotation(covering7, 0F, 1.579523F, 0F);
        covering8 = new ModelRenderer(this, 0, 10);
        covering8.addBox(0F, 0F, 0F, 8, 3, 1);
        covering8.setRotationPoint(4F, 10F, 4F);
        covering8.setTextureSize(64, 64);
        covering8.mirror = true;
        setRotation(covering8, 0F, 1.579523F, 0F);
        top = new ModelRenderer(this, 0, 0);
        top.addBox(0F, 0F, 0F, 8, 1, 8);
        top.setRotationPoint(-4F, 9F, -4F);
        top.setTextureSize(64, 64);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        covering9 = new ModelRenderer(this, 19, 10);
        covering9.addBox(0F, 0F, 0F, 8, 7, 1);
        covering9.setRotationPoint(-4F, 13F, 5F);
        covering9.setTextureSize(64, 64);
        covering9.mirror = true;
        setRotation(covering9, 0F, 0F, 0F);
        covering10 = new ModelRenderer(this, 19, 10);
        covering10.addBox(0F, 0F, 0F, 8, 7, 1);
        covering10.setRotationPoint(-4F, 13F, -6F);
        covering10.setTextureSize(64, 64);
        covering10.mirror = true;
        setRotation(covering10, 0F, 0F, 0F);
        covering11 = new ModelRenderer(this, 19, 10);
        covering11.addBox(0F, 0F, 0F, 8, 7, 1);
        covering11.setRotationPoint(-6F, 13F, 4F);
        covering11.setTextureSize(64, 64);
        covering11.mirror = true;
        setRotation(covering11, 0F, 1.579523F, 0F);
        covering12 = new ModelRenderer(this, 19, 10);
        covering12.addBox(0F, 0F, 0F, 8, 7, 1);
        covering12.setRotationPoint(5F, 13F, 4F);
        covering12.setTextureSize(64, 64);
        covering12.mirror = true;
        setRotation(covering12, 0F, 1.579523F, 0F);
        energyBall = new ModelRenderer(this, 0, 15);
        energyBall.addBox(0F, 0F, 0F, 3, 3, 3);
        energyBall.setRotationPoint(-1.5F, 15F, -1.5F);
        energyBall.setTextureSize(64, 64);
        energyBall.mirror = true;
        setRotation(energyBall, 0F, 0F, 0F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z){
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void render(float f){
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        bottom.render(f);
        laserPillar.render(f);
        laserBase.render(f);
        energyBall.render(f);
        top.render(f);
        covering1.render(f);
        covering2.render(f);
        covering3.render(f);
        covering4.render(f);
        covering5.render(f);
        covering6.render(f);
        covering7.render(f);
        covering8.render(f);
        covering9.render(f);
        covering10.render(f);
        covering11.render(f);
        covering12.render(f);
    }

    @Override
    public String getName(){
        return "modelLaserRelay";
    }

    @Override
    public boolean doesRotate(){
        return true;
    }
}
