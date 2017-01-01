/*
 * This file ("TileEntityCanolaPress.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCanolaPress extends TileEntityInventoryBase implements ISharingFluidHandler{

    public static final int PRODUCE = 80;
    public static final int ENERGY_USE = 35;
    private static final int TIME = 30;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(40000, 100, 0);
    public final FluidTank tank = new FluidTank(2*Util.BUCKET){
        @Override
        public boolean canFill(){
            return false;
        }
    };
    public int currentProcessTime;
    private int lastEnergyStored;
    private int lastTankAmount;
    private int lastProcessTime;

    public TileEntityCanolaPress(){
        super(1, "canolaPress");
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
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("ProcessTime", this.currentProcessTime);
        }
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeSyncableNBT(compound, type);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        if(type != NBTType.SAVE_BLOCK){
            this.currentProcessTime = compound.getInteger("ProcessTime");
        }
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readSyncableNBT(compound, type);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            if(this.isCanola(0) && PRODUCE <= this.tank.getCapacity()-this.tank.getFluidAmount()){
                if(this.storage.getEnergyStored() >= ENERGY_USE){
                    this.currentProcessTime++;
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
                    if(this.currentProcessTime >= TIME){
                        this.currentProcessTime = 0;

                        this.slots.setStackInSlot(0, StackUtil.addStackSize(this.slots.getStackInSlot(0), -1));

                        this.tank.fillInternal(new FluidStack(InitFluids.fluidCanolaOil, PRODUCE), true);
                        this.markDirty();
                    }
                }
            }
            else{
                this.currentProcessTime = 0;
            }

            if((this.storage.getEnergyStored() != this.lastEnergyStored || this.tank.getFluidAmount() != this.lastTankAmount | this.currentProcessTime != this.lastProcessTime) && this.sendUpdateWithInterval()){
                this.lastEnergyStored = this.storage.getEnergyStored();
                this.lastProcessTime = this.currentProcessTime;
                this.lastTankAmount = this.tank.getFluidAmount();
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == 0 && stack.getItem() == InitItems.itemMisc && stack.getItemDamage() == TheMiscItems.CANOLA.ordinal());
    }

    public boolean isCanola(int slot){
        return StackUtil.isValid(this.slots.getStackInSlot(slot)) && this.slots.getStackInSlot(slot).getItem() == InitItems.itemMisc && this.slots.getStackInSlot(slot).getItemDamage() == TheMiscItems.CANOLA.ordinal();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return false;
    }

    @Override
    public FluidTank getFluidHandler(EnumFacing facing){
        return this.tank;
    }

    @Override
    public int getMaxFluidAmountToSplitShare(){
        return this.tank.getFluidAmount();
    }

    @Override
    public boolean doesShareFluid(){
        return true;
    }

    @Override
    public EnumFacing[] getFluidShareSides(){
        return EnumFacing.values();
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
