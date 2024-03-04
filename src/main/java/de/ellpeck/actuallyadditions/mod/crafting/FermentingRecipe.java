package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.mod.inventory.gui.FluidDisplay;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class FermentingRecipe implements Recipe<Container> {
    public static final String NAME = "fermenting";
    private final FluidStack input;
    private final FluidStack output;
    private final int time;

    private Optional<FluidDisplay> inputDisplay;
    private Optional<FluidDisplay> outputDisplay;

    public FermentingRecipe(FluidStack input, FluidStack output, int timeIn) {
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

    public Optional<FluidDisplay> getInputDisplay() {
        return inputDisplay;
    }

    public void setInputDisplay(FluidDisplay inputDisplay) {
        this.inputDisplay = Optional.of(inputDisplay);
    }

    public Optional<FluidDisplay> getOutputDisplay() {
        return outputDisplay;
    }

    public void setOutputDisplay(FluidDisplay outputDisplay) {
        this.outputDisplay = Optional.of(outputDisplay);
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
    public boolean matches(Container pInv, Level pLevel) {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
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
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.FERMENTING_RECIPE.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.FERMENTING.get();
    }

    public static class Serializer implements RecipeSerializer<FermentingRecipe> {
        private static final Codec<FermentingRecipe> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                FluidStack.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                                FluidStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                                Codec.INT.fieldOf("time").forGetter(recipe -> recipe.time)
                        )
                        .apply(instance, FermentingRecipe::new)
        );

//        @Nonnull
//        @Override
//        public FermentingRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
//            JsonObject ingredient = pJson.getAsJsonObject("ingredient");
//
//            ResourceLocation fluidRes = new ResourceLocation(GsonHelper.getAsString(ingredient, "fluid"));
//            Fluid fluid = BuiltInRegistries.FLUIDS.getValue(fluidRes);
//            if (fluid == null)
//                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
//            int inputAmount = GsonHelper.getAsInt(ingredient, "amount", 80);
//            FluidStack input = new FluidStack(fluid, inputAmount);
//
//            JsonObject result = pJson.getAsJsonObject("result");
//            ResourceLocation fluidOutputRes = new ResourceLocation(GsonHelper.getAsString(result, "fluid"));
//            int outputAmount = GsonHelper.getAsInt(result, "amount");
//            Fluid fluidOutput = BuiltInRegistries.FLUIDS.getValue(fluidOutputRes);
//            if(fluidOutput == null)
//                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
//            FluidStack output = new FluidStack(fluidOutput, outputAmount);
//
//            int time = GsonHelper.getAsInt(pJson, "time", 100);
//
//            return new FermentingRecipe(pRecipeId, input, output, time);
//        }


        @Override
        public Codec<FermentingRecipe> codec() {
            return CODEC;
        }

        @Nullable
        @Override
        public FermentingRecipe fromNetwork(@Nonnull FriendlyByteBuf pBuffer) {
            FluidStack input = FluidStack.readFromPacket(pBuffer);
            FluidStack output = FluidStack.readFromPacket(pBuffer);
            int time = pBuffer.readInt();

            return new FermentingRecipe(input, output, time);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, @Nonnull FermentingRecipe pRecipe) {
            pRecipe.input.writeToPacket(pBuffer);
            pRecipe.output.writeToPacket(pBuffer);
            pBuffer.writeInt(pRecipe.output.getAmount());
            pBuffer.writeInt(pRecipe.time);
        }
    }
}
