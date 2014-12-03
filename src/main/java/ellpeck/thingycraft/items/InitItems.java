package ellpeck.thingycraft.items;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class InitItems {

    public static Item itemGem;

    public static void init(){

        itemGem = new ItemGem();

        GameRegistry.registerItem(itemGem, itemGem.getUnlocalizedName().substring(5));

    }
}
