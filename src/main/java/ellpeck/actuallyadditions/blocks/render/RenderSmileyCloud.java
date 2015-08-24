package ellpeck.actuallyadditions.blocks.render;

import ellpeck.actuallyadditions.gadget.cloud.ISmileyCloudEasterEgg;
import ellpeck.actuallyadditions.gadget.cloud.SmileyCloudEasterEggs;
import ellpeck.actuallyadditions.tile.TileEntitySmileyCloud;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSmileyCloud extends RenderTileEntity{

    public RenderSmileyCloud(ModelBaseAA model){
        super(model);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5){
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.0F, -2.0F, 0.0F);

        if(theModel.doesRotate()){
            int meta = tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);
            if(meta == 0) GL11.glRotatef(180F, 0F, 1F, 0F);
            if(meta == 1) GL11.glRotatef(90F, 0F, 1F, 0F);
            if(meta == 3) GL11.glRotatef(270F, 0F, 1F, 0F);
        }

        this.bindTexture(resLoc);

        if(tile instanceof TileEntitySmileyCloud){
            boolean hasRendered = false;

            TileEntitySmileyCloud theCloud = (TileEntitySmileyCloud)tile;
            if(theCloud.name != null && !theCloud.name.isEmpty()){
                for(ISmileyCloudEasterEgg cloud : SmileyCloudEasterEggs.cloudStuff){
                    for(String triggerName : cloud.getTriggerNames()){
                        if(StringUtil.equalsToLowerCase(triggerName, theCloud.name)){

                            if(cloud.shouldRenderOriginal()){
                                theModel.render(0.0625F);
                            }

                            ResourceLocation resLoc = cloud.getResLoc();
                            if(resLoc != null){
                                this.bindTexture(resLoc);
                            }

                            cloud.renderExtra(0.0625F);

                            hasRendered = true;
                            break;
                        }
                    }
                    if(hasRendered) break;
                }
            }
            if(!hasRendered) theModel.render(0.0625F);
        }
        GL11.glPopMatrix();
    }

}
