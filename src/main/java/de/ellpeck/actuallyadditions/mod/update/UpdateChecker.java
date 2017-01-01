/*
 * This file ("UpdateChecker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.update;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UpdateChecker{

    public static final String DOWNLOAD_LINK = "http://ellpeck.de/actadddownload";
    public static final String CHANGELOG_LINK = "http://ellpeck.de/actaddchangelog";
    public static boolean checkFailed;
    public static boolean needsUpdateNotify;
    public static int updateVersionInt;
    public static String updateVersionString;

    private static boolean notified = false;
    private static int ticksElapsedBeforeInfo;

    public UpdateChecker(){
        if(ConfigBoolValues.DO_UPDATE_CHECK.isEnabled() && !Util.isDevVersion()){
            ModUtil.LOGGER.info("Initializing Update Checker...");

            new ThreadUpdateChecker();
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(receiveCanceled = true)
    public void onTick(TickEvent.ClientTickEvent event){
        //Don't notify directly to prevent the Message getting lost in Spam on World Joining
        if(!notified && Minecraft.getMinecraft().player != null){
            ticksElapsedBeforeInfo++;
            if(ticksElapsedBeforeInfo >= 800){
                EntityPlayer player = Minecraft.getMinecraft().player;
                if(UpdateChecker.checkFailed){
                    player.sendMessage(ITextComponent.Serializer.jsonToComponent(StringUtil.localize("info."+ModUtil.MOD_ID+".update.failed")));
                    notified = true;
                }
                else if(UpdateChecker.needsUpdateNotify){
                    player.sendMessage(ITextComponent.Serializer.jsonToComponent(StringUtil.localize("info."+ModUtil.MOD_ID+".update.generic")));
                    player.sendMessage(ITextComponent.Serializer.jsonToComponent(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".update.versionCompare", ModUtil.VERSION, UpdateChecker.updateVersionString)));
                    player.sendMessage(ITextComponent.Serializer.jsonToComponent(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".update.buttons", UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK)));
                    notified = true;
                }
                ticksElapsedBeforeInfo = 0;
            }
        }
    }
}