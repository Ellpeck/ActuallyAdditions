/*
 * This file ("MiscCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class MiscCrafting{

    public static void init(){

        //Dough
        if(ConfigCrafting.DOUGH.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.DOUGH.ordinal()),
                    "cropWheat", "cropWheat"));
            ItemCrafting.recipeDough = Util.GetRecipes.lastIRecipe();
        }

        //Rice Dough
        if(ConfigCrafting.RICE_DOUGH.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.RICE_DOUGH.ordinal()),
                    new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal())));
            ItemCrafting.recipeRiceDough = Util.GetRecipes.lastIRecipe();
        }

        //Paper Cone
        if(ConfigCrafting.PAPER_CONE.isEnabled()){
            GameRegistry.addRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    "P P", " P ",
                    'P', new ItemStack(Items.paper));
        }

        //Knife Handle
        if(ConfigCrafting.KNIFE_HANDLE.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal()),
                    "stickWood",
                    new ItemStack(Items.leather)));
            ItemCrafting.recipeKnifeHandle = Util.GetRecipes.lastIRecipe();
        }

        //Knife Blade
        if(ConfigCrafting.KNIFE_BLADE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                    "K", "K", "F",
                    'K', "ingotIron",
                    'F', new ItemStack(Items.flint)));
            ItemCrafting.recipeKnifeBlade = Util.GetRecipes.lastIRecipe();
        }
    }

}
