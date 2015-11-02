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
import net.minecraft.util.IChatComponent;

public class UpdateCheckerClientNotifier{

    private static boolean notified = false;

    @SubscribeEvent(receiveCanceled = true)
    public void onTick(TickEvent.ClientTickEvent event){
        //Don't notify directly to prevent the Message getting lost in Spam on World Joining
        if(Minecraft.getSystemTime()%300 == 0 && !notified && Minecraft.getMinecraft().thePlayer != null){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if(UpdateChecker.checkFailed){
                player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.failed")));
            }
            else if(UpdateChecker.needsUpdateNotify){
                player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".update.generic")));
                player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID_LOWER+".update.versionCompare", ModUtil.VERSION, UpdateChecker.updateVersion)));
                player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID_LOWER+".update.buttons", UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK)));
            }
            notified = true;
        }
    }
}
