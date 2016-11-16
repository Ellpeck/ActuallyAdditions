/*
 * This file ("GenericTrade.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheJams;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

public class JamVillagerTradeList implements ITradeList{

    @Override
    public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random){
        for(int i = 0; i < random.nextInt(3)+3; i++){
            ItemStack jam = new ItemStack(InitItems.itemJams, 1, random.nextInt(TheJams.values().length));
            ItemStack emerald = new ItemStack(Items.EMERALD);

            if(random.nextFloat() >= 0.65F){
                //Jam as input
                recipeList.add(new MerchantRecipe(StackUtil.setStackSize(jam, random.nextInt(3)+1), StackUtil.setStackSize(emerald, random.nextInt(2)+1)));
            }
            else{
                //Emeralds as input
                recipeList.add(new MerchantRecipe(StackUtil.setStackSize(emerald, random.nextInt(6)+2), StackUtil.setStackSize(jam, random.nextInt(4)+2)));
            }
        }
    }
}
