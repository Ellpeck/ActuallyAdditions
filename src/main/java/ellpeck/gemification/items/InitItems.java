package ellpeck.gemification.items;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.gemification.booklet.ItemInfoBook;
import net.minecraft.item.Item;

public class InitItems {

    public static Item itemGem;
    public static Item itemInfoBook;

    public static void init(){

        itemGem = new ItemGem();
        itemInfoBook = new ItemInfoBook();

        GameRegistry.registerItem(itemGem, itemGem.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemInfoBook, itemInfoBook.getUnlocalizedName().substring(5));

    }
}
