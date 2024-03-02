/*
 * This file ("ItemArmorAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.api.misc.IDisableableItem;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class ItemArmorAA extends ArmorItem implements IDisableableItem {
    private final boolean disabled;

    public ItemArmorAA(ArmorMaterial material, EquipmentSlot type) {
        this(material, type, ActuallyItems.defaultProps());
    }

    public ItemArmorAA(ArmorMaterial material, EquipmentSlot type, Properties properties) {
        super(material, type, properties);
        this.disabled = false;
        //        this.disabled = ConfigurationHandler.config.getBoolean("Disable: " + StringUtil.badTranslate(this.name), "Tool Control", false, "This will disable the " + StringUtil.badTranslate(this.name) + ". It will not be registered.");
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }
}
