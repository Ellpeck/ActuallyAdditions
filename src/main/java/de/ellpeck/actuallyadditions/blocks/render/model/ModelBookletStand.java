/*
 * This file ("ModelBookletStand.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.blocks.render.model;

import net.minecraft.client.model.ModelRenderer;

public class ModelBookletStand extends ModelBaseAA{

    ModelRenderer body1;
    ModelRenderer bottom1;
    ModelRenderer bottom2;
    ModelRenderer body2;
    ModelRenderer body3;
    ModelRenderer book1;
    ModelRenderer book2;
    ModelRenderer book3;
    ModelRenderer book4;

    public ModelBookletStand(){
        textureWidth = 64;
        textureHeight = 64;

        body1 = new ModelRenderer(this, 0, 0);
        body1.addBox(0F, 0F, 0F, 14, 8, 1);
        body1.setRotationPoint(-7F, 17F, 1F);
        body1.setTextureSize(64, 64);
        body1.mirror = true;
        setRotation(body1, -0.7853982F, 0F, 0F);
        bottom1 = new ModelRenderer(this, 25, 6);
        bottom1.addBox(0F, 0F, 0F, 1, 1, 8);
        bottom1.setRotationPoint(-5F, 23F, -5F);
        bottom1.setTextureSize(64, 64);
        bottom1.mirror = true;
        setRotation(bottom1, 0F, 0F, 0F);
        bottom2 = new ModelRenderer(this, 25, 6);
        bottom2.addBox(0F, 0F, 0F, 1, 1, 8);
        bottom2.setRotationPoint(4F, 23F, -5F);
        bottom2.setTextureSize(64, 64);
        bottom2.mirror = true;
        setRotation(bottom2, 0F, 0F, 0F);
        body2 = new ModelRenderer(this, 0, 10);
        body2.addBox(0F, 0F, 0F, 14, 1, 2);
        body2.setRotationPoint(-7F, 20.91F, -5F);
        body2.setTextureSize(64, 64);
        body2.mirror = true;
        setRotation(body2, -0.7853982F, 0F, 0F);
        body3 = new ModelRenderer(this, 0, 14);
        body3.addBox(0F, 0F, 0F, 10, 3, 1);
        body3.setRotationPoint(-5F, 20F, -1F);
        body3.setTextureSize(64, 64);
        body3.mirror = true;
        setRotation(body3, 0F, 0F, 0F);
        book1 = new ModelRenderer(this, 36, 0);
        book1.addBox(0F, 0F, 0F, 8, 10, 0);
        book1.setRotationPoint(0F, 15F, 3.1F);
        book1.setTextureSize(64, 64);
        book1.mirror = true;
        setRotation(book1, -0.837758F, 0.0872665F, 0F);
        book2 = new ModelRenderer(this, 36, 0);
        book2.addBox(0F, 0F, 0F, 8, 10, 0);
        book2.setRotationPoint(-8F, 15F, 3.1F);
        book2.setTextureSize(64, 64);
        book2.mirror = true;
        setRotation(book2, -0.837758F, 0F, 0F);
        book3 = new ModelRenderer(this, 0, 19);
        book3.addBox(0F, 0F, 0F, 7, 8, 1);
        book3.setRotationPoint(7F, 16F, 1.2F);
        book3.setTextureSize(64, 64);
        book3.mirror = true;
        setRotation(book3, 0.837758F, -3.054326F, 0F);
        book4 = new ModelRenderer(this, 0, 19);
        book4.addBox(0F, 0F, 0F, 7, 8, 1);
        book4.setRotationPoint(-7F, 15.3F, 1.2F);
        book4.setTextureSize(64, 64);
        book4.mirror = true;
        setRotation(book4, -0.837758F, 0F, 0F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z){
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void render(float f){
        body1.render(f);
        bottom1.render(f);
        bottom2.render(f);
        body2.render(f);
        body3.render(f);
        book1.render(f);
        book2.render(f);
        book3.render(f);
        book4.render(f);
    }

    @Override
    public String getName(){
        return "modelBookletStand";
    }

    @Override
    public boolean doesRotate(){
        return true;
    }
}
