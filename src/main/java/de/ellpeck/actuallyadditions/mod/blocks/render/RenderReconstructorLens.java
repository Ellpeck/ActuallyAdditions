/*
 * This file ("RenderReconstructorLens.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;


import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

//TODO Fix Reconstructor Lens rendering
public class RenderReconstructorLens extends TileEntitySpecialRenderer{

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5, int par6){
        if(!(tile instanceof TileEntityAtomicReconstructor)){
            return;
        }
        ItemStack stack = ((TileEntityAtomicReconstructor)tile).getStackInSlot(0);

        if(stack != null && stack.getItem() instanceof ILensItem){
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

            int meta = PosUtil.getMetadata(tile.getPos(), tile.getWorld());
            if(meta == 0){
                GlStateManager.translate(0F, -0.5F, 0F);
                GlStateManager.rotate(90F, 1F, 0F, 0F);
            }
            if(meta == 1){
                GlStateManager.translate(0F, -1.5F-0.5F/16F, 0F);
                GlStateManager.rotate(90F, 1F, 0F, 0F);
            }
            if(meta == 2){
                GlStateManager.translate(0F, -1F, 0F);
                GlStateManager.translate(0F, 0F, -0.5F);
            }
            if(meta == 3){
                GlStateManager.translate(0F, -1F, 0F);
                GlStateManager.translate(0F, 0F, 0.5F+0.5F/16F);
            }
            if(meta == 4){
                GlStateManager.translate(0F, -1F, 0F);
                GlStateManager.translate(0.5F+0.5F/16F, 0F, 0F);
                GlStateManager.rotate(90F, 0F, 1F, 0F);
            }
            if(meta == 5){
                GlStateManager.translate(0F, -1F, 0F);
                GlStateManager.translate(-0.5F, 0F, 0F);
                GlStateManager.rotate(90F, 0F, 1F, 0F);
            }

            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            AssetUtil.renderItemInWorld(stack);

            GlStateManager.popMatrix();
        }
    }
}
