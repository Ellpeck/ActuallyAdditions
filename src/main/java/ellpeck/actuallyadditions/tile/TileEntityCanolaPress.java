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

    public EnergyStorage storage = new EnergyStorage(40000, energyUsedPerTick+50);

    public FluidTank tank = new FluidTank(2*FluidContainerRegistry.BUCKET_VOLUME);

    public static int energyUsedPerTick = ConfigIntValues.PRESS_ENERGY_USED.getValue();
    public int mbProducedPerCanola = ConfigIntValues.PRESS_MB_PRODUCED.getValue();

    public int maxTimeProcessing = ConfigIntValues.PRESS_PROCESSING_TIME.getValue();
    public int currentProcessTime;

    public TileEntityCanolaPress(){
        super(3, "canolaPress");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(this.isCanola(0) && this.storage.getEnergyStored() >= energyUsedPerTick && this.mbProducedPerCanola <= this.tank.getCapacity()-this.tank.getFluidAmount()){
                this.currentProcessTime++;
                if(this.currentProcessTime >= this.maxTimeProcessing){
                    this.currentProcessTime = 0;

                    this.slots[0].stackSize--;
                    if(this.slots[0].stackSize == 0) this.slots[0] = null;

                    this.tank.fill(new FluidStack(InitBlocks.fluidCanolaOil, mbProducedPerCanola), true);
                    this.markDirty();
                }
            }
            else this.currentProcessTime = 0;

            if(this.slots[1] != null && this.slots[1].getItem() == Items.bucket && this.slots[2] == null){
                if(this.tank.getFluidAmount() > 0 && this.tank.getFluid().getFluid() == InitBlocks.fluidCanolaOil && this.tank.getFluidAmount() >= FluidContainerRegistry.BUCKET_VOLUME){
                    this.slots[2] = new ItemStack(InitItems.itemBucketCanolaOil);
                    this.slots[1].stackSize--;
                    if(this.slots[1].stackSize == 0) this.slots[1] = null;
                    this.tank.drain(FluidContainerRegistry.BUCKET_VOLUME, true);
                }
            }

            if(this.currentProcessTime > 0) this.storage.extractEnergy(energyUsedPerTick, false);

            if(this.tank.getFluidAmount() > 0){
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP, this.tank);
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, this.tank);
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, this.tank);
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, this.tank);
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, this.tank);
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, this.tank);
            }
        }
    }

    public boolean isCanola(int slot){
        return this.slots[slot] != null && this.slots[slot].getItem() == InitItems.itemMisc && this.slots[slot].getItemDamage() == TheMiscItems.CANOLA.ordinal();
    }

    @SideOnly(Side.CLIENT)
    public int getTankScaled(int i){
        return this.tank.getFluidAmount() * i / this.tank.getCapacity();
    }

    @SideOnly(Side.CLIENT)
    public int getProcessScaled(int i){
        return this.currentProcessTime * i / this.maxTimeProcessing;
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.getEnergyStored(ForgeDirection.UNKNOWN) * i / this.getMaxEnergyStored(ForgeDirection.UNKNOWN);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        compound.setInteger("ProcessTime", this.currentProcessTime);
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.currentProcessTime = compound.getInteger("ProcessTime");
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readFromNBT(compound);
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
        return slot == 2 && stack.getItem() == InitItems.itemBucketCanolaOil;
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
        if(resource.getFluid() == FluidRegistry.getFluid(InitBlocks.fluidCanolaOil.getName())) return this.tank.drain(resource.amount, doDrain);
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
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from){
        return new FluidTankInfo[]{this.tank.getInfo()};
    }
}
