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

import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.tile.CustomEnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.Lang;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;

public abstract class ItemEnergy extends ItemBase {

    private final int maxPower;
    private final int transfer;

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

    // TODO: [port] make sure this is right
    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        return new CompoundNBT();
    }

    //    @Override
    //    public boolean getShareTag() {
    //        return true;
    //    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        stack.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(storage -> {
            NumberFormat format = NumberFormat.getInstance();
            tooltip.add(Lang.trans("misc", "power_long", format.format(storage.getEnergyStored()), format.format(storage.getMaxEnergyStored())));
        });
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemCategory(group, items);
        if (!this.allowdedIn(group)) {
            return;
        }

        ItemStack charged = new ItemStack(this);
        charged.getOrCreateTag().putDouble("Energy", this.getMaxEnergyStored(charged));
        items.add(charged);
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY, null)
            .map(cap -> {
                double maxAmount = cap.getMaxEnergyStored();
                double energyDif = maxAmount - cap.getEnergyStored();
                return energyDif / maxAmount;
            })
            .orElse(super.getDurabilityForDisplay(stack));
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        //float[] color = MathHelper.hsvToRgb(1, 1, 1);
        //float[] color = AssetUtil.getWheelColor(player.level.getGameTime() % 256);
        //return MathHelper.color(color[0] / 255F, color[1] / 255F, color[2] / 255F);
        return super.getRGBDurabilityForDisplay(stack);
    }

    public void setEnergy(ItemStack stack, int energy) {
        stack.getCapability(CapabilityEnergy.ENERGY, null).ifPresent(cap -> {
            if (cap instanceof CustomEnergyStorage) {
                ((CustomEnergyStorage) cap).setEnergyStored(energy);
            }
        });
    }

    @Deprecated
    public int receiveEnergyInternal(ItemStack stack, int maxReceive, boolean simulate) {
        //        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
        //            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
        //            if (storage instanceof CustomEnergyStorage) {
        //                ((CustomEnergyStorage) storage).receiveEnergyInternal(maxReceive, simulate);
        //            }
        //        }
        return 0;
    }

    public int extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
            .map(cap -> cap instanceof CustomEnergyStorage
                ? ((CustomEnergyStorage) cap).extractEnergyInternal(maxExtract, simulate)
                : 0)
            .orElse(0);
    }

    @Deprecated
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        //        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
        //            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
        //            if (storage != null) {
        //                return storage.receiveEnergy(maxReceive, simulate);
        //            }
        //        }
        return 0;
    }

    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
            .map(cap -> cap.extractEnergy(maxExtract, simulate))
            .orElse(0);
    }

    public int getEnergyStored(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY, null)
            .map(IEnergyStorage::getEnergyStored)
            .orElse(0);
    }

    public int getMaxEnergyStored(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY, null)
            .map(IEnergyStorage::getMaxEnergyStored)
            .orElse(0);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return new EnergyCapabilityProvider(stack, this);
    }

    private static class EnergyCapabilityProvider implements ICapabilityProvider {

        public final CustomEnergyStorage storage;
        private final LazyOptional<CustomEnergyStorage> energyCapability;

        public EnergyCapabilityProvider(ItemStack stack, ItemEnergy item) {
            this.storage = new CustomEnergyStorage(item.maxPower, item.transfer, item.transfer) {
                @Override
                public int getEnergyStored() {
                    return stack.getOrCreateTag().getInt("Energy");
                }

                @Override
                public void setEnergyStored(int energy) {
                    stack.getOrCreateTag().putInt("Energy", energy);
                }
            };
            this.energyCapability = LazyOptional.of(() -> this.storage);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == CapabilityEnergy.ENERGY) {
                return this.energyCapability.cast();
            }
            return LazyOptional.empty();
        }
    }
}
