/*
 * This file ("HairyBallHandler.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public final class HairyBallHandler{

    public static void init(){
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.STRING), 100);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.DIAMOND), 2);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.NAME_TAG), 1);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.FISH), 80);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.FISH, 1, 1), 60);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.FISH, 1, 2), 10);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.FISH, 1, 3), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.FEATHER), 60);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.LEATHER), 30);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.DYE), 70);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.CLAY_BALL), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.STICK), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.IRON_INGOT), 10);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.GOLD_INGOT), 6);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.BEEF), 30);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.ENDER_PEARL), 2);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.PLANKS), 20);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.WATERLILY), 10);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.EXPERIENCE_BOTTLE), 3);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.GRAVEL), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.SAND), 50);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.VINE), 30);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.WEB), 4);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(InitItems.itemSolidifiedExperience), 20);
    }

}
