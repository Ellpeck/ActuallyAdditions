package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class SolidFuelRecipe implements Recipe<SingleRecipeInput> {
    public static String NAME = "solid_fuel";
    private final Ingredient itemIngredient;
    private final int burnTime;
    private final int totalEnergy;

    public SolidFuelRecipe(Ingredient itemIngredient, int totalEnergy, int burnTime) {
        this.itemIngredient = itemIngredient;
        this.burnTime = burnTime;
        this.totalEnergy = totalEnergy;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getTotalEnergy() {
        return totalEnergy;
    }

    @Override
    public boolean matches(SingleRecipeInput pInv, @Nonnull Level pLevel) {
        return itemIngredient.test(pInv.getItem(0));
    }

    public boolean matches(ItemStack stack) {
        return this.itemIngredient.test(stack);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull SingleRecipeInput pInv, @Nonnull HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@Nonnull HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.SOLID_FUEL_RECIPE.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.SOLID_FUEL.get();
    }

    public static class Serializer implements RecipeSerializer<SolidFuelRecipe> {
        private static final MapCodec<SolidFuelRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("item").forGetter(recipe -> recipe.itemIngredient),
                                Codec.INT.fieldOf("total_energy").forGetter(recipe -> recipe.totalEnergy),
                                Codec.INT.fieldOf("burn_time").forGetter(recipe -> recipe.burnTime)
                        )
                        .apply(instance, SolidFuelRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, SolidFuelRecipe> STREAM_CODEC = StreamCodec.of(
                SolidFuelRecipe.Serializer::toNetwork, SolidFuelRecipe.Serializer::fromNetwork
        );

        @Nonnull
        @Override
        public MapCodec<SolidFuelRecipe> codec() {
            return CODEC;
        }

        @Nonnull
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SolidFuelRecipe> streamCodec() {
            return STREAM_CODEC;
        }

//        @Override
//        public SolidFuelRecipe fromJson(ResourceLocation pId, JsonObject pJson) {
//            Ingredient itemIngredient = Ingredient.fromJson(pJson.get("item"));
//            int totalEnergy = pJson.get("total_energy").getAsInt();
//            int burnTime = pJson.get("burn_time").getAsInt();
//            return new SolidFuelRecipe(pId, itemIngredient, totalEnergy, burnTime);
//        }

        public static SolidFuelRecipe fromNetwork(RegistryFriendlyByteBuf pBuffer) {
            Ingredient itemIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(pBuffer);
            int totalEnergy = pBuffer.readInt();
            int burnTime = pBuffer.readInt();
            return new SolidFuelRecipe(itemIngredient, totalEnergy, burnTime);
        }

        public static void toNetwork(RegistryFriendlyByteBuf pBuffer, SolidFuelRecipe pRecipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(pBuffer, pRecipe.itemIngredient);
            pBuffer.writeInt(pRecipe.totalEnergy);
            pBuffer.writeInt(pRecipe.burnTime);
        }
    }
}
