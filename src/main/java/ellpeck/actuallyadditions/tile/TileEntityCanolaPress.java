/*
 * This file ("TileEntityCanolaPress.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityCanolaPress extends TileEntityInventoryBase implements IEnergyReceiver, IFluidHandler{

    public EnergyStorage storage = new EnergyStorage(40000);
    public FluidTank tank = new FluidTank(2*FluidContainerRegistry.BUCKET_VOLUME);
    public int currentProcessTime;
    private int lastEnergyStored;
    private int lastTankAmount;
    private int lastProcessTime;

    public TileEntityCanolaPress(){
        super(3, "canolaPress");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(this.isCanola(0) && ConfigIntValues.PRESS_MB_PRODUCED.getValue() <= this.tank.getCapacity()-this.tank.getFluidAmount()){
                if(this.storage.getEnergyStored() >= ConfigIntValues.PRESS_ENERGY_USED.getValue()){
                    this.currentProcessTime++;
                    this.storage.extractEnergy(ConfigIntValues.PRESS_ENERGY_USED.getValue(), false);
                    if(this.currentProcessTime >= ConfigIntValues.PRESS_PROCESSING_TIME.getValue()){
                        this.currentProcessTime = 0;

                        this.slots[0].stackSize--;
                        if(this.slots[0].stackSize == 0){
                            this.slots[0] = null;
                        }

                        this.tank.fill(new FluidStack(InitBlocks.fluidCanolaOil, ConfigIntValues.PRESS_MB_PRODUCED.getValue()), true);
                        this.markDirty();
                    }
                }
            }
            else{
                this.currentProcessTime = 0;
            }

            WorldUtil.fillBucket(tank, slots, 1, 2);

            if(this.tank.getFluidAmount() > 0){
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, this.tank);
                if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, this.tank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, this.tank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, this.tank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, this.tank);
                }
            }

            if(this.storage.getEnergyStored() != this.lastEnergyStored || this.tank.getFluidAmount() != this.lastTankAmount | this.currentProcessTime != this.lastProcessTime){
                this.lastEnergyStored = this.storage.getEnergyStored();
                this.lastProcessTime = this.currentProcessTime;
                this.lastTankAmount = this.tank.getFluidAmount();
                this.sendUpdate();
            }
        }
    }

    public boolean isCanola(int slot){
        return this.slots[slot] != null && this.slots[slot].getItem() == InitItems.itemMisc && this.slots[slot].getItemDamage() == TheMiscItems.CANOLA.ordinal();
    }

    @SideOnly(Side.CLIENT)
    public int getTankScaled(int i){
        return this.tank.getFluidAmount()*i/this.tank.getCapacity();
    }

    @SideOnly(Side.CLIENT)
    public int getProcessScaled(int i){
        return this.currentProcessTime*i/ConfigIntValues.PRESS_PROCESSING_TIME.getValue();
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
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

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == 0 && stack.getItem() == InitItems.itemMisc && stack.getItemDamage() == TheMiscItems.CANOLA.ordinal()) || (i == 1 && stack.getItem() == Items.bucket);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == 2 && FluidContainerRegistry.containsFluid(this.slots[0], new FluidStack(InitBlocks.fluidCanolaOil, FluidContainerRegistry.BUCKET_VOLUME));
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
        if(resource.getFluid() == InitBlocks.fluidCanolaOil){
            return this.tank.drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
        return this.tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid){
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid){
        return from != ForgeDirection.UP;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from){
        return new FluidTankInfo[]{this.tank.getInfo()};
    }
}
