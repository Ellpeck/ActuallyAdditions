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

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

public class ItemHoeAA extends HoeItem {
    private Tier tier;
    public ItemHoeAA(Tier tier) {
        super(tier,
            4,
            -2f,
            new Properties()
//                .addToolType(ToolType.AXE, tier.getLevel())
//                .addToolType(ToolType.HOE, tier.getLevel())
//                .addToolType(ToolType.SHOVEL, tier.getLevel())
//                .addToolType(ToolType.PICKAXE, tier.getLevel())
                .durability(tier.getUses() * 4)
                
        );

        this.tier = tier;
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
