package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtil{

    public static void registerItems(Item[] items){
        for(Item item : items){
            register(item);
        }
    }

    public static void register(Item item){
        register(item, true);
    }

    public static void register(Item item, boolean addTab){
        if(addTab) item.setCreativeTab(CreativeTab.instance);
        item.setUnlocalizedName(createUnlocalizedName(item));
        GameRegistry.registerItem(item, ((INameableItem)item).getName());
        if(!((INameableItem)item).getOredictName().isEmpty()) OreDictionary.registerOre(((INameableItem)item).getOredictName(), new ItemStack(item, 1, Util.WILDCARD));
    }

    public static void register(Item item, Enum[] list){
        item.setCreativeTab(CreativeTab.instance);
        item.setUnlocalizedName(createUnlocalizedName(item));
        GameRegistry.registerItem(item, ((INameableItem)item).getName());
        for(Enum current : list){
            if(!((INameableItem)current).getOredictName().isEmpty()) OreDictionary.registerOre(((INameableItem)current).getOredictName(), new ItemStack(item, 1, current.ordinal()));
        }
    }

    public static String createUnlocalizedName(Item item){
        return ModUtil.MOD_ID_LOWER + "." + ((INameableItem)item).getName();
    }
}
