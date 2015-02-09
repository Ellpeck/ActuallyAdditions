package ellpeck.someprettyrandomstuff.achievement;

import ellpeck.someprettyrandomstuff.blocks.InitBlocks;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheFoods;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class AchievementEvent{

    public static class SmeltEvent{
        @SubscribeEvent
        public void onSmeltedEvent(PlayerEvent.ItemSmeltedEvent event){
            if(event.smelting.getItem() == InitItems.itemFoods && event.smelting.getItemDamage() == TheFoods.BAGUETTE.ordinal()){
                event.player.addStat(InitAchievements.achievementSmeltBaguette, 1);
            }
        }
    }

    public static class CraftEvent{
        @SubscribeEvent
        public void onCraftedEvent(PlayerEvent.ItemCraftedEvent event){
            if(event.crafting.getItem() == InitItems.itemMisc && event.crafting.getItemDamage() == TheMiscItems.DOUGH.ordinal()){
                event.player.addStat(InitAchievements.achievementCraftDough, 1);
            }
            if(event.crafting.getItem() == InitItems.itemMisc && event.crafting.getItemDamage() == TheMiscItems.MASHED_FOOD.ordinal()){
                event.player.addStat(InitAchievements.achievementCraftMashedFood, 1);
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

    public static class PickupEvent{
        @SubscribeEvent
        public void onItemPickupEvent(PlayerEvent.ItemPickupEvent event){

        }
    }

    public static class LoginEvent{
        @SubscribeEvent
        public void onPlayerLoggingInEvent(PlayerEvent.PlayerLoggedInEvent event){

        }
    }

    public static void init(){
        Util.logInfo("Initializing Events...");

        FMLCommonHandler.instance().bus().register(new AchievementEvent.SmeltEvent());
        FMLCommonHandler.instance().bus().register(new AchievementEvent.CraftEvent());
        FMLCommonHandler.instance().bus().register(new AchievementEvent.PickupEvent());
        FMLCommonHandler.instance().bus().register(new AchievementEvent.LoginEvent());
    }

}
