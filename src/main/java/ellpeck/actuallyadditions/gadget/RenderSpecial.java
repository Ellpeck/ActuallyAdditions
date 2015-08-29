/*
 * This file ("RenderSpecial.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.gadget;

import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class RenderSpecial{

    private double lastTimeForBobbing;

    private Block theBlock;
    private int meta;

    public RenderSpecial(Block block, int meta){
        this.theBlock = block;
        this.meta = meta;
    }

    public void render(EntityPlayer player, float size, float offsetUp){
        if(player.isInvisible() || player.getHideCape()) return;

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
        AssetUtil.renderBlock(this.theBlock, this.meta);
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
    }

}
