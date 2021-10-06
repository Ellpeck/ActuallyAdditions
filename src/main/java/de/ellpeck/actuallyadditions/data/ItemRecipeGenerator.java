package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import net.minecraft.data.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.NBTIngredient;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;

public class ItemRecipeGenerator extends RecipeProvider {
    public ItemRecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        //Goggles
        Recipe.shaped(ActuallyItems.ENGINEER_GOGGLES.get())
            .pattern(" R ")
            .pattern("IGI")
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', Items.IRON_BARS)
            .define('G', Tags.Items.GLASS).save(consumer);

        //Advanced Goggles
        Recipe.shaped(ActuallyItems.ENGINEER_GOGGLES_ADVANCED.get())
            .pattern(" R ")
            .pattern("IGI")
            .define('R', ActuallyItems.RESTONIA_EMPOWERED_CRYSTAL.get())
            .define('I', Items.IRON_BARS)
            .define('G', ActuallyItems.ENGINEER_GOGGLES.get()).save(consumer);

        //Laser Upgrades
        //Invisibility
        Recipe.shaped(ActuallyItems.LASER_UPGRADE_INVISIBILITY.get(), 4)
            .pattern("GGG")
            .pattern("RCR")
            .pattern("GGG")
            .define('G', Tags.Items.GLASS_BLACK)
            .define('R', ActuallyItems.VOID_CRYSTAL.get())
            .define('C', ActuallyItems.COIL_ADVANCED.get()).save(consumer);

        //Range
        Recipe.shaped(ActuallyItems.LASER_UPGRADE_RANGE.get(), 2)
            .pattern("GGC")
            .pattern("RCR")
            .pattern("CGG")
            .define('R', Items.COMPASS)
            .define('G', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('C', ActuallyItems.COIL_ADVANCED.get()).save(consumer);

        //Filling Wand
        Recipe.shaped(ActuallyItems.FILLING_WAND.get())
            .pattern("IPI")
            .pattern("DCD")
            .pattern(" B ")
            .define('I', ActuallyItems.ENORI_EMPOWERED_CRYSTAL.get())
            .define('P', ActuallyItems.PALIS_CRYSTAL.get())
            .define('C', ActuallyItems.COIL_ADVANCED.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('B', ActuallyItems.BATTERY_TRIPLE.get()).save(consumer);

        //Bag
        Recipe.shaped(ActuallyItems.BAG.get())
            .pattern("SLS")
            .pattern("SCS")
            .pattern("LVL")
            .define('S', Tags.Items.STRING)
            .define('L', Tags.Items.LEATHER)
            .define('C', Tags.Items.CHESTS_WOODEN)
            .define('B', ActuallyBlocks.VOID_CRYSTAL.getItem()).save(consumer);

        //Void Bag
        Recipe.shapeless(ActuallyItems.VOID_BAG.get())
            .requires(ActuallyItems.BAG.get())
            .requires(Tags.Items.ENDER_PEARLS)
            .requires(Tags.Items.OBSIDIAN)
            .requires(ActuallyBlocks.VOID_CRYSTAL.getItem())
            .save(consumer);

        //Lens
        Recipe.shaped(ActuallyItems.LENS.get())
            .pattern("GGG")
            .pattern("GBG")
            .pattern("GGG")
            .define('G', Tags.Items.GLASS)
            .define('B', ActuallyItems.BLACK_QUARTZ.get()).save(consumer);

        //Booklet
        Recipe.shapeless(ActuallyItems.ITEM_BOOKLET.get())
            .ingredients(ActuallyItems.CANOLA_SEED.get(), Items.PAPER).save(consumer);


        //Clearing NBT Storage
        Recipe.shapeless(ActuallyItems.LASER_WRENCH.get()).ingredients(ActuallyItems.LASER_WRENCH.get()).save(consumer);
        Recipe.shapeless(ActuallyItems.PHANTOM_CONNECTOR.get()).ingredients(ActuallyItems.PHANTOM_CONNECTOR.get()).save(consumer);

        //Disenchanting Lens
        Recipe.shapeless(ActuallyItems.DISENCHANTING_LENS.get())
            .requires(ActuallyItems.LENS.get())
            .requires(Items.ENCHANTING_TABLE)
            .requires(ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL.get(), 7).save(consumer);

        //Mining Lens
        Recipe.shaped(ActuallyItems.MINING_LENS.get())
            .pattern("DGI")
            .pattern("CLB")
            .pattern("QPE")
            .define('D', Tags.Items.GEMS_DIAMOND)
            .define('G', Tags.Items.INGOTS_GOLD)
            .define('I', Tags.Items.INGOTS_IRON)
            .define('C', ItemTags.COALS)
            .define('L', ActuallyItems.LENS.get())
            .define('B', ActuallyItems.BLACK_QUARTZ.get())
            .define('Q', Tags.Items.GEMS_QUARTZ)
            .define('P', Tags.Items.GEMS_LAPIS)
            .define('E', Tags.Items.GEMS_EMERALD).save(consumer);

        //Killer Lens
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        enchantedBook.enchant(Enchantments.SHARPNESS, 5);
        Recipe.shapeless(ActuallyItems.MORE_DAMAGE_LENS.get())
            .requires(Items.DIAMOND_SWORD)
            .requires(ActuallyItems.DAMAGE_LENS.get())
            .requires(NBTIngredient.of(enchantedBook)).save(consumer);


        //Filter
        Recipe.shaped(ActuallyItems.FILTER.get())
            .pattern("III")
            .pattern("IQI")
            .pattern("III")
            .define('I', Items.IRON_BARS)
            .define('Q', ActuallyItems.BLACK_QUARTZ.get()).save(consumer);

        //Crate Keeper
        Recipe.shaped(ActuallyItems.CRATE_KEEPER.get())
            .pattern("WIW")
            .pattern("IQI")
            .pattern("WIW")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', ItemTags.PLANKS)
            .define('Q', ActuallyItems.BLACK_QUARTZ.get()).save(consumer);

        //Laser Wrench
        Recipe.shaped(ActuallyItems.LASER_WRENCH.get())
            .pattern("C  ")
            .pattern(" S ")
            .pattern("  S")
            .define('C', ActuallyItems.COIL_ADVANCED.get())
            .define('S', ActuallyItems.ENORI_CRYSTAL.get()).save(consumer);


        //Rice Recipes
        Recipe.shaped(Items.PAPER, 3)
            .pattern("R  ")
            .pattern(" R ")
            .pattern("  R")
            .define('R', TheFoods.RICE).save(consumer); //TODO foods need worked on still.

        Recipe.shaped(ActuallyItems.RICE_SLIME.get())
            .pattern(" R ")
            .pattern("RBR")
            .pattern(" R ")
            .define('R', ActuallyItems.RICE_DOUGH.get())
            .define('B', Items.WATER_BUCKET).save(consumer);

        Recipe.shaped(ActuallyItems.RICE_SLIME.get())
            .pattern(" R ")
            .pattern("RBR")
            .pattern(" R ")
            .define('R', ActuallyItems.RICE_DOUGH.get())
            .define('B', Items.POTION).save(consumer);

        //Leaf Blower
        Recipe.shaped(ActuallyItems.LEAF_BLOWER.get())
            .pattern(" F")
            .pattern("IP")
            .pattern("IC")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('P', Items.PISTON)
            .define('F', Items.FLINT)
            .define('C', ActuallyItems.COIL_ADVANCED.get()).save(consumer);

        //Drill //TODO is this still being colorable?
        Recipe.shaped(ActuallyItems.DRILL.get())
            .pattern("DDD")
            .pattern("CRC")
            .pattern("III")
            .define('D', Tags.Items.GEMS_DIAMOND)
            .define('C', ActuallyItems.COIL_ADVANCED.get())
            .define('R', ActuallyItems.DRILL_CORE.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get()).save(consumer);

        //Drill Core
        Recipe.shaped(ActuallyItems.DRILL_CORE.get())
            .pattern("ICI")
            .pattern("CRC")
            .pattern("ICI")
            .define('C', ActuallyItems.COIL.get())
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get()).save(consumer);

        //Tele Staff
        Recipe.shaped(ActuallyItems.TELE_STAFF.get())
            .pattern(" FE")
            .pattern(" S ")
            .pattern("SB ")
            .define('F', ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL.get())
            .define('E', ActuallyBlocks.ENDER_PEARL_BLOCK.getItem())
            .define('S', ActuallyBlocks.ENDER_CASING.getItem())
            .define('B', ActuallyItems.BATTERY.get()).save(consumer);

        //Drill Speed upgrade
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get()).save(consumer);

        //Drill Speed upgrade II
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED_II.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', Items.CAKE).save(consumer);

        //Drill Speed upgrade III
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED_III.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.ENORI_EMPOWERED_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL.get()).save(consumer);

        //Drill Fortune upgrade
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FORTUNE.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', Items.GLOWSTONE)
            .define('S', Tags.Items.DUSTS_REDSTONE)
            .define('R', ActuallyBlocks.EMPOWERED_DIAMATINE_CRYSTAL.getItem()).save(consumer);

        //Drill Fortune upgrade II
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FORTUNE_II.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', Items.GLOWSTONE)
            .define('S', ActuallyItems.RESTONIA_EMPOWERED_CRYSTAL.get())
            .define('R', ActuallyBlocks.ENDER_CASING.getItem()).save(consumer);

        //3x3
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_THREE_BY_THREE.get())
            .pattern("DID")
            .pattern("ICI")
            .pattern("DID")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.COIL.get()).save(consumer);

        //5x5
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FIVE_BY_FIVE.get())
            .pattern("DID")
            .pattern("ICI")
            .pattern("DID")
            .define('I', ActuallyItems.ENORI_EMPOWERED_CRYSTAL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.COIL_ADVANCED.get()).save(consumer);

        //Silk Touch
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SILK_TOUCH.get())
            .pattern("DSD")
            .pattern("SCS")
            .pattern("DSD")
            .define('D', ActuallyItems.EMERADIC_CRYSTAL.get())
            .define('S', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.COIL_ADVANCED.get()).save(consumer);

        //Placing
        Recipe.Shaped.shaped(ActuallyItems.DRILL_UPGRADE_BLOCK_PLACING.get())
            .pattern("CEC")
            .pattern("RAR")
            .pattern("CEC")
            .define('C', Tags.Items.COBBLESTONE)
            .define('E', Items.PAPER)
            .define('A', ActuallyItems.COIL.get())
            .define('R', ActuallyItems.ENORI_CRYSTAL.get()).save(consumer);

        //Bat Wings
        Recipe.shaped(ActuallyItems.WINGS_OF_THE_BATS.get())
            .pattern("WNW")
            .pattern("WDW")
            .pattern("WNW")
            .define('W', ActuallyItems.BAT_WING.get())
            .define('N', ActuallyBlocks.DIAMATINE_CRYSTAL.getItem())
            .define('D', ActuallyItems.ENDER_STAR.get()).save(consumer);

        //Coil
        Recipe.shaped(ActuallyItems.COIL.get())
            .pattern(" R ")
            .pattern("RIR")
            .pattern(" R ")
            .define('I', ActuallyItems.BLACK_QUARTZ.get())
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get()).save(consumer);

        //Advanced Coil
        Recipe.shaped(ActuallyItems.COIL_ADVANCED.get())
            .pattern("GGG")
            .pattern("GCG")
            .pattern("GGG")
            .define('C', ActuallyItems.COIL.get())
            .define('G', Items.GOLD_NUGGET).save(consumer);




        //        //Battery
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemBattery), " R ", "ICI", "III", 'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
        //        recipeBattery = RecipeUtil.lastIRecipe();
        //
        //        //Double Battery
        //        new RecipeKeepDataShaped(new ResourceLocation(ActuallyAdditions.MODID, "double_battery"), new ItemStack(InitItems.itemBatteryDouble), new ItemStack(InitItems.itemBattery), " R ", "ICI", "III", 'R', new ItemStack(InitItems.itemBattery), 'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
        //        recipeBatteryDouble = RecipeUtil.lastIRecipe();
        //
        //        //Magnet Ring
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemMagnetRing), "RIB", "IOI", "BIR", 'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 'B', new ItemStack(Items.DYE, 1, 4), 'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()));
        //        recipeMagnetRing = RecipeUtil.lastIRecipe();
        //
        //        //Growth Ring
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemGrowthRing), "SIS", "IOI", "SIS", 'S', new ItemStack(Items.WHEAT_SEEDS), 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()), 'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()));
        //        recipeGrowthRing = RecipeUtil.lastIRecipe();
        //
        //        //Water Ring
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemWaterRemovalRing), "BIB", "IOI", "BIB", 'B', new ItemStack(Items.WATER_BUCKET), 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()), 'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()));
        //        recipeWaterRing = RecipeUtil.lastIRecipe();
        //
        //        //Triple Battery
        //        new RecipeKeepDataShaped(new ResourceLocation(ActuallyAdditions.MODID, "triple_battery"), new ItemStack(InitItems.itemBatteryTriple), new ItemStack(InitItems.itemBatteryDouble), " R ", "ICI", "III", 'R', new ItemStack(InitItems.itemBatteryDouble), 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
        //        recipeBatteryTriple = RecipeUtil.lastIRecipe();
        //
        //        //Quadruple Battery
        //        new RecipeKeepDataShaped(new ResourceLocation(ActuallyAdditions.MODID, "quadruple_battery"), new ItemStack(InitItems.itemBatteryQuadruple), new ItemStack(InitItems.itemBatteryTriple), " R ", "ICI", "III", 'R', new ItemStack(InitItems.itemBatteryTriple), 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
        //        recipeBatteryQuadruple = RecipeUtil.lastIRecipe();
        //
        //        //Quintuple Battery
        //        new RecipeKeepDataShaped(new ResourceLocation(ActuallyAdditions.MODID, "quintuple_battery"), new ItemStack(InitItems.itemBatteryQuintuple), new ItemStack(InitItems.itemBatteryQuadruple), " R ", "ICI", "III", 'R', new ItemStack(InitItems.itemBatteryQuadruple), 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
        //        recipeBatteryQuintuple = RecipeUtil.lastIRecipe();
        //
        //        //Cup
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal()), "S S", "SCS", "SSS", 'S', "stone", 'C', "cropCoffee");
        //        recipeCup = RecipeUtil.lastIRecipe();
        //
        //        //Resonant Rice
        //        if (!OreDictionary.getOres("nuggetEnderium", false).isEmpty()) {
        //            RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitItems.itemResonantRice), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), "nuggetEnderium", Items.GUNPOWDER);
        //        }
        //
        //        //Advanced Leaf Blower
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemLeafBlowerAdvanced), " F", "DP", "DC", 'F', new ItemStack(Items.FLINT), 'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), 'P', new ItemStack(Blocks.PISTON), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
        //        recipeLeafBlowerAdvanced = RecipeUtil.lastIRecipe();
        //
        //        //Phantom Connector
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemPhantomConnector), "YE", "EY", "S ", 'Y', Items.ENDER_EYE, 'E', Items.ENDER_PEARL, 'S', "stickWood");
        //        recipePhantomConnector = RecipeUtil.lastIRecipe();
        //
        //        //Player Probe
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemPlayerProbe), "A A", "AIA", "RHR", 'A', new ItemStack(Blocks.IRON_BARS), 'R', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.REDSTONE.ordinal()), 'H', new ItemStack(Items.SKULL, 1, 1), 'I', new ItemStack(Items.IRON_HELMET));
        //        recipePlayerProbe = RecipeUtil.lastIRecipe();
        //
        //        //Quartz
        //        GameRegistry.addSmelting(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal()), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);
        //
        //        //Knife
        //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitItems.itemKnife), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal()));
        //        recipeKnife = RecipeUtil.lastIRecipe();
        //
        //        //Crafter on a Stick
        //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitItems.itemCrafterOnAStick), new ItemStack(Blocks.CRAFTING_TABLE), new ItemStack(Items.SIGN));
        //
        //        //Tiny Coal
        //        if (ConfigBoolValues.TINY_COAL_STUFF.isEnabled()) {
        //            RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.TINY_COAL.ordinal()), new ItemStack(Items.COAL));
        //            recipeTinyCoal = RecipeUtil.lastIRecipe();
        //            RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.TINY_CHAR.ordinal()), new ItemStack(Items.COAL, 1, 1));
        //            recipeTinyChar = RecipeUtil.lastIRecipe();
        //            RecipeHandler.addOreDictRecipe(new ItemStack(Items.COAL), "CCC", "C C", "CCC", 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_COAL.ordinal()));
        //            RecipeHandler.addOreDictRecipe(new ItemStack(Items.COAL, 1, 1), "CCC", "C C", "CCC", 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_CHAR.ordinal()));
        //        }
        //
        //        //Rice Seeds
        //        RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemRiceSeed), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()));
        //
        //        //Canola Seeds
        //        RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemCanolaSeed), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal()));
        //
        //        //Rings
        //        initPotionRingRecipes();
        //
        //        //Ingots from Dusts
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.IRON.ordinal()), new ItemStack(Items.IRON_INGOT), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.GOLD.ordinal()), new ItemStack(Items.GOLD_INGOT), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.DIAMOND.ordinal()), new ItemStack(Items.DIAMOND), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.EMERALD.ordinal()), new ItemStack(Items.EMERALD), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.LAPIS.ordinal()), new ItemStack(Items.DYE, 1, 4), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ_BLACK.ordinal()), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ.ordinal()), new ItemStack(Items.QUARTZ), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()), new ItemStack(Items.COAL), 1F);
    }

    @Override
    protected void saveAdvancement(DirectoryCache cache, JsonObject cache2, Path advancementJson) {
        //Nope...
    }

    public static class Recipe {
        public static ItemRecipeGenerator.Recipe.Shapeless shapeless(IItemProvider result) {
            return new ItemRecipeGenerator.Recipe.Shapeless(result);
        }

        public static ItemRecipeGenerator.Recipe.Shapeless shapeless(IItemProvider result, int count) {
            return new ItemRecipeGenerator.Recipe.Shapeless(result, count);
        }

        public static ItemRecipeGenerator.Recipe.Shaped shaped(IItemProvider result) {
            return new ItemRecipeGenerator.Recipe.Shaped(result);
        }

        public static ItemRecipeGenerator.Recipe.Shaped shaped(IItemProvider result, int count) {
            return new ItemRecipeGenerator.Recipe.Shaped(result, count);
        }

        private static class Shapeless extends ShapelessRecipeBuilder {
            public Shapeless(IItemProvider result) {
                this(result, 1);
            }

            public Shapeless(IItemProvider result, int countIn) {
                super(result, countIn);
            }

            public ItemRecipeGenerator.Recipe.Shapeless ingredients(IItemProvider... ingredients) {
                Arrays.asList(ingredients).forEach(this::requires);
                return this;
            }

            @Override
            public void save(Consumer<IFinishedRecipe> consumer) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumer);
            }

            @Override
            public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation location) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumer, location);
            }
        }

        private static class Shaped extends ShapedRecipeBuilder {
            public Shaped(IItemProvider resultIn) {
                this(resultIn, 1);
            }

            public Shaped(IItemProvider resultIn, int countIn) {
                super(resultIn, countIn);
            }

            public ItemRecipeGenerator.Recipe.Shaped pattern(String line1, String line2, String line3) {
                this.pattern(line1);
                this.pattern(line2);
                this.pattern(line3);
                return this;
            }

            public ItemRecipeGenerator.Recipe.Shaped pattern(String line1, String line2) {
                this.pattern(line1);
                this.pattern(line2);
                return this;
            }

            public ItemRecipeGenerator.Recipe.Shaped patternSingleKey(char key, IItemProvider resource, String... lines) {
                this.define(key, resource);
                for (String line : lines) {
                    this.pattern(line);
                }

                return this;
            }

            @Override
            public void save(Consumer<IFinishedRecipe> consumerIn) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumerIn);
            }

            @Override
            public void save(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumerIn, id);
            }
        }
    }
}
