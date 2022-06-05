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
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
            PlayerEntity player = Minecraft.getInstance().player;
            if (UpdateChecker.checkFailed) {
                player.displayClientMessage(ITextComponent.Serializer.fromJson(I18n.get("info." + ActuallyAdditions.MODID + ".update.failed")), false);
            } else if (UpdateChecker.needsUpdateNotify) {
                player.displayClientMessage(ITextComponent.Serializer.fromJson(I18n.get("info." + ActuallyAdditions.MODID + ".update.generic")), false);
                player.displayClientMessage(ITextComponent.Serializer.fromJson(I18n.get("info." + ActuallyAdditions.MODID + ".update.versionCompare", ActuallyAdditions.VERSION, UpdateChecker.updateVersionString)), false);
                player.displayClientMessage(ITextComponent.Serializer.fromJson(I18n.get("info." + ActuallyAdditions.MODID + ".update.buttons", UpdateChecker.CHANGELOG_LINK, UpdateChecker.DOWNLOAD_LINK)), false);
            }
            if (threadFinished) {
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
    }
}
