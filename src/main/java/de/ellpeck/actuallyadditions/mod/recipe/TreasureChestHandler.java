/*
 * This file ("TreasureChestHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.recipe;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheJams;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TreasureChestHandler{

    public static void init(){
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.diamond), 5, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.iron_ingot), 30, 1, 5);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.gold_nugget), 60, 1, 8);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.gold_ingot), 35, 1, 3);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.ender_pearl), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.emerald), 3, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.experience_bottle), 5, 3, 6);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemSolidifiedExperience), 15, 3, 6);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_11), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_13), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_blocks), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_cat), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_chirp), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_far), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_mall), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_mellohi), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_stal), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_strad), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_ward), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.record_wait), 1, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.saddle), 5, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.name_tag), 20, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.CU_BA_RA.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.GRA_KI_BA.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.PL_AP_LE.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.CH_AP_CI.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.HO_ME_KI.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(InitItems.itemJams, 1, TheJams.PI_CO.ordinal()), 10, 1, 2);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.fish), 80, 1, 3);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.fish, 1, 1), 60, 1, 3);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.fish, 1, 2), 10, 1, 1);
        ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Items.fish, 1, 3), 40, 1, 2);
    }

}
