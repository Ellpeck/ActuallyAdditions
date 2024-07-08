package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PressingRecipe implements Recipe<RecipeInput> {
    public static final String NAME = "pressing";
    private final Ingredient input;
    private final FluidStack output;

    public PressingRecipe(Ingredient input, FluidStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(@Nonnull RecipeInput pInv, @Nullable Level pLevel) {
        return input.test(pInv.getItem(0));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public Ingredient getInput() {
        return this.input;
    }

    @Override
    public ItemStack assemble(RecipeInput pInv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public FluidStack getOutput() {
        return this.output;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.PRESSING_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.PRESSING.get();
    }

    public static class Serializer implements RecipeSerializer<PressingRecipe> {
        private static final MapCodec<PressingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                                FluidStack.CODEC.fieldOf("fluid").forGetter(recipe -> recipe.output)
                        )
                        .apply(instance, PressingRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, PressingRecipe> STREAM_CODEC = StreamCodec.of(
                PressingRecipe.Serializer::toNetwork, PressingRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<PressingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, PressingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

//        @Nonnull
//        @Override
//        public PressingRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
//            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
//            JsonObject result = pJson.getAsJsonObject("result");
//            ResourceLocation fluidRes = ResourceLocation.tryParse(GsonHelper.getAsString(result, "fluid"));
//            int fluidAmount = GsonHelper.getAsInt(result, "amount");
//            Fluid fluid = BuiltInRegistries.FLUIDS.getValue(fluidRes);
//            if(fluid == null)
//                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
//            FluidStack output = new FluidStack(fluid, fluidAmount);
//
//            return new PressingRecipe(pRecipeId, ingredient, output);
//        }

        public static PressingRecipe fromNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(pBuffer);
            FluidStack output = FluidStack.STREAM_CODEC.decode(pBuffer);

            return new PressingRecipe(ingredient, output);
        }

        public static void toNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer, @Nonnull PressingRecipe pRecipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(pBuffer, pRecipe.input);
            FluidStack.STREAM_CODEC.encode(pBuffer, pRecipe.output);
        }
    }
}
