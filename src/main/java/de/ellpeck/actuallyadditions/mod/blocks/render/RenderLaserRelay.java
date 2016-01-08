/*
 * This file ("RenderLaserRelay.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import de.ellpeck.actuallyadditions.mod.blocks.render.model.ModelBaseAA;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;


public class RenderLaserRelay extends RenderTileEntity{

    public RenderLaserRelay(ModelBaseAA model){
        super(model);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5, int par6){
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.0F, -2.0F, 0.0F);
        this.bindTexture(resLoc);

        int meta = PosUtil.getMetadata(tile.getPos(), tile.getWorld());
        if(meta == 0){
            GlStateManager.rotate(180F, 1F, 0F, 0F);
            GlStateManager.translate(0F, -2F, 0F);
        }
        else if(meta == 3){
            GlStateManager.rotate(-90, 1F, 0F, 0F);
            GlStateManager.translate(0F, -1F, 1F);
        }
        else if(meta == 2){
            GlStateManager.rotate(90, 1F, 0F, 0F);
            GlStateManager.translate(0F, -1F, -1F);
        }
        else if(meta == 4){
            GlStateManager.rotate(90, 0F, 0F, 1F);
            GlStateManager.translate(1F, -1F, 0F);
        }
        else if(meta == 5){
            GlStateManager.rotate(90, 0F, 0F, -1F);
            GlStateManager.translate(-1F, -1F, 0F);
        }

        GlStateManager.scale(0.85F, 0.85F, 0.85F);
        GlStateManager.translate(0F, 0.2657F, 0F);
        theModel.render(0.0625F);
        GlStateManager.popMatrix();
    }

}
