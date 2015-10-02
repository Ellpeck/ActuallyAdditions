/*
 * This file ("UpdateCheckerClientNotifier.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.update;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class UpdateCheckerClientNotifier{

    private static boolean notified = false;

    @SubscribeEvent(receiveCanceled = true)
    public void onTick(TickEvent.ClientTickEvent event){
        //Don't notify directly to prevent the Message getting lost in Spam on World Joining
        if(Minecraft.getSystemTime()%200 == 0 && !notified && UpdateChecker.doneChecking && Minecraft.getMinecraft().thePlayer != null){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if(UpdateChecker.checkFailed){
                player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.failed.desc")));
            }
            else{
                if(UpdateChecker.updateVersion > UpdateChecker.clientVersion){
                    String notice1 = "info."+ModUtil.MOD_ID_LOWER+".update.generic.desc";
                    String notice2 = "info."+ModUtil.MOD_ID_LOWER+".update.versionComp.desc";
                    String notice3 = "info."+ModUtil.MOD_ID_LOWER+".update.changelog.desc";
                    String notice4 = "info."+ModUtil.MOD_ID_LOWER+".update.download.desc";
                    player.addChatComponentMessage(new ChatComponentText(""));
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localize(notice1)));
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localizeFormatted(notice2, ModUtil.VERSION, UpdateChecker.updateVersionS)));
                    player.addChatComponentMessage(new ChatComponentText(StringUtil.localizeFormatted(notice3, UpdateChecker.changelog)));
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localizeFormatted(notice4, UpdateChecker.DOWNLOAD_LINK)));
                    player.addChatComponentMessage(new ChatComponentText(""));
                }
            }
            notified = true;
        }
    }
}
