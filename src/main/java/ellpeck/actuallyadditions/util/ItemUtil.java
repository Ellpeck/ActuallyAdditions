package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import net.minecraft.item.Item;

public class ItemUtil{

    public static Item getItemFromName(String name){
        if(Item.itemRegistry.containsKey(name)){
            return (Item)Item.itemRegistry.getObject(name);
        }
        return null;
    }

    public static void registerItems(Item[] items){
        for(Item item : items){
            register(item);
        }
    }

    public static void register(Item item){
        register(item, true);
    }

    public static void register(Item item, boolean addTab){
        item.setCreativeTab(addTab ? CreativeTab.instance : null);
        item.setUnlocalizedName(createUnlocalizedName(item));
        GameRegistry.registerItem(item, ((INameableItem)item).getName());
    }

    public static String createUnlocalizedName(Item item){
        return ModUtil.MOD_ID_LOWER + "." + ((INameableItem)item).getName();
    }

}
