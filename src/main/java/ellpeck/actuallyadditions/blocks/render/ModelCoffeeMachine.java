/*
 * This file ("ModelCoffeeMachine.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.render;

import net.minecraft.client.model.ModelRenderer;

/**
 * Made by Canitzp.
 * Thanks.
 */
public class ModelCoffeeMachine extends ModelBaseAA{

    ModelRenderer p1;
    ModelRenderer p2;
    ModelRenderer p3;
    ModelRenderer p4;
    ModelRenderer p5;
    ModelRenderer p6;
    ModelRenderer p7;
    ModelRenderer p8;
    ModelRenderer p9;
    ModelRenderer p10;
    ModelRenderer p11;
    ModelRenderer p12;
    ModelRenderer p13;

    public ModelCoffeeMachine(){
        textureWidth = 128;
        textureHeight = 128;

        p1 = new ModelRenderer(this, 0, 0);
        p1.addBox(0F, 0F, 0F, 10, 1, 14);
        p1.setRotationPoint(-5F, 23F, -7F);
        p1.setTextureSize(128, 128);
        p1.mirror = true;
        setRotation(p1, 0F, 0F, 0F);
        p2 = new ModelRenderer(this, 49, 0);
        p2.addBox(0F, 0F, 0F, 10, 8, 6);
        p2.setRotationPoint(-5F, 15F, 1F);
        p2.setTextureSize(128, 128);
        p2.mirror = true;
        setRotation(p2, 0F, 0F, 0F);
        p3 = new ModelRenderer(this, 0, 16);
        p3.addBox(0F, 0F, 0F, 10, 2, 11);
        p3.setRotationPoint(-5F, 13F, -4F);
        p3.setTextureSize(128, 128);
        p3.mirror = true;
        setRotation(p3, 0F, 0F, 0F);
        p4 = new ModelRenderer(this, 43, 16);
        p4.addBox(0F, 0F, 0F, 8, 3, 8);
        p4.setRotationPoint(-4F, 10F, -1F);
        p4.setTextureSize(128, 128);
        p4.mirror = true;
        setRotation(p4, 0F, 0F, 0F);
        p5 = new ModelRenderer(this, 0, 30);
        p5.addBox(0F, 0F, 0F, 2, 1, 2);
        p5.setRotationPoint(-1F, 15F, -3.5F);
        p5.setTextureSize(128, 128);
        p5.mirror = true;
        setRotation(p5, 0F, 0F, 0F);
        p6 = new ModelRenderer(this, 82, 0);
        p6.addBox(0F, 0F, 0F, 4, 5, 1);
        p6.setRotationPoint(-2F, 17F, -1F);
        p6.setTextureSize(128, 128);
        p6.mirror = true;
        setRotation(p6, 0F, 0F, 0F);
        p7 = new ModelRenderer(this, 82, 0);
        p7.addBox(0F, 0F, 0F, 4, 5, 1);
        p7.setRotationPoint(-2F, 17F, -6F);
        p7.setTextureSize(128, 128);
        p7.mirror = true;
        setRotation(p7, 0F, 0F, 0F);
        p8 = new ModelRenderer(this, 82, 0);
        p8.addBox(0F, 0F, 0F, 4, 5, 1);
        p8.setRotationPoint(2F, 17F, -1F);
        p8.setTextureSize(128, 128);
        p8.mirror = true;
        setRotation(p8, 0F, 1.570796F, 0F);
        p9 = new ModelRenderer(this, 82, 0);
        p9.addBox(0F, 0F, 0F, 4, 5, 1);
        p9.setRotationPoint(-3F, 17F, -1F);
        p9.setTextureSize(128, 128);
        p9.mirror = true;
        setRotation(p9, 0F, 1.570796F, 0F);
        p10 = new ModelRenderer(this, 93, 0);
        p10.addBox(0F, 0F, 0F, 4, 1, 4);
        p10.setRotationPoint(-2F, 22F, -5F);
        p10.setTextureSize(128, 128);
        p10.mirror = true;
        setRotation(p10, 0F, 0F, 0F);
        p11 = new ModelRenderer(this, 82, 7);
        p11.addBox(0F, 0F, 0F, 1, 1, 2);
        p11.setRotationPoint(-4F, 18F, -4F);
        p11.setTextureSize(128, 128);
        p11.mirror = true;
        setRotation(p11, 0F, 0F, 0F);
        p12 = new ModelRenderer(this, 82, 7);
        p12.addBox(0F, 0F, 0F, 1, 1, 2);
        p12.setRotationPoint(-4F, 21F, -4F);
        p12.setTextureSize(128, 128);
        p12.mirror = true;
        setRotation(p12, 0F, 0F, 0F);
        p13 = new ModelRenderer(this, 89, 7);
        p13.addBox(0F, 0F, 0F, 1, 2, 2);
        p13.setRotationPoint(-5F, 19F, -4F);
        p13.setTextureSize(128, 128);
        p13.mirror = true;
        setRotation(p13, 0F, 0F, 0F);
    }

    @Override
    public void render(float f){
        p1.render(f);
        p2.render(f);
        p3.render(f);
        p4.render(f);
        p5.render(f);
        p6.render(f);
        p7.render(f);
        p8.render(f);
        p9.render(f);
        p10.render(f);
        p11.render(f);
        p12.render(f);
        p13.render(f);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z){
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public String getName(){
        return "modelCoffeeMachine";
    }

    @Override
    public boolean doesRotate(){
        return true;
    }
}
