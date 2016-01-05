/*
 * This file ("HairyBallHandler.java") is part of the Actually Additions Mod for Minecraft.
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class HairyBallHandler{

    public static void init(){
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.string), 100);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.diamond), 2);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.name_tag), 1);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.fish), 80);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.fish, 1, 1), 60);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.fish, 1, 2), 10);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.fish, 1, 3), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.feather), 60);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.leather), 30);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.dye), 70);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.clay_ball), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.stick), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.iron_ingot), 10);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.gold_ingot), 6);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.beef), 30);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.ender_pearl), 2);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.planks), 20);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.waterlily), 10);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.experience_bottle), 3);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.gravel), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.sand), 50);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.vine), 30);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.web), 4);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(InitItems.itemSolidifiedExperience), 20);
    }

}
