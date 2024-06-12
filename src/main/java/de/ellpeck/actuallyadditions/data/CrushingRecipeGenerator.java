package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.util.NoAdvRecipeOutput;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

public class CrushingRecipeGenerator extends RecipeProvider {
    public CrushingRecipeGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Crushing " + super.getName();
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput output) {
        var recipeOutput = new NoAdvRecipeOutput(output);

        new CrushingBuilder(Ingredient.of(Items.BONE), new CrushingRecipe.CrushingResult(new ItemStack(Items.BONE_MEAL, 6), 1.0f))
                .save(recipeOutput, "bone");
        new CrushingBuilder(Ingredient.of(Items.SUGAR_CANE), new CrushingRecipe.CrushingResult(new ItemStack(Items.SUGAR, 3), 1.0f))
                .save(recipeOutput, "sugar_cane");
        new CrushingBuilder(Ingredient.of(Items.BLAZE_ROD), new CrushingRecipe.CrushingResult(new ItemStack(Items.BLAZE_POWDER, 3), 1.0f))
                .save(recipeOutput, "blaze_rod");

        new CrushingBuilder(Ingredient.of(Items.DANDELION), new CrushingRecipe.CrushingResult(new ItemStack(Items.YELLOW_DYE, 3), 1.0f))
                .save(recipeOutput, "danedlion");
        new CrushingBuilder(Ingredient.of(Items.POPPY), new CrushingRecipe.CrushingResult(new ItemStack(Items.RED_DYE, 3), 1.0f))
                .save(recipeOutput, "poppy");
        new CrushingBuilder(Ingredient.of(Items.BLUE_ORCHID), new CrushingRecipe.CrushingResult(new ItemStack(Items.LIGHT_BLUE_DYE, 3), 1.0f))
                .save(recipeOutput, "blue_orchid");
        new CrushingBuilder(Ingredient.of(Items.ALLIUM), new CrushingRecipe.CrushingResult(new ItemStack(Items.MAGENTA_DYE, 3), 1.0f))
                .save(recipeOutput, "allium");
        new CrushingBuilder(Ingredient.of(Items.AZURE_BLUET), new CrushingRecipe.CrushingResult(new ItemStack(Items.LIGHT_GRAY_DYE, 3), 1.0f))
                .save(recipeOutput, "azure_bluet");
        new CrushingBuilder(Ingredient.of(Items.RED_TULIP), new CrushingRecipe.CrushingResult(new ItemStack(Items.RED_DYE, 3), 1.0f))
                .save(recipeOutput, "red_tulip");
        new CrushingBuilder(Ingredient.of(Items.ORANGE_TULIP), new CrushingRecipe.CrushingResult(new ItemStack(Items.ORANGE_DYE, 3), 1.0f))
                .save(recipeOutput, "orange_tulip");
        new CrushingBuilder(Ingredient.of(Items.WHITE_TULIP), new CrushingRecipe.CrushingResult(new ItemStack(Items.LIGHT_GRAY_DYE, 3), 1.0f))
                .save(recipeOutput, "white_tulip");
        new CrushingBuilder(Ingredient.of(Items.PINK_TULIP), new CrushingRecipe.CrushingResult(new ItemStack(Items.PINK_DYE, 3), 1.0f))
                .save(recipeOutput, "pink_tulip");
        new CrushingBuilder(Ingredient.of(Items.PINK_PETALS), new CrushingRecipe.CrushingResult(new ItemStack(Items.PINK_DYE, 3), 1.0f))
                .save(recipeOutput, "pink_petals");
        new CrushingBuilder(Ingredient.of(Items.OXEYE_DAISY), new CrushingRecipe.CrushingResult(new ItemStack(Items.LIGHT_GRAY_DYE, 3), 1.0f))
                .save(recipeOutput, "oxeye_daisy");
        new CrushingBuilder(Ingredient.of(Items.CORNFLOWER), new CrushingRecipe.CrushingResult(new ItemStack(Items.BLUE_DYE, 3), 1.0f))
                .save(recipeOutput, "cornflower");
        new CrushingBuilder(Ingredient.of(Items.LILY_OF_THE_VALLEY), new CrushingRecipe.CrushingResult(new ItemStack(Items.WHITE_DYE, 3), 1.0f))
                .save(recipeOutput, "lily_of_the_valley");
        new CrushingBuilder(Ingredient.of(Items.WITHER_ROSE), new CrushingRecipe.CrushingResult(new ItemStack(Items.BLACK_DYE, 3), 1.0f))
                .save(recipeOutput, "wither_rose");
        new CrushingBuilder(Ingredient.of(Items.SUNFLOWER), new CrushingRecipe.CrushingResult(new ItemStack(Items.YELLOW_DYE, 4), 1.0f))
                .save(recipeOutput, "sunflower");
        new CrushingBuilder(Ingredient.of(Items.LILAC), new CrushingRecipe.CrushingResult(new ItemStack(Items.MAGENTA_DYE, 4), 1.0f))
                .save(recipeOutput, "lilac");
        new CrushingBuilder(Ingredient.of(Items.ROSE_BUSH), new CrushingRecipe.CrushingResult(new ItemStack(Items.RED_DYE, 4), 1.0f))
                .save(recipeOutput, "rose_bush");
        new CrushingBuilder(Ingredient.of(Items.PEONY), new CrushingRecipe.CrushingResult(new ItemStack(Items.PINK_DYE, 4), 1.0f))
                .save(recipeOutput, "peony");
        new CrushingBuilder(Ingredient.of(Items.PITCHER_PLANT), new CrushingRecipe.CrushingResult(new ItemStack(Items.CYAN_DYE, 4), 1.0f))
                .save(recipeOutput, "pitcher_plant");

        new CrushingBuilder(Ingredient.of(Items.REDSTONE_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.REDSTONE, 10), 1.0f))
                .save(recipeOutput, "redstone_ore");
        new CrushingBuilder(Ingredient.of(Items.DEEPSLATE_REDSTONE_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.REDSTONE, 10), 1.0f))
                .save(recipeOutput, "deepslate_redstone_ore");
        new CrushingBuilder(Ingredient.of(Items.LAPIS_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.LAPIS_LAZULI, 12), 1.0f))
                .save(recipeOutput, "lapis_ore");
        new CrushingBuilder(Ingredient.of(Items.DEEPSLATE_LAPIS_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.LAPIS_LAZULI, 12), 1.0f))
                .save(recipeOutput, "deepslate_lapis_ore");
        new CrushingBuilder(Ingredient.of(Items.COAL_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.COAL, 3), 1.0f))
                .save(recipeOutput, "coal_ore");
        new CrushingBuilder(Ingredient.of(Items.DEEPSLATE_COAL_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.COAL, 3), 1.0f))
                .save(recipeOutput, "deepslate_coal_ore");
        new CrushingBuilder(Ingredient.of(Items.COAL_BLOCK), new CrushingRecipe.CrushingResult(new ItemStack(Items.COAL, 9), 1.0f))
                .save(recipeOutput, "coal_block");
        new CrushingBuilder(Ingredient.of(Items.NETHER_QUARTZ_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.QUARTZ, 3), 1.0f))
                .save(recipeOutput, "nether_quartz_ore");
        new CrushingBuilder(Ingredient.of(Items.COBBLESTONE), new CrushingRecipe.CrushingResult(new ItemStack(Items.SAND, 1), 1.0f))
                .save(recipeOutput, "cobblestone");
        new CrushingBuilder(Ingredient.of(Items.GRAVEL), new CrushingRecipe.CrushingResult(new ItemStack(Items.FLINT, 1), 1.0f))
                .addResult2(new CrushingRecipe.CrushingResult(new ItemStack(Items.FLINT, 1), 0.5f))
                .save(recipeOutput, "gravel");
        new CrushingBuilder(Ingredient.of(ActuallyItems.RICE), new CrushingRecipe.CrushingResult(new ItemStack(Items.SUGAR, 2), 1.0f))
                .save(recipeOutput, "rice");
        new CrushingBuilder(Ingredient.of(Items.GLOWSTONE), new CrushingRecipe.CrushingResult(new ItemStack(Items.GLOWSTONE_DUST, 4), 1.0f))
                .save(recipeOutput, "glowstone");
        new CrushingBuilder(Ingredient.of(Items.DIAMOND_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.DIAMOND, 2), 1.0f))
                .save(recipeOutput, "diamond_ore");
        new CrushingBuilder(Ingredient.of(Items.DEEPSLATE_DIAMOND_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.DIAMOND, 2), 1.0f))
                .save(recipeOutput, "deepslate_diamond_ore");
        new CrushingBuilder(Ingredient.of(Items.EMERALD_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.EMERALD, 2), 1.0f))
                .save(recipeOutput, "emerald_ore");
        new CrushingBuilder(Ingredient.of(Items.DEEPSLATE_EMERALD_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.EMERALD, 2), 1.0f))
                .save(recipeOutput, "deepslate_emerald_ore");
        new CrushingBuilder(Ingredient.of(Items.PRISMARINE_SHARD), new CrushingRecipe.CrushingResult(new ItemStack(Items.PRISMARINE_CRYSTALS, 1), 1.0f))
                .save(recipeOutput, "prismarine_shard");
        new CrushingBuilder(Ingredient.of(ActuallyBlocks.BLACK_QUARTZ_ORE.get()), new CrushingRecipe.CrushingResult(new ItemStack(ActuallyItems.BLACK_QUARTZ.get(), 2), 1.0f))
                .save(recipeOutput, "black_quartz_ore");
        new CrushingBuilder(Ingredient.of(Items.COPPER_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_COPPER, 2), 1.0f))
                .addResult2(new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_GOLD, 1), 0.05f))
                .save(recipeOutput, "copper_ore");
        new CrushingBuilder(Ingredient.of(Items.DEEPSLATE_COPPER_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_COPPER, 2), 1.0f))
                .addResult2(new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_GOLD, 1), 0.05f))
                .save(recipeOutput, "deepslate_copper_ore");

        //TODO: Think about the recipes that returned crushed ores before and what to replace them with
        new CrushingBuilder(Ingredient.of(Items.IRON_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_IRON, 2), 1.0f))
                .addResult2(new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_GOLD, 1), 0.2f))
                .save(recipeOutput, "iron_ore");
        new CrushingBuilder(Ingredient.of(Items.DEEPSLATE_IRON_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_IRON, 2), 1.0f))
                .addResult2(new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_GOLD, 1), 0.2f))
                .save(recipeOutput, "deepslate_iron_ore");
        new CrushingBuilder(Ingredient.of(Items.IRON_HORSE_ARMOR), new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_IRON, 6), 1.0f))
                .save(recipeOutput, "iron_horse_armor");
        new CrushingBuilder(Ingredient.of(Items.GOLDEN_HORSE_ARMOR), new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_GOLD, 6), 1.0f))
                .save(recipeOutput, "golden_horse_armor");
        new CrushingBuilder(Ingredient.of(Items.DIAMOND_HORSE_ARMOR), new CrushingRecipe.CrushingResult(new ItemStack(Items.DIAMOND, 6), 1.0f))
                .save(recipeOutput, "diamond_horse_armor");
        new CrushingBuilder(Ingredient.of(Items.GOLD_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_GOLD, 2), 1.0f))
                .save(recipeOutput, "gold_ore");
        new CrushingBuilder(Ingredient.of(Items.DEEPSLATE_GOLD_ORE), new CrushingRecipe.CrushingResult(new ItemStack(Items.RAW_GOLD, 2), 1.0f))
                .save(recipeOutput, "deepslate_gold_ore");

    }

    public static class CrushingBuilder {
        private final Ingredient ingredient;
        private final NonNullList<CrushingRecipe.CrushingResult> results = NonNullList.withSize(2, CrushingRecipe.CrushingResult.EMPTY);

        public CrushingBuilder(Ingredient ingredient, CrushingRecipe.CrushingResult result) {
            this.ingredient = ingredient;
            this.results.set(0, result);
        }

        public CrushingBuilder addResult2(CrushingRecipe.CrushingResult result) {
            this.results.set(1, result);
            return this;
        }

        public void save(RecipeOutput consumer, ResourceLocation name) {
            if (results.size() != 2)
                throw new IllegalStateException("invalid result count: " + results.size() + ", recipe: " + name.toString());

            CrushingRecipe recipe = new CrushingRecipe(ingredient, results);
            consumer.accept(name, recipe, null);
        }

        public void save(RecipeOutput consumer, String name) {
            ResourceLocation res = new ResourceLocation(ActuallyAdditions.MODID, "crushing/" + name);
            if (results.size() != 2)
                throw new IllegalStateException("invalid result count: " + results.size() + ", recipe: " + name.toString());

            CrushingRecipe recipe = new CrushingRecipe(ingredient, results);
            consumer.accept(res, recipe, null);
        }
    }
}
