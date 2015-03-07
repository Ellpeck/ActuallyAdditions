package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import ellpeck.actuallyadditions.achievement.InitAchievements;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;

public class SmeltEvent{
    @SubscribeEvent
    public void onSmeltedEvent(PlayerEvent.ItemSmeltedEvent event){
        if(event.smelting.getItem() == InitItems.itemFoods && event.smelting.getItemDamage() == TheFoods.BAGUETTE.ordinal()){
            event.player.addStat(InitAchievements.achievementSmeltBaguette, 1);
        }
    }
}
