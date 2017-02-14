/*
 * This file ("RenderBatteryBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import de.ellpeck.actuallyadditions.mod.items.ItemBattery;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBatteryBox;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.NumberFormat;

@SideOnly(Side.CLIENT)
public class RenderBatteryBox extends TileEntitySpecialRenderer{

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5, int par6){
        if(!(tile instanceof TileEntityBatteryBox)){
            return;
        }

        ItemStack stack = ((TileEntityBatteryBox)tile).slots.getStackInSlot(0);
        if(StackUtil.isValid(stack) && stack.getItem() instanceof ItemBattery){
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x+0.5F, (float)y+1F, (float)z+0.5F);

            GlStateManager.pushMatrix();

            GlStateManager.scale(0.0075F, 0.0075F, 0.0075F);
            GlStateManager.rotate(180F, 1F, 0F, 0F);
            GlStateManager.translate(0F, 0F, -50F);

            if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
                IEnergyStorage cap = stack.getCapability(CapabilityEnergy.ENERGY, null);
                NumberFormat format = NumberFormat.getInstance();
                FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

                String s = format.format(cap.getEnergyStored())+"/"+format.format(cap.getMaxEnergyStored());
                float lengthS = -font.getStringWidth(s)/2F;
                String s2 = "Crystal Flux";
                float lengthS2 = -font.getStringWidth(s2)/2F;

                for(int i = 0; i < 4; i++){
                    font.drawString(s, lengthS, 10F, 0xFFFFFF, false);
                    font.drawString(s2, lengthS2, 20F, 0xFFFFFF, false);

                    GlStateManager.translate(-50F, 0F, 50F);
                    GlStateManager.rotate(90F, 0F, 1F, 0F);
                }
            }

            GlStateManager.popMatrix();

            double boop = Minecraft.getSystemTime()/800D;
            GlStateManager.translate(0D, Math.sin(boop%(2*Math.PI))*0.065, 0D);
            GlStateManager.rotate((float)(((boop*40D)%360)), 0, 1, 0);

            float scale = stack.getItem() instanceof ItemBlock ? 0.85F : 0.65F;
            GlStateManager.scale(scale, scale, scale);
            try{
                AssetUtil.renderItemInWorld(stack);
            }
            catch(Exception e){
                ModUtil.LOGGER.error("Something went wrong trying to render an item in a battery box! The item is "+stack.getItem().getRegistryName()+"!", e);
            }

            GlStateManager.popMatrix();
        }
    }
}
