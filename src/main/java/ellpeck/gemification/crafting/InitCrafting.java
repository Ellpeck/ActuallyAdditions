package ellpeck.gemification.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.gemification.blocks.InitBlocks;
import ellpeck.gemification.items.InitItems;
import ellpeck.gemification.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InitCrafting {

    public static void init(){
        GameRegistry.addRecipe(new ItemStack(InitBlocks.blockCrucible), "i i", "gcg", "iii", 'i', Items.iron_ingot, 'g', new ItemStack(InitItems.itemGem, 1, OreDictionary.WILDCARD_VALUE), 'c', Items.cauldron);
        GameRegistry.addRecipe(new ItemStack(InitBlocks.blockCrucibleFire), "ccc", "cac", "sss", 'c', Blocks.cobblestone, 'a', Items.cauldron, 's', Blocks.stone_slab);

        CrucibleCraftingManager.instance.addRecipe(new ItemStack(Blocks.acacia_stairs), Util.chromeDiopside, 200, "ccc", "cgc", "ccc", 'c', Blocks.cobblestone, 'g', Items.stick);
    }

}
