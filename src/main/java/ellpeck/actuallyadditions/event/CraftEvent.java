package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import ellpeck.actuallyadditions.achievement.InitAchievements;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import net.minecraft.item.Item;

public class CraftEvent{
    @SubscribeEvent
    public void onCraftedEvent(PlayerEvent.ItemCraftedEvent event){
        if(event.crafting.getItem() == InitItems.itemMisc && event.crafting.getItemDamage() == TheMiscItems.DOUGH.ordinal()){
            event.player.addStat(InitAchievements.achievementCraftDough, 1);
        }
        if(event.crafting.getItem() == InitItems.itemMisc && event.crafting.getItemDamage() == TheMiscItems.MASHED_FOOD.ordinal()){
            event.player.addStat(InitAchievements.achievementCraftMashedFood, 1);
        }
        if(event.crafting.getItem() == InitItems.itemMisc && event.crafting.getItemDamage() == TheMiscItems.KNIFE_BLADE.ordinal()){
            event.player.addStat(InitAchievements.achievementCraftKnifeBlade, 1);
        }
        if(event.crafting.getItem() == InitItems.itemKnife){
            event.player.addStat(InitAchievements.achievementCraftKnife, 1);
        }
        if(event.crafting.getItem() == InitItems.itemFoods && event.crafting.getItemDamage() == TheFoods.SUBMARINE_SANDWICH.ordinal()){
            event.player.addStat(InitAchievements.achievementCraftSubSandwich, 1);
        }
        if(event.crafting.getItem() == InitItems.itemMisc && event.crafting.getItemDamage() == TheMiscItems.PAPER_CONE.ordinal()){
            event.player.addStat(InitAchievements.achievementCraftPaperCone, 1);
        }
        if(event.crafting.getItem() == InitItems.itemFoods && event.crafting.getItemDamage() == TheFoods.FRENCH_FRY.ordinal()){
            event.player.addStat(InitAchievements.achievementCraftFrenchFry, 1);
        }
        if(event.crafting.getItem() == InitItems.itemFoods && event.crafting.getItemDamage() == TheFoods.FRENCH_FRIES.ordinal()){
            event.player.addStat(InitAchievements.achievementCraftFrenchFries, 1);
        }
        if(event.crafting.getItem() == InitItems.itemFoods && event.crafting.getItemDamage() == TheFoods.FISH_N_CHIPS.ordinal()){
            event.player.addStat(InitAchievements.achievementCraftFishNChips, 1);
        }
        if(event.crafting.getItem()== Item.getItemFromBlock(InitBlocks.blockCompost)){
            event.player.addStat(InitAchievements.achievementCraftCompost, 1);
        }
    }
}
