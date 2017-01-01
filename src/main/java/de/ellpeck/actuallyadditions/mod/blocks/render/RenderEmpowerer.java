/*
 * This file ("RenderEmpowerer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEmpowerer;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEmpowerer extends TileEntitySpecialRenderer{

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5, int par6){
        if(!(tile instanceof TileEntityEmpowerer)){
            return;
        }

        ItemStack stack = ((TileEntityEmpowerer)tile).slots.getStackInSlot(0);
        if(StackUtil.isValid(stack)){
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x+0.5F, (float)y+1F, (float)z+0.5F);

            double boop = Minecraft.getSystemTime()/800D;
            GlStateManager.translate(0D, Math.sin(boop%(2*Math.PI))*0.065, 0D);
            GlStateManager.rotate((float)(((boop*40D)%360)), 0, 1, 0);

            float scale = stack.getItem() instanceof ItemBlock ? 0.85F : 0.65F;
            GlStateManager.scale(scale, scale, scale);
            try{
                AssetUtil.renderItemInWorld(stack);
            }
            catch(Exception e){
                ModUtil.LOGGER.error("Something went wrong trying to render an item in an empowerer! The item is "+stack.getItem().getRegistryName()+"!", e);
            }

            GlStateManager.popMatrix();
        }

        int index = ((TileEntityEmpowerer)tile).recipeForRenderIndex;
        if(index >= 0 && ActuallyAdditionsAPI.EMPOWERER_RECIPES.size() > index){
            EmpowererRecipe recipe = ActuallyAdditionsAPI.EMPOWERER_RECIPES.get(index);
            if(recipe != null){
                for(int i = 0; i < EnumFacing.HORIZONTALS.length; i++){
                    EnumFacing facing = EnumFacing.HORIZONTALS[i];
                    BlockPos offset = tile.getPos().offset(facing, 3);

                    AssetUtil.renderLaser(tile.getPos().getX()+0.5, tile.getPos().getY()+0.5, tile.getPos().getZ()+0.5, offset.getX()+0.5, offset.getY()+0.95, offset.getZ()+0.5, 80, 1F, 0.1F, recipe.particleColor);
                }
            }
        }
    }
}
