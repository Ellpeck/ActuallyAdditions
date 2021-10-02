package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

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
