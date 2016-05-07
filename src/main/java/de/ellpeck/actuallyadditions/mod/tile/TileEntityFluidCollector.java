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


import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFluidCollector extends TileEntityBase implements IFluidHandler, IFluidSaver, IRedstoneToggle{

    public FluidTank tank = new FluidTank(8*Util.BUCKET);
    public boolean isPlacer;
    private int lastTankAmount;
    private int currentTime;
    private boolean activateOnceWithSignal;

    public TileEntityFluidCollector(String name){
        super(name);
    }

    public TileEntityFluidCollector(){
        this("fluidCollector");
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
        EnumFacing sideToManipulate = WorldUtil.getDirectionByPistonRotation(PosUtil.getMetadata(this.pos, this.worldObj));
        BlockPos coordsBlock = WorldUtil.getCoordsFromSide(sideToManipulate, this.pos, 0);

        if(coordsBlock != null){
            Block blockToBreak = PosUtil.getBlock(coordsBlock, this.worldObj);
            if(!this.isPlacer && blockToBreak != null && PosUtil.getMetadata(coordsBlock, this.worldObj) == 0 && Util.BUCKET <= this.tank.getCapacity()-this.tank.getFluidAmount()){
                if(blockToBreak instanceof IFluidBlock && ((IFluidBlock)blockToBreak).getFluid() != null){
                    if(this.tank.fill(new FluidStack(((IFluidBlock)blockToBreak).getFluid(), Util.BUCKET), false) >= Util.BUCKET){
                        this.tank.fill(new FluidStack(((IFluidBlock)blockToBreak).getFluid(), Util.BUCKET), true);
                        WorldUtil.breakBlockAtSide(sideToManipulate, this.worldObj, this.pos);
                    }
                }
                else if(blockToBreak == Blocks.LAVA || blockToBreak == Blocks.FLOWING_LAVA){
                    if(this.tank.fill(new FluidStack(FluidRegistry.LAVA, Util.BUCKET), false) >= Util.BUCKET){
                        this.tank.fill(new FluidStack(FluidRegistry.LAVA, Util.BUCKET), true);
                        WorldUtil.breakBlockAtSide(sideToManipulate, this.worldObj, this.pos);
                    }
                }
                else if(blockToBreak == Blocks.WATER || blockToBreak == Blocks.FLOWING_WATER){
                    if(this.tank.fill(new FluidStack(FluidRegistry.WATER, Util.BUCKET), false) >= Util.BUCKET){
                        this.tank.fill(new FluidStack(FluidRegistry.WATER, Util.BUCKET), true);
                        WorldUtil.breakBlockAtSide(sideToManipulate, this.worldObj, this.pos);
                    }
                }
            }
            else if(this.isPlacer && PosUtil.getBlock(coordsBlock, this.worldObj).isReplaceable(this.worldObj, coordsBlock)){
                if(this.tank.getFluidAmount() >= Util.BUCKET){
                    Block block = this.tank.getFluid().getFluid().getBlock();
                    if(block != null){
                        BlockPos offsetPos = this.pos.offset(sideToManipulate);
                        Block blockPresent = PosUtil.getBlock(offsetPos, this.worldObj);
                        boolean placeable = !(blockPresent instanceof BlockLiquid) && !(blockPresent instanceof IFluidBlock) && blockPresent.isReplaceable(this.worldObj, offsetPos);
                        if(placeable){
                            PosUtil.setBlock(offsetPos, this.worldObj, block, 0, 3);
                            this.tank.drain(Util.BUCKET, true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill){
        if(this.isPlacer){
            return this.tank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain){
        if(!this.isPlacer){
            return this.tank.drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain){
        if(!this.isPlacer){
            return this.tank.drain(maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid){
        return this.isPlacer;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid){
        return !this.isPlacer;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from){
        return new FluidTankInfo[]{this.tank.getInfo()};
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

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
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

            if(!this.isPlacer && this.tank.getFluidAmount() > 0){
                WorldUtil.pushFluid(this.worldObj, this.pos, EnumFacing.DOWN, this.tank);
                if(!this.isRedstonePowered){
                    WorldUtil.pushFluid(this.worldObj, this.pos, EnumFacing.NORTH, this.tank);
                    WorldUtil.pushFluid(this.worldObj, this.pos, EnumFacing.EAST, this.tank);
                    WorldUtil.pushFluid(this.worldObj, this.pos, EnumFacing.SOUTH, this.tank);
                    WorldUtil.pushFluid(this.worldObj, this.pos, EnumFacing.WEST, this.tank);
                }
            }

            if(this.lastTankAmount != this.tank.getFluidAmount() && this.sendUpdateWithInterval()){
                this.lastTankAmount = this.tank.getFluidAmount();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getTankScaled(int i){
        return this.tank.getFluidAmount()*i/this.tank.getCapacity();
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
            super("fluidPlacer");
            this.isPlacer = true;
        }

    }

}
