package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class ItemUtil{

    public static void addInformation(Item item, List list, int lines, String extraName){
        addInformation(item, list, lines, extraName, ((INameableItem)item).getOredictName());
    }

    @SuppressWarnings("unchecked")
    public static void addInformation(Item item, List list, int lines, String extraName, String extraOredictName){
        if(KeyUtil.isShiftPressed()){
            for(int i = 0; i < lines; i++){
                list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + ((INameableItem)item).getName() + extraName + ".desc" + (lines > 1 ? "." +(i+1) : "")));
            }
        }
        else list.add(shiftForInfo());

        if(KeyUtil.isControlPressed()){
            addOredictName(extraOredictName, list);
        }
    }



    public static void registerItems(Item[] items){
        for(Item item : items){
            register(item);
        }
    }

    @SuppressWarnings("unchecked")
    public static void addOredictName(String name, List list){
        list.add(StringUtil.GRAY + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".oredictName.desc") + ": " + name);
    }

    public static void register(Item item){
        item.setCreativeTab(CreativeTab.instance);
        item.setUnlocalizedName(createUnlocalizedName(item));
        GameRegistry.registerItem(item, ((INameableItem)item).getName());
        if(!((INameableItem)item).getOredictName().isEmpty()) OreDictionary.registerOre(((INameableItem)item).getOredictName(), item);
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

    public static String shiftForInfo(){
        return StringUtil.GREEN + StringUtil.ITALIC + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".shiftForInfo.desc");
    }
}
