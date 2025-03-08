package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.config.conditions.BoolConfigCondition;
import de.ellpeck.actuallyadditions.mod.crafting.RecipeKeepDataShaped;
import de.ellpeck.actuallyadditions.mod.crafting.RecipeKeepDataShapeless;
import de.ellpeck.actuallyadditions.mod.crafting.TargetComponentIngredient;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.util.NoAdvRecipeOutput;
import de.ellpeck.actuallyadditions.mod.util.RecipeInjector;
import de.ellpeck.actuallyadditions.registration.AABlockReg;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ItemRecipeGenerator extends RecipeProvider {
    public ItemRecipeGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Item " + super.getName();
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput output, HolderLookup.Provider holderLookup) {
        HolderLookup.RegistryLookup<Enchantment> enchantmentLookup = holderLookup.lookupOrThrow(Registries.ENCHANTMENT);
        var recipeOutput = new NoAdvRecipeOutput(output);

        generateAOIT(recipeOutput);

        //Goggles
        Recipe.shaped(ActuallyItems.ENGINEERS_GOGGLES.get())
            .pattern(" R ")
            .pattern("IGI")
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', Items.IRON_BARS)
            .define('G', Tags.Items.GLASS_BLOCKS).save(recipeOutput);

        //Advanced Goggles
        Recipe.shaped(ActuallyItems.ENGINEERS_GOGGLES_ADVANCED.get())
            .pattern(" R ")
            .pattern("IGI")
            .define('R', ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL.get())
            .define('I', Items.IRON_BARS)
            .define('G', ActuallyItems.ENGINEERS_GOGGLES.get()).save(recipeOutput);

        //Laser Upgrades
        //Invisibility
        Recipe.shaped(ActuallyItems.LASER_UPGRADE_INVISIBILITY.get(), 4)
            .pattern("GGG")
            .pattern("RCR")
            .pattern("GGG")
            .define('G', Items.BLACK_STAINED_GLASS)
            .define('R', ActuallyItems.VOID_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(recipeOutput);

        //Range
        Recipe.shaped(ActuallyItems.LASER_UPGRADE_RANGE.get(), 2)
            .pattern("GGC")
            .pattern("RCR")
            .pattern("CGG")
            .define('R', Items.COMPASS)
            .define('G', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(recipeOutput);

        //Filling Wand
        Recipe.shaped(ActuallyItems.HANDHELD_FILLER.get())
            .pattern("IPI")
            .pattern("DCD")
            .pattern(" B ")
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('P', ActuallyItems.PALIS_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('B', ActuallyItems.TRIPLE_BATTERY.get()).save(recipeOutput);

        //Bag
        Recipe.shaped(ActuallyItems.TRAVELERS_SACK.get())
            .pattern("SLS")
            .pattern("SCS")
            .pattern("LVL")
            .define('S', Tags.Items.STRINGS)
            .define('L', Tags.Items.LEATHERS)
            .define('C', Tags.Items.CHESTS_WOODEN)
            .define('V', ActuallyBlocks.VOID_CRYSTAL.getItem()).save(recipeOutput);

        //Void Bag
        Recipe.shapeless(ActuallyItems.VOID_SACK.get())
            .requires(ActuallyItems.TRAVELERS_SACK.get())
            .requires(Tags.Items.ENDER_PEARLS)
            .requires(Tags.Items.OBSIDIANS)
            .requires(ActuallyBlocks.VOID_CRYSTAL.getItem())
            .save(recipeOutput);

        //Lens
        Recipe.shaped(ActuallyItems.LENS.get())
            .pattern("GGG")
            .pattern("GBG")
            .pattern("GGG")
            .define('G', Tags.Items.GLASS_BLOCKS)
            .define('B', ActuallyItems.BLACK_QUARTZ.get()).save(recipeOutput);

        //Booklet
        Recipe.shapeless(ActuallyItems.ITEM_BOOKLET.get())
            .ingredients(ActuallyItems.CANOLA_SEEDS.get(), Items.PAPER).save(recipeOutput);


        //Clearing NBT Storage
        Recipe.shapeless(ActuallyItems.LASER_WRENCH.get()).ingredients(ActuallyItems.LASER_WRENCH.get()).name(ActuallyAdditions.modLoc("laser_wrench_nbt")).save(recipeOutput);
        Recipe.shapeless(ActuallyItems.PHANTOM_CONNECTOR.get()).ingredients(ActuallyItems.PHANTOM_CONNECTOR.get()).name(ActuallyAdditions.modLoc("phantom_clearing")).save(recipeOutput);

        //Disenchanting Lens
        Recipe.shapeless(ActuallyItems.LENS_OF_DISENCHANTING.get())
            .requires(ActuallyItems.LENS.get())
            .requires(Items.ENCHANTING_TABLE)
            .requires(ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get(), 7).save(recipeOutput);

        //Mining Lens
        Recipe.shaped(ActuallyItems.LENS_OF_THE_MINER.get())
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
            .define('E', Tags.Items.GEMS_EMERALD).save(recipeOutput);

        //Killer Lens
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        enchantedBook.enchant(enchantmentLookup.getOrThrow(Enchantments.SHARPNESS), 5);

        Recipe.shapeless(ActuallyItems.LENS_OF_THE_KILLER.get())
                .requires(Items.DIAMOND_SWORD)
                .requires(ActuallyItems.LENS_OF_CERTAIN_DEATH.get())
                .requires(DataComponentIngredient.of(true, enchantedBook)).save(recipeOutput);

        //Filter
        Recipe.shaped(ActuallyItems.FILTER.get())
            .pattern("III")
            .pattern("IQI")
            .pattern("III")
            .define('I', Items.IRON_BARS)
            .define('Q', ActuallyItems.BLACK_QUARTZ.get()).save(recipeOutput);

        //Crate Keeper
        Recipe.shaped(ActuallyItems.CRATE_KEEPER.get())
            .pattern("WIW")
            .pattern("IQI")
            .pattern("WIW")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', ItemTags.PLANKS)
            .define('Q', ActuallyItems.BLACK_QUARTZ.get()).save(recipeOutput);

        //Laser Wrench
        Recipe.shaped(ActuallyItems.LASER_WRENCH.get())
            .pattern("C  ")
            .pattern(" S ")
            .pattern("  S")
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .define('S', ActuallyItems.ENORI_CRYSTAL.get()).save(recipeOutput);


        //Rice Recipes
        Recipe.shaped(Items.PAPER, 3)
            .pattern("R  ")
            .pattern(" R ")
            .pattern("  R")
            .define('R', ActuallyItems.RICE).save(recipeOutput, ActuallyAdditions.modLoc("rice_paper"));

        Recipe.shaped(ActuallyItems.RICE_SLIMEBALL.get())
            .requiresBook()
            .pattern(" R ")
            .pattern("RBR")
            .pattern(" R ")
            .define('R', ActuallyItems.RICE_DOUGH.get())
            .define('B', Items.WATER_BUCKET)
            .save(recipeOutput, ActuallyAdditions.modLoc("rice_slime"));

        Recipe.shaped(ActuallyItems.RICE_SLIMEBALL.get())
            .requiresBook()
            .pattern(" R ")
            .pattern("RBR")
            .pattern(" R ")
            .define('R', ActuallyItems.RICE_DOUGH.get())
            .define('B', Items.POTION)
            .save(recipeOutput, ActuallyAdditions.modLoc("rice_slime_potion"));

        //Leaf Blower
        Recipe.shaped(ActuallyItems.LEAF_BLOWER.get())
            .pattern(" F")
            .pattern("IP")
            .pattern("IC")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('P', Items.PISTON)
            .define('F', Items.FLINT)
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(recipeOutput);

        //Advanced Leaf Blower
        Recipe.shaped(ActuallyItems.ADVANCED_LEAF_BLOWER.get()).pattern(" F", "DP", "DC")
                .define('F', Items.FLINT)
                .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
                .define('P', Items.PISTON)
                .define('C', ActuallyItems.ADVANCED_COIL.get())
                .save(recipeOutput);

        //Drill //TODO the rest of the coloring recipes
        Recipe.shaped(ActuallyItems.DRILL_MAIN.get())
            .pattern("DDD")
            .pattern("CRC")
            .pattern("III")
            .define('D', Tags.Items.GEMS_DIAMOND)
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .define('R', ActuallyItems.DRILL_CORE.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get()).save(recipeOutput);

        // Drill Colors
        dyeDrill(ActuallyItems.DRILL_BLACK, Tags.Items.DYES_BLACK, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_RED, Tags.Items.DYES_RED, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_GREEN, Tags.Items.DYES_GREEN, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_BROWN, Tags.Items.DYES_BROWN, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_BLUE, Tags.Items.DYES_BLUE, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_PURPLE, Tags.Items.DYES_PURPLE, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_CYAN, Tags.Items.DYES_CYAN, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_LIGHT_GRAY, Tags.Items.DYES_LIGHT_GRAY, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_GRAY, Tags.Items.DYES_GRAY, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_PINK, Tags.Items.DYES_PINK, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_LIME, Tags.Items.DYES_LIME, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_YELLOW, Tags.Items.DYES_YELLOW, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_MAIN, Tags.Items.DYES_LIGHT_BLUE, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_ORANGE, Tags.Items.DYES_ORANGE, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_WHITE, Tags.Items.DYES_WHITE, recipeOutput);
        dyeDrill(ActuallyItems.DRILL_MAGENTA, Tags.Items.DYES_MAGENTA, recipeOutput);


        //Drill Core
        Recipe.shaped(ActuallyItems.DRILL_CORE.get())
            .pattern("ICI")
            .pattern("CRC")
            .pattern("ICI")
            .define('C', ActuallyItems.BASIC_COIL.get())
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get()).save(recipeOutput);

        //Tele Staff
        Recipe.shaped(ActuallyItems.TELEPORT_STAFF.get())
            .pattern(" FE")
            .pattern(" S ")
            .pattern("SB ")
            .define('F', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get())
            .define('E', Tags.Items.ENDER_PEARLS)
            .define('S', ActuallyBlocks.ENDER_CASING.getItem())
            .define('B', ActuallyItems.SINGLE_BATTERY.get()).save(recipeOutput);

        //Drill Speed upgrade
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get()).save(recipeOutput);

        //Drill Speed upgrade II
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED_II.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', Items.CAKE).save(recipeOutput);

        //Drill Speed upgrade III
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED_III.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get()).save(recipeOutput);

        //Drill Fortune upgrade
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FORTUNE.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', Items.GLOWSTONE)
            .define('S', Tags.Items.DUSTS_REDSTONE)
            .define('R', ActuallyBlocks.EMPOWERED_DIAMATINE_CRYSTAL.getItem()).save(recipeOutput);

        //Drill Fortune upgrade II
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FORTUNE_II.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', Items.GLOWSTONE)
            .define('S', ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL.get())
            .define('R', ActuallyBlocks.ENDER_CASING.getItem()).save(recipeOutput);

        //3x3
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_THREE_BY_THREE.get())
            .pattern("DID")
            .pattern("ICI")
            .pattern("DID")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.BASIC_COIL.get()).save(recipeOutput);

        //5x5
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FIVE_BY_FIVE.get())
            .pattern("DID")
            .pattern("ICI")
            .pattern("DID")
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(recipeOutput);

        //Silk Touch
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SILK_TOUCH.get())
            .pattern("DSD")
            .pattern("SCS")
            .pattern("DSD")
            .define('D', ActuallyItems.EMERADIC_CRYSTAL.get())
            .define('S', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(recipeOutput);

        //Placing
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_BLOCK_PLACING.get())
            .pattern("CEC")
            .pattern("RAR")
            .pattern("CEC")
            .define('C', Tags.Items.COBBLESTONES)
            .define('E', Items.PAPER)
            .define('A', ActuallyItems.BASIC_COIL.get())
            .define('R', ActuallyItems.ENORI_CRYSTAL.get()).save(recipeOutput);

        //Bat Wings
        Recipe.shaped(ActuallyItems.WINGS_OF_THE_BATS.get())
            .pattern("WNW")
            .pattern("WDW")
            .pattern("WNW")
            .define('W', ActuallyItems.BATS_WING.get())
            .define('N', ActuallyBlocks.DIAMATINE_CRYSTAL.getItem())
            .define('D', ActuallyItems.ENDER_STAR.get()).save(recipeOutput);

        //Coil
        Recipe.shaped(ActuallyItems.BASIC_COIL.get())
            .pattern(" R ")
            .pattern("RIR")
            .pattern(" R ")
            .define('I', ActuallyItems.BLACK_QUARTZ.get())
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get()).save(recipeOutput);

        //Advanced Coil
        Recipe.shaped(ActuallyItems.ADVANCED_COIL.get())
            .pattern("GGG")
            .pattern("GCG")
            .pattern("GGG")
            .define('C', ActuallyItems.BASIC_COIL.get())
            .define('G', Items.GOLD_NUGGET).save(recipeOutput);

        //Battery
        Recipe.shaped(ActuallyItems.SINGLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(recipeOutput);

        //Double Battery
        Recipe.shaped(ActuallyItems.DOUBLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetComponentIngredient.of(ActuallyItems.SINGLE_BATTERY.get()))
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(new RecipeInjector<ShapedRecipe>(recipeOutput, RecipeKeepDataShaped::new));

        //Triple Battery
        Recipe.shaped(ActuallyItems.TRIPLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetComponentIngredient.of(ActuallyItems.DOUBLE_BATTERY.get()))
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(new RecipeInjector<ShapedRecipe>(recipeOutput, RecipeKeepDataShaped::new));

        //Quad Battery
        Recipe.shaped(ActuallyItems.QUADRUPLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetComponentIngredient.of(ActuallyItems.TRIPLE_BATTERY.get()))
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(new RecipeInjector<ShapedRecipe>(recipeOutput, RecipeKeepDataShaped::new));

        //Quintuple Battery
        Recipe.shaped(ActuallyItems.QUINTUPLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetComponentIngredient.of(ActuallyItems.QUADRUPLE_BATTERY.get()))
            .define('I', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(new RecipeInjector<ShapedRecipe>(recipeOutput, RecipeKeepDataShaped::new));

        //Magnet Ring
        Recipe.shaped(ActuallyItems.RING_OF_MAGNETIZING.get())
                .pattern("RIB", "IOI", "BIR")
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('I', ActuallyItems.ENORI_CRYSTAL.get())
                .define('B', Items.LAPIS_LAZULI)
                .define('O', ActuallyItems.RING.get())
                .save(recipeOutput);

        //Growth Ring
        Recipe.shaped(ActuallyItems.RING_OF_GROWTH.get())
                .pattern("SIS", "IOI", "SIS")
                .define('S', Tags.Items.SEEDS)
                .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
                .define('O', ActuallyItems.RING.get())
                .save(recipeOutput);

        Recipe.shapeless(ActuallyItems.CRAFTER_ON_A_STICK.get()).requires(Items.CRAFTING_TABLE).requires(ItemTags.SIGNS).save(recipeOutput);

        RecipeOutput boolConsumer = recipeOutput.withConditions(new BoolConfigCondition("tinyCoalStuff"));

        Recipe.shapeless(ActuallyItems.TINY_COAL.get(), 8)
                .requires(Items.COAL)
                .save(boolConsumer, ActuallyAdditions.modLoc("coal_to_tiny"));
        Recipe.shapeless(ActuallyItems.TINY_CHARCOAL.get(), 8)
                .requires(Items.CHARCOAL).save(boolConsumer, ActuallyAdditions.modLoc("charcoal_to_tiny"));
        Recipe.shaped(Items.COAL)
                .pattern("CCC", "C C", "CCC").define('C', ActuallyItems.TINY_COAL.get())
                .save(boolConsumer, ActuallyAdditions.modLoc("tiny_to_coal"));
        Recipe.shaped(Items.CHARCOAL)
                .pattern("CCC", "C C", "CCC").define('C', ActuallyItems.TINY_CHARCOAL.get())
                .save(boolConsumer, ActuallyAdditions.modLoc("tiny_to_charcoal"));

        //Canola Seeds
        Recipe.shapeless(ActuallyItems.CANOLA_SEEDS.get())
                .requires(ActuallyItems.CANOLA.get())
                .save(recipeOutput);

        //Rice Seeds
        Recipe.shapeless(ActuallyItems.RICE_SEEDS.get())
                .requires(ActuallyItems.RICE.get())
                .save(recipeOutput);

        //Rice Dough
        Recipe.shapeless(ActuallyItems.RICE_DOUGH.get(), 2)
                .requires(ActuallyItems.RICE.get(), 3)
                .save(recipeOutput);

        //Ring, glow stone dust in the middle, iron in the corners, gold in the cardinals.
        Recipe.shaped(ActuallyItems.RING)
                .pattern("IGI", "GDG", "IGI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('D', Tags.Items.DUSTS_GLOWSTONE)
                .save(recipeOutput);

        //Cup
        Recipe.shaped(ActuallyItems.EMPTY_CUP.get())
                .pattern("S S", "SCS", "SSS")
                .define('S', Tags.Items.STONES)
                .define('C', ActuallyItems.COFFEE_BEANS.get())
                .save(recipeOutput);

        //Phantom Connector
        Recipe.shaped(ActuallyItems.PHANTOM_CONNECTOR.get())
                .pattern("YE", "EY", "S ")
                .define('Y', Items.ENDER_EYE)
                .define('E', Tags.Items.ENDER_PEARLS)
                .define('S', Tags.Items.RODS_WOODEN)
                .save(recipeOutput);

        //Player Probe
        Recipe.shaped(ActuallyItems.PLAYER_PROBE.get())
                .pattern("A A", "AIA", "RHR")
                .define('A', Items.IRON_BARS)
                .define('R', ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL.get())
                .define('H', Items.WITHER_SKELETON_SKULL)
                .define('I', Items.IRON_HELMET)
                .save(recipeOutput);

        // Item Tag
        Recipe.shapeless(ActuallyItems.ITEM_TAG.get())
                .requires(Items.PAPER)
                .requires(ActuallyItems.BLACK_QUARTZ)
                .save(recipeOutput);

        // Sticky Piston from tagged slime balls
        Recipe.shaped(Items.STICKY_PISTON)
                .pattern("R", "P")
                .define('R', Tags.Items.SLIME_BALLS)
                .define('P', Items.PISTON)
                .save(recipeOutput, ActuallyAdditions.modLoc("tagged_sticky_piston"));

        // Slime block from tagged balls
        Recipe.shaped(Items.SLIME_BLOCK)
                .pattern("RRR", "RRR", "RRR")
                .define('R', Tags.Items.SLIME_BALLS)
                .save(recipeOutput, ActuallyAdditions.modLoc("tagged_slime_block"));

        //Shards
        addShard(recipeOutput, ActuallyItems.VOID_CRYSTAL_SHARD, ActuallyItems.VOID_CRYSTAL);
        addShard(recipeOutput, ActuallyItems.ENORI_CRYSTAL_SHARD, ActuallyItems.ENORI_CRYSTAL);
        addShard(recipeOutput, ActuallyItems.RESTONIA_CRYSTAL_SHARD, ActuallyItems.RESTONIA_CRYSTAL);
        addShard(recipeOutput, ActuallyItems.PALIS_CRYSTAL_SHARD, ActuallyItems.PALIS_CRYSTAL);
        addShard(recipeOutput, ActuallyItems.DIAMATINE_CRYSTAL_SHARD, ActuallyItems.DIAMATINE_CRYSTAL);
        addShard(recipeOutput, ActuallyItems.EMERADIC_CRYSTAL_SHARD, ActuallyItems.EMERADIC_CRYSTAL);

        //Crystal Blocks
        addCrystalBlock(recipeOutput, ActuallyBlocks.VOID_CRYSTAL.getItem(), ActuallyItems.VOID_CRYSTAL);
        addCrystalBlock(recipeOutput, ActuallyBlocks.ENORI_CRYSTAL.getItem(), ActuallyItems.ENORI_CRYSTAL);
        addCrystalBlock(recipeOutput, ActuallyBlocks.RESTONIA_CRYSTAL.getItem(), ActuallyItems.RESTONIA_CRYSTAL);
        addCrystalBlock(recipeOutput, ActuallyBlocks.PALIS_CRYSTAL.getItem(), ActuallyItems.PALIS_CRYSTAL);
        addCrystalBlock(recipeOutput, ActuallyBlocks.DIAMATINE_CRYSTAL.getItem(), ActuallyItems.DIAMATINE_CRYSTAL);
        addCrystalBlock(recipeOutput, ActuallyBlocks.EMERADIC_CRYSTAL.getItem(), ActuallyItems.EMERADIC_CRYSTAL);

        //Empowered Crystal Blocks
        addCrystalBlock(recipeOutput, ActuallyBlocks.EMPOWERED_VOID_CRYSTAL.getItem(), ActuallyItems.EMPOWERED_VOID_CRYSTAL);
        addCrystalBlock(recipeOutput, ActuallyBlocks.EMPOWERED_ENORI_CRYSTAL.getItem(), ActuallyItems.EMPOWERED_ENORI_CRYSTAL);
        addCrystalBlock(recipeOutput, ActuallyBlocks.EMPOWERED_RESTONIA_CRYSTAL.getItem(), ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL);
        addCrystalBlock(recipeOutput, ActuallyBlocks.EMPOWERED_PALIS_CRYSTAL.getItem(), ActuallyItems.EMPOWERED_PALIS_CRYSTAL);
        addCrystalBlock(recipeOutput, ActuallyBlocks.EMPOWERED_DIAMATINE_CRYSTAL.getItem(), ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL);
        addCrystalBlock(recipeOutput, ActuallyBlocks.EMPOWERED_EMERADIC_CRYSTAL.getItem(), ActuallyItems.EMPOWERED_EMERADIC_CRYSTAL);


        //        //Quartz
        //        GameRegistry.addSmelting(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal()), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);

        //        //Ingots from Dusts
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.IRON.ordinal()), new ItemStack(Items.IRON_INGOT), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.GOLD.ordinal()), new ItemStack(Items.GOLD_INGOT), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.DIAMOND.ordinal()), new ItemStack(Items.DIAMOND), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.EMERALD.ordinal()), new ItemStack(Items.EMERALD), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.LAPIS.ordinal()), new ItemStack(Items.DYE, 1, 4), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ_BLACK.ordinal()), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ.ordinal()), new ItemStack(Items.QUARTZ), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()), new ItemStack(Items.COAL), 1F);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ActuallyItems.RICE_DOUGH), RecipeCategory.FOOD, Items.BREAD, 0.35F, 200)
            .unlockedBy("", has(Items.AIR))
            .save(recipeOutput, ActuallyAdditions.modLoc("rice_dough_smelting"));

        // Black Quartz Ore
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ActuallyBlocks.BLACK_QUARTZ_ORE.getItem()), RecipeCategory.MISC, ActuallyItems.BLACK_QUARTZ.get(), 0.7F, 200)
            .unlockedBy("", has(Items.AIR))
            .save(recipeOutput, ActuallyAdditions.modLoc("black_quartz_ore_smelting"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ActuallyBlocks.BLACK_QUARTZ_ORE.getItem()), RecipeCategory.MISC, ActuallyItems.BLACK_QUARTZ.get(), 0.7F, 100)
            .unlockedBy("", has(Items.AIR))
            .save(recipeOutput, ActuallyAdditions.modLoc("black_quartz_ore_blasting"));

        //Patterns
        Recipe.shapeless(ActuallyItems.DRILL_PATTERN.get())
                .requires(ActuallyItems.DRILL_CORE.get())
                .requires(Items.PAPER)
                .save(recipeOutput);
        Recipe.shapeless(ActuallyItems.LEAF_BLO_PATTERN.get())
                .requires(ActuallyItems.LEAF_BLOWER.get())
                .requires(Items.PAPER)
                .save(recipeOutput);
        Recipe.shapeless(ActuallyItems.PHAN_CON_PATTERN.get())
                .requires(ActuallyItems.PHANTOM_CONNECTOR.get())
                .requires(Items.PAPER)
                .save(recipeOutput);
        Recipe.shapeless(ActuallyItems.BOOK_PATTERN.get())
                .requires(ActuallyItems.ITEM_BOOKLET.get())
                .requires(Items.PAPER)
                .save(recipeOutput);

        // Reset Recipes
        generateReset(ActuallyBlocks.OIL_GENERATOR, recipeOutput);
        generateReset(ActuallyBlocks.COAL_GENERATOR, recipeOutput);
        generateReset(ActuallyBlocks.LEAF_GENERATOR, recipeOutput);
        generateReset(ActuallyBlocks.POWERED_FURNACE, recipeOutput);
        generateReset(ActuallyBlocks.CRUSHER, recipeOutput);
        generateReset(ActuallyBlocks.CRUSHER_DOUBLE, recipeOutput);
        generateReset(ActuallyBlocks.DISPLAY_STAND, recipeOutput);
        generateReset(ActuallyBlocks.ATOMIC_RECONSTRUCTOR, recipeOutput);
        generateReset(ActuallyBlocks.FARMER, recipeOutput);
        generateReset(ActuallyBlocks.DROPPER, recipeOutput);
        generateReset(ActuallyBlocks.PLACER, recipeOutput);
        generateReset(ActuallyBlocks.BREAKER, recipeOutput);
        generateReset(ActuallyBlocks.FLUID_COLLECTOR, recipeOutput);
        generateReset(ActuallyBlocks.FLUID_PLACER, recipeOutput);
        generateReset(ActuallyBlocks.COFFEE_MACHINE, recipeOutput);
        generateReset(ActuallyBlocks.CANOLA_PRESS, recipeOutput);
        generateReset(ActuallyBlocks.FERMENTING_BARREL, recipeOutput);

    }

    protected void generateReset(@Nonnull AABlockReg<?, ?, ?> item, @Nonnull RecipeOutput consumer) {
        Recipe.shapeless(item.getItem())
                .ingredients(item.getItem())
                .name(ActuallyAdditions.modLoc("reset/" + item.getName()))
                .save(consumer);
    }

    protected void generateAOIT(RecipeOutput consumer) {
        addPaxel(consumer, ActuallyItems.WOODEN_AIOT, Items.WOODEN_AXE, Items.WOODEN_PICKAXE, Items.WOODEN_SWORD, Items.WOODEN_SHOVEL, Items.WOODEN_HOE);
        addPaxel(consumer, ActuallyItems.STONE_AIOT, Items.STONE_AXE, Items.STONE_PICKAXE, Items.STONE_SWORD, Items.STONE_SHOVEL, Items.STONE_HOE);
        addPaxel(consumer, ActuallyItems.IRON_AIOT, Items.IRON_AXE, Items.IRON_PICKAXE, Items.IRON_SWORD, Items.IRON_SHOVEL, Items.IRON_HOE);
        addPaxel(consumer, ActuallyItems.GOLD_AIOT, Items.GOLDEN_AXE, Items.GOLDEN_PICKAXE, Items.GOLDEN_SWORD, Items.GOLDEN_SHOVEL, Items.GOLDEN_HOE);
        addPaxel(consumer, ActuallyItems.DIAMOND_AIOT, Items.DIAMOND_AXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_SWORD, Items.DIAMOND_SHOVEL, Items.DIAMOND_HOE);
        addPaxel(consumer, ActuallyItems.NETHERITE_AIOT, Items.NETHERITE_AXE, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_SHOVEL, Items.NETHERITE_HOE);
    }

    private static void dyeDrill(DeferredItem<? extends Item> result, TagKey<Item> dyeItem, RecipeOutput recipeOutput) {
        Recipe.shapeless(result.get())
                .requires(TargetComponentIngredient.of(ActuallyTags.Items.DRILLS))
                .requires(dyeItem)
                .save(new RecipeInjector<ShapelessRecipe>(recipeOutput, RecipeKeepDataShapeless::new), ActuallyAdditions.modLoc("drill_coloring/dye_" + BuiltInRegistries.ITEM.getKey(result.get()).getPath()));
    }

    public static void addPaxel(RecipeOutput consumer, DeferredItem<? extends Item> output, Item axe, Item pickaxe, Item sword, Item shovel, Item hoe) {
        Recipe.shapeless(output.get())
            .requires(axe)
            .requires(pickaxe)
            .requires(sword)
            .requires(shovel)
            .requires(hoe)
            .save(consumer);
    }

    public static void addPaxel(RecipeOutput consumer, DeferredItem<? extends Item> output, DeferredItem<? extends Item> axe, DeferredItem<? extends Item> pickaxe, DeferredItem<? extends Item> sword, DeferredItem<? extends Item> shovel, DeferredItem<? extends Item> hoe) {
        Recipe.shapeless(output.get())
            .requires(axe.get())
            .requires(pickaxe.get())
            .requires(sword.get())
            .requires(shovel.get())
            .requires(hoe.get())
            .save(consumer);
    }

    public static void decompress(RecipeOutput consumer, ItemLike output, ItemLike input) {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(output.asItem());
        Recipe.shapeless(output, 9).requires(input).save(consumer, ResourceLocation.fromNamespaceAndPath(key.getNamespace(), "decompress/" + key.getPath()));
    }
    public static void compress(RecipeOutput consumer, ItemLike output, ItemLike input) {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(output.asItem());
        Recipe.shaped(output).pattern("xxx","xxx", "xxx").define('x', input).save(consumer, ResourceLocation.fromNamespaceAndPath(key.getNamespace(), "compress/" + key.getPath()));
    }
    public static void addShard(RecipeOutput consumer, DeferredItem<? extends Item> shard, DeferredItem<? extends Item> crystal) {
        compress(consumer, crystal, shard);
        decompress(consumer, shard, crystal);
    }

    public static void addCrystalBlock(RecipeOutput consumer, ItemLike block, DeferredItem<? extends Item> crystal) {
        compress(consumer, block, crystal);
        decompress(consumer, crystal, block);
    }

    public static void addToolAndArmorRecipes(RecipeOutput consumer, DeferredItem<? extends Item> base, DeferredItem<? extends Item> pickaxe, DeferredItem<? extends Item> sword, DeferredItem<? extends Item> axe, DeferredItem<? extends Item> shovel, DeferredItem<? extends Item> hoe, DeferredItem<? extends Item> helm, DeferredItem<? extends Item> chest, DeferredItem<? extends Item> pants, DeferredItem<? extends Item> boots) {
        //Pickaxe
        Recipe.shaped(pickaxe.get())
            .pattern("EEE", " S ", " S ")
            .define('E', base.get())
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Sword
        Recipe.shaped(sword.get())
            .pattern("E", "E", "S")
            .define('E', base.get())
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Axe
        Recipe.shaped(axe.get())
            .pattern("EE", "ES", " S")
            .define('E', base.get())
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Shovel
        Recipe.shaped(shovel.get())
            .pattern("E", "S", "S")
            .define('E', base.get())
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Hoe
        Recipe.shaped(hoe.get())
            .pattern("EE", " S", " S")
            .define('E', base.get())
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Helm
        Recipe.shaped(helm.get())
            .pattern("OOO", "O O")
            .define('O', base.get())
            .save(consumer);

        //Chest
        Recipe.shaped(chest.get())
            .pattern("O O", "OOO", "OOO")
            .define('O', base.get())
            .save(consumer);

        //Legs
        Recipe.shaped(pants.get())
            .pattern("OOO", "O O", "O O")
            .define('O', base.get())
            .save(consumer);

        //Boots
        Recipe.shaped(boots.get())
            .pattern("O O", "O O")
            .define('O', base.get())
            .save(consumer);
    }

    @SuppressWarnings("ClassEscapesDefinedScope")
    public static class Recipe {
        public static ItemRecipeGenerator.Recipe.Shapeless shapeless(ItemLike result) {
            return new ItemRecipeGenerator.Recipe.Shapeless(result);
        }

        public static ItemRecipeGenerator.Recipe.Shapeless shapeless(ItemLike result, int count) {
            return new ItemRecipeGenerator.Recipe.Shapeless(result, count);
        }

        public static ItemRecipeGenerator.Recipe.Shaped shaped(ItemLike result) {
            return new ItemRecipeGenerator.Recipe.Shaped(result);
        }

        public static ItemRecipeGenerator.Recipe.Shaped shaped(ItemLike result, int count) {
            return new ItemRecipeGenerator.Recipe.Shaped(result, count);
        }

        private static class Shapeless extends ShapelessRecipeBuilder {
            private ResourceLocation name;
            public Shapeless(ItemLike result) {
                this(result, 1);
            }

            public Shapeless(ItemLike result, int countIn) {
                super(RecipeCategory.MISC, result, countIn);
            }

            public ItemRecipeGenerator.Recipe.Shapeless ingredients(ItemLike... ingredients) {
                Arrays.asList(ingredients).forEach(this::requires);
                return this;
            }

            public ItemRecipeGenerator.Recipe.Shapeless name(ResourceLocation name) {
                this.name = name;
                return this;
            }

            @Override
            public void save(@Nonnull RecipeOutput consumer) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                if (this.name != null) {
                    this.save(consumer, this.name);
                } else {
                    super.save(consumer);
                }
            }
            @Override
            public void save(@Nonnull RecipeOutput consumer, @Nonnull ResourceLocation location) {
                this.unlockedBy("", has(Items.AIR));
                super.save(consumer, location);
            }
        }

        public static class Shaped extends ShapedRecipeBuilder {
            public Shaped(ItemLike resultIn) {
                this(resultIn, 1);
            }

            public Shaped(ItemLike resultIn, int countIn) {
                super(RecipeCategory.MISC, resultIn, countIn);
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

            public ItemRecipeGenerator.Recipe.Shaped patternSingleKey(char key, ItemLike resource, String... lines) {
                this.define(key, resource);
                for (String line : lines) {
                    this.pattern(line);
                }

                return this;
            }

            public ItemRecipeGenerator.Recipe.Shaped requiresBook() {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                return this;
            }

            @Override
            public void save(@Nonnull RecipeOutput consumerIn) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumerIn);
            }

            @Override
            public void save(@Nonnull RecipeOutput consumer, @Nonnull ResourceLocation location) {
                this.unlockedBy("", has(Items.AIR));
                super.save(consumer, location);
            }
        }
    }
}
