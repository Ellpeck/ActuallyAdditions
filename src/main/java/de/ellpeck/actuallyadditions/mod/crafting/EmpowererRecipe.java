package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EmpowererRecipe implements Recipe<Container> {
    public static String NAME = "empowering";
    private final ResourceLocation id;
    protected final Ingredient input;
    protected final ItemStack output;

    protected final Ingredient modifier1;
    protected final Ingredient modifier2;
    protected final Ingredient modifier3;
    protected final Ingredient modifier4;

    protected final int energyPerStand;
    protected final int particleColor;
    protected final int time;

    public EmpowererRecipe(ResourceLocation id, ItemStack output, Ingredient input, Ingredient modifier1, Ingredient modifier2, Ingredient modifier3, Ingredient modifier4, int energyPerStand, int particleColor, int time) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.modifier1 = modifier1;
        this.modifier2 = modifier2;
        this.modifier3 = modifier3;
        this.modifier4 = modifier4;
        this.energyPerStand = energyPerStand;
        this.particleColor = particleColor;
        this.time = time;
    }

    public boolean matches(ItemStack base, ItemStack stand1, ItemStack stand2, ItemStack stand3, ItemStack stand4) {
        if (!input.test(base) || stand1.isEmpty() || stand2.isEmpty() || stand3.isEmpty() || stand4.isEmpty())
            return false;
        List<Ingredient> matches = new ArrayList<>();
        ItemStack[] stacks = {stand1, stand2, stand3, stand4};
        boolean[] unused = {true, true, true, true};
        for (ItemStack s : stacks) {
            if (unused[0] && this.modifier1.test(s)) {
                matches.add(this.modifier1);
                unused[0] = false;
            } else if (unused[1] && this.modifier2.test(s)) {
                matches.add(this.modifier2);
                unused[1] = false;
            } else if (unused[2] && this.modifier3.test(s)) {
                matches.add(this.modifier3);
                unused[2] = false;
            } else if (unused[3] && this.modifier4.test(s)) {
                matches.add(this.modifier4);
                unused[3] = false;
            }
        }

        return matches.size() == 4;
    }

    @Override
    public boolean matches(@Nonnull Container pInv, @Nonnull Level pLevel) {
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack assemble(@Nonnull Container pInv) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nonnull
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.EMPOWERING_RECIPE.get();
    }

    @Override
    @Nonnull
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.EMPOWERING;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public Ingredient getStandOne() {
        return this.modifier1;
    }

    public Ingredient getStandTwo() {
        return this.modifier2;
    }

    public Ingredient getStandThree() {
        return this.modifier3;
    }

    public Ingredient getStandFour() {
        return this.modifier4;
    }

    public int getTime() {
        return this.time;
    }

    public int getEnergyPerStand() {
        return this.energyPerStand;
    }

    public int getParticleColors() {
        return this.particleColor;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<EmpowererRecipe> {
        @Override
        @Nonnull
        public EmpowererRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient base = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "base"));

            JsonArray modifiers = GsonHelper.getAsJsonArray(pJson, "modifiers");
            if (modifiers.size() != 4)
                throw new IllegalStateException(pRecipeId.toString() + ": Must have exactly 4 modifiers, has: " + modifiers.size());

            Ingredient mod1 = Ingredient.fromJson(modifiers.get(0));
            Ingredient mod2 = Ingredient.fromJson(modifiers.get(1));
            Ingredient mod3 = Ingredient.fromJson(modifiers.get(2));
            Ingredient mod4 = Ingredient.fromJson(modifiers.get(3));
            int energy = GsonHelper.getAsInt(pJson, "energy");
            int color = GsonHelper.getAsInt(pJson, "color");
            int time = GsonHelper.getAsInt(pJson, "time");
            JsonObject resultObject = GsonHelper.getAsJsonObject(pJson, "result");
            ItemStack result = new ItemStack(GsonHelper.getAsItem(resultObject, "item"));

            return new EmpowererRecipe(pRecipeId, result, base, mod1, mod2, mod3, mod4, energy, color, time);
        }

        @Nullable
        @Override
        public EmpowererRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ItemStack result = pBuffer.readItem();
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            Ingredient mod1 = Ingredient.fromNetwork(pBuffer);
            Ingredient mod2 = Ingredient.fromNetwork(pBuffer);
            Ingredient mod3 = Ingredient.fromNetwork(pBuffer);
            Ingredient mod4 = Ingredient.fromNetwork(pBuffer);
            int energy = pBuffer.readInt();
            int color = pBuffer.readInt();
            int time = pBuffer.readInt();

            return new EmpowererRecipe(pRecipeId, result, input, mod1, mod2, mod3, mod4, energy, color, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, EmpowererRecipe pRecipe) {
            pBuffer.writeItem(pRecipe.output);
            pRecipe.input.toNetwork(pBuffer);
            pRecipe.modifier1.toNetwork(pBuffer);
            pRecipe.modifier2.toNetwork(pBuffer);
            pRecipe.modifier3.toNetwork(pBuffer);
            pRecipe.modifier4.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.energyPerStand);
            pBuffer.writeInt(pRecipe.particleColor);
            pBuffer.writeInt(pRecipe.time);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient base;
        private final Ingredient mod1;
        private final Ingredient mod2;
        private final Ingredient mod3;
        private final Ingredient mod4;
        private final int energy;
        private final int color;
        private final int time;
        private final ItemLike output;

        public Result(ResourceLocation id, ItemLike output, Ingredient input, Ingredient modifier1, Ingredient modifier2, Ingredient modifier3, Ingredient modifier4, int energyPerStand, int particleColor, int time) {
            this.id = id;
            this.base = input;
            this.output = output;
            this.mod1 = modifier1;
            this.mod2 = modifier2;
            this.mod3 = modifier3;
            this.mod4 = modifier4;
            this.energy = energyPerStand;
            this.color = particleColor;
            this.time = time;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("base", base.toJson());

            JsonArray modifiers = new JsonArray();

            modifiers.add(mod1.toJson());
            modifiers.add(mod2.toJson());
            modifiers.add(mod3.toJson());
            modifiers.add(mod4.toJson());

            pJson.add("modifiers", modifiers);
            pJson.addProperty("energy", energy);
            pJson.addProperty("time", time);
            pJson.addProperty("color", color);

            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("item", output.asItem().getRegistryName().toString());

            pJson.add("result", resultObject);
        }

        @Override
        @Nonnull
        public ResourceLocation getId() {
            return id;
        }

        @Override
        @Nonnull
        public RecipeSerializer<?> getType() {
            return ActuallyRecipes.EMPOWERING_RECIPE.get();
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
