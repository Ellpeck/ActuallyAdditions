/*
 * This file ("RenderSpecial.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
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
import net.minecraft.util.Vec3;

import java.util.Calendar;

public class RenderSpecial{

    private double lastTimeForBobbing;
    private ItemStack theThingToRender;

    public RenderSpecial(ItemStack stack){
        this.theThingToRender = stack;
    }

    public void render(EntityPlayer player, float partialTicks){
        if(player.isInvisible() || !player.isWearing(EnumPlayerModelParts.CAPE)){
            return;
        }

        if(ClientProxy.pumpkinBlurPumpkinBlur){
            this.theThingToRender = new ItemStack(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)%2 == 0 ? Blocks.lit_pumpkin : Blocks.pumpkin);
        }

        boolean isBlock = this.theThingToRender.getItem() instanceof ItemBlock;
        float size = isBlock ? 0.5F : 0.4F;
        double offsetUp = isBlock ? 0D : 0.1875D;

        double bobHeight = 70;
        double theTime = Minecraft.getSystemTime();
        double time = theTime/50;

        if(time-bobHeight >= lastTimeForBobbing){
            this.lastTimeForBobbing = time;
        }

        GlStateManager.pushMatrix();

        Vec3 currentPos = Minecraft.getMinecraft().thePlayer.getPositionEyes(partialTicks);
        Vec3 playerPos = player.getPositionEyes(partialTicks);
        GlStateManager.translate(playerPos.xCoord-currentPos.xCoord, playerPos.yCoord-currentPos.yCoord-(player.isSneaking() || Minecraft.getMinecraft().thePlayer.isSneaking() ? 0.125D : 0D), playerPos.zCoord-currentPos.zCoord);

        GlStateManager.translate(0D, 2.535D+offsetUp, 0D);
        GlStateManager.rotate(180F, 1.0F, 0.0F, 1.0F);
        GlStateManager.scale(size, size, size);

        if(time-(bobHeight/2) >= lastTimeForBobbing){
            GlStateManager.translate(0D, (time-this.lastTimeForBobbing)/100D, 0D);
        }
        else{
            GlStateManager.translate(0D, -(time-lastTimeForBobbing)/100D+bobHeight/100D, 0D);
        }

        GlStateManager.rotate((float)(theTime/20), 0, 1, 0);

        GlStateManager.disableLighting();
        if(this.theThingToRender != null){
            if(isBlock){
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
