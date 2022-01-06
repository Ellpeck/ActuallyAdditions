package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.PressingRecipe;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.nio.file.Path;
import java.util.function.Consumer;

public class MiscMachineRecipeGenerator extends RecipeProvider {
    public MiscMachineRecipeGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void saveAdvancement(DirectoryCache pCache, JsonObject pAdvancementJson, Path pPath) {
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        consumer.accept(new PressingRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "pressing/canola"), Ingredient.of(ActuallyItems.CANOLA.get()), new FluidStack(InitFluids.CANOLA_OIL.get(), 80)));
    }
}
