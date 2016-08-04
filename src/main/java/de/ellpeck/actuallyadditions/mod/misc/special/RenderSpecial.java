/*
 * This file ("RenderSpecial.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.special;

import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

import java.util.Calendar;

public class RenderSpecial{

    private final ItemStack theThingToRender;

    public RenderSpecial(ItemStack stack){
        this.theThingToRender = stack;
    }

    public void render(EntityPlayer player, float partialTicks){
        if(player.isInvisible() || !player.isWearing(EnumPlayerModelParts.CAPE)){
            return;
        }

        boolean isBlock = this.theThingToRender.getItem() instanceof ItemBlock;
        float size = isBlock ? 0.5F : 0.4F;
        double offsetUp = isBlock ? 0D : 0.1875D;

        double bobHeight = 0.3;
        double boop = Minecraft.getSystemTime()/1000D;

        GlStateManager.pushMatrix();

        Vec3d currentPos = Minecraft.getMinecraft().thePlayer.getPositionEyes(partialTicks);
        Vec3d playerPos = player.getPositionEyes(partialTicks);
        GlStateManager.translate(playerPos.xCoord-currentPos.xCoord, playerPos.yCoord-currentPos.yCoord-(player.isSneaking() || Minecraft.getMinecraft().thePlayer.isSneaking() ? 0.125D : 0D), playerPos.zCoord-currentPos.zCoord);

        GlStateManager.translate(0D, 2.435D+offsetUp, 0D);
        GlStateManager.rotate(180F, 1.0F, 0.0F, 1.0F);
        GlStateManager.scale(size, size, size);

        //Make the floaty stuff look nice using sine waves \o/ -xdjackiexd
        //Peck edit: What do you mean by "nice" you jackass? >_>
        GlStateManager.translate(0D, Math.sin(boop%(2*Math.PI))*bobHeight, 0D);
        GlStateManager.rotate((float)(((boop*40D)%360)), 0, 1, 0);

        GlStateManager.disableLighting();
        if(this.theThingToRender != null){
            if(isBlock){
                GlStateManager.rotate(180F, 1F, 0F, 0F);
                AssetUtil.renderBlockInWorld(Block.getBlockFromItem(this.theThingToRender.getItem()), this.theThingToRender.getItemDamage());
            }
            else{
                GlStateManager.pushMatrix();
                GlStateManager.translate(0D, 0.5D, 0D);
                GlStateManager.rotate(180F, 1F, 0F, 0F);
                AssetUtil.renderItemInWorld(this.theThingToRender);
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.enableLighting();

        GlStateManager.popMatrix();
    }

}
