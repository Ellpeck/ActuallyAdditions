package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.LaserRecipe;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.util.NoAdvRecipeOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class LaserRecipeGenerator extends RecipeProvider {
    public LaserRecipeGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public String getName() {
        return "Laser " + super.getName();
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput output) {
        var recipeOutput = new NoAdvRecipeOutput(output);

        //Crystal Blocks
        laserCrystalizeRecipe(recipeOutput, ActuallyBlocks.RESTONIA_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_REDSTONE, 400);
        laserCrystalizeRecipe(recipeOutput, ActuallyBlocks.PALIS_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_LAPIS, 400);
        laserCrystalizeRecipe(recipeOutput, ActuallyBlocks.DIAMATINE_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_DIAMOND, 600);
        laserCrystalizeRecipe(recipeOutput, ActuallyBlocks.EMERADIC_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_EMERALD, 1000);
        laserCrystalizeRecipe(recipeOutput, ActuallyBlocks.VOID_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_COAL, 600);
        laserCrystalizeRecipe(recipeOutput, ActuallyBlocks.ENORI_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_IRON, 800);

        //Crystal Items
        laserCrystalizeRecipe(recipeOutput, ActuallyItems.RESTONIA_CRYSTAL.get(), Tags.Items.DUSTS_REDSTONE, 40);
        laserCrystalizeRecipe(recipeOutput, ActuallyItems.PALIS_CRYSTAL.get(), Tags.Items.GEMS_LAPIS, 40);
        laserCrystalizeRecipe(recipeOutput, ActuallyItems.DIAMATINE_CRYSTAL.get(), Tags.Items.GEMS_DIAMOND, 60);
        laserCrystalizeRecipe(recipeOutput, ActuallyItems.EMERADIC_CRYSTAL.get(), Tags.Items.GEMS_EMERALD, 100);
        laserCrystalizeRecipe(recipeOutput, ActuallyItems.VOID_CRYSTAL.get(), ItemTags.COALS, 60);
        laserCrystalizeRecipe(recipeOutput, ActuallyItems.ENORI_CRYSTAL.get(), Tags.Items.INGOTS_IRON, 80);

        //Lenses
        laserRecipe(recipeOutput, ActuallyItems.LENS_OF_COLOR.get(), ActuallyItems.LENS.get(), 5000);
        laserRecipe(recipeOutput, ActuallyItems.LENS_OF_DETONATION.get(), ActuallyItems.LENS_OF_COLOR.get(), 5000);
        laserRecipe(recipeOutput, ActuallyItems.LENS_OF_CERTAIN_DEATH.get(), ActuallyItems.LENS_OF_DETONATION.get(), 5000);
        laserRecipe(recipeOutput, ActuallyItems.LENS.get(), ActuallyItems.LENS_OF_CERTAIN_DEATH.get(), 5000);

        //Relays
        laserRecipe(recipeOutput, ActuallyBlocks.LASER_RELAY_FLUIDS.getItem(), ActuallyBlocks.LASER_RELAY.getItem(), 2000);
        laserRecipe(recipeOutput, ActuallyBlocks.LASER_RELAY_ITEM.getItem(), ActuallyBlocks.LASER_RELAY_FLUIDS.getItem(), 2000);
        laserRecipe(recipeOutput, ActuallyBlocks.LASER_RELAY.getItem(), ActuallyBlocks.LASER_RELAY_ITEM.getItem(), 2000);

        //Misc
        laserRecipe(recipeOutput, Items.SOUL_SAND, Tags.Items.SANDS, 20000);
        laserRecipe(recipeOutput, Items.LEATHER, Items.ROTTEN_FLESH, 20000);
        laserRecipe(recipeOutput, Items.NETHER_WART, Items.RED_MUSHROOM, 150000);
        laserRecipe(recipeOutput, Items.PRISMARINE_SHARD, Items.QUARTZ, 30000);
        laserRecipe(recipeOutput, ActuallyItems.CRYSTALLIZED_CANOLA_SEED.get(), ActuallyItems.CANOLA_SEEDS.get(), 2000);
        laserRecipe(recipeOutput, ActuallyBlocks.ETHETIC_WHITE_BLOCK.getItem(), Items.QUARTZ_BLOCK, 10);
        laserRecipe(recipeOutput, ActuallyBlocks.ETHETIC_GREEN_BLOCK.getItem(), Items.CHISELED_QUARTZ_BLOCK, 10);

    }

    private void laserRecipe(RecipeOutput consumer, ItemLike output, Ingredient input, int energy) {
        ResourceLocation id = ActuallyAdditions.modLoc("laser/" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), input, energy), null);
    }
    private void laserRecipe(RecipeOutput consumer, ItemLike output, TagKey<Item> input, int energy) {
        ResourceLocation id = ActuallyAdditions.modLoc("laser/" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), Ingredient.of(input), energy), null);
    }
    private void laserRecipe(RecipeOutput consumer, ItemLike output, ItemLike input, int energy) {
        ResourceLocation id = ActuallyAdditions.modLoc("laser/" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), Ingredient.of(input), energy), null);
    }
    private void laserCrystalizeRecipe(RecipeOutput consumer, ItemLike output, Ingredient input, int energy) {
        ResourceLocation id = ActuallyAdditions.modLoc("laser/crystalize_" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), input, energy), null);
    }
    private void laserCrystalizeRecipe(RecipeOutput consumer, ItemLike output, TagKey<Item> input, int energy) {
        ResourceLocation id = ActuallyAdditions.modLoc("laser/crystalize_" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), Ingredient.of(input), energy), null);
    }
    private void laserCrystalizeRecipe(RecipeOutput consumer, ItemLike output, ItemLike input, int energy) {
        ResourceLocation id = ActuallyAdditions.modLoc("laser/crystalize_" + BuiltInRegistries.ITEM.getKey(output.asItem()).getPath());
        consumer.accept(id, new LaserRecipe(output.asItem().getDefaultInstance(), Ingredient.of(input), energy), null);
    }
}
