package ellpeck.actuallyadditions.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.inventory.GuiCrafter;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.ItemStack;

public class NEIActuallyAdditionsConfig implements IConfigureNEI{

    @Override
    public void loadConfig(){
        Util.logInfo("Initializing Not Enough Items Plugin...");

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

        API.hideItem(new ItemStack(InitBlocks.blockRice));
        API.hideItem(new ItemStack(InitBlocks.blockCanola));
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