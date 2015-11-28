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
import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import ellpeck.actuallyadditions.booklet.chapter.BookletChapterCoffee;
import ellpeck.actuallyadditions.booklet.chapter.BookletChapterCrusher;
import ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import ellpeck.actuallyadditions.booklet.entry.BookletEntryAllSearch;
import ellpeck.actuallyadditions.booklet.page.*;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.crafting.*;
import ellpeck.actuallyadditions.gen.OreGen;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.recipe.ReconstructorRecipeHandler;
import ellpeck.actuallyadditions.tile.*;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

public class InitBooklet{

    public static ArrayList<BookletEntry> entries = new ArrayList<BookletEntry>();
    public static ArrayList<BookletPage> pagesWithItemStackData = new ArrayList<BookletPage>();

    public static int wordCount;
    public static int charCount;

    public static BookletChapter chapterIntro;

    public static BookletEntry entryGettingStarted = new BookletEntry("gettingStarted").setImportant();
    public static BookletEntry entryFunctionalNonRF = new BookletEntry("functionalNoRF");
    public static BookletEntry entryFunctionalRF = new BookletEntry("functionalRF").setSpecial();
    public static BookletEntry entryGeneratingRF = new BookletEntry("generatingRF").setSpecial();
    public static BookletEntry entryItemsNonRF = new BookletEntry("itemsNoRF");
    public static BookletEntry entryItemsRF = new BookletEntry("itemsRF").setSpecial();
    public static BookletEntry entryMisc = new BookletEntry("misc");
    public static BookletEntry allAndSearch = new BookletEntryAllSearch("allAndSearch").setSpecial();

    private static void initChapters(){
        //Getting Started
        chapterIntro = new BookletChapter("intro", entryGettingStarted, new ItemStack(InitItems.itemLexicon), new PageTextOnly(1), new PageTextOnly(2), new PageTextOnly(3));
        new BookletChapter("bookTutorial", entryGettingStarted, new ItemStack(InitItems.itemLexicon), new PageTextOnly(1), new PageTextOnly(2), new PageCrafting(3, ItemCrafting.recipeBook));
        new BookletChapter("crystals", entryGettingStarted, new ItemStack(InitBlocks.blockAtomicReconstructor), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityAtomicReconstructor.ENERGY_USE), new PageTextOnly(2), new PagePicture(3, "pageAtomicReconstructor", 0).setNoText(), new PageTextOnly(4), new PageCrafting(5, BlockCrafting.recipeAtomicReconstructor).setNoText(), new PageCrafting(6, MiscCrafting.recipesCrystals).setNoText(), new PageCrafting(7, MiscCrafting.recipesCrystalBlocks).setNoText(), new PageReconstructor(8, ReconstructorRecipeHandler.mainPageRecipes).setNoText()).setSpecial();
        new BookletChapter("coalGen", entryGettingStarted, new ItemStack(InitBlocks.blockCoalGenerator), new PageCrafting(1, BlockCrafting.recipeCoalGen).addTextReplacement("<rf>", TileEntityCoalGenerator.PRODUCE));
        new BookletChapter("craftingIngs", entryGettingStarted, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeCoil).setNoText(), new PageCrafting(3, ItemCrafting.recipeCoilAdvanced).setNoText(), new PageCrafting(4, BlockCrafting.recipeCase).setNoText(), new PageCrafting(5, BlockCrafting.recipeStoneCase).setNoText(), new PageCrafting(6, BlockCrafting.recipeEnderPearlBlock).setNoText(), new PageCrafting(7, BlockCrafting.recipeEnderCase).setNoText(), new PageCrafting(8, ItemCrafting.recipeRing).setNoText(), new PageCrafting(9, ItemCrafting.recipeKnifeHandle).setNoText(), new PageCrafting(10, ItemCrafting.recipeKnifeBlade).setNoText(), new PageCrafting(11, ItemCrafting.recipeKnife).setNoText(), new PageCrafting(12, ItemCrafting.recipeDough).setNoText(), new PageCrafting(13, ItemCrafting.recipeRiceDough).setNoText(), new PageCrafting(14, BlockCrafting.recipeIronCase).setNoText()).setImportant();

        //Miscellaneous
        new BookletChapter("reconstructorLenses", entryMisc, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeLens).setNoText(), new PageReconstructor(3, ReconstructorRecipeHandler.recipeColorLens), new PageReconstructor(4, ReconstructorRecipeHandler.colorConversionRecipes).setNoText(), new PageReconstructor(5, ReconstructorRecipeHandler.recipeExplosionLens), new PageReconstructor(6, ReconstructorRecipeHandler.recipeDamageLens)).setImportant();
        new BookletChapter("miscDecorStuffsAndThings", entryMisc, new ItemStack(InitBlocks.blockTestifiBucksGreenWall), new PageTextOnly(1), new PageReconstructor(2, ReconstructorRecipeHandler.recipeWhiteWall).setNoText(), new PageReconstructor(3, ReconstructorRecipeHandler.recipeGreenWall).setNoText());
        new BookletChapter("bookStand", entryMisc, new ItemStack(InitBlocks.blockBookletStand), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeBookStand));
        new BookletChapter("quartz", entryMisc, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), new PageTextOnly(1).setStack(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal())).addTextReplacement("<lowest>", OreGen.QUARTZ_MIN).addTextReplacement("<highest>", OreGen.QUARTZ_MAX), new PageTextOnly(2).setStack(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())), new PageCrafting(3, BlockCrafting.recipeQuartzBlock).setNoText(), new PageCrafting(4, BlockCrafting.recipeQuartzPillar).setNoText(), new PageCrafting(5, BlockCrafting.recipeQuartzChiseled).setNoText());
        new BookletChapter("cloud", entryMisc, new ItemStack(InitBlocks.blockSmileyCloud), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeSmileyCloud).setNoText()).setSpecial();
        new BookletChapter("coalStuff", entryMisc, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_COAL.ordinal()), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeTinyCoal).setNoText(), new PageCrafting(3, ItemCrafting.recipeTinyChar).setNoText(), new PageCrafting(4, BlockCrafting.recipeBlockChar).setNoText());
        ArrayList<BookletPage> lampPages = new ArrayList<BookletPage>();
        lampPages.add(new PageTextOnly(lampPages.size()+1));
        lampPages.add(new PageCrafting(lampPages.size()+1, BlockCrafting.recipePowerer).setNoText());
        for(IRecipe recipe : BlockCrafting.recipesLamps){
            lampPages.add(new PageCrafting(lampPages.size()+1, recipe).setNoText());
        }
        new BookletChapter("lamps", entryMisc, new ItemStack(InitBlocks.blockColoredLampOn, 1, TheColoredLampColors.GREEN.ordinal()), lampPages.toArray(new BookletPage[lampPages.size()]));

        new BookletChapter("treasureChest", entryMisc, new ItemStack(InitBlocks.blockTreasureChest), new PagePicture(1, "pageTreasureChest", 150).setStack(new ItemStack(InitBlocks.blockTreasureChest)), new PageTextOnly(2)).setSpecial();
        new BookletChapter("hairBalls", entryMisc, new ItemStack(InitItems.itemHairyBall), new PagePicture(1, "pageFurBalls", 110).setStack(new ItemStack(InitItems.itemHairyBall)), new PageTextOnly(2)).setSpecial();
        new BookletChapter("blackLotus", entryMisc, new ItemStack(InitBlocks.blockBlackLotus), new PageTextOnly(1).setStack(new ItemStack(InitBlocks.blockBlackLotus)), new PageCrafting(2, ItemCrafting.recipeBlackDye));

        //No RF Using Blocks
        new BookletChapter("breaker", entryFunctionalNonRF, new ItemStack(InitBlocks.blockBreaker), new PageCrafting(1, BlockCrafting.recipeBreaker), new PageCrafting(2, BlockCrafting.recipePlacer), new PageCrafting(3, BlockCrafting.recipeLiquidPlacer), new PageCrafting(4, BlockCrafting.recipeLiquidCollector));
        new BookletChapter("dropper", entryFunctionalNonRF, new ItemStack(InitBlocks.blockDropper), new PageTextOnly(1), new PageCrafting(1, BlockCrafting.recipeDropper).setNoText());
        new BookletChapter("phantomfaces", entryFunctionalNonRF, new ItemStack(InitBlocks.blockPhantomLiquiface), new PageTextOnly(1).addTextReplacement("<range>", TileEntityPhantomface.RANGE), new PageCrafting(2, BlockCrafting.recipePhantomface), new PageCrafting(3, BlockCrafting.recipeLiquiface), new PageCrafting(4, BlockCrafting.recipeEnergyface), new PageCrafting(5, ItemCrafting.recipePhantomConnector).setNoText(), new PageCrafting(6, BlockCrafting.recipePhantomBooster)).setImportant();
        new BookletChapter("phantomBreaker", entryFunctionalNonRF, new ItemStack(InitBlocks.blockPhantomBreaker), new PageTextOnly(1).addTextReplacement("<range>", TileEntityPhantomPlacer.RANGE), new PageCrafting(2, BlockCrafting.recipePhantomPlacer).setNoText(), new PageCrafting(3, BlockCrafting.recipePhantomBreaker).setNoText());
        new BookletChapter("esd", entryFunctionalNonRF, new ItemStack(InitBlocks.blockInputterAdvanced), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeESD).setNoText(), new PageCrafting(3, BlockCrafting.recipeAdvancedESD).setNoText()).setSpecial();
        new BookletChapter("xpSolidifier", entryFunctionalNonRF, new ItemStack(InitBlocks.blockXPSolidifier), new PageTextOnly(1).setStack(new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal())), new PageCrafting(2, BlockCrafting.recipeSolidifier).setNoText()).setSpecial();
        new BookletChapter("greenhouseGlass", entryFunctionalNonRF, new ItemStack(InitBlocks.blockGreenhouseGlass), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeGlass).setNoText());
        new BookletChapter("fishingNet", entryFunctionalNonRF, new ItemStack(InitBlocks.blockFishingNet), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeFisher).setNoText());
        new BookletChapter("feeder", entryFunctionalNonRF, new ItemStack(InitBlocks.blockFeeder), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeFeeder).setNoText());
        new BookletChapter("compost", entryFunctionalNonRF, new ItemStack(InitBlocks.blockCompost), new PageTextOnly(1).setStack(new ItemStack(InitItems.itemFertilizer)).addTextReplacement("<num>", TileEntityCompost.AMOUNT), new PageCrafting(2, BlockCrafting.recipeCompost).setNoText(), new PageCrafting(3, ItemCrafting.recipesMashedFood));
        new BookletChapter("crate", entryFunctionalNonRF, new ItemStack(InitBlocks.blockGiantChest), new PageCrafting(1, BlockCrafting.recipeCrate), new PageCrafting(2, ItemCrafting.recipeChestToCrateUpgrade));
        new BookletChapter("rangedCollector", entryFunctionalNonRF, new ItemStack(InitBlocks.blockRangedCollector), new PageTextOnly(1).addTextReplacement("<range>", TileEntityRangedCollector.RANGE), new PageCrafting(2, BlockCrafting.recipeRangedCollector).setNoText());

        //RF Using Blocks
        new BookletChapter("laserRelays", entryFunctionalRF, new ItemStack(InitBlocks.blockLaserRelay), new PageTextOnly(1).addTextReplacement("<range>", TileEntityLaserRelay.MAX_DISTANCE).addTextReplacement("<loss>", ConfigIntValues.LASER_RELAY_LOSS.getValue()), new PagePicture(2, "pageLaserRelay", 0).setNoText(), new PageCrafting(3, BlockCrafting.recipeLaserRelay).setNoText(), new PageCrafting(4, ItemCrafting.recipeLaserWrench).setNoText()).setImportant();
        new BookletChapterCoffee("coffeeMachine", entryFunctionalRF, new ItemStack(InitBlocks.blockCoffeeMachine), new PageTextOnly(1).setStack(new ItemStack(InitItems.itemCoffeeBean)).addTextReplacement("<rf>", TileEntityCoffeeMachine.ENERGY_USED).addTextReplacement("<coffee>", TileEntityCoffeeMachine.CACHE_USE).addTextReplacement("<water>", TileEntityCoffeeMachine.WATER_USE), new PageTextOnly(2).setStack(new ItemStack(InitItems.itemCoffee)), new PagePicture(3, "pageCoffeeMachine", 115), new PageCrafting(4, BlockCrafting.recipeCoffeeMachine).setNoText(), new PageCrafting(5, ItemCrafting.recipeCup).setNoText()).setImportant();
        new BookletChapterCrusher("crusher", entryFunctionalRF, new ItemStack(InitBlocks.blockGrinderDouble), new PageTextOnly(1).addTextReplacement("<rf1>", TileEntityGrinder.getEnergyUse(false)).addTextReplacement("<rf2>", TileEntityGrinder.getEnergyUse(true)), new PageCrafting(2, BlockCrafting.recipeCrusher).setNoText(), new PageCrafting(3, BlockCrafting.recipeDoubleCrusher).setNoText(), new PageCrusherRecipe(4, CrusherCrafting.recipeSugar).setNoText(), new PageCrusherRecipe(5, CrusherCrafting.recipeIronHorseArmor).setNoText(), new PageCrusherRecipe(6, CrusherCrafting.recipeGoldHorseArmor).setNoText(), new PageCrusherRecipe(7, CrusherCrafting.recipeDiamondHorseArmor).setNoText());
        new BookletChapter("furnaceDouble", entryFunctionalRF, new ItemStack(InitBlocks.blockFurnaceDouble), new PageCrafting(1, BlockCrafting.recipeFurnace).addTextReplacement("<rf>", TileEntityFurnaceDouble.ENERGY_USE));
        new BookletChapter("lavaFactory", entryFunctionalRF, new ItemStack(InitBlocks.blockLavaFactoryController), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityLavaFactoryController.ENERGY_USE), new PagePicture(2, "pageLavaFactory", 0).setNoText(), new PageCrafting(3, BlockCrafting.recipeLavaFactory).setNoText(), new PageCrafting(4, BlockCrafting.recipeCasing).setNoText());
        new BookletChapter("energizer", entryFunctionalRF, new ItemStack(InitBlocks.blockEnergizer), new PageCrafting(1, BlockCrafting.recipeEnergizer), new PageCrafting(2, BlockCrafting.recipeEnervator));
        new BookletChapter("repairer", entryFunctionalRF, new ItemStack(InitBlocks.blockItemRepairer), new PageCrafting(1, BlockCrafting.recipeRepairer).addTextReplacement("<rf>", TileEntityItemRepairer.ENERGY_USE));
        new BookletChapter("longRangeBreaker", entryFunctionalRF, new ItemStack(InitBlocks.blockDirectionalBreaker), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityDirectionalBreaker.ENERGY_USE).addTextReplacement("<range>", TileEntityDirectionalBreaker.RANGE), new PageCrafting(2, BlockCrafting.recipeDirectionalBreaker));

        //RF Generating Blocks
        new BookletChapter("solarPanel", entryGeneratingRF, new ItemStack(InitBlocks.blockFurnaceSolar), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityFurnaceSolar.PRODUCE), new PageCrafting(2, BlockCrafting.recipeSolar).setNoText());
        new BookletChapter("heatCollector", entryGeneratingRF, new ItemStack(InitBlocks.blockHeatCollector), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityHeatCollector.ENERGY_PRODUCE).addTextReplacement("<min>", TileEntityHeatCollector.BLOCKS_NEEDED), new PageCrafting(2, BlockCrafting.recipeHeatCollector).setNoText());
        new BookletChapter("canola", entryGeneratingRF, new ItemStack(InitBlocks.blockFermentingBarrel), new PageTextOnly(1).setStack(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())).addTextReplacement("<pressRF>", TileEntityCanolaPress.ENERGY_USE).addTextReplacement("<canola>", TileEntityCanolaPress.PRODUCE).addTextReplacement("<rf>", TileEntityOilGenerator.ENERGY_PRODUCED), new PageCrafting(2, BlockCrafting.recipeCanolaPress).setNoText(), new PageCrafting(3, BlockCrafting.recipeFermentingBarrel).setNoText(), new PageCrafting(4, BlockCrafting.recipeOilGen).setNoText());
        new BookletChapter("leafGen", entryGeneratingRF, new ItemStack(InitBlocks.blockLeafGenerator), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityLeafGenerator.ENERGY_PRODUCED).addTextReplacement("<range>", TileEntityLeafGenerator.RANGE), new PageCrafting(2, BlockCrafting.recipeLeafGen)).setImportant();

        //No RF Using Items
        new BookletChapter("wings", entryItemsNonRF, new ItemStack(InitItems.itemWingsOfTheBats), new PageTextOnly(1).setStack(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BAT_WING.ordinal())), new PageCrafting(2, ItemCrafting.recipeWings).setNoText()).setSpecial();
        new BookletChapter("foods", entryItemsNonRF, new ItemStack(InitItems.itemFoods, 1, TheFoods.HAMBURGER.ordinal()), new PageCrafting(1, FoodCrafting.recipePizza).setNoText(), new PageFurnace(2, new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE_BREAD.ordinal())).setNoText(), new PageCrafting(3, FoodCrafting.recipeHamburger).setNoText(), new PageCrafting(4, FoodCrafting.recipeBigCookie).setNoText(), new PageCrafting(5, FoodCrafting.recipeSubSandwich).setNoText(), new PageCrafting(6, FoodCrafting.recipeFrenchFry).setNoText(), new PageCrafting(7, FoodCrafting.recipeFrenchFries).setNoText(), new PageCrafting(8, FoodCrafting.recipeFishNChips).setNoText(), new PageCrafting(9, FoodCrafting.recipeCheese).setNoText(), new PageCrafting(10, FoodCrafting.recipePumpkinStew).setNoText(), new PageCrafting(11, FoodCrafting.recipeCarrotJuice).setNoText(), new PageCrafting(12, FoodCrafting.recipeSpaghetti).setNoText(), new PageCrafting(13, FoodCrafting.recipeNoodle).setNoText(), new PageCrafting(14, FoodCrafting.recipeChocolate).setNoText(), new PageCrafting(15, FoodCrafting.recipeChocolateCake).setNoText(), new PageCrafting(16, FoodCrafting.recipeToast).setNoText(), new PageFurnace(17, new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal())).setNoText(), new PageCrafting(18, FoodCrafting.recipeChocolateToast).setNoText());
        new BookletChapter("leafBlower", entryItemsNonRF, new ItemStack(InitItems.itemLeafBlowerAdvanced), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeLeafBlower).setNoText(), new PageCrafting(3, ItemCrafting.recipeLeafBlowerAdvanced).setNoText()).setImportant();
        ArrayList<BookletPage> aiotPages = new ArrayList<BookletPage>();
        aiotPages.add(new PageTextOnly(aiotPages.size()+1));
        for(IRecipe recipe : ToolCrafting.recipesPaxels){
            aiotPages.add(new PageCrafting(aiotPages.size()+1, recipe).setNoText().setPageStacksWildcard());
        }
        new BookletChapter("aiots", entryItemsNonRF, new ItemStack(InitItems.emeraldPaxel), aiotPages.toArray(new BookletPage[aiotPages.size()])).setImportant();

        new BookletChapter("jams", entryItemsNonRF, new ItemStack(InitItems.itemJams), new PageTextOnly(1).setStack(new ItemStack(InitItems.itemJams, 1, Util.WILDCARD)), new PageTextOnly(2));

        ArrayList<BookletPage> potionRingPages = new ArrayList<BookletPage>();
        potionRingPages.add(new PageTextOnly(potionRingPages.size()+1));
        for(IRecipe recipe : ItemCrafting.recipesPotionRings){
            potionRingPages.add(new PageCrafting(potionRingPages.size()+1, recipe).setNoText());
        }
        new BookletChapter("potionRings", entryItemsNonRF, new ItemStack(InitItems.itemPotionRing), potionRingPages.toArray(new BookletPage[potionRingPages.size()]));

        //RF Using Items
        new BookletChapter("drill", entryItemsRF, new ItemStack(InitItems.itemDrill), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeDrill, ItemCrafting.recipeDrillEmerald, ItemCrafting.recipeDrillPurple).setPageStacksWildcard(), new PageCrafting(3, ItemCrafting.recipeDrillCore).setNoText(), new PageCrafting(4, ItemCrafting.recipeDrillSpeedI).setNoText(), new PageCrafting(5, ItemCrafting.recipeDrillSpeedII).setNoText(), new PageCrafting(6, ItemCrafting.recipeDrillSpeedIII).setNoText(), new PageCrafting(7, ItemCrafting.recipeDrillFortuneI).setNoText(), new PageCrafting(8, ItemCrafting.recipeDrillFortuneII).setNoText(), new PageCrafting(9, ItemCrafting.recipeDrillSilk).setNoText(), new PageCrafting(10, ItemCrafting.recipeDrillThree).setNoText(), new PageCrafting(11, ItemCrafting.recipeDrillFive).setNoText(), new PageCrafting(12, ItemCrafting.recipeDrillPlacing).setNoText()).setSpecial();
        new BookletChapter("staff", entryItemsRF, new ItemStack(InitItems.itemTeleStaff), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeStaff).setNoText()).setImportant();
        new BookletChapter("magnetRing", entryItemsRF, new ItemStack(InitItems.itemMagnetRing), new PageCrafting(1, ItemCrafting.recipeMagnetRing));
        new BookletChapter("growthRing", entryItemsRF, new ItemStack(InitItems.itemGrowthRing), new PageCrafting(1, ItemCrafting.recipeGrowthRing));
        new BookletChapter("waterRemovalRing", entryItemsRF, new ItemStack(InitItems.itemWaterRemovalRing), new PageCrafting(1, ItemCrafting.recipeWaterRing));
        new BookletChapter("batteries", entryItemsRF, new ItemStack(InitItems.itemBatteryTriple), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeBattery).setNoText(), new PageCrafting(3, ItemCrafting.recipeBatteryDouble).setNoText(), new PageCrafting(4, ItemCrafting.recipeBatteryTriple).setNoText(), new PageCrafting(5, ItemCrafting.recipeBatteryQuadruple).setNoText(), new PageCrafting(6, ItemCrafting.recipeBatteryQuintuple).setNoText());
    }

    public static void init(){
        initChapters();
        countWords();
    }

    private static void countWords(){
        for(BookletEntry entry : entries){
            for(BookletChapter chapter : entry.chapters){
                for(BookletPage page : chapter.pages){
                    if(page.getText() != null){
                        wordCount += page.getText().split(" ").length;
                        charCount += page.getText().length();
                    }
                }
                wordCount += chapter.getLocalizedName().split(" ").length;
                charCount += chapter.getLocalizedName().length();
            }
            wordCount += entry.getLocalizedName().split(" ").length;
            charCount += entry.getLocalizedName().length();
        }
    }
}
