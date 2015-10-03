/*
 * This file ("ItemUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public class ItemUtil{

    public static Item getItemFromName(String name){
        if(Item.itemRegistry.containsKey(name)){
            return (Item)Item.itemRegistry.getObject(name);
        }
        return null;
    }

    public static void registerItems(Item[] items){
        for(Item item : items){
            register(item);
        }
    }

    public static void register(Item item){
        register(item, true);
    }

    public static void register(Item item, boolean addTab){
        item.setCreativeTab(addTab ? CreativeTab.instance : null);
        item.setUnlocalizedName(createUnlocalizedName(item));
        GameRegistry.registerItem(item, ((IActAddItemOrBlock)item).getName());
    }

    public static String createUnlocalizedName(Item item){
        return ModUtil.MOD_ID_LOWER+"."+((IActAddItemOrBlock)item).getName();
    }

    /**
     * Returns true if stacksOne contains every ItemStack in stackTwo
     */
    public static boolean containsAll(ItemStack[] stacksOne, ItemStack[] stacksTwo, boolean checkWildcard){
        for(ItemStack stackTwo : stacksTwo){
            if(!contains(stacksOne, stackTwo, checkWildcard)){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if array contains stack or if both contain null
     */
    public static boolean contains(ItemStack[] array, ItemStack stack, boolean checkWildcard){
        return getPlaceAt(array, stack, checkWildcard) != -1;
    }

    /**
     * Returns the place of stack in array, -1 if not present
     */
    public static int getPlaceAt(ItemStack[] array, ItemStack stack, boolean checkWildcard){
        if(array != null && array.length > 0){
            for(int i = 0; i < array.length; i++){
                if((stack == null && array[i] == null) || areItemsEqual(stack, array[i], checkWildcard)){
                    return i;
                }
            }
        }
        return -1;
    }

    public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2, boolean checkWildcard){
        return stack1 != null && stack2 != null && (stack1.isItemEqual(stack2) || (checkWildcard && stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == Util.WILDCARD || stack2.getItemDamage() == Util.WILDCARD)));
    }

    public static void addEnchantment(ItemStack stack, Enchantment e, int level){
        if(!hasEnchantment(stack, e)){
            stack.addEnchantment(e, level);
        }
    }

    public static boolean hasEnchantment(ItemStack stack, Enchantment e){
        NBTTagList ench = stack.getEnchantmentTagList();
        if(ench != null){
            for(int i = 0; i < ench.tagCount(); i++){
                short id = ench.getCompoundTagAt(i).getShort("id");
                if(id == e.effectId){
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeEnchantment(ItemStack stack, Enchantment e){
        NBTTagList ench = stack.getEnchantmentTagList();
        if(ench != null){
            for(int i = 0; i < ench.tagCount(); i++){
                short id = ench.getCompoundTagAt(i).getShort("id");
                if(id == e.effectId){
                    ench.removeTag(i);
                }
            }
        }
    }
}
