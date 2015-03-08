package ellpeck.actuallyadditions.oredict;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheDusts;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictRegistry{

    public static void init(){
        OreDictionary.registerOre("dustDiamond", new ItemStack(InitItems.itemDust, 1, TheDusts.DIAMOND.ordinal()));
        OreDictionary.registerOre("dustIron", new ItemStack(InitItems.itemDust, 1, TheDusts.IRON.ordinal()));
        OreDictionary.registerOre("dustGold", new ItemStack(InitItems.itemDust, 1, TheDusts.GOLD.ordinal()));
        OreDictionary.registerOre("dustEmerald", new ItemStack(InitItems.itemDust, 1, TheDusts.EMERALD.ordinal()));
        OreDictionary.registerOre("dustLapis", new ItemStack(InitItems.itemDust, 1, TheDusts.LAPIS.ordinal()));
        OreDictionary.registerOre("dustCoal", new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()));
        OreDictionary.registerOre("dustQuartz", new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ.ordinal()));
        OreDictionary.registerOre("dustQuartzBlack", new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ_BLACK.ordinal()));
        OreDictionary.registerOre("oreQuartzBlack", new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal()));
        OreDictionary.registerOre("itemQuartzBlack", new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()));
    }
}
