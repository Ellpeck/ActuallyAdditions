/*
 * This file ("ItemUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.creative.CreativeTab;
import de.ellpeck.actuallyadditions.mod.util.compat.IMCHandler;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.*;


//TODO Remove the whole registry name mapping thing once the 1.11 fading phase is over
public final class ItemUtil{

    private static final Map<String, String> UNDERSCORELESS_TO_UNDERSCORED_NAMES = new HashMap<String, String>();

    public static Item getItemFromName(String name){
        ResourceLocation resLoc = new ResourceLocation(name);
        if(Item.REGISTRY.containsKey(resLoc)){
            return Item.REGISTRY.getObject(resLoc);
        }
        return null;
    }

    public static void registerBlock(Block block, ItemBlockBase itemBlock, String name, boolean addTab){
        block.setUnlocalizedName(ModUtil.MOD_ID+"."+name);

        block.setRegistryName(ModUtil.MOD_ID, name);
        GameRegistry.register(block);

        itemBlock.setRegistryName(block.getRegistryName());
        GameRegistry.register(itemBlock);

        block.setCreativeTab(addTab ? CreativeTab.INSTANCE : null);

        IMCHandler.doBlockIMC(block);

        if(block instanceof IColorProvidingBlock){
            ActuallyAdditions.proxy.addColoredBlock(block);
        }

        addUnderscoreNameToMapUnderscorelessName(block.getRegistryName());
    }

    public static void registerItem(Item item, String name, boolean addTab){
        item.setUnlocalizedName(ModUtil.MOD_ID+"."+name);

        item.setRegistryName(ModUtil.MOD_ID, name);
        GameRegistry.register(item);

        item.setCreativeTab(addTab ? CreativeTab.INSTANCE : null);

        IMCHandler.doItemIMC(item);

        if(item instanceof IColorProvidingItem){
            ActuallyAdditions.proxy.addColoredItem(item);
        }

        addUnderscoreNameToMapUnderscorelessName(item.getRegistryName());
    }

    private static void addUnderscoreNameToMapUnderscorelessName(ResourceLocation name){
        String underscoreless = name.toString().replaceAll("_", "");
        UNDERSCORELESS_TO_UNDERSCORED_NAMES.put(underscoreless, name.toString());
    }

    public static boolean remapName(FMLMissingMappingsEvent.MissingMapping mapping){
        if(mapping != null && mapping.name != null){
            if(mapping.name.toLowerCase(Locale.ROOT).contains("distributor")){
                mapping.ignore();
                return true;
            }

            if(UNDERSCORELESS_TO_UNDERSCORED_NAMES.containsKey(mapping.name)){
                String newName = UNDERSCORELESS_TO_UNDERSCORED_NAMES.get(mapping.name);
                ResourceLocation newResLoc = new ResourceLocation(newName);

                if(Block.REGISTRY.containsKey(newResLoc) && mapping.type == GameRegistry.Type.BLOCK){
                    mapping.remap(Block.REGISTRY.getObject(newResLoc));
                    return true;
                }
                else if(Item.REGISTRY.containsKey(newResLoc) && mapping.type == GameRegistry.Type.ITEM){
                    mapping.remap(Item.REGISTRY.getObject(newResLoc));
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean contains(ItemStack[] array, ItemStack stack, boolean checkWildcard){
        return getPlaceAt(array, stack, checkWildcard) != -1;
    }

    public static int getPlaceAt(ItemStack[] array, ItemStack stack, boolean checkWildcard){
        return getPlaceAt(Arrays.asList(array), stack, checkWildcard);
    }

    public static int getPlaceAt(List<ItemStack> list, ItemStack stack, boolean checkWildcard){
        if(list != null && list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                if((!StackUtil.isValid(stack) && !StackUtil.isValid(list.get(i))) || areItemsEqual(stack, list.get(i), checkWildcard)){
                    return i;
                }
            }
        }
        return -1;
    }

    public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2, boolean checkWildcard){
        return StackUtil.isValid(stack1) && StackUtil.isValid(stack2) && (stack1.isItemEqual(stack2) || (checkWildcard && stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == Util.WILDCARD || stack2.getItemDamage() == Util.WILDCARD)));
    }

    /**
     * Returns true if list contains stack or if both contain null
     */
    public static boolean contains(List<ItemStack> list, ItemStack stack, boolean checkWildcard){
        return !(list == null || list.isEmpty()) && getPlaceAt(list, stack, checkWildcard) != -1;
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
                if(id == Enchantment.getEnchantmentID(e)){
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
                if(id == Enchantment.getEnchantmentID(e)){
                    ench.removeTag(i);
                }
            }
            if(ench.hasNoTags() && stack.hasTagCompound()){
                stack.getTagCompound().removeTag("ench");
            }
        }
    }

    public static boolean canBeStacked(ItemStack stack1, ItemStack stack2){
        return ItemStack.areItemsEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    public static boolean isEnabled(ItemStack stack){
        return stack.hasTagCompound() && stack.getTagCompound().getBoolean("IsEnabled");
    }

    public static void changeEnabled(EntityPlayer player, EnumHand hand){
        changeEnabled(player.getHeldItem(hand));
    }

    public static void changeEnabled(ItemStack stack){
        if(!stack.hasTagCompound()){
            stack.setTagCompound(new NBTTagCompound());
        }

        boolean isEnabled = isEnabled(stack);
        stack.getTagCompound().setBoolean("IsEnabled", !isEnabled);
    }
}
