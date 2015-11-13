/*
 * This file ("ModelFishingNet.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.render.model;

import net.minecraft.client.model.ModelRenderer;

public class ModelFishingNet extends ModelBaseAA{

    public ModelRenderer s1;
    public ModelRenderer s2;
    public ModelRenderer s3;
    public ModelRenderer s4;
    public ModelRenderer s5;
    public ModelRenderer s6;
    public ModelRenderer s7;
    public ModelRenderer s8;
    public ModelRenderer s9;
    public ModelRenderer s10;
    public ModelRenderer s11;
    public ModelRenderer s12;
    public ModelRenderer s13;
    public ModelRenderer s14;
    public ModelRenderer s15;
    public ModelRenderer s16;

    public ModelFishingNet(){
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.s11 = new ModelRenderer(this, 0, 0);
        this.s11.mirror = true;
        this.s11.setRotationPoint(-3.5F, 23.0F, -8.0F);
        this.s11.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16, 0.0F);
        this.s9 = new ModelRenderer(this, 0, 0);
        this.s9.mirror = true;
        this.s9.setRotationPoint(-7.5F, 23.0F, -8.0F);
        this.s9.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16, 0.0F);
        this.s8 = new ModelRenderer(this, 0, 0);
        this.s8.setRotationPoint(-8.0F, 23.0F, 6.5F);
        this.s8.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
        this.s3 = new ModelRenderer(this, 0, 0);
        this.s3.setRotationPoint(-8.0F, 23.0F, -3.5F);
        this.s3.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
        this.s1 = new ModelRenderer(this, 0, 0);
        this.s1.setRotationPoint(-8.0F, 23.0F, -7.5F);
        this.s1.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
        this.s14 = new ModelRenderer(this, 0, 0);
        this.s14.mirror = true;
        this.s14.setRotationPoint(2.5F, 23.0F, -8.0F);
        this.s14.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16, 0.0F);
        this.s15 = new ModelRenderer(this, 0, 0);
        this.s15.mirror = true;
        this.s15.setRotationPoint(4.5F, 23.0F, -8.0F);
        this.s15.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16, 0.0F);
        this.s4 = new ModelRenderer(this, 0, 0);
        this.s4.setRotationPoint(-8.0F, 23.0F, -1.5F);
        this.s4.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
        this.s13 = new ModelRenderer(this, 0, 0);
        this.s13.mirror = true;
        this.s13.setRotationPoint(0.5F, 23.0F, -8.0F);
        this.s13.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16, 0.0F);
        this.s5 = new ModelRenderer(this, 0, 0);
        this.s5.setRotationPoint(-8.0F, 23.0F, 0.5F);
        this.s5.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
        this.s6 = new ModelRenderer(this, 0, 0);
        this.s6.setRotationPoint(-8.0F, 23.0F, 2.5F);
        this.s6.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
        this.s12 = new ModelRenderer(this, 0, 0);
        this.s12.mirror = true;
        this.s12.setRotationPoint(-1.5F, 23.0F, -8.0F);
        this.s12.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16, 0.0F);
        this.s16 = new ModelRenderer(this, 0, 0);
        this.s16.mirror = true;
        this.s16.setRotationPoint(6.5F, 23.0F, -8.0F);
        this.s16.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16, 0.0F);
        this.s2 = new ModelRenderer(this, 0, 0);
        this.s2.setRotationPoint(-8.0F, 23.0F, -5.5F);
        this.s2.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
        this.s7 = new ModelRenderer(this, 0, 0);
        this.s7.setRotationPoint(-8.0F, 23.0F, 4.5F);
        this.s7.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
        this.s10 = new ModelRenderer(this, 0, 0);
        this.s10.mirror = true;
        this.s10.setRotationPoint(-5.5F, 23.0F, -8.0F);
        this.s10.addBox(0.0F, 0.0F, 0.0F, 1, 1, 16, 0.0F);
    }

    @Override
    public void render(float f){
        this.s11.render(f);
        this.s9.render(f);
        this.s8.render(f);
        this.s3.render(f);
        this.s1.render(f);
        this.s14.render(f);
        this.s15.render(f);
        this.s4.render(f);
        this.s13.render(f);
        this.s5.render(f);
        this.s6.render(f);
        this.s12.render(f);
        this.s16.render(f);
        this.s2.render(f);
        this.s7.render(f);
        this.s10.render(f);
    }

    @Override
    public String getName(){
        return "modelFishingNet";
    }
}
