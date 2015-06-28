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
    public static void addInformation(Item item, List list, int lines, String extraName){
        if(KeyUtil.isShiftPressed()){
            for(int i = 0; i < lines; i++){
                list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + ((INameableItem)item).getName() + extraName + ".desc" + (lines > 1 ? "." +(i+1) : "")));
            }
        }
        else list.add(shiftForInfo());
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
        register(item, addTab, null);
    }

    public static void register(Item item, Enum[] list){
        register(item, true, list);
    }

    public static void register(Item item, boolean addTab, Enum[] list){
        if(addTab) item.setCreativeTab(CreativeTab.instance);
        item.setUnlocalizedName(createUnlocalizedName(item));
        GameRegistry.registerItem(item, ((INameableItem)item).getName());
        if(list != null){
            for(Enum current : list){
                if(!((INameableItem)current).getOredictName().isEmpty()) OreDictionary.registerOre(((INameableItem)current).getOredictName(), new ItemStack(item, 1, current.ordinal()));
            }
        }
        else{
            if(!((INameableItem)item).getOredictName().isEmpty()) OreDictionary.registerOre(((INameableItem)item).getOredictName(), new ItemStack(item, 1, Util.WILDCARD));
        }
    }

    public static String createUnlocalizedName(Item item){
        return ModUtil.MOD_ID_LOWER + "." + ((INameableItem)item).getName();
    }

    public static String shiftForInfo(){
        return StringUtil.GREEN + StringUtil.ITALIC + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".shiftForInfo.desc");
    }
}
