package ellpeck.someprettytechystuff.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class InitItems{

    public static Item itemFertilizer;
    public static Item itemMisc;
    public static Item itemCombinedFood;

    public static void init(){

        itemFertilizer = new ItemFertilizer();
        GameRegistry.registerItem(itemFertilizer, itemFertilizer.getUnlocalizedName().substring(5));

        itemMisc = new ItemMisc();
        GameRegistry.registerItem(itemMisc, itemMisc.getUnlocalizedName().substring(5));

        itemCombinedFood = new ItemFoods();
        GameRegistry.registerItem(itemCombinedFood, itemCombinedFood.getUnlocalizedName().substring(5));
    }
}
