/*
 * This file ("RenderSmileyCloud.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

//TODO Fix Smiley Cloud
public class RenderSmileyCloud extends TileEntitySpecialRenderer{

    private static final ResourceLocation resLocValentine = new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/blocks/models/modelPinkFluffyUnicloud.png");

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5, int partial){
        if(!(tile instanceof TileEntitySmileyCloud)){
            return;
        }
        TileEntitySmileyCloud theCloud = (TileEntitySmileyCloud)tile;

        GlStateManager.pushMatrix();
        {
            if(theCloud.flyHeight == 0){
                theCloud.flyHeight = tile.getWorld().rand.nextInt(30)+30;
            }
            int bobHeight = theCloud.flyHeight;
            double theTime = Minecraft.getSystemTime();
            double time = theTime/50;

            if(time-bobHeight >= theCloud.lastFlyHeight){
                theCloud.lastFlyHeight = time;
            }

            if(time-(bobHeight/2) >= theCloud.lastFlyHeight){
                GlStateManager.translate(0, (time-theCloud.lastFlyHeight)/300, 0);
            }
            else{
                GlStateManager.translate(0, -(time-theCloud.lastFlyHeight)/300+(double)bobHeight/300, 0);
            }

            GlStateManager.translate((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, -2F, 0.0F);

            /*
            GlStateManager.pushMatrix();
            {
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

                if(ClientProxy.bulletForMyValentine || (theCloud.name != null && !theCloud.name.isEmpty() && theCloud.name.equals("Pink Fluffy Unicloud"))){
                    this.bindTexture(resLocValentine);
                }
                else{
                    this.bindTexture(resLoc);
                }

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
            GlStateManager.popMatrix();
            */

            if(theCloud.name != null && !theCloud.name.isEmpty() && !Minecraft.getMinecraft().gameSettings.hideGUI){
                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(0F, 0.1F, 0F);
                    GlStateManager.rotate(180F, 1F, 0F, 0F);
                    GlStateManager.rotate(180F, 0F, 1F, 0F);

                    GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
                    float f = 1.6F;
                    float f1 = 0.016666668F*f;
                    GlStateManager.scale(-f1, -f1, f1);
                    GlStateManager.disableLighting();
                    GlStateManager.translate(0.0F, 0F/f1, 0.0F);
                    GlStateManager.depthMask(false);
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    /*
                    //TODO Fix nameplate with Smiley Cloud
                    WorldRenderer tessy = Tessellator.getInstance().getWorldRenderer();
                    //GlStateManager.disable(GlStateManager.GL_TEXTURE_2D);
                    tessy.st
                    int i = Minecraft.getMinecraft().fontRendererObj.getStringWidth(theCloud.name)/2;
                    tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator.addVertex(-i-1, -1.0D, 0.0D);
                    tessellator.addVertex(-i-1, 8.0D, 0.0D);
                    tessellator.addVertex(i+1, 8.0D, 0.0D);
                    tessellator.addVertex(i+1, -1.0D, 0.0D);
                    tessellator.draw();
                    GlStateManager.glEnable(GlStateManager.GL_TEXTURE_2D);
                    GlStateManager.depthMask(true);

                    Minecraft.getMinecraft().fontRendererObj.drawString(theCloud.name, -Minecraft.getMinecraft().fontRendererObj.getStringWidth(theCloud.name)/2, 0, StringUtil.DECIMAL_COLOR_WHITE);

                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1F, 1F, 1F, 1F);
                    GlStateManager.scale(1F/-f1, 1F/-f1, 1F/f1);
                    */
                }
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
    }

}
