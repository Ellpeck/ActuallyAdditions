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

import ellpeck.actuallyadditions.items.tools.table.ItemPickaxeFixedEnchants.EnchantmentCombo;
import ellpeck.actuallyadditions.util.ItemUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;

public class InitToolTableTools{

    public static Item itemMinersPickaxe;
    public static Item itemSilkyMinersPickaxe;

    public static void init(){
        itemMinersPickaxe = new ItemPickaxeFixedEnchants(Item.ToolMaterial.EMERALD, "itemMinersPickaxe", EnumRarity.rare, new EnchantmentCombo(Enchantment.fortune, 2), new EnchantmentCombo(Enchantment.efficiency, 2), new EnchantmentCombo(Enchantment.unbreaking, 1));
        ItemUtil.register(itemMinersPickaxe);

        itemSilkyMinersPickaxe = new ItemPickaxeFixedEnchants(Item.ToolMaterial.EMERALD, "itemSilkyMinersPickaxe", EnumRarity.rare, new EnchantmentCombo(Enchantment.silkTouch, 1), new EnchantmentCombo(Enchantment.efficiency, 2), new EnchantmentCombo(Enchantment.unbreaking, 1));
        ItemUtil.register(itemSilkyMinersPickaxe);
    }
}
