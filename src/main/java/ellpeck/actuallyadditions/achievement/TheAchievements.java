package ellpeck.actuallyadditions.achievement;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public enum TheAchievements{

    //Special Stuff Path
    PICK_UP_XP("pickUpSolidXP", 0, 0, new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal()), null, InitAchievements.PICKUP_ACH),

    //Food & Fertilizer Path
    CRAFT_KNIFE_BLADE("craftKnifeBlade", 0, 2, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()), null),
    CRAFT_KNIFE("craftKnife", 2, 2, new ItemStack(InitItems.itemKnife), CRAFT_KNIFE_BLADE.ach),
    CRAFT_MASHED_FOOD("craftMashedFood", 4, 2, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal()), CRAFT_KNIFE.ach),

    //Machine Path
    CRAFT_WOODEN_CASE("craftWoodenCasing", 0, -3, new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()), null),
    //ESD Part
    CRAFT_ESD("craftESD", -2, -3, new ItemStack(InitBlocks.blockInputter), CRAFT_WOODEN_CASE.ach),
    CRAFT_ADVANCED_ESD("craftAdvancedESD", -4, -3, new ItemStack(InitBlocks.blockInputterAdvanced), CRAFT_ESD.ach),

    CRAFT_STONE_CASE("craftStoneCasing", 2, -4, new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.STONE_CASING.ordinal()), CRAFT_WOODEN_CASE.ach),
    CRAFT_COIL("craftBasicCoil", 2, -2, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), CRAFT_WOODEN_CASE.ach),
    CRAFT_ADVANCED_COIL("craftAdvancedCoil", 4, -2, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), CRAFT_COIL.ach),
    //Phantom Part
    CRAFT_PHANTOMFACE("craftPhantomface", 6, -2, new ItemStack(InitBlocks.blockPhantomface), CRAFT_ADVANCED_COIL.ach),
    CRAFT_ENERGYFACE("craftPhantomEnergyface", 8, -1, new ItemStack(InitBlocks.blockPhantomEnergyface), CRAFT_PHANTOMFACE.ach),
    CRAFT_LIQUIFACE("craftPhantomLiquiface", 8, -3, new ItemStack(InitBlocks.blockPhantomLiquiface), CRAFT_PHANTOMFACE.ach),

    //Misc Achievements
    OPEN_TREASURE_CHEST("openTreasureChest", 0, 4, new ItemStack(InitBlocks.blockTreasureChest), null, InitAchievements.MISC_ACH);

    public final Achievement ach;
    public final int type;

    TheAchievements(String name, int x, int y, ItemStack displayStack, Achievement hasToHaveBefore, int type){
        this.ach = new Achievement("achievement." + ModUtil.MOD_ID_LOWER + "." + name, ModUtil.MOD_ID_LOWER + "." + name, x, y, displayStack, hasToHaveBefore);
        if(hasToHaveBefore == null) this.ach.initIndependentStat();
        this.ach.registerStat();
        this.type = type;
    }

    TheAchievements(String name, int x, int y, ItemStack displayStack, Achievement hasToHaveBefore){
        this(name, x, y, displayStack, hasToHaveBefore, InitAchievements.CRAFTING_ACH);
    }
}