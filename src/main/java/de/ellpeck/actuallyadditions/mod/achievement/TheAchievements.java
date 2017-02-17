/*
 * This file ("TheAchievements.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.achievement;

import de.ellpeck.actuallyadditions.mod.achievement.InitAchievements.Type;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.JsonSerializableSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TheAchievements{

    OPEN_BOOKLET("openBooklet", 0, 0, new ItemStack(InitItems.itemBooklet), null, Type.MISC),
    NAME_SMILEY_CLOUD("nameSmileyCloud", 4, 3, new ItemStack(InitBlocks.blockSmileyCloud), null, Type.MISC, true, 0),
    OPEN_TREASURE_CHEST("openTreasureChest", 1, -3, new ItemStack(InitBlocks.blockTreasureChest), OPEN_BOOKLET, Type.MISC),
    CRAFT_COAL_GEN("craftCoalGen", -2, 0, new ItemStack(InitBlocks.blockCoalGenerator), OPEN_BOOKLET),
    CRAFT_LEAF_GEN("craftLeafGen", -3, -2, new ItemStack(InitBlocks.blockLeafGenerator), CRAFT_COAL_GEN),
    CRAFT_RECONSTRUCTOR("craftReconstructor", -5, 0, new ItemStack(InitBlocks.blockAtomicReconstructor), CRAFT_COAL_GEN),
    MAKE_FIRST_CRYSTAL("makeCrystal", -4, 2, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), CRAFT_RECONSTRUCTOR, Type.PICK_UP, new ItemStack(InitItems.itemCrystal, 1, Util.WILDCARD), new ItemStack(InitBlocks.blockCrystal, 1, Util.WILDCARD)),
    CRAFT_EMPOWERER("craftEmpowerer", -4, 4, new ItemStack(InitBlocks.blockEmpowerer), MAKE_FIRST_CRYSTAL),
    CRAFT_PHANTOMFACE("craftPhantomface", 2, 0, new ItemStack(InitBlocks.blockPhantomface), OPEN_BOOKLET),
    CRAFT_LIQUIFACE("craftLiquiface", 2, 2, new ItemStack(InitBlocks.blockPhantomLiquiface), CRAFT_PHANTOMFACE),
    CRAFT_ENERGYFACE("craftEnergyface", 4, -1, new ItemStack(InitBlocks.blockPhantomEnergyface), CRAFT_PHANTOMFACE),
    CRAFT_LASER_RELAY("craftLaserRelay", -7, -2, new ItemStack(InitBlocks.blockLaserRelay), CRAFT_RECONSTRUCTOR),
    CRAFT_LASER_RELAY_ITEM("craftLaserRelayItem", -9, -2, new ItemStack(InitBlocks.blockLaserRelayItem), CRAFT_LASER_RELAY, Type.PICK_UP),
    CRAFT_ITEM_INTERFACE("craftItemInterface", -11, -3, new ItemStack(InitBlocks.blockItemViewer), CRAFT_LASER_RELAY_ITEM),
    CRAFT_LASER_RELAY_ADVANCED("craftLaserRelayAdvanced", -7, -4, new ItemStack(InitBlocks.blockLaserRelayAdvanced), CRAFT_LASER_RELAY),
    CRAFT_LASER_RELAY_EXTREME("craftLaserRelayExtreme", -9, -4, new ItemStack(InitBlocks.blockLaserRelayExtreme), CRAFT_LASER_RELAY_ADVANCED),
    CRAFT_CRUSHER("craftCrusher", -8, 0, new ItemStack(InitBlocks.blockGrinder), CRAFT_RECONSTRUCTOR),
    CRAFT_DOUBLE_CRUSHER("craftDoubleCrusher", -10, 1, new ItemStack(InitBlocks.blockGrinderDouble), CRAFT_CRUSHER),
    PICK_UP_COFFEE("pickUpCoffee", -2, 2, new ItemStack(InitItems.itemCoffeeBean), CRAFT_COAL_GEN, Type.PICK_UP),
    CRAFT_COFFEE_MACHINE("craftCoffeeMachine", -1, 3, new ItemStack(InitBlocks.blockCoffeeMachine), PICK_UP_COFFEE),
    CRAFT_FIREWORK_BOX("craftFireworkBox", -4, -5, new ItemStack(InitBlocks.blockFireworkBox), null, Type.CRAFTING, true, 0),
    GET_UNPROBED("getUnProbed", -7, 3, new ItemStack(InitItems.itemPlayerProbe), null, Type.MISC, true, 0),

    GET_CRYSTALS_MILESTONE("getCrystalsMilestone", 6, -3, new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.DIAMOND.ordinal()), null, Type.PICK_UP, true, 200, new ItemStack(InitItems.itemCrystal, 1, Util.WILDCARD), new ItemStack(InitBlocks.blockCrystal, 1, Util.WILDCARD)),
    OPEN_BOOKLET_MILESTONE("openBookletMilestone", 6, -1, new ItemStack(InitItems.itemBooklet), null, Type.MISC, true, 50),
    COMPLETE_TRIALS("completeTrials", 6, 1, new ItemStack(Items.GOLD_INGOT), null, Type.MISC, true, 0);

    public final Achievement chieve;
    public final Type type;
    public final int progressToReach;
    public List<ItemStack> itemsToBeGotten;

    TheAchievements(String name, int x, int y, ItemStack displayStack, TheAchievements hasToHaveBefore, ItemStack... specialItemsToBeGotten){
        this(name, x, y, displayStack, hasToHaveBefore, Type.CRAFTING, false, 0, specialItemsToBeGotten);
    }

    TheAchievements(String name, int x, int y, ItemStack displayStack, TheAchievements hasToHaveBefore, Type type, boolean special, int progressToReach, ItemStack... specialItemsToBeGotten){
        this.type = type;
        this.progressToReach = progressToReach;

        this.chieve = new Achievement("achievement."+ModUtil.MOD_ID+"."+name, ModUtil.MOD_ID+"."+name, x, y, displayStack, hasToHaveBefore == null ? null : hasToHaveBefore.chieve);
        if(hasToHaveBefore == null){
            this.chieve.initIndependentStat();
        }
        if(special){
            this.chieve.setSpecial();
        }
        if(progressToReach > 0){
            this.chieve.setSerializableClazz(JsonSerializableSet.class);
        }
        this.chieve.registerStat();

        if(specialItemsToBeGotten == null || specialItemsToBeGotten.length <= 0){
            this.itemsToBeGotten = Collections.singletonList(displayStack);
        }
        else{
            this.itemsToBeGotten = Arrays.asList(specialItemsToBeGotten);
        }
    }

    TheAchievements(String name, int x, int y, ItemStack displayStack, TheAchievements hasToHaveBefore, Type type, ItemStack... specialItemsToBeGotten){
        this(name, x, y, displayStack, hasToHaveBefore, type, false, 0, specialItemsToBeGotten);
    }

    public void get(EntityPlayer player){
        this.get(player, 1);
    }

    public void get(EntityPlayer player, int amount){
        if(this.progressToReach > 0){
            this.updateStatus(player, amount);
        }
        else{
            player.addStat(this.chieve);
        }
    }

    private void updateStatus(EntityPlayer player, int amount){
        if(player instanceof EntityPlayerMP){

            StatisticsManager manager = ((EntityPlayerMP)player).getStatFile();
            if(manager != null && !manager.hasAchievementUnlocked(this.chieve) && manager.canUnlockAchievement(this.chieve)){

                JsonSerializableSet data = manager.getProgress(this.chieve);
                if(data == null){
                    data = manager.setProgress(this.chieve, new JsonSerializableSet());
                }

                int gottenSoFar = 0;
                for(String strg : data){
                    try{
                        int i = Integer.parseInt(strg);
                        gottenSoFar += i;
                        data.remove(strg);
                    }
                    catch(Exception e){
                        data.remove(strg);
                    }
                }

                gottenSoFar += amount;
                if(gottenSoFar >= this.progressToReach){
                    player.addStat(this.chieve);
                }
                else{
                    data.add(Integer.toString(gottenSoFar));
                }
            }
        }
    }
}