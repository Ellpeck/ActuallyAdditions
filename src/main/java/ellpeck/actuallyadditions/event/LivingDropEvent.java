package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

public class LivingDropEvent{

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event){
        if(event.source.getEntity() instanceof EntityPlayer){
            //Drop Special Items (Solidified Experience, Pearl Shards etc.)
            for(int i = 0; i < TheSpecialDrops.values().length; i++){
                TheSpecialDrops theDrop = TheSpecialDrops.values()[i];
                if(theDrop.canDrop && theDrop.dropFrom.isAssignableFrom(event.entityLiving.getClass())){
                    if(new Random().nextInt(100) + 1 <= theDrop.chance){
                        event.entityLiving.entityDropItem(new ItemStack(InitItems.itemSpecialDrop, new Random().nextInt(theDrop.maxAmount) + 1, theDrop.ordinal()), 0);
                    }
                }
            }

            //Drop Cobwebs from Spiders
            if(ConfigBoolValues.DO_SPIDER_DROPS.isEnabled() && event.entityLiving instanceof EntitySpider){
                if(new Random().nextInt(ConfigIntValues.SPIDER_DROP_CHANCE.getValue()) <= 0){
                    event.entityLiving.entityDropItem(new ItemStack(Blocks.web, new Random().nextInt(2)+1), 0);
                }
            }

            //Drop Wings from Bats
            if(ConfigBoolValues.DO_BAT_DROPS.isEnabled() && event.entityLiving instanceof EntityBat){
                if(new Random().nextInt(ConfigIntValues.BAT_DROP_CHANCE.getValue()) <= 0){
                    event.entityLiving.entityDropItem(new ItemStack(InitItems.itemMisc, new Random().nextInt(2)+1, TheMiscItems.BAT_WING.ordinal()), 0);
                }
            }
        }
    }
}
