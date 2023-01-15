package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PressingRecipe implements IRecipe<IInventory> {
    public static final String NAME = "pressing";
    private final ResourceLocation ID;
    private final Ingredient input;
    private final FluidStack output;

    public PressingRecipe(ResourceLocation ID, Ingredient input, FluidStack output) {
        this.ID = ID;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(@Nonnull IInventory pInv, @Nullable World pLevel) {
        return input.test(pInv.getItem(0));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack assemble(IInventory pInv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public FluidStack getOutput() {
        return this.output;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.PRESSING_RECIPE.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ActuallyRecipes.Types.PRESSING;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PressingRecipe> {
        @Nonnull
        @Override
        public PressingRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "ingredient"));
            JsonObject result = pJson.getAsJsonObject("result");
            ResourceLocation fluidRes = new ResourceLocation(JSONUtils.getAsString(result, "fluid"));
            int fluidAmount = JSONUtils.getAsInt(result, "amount");
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidRes);
            if(fluid == null)
                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
            FluidStack output = new FluidStack(fluid, fluidAmount);

            return new PressingRecipe(pRecipeId, ingredient, output);
        }

        @Nullable
        @Override
        public PressingRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull PacketBuffer pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ResourceLocation fluidRes = new ResourceLocation(pBuffer.readUtf());
            int fluidAmount = pBuffer.readInt();
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidRes);
            if(fluid == null)
                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
            FluidStack output = new FluidStack(fluid, fluidAmount);

            return new PressingRecipe(pRecipeId, ingredient, output);
        }

        @Override
        public void toNetwork(@Nonnull PacketBuffer pBuffer, @Nonnull PressingRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeUtf(pRecipe.output.getFluid().getRegistryName().toString());
            pBuffer.writeInt(pRecipe.output.getAmount());
        }
    }
    public static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation ID;
        private final Ingredient input;
        private final FluidStack output;

        public FinishedRecipe(ResourceLocation ID, Ingredient input, FluidStack output) {
            this.ID = ID;
            this.input = input;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", input.toJson());
            JsonObject result = new JsonObject();
            result.addProperty("fluid", output.getFluid().getRegistryName().toString());
            result.addProperty("amount", output.getAmount());
            pJson.add("result", result);
        }

        @Override
        public ResourceLocation getId() {
            return ID;
        }

        @Override
        public IRecipeSerializer<?> getType() {
            return ActuallyRecipes.PRESSING_RECIPE.get();
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
