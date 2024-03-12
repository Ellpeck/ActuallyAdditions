package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ColorChangeRecipe implements Recipe<Container> {
    public static final String NAME = "color_change";

    private final Ingredient input;
    private final ItemStack output;

    public ColorChangeRecipe(ItemStack output, Ingredient input) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(@Nonnull Container pInv, @Nonnull Level pLevel) {
        return input.test(pInv.getItem(0));
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
    public ItemStack assemble(Container pInv, RegistryAccess pRegistryAccess) {
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
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
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
        private static final Codec<ColorChangeRecipe> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input)
                        )
                        .apply(instance, ColorChangeRecipe::new)
        );

//        @Override
//        public ColorChangeRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
//            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
//            JsonObject resultObject = GsonHelper.getAsJsonObject(pJson, "result");
//            ItemStack result = new ItemStack(GsonHelper.getAsItem(resultObject, "item"));
//
//            return new ColorChangeRecipe(pRecipeId, result, ingredient);
//        }

        @Override
        public Codec<ColorChangeRecipe> codec() {
            return CODEC;
        }

        @Nullable
        @Override
        public ColorChangeRecipe fromNetwork(@Nonnull FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();
            return new ColorChangeRecipe(result, ingredient);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, ColorChangeRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
