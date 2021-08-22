/*
 * This file ("ItemSwordAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.api.misc.IDisableableItem;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.config.ConfigurationHandler;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class ItemSwordAA extends SwordItem implements IDisableableItem {

    private final boolean disabled;

    public ItemSwordAA(IItemTier toolMat) {
        super(toolMat);


        this.disabled = ConfigurationHandler.config.getBoolean("Disable: " + StringUtil.badTranslate(this.name), "Tool Control", false, "This will disable the " + StringUtil.badTranslate(this.name) + ". It will not be registered.");
        if (!this.disabled) {
            this.register();
        }
    }

    private void register() {
    }

    protected Class<? extends ItemBlockBase> getItemBlock() {
        return ItemBlockBase.class;
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemToRepair, ItemStack stack) {
        return ItemUtil.areItemsEqual(this.repairItem, stack, false);
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }
}
