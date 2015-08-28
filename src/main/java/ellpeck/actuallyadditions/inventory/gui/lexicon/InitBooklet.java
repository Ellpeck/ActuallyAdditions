package ellpeck.actuallyadditions.inventory.gui.lexicon;

import ellpeck.actuallyadditions.crafting.FoodCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class InitBooklet{

    public static ArrayList<BookletIndexEntry> entries = new ArrayList<BookletIndexEntry>();

    public static BookletIndexEntry entryMisc = new BookletIndexEntry("misc");

    static{
        new BookletChapter("foods", entryMisc, new PageCrafting(1, FoodCrafting.recipePizza), new PageFurnace(2, new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE_BREAD.ordinal())), new PageCrafting(3, FoodCrafting.recipeHamburger), new PageCrafting(4, FoodCrafting.recipeBigCookie), new PageCrafting(5, FoodCrafting.recipeSubSandwich), new PageCrafting(6, FoodCrafting.recipeFrenchFry), new PageCrafting(7, FoodCrafting.recipeFrenchFries), new PageCrafting(8, FoodCrafting.recipeFishNChips), new PageCrafting(9, FoodCrafting.recipeCheese), new PageCrafting(10, FoodCrafting.recipePumpkinStew), new PageCrafting(11, FoodCrafting.recipeCarrotJuice), new PageCrafting(12, FoodCrafting.recipeSpaghetti), new PageCrafting(13, FoodCrafting.recipeNoodle), new PageCrafting(14, FoodCrafting.recipeChocolate), new PageCrafting(15, FoodCrafting.recipeChocolateCake), new PageCrafting(16, FoodCrafting.recipeToast), new PageFurnace(17, new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal())));
    }
}
