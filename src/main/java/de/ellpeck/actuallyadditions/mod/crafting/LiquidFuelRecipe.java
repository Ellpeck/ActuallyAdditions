package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LiquidFuelRecipe implements IRecipe<IInventory> {
    public static String NAME = "liquid_fuel";
    private FluidStack fuel;
    private int burnTime;
    private int totalEnergy;
    private ResourceLocation id;

    /**
     * Oil generator recipe
     * @param id ResourceLocation of the recipe
     * @param fuel The fluid
     * @param totalEnergy The total power generated.
     * @param burnTime The length the fluid burns for, in ticks.
     */
    public LiquidFuelRecipe(ResourceLocation id, FluidStack fuel, int totalEnergy, int burnTime) {
        this.fuel = fuel;
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
    public boolean matches(@Nonnull IInventory pInv,@Nonnull World pLevel) {
        return false;
    }

    public boolean matches(FluidStack stack) {
        return this.fuel.isFluidEqual(stack);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public int getFuelAmount() {
        return this.fuel.getAmount();
    }

    public FluidStack getFuel() {
        return fuel;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull IInventory pInv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.LIQUID_FUEL_RECIPE.get();
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return ActuallyRecipes.Types.LIQUID_FUEL;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<LiquidFuelRecipe> {
        @Nonnull
        @Override
        public LiquidFuelRecipe fromJson(@Nonnull ResourceLocation pId, JsonObject pJson) {
            JsonObject ingredient = pJson.getAsJsonObject("ingredient");

            ResourceLocation fluidRes = new ResourceLocation(JSONUtils.getAsString(ingredient, "fluid"));
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidRes);
            if (fluid == null)
                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
            int inputAmount = JSONUtils.getAsInt(ingredient, "amount", 50);
            FluidStack input = new FluidStack(fluid, inputAmount);

            JsonObject result = pJson.getAsJsonObject("result");
            int totalEnergy = result.get("total_energy").getAsInt();
            int burnTime = result.get("burn_time").getAsInt();
            return new LiquidFuelRecipe(pId, input, totalEnergy, burnTime);
        }

        @Override
        public LiquidFuelRecipe fromNetwork(@Nonnull ResourceLocation pId, @Nonnull PacketBuffer pBuffer) {
            ResourceLocation inputRes = new ResourceLocation(pBuffer.readUtf());
            int inputAmount = pBuffer.readInt();
            Fluid inputFluid = ForgeRegistries.FLUIDS.getValue(inputRes);
            if(inputFluid == null)
                throw new JsonParseException("Unknown input fluid '" + inputRes + "'");
            FluidStack input = new FluidStack(inputFluid, inputAmount);

            int totalEnergy = pBuffer.readInt();
            int burnTime = pBuffer.readInt();
            return new LiquidFuelRecipe(pId, input, totalEnergy, burnTime);
        }

        @Override
        public void toNetwork(@Nonnull PacketBuffer pBuffer, LiquidFuelRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.fuel.getFluid().getRegistryName().toString());
            pBuffer.writeInt(pRecipe.fuel.getAmount());
            pBuffer.writeInt(pRecipe.totalEnergy);
            pBuffer.writeInt(pRecipe.burnTime);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
        private FluidStack fuel;
        private int burnTime;
        private int totalEnergy;
        private ResourceLocation id;

        public FinishedRecipe(ResourceLocation id, FluidStack fuel, int totalEnergy, int burnTime) {
            this.fuel = fuel;
            this.burnTime = burnTime;
            this.totalEnergy = totalEnergy;
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("fluid", fuel.getFluid().getRegistryName().toString());
            ingredient.addProperty("amount", fuel.getAmount());

            JsonObject result = new JsonObject();
            result.addProperty("total_energy", totalEnergy);
            result.addProperty("burn_time", burnTime);

            pJson.add("ingredient", ingredient);
            pJson.add("result", result);
        }

        @Nonnull
        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getType() {
            return ActuallyRecipes.LIQUID_FUEL_RECIPE.get();
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
