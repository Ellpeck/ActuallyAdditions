/*
 * This file ("ItemKnife.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import com.google.common.collect.Multimap;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemKnife extends ItemBase{

    public ItemKnife(String name){
        super(name);
        this.setMaxDamage(100);
        this.setMaxStackSize(1);
        this.setContainerItem(this);
        this.setNoRepair();
    }

    @Override
    public boolean getShareTag(){
        return true;
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }


    @Override
    public Multimap getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
        Multimap map = super.getAttributeModifiers(slot, stack);
        if(slot == EntityEquipmentSlot.MAINHAND){
            map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Knife Modifier", 3, 0));
        }
        return map;
    }


    @Override
    public ItemStack getContainerItem(ItemStack stack){
        ItemStack theStack = stack.copy();
        theStack.setItemDamage(theStack.getItemDamage()+1);
        return StackUtil.setStackSize(theStack, 1);
    }
}
