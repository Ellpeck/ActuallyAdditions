/*
 * This file ("RenderInventory.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at 
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

//TODO Fix the rendering handler
public class RenderInventory{

    private RenderTileEntity tileRender;
    private int renderID;

    public RenderInventory(RenderTileEntity tileRender, int renderID){
        this.tileRender = tileRender;
        this.renderID = renderID;
    }

    /*@Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer){
        GlStateManager.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(this.tileRender.resLoc);
        GlStateManager.glTranslatef(0F, 1F, 0F);
        GlStateManager.glRotatef(180F, 1F, 0F, 0F);
        this.tileRender.theModel.render(0.0625F);
        GlStateManager.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId){
        return true;
    }

    @Override
    public int getRenderId(){
        return this.renderID;
    }*/
}
