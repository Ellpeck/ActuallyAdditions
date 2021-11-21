/*
 * This file ("ItemHoeAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;

public class ItemHoeAA extends HoeItem {
    public ItemHoeAA(IItemTier p_i231595_1_, int p_i231595_2_, float p_i231595_3_, Properties p_i231595_4_) {
        super(p_i231595_1_, p_i231595_2_, p_i231595_3_, p_i231595_4_);
    }

/*    private final ItemStack repairItem;
    private final boolean disabled;

    public ItemHoeAA(IItemTier toolMat) {
        super(toolMat);

        this.disabled = ConfigurationHandler.config.getBoolean("Disable: " + StringUtil.badTranslate(this.name), "Tool Control", false, "This will disable the " + StringUtil.badTranslate(this.name) + ". It will not be registered.");
        if (!this.disabled) {
            this.register();
        }
    }

    private void register() {
    }
    
    @Override
    public boolean isValidRepairItem(ItemStack itemToRepair, ItemStack stack) {
        return ItemUtil.areItemsEqual(this.repairItem, stack, false);
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }*/
}
