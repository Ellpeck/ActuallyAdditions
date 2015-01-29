package ellpeck.someprettytechystuff.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class UpdateEvent{

    @SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent event){

    }

    public static void init(){
        MinecraftForge.EVENT_BUS.register(new UpdateEvent());
    }
}
