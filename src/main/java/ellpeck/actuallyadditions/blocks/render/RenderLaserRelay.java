/*
 * This file ("RenderLaserRelay.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.render;

import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;


public class RenderLaserRelay extends RenderTileEntity{

    public RenderLaserRelay(ModelBaseAA model){
        super(model);
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
            if(meta == 0){
                GL11.glRotatef(180F, 1F, 0F, 0F);
                GL11.glTranslatef(0F, -2F, 0F);
            }
            if(meta == 3){
                GL11.glRotatef(-90, 1F, 0F, 0F);
                GL11.glTranslatef(0F, -1F, 1F);
            }
            if(meta == 2){
                GL11.glRotatef(90, 1F, 0F, 0F);
                GL11.glTranslatef(0F, -1F, -1F);
            }
            if(meta == 4){
                GL11.glRotatef(90, 0F, 0F, 1F);
                GL11.glTranslatef(1F, -1F, 0F);
            }
            if(meta == 5){
                GL11.glRotatef(90, 0F, 0F, -1F);
                GL11.glTranslatef(-1F, -1F, 0F);
            }
        }

        theModel.render(0.0625F);
        GL11.glPopMatrix();
    }

}
