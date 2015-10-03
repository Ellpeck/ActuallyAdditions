/*
 * This file ("ModelSmileyCloud.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.render;

import net.minecraft.client.model.ModelRenderer;

public class ModelSmileyCloud extends ModelBaseAA{

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
    ModelRenderer s16;

    public ModelSmileyCloud(){
        textureWidth = 64;
        textureHeight = 64;

        s1 = new ModelRenderer(this, 0, 0);
        s1.addBox(0F, 0F, 0F, 12, 10, 10);
        s1.setRotationPoint(-6F, 14F, -4F);
        s1.setTextureSize(64, 64);
        s1.mirror = true;
        setRotation(s1, 0F, 0F, 0F);
        s2 = new ModelRenderer(this, 45, 0);
        s2.addBox(0F, 0F, 0F, 1, 8, 8);
        s2.setRotationPoint(-7F, 15F, -3F);
        s2.setTextureSize(64, 64);
        s2.mirror = true;
        setRotation(s2, 0F, 0F, 0F);
        s3 = new ModelRenderer(this, 45, 0);
        s3.addBox(0F, 0F, 0F, 1, 8, 8);
        s3.setRotationPoint(6F, 15F, -3F);
        s3.setTextureSize(64, 64);
        s3.mirror = true;
        setRotation(s3, 0F, 0F, 0F);
        s4 = new ModelRenderer(this, 0, 21);
        s4.addBox(0F, 0F, 0F, 10, 8, 1);
        s4.setRotationPoint(-5F, 15F, 6F);
        s4.setTextureSize(64, 64);
        s4.mirror = true;
        setRotation(s4, 0F, 0F, 0F);
        s5 = new ModelRenderer(this, 23, 27);
        s5.addBox(0F, 0F, 0F, 10, 1, 8);
        s5.setRotationPoint(-5F, 13F, -3F);
        s5.setTextureSize(64, 64);
        s5.mirror = true;
        setRotation(s5, 0F, 0F, 0F);
        s6 = new ModelRenderer(this, 23, 21);
        s6.addBox(0F, 0F, 0F, 6, 1, 4);
        s6.setRotationPoint(-3F, 12F, -1F);
        s6.setTextureSize(64, 64);
        s6.mirror = true;
        setRotation(s6, 0F, 0F, 0F);
        s7 = new ModelRenderer(this, 45, 16);
        s7.addBox(0F, 0F, 0F, 6, 6, 1);
        s7.setRotationPoint(-3F, 16F, 7F);
        s7.setTextureSize(64, 64);
        s7.mirror = true;
        setRotation(s7, 0F, 0F, 0F);
        s8 = new ModelRenderer(this, 0, 31);
        s8.addBox(0F, 0F, 0F, 1, 6, 6);
        s8.setRotationPoint(-8F, 16F, -2F);
        s8.setTextureSize(64, 64);
        s8.mirror = true;
        setRotation(s8, 0F, 0F, 0F);
        s9 = new ModelRenderer(this, 0, 31);
        s9.addBox(0F, 0F, 0F, 1, 6, 6);
        s9.setRotationPoint(7F, 16F, -2F);
        s9.setTextureSize(64, 64);
        s9.mirror = true;
        setRotation(s9, 0F, 0F, 0F);
        s10 = new ModelRenderer(this, 15, 37);
        s10.addBox(0F, 0F, 0F, 6, 1, 1);
        s10.setRotationPoint(-3F, 20F, -5F);
        s10.setTextureSize(64, 64);
        s10.mirror = true;
        setRotation(s10, 0F, 0F, 0F);
        s11 = new ModelRenderer(this, 15, 31);
        s11.addBox(0F, 1F, 0F, 1, 1, 1);
        s11.setRotationPoint(-4F, 18F, -5F);
        s11.setTextureSize(64, 64);
        s11.mirror = true;
        setRotation(s11, 0F, 0F, 0F);
        s12 = new ModelRenderer(this, 15, 31);
        s12.addBox(0F, 1F, 0F, 1, 1, 1);
        s12.setRotationPoint(3F, 18F, -5F);
        s12.setTextureSize(64, 64);
        s12.mirror = true;
        setRotation(s12, 0F, 0F, 0F);
        s13 = new ModelRenderer(this, 15, 40);
        s13.addBox(0F, 0F, 0F, 2, 2, 1);
        s13.setRotationPoint(-3F, 15F, -4.5F);
        s13.setTextureSize(64, 64);
        s13.mirror = true;
        setRotation(s13, 0F, 0F, 0F);
        s14 = new ModelRenderer(this, 15, 40);
        s14.addBox(0F, 0F, 0F, 2, 2, 1);
        s14.setRotationPoint(1F, 15F, -4.5F);
        s14.setTextureSize(64, 64);
        s14.mirror = true;
        setRotation(s14, 0F, 0F, 0F);
        s15 = new ModelRenderer(this, 30, 37);
        s15.addBox(0F, 0F, 0F, 1, 1, 1);
        s15.setRotationPoint(-2.5F, 15.5F, -4.7F);
        s15.setTextureSize(64, 64);
        s15.mirror = true;
        setRotation(s15, 0F, 0F, 0F);
        s16 = new ModelRenderer(this, 30, 37);
        s16.addBox(0F, 0F, 0F, 1, 1, 1);
        s16.setRotationPoint(1.5F, 15.5F, -4.7F);
        s16.setTextureSize(64, 64);
        s16.mirror = true;
        setRotation(s16, 0F, 0F, 0F);
    }

    @Override
    public boolean doesRotate(){
        return true;
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
        s16.render(f);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z){
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public String getName(){
        return "modelSmileyCloud";
    }
}
