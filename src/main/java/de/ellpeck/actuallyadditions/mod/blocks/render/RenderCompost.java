/*
 * This file ("RenderCompost.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCompost;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

public class RenderCompost extends TileEntitySpecialRenderer{

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage){
        if(te instanceof TileEntityCompost){
            TileEntityCompost compost = (TileEntityCompost)te;
            if(compost.getStackInSlot(0) != null){
                float i = compost.getAmount()/TileEntityCompost.AMOUNT;
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)x+0.5F, (float)y+(i/3F)+0.01F, (float)z+0.5F);
                //Hehe
                if("ShadowfactsDev".equals(Minecraft.getMinecraft().thePlayer.getName())){
                    GlStateManager.translate(0F, 1F, 0F);
                }
                GlStateManager.scale(1.5F, i, 1.5F);
                AssetUtil.renderBlockInWorld(Blocks.DIRT, compost.getStackInSlot(0).getItem() == InitItems.itemFertilizer ? 1 : 0);
                GlStateManager.popMatrix();
            }
        }
    }

}
