package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
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
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;


public class LaserRecipe implements IRecipe<IInventory> {
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

    //nah
    @Override
    public boolean matches(IInventory pInv, World pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(IInventory pInv) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.LASER_RECIPE.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ActuallyRecipes.Types.LASER;
    }

    public static Optional<LaserRecipe> getRecipeForStack(ItemStack stack) {
        return ActuallyAdditionsAPI.CONVERSION_LASER_RECIPES.stream().filter(recipe -> recipe.matches(stack)).findFirst();
    }

    public boolean validInput(ItemStack stack) {
        return getRecipeForStack(stack).isPresent();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<LaserRecipe> {
        @Override
        public LaserRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "ingredient"));
            int energy = JSONUtils.getAsInt(pJson, "energy");
            JsonObject resultObject = JSONUtils.getAsJsonObject(pJson, "result");
            ItemStack result = new ItemStack(JSONUtils.getAsItem(resultObject, "item"));

            return new LaserRecipe(pRecipeId, result, ingredient, energy);
        }

        @Nullable
        @Override
        public LaserRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull PacketBuffer pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int energy = pBuffer.readInt();
            ItemStack result = pBuffer.readItem();
            return new LaserRecipe(pRecipeId, result, ingredient, energy);
        }

        @Override
        public void toNetwork(@Nonnull PacketBuffer pBuffer, LaserRecipe pRecipe) {
            pRecipe.itemIngredient.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.energy);
            pBuffer.writeItem(pRecipe.result);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
        private ResourceLocation id;
        private Ingredient itemIngredient;
        private int energy;
        private IItemProvider output;

        public FinishedRecipe(ResourceLocation id, Ingredient itemIngredient, int energy, IItemProvider output) {
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
            resultObject.addProperty("item", output.asItem().getRegistryName().toString());

            pJson.add("result", resultObject);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getType() {
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
