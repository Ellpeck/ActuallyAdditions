/*
 * This file ("ItemCrystal.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.BlockCrystal;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

public class ItemCrystal extends ItemBase {

    private final boolean isEmpowered;

    public ItemCrystal(String name, boolean isEmpowered) {
        super(name);
        this.isEmpowered = isEmpowered;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return this.isEmpowered;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return stack.getItemDamage() >= BlockCrystal.ALL_CRYSTALS.length ? StringUtil.BUGGED_ITEM_NAME : this.getTranslationKey() + "_" + BlockCrystal.ALL_CRYSTALS[stack.getItemDamage()].name;
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return stack.getItemDamage() >= BlockCrystal.ALL_CRYSTALS.length ? EnumRarity.COMMON : BlockCrystal.ALL_CRYSTALS[stack.getItemDamage()].rarity;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            for (int j = 0; j < BlockCrystal.ALL_CRYSTALS.length; j++) {
                list.add(new ItemStack(this, 1, j));
            }
        }
    }

    @Override
    protected void registerRendering() {
        for (int i = 0; i < BlockCrystal.ALL_CRYSTALS.length; i++) {
            String name = this.getRegistryName() + "_" + BlockCrystal.ALL_CRYSTALS[i].name;
            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), new ResourceLocation(name), "inventory");
        }
    }
}
