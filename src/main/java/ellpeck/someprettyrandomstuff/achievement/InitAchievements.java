package ellpeck.someprettyrandomstuff.achievement;

import ellpeck.someprettyrandomstuff.blocks.InitBlocks;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheFoods;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.AchievementPage;

import java.util.ArrayList;

public class InitAchievements{

    public static AchievementPage theAchievementPage;
    public static ArrayList<Achievement> achievementList = new ArrayList<Achievement>();

    public static Achievement achievementCraftDough;
    public static Achievement achievementSmeltBaguette;
    public static Achievement achievementCraftSubSandwich;

    public static Achievement achievementCraftMashedFood;
    public static Achievement achievementCraftCompost;
    public static Achievement achievementCraftFertilizer;

    public static Achievement achievementCraftPaperCone;
    public static Achievement achievementCraftFrenchFry;
    public static Achievement achievementCraftFrenchFries;
    public static Achievement achievementCraftFishNChips;

    public static void init(){
        Util.logInfo("Initializing Achievements...");

        achievementCraftDough = new AchievementSPRS("craftDough", 0, -3, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()), null);
        achievementSmeltBaguette = new AchievementSPRS("smeltBaguette", 2, -3, new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()), achievementCraftDough);
        achievementCraftSubSandwich = new AchievementSPRS("craftSubSandwich", 4, -3, new ItemStack(InitItems.itemFoods, 1, TheFoods.SUBMARINE_SANDWICH.ordinal()), achievementSmeltBaguette);

        achievementCraftMashedFood = new AchievementSPRS("craftMashedFood", 0, 0 , new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal()), null);
        achievementCraftCompost = new AchievementSPRS("craftCompost", 2, 0, new ItemStack(InitBlocks.blockCompost), achievementCraftMashedFood);
        achievementCraftFertilizer = new AchievementSPRS("craftFertilizer", 4, 0, new ItemStack(InitItems.itemFertilizer), achievementCraftCompost);

        achievementCraftPaperCone = new AchievementSPRS("craftPaperCone", 0, 3, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()), null);
        achievementCraftFrenchFry = new AchievementSPRS("craftFrenchFry", 2, 3, new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal()), achievementCraftPaperCone);
        achievementCraftFrenchFries = new AchievementSPRS("craftFrenchFries", 4, 4, new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRIES.ordinal()), achievementCraftFrenchFry);
        achievementCraftFishNChips = new AchievementSPRS("craftFishNChips", 4, 2, new ItemStack(InitItems.itemFoods, 1, TheFoods.FISH_N_CHIPS.ordinal()), achievementCraftFrenchFry);


        theAchievementPage = new AchievementPage(StatCollector.translateToLocal("achievement.page." + Util.MOD_ID), achievementList.toArray(new Achievement[achievementList.size()]));
        AchievementPage.registerAchievementPage(theAchievementPage);
    }

}
