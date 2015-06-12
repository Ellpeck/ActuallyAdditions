package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityOilGenerator extends TileEntityInventoryBase implements IEnergyProvider, IFluidHandler{

    public EnergyStorage storage = new EnergyStorage(30000);

    public FluidTank tank = new FluidTank(2*FluidContainerRegistry.BUCKET_VOLUME);

    public static int energyProducedPerTick = ConfigIntValues.OIL_GEN_ENERGY_PRODUCED.getValue();

    public int fuelUsedPerBurnup = ConfigIntValues.OIL_GEN_FUEL_USED.getValue();
    public int maxBurnTime = ConfigIntValues.OIL_GEN_BURN_TIME.getValue();

    public int currentBurnTime;

    public TileEntityOilGenerator(){
        super(2, "oilGenerator");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){

            if(this.currentBurnTime > 0){
                this.currentBurnTime--;
                this.storage.receiveEnergy(energyProducedPerTick, false);
            }

            if(energyProducedPerTick*this.maxBurnTime <= this.getMaxEnergyStored(ForgeDirection.UNKNOWN)-this.getEnergyStored(ForgeDirection.UNKNOWN)){
                if(this.currentBurnTime <= 0 && this.tank.getFluidAmount() >= this.fuelUsedPerBurnup){
                    this.currentBurnTime = this.maxBurnTime;
                    this.tank.drain(this.fuelUsedPerBurnup, true);
                }
            }

            if(this.slots[0] != null && FluidContainerRegistry.containsFluid(this.slots[0], new FluidStack(InitBlocks.fluidOil, FluidContainerRegistry.BUCKET_VOLUME)) && (this.slots[1] == null || (this.slots[1].stackSize < this.slots[1].getMaxStackSize()))){
                if(FluidContainerRegistry.BUCKET_VOLUME <= this.tank.getCapacity()-this.tank.getFluidAmount()){
                    if(this.slots[1] == null) this.slots[1] = new ItemStack(Items.bucket);
                    else this.slots[1].stackSize++;
                    this.slots[0] = null;
                    this.tank.fill(new FluidStack(InitBlocks.fluidOil, FluidContainerRegistry.BUCKET_VOLUME), true);
                }
            }

            if(this.getEnergyStored(ForgeDirection.UNKNOWN) > 0){
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, storage);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.getEnergyStored(ForgeDirection.UNKNOWN) * i / this.getMaxEnergyStored(ForgeDirection.UNKNOWN);
    }

    @SideOnly(Side.CLIENT)
    public int getTankScaled(int i){
        return this.tank.getFluidAmount() * i / this.tank.getCapacity();
    }

    @SideOnly(Side.CLIENT)
    public int getBurningScaled(int i){
        return this.currentBurnTime * i / this.maxBurnTime;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        compound.setInteger("BurnTime", this.currentBurnTime);
        compound.setInteger("MaxBurnTime", this.maxBurnTime);
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.currentBurnTime = compound.getInteger("BurnTime");
        this.maxBurnTime = compound.getInteger("MaxBurnTime");
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readFromNBT(compound);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return FluidContainerRegistry.containsFluid(stack, new FluidStack(InitBlocks.fluidOil, FluidContainerRegistry.BUCKET_VOLUME)) && i == 0;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == 1;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
        return this.storage.extractEnergy(maxExtract, simulate);
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
        if(resource.getFluid() == InitBlocks.fluidOil) return this.tank.fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid){
        return from != ForgeDirection.DOWN && fluid == InitBlocks.fluidOil;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid){
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from){
        return new FluidTankInfo[]{this.tank.getInfo()};
    }
}
