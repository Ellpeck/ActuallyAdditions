package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ColorChangeRecipe implements Recipe<SingleRecipeInput> {
    public static final String NAME = "color_change";

    private final Ingredient input;
    private final ItemStack output;

    public ColorChangeRecipe(ItemStack output, Ingredient input) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(@Nonnull SingleRecipeInput input, @Nonnull Level pLevel) {
        return this.input.test(input.getItem(0));
    }

    public boolean matches(ItemStack stack) {
        return input.test(stack);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    public static Optional<RecipeHolder<ColorChangeRecipe>> getRecipeForStack(ItemStack stack) {
        return ActuallyAdditionsAPI.COLOR_CHANGE_RECIPES.stream().filter(recipe -> recipe.value().matches(stack)).findFirst();
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output.copy();
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.COLOR_CHANGE_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.COLOR_CHANGE.get();
    }

    public static class Serializer implements RecipeSerializer<ColorChangeRecipe> {
        private static final MapCodec<ColorChangeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input)
                        )
                        .apply(instance, ColorChangeRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, ColorChangeRecipe> STREAM_CODEC = StreamCodec.of(
                ColorChangeRecipe.Serializer::toNetwork, ColorChangeRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<ColorChangeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ColorChangeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        @Nullable
        public static ColorChangeRecipe fromNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(pBuffer);

            ItemStack result = ItemStack.STREAM_CODEC.decode(pBuffer);
            return new ColorChangeRecipe(result, ingredient);
        }

        public static void toNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer, ColorChangeRecipe pRecipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(pBuffer, pRecipe.input);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.output);
        }
    }
}
