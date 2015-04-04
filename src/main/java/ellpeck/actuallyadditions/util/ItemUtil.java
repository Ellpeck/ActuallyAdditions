package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class ItemUtil{

    @SuppressWarnings("unchecked")
    public static void addStandardInformation(Item item, List list){
        if(KeyUtil.isShiftPressed()){
            list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + ((INameableItem)item).getName() + ".desc"));
            addOredictName(item, list);
        }
        else list.add(shiftForInfo());
    }

    public static void registerItems(Item[] items){
        for(Item item : items){
            register(item);
        }
    }

    @SuppressWarnings("unchecked")
    public static void addOredictName(Item item, List list){
        list.add(StringUtil.GRAY + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".oredictName.desc") + ": " + ((INameableItem)item).getOredictName());
    }

    public static void register(Item item){
        item.setCreativeTab(CreativeTab.instance);
        item.setUnlocalizedName(createUnlocalizedName(item));
        GameRegistry.registerItem(item, ((INameableItem)item).getName());
        OreDictionary.registerOre(((INameableItem)item).getOredictName(), item);
    }

    public static void register(Item item, Enum[] list){
        item.setCreativeTab(CreativeTab.instance);
        item.setUnlocalizedName(createUnlocalizedName(item));
        GameRegistry.registerItem(item, ((INameableItem)item).getName());
        for(Enum current : list){
            OreDictionary.registerOre(((INameableItem)current).getOredictName(), new ItemStack(item, 1, current.ordinal()));
        }
    }

    public static String createUnlocalizedName(Item item){
        return ModUtil.MOD_ID_LOWER + "." + ((INameableItem)item).getName();
    }

    public static String shiftForInfo(){
        return StringUtil.GREEN + StringUtil.ITALIC + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".shiftForInfo.desc");
    }

}
