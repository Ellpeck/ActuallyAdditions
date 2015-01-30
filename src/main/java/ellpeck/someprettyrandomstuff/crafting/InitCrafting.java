package ellpeck.someprettyrandomstuff.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheFoods;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class InitCrafting {

    public static void init(){

        GameRegistry.addSmelting(new ItemStack(InitItems.itemMisc, 1,
                TheMiscItems.DOUGH.ordinal()), new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()), 1F);

        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.DOUGH.ordinal()),
                new ItemStack(Items.wheat), new ItemStack(Items.wheat));

        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.PIZZA.ordinal()),
                "HHH", "MCF", " D ",
                'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()),
                'M', new ItemStack(Blocks.brown_mushroom),
                'C', new ItemStack(Items.carrot),
                'F', new ItemStack(Items.fish),
                'H', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()));

        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.HAMBURGER.ordinal()),
                " T ", "CBS", " T ",
                'T', new ItemStack(InitItems.itemFoods, 1, TheFoods.TOAST.ordinal()),
                'C', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()),
                'S', new ItemStack(Blocks.leaves),
                'B', new ItemStack(Items.cooked_beef));

        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.BIG_COOKIE.ordinal()),
                "DCD", "CDC", "DCD",
                'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DOUGH.ordinal()),
                'C', new ItemStack(Items.dye, 1, 3));

        GameRegistry.addRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.SUBMARINE_SANDWICH.ordinal()),
                "PCP", "FBS", "PCP",
                'P', new ItemStack(Items.paper),
                'C', new ItemStack(InitItems.itemFoods, 1, TheFoods.CHEESE.ordinal()),
                'F', new ItemStack(Items.fish),
                'B', new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal()),
                'S', new ItemStack(Blocks.leaves));

    }

}
