package ellpeck.gemification.creative;

import ellpeck.gemification.blocks.InitBlocks;
import ellpeck.gemification.util.Util;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab extends CreativeTabs{

    public static CreativeTab instance = new CreativeTab();

    public CreativeTab(){
        super(Util.MOD_ID);
    }

    public Item getTabIconItem() {
        return Item.getItemFromBlock(InitBlocks.blockCrucible);
    }
}
