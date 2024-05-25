package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.neoforged.neoforge.attachment.AttachmentUtils;

import javax.annotation.Nullable;

public class RecipeKeepDataShaped extends ShapedRecipe {
    public static String NAME = "copy_nbt";
    public RecipeKeepDataShaped(ShapedRecipe shapedRecipe) {
        super(shapedRecipe.getGroup(), shapedRecipe.category(), shapedRecipe.pattern, shapedRecipe.getResultItem(null));
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
        final ItemStack craftingResult = super.assemble(inv, registryAccess);
        TargetNBTIngredient donorIngredient = null;
        ItemStack datasource = ItemStack.EMPTY;
        NonNullList<Ingredient> ingredients = getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient instanceof TargetNBTIngredient) {
                donorIngredient = (TargetNBTIngredient) ingredient;
                break;
            }
        }
        if (donorIngredient != null && !inv.isEmpty()) {
            for (int i = 0; i < inv.getContainerSize(); i++) {
                final ItemStack item = inv.getItem(i);
                if (!item.isEmpty() && donorIngredient.test(item)) {
                    datasource = item;
                    break;
                }
            }
        }

        if (!datasource.isEmpty() && datasource.hasTag())
            craftingResult.setTag(datasource.getTag().copy());

        if (!datasource.isEmpty())
            AttachmentUtils.copyStackAttachments(datasource, craftingResult);

        return craftingResult;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.KEEP_DATA_SHAPED_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<RecipeKeepDataShaped> {
        private static final Codec<RecipeKeepDataShaped> CODEC = ShapedRecipe.Serializer.CODEC.xmap(RecipeKeepDataShaped::new, $ -> $);

        @Override
        public Codec<RecipeKeepDataShaped> codec() {
            return CODEC;
        }

        @Nullable
        @Override
        public RecipeKeepDataShaped fromNetwork(FriendlyByteBuf buffer) {
            return new RecipeKeepDataShaped(RecipeSerializer.SHAPED_RECIPE.fromNetwork(buffer));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RecipeKeepDataShaped recipe) {
            try {
                RecipeSerializer.SHAPED_RECIPE.toNetwork(buffer, recipe);
            }
            catch (Exception exception) {
                ActuallyAdditions.LOGGER.info("Error writing "+ NAME +" Recipe to packet: ", exception);
                throw exception;
            }
        }
    }
}
