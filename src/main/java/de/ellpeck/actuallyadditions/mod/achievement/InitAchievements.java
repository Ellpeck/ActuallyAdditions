/*
 * This file ("InitAchievements.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.achievement;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import java.util.ArrayList;

public final class InitAchievements{

    public static final ArrayList<Achievement> ACHIEVEMENT_LIST = new ArrayList<Achievement>();
    public static int pageNumber;
    public static AchievementPage theAchievementPage;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Achievements...");

        for(int i = 0; i < TheAchievements.values().length; i++){
            ACHIEVEMENT_LIST.add(TheAchievements.values()[i].chieve);
        }

        theAchievementPage = new AchievementPage(StringUtil.localize("achievement.page."+ModUtil.MOD_ID), ACHIEVEMENT_LIST.toArray(new Achievement[ACHIEVEMENT_LIST.size()]));
        pageNumber = AchievementPage.getAchievementPages().size();
        AchievementPage.registerAchievementPage(theAchievementPage);
    }

    public enum Type{
        CRAFTING,
        SMELTING,
        PICK_UP,
        MISC
    }

}
