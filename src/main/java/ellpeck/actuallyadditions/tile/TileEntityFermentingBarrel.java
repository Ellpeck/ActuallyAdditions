package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityFermentingBarrel extends TileEntityInventoryBase implements IFluidHandler{

    public FluidTank canolaTank = new FluidTank(2*FluidContainerRegistry.BUCKET_VOLUME);
    public FluidTank oilTank = new FluidTank(2*FluidContainerRegistry.BUCKET_VOLUME);

    public int currentProcessTime;
    public int maxTimeProcessing = ConfigIntValues.BARREL_PROCESSING_TIME.getValue();

    public int mBProducedPerCycle = ConfigIntValues.BARREL_MB_PRODUCED.getValue();

    public TileEntityFermentingBarrel(){
        super(4, "fermentingBarrel");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(this.canolaTank.getFluidAmount() >= this.mBProducedPerCycle && this.mBProducedPerCycle <= this.oilTank.getCapacity()-this.oilTank.getFluidAmount()){
                this.currentProcessTime++;
                if(this.currentProcessTime >= this.maxTimeProcessing){
                    this.currentProcessTime = 0;

                    this.oilTank.fill(new FluidStack(InitBlocks.fluidOil, mBProducedPerCycle), true);
                    this.canolaTank.drain(mBProducedPerCycle, true);
                    this.markDirty();
                }
            }
            else this.currentProcessTime = 0;

            if(this.slots[0] != null && FluidContainerRegistry.containsFluid(this.slots[0], new FluidStack(InitBlocks.fluidCanolaOil, FluidContainerRegistry.BUCKET_VOLUME)) && (this.slots[1] == null || (this.slots[1].stackSize < this.slots[1].getMaxStackSize()))){
                if(FluidContainerRegistry.BUCKET_VOLUME <= this.canolaTank.getCapacity()-this.canolaTank.getFluidAmount()){
                    if(this.slots[1] == null) this.slots[1] = new ItemStack(Items.bucket);
                    else this.slots[1].stackSize++;
                    this.slots[0] = null;
                    this.canolaTank.fill(new FluidStack(InitBlocks.fluidCanolaOil, FluidContainerRegistry.BUCKET_VOLUME), true);
                }
            }

            if(this.slots[2] != null && this.slots[2].getItem() == Items.bucket && this.slots[3] == null){
                if(this.oilTank.getFluidAmount() > 0 && this.oilTank.getFluid().getFluid() == InitBlocks.fluidOil && this.oilTank.getFluidAmount() >= FluidContainerRegistry.BUCKET_VOLUME){
                    this.slots[3] = new ItemStack(InitItems.itemBucketOil);
                    this.slots[2].stackSize--;
                    if(this.slots[2].stackSize == 0) this.slots[2] = null;
                    this.oilTank.drain(FluidContainerRegistry.BUCKET_VOLUME, true);
                }
            }

            if(this.oilTank.getFluidAmount() > 0){
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, this.oilTank);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        compound.setInteger("ProcessTime", this.currentProcessTime);
        this.canolaTank.writeToNBT(compound);
        NBTTagCompound tag = new NBTTagCompound();
        this.oilTank.writeToNBT(tag);
        compound.setTag("OilTank", tag);
        super.writeToNBT(compound);
    }

    @SideOnly(Side.CLIENT)
    public int getProcessScaled(int i){
        return this.currentProcessTime * i / this.maxTimeProcessing;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.currentProcessTime = compound.getInteger("ProcessTime");
        this.canolaTank.readFromNBT(compound);
        this.oilTank.readFromNBT((NBTTagCompound)compound.getTag("OilTank"));
        super.readFromNBT(compound);
    }

    @SideOnly(Side.CLIENT)
    public int getOilTankScaled(int i){
        return this.oilTank.getFluidAmount() * i / this.oilTank.getCapacity();
    }

    @SideOnly(Side.CLIENT)
    public int getCanolaTankScaled(int i){
        return this.canolaTank.getFluidAmount() * i / this.canolaTank.getCapacity();
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == 0 && FluidContainerRegistry.containsFluid(stack, new FluidStack(InitBlocks.fluidCanolaOil, FluidContainerRegistry.BUCKET_VOLUME))) || (i == 2 && stack.getItem() == Items.bucket);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return (slot == 1 && stack.getItem() == Items.bucket) || (slot == 3 && FluidContainerRegistry.containsFluid(this.slots[0], new FluidStack(InitBlocks.fluidOil, FluidContainerRegistry.BUCKET_VOLUME)));
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
        if(from != ForgeDirection.DOWN && resource.getFluid() == InitBlocks.fluidCanolaOil) return this.canolaTank.fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
        if(resource.getFluid() == InitBlocks.fluidOil) return this.oilTank.drain(resource.amount, doDrain);
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
        return this.oilTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid){
        return from != ForgeDirection.DOWN && fluid == InitBlocks.fluidCanolaOil;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid){
        return from != ForgeDirection.UP && fluid == InitBlocks.fluidOil;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from){
        return new FluidTankInfo[]{this.canolaTank.getInfo(), this.oilTank.getInfo()};
    }
}
