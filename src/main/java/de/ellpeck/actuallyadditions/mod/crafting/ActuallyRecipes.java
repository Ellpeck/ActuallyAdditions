package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ActuallyRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ActuallyAdditions.MODID);

    public static void init(IEventBus bus) {
        SERIALIZERS.register(bus);
    }

    public static final RegistryObject<IRecipeSerializer<?>> KEEP_DATA_SHAPED_RECIPE = SERIALIZERS.register(RecipeKeepDataShaped.NAME, RecipeKeepDataShaped.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> LASER_RECIPE = SERIALIZERS.register(LaserRecipe.NAME, LaserRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> EMPOWERING_RECIPE = SERIALIZERS.register(EmpowererRecipe.NAME, EmpowererRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> CRUSHING_RECIPE = SERIALIZERS.register(CrushingRecipe.NAME, CrushingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> SOLID_FUEL_RECIPE = SERIALIZERS.register(SolidFuelRecipe.NAME, SolidFuelRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> LIQUID_FUEL_RECIPE = SERIALIZERS.register(LiquidFuelRecipe.NAME, LiquidFuelRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> PRESSING_RECIPE = SERIALIZERS.register(PressingRecipe.NAME, PressingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> FERMENTING_RECIPE = SERIALIZERS.register(FermentingRecipe.NAME, FermentingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> COLOR_CHANGE_RECIPE = SERIALIZERS.register(ColorChangeRecipe.NAME, ColorChangeRecipe.Serializer::new);



    public static class Types {
        public static final IRecipeType<LaserRecipe> LASER = IRecipeType.register(ActuallyAdditions.MODID + ":laser");
        public static final IRecipeType<EmpowererRecipe> EMPOWERING = IRecipeType.register(ActuallyAdditions.MODID + ":empower");
        public static final IRecipeType<CrushingRecipe> CRUSHING = IRecipeType.register(ActuallyAdditions.MODID + ":crushing");
        public static final IRecipeType<SolidFuelRecipe> SOLID_FUEL = IRecipeType.register(ActuallyAdditions.MODID + ":solid_fuel");
        public static final IRecipeType<LiquidFuelRecipe> LIQUID_FUEL = IRecipeType.register(ActuallyAdditions.MODID + ":liquid_fuel");
        public static final IRecipeType<PressingRecipe> PRESSING = IRecipeType.register(ActuallyAdditions.MODID + ":pressing");
        public static final IRecipeType<FermentingRecipe> FERMENTING = IRecipeType.register(ActuallyAdditions.MODID + ":fermenting");
        public static final IRecipeType<ColorChangeRecipe> COLOR_CHANGE = IRecipeType.register(ActuallyAdditions.MODID + ":color_change");
    }
}
