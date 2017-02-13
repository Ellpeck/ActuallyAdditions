/*
 * This file ("MiscCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
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

public final class MiscCrafting{

    public static final IRecipe[] RECIPES_CRYSTAL_SHARDS = new IRecipe[TheCrystals.values().length];
    public static final IRecipe[] RECIPES_CRYSTAL_SHARDS_BACK = new IRecipe[TheCrystals.values().length];

    public static final IRecipe[] RECIPES_CRYSTALS = new IRecipe[TheCrystals.values().length];
    public static final IRecipe[] RECIPES_CRYSTAL_BLOCKS = new IRecipe[TheCrystals.values().length];

    public static final IRecipe[] RECIPES_EMPOWERED_CRYSTALS = new IRecipe[TheCrystals.values().length];
    public static final IRecipe[] RECIPES_EMPOWERED_CRYSTAL_BLOCKS = new IRecipe[TheCrystals.values().length];

    public static void init(){

        //Bio Coal
        GameRegistry.addSmelting(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BIOMASS.ordinal()),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BIOCOAL.ordinal()), 1.0F);

        //Crystals
        for(int i = 0; i < TheCrystals.values().length; i++){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCrystal, 1, i),
                    "XXX", "XXX", "XXX",
                    'X', new ItemStack(InitItems.itemCrystal, 1, i)));
            RECIPES_CRYSTAL_BLOCKS[i] = RecipeUtil.lastIRecipe();
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemCrystal, 9, i), new ItemStack(InitBlocks.blockCrystal, 1, i)));
            RECIPES_CRYSTALS[i] = RecipeUtil.lastIRecipe();

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitBlocks.blockCrystalEmpowered, 1, i),
                    "XXX", "XXX", "XXX",
                    'X', new ItemStack(InitItems.itemCrystalEmpowered, 1, i)));
            RECIPES_EMPOWERED_CRYSTAL_BLOCKS[i] = RecipeUtil.lastIRecipe();
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemCrystalEmpowered, 9, i), new ItemStack(InitBlocks.blockCrystalEmpowered, 1, i)));
            RECIPES_EMPOWERED_CRYSTALS[i] = RecipeUtil.lastIRecipe();

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemCrystal, 1, i),
                    "XXX", "XXX", "XXX",
                    'X', new ItemStack(InitItems.itemCrystalShard, 1, i)));
            RECIPES_CRYSTAL_SHARDS[i] = RecipeUtil.lastIRecipe();
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemCrystalShard, 9, i), new ItemStack(InitItems.itemCrystal, 1, i)));
            RECIPES_CRYSTAL_SHARDS_BACK[i] = RecipeUtil.lastIRecipe();
        }

        //Dough
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.DOUGH.ordinal()),
                "cropWheat", "cropWheat"));
        ItemCrafting.recipeDough = RecipeUtil.lastIRecipe();

        //Rice Dough
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.RICE_DOUGH.ordinal()),
                new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal())));
        ItemCrafting.recipeRiceDough = RecipeUtil.lastIRecipe();

        //Paper Cone
        GameRegistry.addRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                "P P", " P ",
                'P', new ItemStack(Items.PAPER));

        //Knife Handle
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal()),
                "stickWood",
                new ItemStack(Items.LEATHER)));
        ItemCrafting.recipeKnifeHandle = RecipeUtil.lastIRecipe();

        //Knife Blade
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                "K", "K", "F",
                'K', "ingotIron",
                'F', new ItemStack(Items.FLINT)));
        ItemCrafting.recipeKnifeBlade = RecipeUtil.lastIRecipe();

        //Ender Star
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.ENDER_STAR.ordinal()),
                new ItemStack(Items.NETHER_STAR),
                new ItemStack(Items.DRAGON_BREATH),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                new ItemStack(Items.PRISMARINE_SHARD)));
        ItemCrafting.recipeEnderStar = RecipeUtil.lastIRecipe();
    }

}
