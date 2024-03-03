package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.FermentingRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.PressingRecipe;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class MiscMachineRecipeGenerator extends RecipeProvider {
    public MiscMachineRecipeGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Misc " + super.getName();
    }

    @Override
    protected @Nullable CompletableFuture<?> saveAdvancement(CachedOutput output, FinishedRecipe finishedRecipe, JsonObject advancementJson) {
        return null; //Nope...
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new PressingRecipe.Result(folderRecipe("pressing", "canola"),
            Ingredient.of(ActuallyItems.CANOLA.get()), new FluidStack(InitFluids.CANOLA_OIL.get(), 80)));

        consumer.accept(new FermentingRecipe.Result(folderRecipe("fermenting", "refined_canola"),
            new FluidStack(InitFluids.CANOLA_OIL.get(), 80), new FluidStack(InitFluids.REFINED_CANOLA_OIL.get(), 80), 100));
    }

//    private String getItemName(ItemLike item) {
//        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
//    }

    private ResourceLocation folderRecipe(String folder, String recipe) {
        return new ResourceLocation(ActuallyAdditions.MODID, folder + "/" + recipe);
    }
}
