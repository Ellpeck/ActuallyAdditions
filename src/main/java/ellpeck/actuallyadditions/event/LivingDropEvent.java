package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

public class LivingDropEvent{

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event){
        if(event.source.getEntity() instanceof EntityPlayer){
            for(int i = 0; i < TheSpecialDrops.values().length; i++){
                TheSpecialDrops theDrop = TheSpecialDrops.values()[i];
                if(theDrop.canDrop && theDrop.dropFrom.isAssignableFrom(event.entityLiving.getClass())){
                    if(new Random().nextInt(100) + 1 <= theDrop.chance){
                        event.entityLiving.entityDropItem(new ItemStack(InitItems.itemSpecialDrop, new Random().nextInt(theDrop.maxAmount) + 1, theDrop.ordinal()), 0);
                    }
                }
            }
        }
    }
}
