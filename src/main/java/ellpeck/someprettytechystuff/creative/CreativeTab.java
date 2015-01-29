package ellpeck.someprettytechystuff.creative;

import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTab extends CreativeTabs{

    public static CreativeTab instance = new CreativeTab();

    public CreativeTab(){
        super(Util.MOD_ID);
    }

    public Item getTabIconItem() {
        return Items.command_block_minecart;
    }
}
