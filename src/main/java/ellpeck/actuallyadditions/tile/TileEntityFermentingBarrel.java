/*
 * This file ("TileEntityFermentingBarrel.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.network.sync.IPacketSyncerToClient;
import ellpeck.actuallyadditions.network.sync.PacketSyncerToClient;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityFermentingBarrel extends TileEntityInventoryBase implements IFluidHandler, IPacketSyncerToClient{

    public FluidTank canolaTank = new FluidTank(2*FluidContainerRegistry.BUCKET_VOLUME);
    public FluidTank oilTank = new FluidTank(2*FluidContainerRegistry.BUCKET_VOLUME);
    public int currentProcessTime;
    private int lastCanola;
    private int lastOil;
    private int lastProcessTime;

    public TileEntityFermentingBarrel(){
        super(4, "fermentingBarrel");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(this.canolaTank.getFluidAmount() >= ConfigIntValues.BARREL_MB_PRODUCED.getValue() && ConfigIntValues.BARREL_MB_PRODUCED.getValue() <= this.oilTank.getCapacity()-this.oilTank.getFluidAmount()){
                this.currentProcessTime++;
                if(this.currentProcessTime >= ConfigIntValues.BARREL_PROCESSING_TIME.getValue()){
                    this.currentProcessTime = 0;

                    this.oilTank.fill(new FluidStack(InitBlocks.fluidOil, ConfigIntValues.BARREL_MB_PRODUCED.getValue()), true);
                    this.canolaTank.drain(ConfigIntValues.BARREL_MB_PRODUCED.getValue(), true);
                    this.markDirty();
                }
            }
            else{
                this.currentProcessTime = 0;
            }

            WorldUtil.emptyBucket(canolaTank, slots, 0, 1, InitBlocks.fluidCanolaOil);
            WorldUtil.fillBucket(oilTank, slots, 2, 3);

            if(this.oilTank.getFluidAmount() > 0){
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, this.oilTank);
                if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, this.oilTank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, this.oilTank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, this.oilTank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, this.oilTank);
                }
            }

            if(this.canolaTank.getFluidAmount() != this.lastCanola || this.oilTank.getFluidAmount() != this.lastOil || this.currentProcessTime != this.lastProcessTime){
                this.lastProcessTime = this.currentProcessTime;
                this.lastCanola = this.canolaTank.getFluidAmount();
                this.lastOil = this.oilTank.getFluidAmount();
                this.sendUpdate();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getProcessScaled(int i){
        return this.currentProcessTime*i/ConfigIntValues.BARREL_PROCESSING_TIME.getValue();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.currentProcessTime = compound.getInteger("ProcessTime");
        this.canolaTank.readFromNBT(compound);
        this.oilTank.readFromNBT((NBTTagCompound)compound.getTag("OilTank"));
        super.readFromNBT(compound);
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

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return (i == 0 && FluidContainerRegistry.containsFluid(stack, new FluidStack(InitBlocks.fluidCanolaOil, FluidContainerRegistry.BUCKET_VOLUME))) || (i == 2 && stack.getItem() == Items.bucket);
    }

    @SideOnly(Side.CLIENT)
    public int getOilTankScaled(int i){
        return this.oilTank.getFluidAmount()*i/this.oilTank.getCapacity();
    }

    @SideOnly(Side.CLIENT)
    public int getCanolaTankScaled(int i){
        return this.canolaTank.getFluidAmount()*i/this.canolaTank.getCapacity();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return (slot == 1 && stack.getItem() == Items.bucket) || (slot == 3 && FluidContainerRegistry.containsFluid(stack, new FluidStack(InitBlocks.fluidOil, FluidContainerRegistry.BUCKET_VOLUME)));
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
        if(from != ForgeDirection.DOWN && resource.getFluid() == InitBlocks.fluidCanolaOil){
            return this.canolaTank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
        if(resource.getFluid() == InitBlocks.fluidOil){
            return this.oilTank.drain(resource.amount, doDrain);
        }
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

    @Override
    public int[] getValues(){
        return new int[]{this.oilTank.getFluidAmount(), this.oilTank.getFluid() == null ? -1 : this.oilTank.getFluid().getFluidID(), this.canolaTank.getFluidAmount(), this.canolaTank.getFluid() == null ? -1 : this.canolaTank.getFluid().getFluidID(), this.currentProcessTime};
    }

    @Override
    public void setValues(int[] values){
        if(values[1] != -1){
            this.oilTank.setFluid(new FluidStack(FluidRegistry.getFluid(values[1]), values[0]));
        }
        else{
            this.oilTank.setFluid(null);
        }
        if(values[3] != -1){
            this.canolaTank.setFluid(new FluidStack(FluidRegistry.getFluid(values[3]), values[2]));
        }
        else{
            this.canolaTank.setFluid(null);
        }
        this.currentProcessTime = values[4];
    }

    @Override
    public void sendUpdate(){
        PacketSyncerToClient.sendPacket(this);
    }
}
