package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.network.PacketFluidCollectorToClient;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityFluidCollector extends TileEntityInventoryBase implements IFluidHandler{

    public FluidTank tank = new FluidTank(8*FluidContainerRegistry.BUCKET_VOLUME);

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
        if(this.isPlacer){
            this.sendPacket();
            return this.tank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
        if(!this.isPlacer){
            this.sendPacket();
            return this.tank.drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
        if(!this.isPlacer){
            this.sendPacket();
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

    public static class TileEntityFluidPlacer extends TileEntityFluidCollector{

        public TileEntityFluidPlacer(){
            super(2, "fluidPlacer");
            this.isPlacer = true;
        }

    }

    public boolean isPlacer;

    private final int timeNeeded = ConfigIntValues.BREAKER_TIME_NEEDED.getValue();
    private int currentTime;

    public TileEntityFluidCollector(int slots, String name){
        super(slots, name);
    }

    public TileEntityFluidCollector(){
        super(2, "fluidCollector");
        this.isPlacer = false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){

            int amountBefore = this.tank.getFluidAmount();
            if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        ForgeDirection sideToManipulate = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));

                        ChunkCoordinates coordsBlock = WorldUtil.getCoordsFromSide(sideToManipulate, xCoord, yCoord, zCoord);
                        if(coordsBlock != null){
                            Block blockToBreak = worldObj.getBlock(coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ);
                            if(!this.isPlacer && blockToBreak != null && worldObj.getBlockMetadata(coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ) == 0){
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
                            else if(this.isPlacer && worldObj.getBlock(coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ).isReplaceable(worldObj, coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ)){
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
                }
                else this.currentTime = this.timeNeeded;
            }

            if(!this.isPlacer) WorldUtil.fillBucket(tank, slots, 0, 1);
            else WorldUtil.emptyBucket(tank, slots, 0, 1);

            if(!this.isPlacer && this.tank.getFluidAmount() > 0){
                WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, this.tank);
                if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, this.tank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, this.tank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, this.tank);
                    WorldUtil.pushFluid(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, this.tank);
                }
            }

            if(amountBefore != this.tank.getFluidAmount()){
                this.sendPacket();
                this.markDirty();
            }
        }
    }

    public void sendPacket(){
        PacketHandler.theNetwork.sendToAllAround(new PacketFluidCollectorToClient(this.tank.getFluid(), this), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 120));
    }

    @SideOnly(Side.CLIENT)
    public int getTankScaled(int i){
        return this.tank.getFluidAmount() * i / this.tank.getCapacity();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("CurrentTime", this.currentTime);
        this.tank.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.currentTime = compound.getInteger("CurrentTime");
        this.tank.readFromNBT(compound);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        if(i == 0){
            if(this.isPlacer) return FluidContainerRegistry.isFilledContainer(stack);
            else return stack.isItemEqual(FluidContainerRegistry.EMPTY_BUCKET);
        }
        return false;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == 1;
    }

}
