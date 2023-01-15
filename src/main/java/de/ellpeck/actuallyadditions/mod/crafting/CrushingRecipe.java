package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrushingRecipe implements IRecipe<IInventory> {
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
        this.id = new ResourceLocation(ActuallyAdditions.MODID, input.getItems()[0].getItem().getRegistryName().getPath() + "_crushing");
        this.input = input;
        this.outputOne = outputOne;
        this.outputTwo = outputTwo;
        this.chance1 = chance1;
        this.chance2 = chance2;
    }

    @Override
    public boolean matches(IInventory pInv, World pLevel) {
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
    public ItemStack assemble(@Nonnull IInventory pInv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getResultItem() {
        return outputOne;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.CRUSHING_RECIPE.get();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return ActuallyRecipes.Types.CRUSHING;
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

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrushingRecipe> {

        @Override
        @Nonnull
        public CrushingRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "ingredient"));

            JsonArray resultList = JSONUtils.getAsJsonArray(pJson, "result");
            if (resultList.size() < 1)
                throw new IllegalStateException(pRecipeId.toString() + ": Recipe must contain at least 1 result item");

            JsonObject result1 = resultList.get(0).getAsJsonObject();
            int count1 = JSONUtils.getAsInt(result1, "count", 0);
            ItemStack output1 = new ItemStack(JSONUtils.getAsItem(result1, "item"), count1);
            float chance1 = JSONUtils.getAsFloat(result1, "chance");

            ItemStack output2 = ItemStack.EMPTY;
            float chance2 = 1.0f;
            if (resultList.size() > 1) {
                JsonObject result2 = resultList.get(1).getAsJsonObject();
                int count2 = JSONUtils.getAsInt(result2, "count", 0);
                output2 = new ItemStack(JSONUtils.getAsItem(result2, "item"), count2);
                chance2 = JSONUtils.getAsFloat(result2, "chance");
            }

            return new CrushingRecipe(pRecipeId, ingredient, output1, chance1, output2, chance2);
        }

        @Override
        public CrushingRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull PacketBuffer pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack output1 = pBuffer.readItem();
            ItemStack output2 = pBuffer.readItem();
            float chance1 = pBuffer.readFloat();
            float chance2 = pBuffer.readFloat();

            return new CrushingRecipe(pRecipeId, ingredient, output1, chance1, output2, chance2);
        }

        @Override
        public void toNetwork(@Nonnull PacketBuffer pBuffer, CrushingRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.outputOne);
            pBuffer.writeItem(pRecipe.outputTwo);
            pBuffer.writeFloat(pRecipe.chance1);
            pBuffer.writeFloat(pRecipe.chance2);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        protected Ingredient input;
        protected IItemProvider outputOne;
        protected int countOne;
        protected float outputChance1;
        protected IItemProvider outputTwo;
        protected int countTwo;
        protected float outputChance2;

        public FinishedRecipe(ResourceLocation id, Ingredient input, IItemProvider outputOne, int countOne, float outputChance1, IItemProvider outputTwo, int countTwo, float outputChance2) {
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
            result1.addProperty("item", outputOne.asItem().getRegistryName().toString());
            result1.addProperty("count", countOne);
            result1.addProperty("chance", outputChance1);

            JsonObject result2 = new JsonObject();
            result2.addProperty("item", outputTwo.asItem().getRegistryName().toString());
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
        public IRecipeSerializer<?> getType() {
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
