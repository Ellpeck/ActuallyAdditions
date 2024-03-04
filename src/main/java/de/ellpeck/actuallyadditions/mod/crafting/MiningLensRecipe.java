package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MiningLensRecipe implements Recipe<Container>, WeightedEntry {
    public static final String NAME = "mining_lens";

    private final int weight;
    private final Ingredient input;
    //private final int weight;
    private final ItemStack output;

    public MiningLensRecipe(Ingredient input, int weight, ItemStack output) {
        this.weight = weight;
        this.input = input;
        //this.weight = weight;
        this.output = output;
    }

    public Weight getWeight() {
        return Weight.of(weight);
    }

    public Ingredient getInput() {
        return input;
    }

    public boolean matches(ItemStack test) {
        return input.test(test);
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

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.MINING_LENS_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.MINING_LENS.get();
    }

    public static class Serializer implements RecipeSerializer<MiningLensRecipe> {
        private static final Codec<MiningLensRecipe> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                                Codec.INT.fieldOf("weight").forGetter(recipe -> recipe.weight),
                                ItemStack.RESULT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
                        )
                        .apply(instance, MiningLensRecipe::new)
        );

//        @Override
//        public MiningLensRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
//            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
//            int weight = GsonHelper.getAsInt(pJson, "weight");
//            JsonObject resultObject = GsonHelper.getAsJsonObject(pJson, "result");
//            ItemStack result = new ItemStack(GsonHelper.getAsItem(resultObject, "item"));
//
//            return new MiningLensRecipe(pRecipeId, ingredient, weight, result);
//        }


        @Override
        public Codec<MiningLensRecipe> codec() {
            return CODEC;
        }

        @Nullable
        @Override
        public MiningLensRecipe fromNetwork(@Nonnull FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int weight = pBuffer.readInt();
            ItemStack result = pBuffer.readItem();
            return new MiningLensRecipe(ingredient, weight, result);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, MiningLensRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.weight);
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
