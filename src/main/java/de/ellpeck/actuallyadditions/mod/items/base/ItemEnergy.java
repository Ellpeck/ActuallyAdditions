/*
 * This file ("ItemEnergy.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyStorage;
import cofh.api.energy.ItemEnergyContainer;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.ItemTeslaWrapper;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;

public abstract class ItemEnergy extends ItemEnergyContainer{

    private final String name;

    public ItemEnergy(int maxPower, int transfer, String name){
        super(maxPower, transfer);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.name = name;

        this.register();
    }

    private void register(){
        ItemUtil.registerItem(this, this.getBaseName(), this.shouldAddCreative());

        this.registerRendering();
    }

    protected String getBaseName(){
        return this.name;
    }

    public boolean shouldAddCreative(){
        return true;
    }

    protected void registerRendering(){
        ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
    }

    @Override
    public boolean getShareTag(){
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        NumberFormat format = NumberFormat.getInstance();
        list.add(format.format(this.getEnergyStored(stack))+"/"+format.format(this.getMaxEnergyStored(stack))+" Crystal Flux");
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
        this.setEnergy(stackFull, this.getMaxEnergyStored(stackFull));
        list.add(stackFull);

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
        double maxAmount = this.getMaxEnergyStored(stack);
        double energyDif = maxAmount-this.getEnergyStored(stack);
        return energyDif/maxAmount;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack){
        int currEnergy = this.getEnergyStored(stack);
        int maxEnergy = this.getMaxEnergyStored(stack);
        return MathHelper.hsvToRGB(Math.max(0.0F, (float)currEnergy/maxEnergy)/3.0F, 1.0F, 1.0F);
    }

    public void setEnergy(ItemStack stack, int energy){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null){
            compound = new NBTTagCompound();
        }
        compound.setInteger("Energy", energy);
        stack.setTagCompound(compound);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt){
        return new EnergyCapabilityProvider(stack, this);
    }

    public int extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate){
        int before = this.maxExtract;
        this.setMaxExtract(Integer.MAX_VALUE);

        int toReturn = this.extractEnergy(stack, maxExtract, simulate);

        this.setMaxExtract(before);
        return toReturn;
    }

    public int receiveEnergyInternal(ItemStack stack, int maxReceive, boolean simulate){
        int before = this.maxReceive;
        this.setMaxReceive(Integer.MAX_VALUE);

        int toReturn = this.receiveEnergy(stack, maxReceive, simulate);

        this.setMaxReceive(before);
        return toReturn;
    }

    private static class EnergyCapabilityProvider implements ICapabilityProvider{

        private final Object forgeUnitsWrapper;
        private Object teslaWrapper;

        private final IEnergyContainerItem item;
        private final ItemStack stack;

        public EnergyCapabilityProvider(final ItemStack stack, final IEnergyContainerItem item){
            this.stack = stack;
            this.item = item;

            this.forgeUnitsWrapper = new IEnergyStorage(){
                @Override
                public int receiveEnergy(int maxReceive, boolean simulate){
                    return item.receiveEnergy(stack, maxReceive, simulate);
                }

                @Override
                public int extractEnergy(int maxExtract, boolean simulate){
                    return item.extractEnergy(stack, maxExtract, simulate);
                }

                @Override
                public int getEnergyStored(){
                    return item.getEnergyStored(stack);
                }

                @Override
                public int getMaxEnergyStored(){
                    return item.getMaxEnergyStored(stack);
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
                return (T)this.forgeUnitsWrapper;
            }
            else if(ActuallyAdditions.teslaLoaded){
                if(capability == TeslaUtil.teslaConsumer || capability == TeslaUtil.teslaProducer || capability == TeslaUtil.teslaHolder){
                    if(this.teslaWrapper == null){
                        this.teslaWrapper = new ItemTeslaWrapper(this.stack, this.item);
                    }
                    return (T)this.teslaWrapper;
                }
            }
            return null;
        }
    }
}
