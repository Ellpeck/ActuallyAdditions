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
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public final class HairyBallHandler {

    public static void init() {
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.STRING), 100);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.DIAMOND), 2);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.NAME_TAG), 1);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.COD), 80);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.SALMON), 60);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.TROPICAL_FISH), 10);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.PUFFERFISH), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.FEATHER), 60);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.LEATHER), 30);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.BLACK_DYE), 70); // todo: expand to more dyes
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.CLAY_BALL), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.STICK), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.IRON_INGOT), 10);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.GOLD_INGOT), 6);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.BEEF), 30);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.ENDER_PEARL), 2);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.OAK_PLANKS), 20); // todo: expand to more planks
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.LILY_PAD), 10);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.EXPERIENCE_BOTTLE), 3);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.GRAVEL), 40);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.SAND), 50);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.VINE), 30);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Blocks.COBWEB), 4);
        ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(ActuallyItems.itemSolidifiedExperience.get()), 20);
    }

}
