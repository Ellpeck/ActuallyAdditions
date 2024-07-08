package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.mod.inventory.gui.FluidDisplay;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class FermentingRecipe implements Recipe<RecipeInput> {
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
        return FluidStack.isSameFluidSameComponents(stack, this.input);
    }

    public boolean matches(FluidStack input, FluidStack output) {
        return FluidStack.isSameFluidSameComponents(input, this.input) && (output.isEmpty() ||
                FluidStack.isSameFluidSameComponents(output, this.output) && input.getAmount() >= this.input.getAmount());
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
    public boolean matches(RecipeInput pInv, Level pLevel) {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack assemble(RecipeInput pInv, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
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
        private static final MapCodec<FermentingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                FluidStack.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                                FluidStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                                Codec.INT.fieldOf("time").forGetter(recipe -> recipe.time)
                        )
                        .apply(instance, FermentingRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, FermentingRecipe> STREAM_CODEC = StreamCodec.of(
                FermentingRecipe.Serializer::toNetwork, FermentingRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<FermentingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FermentingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        @Nullable
        public static FermentingRecipe fromNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer) {
            FluidStack input = FluidStack.STREAM_CODEC.decode(pBuffer);
            FluidStack output = FluidStack.STREAM_CODEC.decode(pBuffer);
            int time = pBuffer.readInt();

            return new FermentingRecipe(input, output, time);
        }

        public static void toNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer, @Nonnull FermentingRecipe pRecipe) {
            FluidStack.STREAM_CODEC.encode(pBuffer, pRecipe.input);
            FluidStack.STREAM_CODEC.encode(pBuffer, pRecipe.output);
            pBuffer.writeInt(pRecipe.time);
        }
    }
}
