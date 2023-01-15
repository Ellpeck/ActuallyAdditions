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
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MiningLensRecipe extends WeightedRandom.Item implements IRecipe<IInventory> {
    public static final String NAME = "mining_lens";

    private final ResourceLocation id;
    private final Ingredient input;
    //private final int weight;
    private final ItemStack output;

    public MiningLensRecipe(ResourceLocation id, Ingredient input, int weight, ItemStack output) {
        super(weight);
        this.input = input;
        //this.weight = weight;
        this.output = output;
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public Ingredient getInput() {
        return input;
    }

    public boolean matches(ItemStack test) {
        return input.test(test);
    }

    @Override
    public boolean matches(IInventory pInv, World pLevel) {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack assemble(IInventory pInv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.MINING_LENS_RECIPE.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ActuallyRecipes.Types.MINING_LENS;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MiningLensRecipe> {
        @Override
        public MiningLensRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "ingredient"));
            int weight = JSONUtils.getAsInt(pJson, "weight");
            JsonObject resultObject = JSONUtils.getAsJsonObject(pJson, "result");
            ItemStack result = new ItemStack(JSONUtils.getAsItem(resultObject, "item"));

            return new MiningLensRecipe(pRecipeId, ingredient, weight, result);
        }

        @Nullable
        @Override
        public MiningLensRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull PacketBuffer pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int weight = pBuffer.readInt();
            ItemStack result = pBuffer.readItem();
            return new MiningLensRecipe(pRecipeId, ingredient, weight, result);
        }

        @Override
        public void toNetwork(@Nonnull PacketBuffer pBuffer, MiningLensRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.weight);
            pBuffer.writeItem(pRecipe.output);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient itemIngredient;
        private final int weight;
        private final IItemProvider output;

        public FinishedRecipe(ResourceLocation id, Ingredient itemIngredient, int weight, IItemProvider output) {
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
            resultObject.addProperty("item", output.asItem().getRegistryName().toString());

            pJson.add("result", resultObject);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getType() {
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
