/*
 * This file ("InitEvents.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.Loader;
import de.ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.nei.NEIScreenEvents;
import de.ellpeck.actuallyadditions.update.UpdateCheckerClientNotificationEvent;
import de.ellpeck.actuallyadditions.util.ModUtil;
import de.ellpeck.actuallyadditions.util.Util;
import net.minecraftforge.common.MinecraftForge;

public class InitEvents{

    public static void init(){
        ModUtil.LOGGER.info("Initializing Events...");

        Util.registerEvent(new PlayerObtainEvents());
        Util.registerEvent(new LivingDropEvent());
        Util.registerEvent(new EntityLivingEvent());
        Util.registerEvent(new BucketFillEvent());
        Util.registerEvent(new LogoutEvent());
        Util.registerEvent(new WorldLoadingEvents());
        MinecraftForge.TERRAIN_GEN_BUS.register(new WorldDecorationEvent());

    }

    public static void initClient(){
        Util.registerEvent(new TooltipEvent());
        Util.registerEvent(new HudEvent());

        if(Loader.isModLoaded("NotEnoughItems")){
            Util.registerEvent(new NEIScreenEvents());
        }

        if(ConfigBoolValues.DO_UPDATE_CHECK.isEnabled() && !Util.isDevVersion()){
            Util.registerEvent(new UpdateCheckerClientNotificationEvent());
        }
    }

}
