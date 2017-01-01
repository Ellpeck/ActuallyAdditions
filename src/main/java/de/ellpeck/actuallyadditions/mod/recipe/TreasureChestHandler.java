/*
 * This file ("TreasureChestHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.recipe;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheJams;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public final class TreasureChestHandler{

    public static void init(){
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.DIAMOND), 5, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.IRON_INGOT), 30, 1, 5);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.GOLD_NUGGET), 60, 1, 8);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.GOLD_INGOT), 35, 1, 3);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.ENDER_PEARL), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.EMERALD), 3, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.EXPERIENCE_BOTTLE), 5, 3, 6);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemSolidifiedExperience), 15, 3, 6);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_11), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_13), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_BLOCKS), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_CAT), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_CHIRP), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_FAR), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_MALL), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_MELLOHI), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_STAL), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_STRAD), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_WARD), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.RECORD_WAIT), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.SADDLE), 5, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.NAME_TAG), 20, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.CU_BA_RA.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.GRA_KI_BA.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.PL_AP_LE.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.CH_AP_CI.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.HO_ME_KI.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.PI_CO.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.FISH), 80, 1, 3);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.FISH, 1, 1), 60, 1, 3);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.FISH, 1, 2), 10, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.FISH, 1, 3), 40, 1, 2);
    }

}
