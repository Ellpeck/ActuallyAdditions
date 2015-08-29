/*
 * This file ("ModelTorch.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.gadget;

import ellpeck.actuallyadditions.blocks.render.ModelBaseAA;
import net.minecraft.client.model.ModelRenderer;

public class ModelTorch extends ModelBaseAA{

    public ModelRenderer s;

    public ModelTorch(){
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.s = new ModelRenderer(this, 0, 0);
        this.s.setRotationPoint(-1.0F, 14.0F, -1.0F);
        this.s.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
    }

    @Override
    public void render(float f){
        this.s.render(f);
    }

    @Override
    public String getName(){
        return "modelHose";
    }
}
