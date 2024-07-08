package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.FermentingRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.PressingRecipe;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.util.NoAdvRecipeOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class MiscMachineRecipeGenerator extends RecipeProvider {
    public MiscMachineRecipeGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public String getName() {
        return "Misc " + super.getName();
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput output) {
        var recipeOutput = new NoAdvRecipeOutput(output);

        recipeOutput.accept(folderRecipe("pressing", "canola"), new PressingRecipe(
                Ingredient.of(ActuallyItems.CANOLA.get()), new FluidStack(InitFluids.CANOLA_OIL.get(), 80)), null);

        recipeOutput.accept(folderRecipe("fermenting", "refined_canola"), new FermentingRecipe(
            new FluidStack(InitFluids.CANOLA_OIL.get(), 80), new FluidStack(InitFluids.REFINED_CANOLA_OIL.get(), 80), 100), null);
    }

//    private String getItemName(ItemLike item) {
//        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
//    }

    private ResourceLocation folderRecipe(String folder, String recipe) {
        return ActuallyAdditions.modLoc(folder + "/" + recipe);
    }
}
