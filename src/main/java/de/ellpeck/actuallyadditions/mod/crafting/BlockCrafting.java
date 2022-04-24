/*
 * This file ("BlockCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

// TODO: [port] MOVE TO DATA_GENERATOR
@Deprecated
public final class BlockCrafting {
    //
    //    private static class FireworkIngredient extends Ingredient {
    //
    //        ItemStack firework = new ItemStack(Items.FIREWORKS);
    //        ItemStack[] fireworks = new ItemStack[] { this.firework };
    //
    //        @Override
    //        public ItemStack[] getMatchingStacks() {
    //            return this.fireworks;
    //        }
    //
    //        @Override
    //        public boolean apply(ItemStack stack) {
    //            return stack.getItem() == Items.FIREWORKS;
    //        }
    //    }
    //
    //    public static void init() {
    //
    //        Block[] removeNBTBlocks = new Block[] { InitBlocks.blockOilGenerator, InitBlocks.blockFluidPlacer, InitBlocks.blockFluidCollector, InitBlocks.blockCanolaPress, InitBlocks.blockFermentingBarrel };
    //
    //        for (Block b : removeNBTBlocks)
    //            RecipeHandler.addShapelessOreDictRecipe(new ItemStack(b), new ItemStack(b));
    //
    //        //Battery Box
    //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitBlocks.blockBatteryBox), new ItemStack(InitBlocks.blockEnergizer), new ItemStack(InitBlocks.blockEnervator), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()));
    //        recipeBatteryBox = RecipeUtil.lastIRecipe();
    //
    //        //Farmer
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockFarmer), "ISI", "SCS", "ISI", 'I', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal()), 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'S', new ItemStack(Items.WHEAT_SEEDS));
    //        recipeFarmer = RecipeUtil.lastIRecipe();
    //
    //        //Empowerer
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockEmpowerer), " R ", " B ", "CDC", 'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'B', new ItemStack(InitItems.itemBatteryDouble, 1, Util.WILDCARD), 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'D', new ItemStack(InitBlocks.blockDisplayStand));
    //        recipeEmpowerer = RecipeUtil.lastIRecipe();
    //
    //        //Tiny Torch
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockTinyTorch, 2), "C", "W", 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_COAL.ordinal()), 'W', "stickWood");
    //        recipesTinyTorch[0] = RecipeUtil.lastIRecipe();
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockTinyTorch, 2), "C", "W", 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_CHAR.ordinal()), 'W', "stickWood");
    //        recipesTinyTorch[1] = RecipeUtil.lastIRecipe();
    //
    //        //Firework Box
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockFireworkBox), "GFG", "SAS", "CCC", 'G', new ItemStack(Items.GUNPOWDER), 'S', new ItemStack(Items.STICK), 'A', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'F', new FireworkIngredient(), 'C', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()));
    //        recipeFireworkBox = RecipeUtil.lastIRecipe();
    //
    //        //Shock Suppressor
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockShockSuppressor), "OAO", "ACA", "OAO", 'A', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.COAL.ordinal()), 'O', new ItemStack(Blocks.OBSIDIAN), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
    //        recipeShockSuppressor = RecipeUtil.lastIRecipe();
    //
    //        //Display Stand
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockDisplayStand), " R ", "EEE", "GGG", 'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), 'E', new ItemStack(InitBlocks.blockTestifiBucksGreenWall), 'G', new ItemStack(InitBlocks.blockTestifiBucksWhiteWall));
    //        recipeDisplayStand = RecipeUtil.lastIRecipe();
    //
    //        //Miner
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockMiner), "IRI", "RCR", "IDI", 'R', "blockRedstone", 'I', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'C', new ItemStack(InitBlocks.blockCrystalEmpowered, 1, TheCrystals.COAL.ordinal()), 'D', new ItemStack(InitItems.itemDrill, 1, Util.WILDCARD));
    //        recipeMiner = RecipeUtil.lastIRecipe();
    //
    //        //Quartz
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockQuartzWall, 6), "XXX", "XXX", 'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()));
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockQuartzSlab, 6), "XXX", 'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()));
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockQuartzStair, 6), "X  ", "XX ", "XXX", 'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()));
    //
    //        //PillarQuartz
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockPillarQuartzWall, 6), "XXX", "XXX", 'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal()));
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockPillarQuartzSlab, 6), "XXX", 'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal()));
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockPillarQuartzStair, 6), "X  ", "XX ", "XXX", 'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal()));
    //
    //        //ChiseledQuartz
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockChiseledQuartzWall, 6), "XXX", "XXX", 'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_CHISELED.ordinal()));
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockChiseledQuartzSlab, 6), "XXX", 'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_CHISELED.ordinal()));
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockChiseledQuartzStair, 6), "X  ", "XX ", "XXX", 'X', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_CHISELED.ordinal()));
    //
    //        //White Ethetic Blocks
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockTestifiBucksWhiteFence, 6), "XXX", "XXX", 'X', new ItemStack(InitBlocks.blockTestifiBucksWhiteWall));
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockTestifiBucksWhiteSlab, 6), "XXX", 'X', new ItemStack(InitBlocks.blockTestifiBucksWhiteWall));
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockTestifiBucksWhiteStairs, 6), "X  ", "XX ", "XXX", 'X', new ItemStack(InitBlocks.blockTestifiBucksWhiteWall));
    //
    //        //Green Ethetic Blocks
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockTestifiBucksGreenFence, 6), "XXX", "XXX", 'X', new ItemStack(InitBlocks.blockTestifiBucksGreenWall));
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockTestifiBucksGreenSlab, 6), "XXX", 'X', new ItemStack(InitBlocks.blockTestifiBucksGreenWall));
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockTestifiBucksGreenStairs, 6), "X  ", "XX ", "XXX", 'X', new ItemStack(InitBlocks.blockTestifiBucksGreenWall));
    //
    //        //Atomic Reconstructor
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockAtomicReconstructor), "IRI", "RCR", "IRI", 'R', "dustRedstone", 'I', "ingotIron", 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()));
    //        recipeAtomicReconstructor = RecipeUtil.lastIRecipe();
    //
    //        //Laser Relay
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockLaserRelay, 4), "OBO", "RCR", "OBO", 'B', new ItemStack(Blocks.REDSTONE_BLOCK), 'O', new ItemStack(Blocks.OBSIDIAN), 'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
    //        recipeLaserRelay = RecipeUtil.lastIRecipe();
    //
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockLaserRelayAdvanced), " I ", "XRX", " I ", 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()), 'R', new ItemStack(InitBlocks.blockLaserRelay), 'X', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()));
    //        recipeLaserRelayAdvanced = RecipeUtil.lastIRecipe();
    //
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockLaserRelayExtreme), " I ", "XRX", " I ", 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()), 'R', new ItemStack(InitBlocks.blockLaserRelayAdvanced), 'X', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()));
    //        recipeLaserRelayExtreme = RecipeUtil.lastIRecipe();
    //
    //        //Whitelist Item Laser Relay
    //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitBlocks.blockLaserRelayItemWhitelist), new ItemStack(InitBlocks.blockLaserRelayItem), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), "gemQuartzBlack", new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()));
    //        recipeLaserRelayItemWhitelist = RecipeUtil.lastIRecipe();
    //
    //        //Item Interface
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockItemViewer), "OBO", "RCR", "OBO", 'B', new ItemStack(Items.REDSTONE), 'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), 'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'C', "chestWood");
    //        recipeItemInterface = RecipeUtil.lastIRecipe();
    //
    //        //Hopping Item Interface
    //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitBlocks.blockItemViewerHopping), new ItemStack(InitBlocks.blockItemViewer), new ItemStack(Blocks.HOPPER));
    //        recipeItemInterfaceHopping = RecipeUtil.lastIRecipe();
    //
    //        //Ranged Collector
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockRangedCollector), " A ", "EHE", " C ", 'E', new ItemStack(Items.ENDER_PEARL), 'H', new ItemStack(Blocks.HOPPER), 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'A', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()));
    //        recipeRangedCollector = RecipeUtil.lastIRecipe();
    //
    //        //Directional Breaker
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockDirectionalBreaker), "BBB", " C ", 'C', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), 'B', new ItemStack(InitBlocks.blockBreaker));
    //        recipeDirectionalBreaker = RecipeUtil.lastIRecipe();
    //
    //        //Smiley Cloud
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockSmileyCloud), " W ", "WXW", " W ", 'W', new ItemStack(Blocks.WOOL, 1, Util.WILDCARD), 'X', new ItemStack(InitItems.itemSolidifiedExperience));
    //        recipeSmileyCloud = RecipeUtil.lastIRecipe();
    //
    //        //Compost
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockCompost), "W W", "W W", "WCW", 'W', "plankWood", 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()));
    //        recipeCompost = RecipeUtil.lastIRecipe();
    //
    //        //XP Solidifier
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockXPSolidifier), "XXX", "DCD", "XXX", 'X', new ItemStack(InitItems.itemSolidifiedExperience), 'D', new ItemStack(InitBlocks.blockCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
    //        recipeSolidifier = RecipeUtil.lastIRecipe();
    //
    //
    //        //Phantom Booster
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockPhantomBooster), "RDR", "DCD", "RDR", 'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal()));
    //        recipePhantomBooster = RecipeUtil.lastIRecipe();
    //
    //        //Coffee Machine
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockCoffeeMachine), " C ", " S ", "AMA", 'M', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), 'C', "cropCoffee", 'S', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'A', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()));
    //        recipeCoffeeMachine = RecipeUtil.lastIRecipe();
    //
    //        //Energizer
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockEnergizer), "I I", "CAC", "I I", 'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), 'A', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()));
    //        recipeEnergizer = RecipeUtil.lastIRecipe();
    //
    //        //Enervator
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockEnervator), " I ", "CAC", " I ", 'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), 'A', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()));
    //        recipeEnervator = RecipeUtil.lastIRecipe();
    //
    //        //Lava Factory
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockLavaFactoryController), "SCS", "ISI", "LLL", 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'S', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), 'I', new ItemStack(InitBlocks.blockCrystalEmpowered, 1, TheCrystals.IRON.ordinal()), 'L', Items.LAVA_BUCKET);
    //        recipeLavaFactory = RecipeUtil.lastIRecipe();
    //
    //        //Casing
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockMisc, 32, TheMiscBlocks.LAVA_FACTORY_CASE.ordinal()), "ICI", 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'I', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal()));
    //        recipeCasing = RecipeUtil.lastIRecipe();
    //
    //        //Canola Press
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockCanolaPress), "CHC", "CDC", "CRC", 'C', "cobblestone", 'H', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), 'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal()));
    //        recipeCanolaPress = RecipeUtil.lastIRecipe();
    //
    //        //Fermenting Barrel
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockFermentingBarrel), "CHC", "CDC", "CRC", 'C', "logWood", 'H', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()), 'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal()));
    //        recipeFermentingBarrel = RecipeUtil.lastIRecipe();
    //
    //        //Phantomface
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockPhantomface), " C ", "EBE", " S ", 'E', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()), 'C', "chestWood", 'S', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), 'B', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal()));
    //        recipePhantomface = RecipeUtil.lastIRecipe();
    //
    //        //Player Interface
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockPlayerInterface), "BCB", "EBE", "BSB", 'E', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()), 'C', new ItemStack(Items.SKULL, 1, 1), 'S', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), 'B', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal()));
    //        recipePlayerInterface = RecipeUtil.lastIRecipe();
    //
    //        //Phantom Placer
    //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitBlocks.blockPhantomPlacer), InitBlocks.blockPlacer, InitBlocks.blockPhantomface);
    //        recipePhantomPlacer = RecipeUtil.lastIRecipe();
    //
    //        //Phantom Breaker
    //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitBlocks.blockPhantomBreaker), InitBlocks.blockBreaker, InitBlocks.blockPhantomface);
    //        recipePhantomBreaker = RecipeUtil.lastIRecipe();
    //
    //        //Phantom Energyface
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockPhantomEnergyface), " R ", "RFR", " R ", 'R', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.REDSTONE.ordinal()), 'F', InitBlocks.blockPhantomface);
    //        recipeEnergyface = RecipeUtil.lastIRecipe();
    //
    //        //Phantom Redstoneface
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockPhantomRedstoneface), "SRS", "RFR", "SRS", 'R', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.REDSTONE.ordinal()), 'S', new ItemStack(Items.REDSTONE), 'F', InitBlocks.blockPhantomface);
    //        recipePhantomRedstoneface = RecipeUtil.lastIRecipe();
    //
    //        //Phantom Liquiface
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockPhantomLiquiface), "RFR", 'R', Items.BUCKET, 'F', InitBlocks.blockPhantomface);
    //        recipeLiquiface = RecipeUtil.lastIRecipe();
    //
    //        //Liquid Placer
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockFluidPlacer), "RFR", 'R', Items.BUCKET, 'F', InitBlocks.blockPlacer);
    //        recipeLiquidPlacer = RecipeUtil.lastIRecipe();
    //
    //        //Liquid Breaker
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockFluidCollector), "RFR", 'R', Items.BUCKET, 'F', InitBlocks.blockBreaker);
    //        recipeLiquidCollector = RecipeUtil.lastIRecipe();
    //
    //        //Oil Generator
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockOilGenerator), "CRC", "CBC", "CRC", 'C', "cobblestone", 'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'B', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal()));
    //        recipeOilGen = RecipeUtil.lastIRecipe();
    //
    //        //Bio Reactor
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockBioReactor), "CRC", "CBC", "CRC", 'C', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()), 'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'B', new ItemStack(Blocks.SAPLING, 1, Util.WILDCARD));
    //        recipeBioReactor = RecipeUtil.lastIRecipe();
    //
    //        //Coal Generator
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockCoalGenerator), "CRC", "CBC", "CRC", 'C', "cobblestone", 'B', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'R', new ItemStack(Items.COAL, 1, Util.WILDCARD));
    //        recipeCoalGen = RecipeUtil.lastIRecipe();
    //
    //        //Leaf Generator
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockLeafGenerator), "IEI", "GLG", "ICI", 'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 'G', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.REDSTONE.ordinal()), 'E', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'L', "treeLeaves", 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
    //        recipeLeafGen = RecipeUtil.lastIRecipe();

    //        //Quartz Block
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()), "QQ", "QQ", 'Q', "gemQuartzBlack");
    //        recipeQuartzBlock = RecipeUtil.lastIRecipe();
    //
    //        //Fishing Net
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockFishingNet), "SSS", "SDS", "SSS", 'D', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.EMERALD.ordinal()), 'S', Items.STRING);
    //        recipeFisher = RecipeUtil.lastIRecipe();
    //
    //        //Repairer
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockItemRepairer), "DID", "OCO", "DID", 'D', new ItemStack(InitBlocks.blockCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()), 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()), 'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal()));
    //        recipeRepairer = RecipeUtil.lastIRecipe();
    //
    //        //Solar Panel
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockFurnaceSolar), "IQI", "CDC", "IBI", 'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()), 'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), 'B', new ItemStack(Blocks.IRON_BARS));
    //        recipeSolar = RecipeUtil.lastIRecipe();
    //
    //        //Heat Collector
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockHeatCollector), "BRB", "CDC", "BQB", 'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 'R', new ItemStack(Items.REPEATER), 'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), 'B', new ItemStack(Blocks.IRON_BARS));
    //        recipeHeatCollector = RecipeUtil.lastIRecipe();
    //
    //        //Quartz Pillar
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ_PILLAR.ordinal()), "Q", "Q", 'Q', "gemQuartzBlack");
    //        recipeQuartzPillar = RecipeUtil.lastIRecipe();
    //
    //        //Chiseled Quartz
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockMisc, 2, TheMiscBlocks.QUARTZ_CHISELED.ordinal()), "Q", "Q", 'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.QUARTZ.ordinal()));
    //        recipeQuartzChiseled = RecipeUtil.lastIRecipe();
    //
    //        //Inputter
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockInputter), "WWW", "CHC", "WWW", 'W', "plankWood", 'C', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()), 'H', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()));
    //        recipeESD = RecipeUtil.lastIRecipe();
    //
    //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitBlocks.blockInputterAdvanced), InitBlocks.blockInputter, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), "gemQuartzBlack", new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()));
    //        recipeAdvancedESD = RecipeUtil.lastIRecipe();
    //
    //        //Crusher
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockGrinder), "MFC", "DQD", "CFM", 'M', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'C', "cobblestone", 'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), 'Q', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'F', new ItemStack(Items.FLINT));
    //        recipeCrusher = RecipeUtil.lastIRecipe();
    //
    //        //Double Crusher
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockGrinderDouble), "CDC", "RFR", "CDC", 'C', "cobblestone", 'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()), 'R', InitBlocks.blockGrinder, 'F', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()));
    //        recipeDoubleCrusher = RecipeUtil.lastIRecipe();
    //
    //        //Double Furnace
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockFurnaceDouble), "PDC", "RFR", "CDP", 'C', "cobblestone", 'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), 'R', new ItemStack(Blocks.FURNACE), 'F', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.IRON_CASING.ordinal()), 'P', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()));
    //        recipeFurnace = RecipeUtil.lastIRecipe();
    //
    //        //Feeder
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockFeeder), "WCW", "DHD", "WCW", 'W', "plankWood", 'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), 'C', new ItemStack(Items.GOLDEN_CARROT), 'H', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()));
    //        recipeFeeder = RecipeUtil.lastIRecipe();
    //
    //        //Giant Chest
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockGiantChest), "CWC", "WDW", "CWC", 'C', "chestWood", 'D', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.WOOD_CASING.ordinal()), 'W', "plankWood");
    //        recipeCrate = RecipeUtil.lastIRecipe();
    //
    //        new RecipeKeepDataShaped(new ResourceLocation(ActuallyAdditions.MODID, "giant_chest_media"), new ItemStack(InitBlocks.blockGiantChestMedium), new ItemStack(InitBlocks.blockGiantChest), "CWC", "WDW", "CWC", 'C', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.COAL.ordinal()), 'D', new ItemStack(InitBlocks.blockGiantChest), 'W', "plankWood");
    //        recipeCrateMedium = RecipeUtil.lastIRecipe();
    //
    //        new RecipeKeepDataShaped(new ResourceLocation(ActuallyAdditions.MODID, "giant_chest_large"), new ItemStack(InitBlocks.blockGiantChestLarge), new ItemStack(InitBlocks.blockGiantChestMedium), "CWC", "WDW", "CWC", 'C', new ItemStack(InitBlocks.blockCrystalEmpowered, 1, TheCrystals.COAL.ordinal()), 'D', new ItemStack(InitBlocks.blockGiantChestMedium), 'W', "plankWood");
    //        recipeCrateLarge = RecipeUtil.lastIRecipe();
    //
    //        //Greenhouse Glass
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockGreenhouseGlass, 2), "GSG", "SDS", "GSG", 'G', "blockGlass", 'D', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.LAPIS.ordinal()), 'S', "treeSapling");
    //        recipeGlass = RecipeUtil.lastIRecipe();
    //
    //        //Placer
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockPlacer), "CCC", "CRP", "CCC", 'C', "cobblestone", 'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), 'P', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()));
    //        recipePlacer = RecipeUtil.lastIRecipe();
    //
    //        //Breaker
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockBreaker), "CCC", "CRP", "CCC", 'C', "cobblestone", 'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()), 'P', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()));
    //        recipeBreaker = RecipeUtil.lastIRecipe();
    //
    //        //Dropper
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockDropper), "CBC", "CDR", "CBC", 'B', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), 'C', "cobblestone", 'D', Blocks.DROPPER, 'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
    //        recipeDropper = RecipeUtil.lastIRecipe();
    //
    //        for (int i = 0; i < BlockColoredLamp.ALL_LAMP_TYPES.length; i++) {
    //            RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockColoredLamp, 6, i), "GCG", "DQD", "GCG", 'C', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), 'G', "glowstone", 'D', "dye" + BlockColoredLamp.ALL_LAMP_TYPES[i].oreName, 'Q', "gemQuartzBlack");
    //            RECIPES_LAMPS[i] = RecipeUtil.lastIRecipe();
    //        }
    //
    //        RecipeHandler.addOreDictRecipe(new ItemStack(InitBlocks.blockLampPowerer, 4), "XXX", "XLX", "XXX", 'X', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'L', new ItemStack(InitBlocks.blockColoredLamp, 1, Util.WILDCARD));
    //        recipePowerer = RecipeUtil.lastIRecipe();
    //
    //    }

}
