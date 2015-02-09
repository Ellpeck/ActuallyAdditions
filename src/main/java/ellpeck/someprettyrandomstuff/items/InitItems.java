package ellpeck.someprettyrandomstuff.items;

import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitItems{

    public static Item itemFertilizer;
    public static Item itemMisc;
    public static Item itemFoods;
    public static Item itemKnife;

    public static void init(){
        Util.logInfo("Initializing Items...");

        itemFertilizer = new ItemFertilizer();
        GameRegistry.registerItem(itemFertilizer, itemFertilizer.getUnlocalizedName().substring(5));

        itemMisc = new ItemMisc();
        GameRegistry.registerItem(itemMisc, itemMisc.getUnlocalizedName().substring(5));

        itemFoods = new ItemFoods();
        GameRegistry.registerItem(itemFoods, itemFoods.getUnlocalizedName().substring(5));

        itemKnife = new ItemKnife();
        GameRegistry.registerItem(itemKnife, itemKnife.getUnlocalizedName().substring(5));
    }
}
