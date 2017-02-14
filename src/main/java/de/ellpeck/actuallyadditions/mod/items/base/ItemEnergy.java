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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.tile.CustomEnergyStorage;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaForgeUnitsWrapper;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;

public abstract class ItemEnergy extends ItemBase{

    private final int maxPower;
    private final int transfer;

    public ItemEnergy(int maxPower, int transfer, String name){
        super(name);
        this.maxPower = maxPower;
        this.transfer = transfer;

        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean getShareTag(){
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                NumberFormat format = NumberFormat.getInstance();
                list.add(String.format("%s/%s Crystal Flux",format.format(storage.getEnergyStored()),format.format(storage.getMaxEnergyStored())));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, NonNullList list){
        ItemStack stackFull = new ItemStack(this);
        if(stackFull.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stackFull.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                this.setEnergy(stackFull, storage.getMaxEnergyStored());
                list.add(stackFull);
            }
        }

        ItemStack stackEmpty = new ItemStack(this);
        this.setEnergy(stackEmpty, 0);
        list.add(stackEmpty);
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack){
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                double maxAmount = storage.getMaxEnergyStored();
                double energyDif = maxAmount-storage.getEnergyStored();
                return energyDif/maxAmount;
            }
        }
        return super.getDurabilityForDisplay(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack){
        EntityPlayer player = ActuallyAdditions.proxy.getCurrentPlayer();
        if(player != null && player.world != null){
            float[] color = AssetUtil.getWheelColor(player.world.getTotalWorldTime()%256);
            return MathHelper.rgb(color[0]/255F, color[1]/255F, color[2]/255F);
        }
        return super.getRGBDurabilityForDisplay(stack);
    }

    public void setEnergy(ItemStack stack, int energy){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage instanceof CustomEnergyStorage){
                ((CustomEnergyStorage)storage).setEnergyStored(energy);
            }
        }
    }

    public int receiveEnergyInternal(ItemStack stack, int maxReceive, boolean simulate){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage instanceof CustomEnergyStorage){
                ((CustomEnergyStorage)storage).receiveEnergyInternal(maxReceive, simulate);
            }
        }
        return 0;
    }

    public int extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage instanceof CustomEnergyStorage){
                ((CustomEnergyStorage)storage).extractEnergyInternal(maxExtract, simulate);
            }
        }
        return 0;
    }

    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                return storage.receiveEnergy(maxReceive, simulate);
            }
        }
        return 0;
    }

    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                return storage.extractEnergy(maxExtract, simulate);
            }
        }
        return 0;
    }

    public int getEnergyStored(ItemStack stack){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                return storage.getEnergyStored();
            }
        }
        return 0;
    }

    public int getMaxEnergyStored(ItemStack stack){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                return storage.getMaxEnergyStored();
            }
        }
        return 0;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt){
        return new EnergyCapabilityProvider(stack, this);
    }

    private static class EnergyCapabilityProvider implements ICapabilityProvider{

        public final CustomEnergyStorage storage;
        private Object teslaWrapper;

        public EnergyCapabilityProvider(final ItemStack stack, ItemEnergy item){
            this.storage = new CustomEnergyStorage(item.maxPower, item.transfer, item.transfer){
                @Override
                public int getEnergyStored(){
                    if(stack.hasTagCompound()){
                        return stack.getTagCompound().getInteger("Energy");
                    }
                    else{
                        return 0;
                    }
                }

                @Override
                public void setEnergyStored(int energy){
                    if(!stack.hasTagCompound()){
                        stack.setTagCompound(new NBTTagCompound());
                    }

                    stack.getTagCompound().setInteger("Energy", energy);
                }
            };
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing){
            return this.getCapability(capability, facing) != null;
        }

        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing){
            if(capability == CapabilityEnergy.ENERGY){
                return (T)this.storage;
            }
            else if(ActuallyAdditions.teslaLoaded){
                if(capability == TeslaUtil.teslaConsumer || capability == TeslaUtil.teslaProducer || capability == TeslaUtil.teslaHolder){
                    if(this.teslaWrapper == null){
                        this.teslaWrapper = new TeslaForgeUnitsWrapper(this.storage);
                    }
                    return (T)this.teslaWrapper;
                }
            }
            return null;
        }
    }
}
