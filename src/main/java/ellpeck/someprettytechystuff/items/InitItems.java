package ellpeck.someprettytechystuff.items;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.someprettytechystuff.blocks.InitBlocks;
import ellpeck.someprettytechystuff.booklet.ItemInfoBook;
import ellpeck.someprettytechystuff.items.tools.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class InitItems {

    public static Item itemInfoBook;
    public static Item itemGem;
    public static Item itemPile;

    public static Item itemSeedIron;
    public static Item itemSeedGold;
    public static Item itemSeedRedstone;
    public static Item itemSeedDiamond;

    public static Item itemInfusedIronIngot;
    public static Item itemInfusedIronAxe;
    public static Item itemInfusedIronHoe;
    public static Item itemInfusedIronPickaxe;
    public static Item itemInfusedIronShovel;
    public static Item itemInfusedIronSword;

    public static ToolMaterial infusedIronMaterial = EnumHelper.addToolMaterial("INFUSED_IRON", 3, 2000, 20.0F, 6.0F, 10);

    public static void init(){

        itemInfoBook = new ItemInfoBook();
        itemGem = new ItemGem();
        itemPile = new ItemPile();

        itemInfusedIronIngot = new ItemInfusedIron();
        itemSeedIron = new ItemSeed(InitBlocks.blockCropIron, new ItemStack(itemPile, 1, 0), "itemSeedIron");
        itemSeedGold = new ItemSeed(InitBlocks.blockCropGold, new ItemStack(itemPile, 1, 1), "itemSeedGold");
        itemSeedRedstone = new ItemSeed(InitBlocks.blockCropRedstone, new ItemStack(itemPile, 1, 2), "itemSeedRedstone");
        itemSeedDiamond = new ItemSeed(InitBlocks.blockCropDiamond, new ItemStack(itemPile, 1, 3), "itemSeedDiamond");

        itemInfusedIronAxe = new ItemAxeG(infusedIronMaterial, "itemInfusedIronAxe");
        itemInfusedIronHoe = new ItemHoeG(infusedIronMaterial, "itemInfusedIronHoe");
        itemInfusedIronPickaxe = new ItemPickaxeG(infusedIronMaterial, "itemInfusedIronPickaxe");
        itemInfusedIronShovel = new ItemShovelG(infusedIronMaterial, "itemInfusedIronShovel");
        itemInfusedIronSword = new ItemSwordG(infusedIronMaterial, "itemInfusedIronSword");

        GameRegistry.registerItem(itemInfoBook, itemInfoBook.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemGem, itemGem.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemPile, itemPile.getUnlocalizedName().substring(5));

        GameRegistry.registerItem(itemSeedIron, itemSeedIron.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemSeedGold, itemSeedGold.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemSeedRedstone, itemSeedRedstone.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemSeedDiamond, itemSeedDiamond.getUnlocalizedName().substring(5));

        GameRegistry.registerItem(itemInfusedIronIngot, itemInfusedIronIngot.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemInfusedIronAxe, itemInfusedIronAxe.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemInfusedIronHoe, itemInfusedIronHoe.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemInfusedIronPickaxe, itemInfusedIronPickaxe.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemInfusedIronShovel, itemInfusedIronShovel.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(itemInfusedIronSword, itemInfusedIronSword.getUnlocalizedName().substring(5));

    }
}
