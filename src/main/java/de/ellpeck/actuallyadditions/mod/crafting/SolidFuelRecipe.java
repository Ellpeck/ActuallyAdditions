package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class SolidFuelRecipe implements IRecipe<SingleItem> {
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
    public boolean matches(SingleItem pInv, World pLevel) {
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
    public ItemStack assemble(SingleItem pInv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.SOLID_FUEL_RECIPE.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ActuallyRecipes.Types.SOLID_FUEL;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SolidFuelRecipe> {
        @Override
        public SolidFuelRecipe fromJson(ResourceLocation pId, JsonObject pJson) {
            Ingredient itemIngredient = Ingredient.fromJson(pJson.get("item"));
            int totalEnergy = pJson.get("total_energy").getAsInt();
            int burnTime = pJson.get("burn_time").getAsInt();
            return new SolidFuelRecipe(pId, itemIngredient, totalEnergy, burnTime);
        }

        @Override
        public SolidFuelRecipe fromNetwork(ResourceLocation pId, PacketBuffer pBuffer) {
            Ingredient itemIngredient = Ingredient.fromNetwork(pBuffer);
            int totalEnergy = pBuffer.readInt();
            int burnTime = pBuffer.readInt();
            return new SolidFuelRecipe(pId, itemIngredient, totalEnergy, burnTime);
        }

        @Override
        public void toNetwork(PacketBuffer pBuffer, SolidFuelRecipe pRecipe) {
            pRecipe.itemIngredient.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.totalEnergy);
            pBuffer.writeInt(pRecipe.burnTime);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
        private Ingredient itemIngredient;
        private int burnTime;
        private int totalEnergy;
        private ResourceLocation id;

        public FinishedRecipe(ResourceLocation id, Ingredient itemIngredient, int totalEnergy, int burnTime) {
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
        public IRecipeSerializer<?> getType() {
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
