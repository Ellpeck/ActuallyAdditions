/*
 * This file ("NEIActuallyAdditionsConfig.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.inventory.gui.GuiCrafter;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;

public class NEIActuallyAdditionsConfig implements IConfigureNEI{

    @Override
    public void loadConfig(){
        ModUtil.LOGGER.info("Initializing Not Enough Items Plugin...");

        API.registerGuiOverlay(GuiCrafter.class, "crafting");
        API.registerGuiOverlayHandler(GuiCrafter.class, new DefaultOverlayHandler(), "crafting");

        CrusherRecipeHandler crusherRecipeHandler = new CrusherRecipeHandler();
        API.registerRecipeHandler(crusherRecipeHandler);
        API.registerUsageHandler(crusherRecipeHandler);

        CrusherRecipeHandler.CrusherDoubleRecipeHandler crusherDoubleRecipeHandler = new CrusherRecipeHandler.CrusherDoubleRecipeHandler();
        API.registerRecipeHandler(crusherDoubleRecipeHandler);
        API.registerUsageHandler(crusherDoubleRecipeHandler);

        FurnaceDoubleRecipeHandler furnaceDoubleRecipeHandler = new FurnaceDoubleRecipeHandler();
        API.registerRecipeHandler(furnaceDoubleRecipeHandler);
        API.registerUsageHandler(furnaceDoubleRecipeHandler);

        HairyBallRecipeHandler ballRecipeHandler = new HairyBallRecipeHandler();
        API.registerRecipeHandler(ballRecipeHandler);
        API.registerUsageHandler(ballRecipeHandler);

        TreasureChestRecipeHandler treasureRecipeHandler = new TreasureChestRecipeHandler();
        API.registerRecipeHandler(treasureRecipeHandler);
        API.registerUsageHandler(treasureRecipeHandler);

        CompostRecipeHandler compostRecipeHandler = new CompostRecipeHandler();
        API.registerRecipeHandler(compostRecipeHandler);
        API.registerUsageHandler(compostRecipeHandler);

        CoffeeMachineRecipeHandler coffeeMachineRecipeHandler = new CoffeeMachineRecipeHandler();
        API.registerRecipeHandler(coffeeMachineRecipeHandler);
        API.registerUsageHandler(coffeeMachineRecipeHandler);

        API.hideItem(new ItemStack(InitBlocks.blockRice));
        API.hideItem(new ItemStack(InitBlocks.blockCanola));
        API.hideItem(new ItemStack(InitBlocks.blockFlax));
        API.hideItem(new ItemStack(InitBlocks.blockCoffee));
        API.hideItem(new ItemStack(InitBlocks.blockWildPlant, 1, Util.WILDCARD));
        API.hideItem(new ItemStack(InitBlocks.blockColoredLampOn, 1, Util.WILDCARD));
    }

    @Override
    public String getName(){
        return ModUtil.MOD_ID + " NEI Plugin";
    }

    @Override
    public String getVersion(){
        return ModUtil.VERSION;
    }

}