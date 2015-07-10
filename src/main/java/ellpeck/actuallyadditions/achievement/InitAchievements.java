package ellpeck.actuallyadditions.achievement;

import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.stats.Achievement;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.AchievementPage;

import java.util.ArrayList;

public class InitAchievements{

    public static final int MISC_ACH = -1;
    public static final int CRAFTING_ACH = 0;
    public static final int SMELTING_ACH = 1;
    public static final int PICKUP_ACH = 2;

    public static AchievementPage theAchievementPage;
    public static ArrayList<Achievement> achievementList = new ArrayList<Achievement>();

    public static void init(){
        ModUtil.LOGGER.info("Initializing Achievements...");

        for(int i = 0; i < TheAchievements.values().length; i++){
            achievementList.add(TheAchievements.values()[i].ach);
        }

        theAchievementPage = new AchievementPage(StatCollector.translateToLocal("achievement.page." + ModUtil.MOD_ID_LOWER), achievementList.toArray(new Achievement[achievementList.size()]));
        AchievementPage.registerAchievementPage(theAchievementPage);
    }

}
