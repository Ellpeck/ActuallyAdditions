/*
 * This file ("ModelToolTable.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.render;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * Made by Canitzp.
 * Thanks.
 */
public class ModelToolTable extends ModelBaseAA{

    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer lag4;
    ModelRenderer tableTop;
    ModelRenderer back1;
    ModelRenderer back2;
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer bottom;
    ModelRenderer side3;
    ModelRenderer onTop;
    ModelRenderer random1;
    ModelRenderer random2;
    ModelRenderer random3;
    ModelRenderer random4;
    ModelRenderer random5;
    ModelRenderer random6;
    ModelRenderer random7;
    ModelRenderer random8;
    ModelRenderer random9;

    public ModelToolTable(){
        textureWidth = 128;
        textureHeight = 128;

        leg1 = new ModelRenderer(this, 0, 0);
        leg1.addBox(0F, 0F, 0F, 2, 10, 2);
        leg1.setRotationPoint(-8F, 14F, -8F);
        leg1.setTextureSize(128, 128);
        leg1.mirror = true;
        setRotation(leg1, 0F, 0F, 0F);
        leg2 = new ModelRenderer(this, 0, 0);
        leg2.addBox(0F, 0F, 0F, 2, 10, 2);
        leg2.setRotationPoint(6F, 14F, -8F);
        leg2.setTextureSize(128, 128);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, 0F);
        leg3 = new ModelRenderer(this, 0, 0);
        leg3.addBox(0F, 0F, 0F, 2, 10, 2);
        leg3.setRotationPoint(-8F, 14F, 6F);
        leg3.setTextureSize(128, 128);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
        lag4 = new ModelRenderer(this, 0, 0);
        lag4.addBox(0F, 0F, 0F, 2, 10, 2);
        lag4.setRotationPoint(6F, 14F, 6F);
        lag4.setTextureSize(128, 128);
        lag4.mirror = true;
        setRotation(lag4, 0F, 0F, 0F);
        tableTop = new ModelRenderer(this, 0, 13);
        tableTop.addBox(0F, 0F, 0F, 16, 2, 16);
        tableTop.setRotationPoint(-8F, 12F, -8F);
        tableTop.setTextureSize(128, 128);
        tableTop.mirror = true;
        setRotation(tableTop, 0F, 0F, 0F);
        back1 = new ModelRenderer(this, 9, 0);
        back1.addBox(0F, 0F, 0F, 12, 10, 1);
        back1.setRotationPoint(-6F, 14F, 7F);
        back1.setTextureSize(128, 128);
        back1.mirror = true;
        setRotation(back1, 0F, 0F, 0F);
        back2 = new ModelRenderer(this, 0, 32);
        back2.addBox(0F, 0F, 0F, 16, 16, 1);
        back2.setRotationPoint(-8F, -4F, 7F);
        back2.setTextureSize(128, 128);
        back2.mirror = true;
        setRotation(back2, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 9, 0);
        side1.addBox(0F, 0F, 0F, 12, 10, 1);
        side1.setRotationPoint(-7F, 14F, 6F);
        side1.setTextureSize(128, 128);
        side1.mirror = true;
        setRotation(side1, 0F, 1.579523F, 0F);
        side2 = new ModelRenderer(this, 9, 0);
        side2.addBox(0F, 0F, 0F, 12, 10, 1);
        side2.setRotationPoint(6F, 14F, 6F);
        side2.setTextureSize(128, 128);
        side2.mirror = true;
        setRotation(side2, 0F, 1.579523F, 0F);
        bottom = new ModelRenderer(this, 36, 0);
        bottom.addBox(0F, 0F, 0F, 8, 12, 1);
        bottom.setRotationPoint(-6F, 24F, 6F);
        bottom.setTextureSize(128, 128);
        bottom.mirror = true;
        setRotation(bottom, 1.579523F, 1.579523F, 0F);
        side3 = new ModelRenderer(this, 9, 0);
        side3.addBox(0F, 0F, 0F, 12, 9, 1);
        side3.setRotationPoint(-6F, 14F, -2F);
        side3.setTextureSize(128, 128);
        side3.mirror = true;
        setRotation(side3, 0F, 0F, 0F);
        onTop = new ModelRenderer(this, 0, 50);
        onTop.addBox(0F, 0F, 0F, 12, 1, 12);
        onTop.setRotationPoint(-6F, 11.8F, -6F);
        onTop.setTextureSize(128, 128);
        onTop.mirror = true;
        setRotation(onTop, 0F, 0F, 0F);
        random1 = new ModelRenderer(this, 35, 32);
        random1.addBox(0F, 0F, 0F, 1, 1, 1);
        random1.setRotationPoint(-7.5F, 15F, 3F);
        random1.setTextureSize(128, 128);
        random1.mirror = true;
        setRotation(random1, 0F, 0F, 0F);
        random2 = new ModelRenderer(this, 35, 32);
        random2.addBox(0F, 0F, 0F, 1, 1, 1);
        random2.setRotationPoint(-7.5F, 15F, -4F);
        random2.setTextureSize(128, 128);
        random2.mirror = true;
        setRotation(random2, 0F, 0F, 0F);
        random3 = new ModelRenderer(this, 35, 32);
        random3.addBox(0F, 0F, 0F, 1, 1, 1);
        random3.setRotationPoint(6.5F, 15F, 3F);
        random3.setTextureSize(128, 128);
        random3.mirror = true;
        setRotation(random3, 0F, 0F, 0F);
        random4 = new ModelRenderer(this, 35, 32);
        random4.addBox(0F, 0F, 0F, 1, 1, 1);
        random4.setRotationPoint(6.5F, 15F, -4F);
        random4.setTextureSize(128, 128);
        random4.mirror = true;
        setRotation(random4, 0F, 0F, 0F);
        random5 = new ModelRenderer(this, 55, 0);
        random5.addBox(0F, 0F, 0F, 1, 2, 1);
        random5.setRotationPoint(-5F, 1F, 6F);
        random5.setTextureSize(128, 128);
        random5.mirror = true;
        setRotation(random5, 0.5235988F, 0F, 0F);
        random6 = new ModelRenderer(this, 55, 0);
        random6.addBox(0F, 0F, 0F, 1, 2, 1);
        random6.setRotationPoint(4F, 1F, 6F);
        random6.setTextureSize(128, 128);
        random6.mirror = true;
        setRotation(random6, 0.5235988F, 0F, 0F);
        random7 = new ModelRenderer(this, 0, 64);
        random7.addBox(0F, 0F, 0F, 11, 1, 4);
        random7.setRotationPoint(-5.5F, 0F, 3.5F);
        random7.setTextureSize(128, 128);
        random7.mirror = true;
        setRotation(random7, 0F, 0F, 0F);
        random8 = new ModelRenderer(this, 35, 35);
        random8.addBox(0F, 0F, 0F, 1, 1, 1);
        random8.setRotationPoint(-3F, 3F, 6F);
        random8.setTextureSize(128, 128);
        random8.mirror = true;
        setRotation(random8, 0F, 0F, 0F);
        random9 = new ModelRenderer(this, 35, 35);
        random9.addBox(0F, 0F, 0F, 1, 1, 1);
        random9.setRotationPoint(2F, 3F, 6F);
        random9.setTextureSize(128, 128);
        random9.mirror = true;
        setRotation(random9, 0F, 0F, 0F);
    }

    @Override
    public void render(float f){
        leg1.render(f);
        leg2.render(f);
        leg3.render(f);
        lag4.render(f);
        tableTop.render(f);
        back1.render(f);
        back2.render(f);
        side1.render(f);
        side2.render(f);
        bottom.render(f);
        side3.render(f);
        onTop.render(f);
        random1.render(f);
        random2.render(f);
        random3.render(f);
        random4.render(f);
        random5.render(f);
        random6.render(f);
        random7.render(f);
        random8.render(f);
        random9.render(f);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z){
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public boolean doesRotate(){
        return true;
    }

    @Override
    public String getName(){
        return "modelToolTable";
    }

    @Override
    public void renderExtra(float f, TileEntity tile){
        GL11.glPushMatrix();
        {
            GL11.glTranslated(-7.25*f, 17.75*f, 2.25*f);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glRotatef(90F, 0F, 1F, 0F);
            GL11.glRotatef(-40F, 0F, 0F, 1F);
            AssetUtil.renderItem(new ItemStack(Items.shears), 0);
        }
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        {
            GL11.glTranslated(5*f, 10.65*f, 4.5*f);
            GL11.glScalef(0.15F, 0.15F, 0.15F);
            GL11.glRotatef(90F, 1F, 0F, 0F);
            GL11.glRotatef(45F, 0F, 0F, 1F);
            AssetUtil.renderBlock(InitBlocks.blockPhantomLiquiface, 0);
        }
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        {
            GL11.glTranslated(-3.25*f, 11.35*f, -1*f);
            GL11.glScalef(0.35F, 0.35F, 0.35F);
            GL11.glRotatef(90F, 1F, 0F, 0F);
            AssetUtil.renderItem(new ItemStack(Items.stick), 0);
        }
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        {
            GL11.glTranslated(-2.25*f, 11.35*f, -4.5*f);
            GL11.glScalef(0.25F, 0.25F, 0.25F);
            GL11.glRotatef(90F, 1F, 0F, 0F);
            GL11.glRotatef(75F, 0F, 0F, 1F);
            AssetUtil.renderItem(new ItemStack(Items.iron_ingot), 0);
        }
        GL11.glPopMatrix();
    }
}
