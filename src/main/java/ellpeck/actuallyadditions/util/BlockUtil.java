package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class BlockUtil{

    public static String createUnlocalizedName(Block block){
        return ModUtil.MOD_ID_LOWER + "." + ((INameableItem)block).getName();
    }

    @SuppressWarnings("unchecked")
    public static void addInformation(Block block, List list, int lines, String extraName, String extraOredictName){
        if(KeyUtil.isShiftPressed()){
            for(int i = 0; i < lines; i++){
                list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + ((INameableItem)block).getName() + extraName + ".desc" + (lines > 1 ? "." +(i+1) : "")));
            }
        }
        else list.add(ItemUtil.shiftForInfo());

        if(KeyUtil.isControlPressed()){
            addOredictName(extraOredictName, list);
        }
    }

    public static void addInformation(Block block, List list, int lines, String extraName){
        addInformation(block, list, lines, extraName, ((INameableItem)block).getOredictName());
    }

    @SuppressWarnings("unchecked")
    public static void addOredictName(String name, List list){
        list.add(StringUtil.GRAY + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".oredictName.desc") + ": " + name);
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock, Enum[] list){
        block.setCreativeTab(CreativeTab.instance);
        block.setBlockName(createUnlocalizedName(block));
        GameRegistry.registerBlock(block, itemBlock, ((INameableItem)block).getName());
        for(Enum current : list){
            if(!((INameableItem)current).getOredictName().isEmpty()) OreDictionary.registerOre(((INameableItem)current).getOredictName(), new ItemStack(block, 1, current.ordinal()));
        }
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock){
        block.setCreativeTab(CreativeTab.instance);
        block.setBlockName(createUnlocalizedName(block));
        GameRegistry.registerBlock(block, itemBlock, ((INameableItem)block).getName());
        if(!((INameableItem)block).getOredictName().isEmpty()) OreDictionary.registerOre(((INameableItem)block).getOredictName(), block);
    }

}
