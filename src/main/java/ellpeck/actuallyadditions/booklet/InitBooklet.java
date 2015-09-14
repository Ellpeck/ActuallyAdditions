/*
 * This file ("InitBooklet.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheColoredLampColors;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.booklet.page.PageCrafting;
import ellpeck.actuallyadditions.booklet.page.PageFurnace;
import ellpeck.actuallyadditions.booklet.page.PageTextOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.crafting.BlockCrafting;
import ellpeck.actuallyadditions.crafting.FoodCrafting;
import ellpeck.actuallyadditions.crafting.ItemCrafting;
import ellpeck.actuallyadditions.crafting.ToolCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

public class InitBooklet{

    public static ArrayList<BookletIndexEntry> entries = new ArrayList<BookletIndexEntry>();
    public static ArrayList<BookletPage> pagesWithItemStackData = new ArrayList<BookletPage>();

    public static BookletChapter chapterIntro;

    public static BookletIndexEntry entryFunctionalNonRF = new BookletIndexEntry("functionalNoRF");
    public static BookletIndexEntry entryFunctionalRF = new BookletIndexEntry("functionalRF");
    public static BookletIndexEntry entryGeneratingRF = new BookletIndexEntry("generatingRF");
    public static BookletIndexEntry entryItemsNonRF = new BookletIndexEntry("itemsNoRF");
    public static BookletIndexEntry entryItemsRF = new BookletIndexEntry("itemsRF");
    public static BookletIndexEntry entryMisc = new BookletIndexEntry("misc");
    public static BookletIndexEntry allAndSearch = new BookletEntryAllSearch("allAndSearch");

    static{
        chapterIntro = new BookletChapter("intro", entryMisc, new ItemStack(InitItems.itemLexicon), new PageTextOnly(1), new PageTextOnly(2), new PageCrafting(3, ItemCrafting.recipeBook));

        //Miscellaneous
        new BookletChapter("craftingIngs", entryMisc, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeCoil).setNoText(), new PageCrafting(3, ItemCrafting.recipeCoilAdvanced).setNoText(), new PageCrafting(4, BlockCrafting.recipeCase).setNoText(), new PageCrafting(5, BlockCrafting.recipeStoneCase).setNoText(), new PageCrafting(6, BlockCrafting.recipeEnderPearlBlock).setNoText(), new PageCrafting(7, BlockCrafting.recipeEnderCase).setNoText(), new PageCrafting(8, ItemCrafting.recipeRing).setNoText(), new PageCrafting(9, ItemCrafting.recipeKnifeHandle).setNoText(), new PageCrafting(10, ItemCrafting.recipeKnifeBlade).setNoText(), new PageCrafting(11, ItemCrafting.recipeKnife).setNoText(), new PageCrafting(12, ItemCrafting.recipeDough).setNoText(), new PageCrafting(13, ItemCrafting.recipeRiceDough).setNoText());
        new BookletChapter("quartz", entryMisc, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), new PageTextOnly(1).setStack(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal())).addTextReplacement("<lowest>", ConfigIntValues.BLACK_QUARTZ_MIN_HEIGHT.getValue()).addTextReplacement("<highest>", ConfigIntValues.BLACK_QUARTZ_MAX_HEIGHT.getValue()), new PageTextOnly(2).setStack(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())), new PageCrafting(3, BlockCrafting.recipeQuartzBlock).setNoText(), new PageCrafting(4, BlockCrafting.recipeQuartzPillar).setNoText(), new PageCrafting(5, BlockCrafting.recipeQuartzChiseled).setNoText());
        new BookletChapter("cloud", entryMisc, new ItemStack(InitBlocks.blockSmileyCloud), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeSmileyCloud).setNoText());
        new BookletChapter("coalStuff", entryMisc, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_COAL.ordinal()), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeTinyCoal).setNoText(), new PageCrafting(3, ItemCrafting.recipeTinyChar).setNoText(), new PageCrafting(4, BlockCrafting.recipeBlockChar).setNoText());
        new BookletChapter("lamps", entryMisc, new ItemStack(InitBlocks.blockColoredLampOn, 1, TheColoredLampColors.GREEN.ordinal()), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipePowerer).setNoText(), new PageCrafting(3, BlockCrafting.recipesLamps[0]).setNoText(), new PageCrafting(4, BlockCrafting.recipesLamps[1]).setNoText(), new PageCrafting(5, BlockCrafting.recipesLamps[2]).setNoText(), new PageCrafting(6, BlockCrafting.recipesLamps[3]).setNoText(), new PageCrafting(7, BlockCrafting.recipesLamps[4]).setNoText(), new PageCrafting(8, BlockCrafting.recipesLamps[5]).setNoText(), new PageCrafting(9, BlockCrafting.recipesLamps[6]).setNoText(), new PageCrafting(10, BlockCrafting.recipesLamps[7]).setNoText(), new PageCrafting(11, BlockCrafting.recipesLamps[8]).setNoText(), new PageCrafting(12, BlockCrafting.recipesLamps[9]).setNoText(), new PageCrafting(13, BlockCrafting.recipesLamps[10]).setNoText(), new PageCrafting(14, BlockCrafting.recipesLamps[11]).setNoText(), new PageCrafting(15, BlockCrafting.recipesLamps[12]).setNoText(), new PageCrafting(16, BlockCrafting.recipesLamps[13]).setNoText(), new PageCrafting(17, BlockCrafting.recipesLamps[14]).setNoText(), new PageCrafting(18, BlockCrafting.recipesLamps[15]).setNoText());
        new BookletChapter("treasureChest", entryMisc, new ItemStack(InitBlocks.blockTreasureChest), new PageTextOnly(1).setStack(new ItemStack(InitBlocks.blockTreasureChest)));

        //No RF Using Blocks
        new BookletChapter("breaker", entryFunctionalNonRF, new ItemStack(InitBlocks.blockBreaker), new PageCrafting(1, BlockCrafting.recipeBreaker), new PageCrafting(2, BlockCrafting.recipePlacer), new PageCrafting(3, BlockCrafting.recipeLiquidPlacer), new PageCrafting(4, BlockCrafting.recipeLiquidCollector));
        new BookletChapter("phantomfaces", entryFunctionalNonRF, new ItemStack(InitBlocks.blockPhantomLiquiface), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipePhantomface), new PageCrafting(3, BlockCrafting.recipeLiquiface), new PageCrafting(4, BlockCrafting.recipeEnergyface), new PageCrafting(5, ItemCrafting.recipePhantomConnector), new PageCrafting(6, BlockCrafting.recipePhantomBooster));
        new BookletChapter("phantomBreaker", entryFunctionalNonRF, new ItemStack(InitBlocks.blockPhantomBreaker), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipePhantomPlacer), new PageCrafting(3, BlockCrafting.recipePhantomBreaker));
        new BookletChapter("esd", entryFunctionalNonRF, new ItemStack(InitBlocks.blockInputterAdvanced), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeESD), new PageCrafting(3, BlockCrafting.recipeAdvancedESD));
        new BookletChapter("xpSolidifier", entryFunctionalNonRF, new ItemStack(InitBlocks.blockXPSolidifier), new PageTextOnly(1).setStack(new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal())), new PageCrafting(2, BlockCrafting.recipeSolidifier));
        new BookletChapter("greenhouseGlass", entryFunctionalNonRF, new ItemStack(InitBlocks.blockGreenhouseGlass), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeGlass));
        new BookletChapter("fishingNet", entryFunctionalNonRF, new ItemStack(InitBlocks.blockFishingNet), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeFisher));
        new BookletChapter("feeder", entryFunctionalNonRF, new ItemStack(InitBlocks.blockFeeder), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeFeeder));
        new BookletChapter("compost", entryFunctionalNonRF, new ItemStack(InitBlocks.blockCompost), new PageTextOnly(1).setStack(new ItemStack(InitItems.itemFertilizer)), new PageCrafting(2, BlockCrafting.recipeCompost), new PageCrafting(3, ItemCrafting.recipeMashedFood));
        new BookletChapter("crate", entryFunctionalNonRF, new ItemStack(InitBlocks.blockGiantChest), new PageCrafting(1, BlockCrafting.recipeCrate));

        //RF Using Blocks
        new BookletChapter("coffeeMachine", entryFunctionalRF, new ItemStack(InitBlocks.blockCoffeeMachine), new PageTextOnly(1), new PageTextOnly(2), new PageTextOnly(3), new PageCrafting(4, BlockCrafting.recipeCoffeeMachine), new PageCrafting(5, ItemCrafting.recipeCup));
        new BookletChapterCrusher("crusher", entryFunctionalRF, new ItemStack(InitBlocks.blockGrinderDouble), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeCrusher), new PageCrafting(3, BlockCrafting.recipeDoubleCrusher));
        new BookletChapterFurnace("furnaceDouble", entryFunctionalRF, new ItemStack(InitBlocks.blockFurnaceDouble), new PageCrafting(1, BlockCrafting.recipeFurnace));
        new BookletChapter("miner", entryFunctionalRF, new ItemStack(InitBlocks.blockOreMagnet), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeMiner), new PageCrafting(3, BlockCrafting.recipeCasing));
        new BookletChapter("lavaFactory", entryFunctionalRF, new ItemStack(InitBlocks.blockLavaFactoryController), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeLavaFactory));
        new BookletChapter("energizer", entryFunctionalRF, new ItemStack(InitBlocks.blockEnergizer), new PageCrafting(1, BlockCrafting.recipeEnergizer), new PageCrafting(2, BlockCrafting.recipeEnervator));
        new BookletChapter("repairer", entryFunctionalRF, new ItemStack(InitBlocks.blockItemRepairer), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeRepairer));

        //RF Generating Blocks
        new BookletChapter("coalGen", entryGeneratingRF, new ItemStack(InitBlocks.blockCoalGenerator), new PageCrafting(1, BlockCrafting.recipeCoalGen));
        new BookletChapter("solarPanel", entryGeneratingRF, new ItemStack(InitBlocks.blockFurnaceSolar), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeSolar));
        new BookletChapter("heatCollector", entryGeneratingRF, new ItemStack(InitBlocks.blockHeatCollector), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeHeatCollector));
        new BookletChapter("canola", entryGeneratingRF, new ItemStack(InitBlocks.blockFermentingBarrel), new PageTextOnly(1), new PageTextOnly(2).setStack(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())), new PageCrafting(3, BlockCrafting.recipeCanolaPress), new PageCrafting(4, BlockCrafting.recipeFermentingBarrel), new PageCrafting(5, BlockCrafting.recipeOilGen));

        //No RF Using Items
        new BookletChapter("wings", entryItemsNonRF, new ItemStack(InitItems.itemWingsOfTheBats), new PageTextOnly(1).setStack(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BAT_WING.ordinal())), new PageCrafting(2, ItemCrafting.recipeWings));
        new BookletChapter("foods", entryItemsNonRF, new ItemStack(InitItems.itemFoods, 1, TheFoods.HAMBURGER.ordinal()), new PageCrafting(1, FoodCrafting.recipePizza), new PageFurnace(2, new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE_BREAD.ordinal())), new PageCrafting(3, FoodCrafting.recipeHamburger), new PageCrafting(4, FoodCrafting.recipeBigCookie), new PageCrafting(5, FoodCrafting.recipeSubSandwich), new PageCrafting(6, FoodCrafting.recipeFrenchFry), new PageCrafting(7, FoodCrafting.recipeFrenchFries), new PageCrafting(8, FoodCrafting.recipeFishNChips), new PageCrafting(9, FoodCrafting.recipeCheese), new PageCrafting(10, FoodCrafting.recipePumpkinStew), new PageCrafting(11, FoodCrafting.recipeCarrotJuice), new PageCrafting(12, FoodCrafting.recipeSpaghetti), new PageCrafting(13, FoodCrafting.recipeNoodle), new PageCrafting(14, FoodCrafting.recipeChocolate), new PageCrafting(15, FoodCrafting.recipeChocolateCake), new PageCrafting(16, FoodCrafting.recipeToast), new PageFurnace(17, new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal())));
        new BookletChapter("leafBlower", entryItemsNonRF, new ItemStack(InitItems.itemLeafBlowerAdvanced), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeLeafBlower), new PageCrafting(3, ItemCrafting.recipeLeafBlowerAdvanced));
        ArrayList<BookletPage> aiotPages = new ArrayList<BookletPage>();
        aiotPages.add(new PageTextOnly(aiotPages.size()+1));
        for(IRecipe recipe : ToolCrafting.recipesPaxels){
            aiotPages.add(new PageCrafting(aiotPages.size()+1, recipe));
        }
        new BookletChapter("aiots", entryItemsNonRF, new ItemStack(InitItems.emeraldPaxel), aiotPages.toArray(new BookletPage[aiotPages.size()]));

        new BookletChapter("jams", entryItemsNonRF, new ItemStack(InitItems.itemJams), new PageTextOnly(1).setStack(new ItemStack(InitItems.itemJams)));

        ArrayList<BookletPage> potionRingPages = new ArrayList<BookletPage>();
        potionRingPages.add(new PageTextOnly(potionRingPages.size()+1));
        for(IRecipe recipe : ItemCrafting.recipesPotionRings){
            potionRingPages.add(new PageCrafting(potionRingPages.size()+1, recipe));
        }
        new BookletChapter("potionRings", entryItemsNonRF, new ItemStack(InitItems.itemPotionRing), potionRingPages.toArray(new BookletPage[potionRingPages.size()]));

        //RF Using Items
        new BookletChapter("drill", entryItemsRF, new ItemStack(InitItems.itemDrill), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeDrill), new PageCrafting(3, ItemCrafting.recipeDrillSpeedI), new PageCrafting(4, ItemCrafting.recipeDrillSpeedII), new PageCrafting(5, ItemCrafting.recipeDrillSpeedIII), new PageCrafting(6, ItemCrafting.recipeDrillFortuneI), new PageCrafting(7, ItemCrafting.recipeDrillFortuneII), new PageCrafting(8, ItemCrafting.recipeDrillSilk), new PageCrafting(9, ItemCrafting.recipeDrillThree), new PageCrafting(10, ItemCrafting.recipeDrillFive), new PageCrafting(11, ItemCrafting.recipeDrillPlacing));
        new BookletChapter("staff", entryItemsRF, new ItemStack(InitItems.itemTeleStaff), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeStaff));
        new BookletChapter("magnetRing", entryItemsRF, new ItemStack(InitItems.itemMagnetRing), new PageCrafting(1, ItemCrafting.recipeMagnetRing));
        new BookletChapter("growthRing", entryItemsRF, new ItemStack(InitItems.itemGrowthRing), new PageCrafting(1, ItemCrafting.recipeGrowthRing));
        new BookletChapter("waterRemovalRing", entryItemsRF, new ItemStack(InitItems.itemWaterRemovalRing), new PageCrafting(1, ItemCrafting.recipeWaterRing));
        new BookletChapter("batteries", entryItemsRF, new ItemStack(InitItems.itemBatteryTriple), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeBattery), new PageCrafting(3, ItemCrafting.recipeBatteryDouble), new PageCrafting(4, ItemCrafting.recipeBatteryTriple), new PageCrafting(5, ItemCrafting.recipeBatteryQuadruple), new PageCrafting(6, ItemCrafting.recipeBatteryQuintuple));
    }
}
