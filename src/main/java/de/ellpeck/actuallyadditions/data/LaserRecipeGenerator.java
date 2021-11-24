package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.LaserRecipe;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.nio.file.Path;
import java.util.function.Consumer;

public class LaserRecipeGenerator extends RecipeProvider {
    public LaserRecipeGenerator(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected void saveAdvancement(DirectoryCache pCache, JsonObject pAdvancementJson, Path pPath) {
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        //Crystal Blocks
        laserCrystalizeRecipe(consumer, ActuallyBlocks.RESTONIA_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_REDSTONE, 400);
        laserCrystalizeRecipe(consumer, ActuallyBlocks.PALIS_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_LAPIS, 400);
        laserCrystalizeRecipe(consumer, ActuallyBlocks.DIAMATINE_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_DIAMOND, 600);
        laserCrystalizeRecipe(consumer, ActuallyBlocks.EMERADIC_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_EMERALD, 1000);
        laserCrystalizeRecipe(consumer, ActuallyBlocks.VOID_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_COAL, 600);
        laserCrystalizeRecipe(consumer, ActuallyBlocks.ENORI_CRYSTAL.getItem(), Tags.Items.STORAGE_BLOCKS_IRON, 800);

        //Crystal Items
        laserCrystalizeRecipe(consumer, ActuallyItems.RESTONIA_CRYSTAL.get(), Tags.Items.DUSTS_REDSTONE, 40);
        laserCrystalizeRecipe(consumer, ActuallyItems.PALIS_CRYSTAL.get(), Tags.Items.GEMS_LAPIS, 40);
        laserCrystalizeRecipe(consumer, ActuallyItems.DIAMATINE_CRYSTAL.get(), Tags.Items.GEMS_DIAMOND, 60);
        laserCrystalizeRecipe(consumer, ActuallyItems.EMERADIC_CRYSTAL.get(), Tags.Items.GEMS_EMERALD, 100);
        laserCrystalizeRecipe(consumer, ActuallyItems.VOID_CRYSTAL.get(), ItemTags.COALS, 60);
        laserCrystalizeRecipe(consumer, ActuallyItems.ENORI_CRYSTAL.get(), Tags.Items.INGOTS_IRON, 80);

        //Lenses
        laserRecipe(consumer, ActuallyItems.LENS_OF_COLOR.get(), ActuallyItems.LENS.get(), 5000);
        laserRecipe(consumer, ActuallyItems.LENS_OF_DETONATION.get(), ActuallyItems.LENS_OF_COLOR.get(), 5000);
        laserRecipe(consumer, ActuallyItems.LENS_OF_CERTAIN_DEATH.get(), ActuallyItems.LENS_OF_DETONATION.get(), 5000);
        laserRecipe(consumer, ActuallyItems.LENS.get(), ActuallyItems.LENS_OF_CERTAIN_DEATH.get(), 5000);

        //Relays
        laserRecipe(consumer, ActuallyBlocks.LASER_RELAY_FLUIDS.getItem(), ActuallyBlocks.LASER_RELAY.getItem(), 2000);
        laserRecipe(consumer, ActuallyBlocks.LASER_RELAY_ITEM.getItem(), ActuallyBlocks.LASER_RELAY_FLUIDS.getItem(), 2000);
        laserRecipe(consumer, ActuallyBlocks.LASER_RELAY.getItem(), ActuallyBlocks.LASER_RELAY_ITEM.getItem(), 2000);

        //Misc
        laserRecipe(consumer, Items.SOUL_SAND, Tags.Items.SAND, 20000);
        laserRecipe(consumer, Items.LEATHER, Items.ROTTEN_FLESH, 20000);
        laserRecipe(consumer, Items.NETHER_WART, Items.RED_MUSHROOM, 150000);
        laserRecipe(consumer, Items.PRISMARINE_SHARD, Items.QUARTZ, 30000);
        laserRecipe(consumer, ActuallyItems.CRYSTALLIZED_CANOLA_SEED.get(), ActuallyItems.CANOLA_SEEDS.get(), 2000);
        laserRecipe(consumer, ActuallyBlocks.ETHETIC_WHITE_BLOCK.getItem(), Items.QUARTZ_BLOCK, 10);
        laserRecipe(consumer, ActuallyBlocks.ETHETIC_GREEN_BLOCK.getItem(), Items.CHISELED_QUARTZ_BLOCK, 10);

    }

    private void laserRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider output, Ingredient input, int energy) {
        consumer.accept(new LaserRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "laser/" + output.asItem().getRegistryName().getPath()),
            input, energy, output));
    }
    private void laserRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider output, ITag<Item> input, int energy) {
        consumer.accept(new LaserRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "laser/" + output.asItem().getRegistryName().getPath()),
            Ingredient.of(input), energy, output));
    }
    private void laserRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider output, IItemProvider input, int energy) {
        consumer.accept(new LaserRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "laser/" + output.asItem().getRegistryName().getPath()),
            Ingredient.of(input), energy, output));
    }
    private void laserCrystalizeRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider output, Ingredient input, int energy) {
        consumer.accept(new LaserRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "laser/crystalize_" + output.asItem().getRegistryName().getPath()),
            input, energy, output));
    }
    private void laserCrystalizeRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider output, ITag<Item> input, int energy) {
        consumer.accept(new LaserRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "laser/crystalize_" + output.asItem().getRegistryName().getPath()),
            Ingredient.of(input), energy, output));
    }
    private void laserCrystalizeRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider output, IItemProvider input, int energy) {
        consumer.accept(new LaserRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "laser/crystalize_" + output.asItem().getRegistryName().getPath()),
            Ingredient.of(input), energy, output));
    }
}
