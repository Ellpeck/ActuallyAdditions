/*
 * This file ("InitBooklet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.chapter.BookletChapter;
import de.ellpeck.actuallyadditions.mod.booklet.chapter.BookletChapterCoffee;
import de.ellpeck.actuallyadditions.mod.booklet.chapter.BookletChapterCrusher;
import de.ellpeck.actuallyadditions.mod.booklet.chapter.BookletChapterTrials;
import de.ellpeck.actuallyadditions.mod.booklet.entry.BookletEntry;
import de.ellpeck.actuallyadditions.mod.booklet.entry.BookletEntryAllItems;
import de.ellpeck.actuallyadditions.mod.booklet.entry.BookletEntryTrials;
import de.ellpeck.actuallyadditions.mod.booklet.page.*;
import de.ellpeck.actuallyadditions.mod.crafting.*;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.gen.OreGen;
import de.ellpeck.actuallyadditions.mod.gen.WorldGenLushCaves;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemWingsOfTheBats;
import de.ellpeck.actuallyadditions.mod.items.lens.LensDisenchanting;
import de.ellpeck.actuallyadditions.mod.items.lens.LensMining;
import de.ellpeck.actuallyadditions.mod.items.lens.LensRecipeHandler;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.recipe.EmpowererHandler;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.mod.update.UpdateChecker;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;

import java.util.*;

public final class InitBooklet{

    public static BookletChapter[] chaptersIntroduction = new BookletChapter[11];

    public static void preInit(){
        ActuallyAdditionsAPI.entryAllAndSearch = new BookletEntryAllItems("allAndSearch").setImportant();
        ActuallyAdditionsAPI.entryTrials = new BookletEntryTrials("trials");

        ActuallyAdditionsAPI.entryGettingStarted = new BookletEntry("gettingStarted").setImportant();
        ActuallyAdditionsAPI.entryReconstruction = new BookletEntry("reconstruction");
        ActuallyAdditionsAPI.entryLaserRelays = new BookletEntry("laserRelays").setSpecial();
        ActuallyAdditionsAPI.entryFunctionalNonRF = new BookletEntry("functionalNoRF");
        ActuallyAdditionsAPI.entryFunctionalRF = new BookletEntry("functionalRF");
        ActuallyAdditionsAPI.entryGeneratingRF = new BookletEntry("generatingRF");
        ActuallyAdditionsAPI.entryItemsNonRF = new BookletEntry("itemsNoRF");
        ActuallyAdditionsAPI.entryItemsRF = new BookletEntry("itemsRF");
        ActuallyAdditionsAPI.entryMisc = new BookletEntry("misc");
        ActuallyAdditionsAPI.entryUpdatesAndInfos = new BookletEntry("updatesAndInfos").setSpecial();
    }

    public static void postInit(){
        initChapters();

        int chapCount = 0;
        int pageCount = 0;
        int infoCount = 0;
        for(IBookletEntry entry : ActuallyAdditionsAPI.BOOKLET_ENTRIES){
            for(IBookletChapter chapter : entry.getAllChapters()){
                if(!ActuallyAdditionsAPI.ALL_CHAPTERS.contains(chapter)){
                    ActuallyAdditionsAPI.ALL_CHAPTERS.add(chapter);
                    chapCount++;

                    for(IBookletPage page : chapter.getAllPages()){
                        pageCount++;

                        List<ItemStack> items = new ArrayList<ItemStack>();
                        page.getItemStacksForPage(items);
                        List<FluidStack> fluids = new ArrayList<FluidStack>();
                        page.getFluidStacksForPage(fluids);

                        if((items != null && !items.isEmpty()) || (fluids != null && !fluids.isEmpty())){
                            if(!ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA.contains(page)){
                                ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA.add(page);
                                infoCount++;
                            }
                        }
                    }
                }
            }
        }

        Collections.sort(ActuallyAdditionsAPI.BOOKLET_ENTRIES, new Comparator<IBookletEntry>(){
            @Override
            public int compare(IBookletEntry entry1, IBookletEntry entry2){
                Integer prio1 = entry1.getSortingPriority();
                Integer prio2 = entry2.getSortingPriority();
                return prio2.compareTo(prio1);
            }
        });
        Collections.sort(ActuallyAdditionsAPI.ALL_CHAPTERS, new Comparator<IBookletChapter>(){
            @Override
            public int compare(IBookletChapter chapter1, IBookletChapter chapter2){
                Integer prio1 = chapter1.getSortingPriority();
                Integer prio2 = chapter2.getSortingPriority();
                return prio2.compareTo(prio1);
            }
        });
        Collections.sort(ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA, new Comparator<IBookletPage>(){
            @Override
            public int compare(IBookletPage page1, IBookletPage page2){
                Integer prio1 = page1.getSortingPriority();
                Integer prio2 = page2.getSortingPriority();
                return prio2.compareTo(prio1);
            }
        });

        ModUtil.LOGGER.info("Registered a total of "+chapCount+" booklet chapters, where "+infoCount+" out of "+pageCount+" booklet pages contain information about items or fluids!");
    }

    private static void initChapters(){
        //Getting Started
        chaptersIntroduction[0] = new BookletChapter("bookTutorial", ActuallyAdditionsAPI.entryGettingStarted, new ItemStack(InitItems.itemBooklet), new PageTextOnly(1), new PageTextOnly(2), new PageTextOnly(3), new PageCrafting(4, ItemCrafting.recipeBook).setNoText());
        chaptersIntroduction[1] = new BookletChapter("videoGuide", ActuallyAdditionsAPI.entryGettingStarted, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.YOUTUBE_ICON.ordinal()), new PageLinkButton(1, "https://www.youtube.com/watch?v=fhjz0Ew56pM"), new PageLinkButton(2, "https://www.youtube.com/playlist?list=PLJeFZ64pT89MrTRZYzD_rtHFajPVlt6cF")).setImportant();
        new BookletChapter("intro", ActuallyAdditionsAPI.entryGettingStarted, new ItemStack(InitItems.itemBooklet), new PageTextOnly(1), new PageTextOnly(2), new PageTextOnly(3));
        ArrayList<BookletPage> crystalPages = new ArrayList<BookletPage>();
        crystalPages.addAll(Arrays.asList(new PageTextOnly(1).addTextReplacement("<rf>", TileEntityAtomicReconstructor.ENERGY_USE), new PageTextOnly(2), new PageTextOnly(3), new PagePicture(4, "page_atomic_reconstructor", 0).setNoText(), new PageTextOnly(5), new PageCrafting(6, BlockCrafting.recipeAtomicReconstructor).setWildcard()));
        for(int i = 0; i < LensRecipeHandler.MAIN_PAGE_RECIPES.size(); i++){
            crystalPages.add(new PageReconstructor(7+i, LensRecipeHandler.MAIN_PAGE_RECIPES.get(i)).setNoText());
        }
        crystalPages.add(new PageCrafting(crystalPages.size()+1, MiscCrafting.RECIPES_CRYSTALS).setNoText());
        crystalPages.add(new PageCrafting(crystalPages.size()+1, MiscCrafting.RECIPES_CRYSTAL_BLOCKS).setNoText());
        chaptersIntroduction[2] = new BookletChapter("engineerHouse", ActuallyAdditionsAPI.entryGettingStarted, new ItemStack(Items.EMERALD), new PageTextOnly(1), new PagePicture(2, "page_engineer_house", 145));
        chaptersIntroduction[6] = new BookletChapter("crystals", ActuallyAdditionsAPI.entryGettingStarted, new ItemStack(InitBlocks.blockAtomicReconstructor), crystalPages.toArray(new BookletPage[crystalPages.size()])).setSpecial();
        chaptersIntroduction[5] = new BookletChapter("coalGen", ActuallyAdditionsAPI.entryGettingStarted, new ItemStack(InitBlocks.blockCoalGenerator), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityCoalGenerator.PRODUCE), new PageCrafting(2, BlockCrafting.recipeCoalGen).setWildcard().setNoText());
        ArrayList<BookletPage> empowererPages = new ArrayList<BookletPage>();
        empowererPages.addAll(Arrays.asList(new PageTextOnly(1), new PagePicture(2, "page_empowerer", 137), new PageCrafting(3, BlockCrafting.recipeEmpowerer), new PageCrafting(4, BlockCrafting.recipeDisplayStand)));
        for(int i = 0; i < EmpowererHandler.MAIN_PAGE_RECIPES.size(); i++){
            empowererPages.add(new PageEmpowerer(7+i, EmpowererHandler.MAIN_PAGE_RECIPES.get(i)).setNoText());
        }
        empowererPages.add(new PageCrafting(empowererPages.size()+1, MiscCrafting.RECIPES_EMPOWERED_CRYSTALS).setNoText());
        empowererPages.add(new PageCrafting(empowererPages.size()+1, MiscCrafting.RECIPES_EMPOWERED_CRYSTAL_BLOCKS).setNoText());
        new BookletChapter("empowerer", ActuallyAdditionsAPI.entryGettingStarted, new ItemStack(InitBlocks.blockEmpowerer), empowererPages.toArray(new BookletPage[empowererPages.size()])).setSpecial();
        new BookletChapter("craftingIngs", ActuallyAdditionsAPI.entryGettingStarted, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeCoil).setNoText(), new PageCrafting(3, ItemCrafting.recipeCoilAdvanced).setNoText(), new PageCrafting(4, BlockCrafting.recipeCase).setNoText(), new PageCrafting(5, BlockCrafting.recipeEnderPearlBlock).setNoText(), new PageCrafting(6, BlockCrafting.recipeEnderCase).setNoText(), new PageCrafting(7, ItemCrafting.recipeRing).setNoText(), new PageCrafting(8, ItemCrafting.recipeKnifeHandle).setNoText(), new PageCrafting(9, ItemCrafting.recipeKnifeBlade).setNoText(), new PageCrafting(10, ItemCrafting.recipeKnife).setNoText(), new PageCrafting(11, ItemCrafting.recipeDough).setNoText(), new PageCrafting(12, ItemCrafting.recipeRiceDough).setNoText(), new PageCrafting(13, BlockCrafting.recipeIronCase).setNoText(), new PageCrafting(14, ItemCrafting.recipeLens).setNoText());
        chaptersIntroduction[4] = new BookletChapter("rf", ActuallyAdditionsAPI.entryGettingStarted, new ItemStack(Items.REDSTONE), new PageTextOnly(1), new PageTextOnly(2)).setImportant();

        //Miscellaneous
        new BookletChapter("worms", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitItems.itemWorm), new PageTextOnly(1).addItemsToPage(new ItemStack(InitItems.itemWorm)), new PagePicture(2, "page_worms", 145)).setImportant();
        new BookletChapter("lushCaves", ActuallyAdditionsAPI.entryMisc, new ItemStack(Blocks.STONE), new PageTextOnly(1), new PagePicture(2, "page_lush_caves", 0).setNoText());
        new BookletChapter("crystalClusters", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitBlocks.blockCrystalClusterEmerald), new PageTextOnly(1).addItemsToPage(WorldGenLushCaves.CRYSTAL_CLUSTERS), new PageCrafting(2, MiscCrafting.RECIPES_CRYSTAL_SHARDS).setNoText(), new PageCrafting(3, MiscCrafting.RECIPES_CRYSTAL_SHARDS_BACK).setNoText()).setSpecial();
        new BookletChapter("banners", ActuallyAdditionsAPI.entryMisc, new ItemStack(Items.BANNER, 1, 15), new PageTextOnly(1));
        new BookletChapter("miscDecorStuffsAndThings", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitBlocks.blockTestifiBucksGreenWall), new PageTextOnly(1), new PageReconstructor(2, LensRecipeHandler.recipeWhiteWall).setNoText(), new PageReconstructor(3, LensRecipeHandler.recipeGreenWall).setNoText());
        chaptersIntroduction[3] = new BookletChapter("quartz", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), new PageTextOnly(1).addItemsToPage(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal())).addTextReplacement("<lowest>", OreGen.QUARTZ_MIN).addTextReplacement("<highest>", OreGen.QUARTZ_MAX), new PageTextOnly(2).addItemsToPage(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())), new PageCrafting(3, BlockCrafting.recipeQuartzBlock).setNoText(), new PageCrafting(4, BlockCrafting.recipeQuartzPillar).setNoText(), new PageCrafting(5, BlockCrafting.recipeQuartzChiseled).setNoText());
        new BookletChapter("cloud", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitBlocks.blockSmileyCloud), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeSmileyCloud).setWildcard()).setSpecial();
        new BookletChapter("coalStuff", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_COAL.ordinal()), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeTinyCoal).setNoText(), new PageCrafting(3, ItemCrafting.recipeTinyChar).setNoText(), new PageCrafting(4, BlockCrafting.recipeBlockChar).setNoText());
        ArrayList<BookletPage> lampPages = new ArrayList<BookletPage>();
        lampPages.add(new PageTextOnly(lampPages.size()+1));
        lampPages.add(new PageTextOnly(lampPages.size()+1));
        lampPages.add(new PageCrafting(lampPages.size()+1, BlockCrafting.recipePowerer).setWildcard().setNoText());
        for(IRecipe recipe : BlockCrafting.RECIPES_LAMPS){
            lampPages.add(new PageCrafting(lampPages.size()+1, recipe).setNoText());
        }
        new BookletChapter("lamps", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitBlocks.blockColoredLampOn, 1, TheColoredLampColors.GREEN.ordinal()), lampPages.toArray(new BookletPage[lampPages.size()]));
        new BookletChapter("enderStar", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.ENDER_STAR.ordinal()), new PageCrafting(1, ItemCrafting.recipeEnderStar));
        new BookletChapter("spawnerShard", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.SPAWNER_SHARD.ordinal()), new PageTextOnly(1).addItemsToPage(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.SPAWNER_SHARD.ordinal())));
        new BookletChapter("treasureChest", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitBlocks.blockTreasureChest), new PagePicture(1, "page_treasure_chest", 150).addItemsToPage(new ItemStack(InitBlocks.blockTreasureChest)), new PageTextOnly(2)).setSpecial();
        new BookletChapter("hairBalls", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitItems.itemHairyBall), new PagePicture(1, "page_fur_balls", 110).addItemsToPage(new ItemStack(InitItems.itemHairyBall)), new PageTextOnly(2)).setSpecial();
        new BookletChapter("blackLotus", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitBlocks.blockBlackLotus), new PageTextOnly(1).addItemsToPage(new ItemStack(InitBlocks.blockBlackLotus)), new PageCrafting(2, ItemCrafting.recipeBlackDye));
        new BookletChapter("waterBowl", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitItems.itemWaterBowl), new PageTextOnly(1).addItemsToPage(new ItemStack(InitItems.itemWaterBowl)));
        new BookletChapter("tinyTorch", ActuallyAdditionsAPI.entryMisc, new ItemStack(InitBlocks.blockTinyTorch), new PageCrafting(1, BlockCrafting.recipesTinyTorch).setWildcard()).setSpecial();

        //Reconstruction
        chaptersIntroduction[7] = new BookletChapter("reconstructorLenses", ActuallyAdditionsAPI.entryReconstruction, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()), new PageTextOnly(1)).setImportant();
        new BookletChapter("additionalRecipes", ActuallyAdditionsAPI.entryReconstruction, new ItemStack(Items.LEATHER), new PageReconstructor(1, LensRecipeHandler.recipeSoulSand).setNoText(), new PageReconstructor(2, LensRecipeHandler.recipeLeather).setNoText(), new PageReconstructor(3, LensRecipeHandler.recipeNetherWart).setNoText(), new PageReconstructor(4, LensRecipeHandler.recipePrismarine).setNoText()).setSpecial();
        new BookletChapter("lensColor", ActuallyAdditionsAPI.entryReconstruction, new ItemStack(InitItems.itemColorLens), new PageTextOnly(1), new PageReconstructor(2, LensRecipeHandler.recipeColorLens).setNoText());
        new BookletChapter("lensDeath", ActuallyAdditionsAPI.entryReconstruction, new ItemStack(InitItems.itemDamageLens), new PageTextOnly(1), new PageReconstructor(2, LensRecipeHandler.recipeDamageLens).setNoText());
        new BookletChapter("lensMoreDeath", ActuallyAdditionsAPI.entryReconstruction, new ItemStack(InitItems.itemMoreDamageLens), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeLensMoreDeath).setNoText());
        new BookletChapter("lensDetonation", ActuallyAdditionsAPI.entryReconstruction, new ItemStack(InitItems.itemExplosionLens), new PageTextOnly(1), new PageReconstructor(2, LensRecipeHandler.recipeExplosionLens).setNoText());
        new BookletChapter("lensDisenchanting", ActuallyAdditionsAPI.entryReconstruction, new ItemStack(InitItems.itemDisenchantingLens), new PageTextOnly(1).addTextReplacement("<energy>", LensDisenchanting.ENERGY_USE), new PageCrafting(2, ItemCrafting.recipeDisenchantingLens).setNoText()).setSpecial();
        new BookletChapter("lensMining", ActuallyAdditionsAPI.entryReconstruction, new ItemStack(InitItems.itemMiningLens), new PageTextOnly(1).addTextReplacement("<energy>", LensMining.ENERGY_USE), new PageCrafting(2, ItemCrafting.recipeMiningLens).setNoText()).setImportant();

        //Laser Relays
        chaptersIntroduction[8] = new BookletChapter("laserIntro", ActuallyAdditionsAPI.entryLaserRelays, new ItemStack(InitItems.itemLaserWrench), new PageTextOnly(1), new PageTextOnly(2).addTextReplacement("<range>", TileEntityLaserRelay.MAX_DISTANCE), new PageCrafting(3, ItemCrafting.recipeLaserWrench)).setImportant();
        new BookletChapter("laserRelays", ActuallyAdditionsAPI.entryLaserRelays, new ItemStack(InitBlocks.blockLaserRelay), new PageTextOnly(1), new PageTextOnly(2).addTextReplacement("<cap1>", TileEntityLaserRelayEnergy.CAP).addTextReplacement("<cap2>", TileEntityLaserRelayEnergyAdvanced.CAP).addTextReplacement("<cap3>", TileEntityLaserRelayEnergyExtreme.CAP), new PagePicture(3, "page_laser_relay", 0).setNoText(), new PageCrafting(4, BlockCrafting.recipeLaserRelay).setWildcard().setNoText(), new PageCrafting(5, BlockCrafting.recipeLaserRelayAdvanced).setWildcard().setNoText(), new PageCrafting(6, BlockCrafting.recipeLaserRelayExtreme).setWildcard().setNoText());
        new BookletChapter("fluidLaser", ActuallyAdditionsAPI.entryLaserRelays, new ItemStack(InitBlocks.blockLaserRelayFluids), new PageTextOnly(1), new PageReconstructor(2, LensRecipeHandler.recipeFluidLaser).setWildcard().setNoText());
        new BookletChapter("itemRelays", ActuallyAdditionsAPI.entryLaserRelays, new ItemStack(InitBlocks.blockLaserRelayItem), new PageTextOnly(1), new PageReconstructor(2, LensRecipeHandler.recipeItemLaser).setWildcard().setNoText()).setSpecial();
        new BookletChapter("itemInterfaces", ActuallyAdditionsAPI.entryLaserRelays, new ItemStack(InitBlocks.blockItemViewer), new PageTextOnly(1), new PageTextOnly(2), new PageCrafting(3, BlockCrafting.recipeItemInterface).setNoText());
        new BookletChapter("itemRelaysAdvanced", ActuallyAdditionsAPI.entryLaserRelays, new ItemStack(InitBlocks.blockLaserRelayItemWhitelist), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeLaserRelayItemWhitelist).setWildcard());
        new BookletChapter("itemInterfacesHopping", ActuallyAdditionsAPI.entryLaserRelays, new ItemStack(InitBlocks.blockItemViewerHopping), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeItemInterfaceHopping).setWildcard().setNoText());
        new BookletChapter("laserUpgradeInvisibility", ActuallyAdditionsAPI.entryLaserRelays, new ItemStack(InitItems.itemLaserUpgradeInvisibility), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeLaserUpgradeInvisibility).setNoText()).setImportant();
        new BookletChapter("laserUpgradeRange", ActuallyAdditionsAPI.entryLaserRelays, new ItemStack(InitItems.itemLaserUpgradeRange), new PageTextOnly(1).addTextReplacement("<def>", TileEntityLaserRelay.MAX_DISTANCE).addTextReplacement("<upgraded>", TileEntityLaserRelay.MAX_DISTANCE_RANGED), new PageCrafting(2, ItemCrafting.recipeLaserUpgradeRange).setNoText()).setImportant();

        //No RF Using Blocks
        new BookletChapter("breaker", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockBreaker), new PageCrafting(1, BlockCrafting.recipeBreaker).setWildcard(), new PageCrafting(2, BlockCrafting.recipePlacer).setWildcard(), new PageCrafting(3, BlockCrafting.recipeLiquidPlacer).setWildcard(), new PageCrafting(4, BlockCrafting.recipeLiquidCollector).setWildcard());
        new BookletChapter("dropper", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockDropper), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeDropper).setNoText());
        new BookletChapter("phantomfaces", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockPhantomLiquiface), new PageTextOnly(1).addTextReplacement("<range>", TileEntityPhantomface.RANGE), new PageTextOnly(2), new PageCrafting(3, BlockCrafting.recipePhantomface), new PageCrafting(4, BlockCrafting.recipeLiquiface), new PageCrafting(5, BlockCrafting.recipeEnergyface), new PageCrafting(6, ItemCrafting.recipePhantomConnector).setNoText(), new PageCrafting(7, BlockCrafting.recipePhantomBooster)).setImportant();
        new BookletChapter("phantomRedstoneface", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockPhantomRedstoneface), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipePhantomRedstoneface).setNoText());
        new BookletChapter("phantomBreaker", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockPhantomBreaker), new PageTextOnly(1).addTextReplacement("<range>", TileEntityPhantomPlacer.RANGE), new PageCrafting(2, BlockCrafting.recipePhantomPlacer).setNoText(), new PageCrafting(3, BlockCrafting.recipePhantomBreaker).setNoText());
        new BookletChapter("esd", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockInputterAdvanced), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeESD).setNoText(), new PageCrafting(3, BlockCrafting.recipeAdvancedESD).setNoText()).setSpecial();
        new BookletChapter("xpSolidifier", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockXPSolidifier), new PageTextOnly(1).addItemsToPage(new ItemStack(InitItems.itemSolidifiedExperience)), new PageCrafting(2, BlockCrafting.recipeSolidifier).setNoText()).setImportant();
        new BookletChapter("greenhouseGlass", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockGreenhouseGlass), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeGlass));
        new BookletChapter("fishingNet", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockFishingNet), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeFisher).setNoText());
        new BookletChapter("feeder", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockFeeder), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeFeeder).setNoText());
        new BookletChapter("compost", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockCompost), new PageTextOnly(1).addItemsToPage(new ItemStack(InitItems.itemFertilizer)), new PageCrafting(2, BlockCrafting.recipeCompost).setNoText(), new PageTextOnly(3).addItemsToPage(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal())));
        new BookletChapter("crate", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockGiantChest), new PageCrafting(1, BlockCrafting.recipeCrate), new PageCrafting(2, BlockCrafting.recipeCrateMedium).setNoText(), new PageCrafting(3, BlockCrafting.recipeCrateLarge).setNoText(), new PageCrafting(4, ItemCrafting.recipeCrateKeeper), new PageCrafting(5, ItemCrafting.recipeChestToCrateUpgrade), new PageCrafting(6, ItemCrafting.recipeSmallToMediumCrateUpgrade), new PageCrafting(7, ItemCrafting.recipeMediumToLargeCrateUpgrade));
        new BookletChapter("rangedCollector", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitBlocks.blockRangedCollector), new PageTextOnly(1).addTextReplacement("<range>", TileEntityRangedCollector.RANGE), new PageCrafting(2, BlockCrafting.recipeRangedCollector).setNoText());

        //RF Using Blocks
        new BookletChapter("fireworkBox", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockFireworkBox), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityFireworkBox.USE_PER_SHOT), new PageCrafting(2, BlockCrafting.recipeFireworkBox)).setSpecial();
        new BookletChapter("batteryBox", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockBatteryBox), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeBatteryBox).setNoText()).setSpecial();
        new BookletChapter("farmer", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockFarmer), new PageTextOnly(1), new PagePicture(2, "page_farmer_crops", 95).addItemsToPage(new ItemStack(Items.WHEAT_SEEDS)).addItemsToPage(new ItemStack(InitItems.itemCanolaSeed)), new PagePicture(3, "page_farmer_cactus", 105).addItemsToPage(new ItemStack(Blocks.CACTUS)), new PagePicture(4, "page_farmer_wart", 95).addItemsToPage(new ItemStack(Items.NETHER_WART)), new PagePicture(5, "page_farmer_reeds", 105).addItemsToPage(new ItemStack(Items.REEDS)), new PagePicture(6, "page_farmer_melons", 105).addItemsToPage(new ItemStack(Items.MELON), new ItemStack(Blocks.PUMPKIN), new ItemStack(Blocks.MELON_BLOCK)), new PageCrafting(4, BlockCrafting.recipeFarmer).setWildcard().setNoText()).setImportant();
        new BookletChapter("miner", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockMiner), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityMiner.ENERGY_USE_PER_BLOCK).addTextReplacement("<range>", TileEntityMiner.DEFAULT_RANGE), new PageCrafting(2, BlockCrafting.recipeMiner)).setSpecial();
        new BookletChapterCoffee("coffeeMachine", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockCoffeeMachine), new PageTextOnly(1).addItemsToPage(new ItemStack(InitItems.itemCoffeeBean)).addTextReplacement("<rf>", TileEntityCoffeeMachine.ENERGY_USED).addTextReplacement("<coffee>", TileEntityCoffeeMachine.CACHE_USE).addTextReplacement("<water>", TileEntityCoffeeMachine.WATER_USE), new PageTextOnly(2).addItemsToPage(new ItemStack(InitItems.itemCoffee)), new PagePicture(3, "page_coffee_machine", 115), new PageCrafting(4, BlockCrafting.recipeCoffeeMachine).setWildcard().setNoText(), new PageCrafting(5, ItemCrafting.recipeCup).setNoText()).setImportant();
        new BookletChapterCrusher("crusher", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockGrinderDouble), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityGrinder.ENERGY_USE), new PageCrafting(2, BlockCrafting.recipeCrusher).setWildcard().setNoText(), new PageCrafting(3, BlockCrafting.recipeDoubleCrusher).setWildcard().setNoText(), new PageCrusherRecipe(4, CrusherCrafting.recipeIronHorseArmor).setNoText(), new PageCrusherRecipe(5, CrusherCrafting.recipeGoldHorseArmor).setNoText(), new PageCrusherRecipe(6, CrusherCrafting.recipeDiamondHorseArmor).setNoText());
        new BookletChapter("furnaceDouble", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockFurnaceDouble), new PageCrafting(1, BlockCrafting.recipeFurnace).setWildcard().addTextReplacement("<rf>", TileEntityFurnaceDouble.ENERGY_USE));
        new BookletChapter("lavaFactory", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockLavaFactoryController), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityLavaFactoryController.ENERGY_USE), new PagePicture(2, "page_lava_factory", 0).setNoText(), new PageCrafting(3, BlockCrafting.recipeLavaFactory).setNoText(), new PageCrafting(4, BlockCrafting.recipeCasing).setNoText());
        new BookletChapter("energizer", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockEnergizer), new PageCrafting(1, BlockCrafting.recipeEnergizer), new PageCrafting(2, BlockCrafting.recipeEnervator));
        new BookletChapter("repairer", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockItemRepairer), new PageCrafting(1, BlockCrafting.recipeRepairer).addTextReplacement("<rf>", TileEntityItemRepairer.ENERGY_USE));
        new BookletChapter("longRangeBreaker", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockDirectionalBreaker), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityDirectionalBreaker.ENERGY_USE).addTextReplacement("<range>", TileEntityDirectionalBreaker.RANGE), new PageCrafting(2, BlockCrafting.recipeDirectionalBreaker).setWildcard());
        new BookletChapter("playerInterface", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockPlayerInterface), new PageTextOnly(1).addTextReplacement("<range>", TileEntityPlayerInterface.DEFAULT_RANGE), new PageCrafting(2, BlockCrafting.recipePlayerInterface).setNoText()).setSpecial();
        new BookletChapter("displayStand", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockDisplayStand), new PageTextOnly(1), new PageTextOnly(2), new PageCrafting(3, BlockCrafting.recipeDisplayStand).setNoText()).setSpecial();
        new BookletChapter("shockSuppressor", ActuallyAdditionsAPI.entryFunctionalRF, new ItemStack(InitBlocks.blockShockSuppressor), new PageTextOnly(1).addTextReplacement("<range>", TileEntityShockSuppressor.RANGE).addTextReplacement("<rf>", TileEntityShockSuppressor.USE_PER), new PageCrafting(2, BlockCrafting.recipeShockSuppressor));

        //RF Generating Blocks
        new BookletChapter("solarPanel", ActuallyAdditionsAPI.entryGeneratingRF, new ItemStack(InitBlocks.blockFurnaceSolar), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityFurnaceSolar.PRODUCE), new PageCrafting(2, BlockCrafting.recipeSolar).setNoText());
        new BookletChapter("heatCollector", ActuallyAdditionsAPI.entryGeneratingRF, new ItemStack(InitBlocks.blockHeatCollector), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityHeatCollector.ENERGY_PRODUCE).addTextReplacement("<min>", TileEntityHeatCollector.BLOCKS_NEEDED), new PageCrafting(2, BlockCrafting.recipeHeatCollector).setNoText());
        new BookletChapter("canola", ActuallyAdditionsAPI.entryGeneratingRF, new ItemStack(InitBlocks.blockFermentingBarrel), new PageTextOnly(1).addItemsToPage(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal())).addItemsToPage(new ItemStack(InitItems.itemCanolaSeed)).addFluidToPage(InitFluids.fluidCanolaOil), new PageTextOnly(2).addFluidToPage(InitFluids.fluidOil).addFluidToPage(InitFluids.fluidCrystalOil).addFluidToPage(InitFluids.fluidEmpoweredOil), new PageCrafting(3, BlockCrafting.recipeCanolaPress).setNoText(), new PageCrafting(4, BlockCrafting.recipeFermentingBarrel), new PageCrafting(5, BlockCrafting.recipeOilGen), new PageReconstructor(6, LensRecipeHandler.recipeCrystallizedCanolaSeed).setNoText(), new PageEmpowerer(7, EmpowererHandler.recipeEmpoweredCanolaSeed).setNoText());
        new BookletChapter("leafGen", ActuallyAdditionsAPI.entryGeneratingRF, new ItemStack(InitBlocks.blockLeafGenerator), new PageTextOnly(1).addTextReplacement("<rf>", TileEntityLeafGenerator.ENERGY_PRODUCED).addTextReplacement("<range>", TileEntityLeafGenerator.RANGE), new PageCrafting(2, BlockCrafting.recipeLeafGen)).setImportant();
        new BookletChapter("bioReactor", ActuallyAdditionsAPI.entryGeneratingRF, new ItemStack(InitBlocks.blockBioReactor), new PageTextOnly(1), new PageCrafting(2, BlockCrafting.recipeBioReactor).setNoText()).setSpecial();

        //No RF Using Items
        chaptersIntroduction[9] = new BookletChapter("goggles", ActuallyAdditionsAPI.entryItemsNonRF, new ItemStack(InitItems.itemEngineerGoggles), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeGoggles).setNoText(), new PageCrafting(3, ItemCrafting.recipeGogglesAdvanced).setNoText()).setImportant();
        new BookletChapter("bags", ActuallyAdditionsAPI.entryItemsNonRF, new ItemStack(InitItems.itemBag), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeBag), new PageCrafting(3, ItemCrafting.recipeVoidBag).setNoText()).setImportant();
        new BookletChapter("wings", ActuallyAdditionsAPI.entryItemsNonRF, new ItemStack(InitItems.itemWingsOfTheBats), new PageTextOnly(1).addItemsToPage(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BAT_WING.ordinal())).addTextReplacement("<secs>", ItemWingsOfTheBats.MAX_FLY_TIME/20), new PageCrafting(2, ItemCrafting.recipeWings).setNoText()).setSpecial();
        new BookletChapter("foods", ActuallyAdditionsAPI.entryItemsNonRF, new ItemStack(InitItems.itemFoods, 1, TheFoods.HAMBURGER.ordinal()), new PageCrafting(1, FoodCrafting.recipeBacon).setNoText(), new PageFurnace(2, new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE_BREAD.ordinal())).setNoText(), new PageCrafting(3, FoodCrafting.recipeHamburger).setNoText(), new PageCrafting(4, FoodCrafting.recipeBigCookie).setNoText(), new PageCrafting(5, FoodCrafting.recipeSubSandwich).setNoText(), new PageCrafting(6, FoodCrafting.recipeFrenchFry).setNoText(), new PageCrafting(7, FoodCrafting.recipeFrenchFries).setNoText(), new PageCrafting(8, FoodCrafting.recipeFishNChips).setNoText(), new PageCrafting(9, FoodCrafting.recipeCheese).setNoText(), new PageCrafting(10, FoodCrafting.recipePumpkinStew).setNoText(), new PageCrafting(11, FoodCrafting.recipeCarrotJuice).setNoText(), new PageCrafting(12, FoodCrafting.recipeSpaghetti).setNoText(), new PageCrafting(13, FoodCrafting.recipeNoodle).setNoText(), new PageCrafting(14, FoodCrafting.recipeChocolate).setNoText(), new PageCrafting(15, FoodCrafting.recipeChocolateCake).setNoText(), new PageCrafting(16, FoodCrafting.recipeToast).setNoText(), new PageFurnace(17, new ItemStack(InitItems.itemFoods, 1, TheFoods.BAGUETTE.ordinal())).setNoText(), new PageCrafting(18, FoodCrafting.recipeChocolateToast).setNoText(), new PageCrafting(1, FoodCrafting.recipePizza).setNoText());
        new BookletChapter("leafBlower", ActuallyAdditionsAPI.entryItemsNonRF, new ItemStack(InitItems.itemLeafBlowerAdvanced), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeLeafBlower).setNoText(), new PageCrafting(3, ItemCrafting.recipeLeafBlowerAdvanced).setNoText()).setImportant();
        new BookletChapter("playerProbe", ActuallyAdditionsAPI.entryItemsNonRF, new ItemStack(InitItems.itemPlayerProbe), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipePlayerProbe).setNoText()).setSpecial();
        ArrayList<BookletPage> aiotPages = new ArrayList<BookletPage>();
        aiotPages.add(new PageTextOnly(aiotPages.size()+1));
        for(IRecipe recipe : ToolCrafting.RECIPES_PAXELS){
            aiotPages.add(new PageCrafting(aiotPages.size()+1, recipe).setWildcard().setNoText());
        }
        new BookletChapter("aiots", ActuallyAdditionsAPI.entryItemsNonRF, new ItemStack(InitItems.emeraldPaxel), aiotPages.toArray(new BookletPage[aiotPages.size()])).setImportant();

        new BookletChapter("jams", ActuallyAdditionsAPI.entryItemsNonRF, new ItemStack(InitItems.itemJams), new PageTextOnly(1).addItemsToPage(new ItemStack(InitItems.itemJams, 1, Util.WILDCARD)), new PagePicture(2, "page_jam_house", 150), new PageTextOnly(3));

        ArrayList<BookletPage> potionRingPages = new ArrayList<BookletPage>();
        potionRingPages.add(new PageTextOnly(potionRingPages.size()+1));
        for(IRecipe recipe : ItemCrafting.RECIPES_POTION_RINGS){
            potionRingPages.add(new PageCrafting(potionRingPages.size()+1, recipe).setNoText());
        }
        new BookletChapter("potionRings", ActuallyAdditionsAPI.entryItemsNonRF, new ItemStack(InitItems.itemPotionRing), potionRingPages.toArray(new BookletPage[potionRingPages.size()]));
        new BookletChapter("spawnerChanger", ActuallyAdditionsAPI.entryItemsNonRF, new ItemStack(InitItems.itemSpawnerChanger), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeSpawnerChanger).setNoText());
        new BookletChapter("itemFilter", ActuallyAdditionsAPI.entryFunctionalNonRF, new ItemStack(InitItems.itemFilter), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeFilter).setNoText()).setImportant();

        //RF Using Items
        new BookletChapter("drill", ActuallyAdditionsAPI.entryItemsRF, new ItemStack(InitItems.itemDrill, 1, TheColoredLampColors.LIGHT_BLUE.ordinal()), new PageTextOnly(1), new PageTextOnly(2), new PageCrafting(3, ItemCrafting.recipeDrill).setNoText(), new PageCrafting(4, ItemCrafting.RECIPES_DRILL_COLORING), new PageCrafting(4, ItemCrafting.recipeDrillCore).setNoText(), new PageCrafting(5, ItemCrafting.recipeDrillSpeedI).setNoText(), new PageCrafting(6, ItemCrafting.recipeDrillSpeedII).setNoText(), new PageCrafting(7, ItemCrafting.recipeDrillSpeedIII).setNoText(), new PageCrafting(8, ItemCrafting.recipeDrillFortuneI).setNoText(), new PageCrafting(9, ItemCrafting.recipeDrillFortuneII).setNoText(), new PageCrafting(10, ItemCrafting.recipeDrillSilk).setNoText(), new PageCrafting(11, ItemCrafting.recipeDrillThree).setNoText(), new PageCrafting(12, ItemCrafting.recipeDrillFive).setNoText(), new PageCrafting(13, ItemCrafting.recipeDrillPlacing).setNoText()).setSpecial();
        new BookletChapter("fillingWand", ActuallyAdditionsAPI.entryItemsRF, new ItemStack(InitItems.itemFillingWand), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeFillingWand).setNoText()).setSpecial();
        new BookletChapter("staff", ActuallyAdditionsAPI.entryItemsRF, new ItemStack(InitItems.itemTeleStaff), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeStaff).setNoText()).setImportant();
        new BookletChapter("magnetRing", ActuallyAdditionsAPI.entryItemsRF, new ItemStack(InitItems.itemMagnetRing), new PageCrafting(1, ItemCrafting.recipeMagnetRing));
        new BookletChapter("growthRing", ActuallyAdditionsAPI.entryItemsRF, new ItemStack(InitItems.itemGrowthRing), new PageCrafting(1, ItemCrafting.recipeGrowthRing));
        new BookletChapter("waterRemovalRing", ActuallyAdditionsAPI.entryItemsRF, new ItemStack(InitItems.itemWaterRemovalRing), new PageCrafting(1, ItemCrafting.recipeWaterRing));
        new BookletChapter("batteries", ActuallyAdditionsAPI.entryItemsRF, new ItemStack(InitItems.itemBatteryTriple), new PageTextOnly(1), new PageCrafting(2, ItemCrafting.recipeBattery).setNoText(), new PageCrafting(3, ItemCrafting.recipeBatteryDouble).setNoText(), new PageCrafting(4, ItemCrafting.recipeBatteryTriple).setNoText(), new PageCrafting(5, ItemCrafting.recipeBatteryQuadruple).setNoText(), new PageCrafting(6, ItemCrafting.recipeBatteryQuintuple).setNoText());

        //Updates and infos
        new BookletChapter("changelog", ActuallyAdditionsAPI.entryUpdatesAndInfos, new ItemStack(Items.CLOCK), new PageLinkButton(1, UpdateChecker.CHANGELOG_LINK));
        new BookletChapter("curse", ActuallyAdditionsAPI.entryUpdatesAndInfos, new ItemStack(Items.FLINT_AND_STEEL), new PageLinkButton(1, "http://ellpeck.de/actadd"));
        new BookletChapter("patreon", ActuallyAdditionsAPI.entryUpdatesAndInfos, new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), new PageLinkButton(1, "http://patreon.com/Ellpeck"), new PagePicture(2, "page_patreon", 153)).setImportant();
        new BookletChapter("website", ActuallyAdditionsAPI.entryUpdatesAndInfos, new ItemStack(InitItems.itemBooklet), new PageLinkButton(1, "http://ellpeck.de"));

        //Trials
        chaptersIntroduction[10] = new BookletChapter("trialsIntro", ActuallyAdditionsAPI.entryTrials, new ItemStack(Items.GOLD_INGOT), new PageTextOnly(1), new PageTextOnly(2)).setSpecial();
        new BookletChapterTrials("crystalProduction", new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), false);
        new BookletChapterTrials("leatherProduction", new ItemStack(Items.LEATHER), false);
        new BookletChapterTrials("crystalOil", UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, InitFluids.fluidCrystalOil), false);
        new BookletChapterTrials("autoDisenchanter", new ItemStack(InitItems.itemDisenchantingLens), false);
        new BookletChapterTrials("empoweredOil", UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, InitFluids.fluidEmpoweredOil), false);
        new BookletChapterTrials("mobFarm", new ItemStack(Items.ROTTEN_FLESH), false);
        new BookletChapterTrials("empowererAutomation", new ItemStack(InitBlocks.blockEmpowerer), false);
    }
}
