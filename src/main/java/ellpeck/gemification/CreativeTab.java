package ellpeck.gemification;

import ellpeck.gemification.blocks.InitBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab extends CreativeTabs{

    public static CreativeTab instance = new CreativeTab();

    public CreativeTab(){
        super(Gemification.MOD_ID);
    }

    public Item getTabIconItem() {
        return Item.getItemFromBlock(InitBlocks.blockCrucible);
    }
}
