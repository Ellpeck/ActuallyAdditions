/*
 * This file ("ItemBattery.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

public class ItemBattery extends ItemEnergy {

    public ItemBattery(int capacity, int transfer) {
        super(capacity, transfer);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return ItemUtil.isEnabled(stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && entity instanceof PlayerEntity && ItemUtil.isEnabled(stack) && !isSelected) {
            PlayerEntity player = (PlayerEntity) entity;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack slot = player.inventory.getStackInSlot(i);
                if (StackUtil.isValid(slot) && slot.getCount() == 1) {
                    int extractable = this.extractEnergy(stack, Integer.MAX_VALUE, true);
                    int received = 0;

                    if (slot.hasCapability(CapabilityEnergy.ENERGY, null)) {
                        IEnergyStorage cap = slot.getCapability(CapabilityEnergy.ENERGY, null);
                        if (cap != null) {
                            received = cap.receiveEnergy(extractable, false);
                        }
                    }

                    if (received > 0) {
                        this.extractEnergy(stack, received, false);
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
        if (!worldIn.isRemote && player.isSneaking()) {
            ItemUtil.changeEnabled(player, hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
        return super.onItemRightClick(worldIn, player, hand);
    }

    @Override
    public void addInformation(ItemStack stack, World playerIn, List<String> list, ITooltipFlag advanced) {
        super.addInformation(stack, playerIn, list, advanced);
        list.add(StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".battery." + (ItemUtil.isEnabled(stack)
            ? "discharge"
            : "noDischarge")));
        list.add(StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".battery.changeMode"));
    }
}
