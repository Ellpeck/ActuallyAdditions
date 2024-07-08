package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class MiningLensRecipe implements Recipe<RecipeInput>, WeightedEntry {
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
    public boolean matches(RecipeInput pInv, Level pLevel) {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack assemble(RecipeInput pInv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
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
        private static final MapCodec<MiningLensRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                                Codec.INT.fieldOf("weight").forGetter(recipe -> recipe.weight),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
                        )
                        .apply(instance, MiningLensRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, MiningLensRecipe> STREAM_CODEC = StreamCodec.of(
                MiningLensRecipe.Serializer::toNetwork, MiningLensRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<MiningLensRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MiningLensRecipe> streamCodec() {
            return STREAM_CODEC;
        }

//        @Override
//        public MiningLensRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
//            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
//            int weight = GsonHelper.getAsInt(pJson, "weight");
//            JsonObject resultObject = GsonHelper.getAsJsonObject(pJson, "result");
//            ItemStack result = new ItemStack(GsonHelper.getAsItem(resultObject, "item"));
//
//            return new MiningLensRecipe(pRecipeId, ingredient, weight, result);
//        }

        public static MiningLensRecipe fromNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(pBuffer);
            int weight = pBuffer.readInt();
            ItemStack result = ItemStack.STREAM_CODEC.decode(pBuffer);
            return new MiningLensRecipe(ingredient, weight, result);
        }

        public static void toNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer, MiningLensRecipe pRecipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(pBuffer, pRecipe.input);
            pBuffer.writeInt(pRecipe.weight);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.output);
        }
    }
}
