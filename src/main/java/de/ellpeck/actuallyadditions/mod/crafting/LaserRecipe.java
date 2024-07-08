package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Optional;


public class LaserRecipe implements Recipe<RecipeInput> {
    public static String NAME = "laser";
    private ItemStack result;
    private Ingredient itemIngredient;
    private int energy;

    public LaserRecipe(ItemStack result, Ingredient itemIngredient, int energy) {
        this.result = result;
        this.itemIngredient = itemIngredient;
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean matches(ItemStack itemStack, int energyIn) {
        return itemIngredient.test(itemStack) && (energyIn >= energy);
    }

    public boolean matches(ItemStack itemStack) {
        return itemIngredient.test(itemStack);
    }

    public Ingredient getInput() {
        return itemIngredient;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.get());
    }

    //nah
    @Override
    public boolean matches(RecipeInput pInv, Level pLevel) {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack assemble(RecipeInput pInv, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.LASER_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.LASER.get();
    }

    public static Optional<RecipeHolder<LaserRecipe>> getRecipeForStack(ItemStack stack) {
        return ActuallyAdditionsAPI.CONVERSION_LASER_RECIPES.stream().filter(recipe -> recipe.value().matches(stack)).findFirst();
    }

    public boolean validInput(ItemStack stack) {
        return getRecipeForStack(stack).isPresent();
    }

    public static class Serializer implements RecipeSerializer<LaserRecipe> {
        private static final MapCodec<LaserRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.itemIngredient),
                                Codec.INT.fieldOf("energy").forGetter(recipe -> recipe.energy)
                        )
                        .apply(instance, LaserRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, LaserRecipe> STREAM_CODEC = StreamCodec.of(
                LaserRecipe.Serializer::toNetwork, LaserRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<LaserRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LaserRecipe> streamCodec() {
            return STREAM_CODEC;
        }

//        @Override
//        public LaserRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
//            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
//            int energy = GsonHelper.getAsInt(pJson, "energy");
//            JsonObject resultObject = GsonHelper.getAsJsonObject(pJson, "result");
//            ItemStack result = new ItemStack(GsonHelper.getAsItem(resultObject, "item"));
//
//            return new LaserRecipe(pRecipeId, result, ingredient, energy);
//        }

        public static LaserRecipe fromNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(pBuffer);
            int energy = pBuffer.readInt();
            ItemStack result = ItemStack.STREAM_CODEC.decode(pBuffer);
            return new LaserRecipe(result, ingredient, energy);
        }

        public static void toNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer, LaserRecipe pRecipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(pBuffer, pRecipe.itemIngredient);
            pBuffer.writeInt(pRecipe.energy);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.result);
        }
    }
}
