package de.ellpeck.actuallyadditions.common.items.base;

import java.text.NumberFormat;
import java.util.List;

import javax.annotation.Nullable;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.tile.CustomEnergyStorage;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemEnergy extends ItemBase {

    private final int maxPower;
    private final int transfer;

    public ItemEnergy(int maxPower, int transfer, String name) {
        super(name);
        this.maxPower = maxPower;
        this.transfer = transfer;

        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, World playerIn, List<String> tooltip, ITooltipFlag advanced) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) {
                NumberFormat format = NumberFormat.getInstance();
                tooltip.add(String.format("%s/%s Crystal Flux", format.format(storage.getEnergyStored()), format.format(storage.getMaxEnergyStored())));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

    @Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tabs)) {
            ItemStack stackFull = new ItemStack(this);
            if (stackFull.hasCapability(CapabilityEnergy.ENERGY, null)) {
                IEnergyStorage storage = stackFull.getCapability(CapabilityEnergy.ENERGY, null);
                if (storage != null) {
                    this.setEnergy(stackFull, storage.getMaxEnergyStored());
                    list.add(stackFull);
                }
            }

            ItemStack stackEmpty = new ItemStack(this);
            this.setEnergy(stackEmpty, 0);
            list.add(stackEmpty);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) {
                double maxAmount = storage.getMaxEnergyStored();
                double energyDif = maxAmount - storage.getEnergyStored();
                return energyDif / maxAmount;
            }
        }
        return super.getDurabilityForDisplay(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        EntityPlayer player = ActuallyAdditions.PROXY.getCurrentPlayer();
        if (player != null && player.world != null) {
            float[] color = AssetUtil.getWheelColor(player.world.getTotalWorldTime() % 256);
            return MathHelper.rgb(color[0] / 255F, color[1] / 255F, color[2] / 255F);
        }
        return super.getRGBDurabilityForDisplay(stack);
    }

    public void setEnergy(ItemStack stack, int energy) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage instanceof CustomEnergyStorage) {
                ((CustomEnergyStorage) storage).setEnergyStored(energy);
            }
        }
    }

    public int receiveEnergyInternal(ItemStack stack, int maxReceive, boolean simulate) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage instanceof CustomEnergyStorage) {
                ((CustomEnergyStorage) storage).receiveEnergyInternal(maxReceive, simulate);
            }
        }
        return 0;
    }

    public int extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage instanceof CustomEnergyStorage) {
                ((CustomEnergyStorage) storage).extractEnergyInternal(maxExtract, simulate);
            }
        }
        return 0;
    }

    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) { return storage.receiveEnergy(maxReceive, simulate); }
        }
        return 0;
    }

    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) { return storage.extractEnergy(maxExtract, simulate); }
        }
        return 0;
    }

    public int getEnergyStored(ItemStack stack) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) { return storage.getEnergyStored(); }
        }
        return 0;
    }

    public int getMaxEnergyStored(ItemStack stack) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) { return storage.getMaxEnergyStored(); }
        }
        return 0;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new EnergyCapabilityProvider(stack, this);
    }

    private static class EnergyCapabilityProvider implements ICapabilityProvider {

        public final CustomEnergyStorage storage;

        public EnergyCapabilityProvider(final ItemStack stack, ItemEnergy item) {
            this.storage = new CustomEnergyStorage(item.maxPower, item.transfer, item.transfer) {
                @Override
                public int getEnergyStored() {
                    if (stack.hasTagCompound()) {
                        return stack.getTagCompound().getInteger("Energy");
                    } else {
                        return 0;
                    }
                }

                @Override
                public void setEnergyStored(int energy) {
                    if (!stack.hasTagCompound()) {
                        stack.setTagCompound(new NBTTagCompound());
                    }

                    stack.getTagCompound().setInteger("Energy", energy);
                }
            };
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return this.getCapability(capability, facing) != null;
        }

        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if (capability == CapabilityEnergy.ENERGY) { return CapabilityEnergy.ENERGY.cast(this.storage); }
            return null;
        }
    }
}
