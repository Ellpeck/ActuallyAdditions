package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class CrushingRecipe implements Recipe<RecipeInput> {
    public static String NAME = "crushing";
    protected Ingredient input;
    protected NonNullList<CrushingResult> outputs;

    public CrushingRecipe(Ingredient input, NonNullList<CrushingResult> outputList) {
        this.input = input;
        this.outputs = outputList;
    }

    public CrushingRecipe(Ingredient input, ItemStack outputOne, float chance1, ItemStack outputTwo, float chance2) {
        this(input, createList(new CrushingResult(outputOne, chance1), new CrushingResult(outputTwo, chance2)));
    }

    private static NonNullList<CrushingResult> createList(CrushingResult... results) {
        NonNullList<CrushingResult> list = NonNullList.create();
        for (CrushingResult result : results) {
            list.add(result);
        }
        return list;
    }

    @Override
    public boolean matches(RecipeInput pInv, Level pLevel) {
        return input.test(pInv.getItem(0));
    }

    public boolean matches (ItemStack stack) {
        return input.test(stack);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack assemble(RecipeInput pInv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.outputs.getFirst().stack.copy();
    }

    @Override
    @Nonnull
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.CRUSHING_RECIPE.get();
    }

    @Override
    @Nonnull
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.CRUSHING.get();
    }

    public ItemStack getOutputOne() {
        return this.outputs.getFirst().stack;
    }

    public ItemStack getOutputTwo() {
        return this.outputs.get(1).stack;
    }

    public float getFirstChance() {
        return this.outputs.getFirst().chance;
    }
    public float getSecondChance() {
        return this.outputs.get(1).chance;
    }

    public Ingredient getInput() {
        return this.input;
    }


    public record CrushingResult(ItemStack stack, float chance) {
        public static final CrushingResult EMPTY = new CrushingResult(ItemStack.EMPTY, 0.0F);
    };

    public static class Serializer implements RecipeSerializer<CrushingRecipe> {
        private static final Codec<CrushingResult> RESULT_CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                ItemStack.OPTIONAL_CODEC.fieldOf("result").forGetter(result -> result.stack),
                                Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter(recipe -> recipe.chance)
                        )
                        .apply(instance, CrushingResult::new)
        );

        private static final MapCodec<CrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                                RESULT_CODEC
                                        .listOf()
                                        .fieldOf("result")
                                        .flatXmap(
                                                array -> {
                                                    CrushingResult[] resultArray = array.toArray(CrushingResult[]::new);
                                                    NonNullList<CrushingResult> results = NonNullList.withSize(2, CrushingResult.EMPTY);
                                                    if (resultArray.length < 1) {
                                                        return DataResult.error(() -> "Recipe must contain at least 1 result");
                                                    } else if (resultArray.length > 2) {
                                                        return DataResult.error(() -> "Too many results for crushing recipe. The maximum is: 2");
                                                    } else {
                                                        for (int i = 0; i < resultArray.length; i++) {
                                                            results.set(i, resultArray[i]);
                                                        }
                                                        return DataResult.success(results);
                                                    }
                                                },
                                                DataResult::success
                                        )
                                        .forGetter(recipe -> recipe.outputs)
                        )
                        .apply(instance, CrushingRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> STREAM_CODEC = StreamCodec.of(
                CrushingRecipe.Serializer::toNetwork, CrushingRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<CrushingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

//        @Override
//        @Nonnull
//        public CrushingRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
//            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
//
//            JsonArray resultList = GsonHelper.getAsJsonArray(pJson, "result");
//            if (resultList.size() < 1)
//                throw new IllegalStateException(pRecipeId.toString() + ": Recipe must contain at least 1 result item");
//
//            JsonObject result1 = resultList.get(0).getAsJsonObject();
//            int count1 = GsonHelper.getAsInt(result1, "count", 0);
//            ItemStack output1 = new ItemStack(GsonHelper.getAsItem(result1, "item"), count1);
//            float chance1 = GsonHelper.getAsFloat(result1, "chance");
//
//            ItemStack output2 = ItemStack.EMPTY;
//            float chance2 = 1.0f;
//            if (resultList.size() > 1) {
//                JsonObject result2 = resultList.get(1).getAsJsonObject();
//                int count2 = GsonHelper.getAsInt(result2, "count", 0);
//                output2 = new ItemStack(GsonHelper.getAsItem(result2, "item"), count2);
//                chance2 = GsonHelper.getAsFloat(result2, "chance");
//            }
//
//            return new CrushingRecipe(pRecipeId, ingredient, output1, chance1, output2, chance2);
//        }

        public static CrushingRecipe fromNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(pBuffer);

            int i = pBuffer.readVarInt();

            NonNullList<CrushingResult> nonnulllist = NonNullList.withSize(i, CrushingResult.EMPTY);
            for (int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, new CrushingResult(ItemStack.OPTIONAL_STREAM_CODEC.decode(pBuffer), pBuffer.readFloat()));
            }

            return new CrushingRecipe(ingredient, nonnulllist);
        }

        public static void toNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer, CrushingRecipe pRecipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(pBuffer, pRecipe.input);
            pBuffer.writeVarInt(pRecipe.outputs.size());
            for (CrushingResult result : pRecipe.outputs) {
                ItemStack.OPTIONAL_STREAM_CODEC.encode(pBuffer, result.stack);
                pBuffer.writeFloat(result.chance);
            }
        }
    }
}
