package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

public class ItemUtil{

    public static String addStandardInformation(Item item){
        if(KeyUtil.isShiftPressed()) return StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + ((IName)item).getName() + ".desc");
        else return shiftForInfo();
    }

    public static void registerItems(Item[] items){
        for(Item item : items){
            register(item);
        }
    }

    public static void register(Item item){
        item.setCreativeTab(CreativeTab.instance);
        item.setUnlocalizedName(createUnlocalizedName(item));
        GameRegistry.registerItem(item, ((IName)item).getName());
    }

    public static String createUnlocalizedName(Item item){
        return ModUtil.MOD_ID_LOWER + "." + ((IName)item).getName();
    }

    public static String shiftForInfo(){
        return StringUtil.GREEN + StringUtil.ITALIC + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".shiftForInfo.desc");
    }

}
