package ellpeck.someprettyrandomstuff.blocks.render;

import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderTileEntity extends TileEntitySpecialRenderer{

    ModelBaseSPRS theModel;

    public RenderTileEntity(ModelBaseSPRS model){
        this.theModel = model;
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5){
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.0F, -2.0F, 0.0F);
        this.bindTexture(new ResourceLocation(Util.MOD_ID_LOWER, "textures/blocks/models/" + this.theModel.getName() + ".png"));
        theModel.render(0.0625F);
        GL11.glPopMatrix();
    }

}
