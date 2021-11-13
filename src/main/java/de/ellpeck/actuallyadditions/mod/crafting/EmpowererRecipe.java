package de.ellpeck.actuallyadditions.mod.crafting;

import com.google.gson.JsonObject;
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
import java.util.ArrayList;
import java.util.List;

public class EmpowererRecipe implements IRecipe<IInventory> {
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
    public boolean matches(@Nonnull IInventory pInv, @Nonnull World pLevel) {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack assemble(@Nonnull IInventory pInv) {
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
    public IRecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.EMPOWERING_RECIPE.get();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
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

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<EmpowererRecipe> {
        @Override
        @Nonnull
        public EmpowererRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pJson) {
            Ingredient base = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "base"));
            Ingredient mod1 = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "modifier1"));
            Ingredient mod2 = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "modifier2"));
            Ingredient mod3 = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "modifier3"));
            Ingredient mod4 = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "modifier4"));
            int energy = JSONUtils.getAsInt(pJson, "energy");
            int color = JSONUtils.getAsInt(pJson, "color");
            int time = JSONUtils.getAsInt(pJson, "time");
            JsonObject resultObject = JSONUtils.getAsJsonObject(pJson, "result");
            ItemStack result = new ItemStack(JSONUtils.getAsItem(resultObject, "item"));

            return new EmpowererRecipe(pRecipeId, result, base, mod1, mod2, mod3, mod4, energy, color, time);
        }

        @Nullable
        @Override
        public EmpowererRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, PacketBuffer pBuffer) {
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
        public void toNetwork(PacketBuffer pBuffer, EmpowererRecipe pRecipe) {
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

    public static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient base;
        private final Ingredient mod1;
        private final Ingredient mod2;
        private final Ingredient mod3;
        private final Ingredient mod4;
        private final int energy;
        private final int color;
        private final int time;
        private final IItemProvider output;

        public FinishedRecipe(ResourceLocation id, IItemProvider output, Ingredient input, Ingredient modifier1, Ingredient modifier2, Ingredient modifier3, Ingredient modifier4, int energyPerStand, int particleColor, int time) {
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
            pJson.add("modifier1", mod1.toJson());
            pJson.add("modifier2", mod2.toJson());
            pJson.add("modifier3", mod3.toJson());
            pJson.add("modifier4", mod4.toJson());
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
        public IRecipeSerializer<?> getType() {
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
