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

        HairyBallRecipeHandler ballRecipeHandler = new HairyBallRecipeHandler();
        API.registerRecipeHandler(ballRecipeHandler);
        API.registerUsageHandler(ballRecipeHandler);

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