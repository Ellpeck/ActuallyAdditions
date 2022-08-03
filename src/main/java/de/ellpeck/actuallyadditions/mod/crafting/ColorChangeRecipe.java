package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ColorChangeRecipe implements IRecipe<IInventory> {
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
    public boolean matches(@Nonnull IInventory pInv, @Nonnull World pLevel) {
        return input.test(pInv.getItem(0));
    }

    public boolean matches(ItemStack stack) {
        return input.test(stack);
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull IInventory pInv) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.COLOR_CHANGE_RECIPE.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ActuallyRecipes.Types.COLOR_CHANGE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ColorChangeRecipe> {
        @Override
        public ColorChangeRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "ingredient"));
            JsonObject resultObject = JSONUtils.getAsJsonObject(pJson, "result");
            ItemStack result = new ItemStack(JSONUtils.getAsItem(resultObject, "item"));

            return new ColorChangeRecipe(pRecipeId, result, ingredient);
        }

        @Nullable
        @Override
        public ColorChangeRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull PacketBuffer pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();
            return new ColorChangeRecipe(pRecipeId, result, ingredient);
        }

        @Override
        public void toNetwork(@Nonnull PacketBuffer pBuffer, ColorChangeRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient itemIngredient;
        private final IItemProvider output;

        public FinishedRecipe(ResourceLocation id, Ingredient itemIngredient, IItemProvider output) {
            this.id = id;
            this.itemIngredient = itemIngredient;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", itemIngredient.toJson());

            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("item", output.asItem().getRegistryName().toString());

            pJson.add("result", resultObject);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getType() {
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
