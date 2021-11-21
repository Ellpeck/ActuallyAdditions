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

import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;

public class ItemSwordAA extends SwordItem {
    public ItemSwordAA(IItemTier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
        super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_);
    }

/*    private final boolean disabled;

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
    }*/
}
