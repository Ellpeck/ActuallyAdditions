package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class LivingKillEvent{

    private static final int SQUID_MESSAGES = 3;

    @SubscribeEvent
    public void onDeathEvent(LivingDeathEvent event){
        /*if(event.source.getEntity() instanceof EntityPlayer){
            if(event.entity instanceof EntitySquid){
                String message = StatCollector.translateToLocal("info."+ModUtil.MOD_ID_LOWER+".squidKilled.desc."+(new Random().nextInt(SQUID_MESSAGES)+1));
                ((EntityPlayer)event.source.getEntity()).addChatComponentMessage(new ChatComponentText(message));
                event.source.getEntity().worldObj.playSoundAtEntity(event.source.getEntity(), "mob.wither.death", 0.3F, 0.001F);
            }
        }*/
    }
}
