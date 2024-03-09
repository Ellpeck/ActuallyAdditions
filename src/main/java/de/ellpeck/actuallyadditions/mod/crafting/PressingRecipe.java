package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PressingRecipe implements Recipe<Container> {
    public static final String NAME = "pressing";
    private final Ingredient input;
    private final FluidStack output;

    public PressingRecipe(Ingredient input, FluidStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(@Nonnull Container pInv, @Nullable Level pLevel) {
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
    public ItemStack assemble(Container pInv, RegistryAccess pRegistryAccess) {
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
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
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
        private static final Codec<PressingRecipe> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                                FluidStack.CODEC.fieldOf("fluid").forGetter(recipe -> recipe.output)
                        )
                        .apply(instance, PressingRecipe::new)
        );

//        @Nonnull
//        @Override
//        public PressingRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
//            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
//            JsonObject result = pJson.getAsJsonObject("result");
//            ResourceLocation fluidRes = new ResourceLocation(GsonHelper.getAsString(result, "fluid"));
//            int fluidAmount = GsonHelper.getAsInt(result, "amount");
//            Fluid fluid = BuiltInRegistries.FLUIDS.getValue(fluidRes);
//            if(fluid == null)
//                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
//            FluidStack output = new FluidStack(fluid, fluidAmount);
//
//            return new PressingRecipe(pRecipeId, ingredient, output);
//        }

        @Override
        public Codec<PressingRecipe> codec() {
            return CODEC;
        }

        @Nullable
        @Override
        public PressingRecipe fromNetwork(@Nonnull FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            FluidStack output = FluidStack.readFromPacket(pBuffer);

            return new PressingRecipe(ingredient, output);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, @Nonnull PressingRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pRecipe.output.writeToPacket(pBuffer);
        }
    }
}
