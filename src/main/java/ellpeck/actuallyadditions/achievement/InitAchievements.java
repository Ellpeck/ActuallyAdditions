package ellpeck.actuallyadditions.achievement;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.AchievementPage;

import java.util.ArrayList;

public class InitAchievements{

    public static AchievementPage theAchievementPage;
    public static ArrayList<Achievement> achievementList = new ArrayList<Achievement>();

    public static Achievement achievementCraftKnifeBlade;
    public static Achievement achievementCraftKnife;

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

        achievementCraftKnifeBlade = new AchievementAA("craftKnifeBlade", -2, 0, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()), null);
        achievementCraftKnife = new AchievementAA("craftKnife", 0, 0, new ItemStack(InitItems.itemKnife), achievementCraftKnifeBlade);

        achievementCraftDough = new AchievementAA("craftDough", 0, -3, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()), achievementCraftKnife);
        achievementSmeltBaguette = new AchievementAA("smeltBaguette", 2, -3, new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()), achievementCraftDough);
        achievementCraftSubSandwich = new AchievementAA("craftSubSandwich", 4, -3, new ItemStack(InitItems.itemFoods, 1, TheFoods.SUBMARINE_SANDWICH.ordinal()), achievementSmeltBaguette);

        achievementCraftMashedFood = new AchievementAA("craftMashedFood", 3, 0, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal()), achievementCraftKnife);
        achievementCraftCompost = new AchievementAA("craftCompost", 5, 0, new ItemStack(InitBlocks.blockCompost), achievementCraftMashedFood);
        achievementCraftFertilizer = new AchievementAA("craftFertilizer", 7, 0, new ItemStack(InitItems.itemFertilizer), achievementCraftCompost);

        achievementCraftPaperCone = new AchievementAA("craftPaperCone", 0, 3, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()), achievementCraftKnife);
        achievementCraftFrenchFry = new AchievementAA("craftFrenchFry", 2, 3, new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRY.ordinal()), achievementCraftPaperCone);
        achievementCraftFrenchFries = new AchievementAA("craftFrenchFries", 4, 4, new ItemStack(InitItems.itemFoods, 1, TheFoods.FRENCH_FRIES.ordinal()), achievementCraftFrenchFry);
        achievementCraftFishNChips = new AchievementAA("craftFishNChips", 4, 2, new ItemStack(InitItems.itemFoods, 1, TheFoods.FISH_N_CHIPS.ordinal()), achievementCraftFrenchFry);


        theAchievementPage = new AchievementPage(StatCollector.translateToLocal("achievement.page." + ModUtil.MOD_ID_LOWER), achievementList.toArray(new Achievement[achievementList.size()]));
        AchievementPage.registerAchievementPage(theAchievementPage);
    }

}
