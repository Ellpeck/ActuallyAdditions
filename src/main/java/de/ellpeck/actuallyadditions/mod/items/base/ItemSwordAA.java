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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ItemSwordAA extends SwordItem {
    private Tier tier;
    public ItemSwordAA(Tier tier) {
        super(tier,
            (int) tier.getAttackDamageBonus(),
            tier.getSpeed(),
            new Properties()
//                .addToolType(ToolType.AXE, tier.getLevel())
//                .addToolType(ToolType.HOE, tier.getLevel())
//                .addToolType(ToolType.SHOVEL, tier.getLevel())
//                .addToolType(ToolType.PICKAXE, tier.getLevel())
                .durability(tier.getUses() * 4)
                .tab(ActuallyAdditions.GROUP)
        );
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
