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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;

public class UpdateChecker {

    public static final String DOWNLOAD_LINK = "http://ellpeck.de/actadddownload";
    public static final String CHANGELOG_LINK = "http://ellpeck.de/actaddchangelog";
    public static boolean checkFailed;
    public static boolean needsUpdateNotify;
    public static int updateVersionInt;
    public static String updateVersionString;
    public static boolean threadFinished = false;

    public UpdateChecker() {
//        if (CommonConfig.OTHER.DO_UPDATE_CHECK.get() && !Util.isDevVersion()) {
//            ActuallyAdditions.LOGGER.info("Initializing Update Checker...");
//            new ThreadUpdateChecker();
//            MinecraftForge.EVENT_BUS.register(this);
//        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(receiveCanceled = true)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            if (UpdateChecker.checkFailed) {
                player.displayClientMessage(Component.translatable("info.actuallyadditions.update.failed"), false);
            } else if (UpdateChecker.needsUpdateNotify) {
                player.displayClientMessage(Component.translatable("info.actuallyadditions.update.generic"), false);
                player.displayClientMessage(Component.translatable("info.actuallyadditions.update.versionCompare", ActuallyAdditions.VERSION, UpdateChecker.updateVersionString), false);
                player.displayClientMessage(Component.translatable("info.actuallyadditions.update.buttons", UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK), false);
            }
            if (threadFinished) {
                NeoForge.EVENT_BUS.unregister(this);
            }
        }
    }
}
