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


public class LaserRecipe implements Recipe<Container> {
    public static String NAME = "laser";
    private ItemStack result;
    private Ingredient itemIngredient;
    private int energy;
    private ResourceLocation id;

    public LaserRecipe(ResourceLocation id, ItemStack result, Ingredient itemIngredient, int energy) {
        this.result = result;
        this.itemIngredient = itemIngredient;
        this.energy = energy;
        this.id = id;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean matches(ItemStack itemStack, int energyIn) {
        return itemIngredient.test(itemStack) && (energyIn >= energy);
    }

    public boolean matches(ItemStack itemStack) {
        return itemIngredient.test(itemStack);
    }

    public Ingredient getInput() {
        return itemIngredient;
    }

    //nah
    @Override
    public boolean matches(Container pInv, Level pLevel) {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack assemble(Container pInv, RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.LASER_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.LASER;
    }

    public static Optional<LaserRecipe> getRecipeForStack(ItemStack stack) {
        return ActuallyAdditionsAPI.CONVERSION_LASER_RECIPES.stream().filter(recipe -> recipe.matches(stack)).findFirst();
    }

    public boolean validInput(ItemStack stack) {
        return getRecipeForStack(stack).isPresent();
    }

    public static class Serializer implements RecipeSerializer<LaserRecipe> {
        @Override
        public LaserRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
            int energy = GsonHelper.getAsInt(pJson, "energy");
            JsonObject resultObject = GsonHelper.getAsJsonObject(pJson, "result");
            ItemStack result = new ItemStack(GsonHelper.getAsItem(resultObject, "item"));

            return new LaserRecipe(pRecipeId, result, ingredient, energy);
        }

        @Nullable
        @Override
        public LaserRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int energy = pBuffer.readInt();
            ItemStack result = pBuffer.readItem();
            return new LaserRecipe(pRecipeId, result, ingredient, energy);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, LaserRecipe pRecipe) {
            pRecipe.itemIngredient.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.energy);
            pBuffer.writeItem(pRecipe.result);
        }
    }

    public static class Result implements FinishedRecipe {
        private ResourceLocation id;
        private Ingredient itemIngredient;
        private int energy;
        private ItemLike output;

        public Result(ResourceLocation id, Ingredient itemIngredient, int energy, ItemLike output) {
            this.id = id;
            this.itemIngredient = itemIngredient;
            this.energy = energy;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", itemIngredient.toJson());
            pJson.addProperty("energy", energy);

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
            return ActuallyRecipes.LASER_RECIPE.get();
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
