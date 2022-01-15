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
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FermentingRecipe implements IRecipe<IInventory> {
    public static final String NAME = "fermenting";
    private final ResourceLocation ID;
    private final FluidStack input;
    private final FluidStack output;
    private int time;

    public FermentingRecipe(ResourceLocation ID, FluidStack input, FluidStack output, int timeIn) {
        this.ID = ID;
        this.input = input;
        this.output = output;
        this.time = timeIn;
    }

    public boolean matches(FluidStack stack) {
        return stack.isFluidEqual(this.input);
    }

    public boolean matches(FluidStack input, FluidStack output) {
        return input.isFluidEqual(this.input) && (output.isEmpty() || output.isFluidEqual(this.output) && input.getAmount() >= this.input.getAmount());
    }

    public int getTime() {
        return this.time;
    }

    public FluidStack getOutput() {
        return output;
    }

    public FluidStack getInput() {
        return input;
    }

    @Override
    public boolean matches(IInventory pInv, World pLevel) {
        return false;
    }

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
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.FERMENTING_RECIPE.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ActuallyRecipes.Types.FERMENTING;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FermentingRecipe> {
        @Nonnull
        @Override
        public FermentingRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            JsonObject ingredient = pJson.getAsJsonObject("ingredient");

            ResourceLocation fluidRes = new ResourceLocation(JSONUtils.getAsString(ingredient, "fluid"));
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidRes);
            if (fluid == null)
                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
            int inputAmount = JSONUtils.getAsInt(ingredient, "amount", 80);
            FluidStack input = new FluidStack(fluid, inputAmount);

            JsonObject result = pJson.getAsJsonObject("result");
            ResourceLocation fluidOutputRes = new ResourceLocation(JSONUtils.getAsString(result, "fluid"));
            int outputAmount = JSONUtils.getAsInt(result, "amount");
            Fluid fluidOutput = ForgeRegistries.FLUIDS.getValue(fluidOutputRes);
            if(fluidOutput == null)
                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
            FluidStack output = new FluidStack(fluidOutput, outputAmount);

            int time = JSONUtils.getAsInt(pJson, "time", 100);

            return new FermentingRecipe(pRecipeId, input, output, time);
        }

        @Nullable
        @Override
        public FermentingRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull PacketBuffer pBuffer) {
            ResourceLocation inputRes = new ResourceLocation(pBuffer.readUtf());
            int inputAmount = pBuffer.readInt();
            Fluid inputFluid = ForgeRegistries.FLUIDS.getValue(inputRes);
            if(inputFluid == null)
                throw new JsonParseException("Unknown input fluid '" + inputRes + "'");
            FluidStack input = new FluidStack(inputFluid, inputAmount);

            ResourceLocation outputRes = new ResourceLocation(pBuffer.readUtf());
            int outputAmount = pBuffer.readInt();
            Fluid outputFluid = ForgeRegistries.FLUIDS.getValue(outputRes);
            if(outputFluid == null)
                throw new JsonParseException("Unknown output fluid '" + outputRes + "'");
            FluidStack output = new FluidStack(outputFluid, outputAmount);

            int time = pBuffer.readInt();

            return new FermentingRecipe(pRecipeId, input, output, time);
        }

        @Override
        public void toNetwork(@Nonnull PacketBuffer pBuffer, @Nonnull FermentingRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.input.getFluid().getRegistryName().toString());
            pBuffer.writeInt(pRecipe.input.getAmount());
            pBuffer.writeUtf(pRecipe.output.getFluid().getRegistryName().toString());
            pBuffer.writeInt(pRecipe.output.getAmount());
            pBuffer.writeInt(pRecipe.time);
        }
    }
    public static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation ID;
        private final FluidStack input;
        private final FluidStack output;
        private final int time;

        public FinishedRecipe(ResourceLocation ID, FluidStack input, FluidStack output, int timeIn) {
            this.ID = ID;
            this.input = input;
            this.output = output;
            this.time = timeIn;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("fluid", input.getFluid().getRegistryName().toString());
            ingredient.addProperty("amount", input.getAmount());

            JsonObject result = new JsonObject();
            result.addProperty("fluid", output.getFluid().getRegistryName().toString());
            result.addProperty("amount", output.getAmount());

            pJson.add("ingredient", ingredient);
            pJson.add("result", result);
            pJson.addProperty("time", time);
        }

        @Override
        public ResourceLocation getId() {
            return ID;
        }

        @Override
        public IRecipeSerializer<?> getType() {
            return ActuallyRecipes.FERMENTING_RECIPE.get();
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
