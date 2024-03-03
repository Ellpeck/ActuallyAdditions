package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ActuallyRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ActuallyAdditions.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ActuallyAdditions.MODID);

    public static void init(IEventBus bus) {
        RECIPE_TYPES.register(bus);
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
        public static final RegistryObject<RecipeType<LaserRecipe>> LASER = RECIPE_TYPES.register("laser", () -> new RecipeType<>() {});
        public static final RegistryObject<RecipeType<EmpowererRecipe>> EMPOWERING = RECIPE_TYPES.register("empower", () -> new RecipeType<>() {});
        public static final RegistryObject<RecipeType<CrushingRecipe>> CRUSHING = RECIPE_TYPES.register("crushing", () -> new RecipeType<>() {});
        public static final RegistryObject<RecipeType<SolidFuelRecipe>> SOLID_FUEL = RECIPE_TYPES.register("solid_fuel", () -> new RecipeType<>() {});
        public static final RegistryObject<RecipeType<LiquidFuelRecipe>> LIQUID_FUEL = RECIPE_TYPES.register("liquid_fuel", () -> new RecipeType<>() {});
        public static final RegistryObject<RecipeType<PressingRecipe>> PRESSING = RECIPE_TYPES.register("pressing", () -> new RecipeType<>() {});
        public static final RegistryObject<RecipeType<FermentingRecipe>> FERMENTING = RECIPE_TYPES.register("fermenting", () -> new RecipeType<>() {});
        public static final RegistryObject<RecipeType<ColorChangeRecipe>> COLOR_CHANGE = RECIPE_TYPES.register("color_change", () -> new RecipeType<>() {});
        public static final RegistryObject<RecipeType<MiningLensRecipe>> MINING_LENS = RECIPE_TYPES.register("mining_lens", () -> new RecipeType<>() {});
    }
}
