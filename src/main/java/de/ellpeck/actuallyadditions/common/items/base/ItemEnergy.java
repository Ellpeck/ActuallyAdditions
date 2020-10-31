package de.ellpeck.actuallyadditions.common.items.base;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.tile.CustomEnergyStorage;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
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

    public ItemEnergy(Properties properties, int maxPower, int transfer, String name) {
        super(properties.maxStackSize(1), name);
        this.maxPower = maxPower;
        this.transfer = transfer;
    }

//    @Override
//    public boolean getShareTag() {
//        return true;
//    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> {
            NumberFormat format = NumberFormat.getInstance();
            // todo: migrate to i18n
            tooltip.add(new StringTextComponent(String.format("%s/%s Crystal Flux", format.format(cap.getEnergyStored()), format.format(cap.getMaxEnergyStored()))));
        });
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

// todo: check this out
//    @Override
//    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
//        if (this.isInCreativeTab(tabs)) {
//            ItemStack stackFull = new ItemStack(this);
//            if (stackFull.hasCapability(CapabilityEnergy.ENERGY, null)) {
//                IEnergyStorage storage = stackFull.getCapability(CapabilityEnergy.ENERGY, null);
//                if (storage != null) {
//                    this.setEnergy(stackFull, storage.getMaxEnergyStored());
//                    list.add(stackFull);
//                }
//            }
//
//            ItemStack stackEmpty = new ItemStack(this);
//            this.setEnergy(stackEmpty, 0);
//            list.add(stackEmpty);
//        }
//    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
                .map(cap -> {
                    double maxAmount = cap.getMaxEnergyStored();
                    double energyDif = maxAmount - cap.getEnergyStored();
                    return energyDif / maxAmount;
                })
                .orElse(super.getDurabilityForDisplay(stack));
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        PlayerEntity player = ActuallyAdditions.PROXY.getCurrentPlayer();
        if (player != null && player.world != null) {
            float[] color = AssetUtil.getWheelColor(player.world.getGameTime() % 256); // todo: validate that getGameTime is correct (check diff for old)
            return MathHelper.rgb(color[0] / 255F, color[1] / 255F, color[2] / 255F);
        }
        return super.getRGBDurabilityForDisplay(stack);
    }

    public void setEnergy(ItemStack stack, int energy) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((CustomEnergyStorage) cap).setEnergyStored(energy));
    }

    public int receiveEnergyInternal(ItemStack stack, int maxReceive, boolean simulate) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((CustomEnergyStorage) cap).receiveEnergyInternal(maxReceive, simulate));
        return 0;
    }

    public int extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> cap.extractEnergy(maxExtract, simulate));
        return 0;
    }

    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(cap -> cap.receiveEnergy(maxReceive, simulate)).orElse(0);
    }

    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(cap -> cap.extractEnergy(maxExtract, simulate)).orElse(0);
    }

    public int getEnergyStored(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getMaxEnergyStored(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return new EnergyCapabilityProvider(stack, this);
    }

    private static class EnergyCapabilityProvider implements ICapabilityProvider {

        public final CustomEnergyStorage storage;
        private final LazyOptional<CustomEnergyStorage> lazyStorage;

        public EnergyCapabilityProvider(final ItemStack stack, ItemEnergy item) {
            this.storage = new CustomEnergyStorage(item.maxPower, item.transfer, item.transfer) {
                @Override
                public int getEnergyStored() {
                    if (stack.hasTag()) {
                        return stack.getOrCreateTag().getInt("Energy");
                    } else {
                        return 0;
                    }
                }

                @Override
                public void setEnergyStored(int energy) {
                    stack.getOrCreateTag().putInt("Energy", energy);
                }
            };

            lazyStorage = LazyOptional.of(() -> this.storage);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == CapabilityEnergy.ENERGY) {
                return this.lazyStorage.cast();
            }

            return LazyOptional.empty();
        }
    }
}
