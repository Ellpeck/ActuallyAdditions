package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SolidFuelRecipe implements Recipe<SingleItem> {
    public static String NAME = "solid_fuel";
    private Ingredient itemIngredient;
    private int burnTime;
    private int totalEnergy;

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
    public boolean matches(SingleItem pInv, Level pLevel) {
        return itemIngredient.test(pInv.getItem());
    }

    public boolean matches(ItemStack stack) {
        return this.itemIngredient.test(stack);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack assemble(SingleItem pInv, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.SOLID_FUEL_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.SOLID_FUEL.get();
    }

    public static class Serializer implements RecipeSerializer<SolidFuelRecipe> {
        private static final Codec<SolidFuelRecipe> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("item").forGetter(recipe -> recipe.itemIngredient),
                                Codec.INT.fieldOf("total_energy").forGetter(recipe -> recipe.totalEnergy),
                                Codec.INT.fieldOf("burn_time").forGetter(recipe -> recipe.burnTime)
                        )
                        .apply(instance, SolidFuelRecipe::new)
        );

//        @Override
//        public SolidFuelRecipe fromJson(ResourceLocation pId, JsonObject pJson) {
//            Ingredient itemIngredient = Ingredient.fromJson(pJson.get("item"));
//            int totalEnergy = pJson.get("total_energy").getAsInt();
//            int burnTime = pJson.get("burn_time").getAsInt();
//            return new SolidFuelRecipe(pId, itemIngredient, totalEnergy, burnTime);
//        }


        @Override
        public Codec<SolidFuelRecipe> codec() {
            return CODEC;
        }

        @Override
        public SolidFuelRecipe fromNetwork(FriendlyByteBuf pBuffer) {
            Ingredient itemIngredient = Ingredient.fromNetwork(pBuffer);
            int totalEnergy = pBuffer.readInt();
            int burnTime = pBuffer.readInt();
            return new SolidFuelRecipe(itemIngredient, totalEnergy, burnTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SolidFuelRecipe pRecipe) {
            pRecipe.itemIngredient.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.totalEnergy);
            pBuffer.writeInt(pRecipe.burnTime);
        }
    }
}
