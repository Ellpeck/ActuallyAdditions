package ellpeck.someprettytechystuff.blocks.models;

import net.minecraft.client.model.ModelRenderer;

public class ModelCrucible extends ModelBaseG{
    public ModelRenderer floor, rimOne, rimTwo, rimThree, rimFour, wallOne, wallTwo, wallThree, wallFour, supportOne, supportTwo, supportThree, supportFour;

    public ModelCrucible() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.supportTwo = new ModelRenderer(this, 0, 0);
        this.supportTwo.setRotationPoint(-6.5F, 21.0F, 4.5F);
        this.supportTwo.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2);
        this.wallTwo = new ModelRenderer(this, 0, 0);
        this.wallTwo.setRotationPoint(7.0F, 9.5F, -8.0F);
        this.wallTwo.addBox(0.0F, 0.0F, 0.0F, 1, 9, 16);
        this.floor = new ModelRenderer(this, 0, 0);
        this.floor.setRotationPoint(-6.0F, 21.5F, -6.0F);
        this.floor.addBox(0.0F, 0.0F, 0.0F, 12, 1, 12);
        this.rimThree = new ModelRenderer(this, 0, 0);
        this.rimThree.setRotationPoint(6.0F, 18.5F, -6.0F);
        this.rimThree.addBox(0.0F, 0.0F, 0.0F, 1, 3, 12);
        this.wallThree = new ModelRenderer(this, 0, 0);
        this.wallThree.setRotationPoint(-7.0F, 9.5F, 7.0F);
        this.wallThree.addBox(0.0F, 0.0F, 0.0F, 14, 9, 1);
        this.rimTwo = new ModelRenderer(this, 0, 0);
        this.rimTwo.setRotationPoint(-7.0F, 18.5F, 6.0F);
        this.rimTwo.addBox(0.0F, 0.0F, 0.0F, 14, 3, 1);
        this.rimOne = new ModelRenderer(this, 0, 0);
        this.rimOne.setRotationPoint(-7.0F, 18.5F, -7.0F);
        this.rimOne.addBox(0.0F, 0.0F, 0.0F, 14, 3, 1);
        this.supportOne = new ModelRenderer(this, 0, 0);
        this.supportOne.setRotationPoint(4.5F, 21.0F, 4.5F);
        this.supportOne.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2);
        this.wallOne = new ModelRenderer(this, 0, 0);
        this.wallOne.setRotationPoint(-8.0F, 9.5F, -8.0F);
        this.wallOne.addBox(0.0F, 0.0F, 0.0F, 1, 9, 16);
        this.supportFour = new ModelRenderer(this, 0, 0);
        this.supportFour.setRotationPoint(-6.5F, 21.0F, -6.5F);
        this.supportFour.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2);
        this.wallFour = new ModelRenderer(this, 0, 0);
        this.wallFour.setRotationPoint(-7.0F, 9.5F, -8.0F);
        this.wallFour.addBox(0.0F, 0.0F, 0.0F, 14, 9, 1);
        this.rimFour = new ModelRenderer(this, 0, 0);
        this.rimFour.setRotationPoint(-7.0F, 18.5F, -6.0F);
        this.rimFour.addBox(0.0F, 0.0F, 0.0F, 1, 3, 12);
        this.supportThree = new ModelRenderer(this, 0, 0);
        this.supportThree.setRotationPoint(4.5F, 21.0F, -6.5F);
        this.supportThree.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2);
    }

    public void render(float f){
        this.supportTwo.render(f);
        this.wallTwo.render(f);
        this.floor.render(f);
        this.rimThree.render(f);
        this.wallThree.render(f);
        this.rimTwo.render(f);
        this.rimOne.render(f);
        this.supportOne.render(f);
        this.wallOne.render(f);
        this.supportFour.render(f);
        this.wallFour.render(f);
        this.rimFour.render(f);
        this.supportThree.render(f);
    }
}
