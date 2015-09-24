/*
 * This file ("InitToolTableTools.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items.tools.table;

import ellpeck.actuallyadditions.util.ItemUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InitToolTableTools{

    public static Item itemMinersPickaxe;
    public static Item itemSilkyMinersPickaxe;

    public static Item itemDiggersShovel;
    public static Item itemFastDiggersShovel;

    public static void init(){
        itemMinersPickaxe = new ItemPickaxeFixedEnchants(Item.ToolMaterial.EMERALD, "itemMinersPickaxe", EnumRarity.rare, new ItemStack(Items.diamond), 400, new EnchantmentCombo(Enchantment.fortune, 2), new EnchantmentCombo(Enchantment.efficiency, 2), new EnchantmentCombo(Enchantment.unbreaking, 1));
        ItemUtil.register(itemMinersPickaxe);
        itemSilkyMinersPickaxe = new ItemPickaxeFixedEnchants(Item.ToolMaterial.EMERALD, "itemSilkyMinersPickaxe", EnumRarity.epic, new ItemStack(Items.diamond), 400, new EnchantmentCombo(Enchantment.silkTouch, 1), new EnchantmentCombo(Enchantment.efficiency, 2), new EnchantmentCombo(Enchantment.unbreaking, 1));
        ItemUtil.register(itemSilkyMinersPickaxe);

        itemDiggersShovel = new ItemShovelFixedEnchants(Item.ToolMaterial.EMERALD, "itemDiggersShovel", EnumRarity.rare, new ItemStack(Items.diamond), 400, new EnchantmentCombo(Enchantment.efficiency, 2), new EnchantmentCombo(Enchantment.unbreaking, 2));
        ItemUtil.register(itemDiggersShovel);
        itemFastDiggersShovel = new ItemShovelFixedEnchants(Item.ToolMaterial.EMERALD, "itemFastDiggersShovel", EnumRarity.epic, new ItemStack(Items.diamond), 400, new EnchantmentCombo(Enchantment.efficiency, 4), new EnchantmentCombo(Enchantment.unbreaking, 1));
        ItemUtil.register(itemFastDiggersShovel);
    }
}
