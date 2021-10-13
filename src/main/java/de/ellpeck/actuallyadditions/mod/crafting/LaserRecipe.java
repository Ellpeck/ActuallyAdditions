package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;


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

    //nah
    @Override
    public boolean matches(IInventory pInv, World pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(IInventory pInv) {
        return null;
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

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<LaserRecipe> {

        @Override
        public LaserRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "ingredient"));
            int energy = JSONUtils.getAsInt(pJson, "energy");
            ItemStack result = new ItemStack(JSONUtils.getAsItem(pJson, "result"));

            return new LaserRecipe(pRecipeId, result, ingredient, energy);
        }

        @Nullable
        @Override
        public LaserRecipe fromNetwork(ResourceLocation pRecipeId, PacketBuffer pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int energy = pBuffer.readInt();
            ItemStack result = pBuffer.readItem();
            return new LaserRecipe(pRecipeId, result, ingredient, energy);
        }

        @Override
        public void toNetwork(PacketBuffer pBuffer, LaserRecipe pRecipe) {
            pRecipe.itemIngredient.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.energy);
            pBuffer.writeItem(pRecipe.result);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
        private ResourceLocation id;
        private Ingredient itemIngredient;
        private int energy;
        private ItemStack output;

        public FinishedRecipe(ResourceLocation id, Ingredient itemIngredient, int energy, ItemStack output) {
            this.id = id;
            this.itemIngredient = itemIngredient;
            this.energy = energy;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input", itemIngredient.toJson());
            pJson.addProperty("energy", energy);
            pJson.addProperty("output", Registry.ITEM.getKey(output.getItem()).toString());
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
