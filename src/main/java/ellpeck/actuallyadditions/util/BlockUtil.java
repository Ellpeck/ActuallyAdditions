package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil{

    public static String createUnlocalizedName(Block block){
        return ModUtil.MOD_ID_LOWER + "." + ((INameableItem)block).getName();
    }

    @SuppressWarnings("unchecked")
    public static void addInformation(Block block, List list, int lines, String extraName){
        if(KeyUtil.isShiftPressed()){
            for(int i = 0; i < lines; i++){
                list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+"."+((INameableItem)block).getName()+extraName+".desc"+(lines > 1 ? "."+(i+1) : "")));
            }
        }
        else list.add(ItemUtil.shiftForInfo());
    }

    @SuppressWarnings("unchecked")
    public static void addPowerUsageInfo(List list, int usage){
        if(KeyUtil.isShiftPressed()){
            list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".uses.desc") + " " + usage + " RF/t");
        }
    }

    @SuppressWarnings("unchecked")
    public static void addPowerProductionInfo(List list, int produce){
        if(KeyUtil.isShiftPressed()){
            list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".produces.desc") + " " + produce + " RF/t");
        }
    }

    public static ArrayList<Block> wailaRegisterList = new ArrayList<Block>();

    public static void register(Block block, Class<? extends ItemBlock> itemBlock, boolean addTab){
        block.setCreativeTab(addTab ? CreativeTab.instance : null);
        block.setBlockName(createUnlocalizedName(block));
        GameRegistry.registerBlock(block, itemBlock, ((INameableItem)block).getName());

        wailaRegisterList.add(block);
    }

    public static void register(Block block, Class<? extends ItemBlock> itemBlock){
        register(block, itemBlock, true);
    }
}
