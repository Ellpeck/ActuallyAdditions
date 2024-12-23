package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.NeoForgeExtraCodecs;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class MiningLensRecipe implements Recipe<RecipeInput>, WeightedEntry {
    public static final String NAME = "mining_lens";

    public static Map<Type, Integer> WEIGHT_CACHE = new HashMap<>(Map.of(
            Type.STONE, Integer.MAX_VALUE,
            Type.NETHERRACK, Integer.MAX_VALUE,
            Type.DEEPSLATE, Integer.MAX_VALUE,
            Type.END_STONE, Integer.MAX_VALUE
    ));

    public enum Type {
        STONE,
        NETHERRACK,
        DEEPSLATE,
        END_STONE
    }

    private final int weight;
    private final Type type;
    private final Ingredient input;
    private final ItemStack output;

    public MiningLensRecipe(Ingredient input, int weight, ItemStack output) {
        this.weight = weight;
        this.type = inferType(output);
        this.input = Ingredient.of(getTag());
        this.output = output;
    }

    public MiningLensRecipe(Type type, int weight, ItemStack output) {
        this.weight = weight;
        this.type = type;
        this.input = Ingredient.of(getTag());
        this.output = output;
    }

    public TagKey<Item> getTag() {
        return switch(type) {
            case STONE -> ActuallyTags.Items.STONE_MINING_LENS;
            case NETHERRACK -> ActuallyTags.Items.NETHERRACK_MINING_LENS;
            case DEEPSLATE -> ActuallyTags.Items.DEEPSLATE_MINING_LENS;
            case END_STONE -> ActuallyTags.Items.END_STONE_MINING_LENS;
        };
    }

    public Type getInputType() {
        return type;
    }

    // Used for the fallback Codec to load old mining lens recipes.
    public Type inferType(ItemStack output) {
        String name = BuiltInRegistries.ITEM.getKey(output.getItem()).getPath();

        if (name.contains("deepslate"))
            return Type.DEEPSLATE;
        else if (name.contains("nether"))
            return Type.NETHERRACK;
        else if (name.contains("end"))
            return Type.END_STONE;
        return Type.STONE;
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
    public boolean matches(@Nonnull RecipeInput pInv, @Nonnull Level pLevel) {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeInput pInv, @Nonnull HolderLookup.Provider provider) {
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

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.MINING_LENS.get();
    }

    public static class Serializer implements RecipeSerializer<MiningLensRecipe> {
        // Fallback Codec for old type recipes.
        private static final MapCodec<MiningLensRecipe> OLD_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                                Codec.INT.fieldOf("weight").forGetter(recipe -> recipe.weight),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
                        )
                        .apply(instance, MiningLensRecipe::new));
        // new Codec
        private static final MapCodec<MiningLensRecipe> NEW_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.STRING.xmap(Type::valueOf, Enum::name).fieldOf("input_type").forGetter(recipe -> recipe.type),
                                Codec.INT.fieldOf("weight").forGetter(recipe -> recipe.weight),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
                        )
                        .apply(instance, MiningLensRecipe::new)
        );

        private static final MapCodec<MiningLensRecipe> CODEC = NeoForgeExtraCodecs.mapWithAlternative(NEW_CODEC, OLD_CODEC);
        public static final StreamCodec<RegistryFriendlyByteBuf, MiningLensRecipe> STREAM_CODEC = StreamCodec.of(
                MiningLensRecipe.Serializer::toNetwork, MiningLensRecipe.Serializer::fromNetwork
        );

        @Nonnull
        @Override
        public MapCodec<MiningLensRecipe> codec() {
            return CODEC;
        }

        @Nonnull
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MiningLensRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static MiningLensRecipe fromNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer) {
            Type type = Type.valueOf(pBuffer.readUtf());
            int weight = pBuffer.readInt();
            ItemStack result = ItemStack.STREAM_CODEC.decode(pBuffer);
            return new MiningLensRecipe(type, weight, result);
        }

        public static void toNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer, MiningLensRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.type.name());
            pBuffer.writeInt(pRecipe.weight);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.output);
        }
    }
}
