package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.FermentingRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.MiningLensRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.PressingRecipe;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.util.function.Consumer;

public class MiningLensGenerator extends RecipeProvider {
    public MiningLensGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void saveAdvancement(DirectoryCache pCache, JsonObject pAdvancementJson, Path pPath) {
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        buildMiningLens(consumer);
    }

    private String getItemName(IItemProvider item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
    }

    private ResourceLocation folderRecipe(String folder, String recipe) {
        return new ResourceLocation(ActuallyAdditions.MODID, folder + "/" + recipe);
    }

    private void buildStoneOre(Consumer<IFinishedRecipe> consumer, int weight, IItemProvider output) {
        buildTagOre(consumer, Tags.Items.STONE, "stone", weight, output);
    }
    private void buildNetherOre(Consumer<IFinishedRecipe> consumer, int weight, IItemProvider output) {
        buildTagOre(consumer, Tags.Items.NETHERRACK, "nether", weight, output);
    }

    private void buildTagOre(Consumer<IFinishedRecipe> consumer, ITag tag, String prefix, int weight, IItemProvider output) {
        consumer.accept(new MiningLensRecipe.FinishedRecipe(
                folderRecipe("mininglens", prefix + "_" + getItemName(output)),
                Ingredient.of(tag),
                weight,
                output
        ));
    }

    private void buildMiningLens(Consumer<IFinishedRecipe> consumer) {
        buildStoneOre(consumer, 5000, Items.COAL_ORE);
        buildStoneOre(consumer, 3000, Items.IRON_ORE);
        buildStoneOre(consumer, 500, Items.GOLD_ORE);
        buildNetherOre(consumer, 500, Items.NETHER_GOLD_ORE);
        buildStoneOre(consumer, 50, Items.DIAMOND_ORE);
        buildStoneOre(consumer, 250, Items.LAPIS_ORE);
        buildStoneOre(consumer, 200, Items.REDSTONE_ORE);
        buildStoneOre(consumer, 30, Items.EMERALD_ORE);
        buildNetherOre(consumer, 3000, Items.NETHER_QUARTZ_ORE);
    }
}
