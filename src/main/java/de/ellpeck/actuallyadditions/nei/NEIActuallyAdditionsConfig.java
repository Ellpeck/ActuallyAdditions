/*
 * This file ("NEIActuallyAdditionsConfig.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;
import de.ellpeck.actuallyadditions.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.inventory.gui.GuiCrafter;
import de.ellpeck.actuallyadditions.util.ModUtil;
import de.ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;

public class NEIActuallyAdditionsConfig implements IConfigureNEI{

    @Override
    public void loadConfig(){
        ModUtil.LOGGER.info("Initializing Not Enough Items Plugin...");

        API.registerGuiOverlay(GuiCrafter.class, "crafting");
        API.registerGuiOverlayHandler(GuiCrafter.class, new DefaultOverlayHandler(), "crafting");

        NEICrusherRecipe crusherRecipe = new NEICrusherRecipe();
        API.registerRecipeHandler(crusherRecipe);
        API.registerUsageHandler(crusherRecipe);

        NEICrusherRecipe.Double crusherDoubleRecipe = new NEICrusherRecipe.Double();
        API.registerRecipeHandler(crusherDoubleRecipe);
        API.registerUsageHandler(crusherDoubleRecipe);

        NEIFurnaceDoubleRecipe furnaceDoubleRecipe = new NEIFurnaceDoubleRecipe();
        API.registerRecipeHandler(furnaceDoubleRecipe);
        API.registerUsageHandler(furnaceDoubleRecipe);

        NEIHairyBallRecipe ballRecipe = new NEIHairyBallRecipe();
        API.registerRecipeHandler(ballRecipe);
        API.registerUsageHandler(ballRecipe);

        NEITreasureChestRecipe treasureChestRecipe = new NEITreasureChestRecipe();
        API.registerRecipeHandler(treasureChestRecipe);
        API.registerUsageHandler(treasureChestRecipe);

        NEICompostRecipe compostRecipe = new NEICompostRecipe();
        API.registerRecipeHandler(compostRecipe);
        API.registerUsageHandler(compostRecipe);

        NEICoffeeMachineRecipe coffeeMachineRecipe = new NEICoffeeMachineRecipe();
        API.registerRecipeHandler(coffeeMachineRecipe);
        API.registerUsageHandler(coffeeMachineRecipe);

        NEIReconstructorRecipe reconstructorRecipe = new NEIReconstructorRecipe();
        API.registerRecipeHandler(reconstructorRecipe);
        API.registerUsageHandler(reconstructorRecipe);

        NEIBookletRecipe bookletRecipe = new NEIBookletRecipe();
        API.registerRecipeHandler(bookletRecipe);
        API.registerUsageHandler(bookletRecipe);

        API.hideItem(new ItemStack(InitBlocks.blockRice));
        API.hideItem(new ItemStack(InitBlocks.blockCanola));
        API.hideItem(new ItemStack(InitBlocks.blockFlax));
        API.hideItem(new ItemStack(InitBlocks.blockCoffee));
        API.hideItem(new ItemStack(InitBlocks.blockWildPlant, 1, Util.WILDCARD));
        API.hideItem(new ItemStack(InitBlocks.blockColoredLampOn, 1, Util.WILDCARD));
    }

    @Override
    public String getName(){
        return ModUtil.MOD_ID+" NEI Plugin";
    }

    @Override
    public String getVersion(){
        return ModUtil.VERSION;
    }

}