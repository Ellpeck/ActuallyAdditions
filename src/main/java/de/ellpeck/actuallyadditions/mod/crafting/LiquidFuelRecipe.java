package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LiquidFuelRecipe implements Recipe<Container> {
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
    public boolean matches(@Nonnull Container pInv,@Nonnull Level pLevel) {
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
    public ItemStack assemble(Container pInv, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.LIQUID_FUEL_RECIPE.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.LIQUID_FUEL.get();
    }

    public static class Serializer implements RecipeSerializer<LiquidFuelRecipe> {
        @Nonnull
        @Override
        public LiquidFuelRecipe fromJson(@Nonnull ResourceLocation pId, JsonObject pJson) {
            JsonObject ingredient = pJson.getAsJsonObject("ingredient");

            ResourceLocation fluidRes = new ResourceLocation(GsonHelper.getAsString(ingredient, "fluid"));
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidRes);
            if (fluid == null)
                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
            int inputAmount = GsonHelper.getAsInt(ingredient, "amount", 50);
            FluidStack input = new FluidStack(fluid, inputAmount);

            JsonObject result = pJson.getAsJsonObject("result");
            int totalEnergy = result.get("total_energy").getAsInt();
            int burnTime = result.get("burn_time").getAsInt();
            return new LiquidFuelRecipe(pId, input, totalEnergy, burnTime);
        }

        @Override
        public LiquidFuelRecipe fromNetwork(@Nonnull ResourceLocation pId, @Nonnull FriendlyByteBuf pBuffer) {
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
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, LiquidFuelRecipe pRecipe) {
            pBuffer.writeUtf(ForgeRegistries.FLUIDS.getKey(pRecipe.fuel.getFluid()).toString());
            pBuffer.writeInt(pRecipe.fuel.getAmount());
            pBuffer.writeInt(pRecipe.totalEnergy);
            pBuffer.writeInt(pRecipe.burnTime);
        }
    }

    public static class Result implements FinishedRecipe {
        private FluidStack fuel;
        private int burnTime;
        private int totalEnergy;
        private ResourceLocation id;

        public Result(ResourceLocation id, FluidStack fuel, int totalEnergy, int burnTime) {
            this.fuel = fuel;
            this.burnTime = burnTime;
            this.totalEnergy = totalEnergy;
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(fuel.getFluid()).toString());
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
        public RecipeSerializer<?> getType() {
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
