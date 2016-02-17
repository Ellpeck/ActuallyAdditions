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

import de.ellpeck.actuallyadditions.mod.misc.cloud.ISmileyCloudEasterEgg;
import de.ellpeck.actuallyadditions.mod.misc.cloud.SmileyCloudEasterEggs;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSmileyCloud extends TileEntitySpecialRenderer{

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5, int partial){
        if(tile instanceof TileEntitySmileyCloud){
            TileEntitySmileyCloud theCloud = (TileEntitySmileyCloud)tile;
            if(theCloud.flyHeight == 0){
                theCloud.flyHeight = tile.getWorld().rand.nextInt(30)+30;
            }
            int bobHeight = theCloud.flyHeight;
            double theTime = Minecraft.getSystemTime();
            double time = theTime/50;

            if(time-bobHeight >= theCloud.lastFlyHeight){
                theCloud.lastFlyHeight = time;
            }
            GlStateManager.pushMatrix();

            if(time-(bobHeight/2) >= theCloud.lastFlyHeight){
                GlStateManager.translate(0, (time-theCloud.lastFlyHeight)/300, 0);
            }
            else{
                GlStateManager.translate(0, -(time-theCloud.lastFlyHeight)/300+(double)bobHeight/300, 0);
            }


            GlStateManager.translate((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, -2F, 0.0F);

            if(ClientProxy.bulletForMyValentine || (theCloud.name != null && !theCloud.name.isEmpty() && theCloud.name.equals("Pink Fluffy Unicloud"))){
                theCloud.setPinkAndFluffy();
            } else {
                theCloud.setNormalCloud();
            }

            if(theCloud.name != null && !theCloud.name.isEmpty()){
                for(ISmileyCloudEasterEgg cloud : SmileyCloudEasterEggs.cloudStuff){
                    boolean canBreak = false;
                    for(String triggerName : cloud.getTriggerNames()){
                        if(StringUtil.equalsToLowerCase(triggerName, theCloud.name)){
                            GlStateManager.pushMatrix();
                            switch (PosUtil.getMetadata(theCloud.getPos(), theCloud.getWorld())){
                                case 1: {
                                    GlStateManager.rotate(180, 0, 1, 0);
                                    break;
                                }
                                case 2: {
                                    GlStateManager.rotate(270, 0, 1, 0);
                                    break;
                                }
                                case 3: {
                                    GlStateManager.rotate(90, 0, 1, 0);
                                    break;
                                }
                            }
                            cloud.renderExtra(0.0625F);
                            GlStateManager.popMatrix();
                            canBreak = true;
                            break;
                        }
                    }
                    if(canBreak){
                        break;
                    }
                }
            }
            GlStateManager.popMatrix();

            if(theCloud.name != null && !theCloud.name.isEmpty() && !Minecraft.getMinecraft().gameSettings.hideGUI) {
                AssetUtil.renderNameTag(theCloud.name, x + 0.5F, y + 1.3F, z + 0.66);
            }

        }
    }

}
