package ellpeck.gemification.blocks.models;

import net.minecraft.client.model.ModelRenderer;

public class ModelCrucibleFire extends ModelBaseG{
    public ModelRenderer floor, wallOne, wallTwo, wallThree, wallFour, supportOne, supportTwo, supportThree, supportFour, outsideSupOne, outsideSupTwo, outsideSupThree, outsideSupFour, topFloor, floorBlock;

    public ModelCrucibleFire() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.wallFour = new ModelRenderer(this, 0, 0);
        this.wallFour.setRotationPoint(-4.5F, 15.0F, 4.5F);
        this.wallFour.addBox(0.0F, 0.0F, 0.0F, 9, 3, 1);
        this.outsideSupFour = new ModelRenderer(this, 0, 30);
        this.outsideSupFour.setRotationPoint(-8.0F, 9.0F, 6.0F);
        this.outsideSupFour.addBox(0.0F, 0.0F, 0.0F, 2, 15, 2);
        this.supportFour = new ModelRenderer(this, 0, 0);
        this.supportFour.setRotationPoint(3.5F, 19.0F, -4.5F);
        this.supportFour.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
        this.outsideSupThree = new ModelRenderer(this, 0, 30);
        this.outsideSupThree.setRotationPoint(-8.0F, 9.0F, -8.0F);
        this.outsideSupThree.addBox(0.0F, 0.0F, 0.0F, 2, 15, 2);
        this.outsideSupOne = new ModelRenderer(this, 0, 30);
        this.outsideSupOne.setRotationPoint(6.0F, 9.0F, 6.0F);
        this.outsideSupOne.addBox(0.0F, 0.0F, 0.0F, 2, 15, 2);
        this.floorBlock = new ModelRenderer(this, 0, 17);
        this.floorBlock.setRotationPoint(-5.0F, 21.0F, -5.0F);
        this.floorBlock.addBox(0.0F, 0.0F, 0.0F, 10, 3, 10);
        this.wallOne = new ModelRenderer(this, 0, 0);
        this.wallOne.setRotationPoint(4.5F, 15.0F, -5.5F);
        this.wallOne.addBox(0.0F, 0.0F, 0.0F, 1, 3, 11);
        this.wallTwo = new ModelRenderer(this, 0, 0);
        this.wallTwo.setRotationPoint(-5.5F, 15.0F, -5.5F);
        this.wallTwo.addBox(0.0F, 0.0F, 0.0F, 1, 3, 11);
        this.supportOne = new ModelRenderer(this, 0, 0);
        this.supportOne.setRotationPoint(3.5F, 19.0F, 3.5F);
        this.supportOne.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
        this.wallThree = new ModelRenderer(this, 0, 0);
        this.wallThree.setRotationPoint(-4.5F, 15.0F, -5.5F);
        this.wallThree.addBox(0.0F, 0.0F, 0.0F, 9, 3, 1);
        this.outsideSupTwo = new ModelRenderer(this, 0, 30);
        this.outsideSupTwo.setRotationPoint(6.0F, 9.0F, -8.0F);
        this.outsideSupTwo.addBox(0.0F, 0.0F, 0.0F, 2, 15, 2);
        this.floor = new ModelRenderer(this, 0, 0);
        this.floor.mirror = true;
        this.floor.setRotationPoint(-4.5F, 18.0F, -4.5F);
        this.floor.addBox(0.0F, 0.0F, 0.0F, 9, 1, 9);
        this.supportThree = new ModelRenderer(this, 0, 0);
        this.supportThree.setRotationPoint(-4.5F, 19.0F, -4.5F);
        this.supportThree.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
        this.topFloor = new ModelRenderer(this, 0, 30);
        this.topFloor.setRotationPoint(-8.0F, 8.0F, -8.0F);
        this.topFloor.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16);
        this.supportTwo = new ModelRenderer(this, 0, 0);
        this.supportTwo.setRotationPoint(-4.5F, 19.0F, 3.5F);
        this.supportTwo.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    }

    public void render(float f){
        this.wallFour.render(f);
        this.outsideSupFour.render(f);
        this.supportFour.render(f);
        this.outsideSupThree.render(f);
        this.outsideSupOne.render(f);
        this.floorBlock.render(f);
        this.wallOne.render(f);
        this.wallTwo.render(f);
        this.supportOne.render(f);
        this.wallThree.render(f);
        this.outsideSupTwo.render(f);
        this.floor.render(f);
        this.supportThree.render(f);
        this.topFloor.render(f);
        this.supportTwo.render(f);
    }
}
