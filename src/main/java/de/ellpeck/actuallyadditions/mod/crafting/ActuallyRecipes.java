package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ActuallyRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ActuallyAdditions.MODID);

    public static void init(IEventBus bus) {
        SERIALIZERS.register(bus);
    }

    public static final RegistryObject<RecipeSerializer<?>> KEEP_DATA_SHAPED_RECIPE = SERIALIZERS.register(RecipeKeepDataShaped.NAME, RecipeKeepDataShaped.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> LASER_RECIPE = SERIALIZERS.register(LaserRecipe.NAME, LaserRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> EMPOWERING_RECIPE = SERIALIZERS.register(EmpowererRecipe.NAME, EmpowererRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> CRUSHING_RECIPE = SERIALIZERS.register(CrushingRecipe.NAME, CrushingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SOLID_FUEL_RECIPE = SERIALIZERS.register(SolidFuelRecipe.NAME, SolidFuelRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> LIQUID_FUEL_RECIPE = SERIALIZERS.register(LiquidFuelRecipe.NAME, LiquidFuelRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> PRESSING_RECIPE = SERIALIZERS.register(PressingRecipe.NAME, PressingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> FERMENTING_RECIPE = SERIALIZERS.register(FermentingRecipe.NAME, FermentingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> COLOR_CHANGE_RECIPE = SERIALIZERS.register(ColorChangeRecipe.NAME, ColorChangeRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> MINING_LENS_RECIPE = SERIALIZERS.register(MiningLensRecipe.NAME, MiningLensRecipe.Serializer::new);



    public static class Types {
        public static final RecipeType<LaserRecipe> LASER = RecipeType.register(ActuallyAdditions.MODID + ":laser");
        public static final RecipeType<EmpowererRecipe> EMPOWERING = RecipeType.register(ActuallyAdditions.MODID + ":empower");
        public static final RecipeType<CrushingRecipe> CRUSHING = RecipeType.register(ActuallyAdditions.MODID + ":crushing");
        public static final RecipeType<SolidFuelRecipe> SOLID_FUEL = RecipeType.register(ActuallyAdditions.MODID + ":solid_fuel");
        public static final RecipeType<LiquidFuelRecipe> LIQUID_FUEL = RecipeType.register(ActuallyAdditions.MODID + ":liquid_fuel");
        public static final RecipeType<PressingRecipe> PRESSING = RecipeType.register(ActuallyAdditions.MODID + ":pressing");
        public static final RecipeType<FermentingRecipe> FERMENTING = RecipeType.register(ActuallyAdditions.MODID + ":fermenting");
        public static final RecipeType<ColorChangeRecipe> COLOR_CHANGE = RecipeType.register(ActuallyAdditions.MODID + ":color_change");
        public static final RecipeType<MiningLensRecipe> MINING_LENS = RecipeType.register(ActuallyAdditions.MODID + ":mining_lens");
    }
}
