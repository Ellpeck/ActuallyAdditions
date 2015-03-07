package ellpeck.someprettyrandomstuff.oredict;

import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheDusts;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictRegistry{

    public static void init(){
        OreDictionary.registerOre("dustDiamond", new ItemStack(InitItems.itemDust, 1, TheDusts.DIAMOND.ordinal()));
        OreDictionary.registerOre("dustIron", new ItemStack(InitItems.itemDust, 1, TheDusts.IRON.ordinal()));
        OreDictionary.registerOre("dustGold", new ItemStack(InitItems.itemDust, 1, TheDusts.GOLD.ordinal()));
        OreDictionary.registerOre("dustEmerald", new ItemStack(InitItems.itemDust, 1, TheDusts.EMERALD.ordinal()));
        OreDictionary.registerOre("dustLapis", new ItemStack(InitItems.itemDust, 1, TheDusts.LAPIS.ordinal()));
    }
}
