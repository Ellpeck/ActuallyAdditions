package ellpeck.someprettyrandomstuff.achievement;

import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class AchievementSPRS extends Achievement{

    public AchievementSPRS(String name, int x, int y, ItemStack displayStack, Achievement hasToHaveBefore){
        super("achievement." + Util.MOD_ID_LOWER + "." + name, name, x, y, displayStack, hasToHaveBefore);
        InitAchievements.achievementList.add(this);
        if(hasToHaveBefore == null) this.initIndependentStat();
        this.registerStat();
    }
}