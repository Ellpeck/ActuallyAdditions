/*
 * This file ("InitEvents.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.nei.NEIScreenEvents;
import de.ellpeck.actuallyadditions.mod.update.UpdateCheckerClientNotificationEvent;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

public class InitEvents{

    public static void init(){
        ModUtil.LOGGER.info("Initializing Events...");

        Util.registerEvent(new PlayerObtainEvents());
        Util.registerEvent(new LivingDropEvent());
        Util.registerEvent(new EntityLivingEvent());
        Util.registerEvent(new LogoutEvent());
        Util.registerEvent(new WorldLoadingEvents());
        Util.registerEvent(new BreakEvent());
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
