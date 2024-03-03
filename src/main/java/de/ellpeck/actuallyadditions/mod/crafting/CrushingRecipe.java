package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrushingRecipe implements Recipe<Container> {
    public static String NAME = "crushing";
    private final ResourceLocation id;
    protected Ingredient input;
    protected ItemStack outputOne;
    protected ItemStack outputTwo;
    protected float chance1;
    protected float chance2;

    public CrushingRecipe(ResourceLocation id, Ingredient input, ItemStack outputOne, float chance1, ItemStack outputTwo, float chance2) {
        this.id = id;
        this.input = input;
        this.outputOne = outputOne;
        this.outputTwo = outputTwo;
        this.chance1 = chance1;
        this.chance2 = chance2;
    }

    public CrushingRecipe(Ingredient input, ItemStack outputOne, float chance1, ItemStack outputTwo, float chance2) {
        this.id = new ResourceLocation(ActuallyAdditions.MODID, ForgeRegistries.ITEMS.getKey(input.getItems()[0].getItem()).getPath() + "_crushing");
        this.input = input;
        this.outputOne = outputOne;
        this.outputTwo = outputTwo;
        this.chance1 = chance1;
        this.chance2 = chance2;
    }

    @Override
    public boolean matches(Container pInv, Level pLevel) {
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
    public ItemStack assemble(Container pInv, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return outputOne;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return id;
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
        return this.outputOne;
    }

    public ItemStack getOutputTwo() {
        return this.outputTwo;
    }

    public float getFirstChance() {
        return this.chance1;
    }
    public float getSecondChance() {
        return this.chance2;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public static class Serializer implements RecipeSerializer<CrushingRecipe> {

        @Override
        @Nonnull
        public CrushingRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));

            JsonArray resultList = GsonHelper.getAsJsonArray(pJson, "result");
            if (resultList.size() < 1)
                throw new IllegalStateException(pRecipeId.toString() + ": Recipe must contain at least 1 result item");

            JsonObject result1 = resultList.get(0).getAsJsonObject();
            int count1 = GsonHelper.getAsInt(result1, "count", 0);
            ItemStack output1 = new ItemStack(GsonHelper.getAsItem(result1, "item"), count1);
            float chance1 = GsonHelper.getAsFloat(result1, "chance");

            ItemStack output2 = ItemStack.EMPTY;
            float chance2 = 1.0f;
            if (resultList.size() > 1) {
                JsonObject result2 = resultList.get(1).getAsJsonObject();
                int count2 = GsonHelper.getAsInt(result2, "count", 0);
                output2 = new ItemStack(GsonHelper.getAsItem(result2, "item"), count2);
                chance2 = GsonHelper.getAsFloat(result2, "chance");
            }

            return new CrushingRecipe(pRecipeId, ingredient, output1, chance1, output2, chance2);
        }

        @Override
        public CrushingRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack output1 = pBuffer.readItem();
            ItemStack output2 = pBuffer.readItem();
            float chance1 = pBuffer.readFloat();
            float chance2 = pBuffer.readFloat();

            return new CrushingRecipe(pRecipeId, ingredient, output1, chance1, output2, chance2);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, CrushingRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.outputOne);
            pBuffer.writeItem(pRecipe.outputTwo);
            pBuffer.writeFloat(pRecipe.chance1);
            pBuffer.writeFloat(pRecipe.chance2);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        protected Ingredient input;
        protected ItemLike outputOne;
        protected int countOne;
        protected float outputChance1;
        protected ItemLike outputTwo;
        protected int countTwo;
        protected float outputChance2;

        public Result(ResourceLocation id, Ingredient input, ItemLike outputOne, int countOne, float outputChance1, ItemLike outputTwo, int countTwo, float outputChance2) {
            this.id = id;
            this.countOne = countOne;
            this.countTwo = countTwo;
            this.input = input;
            this.outputOne = outputOne;
            this.outputTwo = outputTwo;
            this.outputChance1 = outputChance1;
            this.outputChance2 = outputChance2;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", input.toJson());

            JsonObject result1 = new JsonObject();
            result1.addProperty("item", ForgeRegistries.ITEMS.getKey(outputOne.asItem()).toString());
            result1.addProperty("count", countOne);
            result1.addProperty("chance", outputChance1);

            JsonObject result2 = new JsonObject();
            result2.addProperty("item", ForgeRegistries.ITEMS.getKey(outputTwo.asItem()).toString());
            result2.addProperty("count", countTwo);
            result2.addProperty("chance", outputChance2);

            JsonArray resultList = new JsonArray();
            resultList.add(result1);
            resultList.add(result2);

            pJson.add("result", resultList);
        }

        @Override
        @Nonnull
        public ResourceLocation getId() {
            return id;
        }

        @Override
        @Nonnull
        public RecipeSerializer<?> getType() {
            return ActuallyRecipes.CRUSHING_RECIPE.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
