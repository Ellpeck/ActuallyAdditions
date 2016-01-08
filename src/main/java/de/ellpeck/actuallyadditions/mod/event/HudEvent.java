/*
 * This file ("HudEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.api.block.IHudDisplay;
import de.ellpeck.actuallyadditions.api.tile.IEnergyDisplay;
import de.ellpeck.actuallyadditions.mod.tile.IRedstoneToggle;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudEvent{

    @SubscribeEvent
    public void onGameOverlay(RenderGameOverlayEvent.Post event){
        if(event.type == RenderGameOverlayEvent.ElementType.ALL && Minecraft.getMinecraft().currentScreen == null){
            Minecraft minecraft = Minecraft.getMinecraft();
            Profiler profiler = minecraft.mcProfiler;
            EntityPlayer player = minecraft.thePlayer;
            MovingObjectPosition posHit = minecraft.objectMouseOver;
            FontRenderer font = minecraft.fontRendererObj;
            ItemStack stack = player.getCurrentEquippedItem();

            profiler.startSection(ModUtil.MOD_ID+"Hud");

            if(stack != null){
                if(stack.getItem() instanceof IHudDisplay){
                    profiler.startSection("ItemHudDisplay");
                    ((IHudDisplay)stack.getItem()).displayHud(minecraft, player, stack, posHit, profiler, event.resolution);
                    profiler.endSection();
                }
            }

            if(posHit != null && posHit.getBlockPos() != null){
                Block blockHit = PosUtil.getBlock(posHit.getBlockPos(), minecraft.theWorld);
                TileEntity tileHit = minecraft.theWorld.getTileEntity(posHit.getBlockPos());

                if(blockHit instanceof IHudDisplay){
                    profiler.startSection("BlockHudDisplay");
                    ((IHudDisplay)blockHit).displayHud(minecraft, player, stack, posHit, profiler, event.resolution);
                    profiler.endSection();
                }

                if(tileHit instanceof IRedstoneToggle){
                    profiler.startSection("RedstoneToggleHudDisplay");

                    String strg = "Redstone Mode: "+EnumChatFormatting.DARK_RED+(((IRedstoneToggle)tileHit).isPulseMode() ? "Pulse" : "Deactivation")+EnumChatFormatting.RESET;
                    font.drawStringWithShadow(strg, event.resolution.getScaledWidth()/2+5, event.resolution.getScaledHeight()/2+5, StringUtil.DECIMAL_COLOR_WHITE);

                    if(stack != null && Block.getBlockFromItem(stack.getItem()) instanceof BlockRedstoneTorch){
                        String expl = EnumChatFormatting.GREEN+"Right-Click to toggle!";
                        font.drawStringWithShadow(expl, event.resolution.getScaledWidth()/2+5, event.resolution.getScaledHeight()/2+15, StringUtil.DECIMAL_COLOR_WHITE);
                    }

                    profiler.endSection();
                }

                if(tileHit instanceof IEnergyDisplay){
                    profiler.startSection("EnergyDisplay");
                    String strg = ((IEnergyDisplay)tileHit).getEnergy()+"/"+((IEnergyDisplay)tileHit).getMaxEnergy()+" RF";
                    font.drawStringWithShadow(EnumChatFormatting.GOLD+strg, event.resolution.getScaledWidth()/2+5, event.resolution.getScaledHeight()/2-10, StringUtil.DECIMAL_COLOR_WHITE);
                    profiler.endSection();
                }
            }

            profiler.endSection();
        }
    }

}
