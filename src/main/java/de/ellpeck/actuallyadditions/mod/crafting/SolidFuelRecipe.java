package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class SolidFuelRecipe implements Recipe<SingleItem> {
    public static String NAME = "solid_fuel";
    private Ingredient itemIngredient;
    private int burnTime;
    private int totalEnergy;
    private ResourceLocation id;

    public SolidFuelRecipe(ResourceLocation id, Ingredient itemIngredient, int totalEnergy, int burnTime) {
        this.itemIngredient = itemIngredient;
        this.burnTime = burnTime;
        this.totalEnergy = totalEnergy;
        this.id = id;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getTotalEnergy() {
        return totalEnergy;
    }

    @Override
    public boolean matches(SingleItem pInv, Level pLevel) {
        return itemIngredient.test(pInv.getItem());
    }

    public boolean matches(ItemStack stack) {
        return this.itemIngredient.test(stack);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack assemble(SingleItem pInv, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.SOLID_FUEL_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.SOLID_FUEL;
    }

    public static class Serializer implements RecipeSerializer<SolidFuelRecipe> {
        @Override
        public SolidFuelRecipe fromJson(ResourceLocation pId, JsonObject pJson) {
            Ingredient itemIngredient = Ingredient.fromJson(pJson.get("item"));
            int totalEnergy = pJson.get("total_energy").getAsInt();
            int burnTime = pJson.get("burn_time").getAsInt();
            return new SolidFuelRecipe(pId, itemIngredient, totalEnergy, burnTime);
        }

        @Override
        public SolidFuelRecipe fromNetwork(ResourceLocation pId, FriendlyByteBuf pBuffer) {
            Ingredient itemIngredient = Ingredient.fromNetwork(pBuffer);
            int totalEnergy = pBuffer.readInt();
            int burnTime = pBuffer.readInt();
            return new SolidFuelRecipe(pId, itemIngredient, totalEnergy, burnTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SolidFuelRecipe pRecipe) {
            pRecipe.itemIngredient.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.totalEnergy);
            pBuffer.writeInt(pRecipe.burnTime);
        }
    }

    public static class Result implements FinishedRecipe {
        private Ingredient itemIngredient;
        private int burnTime;
        private int totalEnergy;
        private ResourceLocation id;

        public Result(ResourceLocation id, Ingredient itemIngredient, int totalEnergy, int burnTime) {
            this.itemIngredient = itemIngredient;
            this.burnTime = burnTime;
            this.totalEnergy = totalEnergy;
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("item", itemIngredient.toJson());
            pJson.addProperty("total_energy", totalEnergy);
            pJson.addProperty("burn_time", burnTime);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ActuallyRecipes.SOLID_FUEL_RECIPE.get();
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
