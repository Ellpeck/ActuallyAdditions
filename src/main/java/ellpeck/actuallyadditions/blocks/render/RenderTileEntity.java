/*
 * This file ("RenderTileEntity.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.render;

import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderTileEntity extends TileEntitySpecialRenderer{

    public ModelBaseAA theModel;
    protected ResourceLocation resLoc;

    public RenderTileEntity(ModelBaseAA model){
        this.theModel = model;
        this.resLoc = new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/blocks/models/"+this.theModel.getName()+".png");
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5){
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.0F, -2.0F, 0.0F);
        this.bindTexture(resLoc);

        if(theModel.doesRotate()){
            int meta = tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);
            if(meta == 0) GL11.glRotatef(180F, 0F, 1F, 0F);
            if(meta == 1) GL11.glRotatef(90F, 0F, 1F, 0F);
            if(meta == 3) GL11.glRotatef(270F, 0F, 1F, 0F);
        }

        theModel.render(0.0625F);
        theModel.renderExtra(0.0625F, tile);

        GL11.glPopMatrix();
    }

}
