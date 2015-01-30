package ellpeck.someprettyrandomstuff.blocks.renderer;

import ellpeck.someprettyrandomstuff.blocks.models.ModelBaseSPTS;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BlockBaseRenderer extends TileEntitySpecialRenderer{

    private final ResourceLocation resLoc;
    private ModelBaseSPTS model;

    public BlockBaseRenderer(ModelBaseSPTS model, ResourceLocation resLoc){
        this.resLoc = resLoc;
        this.model = model;
    }

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f){
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x+0.5F, (float)y+1.5F, (float)z+0.5F);

        if(tile.getBlockMetadata() == 2 || tile.getBlockMetadata() == 3) GL11.glRotatef(180, 1F, 0F, 1F);
        else if(tile.getBlockMetadata() == 4 || tile.getBlockMetadata() == 5) GL11.glRotatef(180, 1F, 0F, 0F);
        else GL11.glRotatef(180, 0F, 0F, 0F);

        this.bindTexture(resLoc);
        GL11.glPushMatrix();
        this.model.render(0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
