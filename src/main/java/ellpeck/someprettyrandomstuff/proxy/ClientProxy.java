package ellpeck.someprettyrandomstuff.proxy;

import ellpeck.someprettyrandomstuff.blocks.InitBlocks;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.ItemFoods;
import ellpeck.someprettyrandomstuff.items.ItemMisc;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.item.Item;

@SuppressWarnings("unused")
public class ClientProxy implements IProxy{

    public void preInit(){
        Util.logInfo("PreInitializing Textures...");
        Util.preInitIcons(InitItems.itemMisc, ItemMisc.allMiscItems);
        Util.preInitIcons(InitItems.itemFoods, ItemFoods.allFoods);
    }

    public void init(){
        Util.logInfo("Initializing Textures...");
        Util.initIcons(InitItems.itemMisc, ItemMisc.allMiscItems);
        Util.initIcons(InitItems.itemFoods, ItemFoods.allFoods);

        Util.initIcons(InitItems.itemFertilizer);
        Util.initIcons(Item.getItemFromBlock(InitBlocks.blockCompost));
    }

    public void postInit(){
        Util.logInfo("PostInitializing Textures...");
    }
}
