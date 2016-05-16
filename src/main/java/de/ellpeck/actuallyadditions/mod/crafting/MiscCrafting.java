/*
 * This file ("MiscCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigCrafting;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class MiscCrafting{

    public static IRecipe[] recipesCrystals = new IRecipe[TheCrystals.values().length];
    public static IRecipe[] recipesCrystalBlocks = new IRecipe[TheCrystals.values().length];

    public static void init(){

        //Crystals
        for(int i = 0; i < TheCrystals.values().length; i++){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCrystal, 1, i),
                    "XXX", "XXX", "XXX",
                    'X', new ItemStack(InitItems.itemCrystal, 1, i)));
            recipesCrystalBlocks[i] = RecipeUtil.lastIRecipe();
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemCrystal, 9, i), new ItemStack(InitBlocks.blockCrystal, 1, i)));
            recipesCrystals[i] = RecipeUtil.lastIRecipe();
        }

        //Dough
        if(ConfigCrafting.DOUGH.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.DOUGH.ordinal()),
                    "cropWheat", "cropWheat"));
            ItemCrafting.recipeDough = RecipeUtil.lastIRecipe();
        }

        //Rice Dough
        if(ConfigCrafting.RICE_DOUGH.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.RICE_DOUGH.ordinal()),
                    new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal())));
            ItemCrafting.recipeRiceDough = RecipeUtil.lastIRecipe();
        }

        //Paper Cone
        if(ConfigCrafting.PAPER_CONE.isEnabled()){
            GameRegistry.addRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    "P P", " P ",
                    'P', new ItemStack(Items.PAPER));
        }

        //Knife Handle
        if(ConfigCrafting.KNIFE_HANDLE.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal()),
                    "stickWood",
                    new ItemStack(Items.LEATHER)));
            ItemCrafting.recipeKnifeHandle = RecipeUtil.lastIRecipe();
        }

        //Knife Blade
        if(ConfigCrafting.KNIFE_BLADE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                    "K", "K", "F",
                    'K', "ingotIron",
                    'F', new ItemStack(Items.FLINT)));
            ItemCrafting.recipeKnifeBlade = RecipeUtil.lastIRecipe();
        }

        //Ender Star
        if(ConfigCrafting.ENDER_STAR.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.ENDER_STAR.ordinal()),
                    new ItemStack(Items.NETHER_STAR),
                    new ItemStack(Items.DRAGON_BREATH),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
            ItemCrafting.recipeEnderStar = RecipeUtil.lastIRecipe();
        }
    }

}
