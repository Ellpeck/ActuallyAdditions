/*
 * This file ("TileEntityFluidCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityFluidCollector extends TileEntityBase implements ISharingFluidHandler{

    public boolean isPlacer;
    public final FluidTank tank = new FluidTank(8*Util.BUCKET){
        @Override
        public boolean canFill(){
            return TileEntityFluidCollector.this.isPlacer;
        }

        @Override
        public boolean canDrain(){
            return !TileEntityFluidCollector.this.isPlacer;
        }
    };
    private int lastTankAmount;
    private int currentTime;
    private int lastCompare;

    public TileEntityFluidCollector(String name){
        super(name);
    }

    public TileEntityFluidCollector(){
        this("fluidCollector");
        this.isPlacer = false;
    }

    @Override
    public boolean isRedstoneToggle(){
        return true;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }

    private void doWork(){
        IBlockState state = this.world.getBlockState(this.pos);
        Block block = state.getBlock();
        EnumFacing sideToManipulate = WorldUtil.getDirectionByPistonRotation(block.getMetaFromState(state));
        BlockPos coordsBlock = this.pos.offset(sideToManipulate);

        IBlockState stateToBreak = this.world.getBlockState(coordsBlock);
        Block blockToBreak = stateToBreak.getBlock();
        if(!this.isPlacer && blockToBreak != null && blockToBreak.getMetaFromState(stateToBreak) == 0 && Util.BUCKET <= this.tank.getCapacity()-this.tank.getFluidAmount()){
            if(blockToBreak instanceof IFluidBlock && ((IFluidBlock)blockToBreak).getFluid() != null){
                if(this.tank.fillInternal(new FluidStack(((IFluidBlock)blockToBreak).getFluid(), Util.BUCKET), false) >= Util.BUCKET){
                    this.tank.fillInternal(new FluidStack(((IFluidBlock)blockToBreak).getFluid(), Util.BUCKET), true);
                    this.world.setBlockToAir(coordsBlock);
                }
            }
            else if(blockToBreak == Blocks.LAVA || blockToBreak == Blocks.FLOWING_LAVA){
                if(this.tank.fillInternal(new FluidStack(FluidRegistry.LAVA, Util.BUCKET), false) >= Util.BUCKET){
                    this.tank.fillInternal(new FluidStack(FluidRegistry.LAVA, Util.BUCKET), true);
                    this.world.setBlockToAir(coordsBlock);
                }
            }
            else if(blockToBreak == Blocks.WATER || blockToBreak == Blocks.FLOWING_WATER){
                if(this.tank.fillInternal(new FluidStack(FluidRegistry.WATER, Util.BUCKET), false) >= Util.BUCKET){
                    this.tank.fillInternal(new FluidStack(FluidRegistry.WATER, Util.BUCKET), true);
                    this.world.setBlockToAir(coordsBlock);
                }
            }
        }
        else if(this.isPlacer && blockToBreak.isReplaceable(this.world, coordsBlock)){
            if(this.tank.getFluidAmount() >= Util.BUCKET){
                FluidStack stack = this.tank.getFluid();
                Block fluid = stack.getFluid().getBlock();
                if(fluid != null){
                    BlockPos offsetPos = this.pos.offset(sideToManipulate);
                    boolean placeable = !(blockToBreak instanceof BlockLiquid) && !(blockToBreak instanceof IFluidBlock) && blockToBreak.isReplaceable(this.world, offsetPos);
                    if(placeable){
                        this.tank.drainInternal(Util.BUCKET, true);

                        if(this.world.provider.doesWaterVaporize() && stack.getFluid().doesVaporize(stack)){
                            this.world.playSound(null, offsetPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F+(this.world.rand.nextFloat()-this.world.rand.nextFloat())*0.8F);

                            if(this.world instanceof WorldServer){
                                for(int l = 0; l < 8; ++l){
                                    ((WorldServer)this.world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, false, (double)offsetPos.getX()+Math.random(), (double)offsetPos.getY()+Math.random(), (double)offsetPos.getZ()+Math.random(), 1, 0.0D, 0.0D, 0.0D, 0);
                                }
                            }
                        }
                        else{
                            this.world.setBlockState(offsetPos, fluid.getDefaultState(), 3);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getComparatorStrength(){
        float calc = ((float)this.tank.getFluidAmount()/(float)this.tank.getCapacity())*15F;
        return (int)calc;
    }

    @Override
    public IFluidHandler getFluidHandler(EnumFacing facing){
        return this.tank;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("CurrentTime", this.currentTime);
        }
        this.tank.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.currentTime = compound.getInteger("CurrentTime");
        }
        this.tank.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            if(!this.isRedstonePowered && !this.isPulseMode){
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

            if(this.lastCompare != this.getComparatorStrength()){
                this.lastCompare = this.getComparatorStrength();

                this.markDirty();
            }

            if(this.lastTankAmount != this.tank.getFluidAmount() && this.sendUpdateWithInterval()){
                this.lastTankAmount = this.tank.getFluidAmount();
            }
        }
    }

    @Override
    public int getMaxFluidAmountToSplitShare(){
        return this.tank.getFluidAmount();
    }

    @Override
    public boolean doesShareFluid(){
        return !this.isPlacer;
    }

    @Override
    public EnumFacing[] getFluidShareSides(){
        return EnumFacing.values();
    }
}
