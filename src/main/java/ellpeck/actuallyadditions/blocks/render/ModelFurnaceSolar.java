package ellpeck.actuallyadditions.blocks.render;

import net.minecraft.client.model.ModelRenderer;

public class ModelFurnaceSolar extends ModelBaseAA{

    public ModelRenderer s;

    public ModelFurnaceSolar(){
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.s = new ModelRenderer(this, 0, 0);
        this.s.setRotationPoint(-8.0F, 21.0F, -8.0F);
        this.s.addBox(0.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
    }

    @Override
    public void render(float f){
        this.s.render(f);
    }

    @Override
    public String getName(){
        return "modelFurnaceSolar";
    }
}
