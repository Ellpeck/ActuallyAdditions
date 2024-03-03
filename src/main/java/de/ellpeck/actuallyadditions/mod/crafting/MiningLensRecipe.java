package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
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

public class MiningLensRecipe implements Recipe<Container>, WeightedEntry {
    public static final String NAME = "mining_lens";

    private final ResourceLocation id;
    private final int weight;
    private final Ingredient input;
    //private final int weight;
    private final ItemStack output;

    public MiningLensRecipe(ResourceLocation id, Ingredient input, int weight, ItemStack output) {
        super();
        this.weight = weight;
        this.input = input;
        //this.weight = weight;
        this.output = output;
        this.id = id;
    }

    public Weight getWeight() {
        return Weight.of(weight);
    }

    public Ingredient getInput() {
        return input;
    }

    public boolean matches(ItemStack test) {
        return input.test(test);
    }

    @Override
    public boolean matches(Container pInv, Level pLevel) {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack assemble(Container pInv, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.MINING_LENS_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.MINING_LENS;
    }

    public static class Serializer implements RecipeSerializer<MiningLensRecipe> {
        @Override
        public MiningLensRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
            int weight = GsonHelper.getAsInt(pJson, "weight");
            JsonObject resultObject = GsonHelper.getAsJsonObject(pJson, "result");
            ItemStack result = new ItemStack(GsonHelper.getAsItem(resultObject, "item"));

            return new MiningLensRecipe(pRecipeId, ingredient, weight, result);
        }

        @Nullable
        @Override
        public MiningLensRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int weight = pBuffer.readInt();
            ItemStack result = pBuffer.readItem();
            return new MiningLensRecipe(pRecipeId, ingredient, weight, result);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, MiningLensRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.weight);
            pBuffer.writeItem(pRecipe.output);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient itemIngredient;
        private final int weight;
        private final ItemLike output;

        public Result(ResourceLocation id, Ingredient itemIngredient, int weight, ItemLike output) {
            this.id = id;
            this.itemIngredient = itemIngredient;
            this.weight = weight;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", itemIngredient.toJson());
            pJson.addProperty("weight", weight);

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
            return ActuallyRecipes.MINING_LENS_RECIPE.get();
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
