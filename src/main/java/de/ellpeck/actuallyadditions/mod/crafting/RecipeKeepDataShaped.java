package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public class RecipeKeepDataShaped extends ShapedRecipe {
    public static String NAME = "copy_nbt";
    public static final Logger LOGGER = LogManager.getLogger();
    public RecipeKeepDataShaped(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn) {
        super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn);
    }

    public RecipeKeepDataShaped(ShapedRecipe shapedRecipe) {
        super(shapedRecipe.getId(), shapedRecipe.getGroup(), shapedRecipe.getRecipeWidth(), shapedRecipe.getRecipeHeight(), shapedRecipe.getIngredients(), shapedRecipe.getResultItem());
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        final ItemStack craftingResult = super.assemble(inv);
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

        return craftingResult;
    }


    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.KEEP_DATA_SHAPED_RECIPE.get();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeKeepDataShaped> {
        @Nullable
        @Override
        public RecipeKeepDataShaped fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            return new RecipeKeepDataShaped(IRecipeSerializer.SHAPED_RECIPE.fromNetwork(recipeId, buffer));
        }

        @Override
        public RecipeKeepDataShaped fromJson(ResourceLocation recipeId, JsonObject json) {
            try {
                return new RecipeKeepDataShaped(IRecipeSerializer.SHAPED_RECIPE.fromJson(recipeId, json));
            }
            catch (Exception exception) {
                LOGGER.info("Error reading "+ NAME +" Recipe from packet: ", exception);
                throw exception;
            }
        }

        @Override
        public void toNetwork(PacketBuffer buffer, RecipeKeepDataShaped recipe) {
            try {
                IRecipeSerializer.SHAPED_RECIPE.toNetwork(buffer, recipe);
            }
            catch (Exception exception) {
                LOGGER.info("Error writing "+ NAME +" Recipe to packet: ", exception);
                throw exception;
            }
        }
    }
}
