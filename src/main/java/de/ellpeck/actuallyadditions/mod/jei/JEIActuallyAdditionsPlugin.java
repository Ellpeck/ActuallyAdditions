/*
 * This file ("JEIActuallyAdditionsPlugin.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.*;
import de.ellpeck.actuallyadditions.mod.inventory.gui.CrusherScreen;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.jei.coffee.CoffeeMachineCategory;
import de.ellpeck.actuallyadditions.mod.jei.crusher.CrusherCategory;
import de.ellpeck.actuallyadditions.mod.jei.empowerer.EmpowererRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.fermenting.FermentingCategory;
import de.ellpeck.actuallyadditions.mod.jei.laser.LaserRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.lens.MiningLensRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.pressing.PressingCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIActuallyAdditionsPlugin implements IModPlugin {
    public static final ResourceLocation ID = ActuallyAdditions.modLoc("jei_plugin");
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    public static final RecipeType<FermentingRecipe> FERMENTING = RecipeType.create(ActuallyAdditions.MODID, "fermenting", FermentingRecipe.class);
    public static final RecipeType<PressingRecipe> PRESSING = RecipeType.create(ActuallyAdditions.MODID, "pressing", PressingRecipe.class);
    public static final RecipeType<LaserRecipe> LASER = RecipeType.create(ActuallyAdditions.MODID, "laser", LaserRecipe.class);
    public static final RecipeType<EmpowererRecipe> EMPOWERER = RecipeType.create(ActuallyAdditions.MODID, "empowerer", EmpowererRecipe.class);
    public static final RecipeType<CoffeeIngredientRecipe> COFFEE_MACHINE = RecipeType.create(ActuallyAdditions.MODID, "coffee_machine", CoffeeIngredientRecipe.class);
    public static final RecipeType<CrushingRecipe> CRUSHING = RecipeType.create(ActuallyAdditions.MODID, "crushing", CrushingRecipe.class);
    public static final RecipeType<MiningLensRecipe> MINING_LENS = RecipeType.create(ActuallyAdditions.MODID, "mining_lens", MiningLensRecipe.class);

    @Override
    public void registerItemSubtypes(@NotNull ISubtypeRegistration reg) {
        ActuallyItems.ITEMS.getEntries().forEach(entry -> {
            if (entry.get() instanceof ItemEnergy)
                reg.registerSubtypeInterpreter(entry.get(), EnergyInterpreter.INSTANCE);
        });
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();

        registry.addRecipeCategories(new FermentingCategory(helpers.getGuiHelper()));
        registry.addRecipeCategories(new LaserRecipeCategory(helpers.getGuiHelper()));
        registry.addRecipeCategories(new EmpowererRecipeCategory(helpers.getGuiHelper()));
        registry.addRecipeCategories(new CoffeeMachineCategory(helpers.getGuiHelper()));
        registry.addRecipeCategories(new PressingCategory(helpers.getGuiHelper()));
        registry.addRecipeCategories(new CrusherCategory(helpers.getGuiHelper()));
        registry.addRecipeCategories(new MiningLensRecipeCategory(helpers.getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ActuallyItems.CRAFTER_ON_A_STICK.get()), RecipeTypes.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.POWERED_FURNACE.getItem()), RecipeTypes.SMELTING);
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.FERMENTING_BARREL.getItem()), FERMENTING);
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.getItem()), LASER);
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.EMPOWERER.getItem()), EMPOWERER);
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.COFFEE_MACHINE.getItem()), COFFEE_MACHINE);
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.CANOLA_PRESS.getItem()), PRESSING);
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.CRUSHER.getItem()), CRUSHING);
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.CRUSHER_DOUBLE.getItem()), CRUSHING);
        registry.addRecipeCatalyst(new ItemStack(ActuallyItems.LENS_OF_THE_MINER.get()), MINING_LENS);

//        registry.addRecipeCatalyst(new ItemStack(ActuallyItems.itemBooklet.get()), BookletRecipeCategory.NAME);
        //        registry.addRecipeCatalyst(new ItemStack(InitBlocks.blockCompost.get()), CompostRecipeCategory.NAME);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        Level level = Minecraft.getInstance().level;

        registry.addRecipes(FERMENTING, level.getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.FERMENTING.get()).stream().map(RecipeHolder::value).toList());
        registry.addRecipes(LASER, level.getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.LASER.get()).stream().map(RecipeHolder::value).toList());
        registry.addRecipes(EMPOWERER, level.getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.EMPOWERING.get()).stream().map(RecipeHolder::value).toList());
        registry.addRecipes(COFFEE_MACHINE, level.getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.COFFEE_INGREDIENT.get()).stream().map(RecipeHolder::value).toList());
        registry.addRecipes(PRESSING, level.getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.PRESSING.get()).stream().map(RecipeHolder::value).toList());
        registry.addRecipes(CRUSHING, level.getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.CRUSHING.get()).stream().map(RecipeHolder::value).toList());
        registry.addRecipes(MINING_LENS, level.getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.MINING_LENS.get()).stream().map(RecipeHolder::value).toList());
        //registry.addRecipes(ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA, BookletRecipeCategory.NAME);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(GuiCoffeeMachine.class, 53, 42, 22, 16, COFFEE_MACHINE);
        registration.addRecipeClickArea(CrusherScreen.class, 80, 40, 24, 22, CRUSHING);
        registration.addRecipeClickArea(CrusherScreen.CrusherDoubleScreen.class, 51, 40, 74, 22, CRUSHING);
        registration.addRecipeClickArea(GuiFurnaceDouble.class, 51, 40, 74, 22, RecipeTypes.SMELTING);
    }

    //    @Override
//    public void register(IModRegistry registry) {
//        IJeiHelpers helpers = registry.getJeiHelpers();
//
//        registry.handleRecipes(IBookletPage.class, BookletRecipeWrapper::new, BookletRecipeCategory.NAME);
//        registry.handleRecipes(CoffeeIngredient.class, CoffeeMachineRecipeWrapper::new, CoffeeMachineRecipeCategory.NAME);
//        registry.handleRecipes(CrusherRecipe.class, CrusherRecipeWrapper::new, CrusherRecipeCategory.NAME);
//        registry.handleRecipes(LensConversionRecipe.class, ReconstructorRecipeWrapper.FACTORY, ReconstructorRecipeCategory.NAME);
//        registry.handleRecipes(EmpowererRecipe.class, EmpowererRecipeWrapper::new, EmpowererRecipeCategory.NAME);
//        registry.handleRecipes(CompostRecipe.class, CompostRecipeWrapper::new, CompostRecipeCategory.NAME);
//
//
//        IIngredientBlacklist blacklist = helpers.getIngredientBlacklist();
//        blacklist.addIngredientToBlacklist(new ItemStack(ActuallyBlocks.blockRice.get()));
//        blacklist.addIngredientToBlacklist(new ItemStack(ActuallyBlocks.blockCanola.get()));
//        blacklist.addIngredientToBlacklist(new ItemStack(ActuallyBlocks.blockFlax.get()));
//        blacklist.addIngredientToBlacklist(new ItemStack(ActuallyBlocks.blockCoffee.get()));
//        blacklist.addIngredientToBlacklist(new ItemStack(ActuallyBlocks.blockWildPlant.get(), 1, Util.WILDCARD));
//        blacklist.addIngredientToBlacklist(new ItemStack(ActuallyBlocks.blockColoredLampOn.get(), 1, Util.WILDCARD));
//        //        blacklist.addIngredientToBlacklist(new ItemStack(InitItems.itemMisc.get(), 1, TheMiscItems.YOUTUBE_ICON.ordinal()));
//
//    }
}
