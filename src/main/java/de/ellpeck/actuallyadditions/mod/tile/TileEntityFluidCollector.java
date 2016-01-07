/*
 * This file ("TileEntityFluidCollector.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFluidCollector extends TileEntityInventoryBase implements IFluidHandler, IFluidSaver, IRedstoneToggle{

    public FluidTank tank = new FluidTank(8*FluidContainerRegistry.BUCKET_VOLUME);
    public boolean isPlacer;
    private int lastTankAmount;
    private int currentTime;
    private boolean activateOnceWithSignal;

    public TileEntityFluidCollector(int slots, String name){
        super(slots, name);
    }

    public TileEntityFluidCollector(){
        super(2, "fluidCollector");
        this.isPlacer = false;
    }

    @Override
    public void toggle(boolean to){
        this.activateOnceWithSignal = to;
    }

    @Override
    public boolean isPulseMode(){
        return this.activateOnceWithSignal;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }

    private void doWork(){
        ForgeDirection sideToManipulate = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));

        Position coordsBlock = WorldUtil.getCoordsFromSide(sideToManipulate, xCoord, yCoord, zCoord, 0);
        if(coordsBlock != null){
            Block blockToBreak = worldObj.getBlock(coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ());
            if(!this.isPlacer && blockToBreak != null && worldObj.getBlockMetadata(coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ()) == 0 && FluidContainerRegistry.BUCKET_VOLUME <= this.tank.getCapacity()-this.tank.getFluidAmount()){
                if(blockToBreak instanceof IFluidBlock && ((IFluidBlock)blockToBreak).getFluid() != null){
                    if(this.tank.fill(new FluidStack(((IFluidBlock)blockToBreak).getFluid(), FluidContainerRegistry.BUCKET_VOLUME), false) >= FluidContainerRegistry.BUCKET_VOLUME){
                        this.tank.fill(new FluidStack(((IFluidBlock)blockToBreak).getFluid(), FluidContainerRegistry.BUCKET_VOLUME), true);
                        WorldUtil.breakBlockAtSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord);
                    }
                }
                else if(blockToBreak == Blocks.lava || blockToBreak == Blocks.flowing_lava){
                    if(this.tank.fill(new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME), false) >= FluidContainerRegistry.BUCKET_VOLUME){
                        this.tank.fill(new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME), true);
                        WorldUtil.breakBlockAtSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord);
                    }
                }
                else if(blockToBreak == Blocks.water || blockToBreak == Blocks.flowing_water){
                    if(this.tank.fill(new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME), false) >= FluidContainerRegistry.BUCKET_VOLUME){
                        this.tank.fill(new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME), true);
                        WorldUtil.breakBlockAtSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord);
                    }
                }
            }
            else if(this.isPlacer && worldObj.getBlock(coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ()).isReplaceable(worldObj, coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ())){
                if(this.tank.getFluidAmount() >= FluidContainerRegistry.BUCKET_VOLUME){
                    if(this.tank.getFluid().getFluid().getBlock() != null){
                        Block block = worldObj.getBlock(xCoord+sideToManipulate.offsetX, yCoord+sideToManipulate.offsetY, zCoord+sideToManipulate.offsetZ);
                        if(!(block instanceof IFluidBlock) && block != Blocks.lava && block != Blocks.water && block != Blocks.flowing_lava && block != Blocks.flowing_water){
                            WorldUtil.placeBlockAtSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord, new ItemStack(this.tank.getFluid().getFluid().getBlock()));
                            this.tank.drain(FluidContainerRegistry.BUCKET_VOLUME, true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
        if(this.isPlacer){
            return this.tank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
        if(!this.isPlacer){
            return this.tank.drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
        if(!this.isPlacer){
            return this.tank.drain(maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid){
        return this.isPlacer;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid){
        return !this.isPlacer;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from){
        return new FluidTankInfo[]{this.tank.getInfo()};
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!this.isRedstonePowered && !this.activateOnceWithSignal){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        this.doWork();
                    }
                }
                else{
                    this.currentTime = 15;
                }
            }

            if(!this.isPlacer){
                WorldUtil.fillBucket(tank, slots, 0, 1);
            }
            else{
                WorldUtil.emptyBucket(tank, slots, 0, 1);
            }

            if(!this.isPlacer && this.tank.getFluidAmount() > 0){
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, this.tank);
                if(!this.isRedstonePowered){
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, this.tank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, this.tank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, this.tank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, this.tank);
                }
            }

            if(lastTankAmount != this.tank.getFluidAmount() && this.sendUpdateWithInterval()){
                lastTankAmount = this.tank.getFluidAmount();
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("CurrentTime", this.currentTime);
        this.tank.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.currentTime = compound.getInteger("CurrentTime");
        this.tank.readFromNBT(compound);
    }

    @SideOnly(Side.CLIENT)
    public int getTankScaled(int i){
        return this.tank.getFluidAmount()*i/this.tank.getCapacity();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        if(i == 0){
            if(this.isPlacer){
                return FluidContainerRegistry.isFilledContainer(stack);
            }
            else{
                return stack.isItemEqual(FluidContainerRegistry.EMPTY_BUCKET);
            }
        }
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == 1;
    }

    @Override
    public FluidStack[] getFluids(){
        return new FluidStack[]{this.tank.getFluid()};
    }

    @Override
    public void setFluids(FluidStack[] fluids){
        this.tank.setFluid(fluids[0]);
    }

    public static class TileEntityFluidPlacer extends TileEntityFluidCollector{

        public TileEntityFluidPlacer(){
            super(2, "fluidPlacer");
            this.isPlacer = true;
        }

    }

}
