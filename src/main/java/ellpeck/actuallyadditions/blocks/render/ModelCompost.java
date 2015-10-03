/*
 * This file ("ModelCompost.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.render;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;

public class ModelCompost extends ModelBaseAA{

    public ModelRenderer floor;
    public ModelRenderer wallOne;
    public ModelRenderer wallTwo;
    public ModelRenderer wallThree;
    public ModelRenderer wallFour;
    public ModelRenderer[] innerRawList = new ModelRenderer[13];
    public ModelRenderer innerDone;

    public ModelCompost(){
        this.textureWidth = 64;
        this.textureHeight = 128;
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

        for(int i = 0; i < this.innerRawList.length; i++){
            this.innerRawList[i] = new ModelRenderer(this, 0, 29);
            this.innerRawList[i].setRotationPoint(-6.0F, 10.0F, -6.0F);
            this.innerRawList[i].addBox(0.0F, 12-i, 0.0F, 12, i+1, 12, 0.0F);
        }

        this.innerDone = new ModelRenderer(this, 0, 54);
        this.innerDone.setRotationPoint(-6.0F, 10.0F, -6.0F);
        this.innerDone.addBox(0.0F, 0.0F, 0.0F, 12, 13, 12, 0.0F);
    }

    @Override
    public void renderExtra(float f, TileEntity tile){
        int meta = tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);
        if(meta > 0 && meta <= ConfigIntValues.COMPOST_AMOUNT.getValue()){
            int heightToDisplay = meta*13/ConfigIntValues.COMPOST_AMOUNT.getValue();
            if(heightToDisplay > 13){
                heightToDisplay = 13;
            }

            this.innerRawList[heightToDisplay-1].render(f);
        }
        else if(meta == ConfigIntValues.COMPOST_AMOUNT.getValue()+1){
            this.innerDone.render(f);
        }
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
