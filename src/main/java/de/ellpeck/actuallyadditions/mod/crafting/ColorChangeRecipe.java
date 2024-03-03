package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ColorChangeRecipe implements Recipe<Container> {
    public static final String NAME = "color_change";

    private final Ingredient input;
    private final ItemStack output;
    private final ResourceLocation id;

    public ColorChangeRecipe(ResourceLocation id, ItemStack output, Ingredient input) {
        this.input = input;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(@Nonnull Container pInv, @Nonnull Level pLevel) {
        return input.test(pInv.getItem(0));
    }

    public boolean matches(ItemStack stack) {
        return input.test(stack);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack assemble(Container pInv, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    public static Optional<ColorChangeRecipe> getRecipeForStack(ItemStack stack) {
        return ActuallyAdditionsAPI.COLOR_CHANGE_RECIPES.stream().filter(recipe -> recipe.matches(stack)).findFirst();
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.COLOR_CHANGE_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.COLOR_CHANGE;
    }

    public static class Serializer implements RecipeSerializer<ColorChangeRecipe> {
        @Override
        public ColorChangeRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
            JsonObject resultObject = GsonHelper.getAsJsonObject(pJson, "result");
            ItemStack result = new ItemStack(GsonHelper.getAsItem(resultObject, "item"));

            return new ColorChangeRecipe(pRecipeId, result, ingredient);
        }

        @Nullable
        @Override
        public ColorChangeRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();
            return new ColorChangeRecipe(pRecipeId, result, ingredient);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, ColorChangeRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient itemIngredient;
        private final ItemLike output;

        public Result(ResourceLocation id, Ingredient itemIngredient, ItemLike output) {
            this.id = id;
            this.itemIngredient = itemIngredient;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", itemIngredient.toJson());

            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("item", ForgeRegistries.ITEMS.getKey(output.asItem()).toString());

            pJson.add("result", resultObject);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ActuallyRecipes.COLOR_CHANGE_RECIPE.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
