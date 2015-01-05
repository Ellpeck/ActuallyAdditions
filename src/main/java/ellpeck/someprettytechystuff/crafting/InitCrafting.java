package ellpeck.someprettytechystuff.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.someprettytechystuff.blocks.InitBlocks;
import ellpeck.someprettytechystuff.items.InitItems;
import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InitCrafting {

    public static void init(){
        GameRegistry.addRecipe(new ItemStack(InitBlocks.blockCrucible), "i i", "gcg", "iii", 'i', Items.iron_ingot, 'g', new ItemStack(InitItems.itemGem, 1, OreDictionary.WILDCARD_VALUE), 'c', Items.cauldron);
        GameRegistry.addRecipe(new ItemStack(InitBlocks.blockCrucibleFire), "ccc", "cac", "csc", 'c', Blocks.cobblestone, 'a', Items.cauldron, 's', Blocks.stone_slab);
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemInfoBook), new ItemStack(Items.book), new ItemStack(InitItems.itemGem, 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addRecipe(new ItemStack(Items.iron_ingot), "xx", "xx", 'x', new ItemStack(InitItems.itemPile, 1, 0));
        GameRegistry.addRecipe(new ItemStack(Items.gold_ingot), "xx", "xx", 'x', new ItemStack(InitItems.itemPile, 1, 1));
        GameRegistry.addRecipe(new ItemStack(Items.redstone), "xx", "xx", 'x', new ItemStack(InitItems.itemPile, 1, 2));
        GameRegistry.addRecipe(new ItemStack(Items.diamond), "xx", "xx", 'x', new ItemStack(InitItems.itemPile, 1, 3));
        GameRegistry.addRecipe(new ItemStack(InitItems.itemPile, 8, 0), "x", "x", 'x', new ItemStack(Items.iron_ingot));
        GameRegistry.addRecipe(new ItemStack(InitItems.itemPile, 8, 1), "x", "x", 'x', new ItemStack(Items.gold_ingot));
        GameRegistry.addRecipe(new ItemStack(InitItems.itemPile, 8, 2), "x", "x", 'x', new ItemStack(Items.redstone));
        GameRegistry.addRecipe(new ItemStack(InitItems.itemPile, 8, 3), "x", "x", 'x', new ItemStack(Items.diamond));

        CrucibleCraftingManager.instance.addRecipe(new ItemStack(Blocks.acacia_stairs), Util.chromeDiopside, 200, "ccc", "cgc", "ccc", 'c', Blocks.cobblestone, 'g', Items.stick);

        CrucibleCraftingManager.instance.addRecipe(new ItemStack(InitItems.itemInfusedIronIngot), Util.goshenite, 500, "gig", "idi", "gig", 'g', new ItemStack(InitItems.itemGem, 1, OreDictionary.WILDCARD_VALUE), 'i', Items.iron_ingot, 'd', Items.diamond);
        CrucibleCraftingManager.instance.addRecipe(new ItemStack(InitItems.itemInfusedIronAxe), Util.chromeDiopside, 1000, "gig", "iai", "gig", 'g', new ItemStack(InitItems.itemGem, 1, OreDictionary.WILDCARD_VALUE), 'i', InitItems.itemInfusedIronIngot, 'a', Items.iron_axe);
        CrucibleCraftingManager.instance.addRecipe(new ItemStack(InitItems.itemInfusedIronHoe), Util.jasper, 1000, "gig", "iai", "gig", 'g', new ItemStack(InitItems.itemGem, 1, OreDictionary.WILDCARD_VALUE), 'i', InitItems.itemInfusedIronIngot, 'a', Items.iron_hoe);
        CrucibleCraftingManager.instance.addRecipe(new ItemStack(InitItems.itemInfusedIronPickaxe), Util.hematite, 1000, "gig", "iai", "gig", 'g', new ItemStack(InitItems.itemGem, 1, OreDictionary.WILDCARD_VALUE), 'i', InitItems.itemInfusedIronIngot, 'a', Items.iron_pickaxe);
        CrucibleCraftingManager.instance.addRecipe(new ItemStack(InitItems.itemInfusedIronShovel), Util.tourmaline, 1000, "gig", "iai", "gig", 'g', new ItemStack(InitItems.itemGem, 1, OreDictionary.WILDCARD_VALUE), 'i', InitItems.itemInfusedIronIngot, 'a', Items.iron_shovel);
        CrucibleCraftingManager.instance.addRecipe(new ItemStack(InitItems.itemInfusedIronSword), Util.almandineGarnet, 1000, "gig", "iai", "gig", 'g', new ItemStack(InitItems.itemGem, 1, OreDictionary.WILDCARD_VALUE), 'i', InitItems.itemInfusedIronIngot, 'a', Items.iron_sword);
    }

}
