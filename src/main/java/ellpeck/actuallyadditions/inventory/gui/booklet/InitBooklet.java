package ellpeck.actuallyadditions.inventory.gui.booklet;

import ellpeck.actuallyadditions.crafting.BlockCrafting;
import ellpeck.actuallyadditions.crafting.FoodCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class InitBooklet{

    public static ArrayList<BookletIndexEntry> entries = new ArrayList<BookletIndexEntry>();

    public static BookletIndexEntry entryFunctionalNonRF = new BookletIndexEntry("functionalNoRF");
    public static BookletIndexEntry entryMisc = new BookletIndexEntry("misc");
    public static BookletIndexEntry allAndSearch = new BookletIndexEntry("allAndSearch");

    static{
        new BookletChapter("breaker", entryFunctionalNonRF, new PageCrafting(1, BlockCrafting.recipeBreaker), new PageCrafting(2, BlockCrafting.recipePlacer), new PageCrafting(3, BlockCrafting.recipeLiquidPlacer), new PageCrafting(4, BlockCrafting.recipeLiquidCollector));
        new BookletChapter("phantomfaces", entryFunctionalNonRF, new PageText(1), new PageCrafting(2, BlockCrafting.recipePhantomface), new PageCrafting(3, BlockCrafting.recipeLiquiface), new PageCrafting(4, BlockCrafting.recipeEnergyface));
        new BookletChapter("phantomBreaker", entryFunctionalNonRF, new PageText(1), new PageCrafting(2, BlockCrafting.recipePhantomPlacer), new PageCrafting(3, BlockCrafting.recipePhantomBreaker));

        new BookletChapter("cloud", entryMisc, new PageText(1), new PageCrafting(2, BlockCrafting.recipeSmileyCloud));
        new BookletChapter("foods", entryMisc, new PageCrafting(1, FoodCrafting.recipePizza), new PageFurnace(2, new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE_BREAD.ordinal())), new PageCrafting(3, FoodCrafting.recipeHamburger), new PageCrafting(4, FoodCrafting.recipeBigCookie), new PageCrafting(5, FoodCrafting.recipeSubSandwich), new PageCrafting(6, FoodCrafting.recipeFrenchFry), new PageCrafting(7, FoodCrafting.recipeFrenchFries), new PageCrafting(8, FoodCrafting.recipeFishNChips), new PageCrafting(9, FoodCrafting.recipeCheese), new PageCrafting(10, FoodCrafting.recipePumpkinStew), new PageCrafting(11, FoodCrafting.recipeCarrotJuice), new PageCrafting(12, FoodCrafting.recipeSpaghetti), new PageCrafting(13, FoodCrafting.recipeNoodle), new PageCrafting(14, FoodCrafting.recipeChocolate), new PageCrafting(15, FoodCrafting.recipeChocolateCake), new PageCrafting(16, FoodCrafting.recipeToast), new PageFurnace(17, new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal())));
    }
}
