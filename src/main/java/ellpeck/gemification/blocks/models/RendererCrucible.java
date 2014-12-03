package ellpeck.gemification.blocks.models;

import ellpeck.gemification.Gemification;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RendererCrucible extends TileEntitySpecialRenderer{

    public static final ResourceLocation resLoc = new ResourceLocation(Gemification.MOD_ID, "textures/blocks/models/modelCrucible.png");
    private ModelCrucible model;

    public RendererCrucible(){
        this.model = new ModelCrucible();
    }

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        GL11.glRotatef(180, 0F, 0F, 1F);
        this.bindTexture(resLoc);
        GL11.glPushMatrix();
        this.model.render(0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
