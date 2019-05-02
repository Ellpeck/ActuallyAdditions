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
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.config.ConfigurationHandler;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.IRarity;

public class ItemSwordAA extends ItemSword implements IDisableableItem {

    private final String name;
    private final IRarity rarity;
    private final ItemStack repairItem;
    private final boolean disabled;

    public ItemSwordAA(ToolMaterial toolMat, ItemStack repairItem, String unlocalizedName, IRarity rarity) {
        super(toolMat);

        this.repairItem = repairItem;
        this.name = unlocalizedName;
        this.rarity = rarity;

        this.disabled = ConfigurationHandler.config.getBoolean("Disable: " + StringUtil.badTranslate(this.name), "Tool Control", false, "This will disable the " + StringUtil.badTranslate(this.name) + ". It will not be registered.");
        if (!this.disabled) this.register();
    }

    private void register() {
        ItemUtil.registerItem(this, this.getBaseName(), this.shouldAddCreative());

        this.registerRendering();
    }

    protected String getBaseName() {
        return this.name;
    }

    public boolean shouldAddCreative() {
        return true;
    }

    protected void registerRendering() {
        ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
    }

    protected Class<? extends ItemBlockBase> getItemBlock() {
        return ItemBlockBase.class;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack) {
        return ItemUtil.areItemsEqual(this.repairItem, stack, false);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return this.rarity;
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }
}
