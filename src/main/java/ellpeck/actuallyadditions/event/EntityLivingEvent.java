package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.items.InitItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import java.util.Random;

public class EntityLivingEvent{

    @SubscribeEvent
    public void livingUpdateEvent(LivingUpdateEvent event){
        if(!event.entityLiving.worldObj.isRemote){
            if(event.entityLiving instanceof EntityOcelot){
                EntityOcelot theOcelot = (EntityOcelot)event.entityLiving;
                if(ConfigBoolValues.DO_CAT_DROPS.isEnabled() && theOcelot.isTamed()){
                    if(new Random().nextInt(ConfigIntValues.CAT_DROP_CHANCE.getValue())+1 == 1){
                        EntityItem item = new EntityItem(theOcelot.worldObj, theOcelot.posX + 0.5, theOcelot.posY + 0.5, theOcelot.posZ + 0.5, new ItemStack(InitItems.itemHairyBall));
                        theOcelot.worldObj.spawnEntityInWorld(item);
                    }
                }
            }
        }
    }

}
