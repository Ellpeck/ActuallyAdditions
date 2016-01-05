/*
 * This file ("ModelPhantomBooster.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render.model;

import net.minecraft.client.model.ModelRenderer;

public class ModelPhantomBooster extends ModelBaseAA{

    ModelRenderer s1;
    ModelRenderer s2;
    ModelRenderer s3;
    ModelRenderer s4;
    ModelRenderer s5;
    ModelRenderer s6;
    ModelRenderer s7;
    ModelRenderer s8;
    ModelRenderer s9;
    ModelRenderer s10;
    ModelRenderer s11;
    ModelRenderer s12;
    ModelRenderer s13;
    ModelRenderer s14;
    ModelRenderer s15;

    public ModelPhantomBooster(){
        textureWidth = 128;
        textureHeight = 128;

        s1 = new ModelRenderer(this, 0, 0);
        s1.addBox(0F, 0F, 0F, 4, 16, 4);
        s1.setRotationPoint(-2F, 8F, -2F);
        s1.setTextureSize(128, 128);
        s1.mirror = true;
        setRotation(s1, 0F, 0F, 0F);
        s2 = new ModelRenderer(this, 17, 0);
        s2.addBox(0F, 0F, 0F, 6, 1, 6);
        s2.setRotationPoint(-3F, 9F, -3F);
        s2.setTextureSize(128, 128);
        s2.mirror = true;
        setRotation(s2, 0F, 0F, 0F);
        s3 = new ModelRenderer(this, 17, 0);
        s3.addBox(0F, 0F, 0F, 6, 1, 6);
        s3.setRotationPoint(-3F, 22F, -3F);
        s3.setTextureSize(128, 128);
        s3.mirror = true;
        setRotation(s3, 0F, 0F, 0F);
        s4 = new ModelRenderer(this, 17, 8);
        s4.addBox(0F, 0F, 0F, 6, 2, 1);
        s4.setRotationPoint(-3F, 10F, -4F);
        s4.setTextureSize(128, 128);
        s4.mirror = true;
        setRotation(s4, 0F, 0F, 0F);
        s5 = new ModelRenderer(this, 17, 8);
        s5.addBox(0F, 0F, 0F, 6, 2, 1);
        s5.setRotationPoint(-3F, 10F, 3F);
        s5.setTextureSize(128, 128);
        s5.mirror = true;
        setRotation(s5, 0F, 0F, 0F);
        s6 = new ModelRenderer(this, 17, 8);
        s6.addBox(0F, 0F, 0F, 6, 2, 1);
        s6.setRotationPoint(-4F, 10F, 3F);
        s6.setTextureSize(128, 128);
        s6.mirror = true;
        setRotation(s6, 0F, 1.579523F, 0F);
        s7 = new ModelRenderer(this, 17, 8);
        s7.addBox(0F, 0F, 0F, 6, 2, 1);
        s7.setRotationPoint(3F, 10F, 3F);
        s7.setTextureSize(128, 128);
        s7.mirror = true;
        setRotation(s7, 0F, 1.579523F, 0F);
        s8 = new ModelRenderer(this, 17, 12);
        s8.addBox(0F, 0F, 0F, 6, 8, 1);
        s8.setRotationPoint(-3F, 12F, -5F);
        s8.setTextureSize(128, 128);
        s8.mirror = true;
        setRotation(s8, 0F, 0F, 0F);
        s9 = new ModelRenderer(this, 17, 12);
        s9.addBox(0F, 0F, 0F, 6, 8, 1);
        s9.setRotationPoint(-3F, 12F, 4F);
        s9.setTextureSize(128, 128);
        s9.mirror = true;
        setRotation(s9, 0F, 0F, 0F);
        s10 = new ModelRenderer(this, 17, 12);
        s10.addBox(0F, 0F, 0F, 6, 8, 1);
        s10.setRotationPoint(-5F, 12F, 3F);
        s10.setTextureSize(128, 128);
        s10.mirror = true;
        setRotation(s10, 0F, 1.579523F, 0F);
        s11 = new ModelRenderer(this, 17, 12);
        s11.addBox(0F, 0F, 0F, 6, 8, 1);
        s11.setRotationPoint(4F, 12F, 3F);
        s11.setTextureSize(128, 128);
        s11.mirror = true;
        setRotation(s11, 0F, 1.579523F, 0F);
        s12 = new ModelRenderer(this, 17, 8);
        s12.addBox(0F, 0F, 0F, 6, 2, 1);
        s12.setRotationPoint(-4F, 20F, 3F);
        s12.setTextureSize(128, 128);
        s12.mirror = true;
        setRotation(s12, 0F, 1.579523F, 0F);
        s13 = new ModelRenderer(this, 17, 8);
        s13.addBox(0F, 0F, 0F, 6, 2, 1);
        s13.setRotationPoint(-3F, 20F, 3F);
        s13.setTextureSize(128, 128);
        s13.mirror = true;
        setRotation(s13, 0F, 0F, 0F);
        s14 = new ModelRenderer(this, 17, 8);
        s14.addBox(0F, 0F, 0F, 6, 2, 1);
        s14.setRotationPoint(3F, 20F, 3F);
        s14.setTextureSize(128, 128);
        s14.mirror = true;
        setRotation(s14, 0F, 1.579523F, 0F);
        s15 = new ModelRenderer(this, 17, 8);
        s15.addBox(0F, 0F, 0F, 6, 2, 1);
        s15.setRotationPoint(-3F, 20F, -4F);
        s15.setTextureSize(128, 128);
        s15.mirror = true;
        setRotation(s15, 0F, 0F, 0F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z){
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void render(float f){
        s1.render(f);
        s2.render(f);
        s3.render(f);
        s4.render(f);
        s5.render(f);
        s6.render(f);
        s7.render(f);
        s8.render(f);
        s9.render(f);
        s10.render(f);
        s11.render(f);
        s12.render(f);
        s13.render(f);
        s14.render(f);
        s15.render(f);
    }

    @Override
    public String getName(){
        return "modelPhantomBooster";
    }

    @Override
    public boolean doesRotate(){
        return true;
    }
}
