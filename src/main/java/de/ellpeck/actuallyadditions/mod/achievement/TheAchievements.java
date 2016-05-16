/*
 * This file ("TheAchievements.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.achievement;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public enum TheAchievements{

    OPEN_BOOKLET("openBooklet", 0, 0, new ItemStack(InitItems.itemBooklet), null, InitAchievements.Type.MISC),
    NAME_SMILEY_CLOUD("nameSmileyCloud", 4, 2, new ItemStack(InitBlocks.blockSmileyCloud), null, InitAchievements.Type.MISC, true),
    OPEN_TREASURE_CHEST("openTreasureChest", 1, -3, new ItemStack(InitBlocks.blockTreasureChest), OPEN_BOOKLET, InitAchievements.Type.MISC),
    CRAFT_COAL_GEN("craftCoalGen", -2, 0, new ItemStack(InitBlocks.blockCoalGenerator), OPEN_BOOKLET),
    CRAFT_LEAF_GEN("craftLeafGen", -3, -2, new ItemStack(InitBlocks.blockLeafGenerator), CRAFT_COAL_GEN),
    CRAFT_RECONSTRUCTOR("craftReconstructor", -5, 0, new ItemStack(InitBlocks.blockAtomicReconstructor), CRAFT_COAL_GEN),
    CRAFT_PHANTOMFACE("craftPhantomface", 2, 0, new ItemStack(InitBlocks.blockPhantomface), OPEN_BOOKLET),
    CRAFT_LIQUIFACE("craftLiquiface", 2, 2, new ItemStack(InitBlocks.blockPhantomLiquiface), CRAFT_PHANTOMFACE),
    CRAFT_ENERGYFACE("craftEnergyface", 2, -2, new ItemStack(InitBlocks.blockPhantomEnergyface), CRAFT_PHANTOMFACE),
    CRAFT_LASER_RELAY("craftLaserRelay", -7, -2, new ItemStack(InitBlocks.blockLaserRelay), CRAFT_RECONSTRUCTOR),
    CRAFT_CRUSHER("craftCrusher", -8, 0, new ItemStack(InitBlocks.blockGrinder), CRAFT_RECONSTRUCTOR),
    PICK_UP_COFFEE("pickUpCoffee", -4, 2, new ItemStack(InitItems.itemCoffeeBean), CRAFT_RECONSTRUCTOR, InitAchievements.Type.PICK_UP),
    CRAFT_COFFEE_MACHINE("craftCoffeeMachine", -3, 3, new ItemStack(InitBlocks.blockCoffeeMachine), PICK_UP_COFFEE);

    public final Achievement ach;
    public final InitAchievements.Type type;

    TheAchievements(String name, int x, int y, ItemStack displayStack, TheAchievements hasToHaveBefore){
        this(name, x, y, displayStack, hasToHaveBefore, InitAchievements.Type.CRAFTING, false);
    }

    TheAchievements(String name, int x, int y, ItemStack displayStack, TheAchievements hasToHaveBefore, InitAchievements.Type type, boolean special){
        this.ach = new Achievement("achievement."+ModUtil.MOD_ID+"."+name, ModUtil.MOD_ID+"."+name, x, y, displayStack, hasToHaveBefore == null ? null : hasToHaveBefore.ach);
        if(hasToHaveBefore == null){
            this.ach.initIndependentStat();
        }
        if(special){
            this.ach.setSpecial();
        }
        this.ach.registerStat();
        this.type = type;
    }

    TheAchievements(String name, int x, int y, ItemStack displayStack, TheAchievements hasToHaveBefore, InitAchievements.Type type){
        this(name, x, y, displayStack, hasToHaveBefore, type, false);
    }
}