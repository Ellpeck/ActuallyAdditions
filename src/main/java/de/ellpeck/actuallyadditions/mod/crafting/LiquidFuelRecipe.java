package de.ellpeck.actuallyadditions.mod.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class LiquidFuelRecipe implements Recipe<RecipeInput> {
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
    public boolean matches(@Nonnull RecipeInput pInv,@Nonnull Level pLevel) {
        return false;
    }

    public boolean matches(FluidStack stack) {
        return FluidStack.isSameFluidSameComponents(this.fuel, stack);
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
    public ItemStack assemble(RecipeInput pInv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
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
        private static final MapCodec<LiquidFuelRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                FluidStack.CODEC.fieldOf("fuel").forGetter(recipe -> recipe.fuel),
                                Codec.INT.fieldOf("total_energy").forGetter(recipe -> recipe.totalEnergy),
                                Codec.INT.fieldOf("burn_time").forGetter(recipe -> recipe.burnTime)
                        )
                        .apply(instance, LiquidFuelRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, LiquidFuelRecipe> STREAM_CODEC = StreamCodec.of(
                LiquidFuelRecipe.Serializer::toNetwork, LiquidFuelRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<LiquidFuelRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LiquidFuelRecipe> streamCodec() {
            return STREAM_CODEC;
        }

//        @Nonnull
//        @Override
//        public LiquidFuelRecipe fromJson(@Nonnull ResourceLocation pId, JsonObject pJson) {
//            JsonObject ingredient = pJson.getAsJsonObject("ingredient");
//
//            ResourceLocation fluidRes = ResourceLocation.tryParse(GsonHelper.getAsString(ingredient, "fluid"));
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

        public static LiquidFuelRecipe fromNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer) {
            FluidStack input = FluidStack.STREAM_CODEC.decode(pBuffer);
            int totalEnergy = pBuffer.readInt();
            int burnTime = pBuffer.readInt();
            return new LiquidFuelRecipe(input, totalEnergy, burnTime);
        }

        public static void toNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer, LiquidFuelRecipe pRecipe) {
            FluidStack.STREAM_CODEC.encode(pBuffer, pRecipe.fuel);
            pBuffer.writeInt(pRecipe.totalEnergy);
            pBuffer.writeInt(pRecipe.burnTime);
        }
    }
}
