/*
 * This file ("InitAchievements.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.achievement;

import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import java.util.ArrayList;

public class InitAchievements{

    public enum Type{
        CRAFTING,
        SMELTING,
        PICK_UP,
        MISC
    }

    public static int pageNumber;

    public static AchievementPage theAchievementPage;
    public static ArrayList<Achievement> achievementList = new ArrayList<Achievement>();

    public static void init(){
        ModUtil.LOGGER.info("Initializing Achievements...");

        for(int i = 0; i < TheAchievements.values().length; i++){
            achievementList.add(TheAchievements.values()[i].ach);
        }

        theAchievementPage = new AchievementPage(StringUtil.localize("achievement.page."+ModUtil.MOD_ID_LOWER), achievementList.toArray(new Achievement[achievementList.size()]));
        pageNumber = AchievementPage.getAchievementPages().size();
        AchievementPage.registerAchievementPage(theAchievementPage);
    }

}
