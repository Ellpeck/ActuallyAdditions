/*
 * This file ("UpdateCheckerClientNotificationEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.update;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;

public class UpdateCheckerClientNotificationEvent{

    private static boolean notified = false;
    private static int ticksElapsedBeforeInfo;

    @SubscribeEvent(receiveCanceled = true)
    public void onTick(TickEvent.ClientTickEvent event){
        //Don't notify directly to prevent the Message getting lost in Spam on World Joining
        if(!notified && Minecraft.getMinecraft().thePlayer != null){
            ticksElapsedBeforeInfo++;
            if(ticksElapsedBeforeInfo >= 800){
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                if(UpdateChecker.checkFailed){
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.failed")));
                    notified = true;
                }
                else if(UpdateChecker.needsUpdateNotify){
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.generic")));
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID_LOWER+".update.versionCompare", ModUtil.VERSION, UpdateChecker.updateVersionString)));
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID_LOWER+".update.buttons", UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK)));
                    notified = true;
                }
                ticksElapsedBeforeInfo = 0;
            }
        }
    }
}
