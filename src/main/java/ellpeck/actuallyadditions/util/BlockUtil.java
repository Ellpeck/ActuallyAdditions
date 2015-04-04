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
    public static void addStandardInformation(Block block, List list){
        if(KeyUtil.isShiftPressed()){
            list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + ((INameableItem)block).getName() + ".desc"));
            addOredictName(block, list);
        }
        else list.add(ItemUtil.shiftForInfo());
    }

    @SuppressWarnings("unchecked")
    public static void addOredictName(Block block, List list){
        list.add(StringUtil.GRAY + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".oredictName.desc") + ": " + ((INameableItem)block).getOredictName());
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock, Enum[] list){
        block.setCreativeTab(CreativeTab.instance);
        block.setBlockName(createUnlocalizedName(block));
        GameRegistry.registerBlock(block, itemBlock, ((INameableItem)block).getName());
        for(Enum current : list){
            OreDictionary.registerOre(((INameableItem)current).getOredictName(), new ItemStack(block, 1, current.ordinal()));
        }
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock){
        block.setCreativeTab(CreativeTab.instance);
        block.setBlockName(createUnlocalizedName(block));
        GameRegistry.registerBlock(block, itemBlock, ((INameableItem)block).getName());
        OreDictionary.registerOre(((INameableItem)block).getOredictName(), block);
    }

}
