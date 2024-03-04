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
import de.ellpeck.actuallyadditions.mod.crafting.ActuallyRecipes;
import de.ellpeck.actuallyadditions.mod.crafting.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.FermentingRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.LaserRecipe;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

@JeiPlugin
public class JEIActuallyAdditionsPlugin implements IModPlugin {
    public static final ResourceLocation ID = new ResourceLocation(ActuallyAdditions.MODID, "jei_plugin");
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    public static final RecipeType<FermentingRecipe> FERMENTING = RecipeType.create(ActuallyAdditions.MODID, "fermenting", FermentingRecipe.class);
    public static final RecipeType<LaserRecipe> LASER = RecipeType.create(ActuallyAdditions.MODID, "laser", LaserRecipe.class);
    public static final RecipeType<EmpowererRecipe> EMPOWERER = RecipeType.create(ActuallyAdditions.MODID, "empowerer", EmpowererRecipe.class);

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();

        registry.addRecipeCategories(new FermentingCategory(helpers.getGuiHelper())); 
        registry.addRecipeCategories(new LaserRecipeCategory(helpers.getGuiHelper())); 
        registry.addRecipeCategories(new EmpowererRecipeCategory(helpers.getGuiHelper())); 

        //registry.addRecipeCategories(new CoffeeMachineRecipeCategory(helpers.getGuiHelper()), new CompostRecipeCategory(helpers.getGuiHelper()), new CrusherRecipeCategory(helpers.getGuiHelper()), new ReconstructorRecipeCategory(helpers.getGuiHelper()), new EmpowererRecipeCategory(helpers.getGuiHelper()), new BookletRecipeCategory(helpers.getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ActuallyItems.CRAFTER_ON_A_STICK.get()), RecipeTypes.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.FERMENTING_BARREL.getItem()), FERMENTING); 
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.getItem()), LASER); 
        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.EMPOWERER.getItem()), EMPOWERER); 

//        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.blockFurnaceDouble.get()), VanillaRecipeCategoryUid.SMELTING);
//        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.blockGrinder.get()), CrusherRecipeCategory.NAME);
//        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.blockGrinderDouble.get()), CrusherRecipeCategory.NAME);
//        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.blockCoffeeMachine.get()), CoffeeMachineRecipeCategory.NAME);
//        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.blockAtomicReconstructor.get()), ReconstructorRecipeCategory.NAME);
//        registry.addRecipeCatalyst(new ItemStack(ActuallyBlocks.blockEmpowerer.get()), EmpowererRecipeCategory.NAME);
//        registry.addRecipeCatalyst(new ItemStack(ActuallyItems.itemBooklet.get()), BookletRecipeCategory.NAME);
        //        registry.addRecipeCatalyst(new ItemStack(InitBlocks.blockCompost.get()), CompostRecipeCategory.NAME);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        Level level = Minecraft.getInstance().level;

        registry.addRecipes(FERMENTING, level.getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.FERMENTING.get()).stream().map(RecipeHolder::value).toList());
        registry.addRecipes(LASER, level.getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.LASER.get()).stream().map(RecipeHolder::value).toList());
        registry.addRecipes(EMPOWERER, level.getRecipeManager().getAllRecipesFor(ActuallyRecipes.Types.EMPOWERING.get()).stream().map(RecipeHolder::value).toList());


        //registry.addRecipes(ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA, BookletRecipeCategory.NAME);
        //registry.addRecipes(ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS, CoffeeMachineRecipeCategory.NAME);
        //registry.addRecipes(ActuallyAdditionsAPI.CRUSHER_RECIPES, CrusherRecipeCategory.NAME);
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
//
//        registry.addRecipeClickArea(GuiCoffeeMachine.class, 53, 42, 22, 16, CoffeeMachineRecipeCategory.NAME);
//        registry.addRecipeClickArea(GuiGrinder.class, 80, 40, 24, 22, CrusherRecipeCategory.NAME);
//        registry.addRecipeClickArea(GuiGrinder.GuiGrinderDouble.class, 51, 40, 74, 22, CrusherRecipeCategory.NAME);
//        registry.addRecipeClickArea(GuiFurnaceDouble.class, 51, 40, 74, 22, VanillaRecipeCategoryUid.SMELTING);
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
