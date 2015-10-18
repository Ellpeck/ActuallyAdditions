/*
 * This file ("RenderSmileyCloud.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.render;

import ellpeck.actuallyadditions.misc.cloud.ISmileyCloudEasterEgg;
import ellpeck.actuallyadditions.misc.cloud.SmileyCloudEasterEggs;
import ellpeck.actuallyadditions.tile.TileEntitySmileyCloud;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class RenderSmileyCloud extends RenderTileEntity{

    public RenderSmileyCloud(ModelBaseAA model){
        super(model);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5){
        if(!(tile instanceof TileEntitySmileyCloud)){
            return;
        }
        TileEntitySmileyCloud theCloud = (TileEntitySmileyCloud)tile;

        GL11.glPushMatrix();
        {
            if(theCloud.flyHeight == 0){
                theCloud.flyHeight = new Random().nextInt(30)+30;
            }
            int bobHeight = theCloud.flyHeight;
            long theTime = Minecraft.getSystemTime();
            long time = theTime/50;

            if(time-bobHeight >= theCloud.lastFlyHeight){
                theCloud.lastFlyHeight = time;
            }

            if(time-(bobHeight/2) >= theCloud.lastFlyHeight){
                GL11.glTranslated(0, ((double)time-theCloud.lastFlyHeight)/300, 0);
            }
            else{
                GL11.glTranslated(0, -((double)time-theCloud.lastFlyHeight)/300+(double)bobHeight/300, 0);
            }

            GL11.glTranslatef((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0.0F, -2F, 0.0F);

            GL11.glPushMatrix();
            {
                if(theModel.doesRotate()){
                    int meta = tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);
                    if(meta == 0){
                        GL11.glRotatef(180F, 0F, 1F, 0F);
                    }
                    if(meta == 1){
                        GL11.glRotatef(90F, 0F, 1F, 0F);
                    }
                    if(meta == 3){
                        GL11.glRotatef(270F, 0F, 1F, 0F);
                    }
                }

                this.bindTexture(resLoc);

                theModel.render(0.0625F);

                if(theCloud.name != null && !theCloud.name.isEmpty()){
                    for(ISmileyCloudEasterEgg cloud : SmileyCloudEasterEggs.cloudStuff){
                        boolean canBreak = false;
                        for(String triggerName : cloud.getTriggerNames()){
                            if(StringUtil.equalsToLowerCase(triggerName, theCloud.name)){
                                cloud.renderExtra(0.0625F);
                                canBreak = true;
                                break;
                            }
                        }
                        if(canBreak){
                            break;
                        }
                    }
                }
            }
            GL11.glPopMatrix();

            if(theCloud.name != null && !theCloud.name.isEmpty() && !Minecraft.getMinecraft().gameSettings.hideGUI){
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0F, 0.1F, 0F);
                    GL11.glRotatef(180F, 1F, 0F, 0F);
                    GL11.glRotatef(180F, 0F, 1F, 0F);

                    GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
                    float f = 1.6F;
                    float f1 = 0.016666668F*f;
                    GL11.glScalef(-f1, -f1, f1);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glTranslatef(0.0F, 0F/f1, 0.0F);
                    GL11.glDepthMask(false);
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    Tessellator tessellator = Tessellator.instance;
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tessellator.startDrawingQuads();
                    int i = Minecraft.getMinecraft().fontRenderer.getStringWidth(theCloud.name)/2;
                    tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator.addVertex(-i-1, -1.0D, 0.0D);
                    tessellator.addVertex(-i-1, 8.0D, 0.0D);
                    tessellator.addVertex(i+1, 8.0D, 0.0D);
                    tessellator.addVertex(i+1, -1.0D, 0.0D);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDepthMask(true);

                    Minecraft.getMinecraft().fontRenderer.drawString(theCloud.name, -Minecraft.getMinecraft().fontRenderer.getStringWidth(theCloud.name)/2, 0, StringUtil.DECIMAL_COLOR_WHITE);

                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glColor4f(1F, 1F, 1F, 1F);
                    GL11.glScalef(1F/-f1, 1F/-f1, 1F/f1);
                }
                GL11.glPopMatrix();
            }
        }
        GL11.glPopMatrix();
    }

}
