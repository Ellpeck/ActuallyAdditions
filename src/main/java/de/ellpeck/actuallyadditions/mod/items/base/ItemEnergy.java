/*
 * This file ("ItemEnergy.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.mod.attachments.ActuallyAttachments;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.tile.CustomEnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

public abstract class ItemEnergy extends ItemBase {

    public final int maxPower;
    public final int transfer;

    public ItemEnergy(int maxPower, int transfer) {
        super(ActuallyItems.defaultProps().stacksTo(1));
        this.maxPower = maxPower;
        this.transfer = transfer;
    }
    public ItemEnergy(Properties props, int maxPower, int transfer) {
        super(props);
        this.maxPower = maxPower;
        this.transfer = transfer;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        IEnergyStorage storage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if(storage != null) {
            int energy = storage.getEnergyStored();
            NumberFormat format = NumberFormat.getInstance();
            tooltip.add(Component.translatable("misc.actuallyadditions.power_long", format.format(energy), format.format(storage.getMaxEnergyStored()))
                    .withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage storage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (storage != null) {
            return Math.round((13.0F / storage.getMaxEnergyStored() * storage.getEnergyStored()));
        }
        return 0;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int defaultColor = super.getBarColor(stack);
        if (FMLEnvironment.dist.isClient()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return defaultColor;
            float[] color = AssetUtil.getWheelColor(mc.player.level().getGameTime() % 256);
            return Mth.color(color[0] / 255F, color[1] / 255F, color[2] / 255F);
        }
        return defaultColor;
    }

    public void setEnergy(ItemStack stack, int energy) {
        Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM)).ifPresent(cap -> {
            if (cap instanceof CustomEnergyStorage) {
                ((CustomEnergyStorage) cap).setEnergyStored(energy);
            }
        });
    }

    @Deprecated
    public int receiveEnergyInternal(ItemStack stack, int maxReceive, boolean simulate) {
        return Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
            .map(cap -> ((CustomEnergyStorage) cap).receiveEnergyInternal(maxReceive, simulate))
            .orElse(0);
    }

    public int extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate) {
        return Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
            .map(cap -> cap instanceof EnergyStorage
                ? ((CustomEnergyStorage) cap).extractEnergyInternal(maxExtract, simulate)
                : 0)
            .orElse(0);
    }

    @Deprecated
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        return Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
            .map(cap -> cap.receiveEnergy(maxReceive, simulate))
            .orElse(0);
    }

    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        return Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
            .map(cap -> cap.extractEnergy(maxExtract, simulate))
            .orElse(0);
    }

    public int getEnergyStored(ItemStack stack) {
        return Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
            .map(IEnergyStorage::getEnergyStored)
            .orElse(0);
    }

    public int getMaxEnergyStored(ItemStack stack) {
        return Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
            .map(IEnergyStorage::getMaxEnergyStored)
            .orElse(0);
    }

	public IEnergyStorage getEnergyStorage(ItemStack stack) {
		return stack.getData(ActuallyAttachments.ENERGY_STORAGE);
	}

//    @Override TODO: Register Energy cap/attachment
//    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
//        return new EnergyCapabilityProvider(stack, this);
//    }

//    private static class EnergyCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
//
//        public final CustomEnergyStorage storage;
//        private final LazyOptional<CustomEnergyStorage> energyCapability;
//
//        private final ItemStack stack;
//
//        public EnergyCapabilityProvider(ItemStack stack, ItemEnergy item) {
//            this.storage = new CustomEnergyStorage(item.maxPower, item.transfer, item.transfer);
//            this.energyCapability = LazyOptional.of(() -> this.storage);
//            this.stack = stack;
//        }
//
//        @Nonnull
//        @Override
//        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//            if (cap == Capabilities.ENERGY) {
//                return this.energyCapability.cast();
//            }
//            return LazyOptional.empty();
//        }
//
//        @Override
//        public CompoundTag serializeNBT() {
//            if (this.storage.isDirty())
//                stack.getOrCreateTag().putInt("Energy", this.storage.getEnergyStored());
//            this.storage.clearDirty();
//            return new CompoundTag();
//        }
//
//        @Override
//        public void deserializeNBT(CompoundTag nbt) {
//            if (stack.getOrCreateTag().contains("Energy"))
//                this.storage.setEnergyStored(stack.getOrCreateTag().getInt("Energy"));
//        }
//    }
}
