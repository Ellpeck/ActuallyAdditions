package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
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


public class LaserRecipe implements Recipe<Container> {
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
    public boolean matches(Container pInv, Level pLevel) {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack assemble(Container pInv, RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
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
        private static final Codec<LaserRecipe> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                ItemStack.RESULT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.itemIngredient),
                                Codec.INT.fieldOf("energy").forGetter(recipe -> recipe.energy)
                        )
                        .apply(instance, LaserRecipe::new)
        );

//        @Override
//        public LaserRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
//            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
//            int energy = GsonHelper.getAsInt(pJson, "energy");
//            JsonObject resultObject = GsonHelper.getAsJsonObject(pJson, "result");
//            ItemStack result = new ItemStack(GsonHelper.getAsItem(resultObject, "item"));
//
//            return new LaserRecipe(pRecipeId, result, ingredient, energy);
//        }


        @Override
        public Codec<LaserRecipe> codec() {
            return CODEC;
        }

        @Nullable
        @Override
        public LaserRecipe fromNetwork(@Nonnull FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int energy = pBuffer.readInt();
            ItemStack result = pBuffer.readItem();
            return new LaserRecipe(result, ingredient, energy);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, LaserRecipe pRecipe) {
            pRecipe.itemIngredient.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.energy);
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
