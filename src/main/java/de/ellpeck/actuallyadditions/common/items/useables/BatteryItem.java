package de.ellpeck.actuallyadditions.common.items.useables;

import de.ellpeck.actuallyadditions.common.items.CrystalFluxItem;
import de.ellpeck.actuallyadditions.common.items.IActivates;
import de.ellpeck.actuallyadditions.common.utilities.Help;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class BatteryItem extends CrystalFluxItem implements IActivates {
    public BatteryItem(Supplier<Integer> maxFlux, int transfer) {
        super(baseProps().maxStackSize(1).setNoRepair().maxDamage(0), maxFlux, transfer);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (world.isRemote || !(entity instanceof PlayerEntity) || !isEnabled(stack) || isSelected) {
            return;
        }

        // Don't run if we have no charge. Duh
        if (stack.getCapability(CapabilityEnergy.ENERGY)
                .map(e -> e.getEnergyStored() <= 0)
                .orElse(true)
        ) {
            return;
        }

        PlayerEntity player = (PlayerEntity) entity;
        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack slot = player.inventory.getStackInSlot(i);

            // We don't care for weirdo stackable charging items, nor the same item as is charging others. Duh
            if (slot.isEmpty() || slot.getCount() != 1 || slot.equals(stack)) {
                continue;
            }

            slot.getCapability(CapabilityEnergy.ENERGY).ifPresent(energy -> {
                // Don't keep charging if empty
                if (energy.getEnergyStored() >= energy.getMaxEnergyStored()) {
                    return;
                }

                // Find max power from the battery and use that to attempt to charge the other item
                int maxExtractable = stack.getCapability(CapabilityEnergy.ENERGY).map(e -> e.extractEnergy(Integer.MAX_VALUE, true)).orElse(0);
                int received = energy.receiveEnergy(maxExtractable, false);

                if (received > 0) {
                    stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> e.extractEnergy(received, false));
                }
            });
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (!world.isRemote && player.isSneaking()) {
            ItemStack heldItem = player.getHeldItem(hand);

            toggle(heldItem);
            return ActionResult.resultSuccess(heldItem);
        }

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        boolean enabled = isEnabled(stack);
        tooltip.add(Help.trans(String.format("tooltip.battery.%s", enabled ? "charging" : "not-charging")).mergeStyle(enabled ? TextFormatting.GREEN : TextFormatting.GRAY));
        tooltip.add(Help.trans("tooltip.battery.charge-help"));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isEnabled(stack);
    }
}
