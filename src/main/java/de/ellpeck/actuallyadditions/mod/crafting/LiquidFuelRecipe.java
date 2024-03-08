package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class LiquidFuelRecipe implements Recipe<Container> {
    public static String NAME = "liquid_fuel";
    private FluidStack fuel;
    private int burnTime;
    private int totalEnergy;

    /**
     * Oil generator recipe
     * @param fuel The fluid
     * @param totalEnergy The total power generated.
     * @param burnTime The length the fluid burns for, in ticks.
     */
    public LiquidFuelRecipe( FluidStack fuel, int totalEnergy, int burnTime) {
        this.fuel = fuel;
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
    public boolean matches(@Nonnull Container pInv,@Nonnull Level pLevel) {
        return false;
    }

    public boolean matches(FluidStack stack) {
        return this.fuel.isFluidEqual(stack);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public int getFuelAmount() {
        return this.fuel.getAmount();
    }

    public FluidStack getFuel() {
        return fuel;
    }

    @Nonnull
    @Override
    public ItemStack assemble(Container pInv, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ActuallyRecipes.LIQUID_FUEL_RECIPE.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return ActuallyRecipes.Types.LIQUID_FUEL.get();
    }

    public static class Serializer implements RecipeSerializer<LiquidFuelRecipe> {
        private static final Codec<LiquidFuelRecipe> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                FluidStack.CODEC.fieldOf("fuel").forGetter(recipe -> recipe.fuel),
                                Codec.INT.fieldOf("total_energy").forGetter(recipe -> recipe.totalEnergy),
                                Codec.INT.fieldOf("burn_time").forGetter(recipe -> recipe.burnTime)
                        )
                        .apply(instance, LiquidFuelRecipe::new)
        );

//        @Nonnull
//        @Override
//        public LiquidFuelRecipe fromJson(@Nonnull ResourceLocation pId, JsonObject pJson) {
//            JsonObject ingredient = pJson.getAsJsonObject("ingredient");
//
//            ResourceLocation fluidRes = new ResourceLocation(GsonHelper.getAsString(ingredient, "fluid"));
//            Fluid fluid = BuiltInRegistries.FLUIDS.getValue(fluidRes);
//            if (fluid == null)
//                throw new JsonParseException("Unknown fluid '" + fluidRes + "'");
//            int inputAmount = GsonHelper.getAsInt(ingredient, "amount", 50);
//            FluidStack input = new FluidStack(fluid, inputAmount);
//
//            JsonObject result = pJson.getAsJsonObject("result");
//            int totalEnergy = result.get("total_energy").getAsInt();
//            int burnTime = result.get("burn_time").getAsInt();
//            return new LiquidFuelRecipe(pId, input, totalEnergy, burnTime);
//        }

        @Override
        public Codec<LiquidFuelRecipe> codec() {
            return CODEC;
        }

        @Override
        public LiquidFuelRecipe fromNetwork(@Nonnull FriendlyByteBuf pBuffer) {
            FluidStack input = FluidStack.readFromPacket(pBuffer);
            int totalEnergy = pBuffer.readInt();
            int burnTime = pBuffer.readInt();
            return new LiquidFuelRecipe(input, totalEnergy, burnTime);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, LiquidFuelRecipe pRecipe) {
            pRecipe.fuel.writeToPacket(pBuffer);
            pBuffer.writeInt(pRecipe.totalEnergy);
            pBuffer.writeInt(pRecipe.burnTime);
        }
    }
}
