package ellpeck.someprettyrandomstuff.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class InitItems{

    public static Item itemFertilizer;
    public static Item itemMisc;
    public static Item itemFoods;

    public static void init(){

        itemFertilizer = new ItemFertilizer();
        GameRegistry.registerItem(itemFertilizer, itemFertilizer.getUnlocalizedName().substring(5));

        itemMisc = new ItemMisc();
        GameRegistry.registerItem(itemMisc, itemMisc.getUnlocalizedName().substring(5));

        itemFoods = new ItemFoods();
        GameRegistry.registerItem(itemFoods, itemFoods.getUnlocalizedName().substring(5));
    }
}
