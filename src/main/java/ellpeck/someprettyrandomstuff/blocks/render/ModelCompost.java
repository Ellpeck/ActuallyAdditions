package ellpeck.someprettyrandomstuff.blocks.render;

import net.minecraft.client.model.ModelRenderer;

public class ModelCompost extends ModelBaseSPRS{

    public ModelRenderer floor;
    public ModelRenderer wallOne;
    public ModelRenderer wallTwo;
    public ModelRenderer wallThree;
    public ModelRenderer wallFour;

    public ModelCompost(){
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.wallThree = new ModelRenderer(this, 0, 0);
        this.wallThree.setRotationPoint(-6.0F, 8.0F, 6.0F);
        this.wallThree.addBox(0.0F, 0.0F, 0.0F, 12, 15, 1, 0.0F);
        this.wallFour = new ModelRenderer(this, 0, 0);
        this.wallFour.setRotationPoint(-6.0F, 8.0F, -7.0F);
        this.wallFour.addBox(0.0F, 0.0F, 0.0F, 12, 15, 1, 0.0F);
        this.wallOne = new ModelRenderer(this, 0, 0);
        this.wallOne.setRotationPoint(-7.0F, 8.0F, -7.0F);
        this.wallOne.addBox(0.0F, 0.0F, 0.0F, 1, 15, 14, 0.0F);
        this.wallTwo = new ModelRenderer(this, 0, 0);
        this.wallTwo.setRotationPoint(6.0F, 8.0F, -7.0F);
        this.wallTwo.addBox(0.0F, 0.0F, 0.0F, 1, 15, 14, 0.0F);
        this.floor = new ModelRenderer(this, 0, 0);
        this.floor.setRotationPoint(-7.0F, 23.0F, -7.0F);
        this.floor.addBox(0.0F, 0.0F, 0.0F, 14, 1, 14, 0.0F);
    }

    @Override
    public void render(float f){
        this.wallThree.render(f);
        this.wallFour.render(f);
        this.wallOne.render(f);
        this.wallTwo.render(f);
        this.floor.render(f);
    }

    @Override
    public String getName(){
        return "modelCompost";
    }
}
