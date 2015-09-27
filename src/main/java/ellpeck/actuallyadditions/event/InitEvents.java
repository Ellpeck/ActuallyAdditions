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
        Util.registerEvent(new EntityConstructingEvent());
        MinecraftForge.TERRAIN_GEN_BUS.register(new WorldDecorationEvent());
    }

}
