package ellpeck.actuallyadditions.achievement;

import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class AchievementAA extends Achievement{

    public AchievementAA(String name, int x, int y, ItemStack displayStack, Achievement hasToHaveBefore){
        super("achievement." + ModUtil.MOD_ID_LOWER + "." + name, ModUtil.MOD_ID_LOWER + "." + name, x, y, displayStack, hasToHaveBefore);
        InitAchievements.achievementList.add(this);
        if(hasToHaveBefore == null) this.initIndependentStat();
        this.registerStat();
    }
}