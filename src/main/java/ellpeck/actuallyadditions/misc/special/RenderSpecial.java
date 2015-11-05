/*
 * This file ("RenderSpecial.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc.special;

import ellpeck.actuallyadditions.proxy.ClientProxy;
import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.Calendar;

public class RenderSpecial{

    private double lastTimeForBobbing;
    private ItemStack theThingToRender;

    public RenderSpecial(ItemStack stack){
        this.theThingToRender = stack;
    }

    public void render(EntityPlayer player){
        if(player.isInvisible() || player.getHideCape()){
            return;
        }

        boolean isBlock = this.theThingToRender.getItem() instanceof ItemBlock;
        float size = isBlock ? 0.3F : 0.4F;
        float offsetUp = isBlock ? 0F : 0.2F;

        if(ClientProxy.pumpkinBlurPumpkinBlur){
            this.theThingToRender = new ItemStack(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)%2 == 0 ? Blocks.lit_pumpkin : Blocks.pumpkin);
            size = 0.3F;
            offsetUp = 0;
        }

        int bobHeight = 70;
        long theTime = Minecraft.getSystemTime();
        long time = theTime/50;

        if(time-bobHeight >= lastTimeForBobbing){
            this.lastTimeForBobbing = time;
        }

        GL11.glPushMatrix();
        GL11.glTranslated(0D, -0.775D+offsetUp, 0D);
        GL11.glRotatef(180F, 1.0F, 0.0F, 1.0F);
        GL11.glScalef(size, size, size);

        if(time-(bobHeight/2) >= lastTimeForBobbing){
            GL11.glTranslated(0, ((double)time-this.lastTimeForBobbing)/100, 0);
        }
        else{
            GL11.glTranslated(0, -((double)time-lastTimeForBobbing)/100+(double)bobHeight/100, 0);
        }

        GL11.glRotated((double)theTime/20, 0, 1, 0);

        GL11.glDisable(GL11.GL_LIGHTING);
        if(this.theThingToRender != null){
            if(isBlock){
                AssetUtil.renderBlock(Block.getBlockFromItem(this.theThingToRender.getItem()), this.theThingToRender.getItemDamage());
            }
            else{
                GL11.glTranslatef(-0.5F, 0F, 0F);
                AssetUtil.renderItem(this.theThingToRender, 0);
            }
        }
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
    }

}
