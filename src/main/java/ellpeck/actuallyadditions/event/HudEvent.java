/*
 * This file ("HudEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.blocks.IHudDisplay;
import ellpeck.actuallyadditions.tile.IEnergyDisplay;
import ellpeck.actuallyadditions.tile.IRedstoneToggle;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class HudEvent{

    @SubscribeEvent
    public void onGameOverlay(RenderGameOverlayEvent.Post event){
        if(event.type == RenderGameOverlayEvent.ElementType.ALL){
            Minecraft minecraft = Minecraft.getMinecraft();
            Profiler profiler = minecraft.mcProfiler;
            EntityPlayer player = minecraft.thePlayer;
            MovingObjectPosition posHit = minecraft.objectMouseOver;
            FontRenderer font = minecraft.fontRenderer;

            profiler.startSection(ModUtil.MOD_ID+"Hud");

            if(posHit != null){
                Block blockHit = minecraft.theWorld.getBlock(posHit.blockX, posHit.blockY, posHit.blockZ);
                TileEntity tileHit = minecraft.theWorld.getTileEntity(posHit.blockX, posHit.blockY, posHit.blockZ);

                if(blockHit instanceof IHudDisplay){
                    profiler.startSection("BlockHudDisplay");
                    ((IHudDisplay)blockHit).displayHud(minecraft, player, player.getCurrentEquippedItem(), posHit, profiler, event.resolution);
                    profiler.endSection();
                }

                if(tileHit instanceof IRedstoneToggle){
                    if(player.getCurrentEquippedItem() != null && Block.getBlockFromItem(player.getCurrentEquippedItem().getItem()) instanceof BlockRedstoneTorch){
                        profiler.startSection("RedstoneToggleHudDisplay");

                        String strg = "Redstone Mode: "+EnumChatFormatting.DARK_RED+(((IRedstoneToggle)tileHit).isPulseMode() ? "Pulse" : "Deactivation")+EnumChatFormatting.RESET;
                        String expl = "Right-Click to toggle!";
                        font.drawStringWithShadow(strg, event.resolution.getScaledWidth()/2+5, event.resolution.getScaledHeight()/2+5, StringUtil.DECIMAL_COLOR_WHITE);
                        font.drawStringWithShadow(expl, event.resolution.getScaledWidth()/2+5, event.resolution.getScaledHeight()/2+15, StringUtil.DECIMAL_COLOR_WHITE);

                        profiler.endSection();
                    }
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
