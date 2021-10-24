package de.ellpeck.actuallyadditions.mod.crafting;

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

import javax.annotation.Nullable;

public class CrushingRecipe implements IRecipe<IInventory> {
    public static String NAME = "crushing";
    private ResourceLocation id;
    protected Ingredient input;
    protected ItemStack outputOne;
    protected ItemStack outputTwo;
    protected int outputChance;

    public CrushingRecipe(ResourceLocation id, Ingredient input, ItemStack outputOne, ItemStack outputTwo, int outputChance) {
        this.id = id;
        this.input = input;
        this.outputOne = outputOne;
        this.outputTwo = outputTwo;
        this.outputChance = outputChance;
    }

    public CrushingRecipe(Ingredient input, ItemStack outputOne, ItemStack outputTwo, int outputChance) {
        this.id = new ResourceLocation(ActuallyAdditions.MODID, input.getItems()[0].getItem().getRegistryName().getPath() + "_crushing");
        this.input = input;
        this.outputOne = outputOne;
        this.outputTwo = outputTwo;
        this.outputChance = outputChance;
    }

    @Override
    public boolean matches(IInventory pInv, World pLevel) {
        return input.test(pInv.getItem(0));
    }

    @Override
    public ItemStack assemble(IInventory pInv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return outputOne;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.CRUSHING_RECIPE.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ActuallyRecipes.Types.CRUSHING;
    }

    public ItemStack getOutputOne() {
        return this.outputOne;
    }

    public ItemStack getOutputTwo() {
        return this.outputTwo;
    }

    public int getSecondChance() {
        return this.outputChance;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrushingRecipe> {

        @Override
        public CrushingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "input"));
            ItemStack output1 = new ItemStack(JSONUtils.getAsItem(pJson, "output_one"));
            ItemStack output2 = new ItemStack(JSONUtils.getAsItem(pJson, "output_two"));
            int chance = JSONUtils.getAsInt(pJson, "second_chance");

            return new CrushingRecipe(pRecipeId, ingredient, output1, output2, chance);
        }

        @Nullable
        @Override
        public CrushingRecipe fromNetwork(ResourceLocation pRecipeId, PacketBuffer pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack output1 = pBuffer.readItem();
            ItemStack output2 = pBuffer.readItem();
            int chance = pBuffer.readInt();

            return new CrushingRecipe(pRecipeId, ingredient, output1, output2, chance);
        }

        @Override
        public void toNetwork(PacketBuffer pBuffer, CrushingRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.outputOne);
            pBuffer.writeItem(pRecipe.outputTwo);
            pBuffer.writeInt(pRecipe.outputChance);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
        private ResourceLocation id;
        protected Ingredient input;
        protected IItemProvider outputOne;
        protected IItemProvider outputTwo;
        protected int outputChance;

        public FinishedRecipe(ResourceLocation id, Ingredient input, IItemProvider outputOne, IItemProvider outputTwo, int outputChance) {
            this.id = id;
            this.input = input;
            this.outputOne = outputOne;
            this.outputTwo = outputTwo;
            this.outputChance = outputChance;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input", input.toJson());
            pJson.addProperty("output_one", outputOne.asItem().getRegistryName().toString());
            pJson.addProperty("output_two", outputTwo.asItem().getRegistryName().toString());
            pJson.addProperty("second_chance", outputChance);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
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
