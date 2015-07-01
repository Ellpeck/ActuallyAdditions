package ellpeck.actuallyadditions.event;

import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;

public class InitEvents{

    public static void init(){
        ModUtil.LOGGER.info("Initializing Events...");

        Util.registerEvent(new SmeltEvent());
        Util.registerEvent(new CraftEvent());
        Util.registerEvent(new KilledEvent());
        Util.registerEvent(new PickupEvent());
        Util.registerEvent(new TooltipEvent());
        Util.registerEvent(new EntityLivingEvent());
        Util.registerEvent(new WorldDecorationEvent());
        Util.registerEvent(new BucketFillEvent());
    }

}
