/*
 * This file ("ItemAxeFixedEnchants.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items.tools.table;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.items.tools.ItemAxeAA;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemAxeFixedEnchants extends ItemAxeAA{

    public static class EnchantmentCombo{
        public Enchantment enchantment;
        public int level;

        public EnchantmentCombo(Enchantment ench, int level){
            this.enchantment = ench;
            this.level = level;
        }
    }

    private EnchantmentCombo[] enchantments;

    public ItemAxeFixedEnchants(ToolMaterial toolMat, String unlocalizedName, EnumRarity rarity, EnchantmentCombo... enchantments){
        super(toolMat, "", unlocalizedName, rarity);
        this.enchantments = enchantments;
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass){
        return false;
    }

    @Override
    public boolean isRepairable(){
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2){
        return this.isRepairable();
    }

    @Override
    public int getItemEnchantability(ItemStack stack){
        return 0;
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player){
        for(EnchantmentCombo combo : this.enchantments){
            stack.addEnchantment(combo.enchantment, combo.level);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        ItemStack stack = new ItemStack(item);
        for(EnchantmentCombo combo : this.enchantments){
            stack.addEnchantment(combo.enchantment, combo.level);
        }
        list.add(stack);
    }
}
