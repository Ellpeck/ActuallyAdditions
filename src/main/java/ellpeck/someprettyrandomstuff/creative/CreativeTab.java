package ellpeck.someprettyrandomstuff.creative;

import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheFoods;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs{

    public static CreativeTab instance = new CreativeTab();

    public CreativeTab(){
        super(Util.MOD_ID_LOWER);
    }

    @Override
    public Item getTabIconItem(){
        return InitItems.itemFoods;
    }

    @Override
    public ItemStack getIconItemStack(){
        return new ItemStack(this.getTabIconItem(), 1, TheFoods.SUBMARINE_SANDWICH.ordinal());
    }
}
