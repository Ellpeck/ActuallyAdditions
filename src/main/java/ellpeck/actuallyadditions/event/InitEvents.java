/*
 * This file ("InitEvents.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.Loader;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.nei.NeiScreenEvents;
import ellpeck.actuallyadditions.update.UpdateCheckerClientNotifier;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraftforge.common.MinecraftForge;

public class InitEvents{

    public static void init(){
        ModUtil.LOGGER.info("Initializing Events...");

        Util.registerEvent(new SmeltEvent());

        Util.registerEvent(new CraftEvent());
        Util.registerEvent(new LivingDropEvent());
        Util.registerEvent(new PickupEvent());
        Util.registerEvent(new EntityLivingEvent());
        Util.registerEvent(new BucketFillEvent());
        Util.registerEvent(new LogoutEvent());
        MinecraftForge.TERRAIN_GEN_BUS.register(new WorldDecorationEvent());
    }

    public static void initClient(){
        Util.registerEvent(new TooltipEvent());
        Util.registerEvent(new RenderPlayerEventAA());

        if(Loader.isModLoaded("NotEnoughItems")){
            Util.registerEvent(new NeiScreenEvents());
        }

        if(ConfigBoolValues.DO_UPDATE_CHECK.isEnabled()){
            Util.registerEvent(new UpdateCheckerClientNotifier());
        }
    }

}
