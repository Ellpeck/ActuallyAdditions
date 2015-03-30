package ellpeck.actuallyadditions.gadget;

import ellpeck.actuallyadditions.blocks.render.ModelBaseAA;
import ellpeck.actuallyadditions.blocks.render.ModelFurnaceSolar;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSpecial{

    private double bobbing;
    private double rotation;

    ModelBaseAA theModel;
    ResourceLocation theTexture;

    public RenderSpecial(ModelBaseAA model){
        this.theModel = model;
        this.theTexture = new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/blocks/models/" + (model instanceof ModelFurnaceSolar ? "" : "special/") + this.theModel.getName() + ".png");
    }

    public void render(float size, float offsetUp){

        if(bobbing >= 0.5) bobbing = 0;
        else bobbing+=0.01;

        if(rotation >= 360) rotation = 0;
        else rotation+=1;

        GL11.glPushMatrix();
        GL11.glTranslatef(0F, offsetUp, 0F);
        GL11.glRotatef(180F, 1.0F, 0.0F, 1.0F);
        GL11.glScalef(size, size, size);

        if(bobbing <= 0.25)GL11.glTranslated(0, bobbing, 0);
        else GL11.glTranslated(0, 0.5 - bobbing, 0);

        GL11.glRotated(rotation, 0, 1, 0);

        Minecraft.getMinecraft().renderEngine.bindTexture(theTexture);
        theModel.render(0.0625F);
        GL11.glPopMatrix();
    }

}
