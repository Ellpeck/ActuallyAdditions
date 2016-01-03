/*
 * This file ("HairyBallHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.recipe;

import de.ellpeck.actuallyadditions.items.InitItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

import java.util.ArrayList;

public class HairyBallHandler{

    public static ArrayList<Return> returns = new ArrayList<Return>();

    public static void init(){
        addReturn(new ItemStack(Items.string), 100);
        addReturn(new ItemStack(Items.diamond), 2);
        addReturn(new ItemStack(Items.name_tag), 1);
        addReturn(new ItemStack(Items.fish), 80);
        addReturn(new ItemStack(Items.fish, 1, 1), 60);
        addReturn(new ItemStack(Items.fish, 1, 2), 10);
        addReturn(new ItemStack(Items.fish, 1, 3), 40);
        addReturn(new ItemStack(Items.feather), 60);
        addReturn(new ItemStack(Items.leather), 30);
        addReturn(new ItemStack(Items.dye), 70);
        addReturn(new ItemStack(Items.clay_ball), 40);
        addReturn(new ItemStack(Items.stick), 40);
        addReturn(new ItemStack(Items.iron_ingot), 10);
        addReturn(new ItemStack(Items.gold_ingot), 6);
        addReturn(new ItemStack(Items.beef), 30);
        addReturn(new ItemStack(Items.ender_pearl), 2);
        addReturn(new ItemStack(Blocks.planks), 20);
        addReturn(new ItemStack(Blocks.waterlily), 10);
        addReturn(new ItemStack(Items.experience_bottle), 3);
        addReturn(new ItemStack(Blocks.gravel), 40);
        addReturn(new ItemStack(Blocks.sand), 50);
        addReturn(new ItemStack(Blocks.vine), 30);
        addReturn(new ItemStack(Blocks.web), 4);
        addReturn(new ItemStack(InitItems.itemSolidifiedExperience), 20);
    }

    public static void addReturn(ItemStack stack, int chance){
        returns.add(new Return(stack, chance));
    }

    public static class Return extends WeightedRandom.Item{

        public ItemStack returnItem;
        public ItemStack inputItem;

        public Return(ItemStack returnItem, int chance){
            super(chance);
            this.returnItem = returnItem;
            this.inputItem = new ItemStack(InitItems.itemHairyBall);
        }

    }

}
