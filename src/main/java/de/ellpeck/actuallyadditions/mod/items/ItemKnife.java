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
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class ItemKnife extends ItemBase {

    public ItemKnife() {
        super(ActuallyItems.defaultNonStacking().defaultDurability(100).setNoRepair());
    }


    //    @Override
    //    public boolean getShareTag() {
    //        return true;
    //    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        if (slot == EquipmentSlotType.MAINHAND) {
            // TODO: [port] validate
            map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier("Knife Modifier", 3, AttributeModifier.Operation.ADDITION));
        }
        return map;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        ItemStack theStack = stack.copy();
        theStack.setDamageValue(theStack.getDamageValue() + 1);
        return theStack;
    }
}
