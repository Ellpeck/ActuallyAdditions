/*
 * This file ("RenderTileEntity.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import de.ellpeck.actuallyadditions.mod.blocks.render.model.ModelBaseAA;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderTileEntity extends TileEntitySpecialRenderer{

    public ModelBaseAA theModel;
    public ResourceLocation resLoc;

    public RenderTileEntity(ModelBaseAA model){
        this.theModel = model;
        this.resLoc = new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/blocks/models/"+this.theModel.getName()+".png");
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5, int i){
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.0F, -2.0F, 0.0F);
        this.bindTexture(resLoc);

        if(theModel.doesRotate()){
            int meta = PosUtil.getMetadata(tile.getPos(), tile.getWorld());
            if(meta == 0){
                GlStateManager.rotate(180F, 0F, 1F, 0F);
            }
            if(meta == 1){
                GlStateManager.rotate(90F, 0F, 1F, 0F);
            }
            if(meta == 3){
                GlStateManager.rotate(270F, 0F, 1F, 0F);
            }
        }

        theModel.render(0.0625F);
        theModel.renderExtra(0.0625F, tile);

        GlStateManager.popMatrix();
    }

}
