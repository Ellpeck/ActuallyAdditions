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
        Util.registerEvent(new TooltipEvent());
        Util.registerEvent(new EntityLivingEvent());
        Util.registerEvent(new BucketFillEvent());
        MinecraftForge.TERRAIN_GEN_BUS.register(new WorldDecorationEvent());
    }

}
