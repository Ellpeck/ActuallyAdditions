package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.MapCodec;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class RecipeKeepDataShaped extends ShapedRecipe {
    public static String NAME = "copy_nbt";
    public RecipeKeepDataShaped(ShapedRecipe shapedRecipe) {
        super(shapedRecipe.getGroup(), shapedRecipe.category(), shapedRecipe.pattern, shapedRecipe.getResultItem(null));
    }

    @Override
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider registries) {
        final ItemStack craftingResult = super.assemble(inv, registries);
        TargetNBTIngredient donorIngredient = null;
        ItemStack datasource = ItemStack.EMPTY;
        NonNullList<Ingredient> ingredients = getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getCustomIngredient() instanceof TargetNBTIngredient) {
                donorIngredient = (TargetNBTIngredient)ingredient.getCustomIngredient();
                break;
            }
        }
        if (donorIngredient != null && !inv.isEmpty()) {
            for (int i = 0; i < inv.size(); i++) {
                final ItemStack item = inv.getItem(i);
                if (!item.isEmpty() && donorIngredient.test(item)) {
                    datasource = item;
                    break;
                }
            }
        }

        if (!datasource.isEmpty() && !datasource.getComponents().isEmpty())
            craftingResult.applyComponents(datasource.getComponents());

        return craftingResult;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.KEEP_DATA_SHAPED_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<RecipeKeepDataShaped> {
        private static final MapCodec<RecipeKeepDataShaped> CODEC = ShapedRecipe.Serializer.CODEC.xmap(RecipeKeepDataShaped::new, $ -> $);
        public static final StreamCodec<RegistryFriendlyByteBuf, RecipeKeepDataShaped> STREAM_CODEC = StreamCodec.of(
                RecipeKeepDataShaped.Serializer::toNetwork, RecipeKeepDataShaped.Serializer::fromNetwork
        );

        @Override
        public MapCodec<RecipeKeepDataShaped> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RecipeKeepDataShaped> streamCodec() {
            return STREAM_CODEC;
        }

        public static RecipeKeepDataShaped fromNetwork(RegistryFriendlyByteBuf buffer) {
            return new RecipeKeepDataShaped(RecipeSerializer.SHAPED_RECIPE.streamCodec().decode(buffer));
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, RecipeKeepDataShaped recipe) {
            try {
                RecipeSerializer.SHAPED_RECIPE.streamCodec().encode(buffer, recipe);
            }
            catch (Exception exception) {
                ActuallyAdditions.LOGGER.info("Error writing "+ NAME +" Recipe to packet: ", exception);
                throw exception;
            }
        }
    }
}
