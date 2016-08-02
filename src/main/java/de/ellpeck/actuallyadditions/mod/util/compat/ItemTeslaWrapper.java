/*
 * This file ("ItemTeslaWrapper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util.compat;

import cofh.api.energy.IEnergyContainerItem;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class ItemTeslaWrapper implements ITeslaProducer, ITeslaHolder, ITeslaConsumer, ICapabilityProvider{

    private final ItemStack stack;
    private final IEnergyContainerItem item;

    public ItemTeslaWrapper(ItemStack stack, IEnergyContainerItem item){
        this.stack = stack;
        this.item = item;
    }

    @Override
    public long givePower(long power, boolean simulated){
        return this.item.receiveEnergy(this.stack, (int)power, simulated);
    }

    @Override
    public long getStoredPower(){
        return this.item.getEnergyStored(this.stack);
    }

    @Override
    public long getCapacity(){
        return this.item.getMaxEnergyStored(this.stack);
    }

    @Override
    public long takePower(long power, boolean simulated){
        return this.item.extractEnergy(this.stack, (int)power, simulated);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing){
        return capability == TeslaUtil.teslaProducer || capability == TeslaUtil.teslaHolder || capability == TeslaUtil.teslaConsumer;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
        return this.hasCapability(capability, facing) ? (T)this : null;
    }
}
