package ellpeck.actuallyadditions.gadget;

import ellpeck.actuallyadditions.blocks.render.ModelBaseAA;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderSpecial{

    private double lastTimeForBobbing;

    ModelBaseAA theModel;
    ResourceLocation theTexture;

    public RenderSpecial(ModelBaseAA model){
        this.theModel = model;
        this.theTexture = new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/blocks/models/special/" + this.theModel.getName() + ".png");
    }

    public void render(EntityPlayer player, float renderTick, float size, float offsetUp){
        int bobHeight = 70;
        long theTime = Minecraft.getSystemTime();
        long time = theTime/50;

        if(time-bobHeight >= lastTimeForBobbing){
            this.lastTimeForBobbing = time;
        }

        GL11.glPushMatrix();

        if(player != Minecraft.getMinecraft().thePlayer){
            Vec3 clientPos = Minecraft.getMinecraft().thePlayer.getPosition(renderTick);
            Vec3 playerPos = player.getPosition(renderTick);
            GL11.glTranslated(playerPos.xCoord-clientPos.xCoord, playerPos.yCoord-clientPos.yCoord+1.6225, playerPos.zCoord-clientPos.zCoord);
        }

        GL11.glTranslated(0F, offsetUp + 0.15D, 0F);

        GL11.glRotatef(180F, 1.0F, 0.0F, 1.0F);
        GL11.glScalef(size, size, size);

        if(!(time-(bobHeight/2) < lastTimeForBobbing)){
            GL11.glTranslated(0, ((double)time-this.lastTimeForBobbing)/100, 0);
        }
        else{
            GL11.glTranslated(0, -((double)time-lastTimeForBobbing)/100+(double)bobHeight/100, 0);
        }

        GL11.glRotated((double)theTime/20, 0, 1, 0);

        Minecraft.getMinecraft().renderEngine.bindTexture(theTexture);
        theModel.render(0.0625F);
        GL11.glPopMatrix();
    }

}
