/*
 * This file ("TileEntityCanolaPress.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCanolaPress extends TileEntityInventoryBase implements IEnergyReceiver, IFluidHandler, IEnergySaver, IFluidSaver{

    public static final int PRODUCE = 120;
    public static final int ENERGY_USE = 35;
    private static final int TIME = 30;
    public EnergyStorage storage = new EnergyStorage(40000);
    public FluidTank tank = new FluidTank(2*FluidContainerRegistry.BUCKET_VOLUME);
    public int currentProcessTime;
    private int lastEnergyStored;
    private int lastTankAmount;
    private int lastProcessTime;

    public TileEntityCanolaPress(){
        super(3, "canolaPress");
    }

    @SideOnly(Side.CLIENT)
    public int getTankScaled(int i){
        return this.tank.getFluidAmount()*i/this.tank.getCapacity();
    }

    @SideOnly(Side.CLIENT)
    public int getProcessScaled(int i){
        return this.currentProcessTime*i/TIME;
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(this.isCanola(0) && PRODUCE <= this.tank.getCapacity()-this.tank.getFluidAmount()){
                if(this.storage.getEnergyStored() >= ENERGY_USE){
                    this.currentProcessTime++;
                    this.storage.extractEnergy(ENERGY_USE, false);
                    if(this.currentProcessTime >= TIME){
                        this.currentProcessTime = 0;

                        this.slots[0].stackSize--;
                        if(this.slots[0].stackSize == 0){
                            this.slots[0] = null;
                        }

                        this.tank.fill(new FluidStack(InitBlocks.fluidCanolaOil, PRODUCE), true);
                        this.markDirty();
                    }
                }
            }
            else{
                this.currentProcessTime = 0;
            }

            WorldUtil.fillBucket(tank, slots, 1, 2);

            if(this.tank.getFluidAmount() > 0){
                WorldUtil.pushFluid(worldObj, this.pos, EnumFacing.DOWN, this.tank);
                if(!this.isRedstonePowered){
                    WorldUtil.pushFluid(worldObj, this.pos, EnumFacing.NORTH, this.tank);
                    WorldUtil.pushFluid(worldObj, this.pos, EnumFacing.EAST, this.tank);
                    WorldUtil.pushFluid(worldObj, this.pos, EnumFacing.SOUTH, this.tank);
                    WorldUtil.pushFluid(worldObj, this.pos, EnumFacing.WEST, this.tank);
                }
            }

            if((this.storage.getEnergyStored() != this.lastEnergyStored || this.tank.getFluidAmount() != this.lastTankAmount | this.currentProcessTime != this.lastProcessTime) && this.sendUpdateWithInterval()){
                this.lastEnergyStored = this.storage.getEnergyStored();
                this.lastProcessTime = this.currentProcessTime;
                this.lastTankAmount = this.tank.getFluidAmount();
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        compound.setInteger("ProcessTime", this.currentProcessTime);
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeSyncableNBT(compound, sync);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        this.currentProcessTime = compound.getInteger("ProcessTime");
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readSyncableNBT(compound, sync);
    }

    public boolean isCanola(int slot){
        return this.slots[slot] != null && this.slots[slot].getItem() == InitItems.itemMisc && this.slots[slot].getItemDamage() == TheMiscItems.CANOLA.ordinal();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == 0 && stack.getItem() == InitItems.itemMisc && stack.getItemDamage() == TheMiscItems.CANOLA.ordinal()) || (i == 1 && stack.getItem() == Items.bucket);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return slot == 2 && FluidContainerRegistry.containsFluid(this.slots[0], new FluidStack(InitBlocks.fluidCanolaOil, FluidContainerRegistry.BUCKET_VOLUME));
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return true;
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill){
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain){
        if(resource.getFluid() == InitBlocks.fluidCanolaOil){
            return this.tank.drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain){
        return this.tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid){
        return false;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid){
        return from != EnumFacing.UP;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from){
        return new FluidTankInfo[]{this.tank.getInfo()};
    }

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy){
        this.storage.setEnergyStored(energy);
    }

    @Override
    public FluidStack[] getFluids(){
        return new FluidStack[]{this.tank.getFluid()};
    }

    @Override
    public void setFluids(FluidStack[] fluids){
        this.tank.setFluid(fluids[0]);
    }
}
