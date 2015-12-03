/*
 * This file ("TheAchievements.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.achievement;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public enum TheAchievements{

    OPEN_BOOKLET("openBooklet", 0, 0, new ItemStack(InitItems.itemBooklet), null, InitAchievements.MISC_ACH),
    NAME_SMILEY_CLOUD("nameSmileyCloud", 2, 0, new ItemStack(InitBlocks.blockSmileyCloud), OPEN_BOOKLET.ach, InitAchievements.MISC_ACH),
    CRAFT_PHANTOMFACE("craftPhantomface", -2, 0, new ItemStack(InitBlocks.blockPhantomface), OPEN_BOOKLET.ach),
    OPEN_TREASURE_CHEST("openTreasureChest", 0, -2, new ItemStack(InitBlocks.blockTreasureChest), OPEN_BOOKLET.ach, InitAchievements.MISC_ACH);

    public final Achievement ach;
    public final int type;

    TheAchievements(String name, int x, int y, ItemStack displayStack, Achievement hasToHaveBefore){
        this(name, x, y, displayStack, hasToHaveBefore, InitAchievements.CRAFTING_ACH);
    }

    TheAchievements(String name, int x, int y, ItemStack displayStack, Achievement hasToHaveBefore, int type){
        this.ach = new Achievement("achievement."+ModUtil.MOD_ID_LOWER+"."+name, ModUtil.MOD_ID_LOWER+"."+name, x, y, displayStack, hasToHaveBefore);
        if(hasToHaveBefore == null){
            this.ach.initIndependentStat();
        }
        this.ach.registerStat();
        this.type = type;
    }
}