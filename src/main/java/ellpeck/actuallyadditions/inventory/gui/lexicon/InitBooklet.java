package ellpeck.actuallyadditions.inventory.gui.lexicon;

import ellpeck.actuallyadditions.crafting.FoodCrafting;

import java.util.ArrayList;

public class InitBooklet{

    public static ArrayList<BookletIndexEntry> entries = new ArrayList<BookletIndexEntry>();

    public static BookletIndexEntry entryFood = new BookletIndexEntry("food");

    static{
        new BookletChapter("pizza", entryFood, new PageText(1), new PageText(2), new PageCrafting(3, FoodCrafting.recipePizza));
    }
}
