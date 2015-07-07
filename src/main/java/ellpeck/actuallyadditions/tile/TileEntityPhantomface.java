package ellpeck.actuallyadditions.tile;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import ellpeck.actuallyadditions.blocks.BlockPhantomface;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityPhantomface extends TileEntityInventoryBase{

    public WorldPos boundPosition;

    public int type;

    public final int defaultRange = ConfigIntValues.PHANTOMFACE_RANGE.getValue();
    public int range;

    private int rangeBefore;
    private WorldPos boundPosBefore;
    private Block boundBlockBefore;

    public TileEntityPhantomface(String name){
        super(0, name);
    }

    public static int upgradeRange(int defaultRange, World world, int x, int y, int z){
        int newRange = defaultRange;
        for(int i = 0; i < 3; i++){
            Block block = world.getBlock(x, y+1+i, z);
            if(block == InitBlocks.blockPhantomBooster) newRange = newRange*2;
            else break;
        }
        return newRange;
    }

    public boolean isBoundTileInRage(){
        if(this.hasBoundTile()){
            int xDif = this.boundPosition.getX()-this.xCoord;
            int yDif = this.boundPosition.getY()-this.yCoord;
            int zDif = this.boundPosition.getZ()-this.zCoord;

            if(xDif >= -this.range && xDif <= this.range){
                if(yDif >= -this.range && yDif <= this.range){
                    if(zDif >= -this.range && zDif <= this.range){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            this.range = upgradeRange(defaultRange, worldObj, xCoord, yCoord, zCoord);

            if(!this.hasBoundTile()){
                this.boundPosition = null;
            }

            if((this.boundPosition != null && (this.boundBlockBefore != this.boundPosition.getBlock() || !this.boundPosition.isEqual(boundPosBefore))) || this.range != this.rangeBefore){
                this.rangeBefore = this.range;
                this.boundPosBefore = this.boundPosition;
                this.boundBlockBefore = this.boundPosition == null ? null : this.boundPosition.getBlock();
                System.out.println("Hi!");
                WorldUtil.updateTileAndTilesAround(this);
            }
        }
    }

    public boolean hasBoundTile(){
        if(this.boundPosition != null && this.boundPosition.getWorld() != null){
            if(this.boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof TileEntityPhantomface || (this.xCoord == this.boundPosition.getX() && this.yCoord == this.boundPosition.getY() && this.zCoord == this.boundPosition.getZ() && this.worldObj == this.boundPosition.getWorld())){
                this.boundPosition = null;
                return false;
            }
            return this.boundPosition.getWorld() == this.worldObj;
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        if(this.hasBoundTile()){
            compound.setInteger("XCoordOfTileStored", boundPosition.getX());
            compound.setInteger("YCoordOfTileStored", boundPosition.getY());
            compound.setInteger("ZCoordOfTileStored", boundPosition.getZ());
            compound.setInteger("WorldOfTileStored", boundPosition.getWorld().provider.dimensionId);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        int x = compound.getInteger("XCoordOfTileStored");
        int y = compound.getInteger("YCoordOfTileStored");
        int z = compound.getInteger("ZCoordOfTileStored");
        World world = DimensionManager.getWorld(compound.getInteger("WorldOfTileStored"));
        if(x != 0 && y != 0 && z != 0 && world != null){
            this.boundPosition = new WorldPos(world, x, y, z);
            this.markDirty();
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return false;
    }

    public static class TileEntityPhantomLiquiface extends TileEntityPhantomface implements IFluidHandler{

        public TileEntityPhantomLiquiface(){
            super("liquiface");
            this.type = BlockPhantomface.LIQUIFACE;
        }

        @Override
        public void updateEntity(){
            super.updateEntity();

            if(!worldObj.isRemote){
                if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && this.isBoundTileInRage() && this.getHandler() != null){
                    this.pushFluid(ForgeDirection.UP);
                    this.pushFluid(ForgeDirection.DOWN);
                    this.pushFluid(ForgeDirection.NORTH);
                    this.pushFluid(ForgeDirection.EAST);
                    this.pushFluid(ForgeDirection.SOUTH);
                    this.pushFluid(ForgeDirection.WEST);
                }
            }
        }

        private void pushFluid(ForgeDirection side){
            TileEntity tile = WorldUtil.getTileEntityFromSide(side, worldObj, xCoord, yCoord, zCoord);
            if(tile != null && tile instanceof IFluidHandler && this.getTankInfo(side) != null && this.getTankInfo(side).length > 0 && ((IFluidHandler)tile).getTankInfo(side.getOpposite()) != null && ((IFluidHandler)tile).getTankInfo(side.getOpposite()).length > 0){
                for(FluidTankInfo myInfo : this.getTankInfo(side)){
                    for(FluidTankInfo hisInfo : ((IFluidHandler)tile).getTankInfo(side.getOpposite())){
                        if(myInfo != null && hisInfo != null && myInfo.fluid != null && myInfo.fluid.getFluid() != null){
                            if(((IFluidHandler)tile).canFill(side.getOpposite(), myInfo.fluid.getFluid()) && this.canDrain(side, myInfo.fluid.getFluid())){
                                FluidStack receive = this.drain(side, Math.min(hisInfo.capacity-(hisInfo.fluid == null ? 0 : hisInfo.fluid.amount), myInfo.fluid.amount), false);
                                if(receive != null){
                                    int actualReceive = ((IFluidHandler)tile).fill(side.getOpposite(), receive, true);
                                    this.drain(side, new FluidStack(receive.getFluid(), actualReceive), true);
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public boolean isBoundTileInRage(){
            return super.isBoundTileInRage() && this.boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IFluidHandler;
        }

        public IFluidHandler getHandler(){
            if(this.boundPosition != null && this.boundPosition.getWorld() != null){
                TileEntity tile = boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
                if(tile instanceof IFluidHandler) return (IFluidHandler)tile;
            }
            return null;
        }

        @Override
        public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
            if(this.isBoundTileInRage()) return this.getHandler().fill(from, resource, doFill);
            return 0;
        }

        @Override
        public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
            if(this.isBoundTileInRage()) return this.getHandler().drain(from, resource, doDrain);
            return null;
        }

        @Override
        public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
            if(this.isBoundTileInRage()) return this.getHandler().drain(from, maxDrain, doDrain);
            return null;
        }

        @Override
        public boolean canFill(ForgeDirection from, Fluid fluid){
            return this.isBoundTileInRage() && this.getHandler().canFill(from, fluid);
        }

        @Override
        public boolean canDrain(ForgeDirection from, Fluid fluid){
            return this.isBoundTileInRage() && this.getHandler().canDrain(from, fluid);
        }

        @Override
        public FluidTankInfo[] getTankInfo(ForgeDirection from){
            if(this.isBoundTileInRage()) return this.getHandler().getTankInfo(from);
            return new FluidTankInfo[0];
        }
    }

    public static class TileEntityPhantomEnergyface extends TileEntityPhantomface implements IEnergyHandler{

        public TileEntityPhantomEnergyface(){
            super("energyface");
            this.type = BlockPhantomface.ENERGYFACE;
        }

        @Override
        public boolean isBoundTileInRage(){
            return super.isBoundTileInRage() && (this.boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IEnergyReceiver || this.boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IEnergyProvider);
        }

        @Override
        public void updateEntity(){
            super.updateEntity();

            if(!worldObj.isRemote){
                if(this.isBoundTileInRage() && this.getProvider() != null){
                    this.pushEnergy(ForgeDirection.UP);
                    this.pushEnergy(ForgeDirection.DOWN);
                    this.pushEnergy(ForgeDirection.NORTH);
                    this.pushEnergy(ForgeDirection.EAST);
                    this.pushEnergy(ForgeDirection.SOUTH);
                    this.pushEnergy(ForgeDirection.WEST);
                }
            }
        }

        private void pushEnergy(ForgeDirection side){
            TileEntity tile = WorldUtil.getTileEntityFromSide(side, worldObj, xCoord, yCoord, zCoord);
            if(tile != null && tile instanceof IEnergyReceiver && this.getProvider().getEnergyStored(ForgeDirection.UNKNOWN) > 0){
                if(((IEnergyReceiver)tile).canConnectEnergy(side.getOpposite()) && this.canConnectEnergy(side)){
                    int receive = this.extractEnergy(side, Math.min(((IEnergyReceiver)tile).getMaxEnergyStored(ForgeDirection.UNKNOWN)-((IEnergyReceiver)tile).getEnergyStored(ForgeDirection.UNKNOWN), this.getEnergyStored(ForgeDirection.UNKNOWN)), true);
                    int actualReceive = ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), receive, false);
                    this.extractEnergy(side, actualReceive, false);
                }
            }
        }

        public IEnergyProvider getProvider(){
            if(this.boundPosition != null && this.boundPosition.getWorld() != null){
                TileEntity tile = boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
                if(tile instanceof IEnergyProvider) return (IEnergyProvider)tile;
            }
            return null;
        }

        public IEnergyReceiver getReceiver(){
            if(this.boundPosition != null && this.boundPosition.getWorld() != null){
                TileEntity tile = boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
                if(tile instanceof IEnergyReceiver) return (IEnergyReceiver)tile;
            }
            return null;
        }

        @Override
        public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
            return this.isBoundTileInRage() && this.getReceiver() != null ? this.getReceiver().receiveEnergy(from, maxReceive, simulate) : 0;
        }

        @Override
        public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
            return this.isBoundTileInRage() && this.getProvider() != null ? this.getProvider().extractEnergy(from, maxExtract, simulate) : 0;
        }

        @Override
        public int getEnergyStored(ForgeDirection from){
            if(this.isBoundTileInRage()){
                if(this.getProvider() != null) return this.getProvider().getEnergyStored(from);
                if(this.getReceiver() != null) return this.getReceiver().getEnergyStored(from);
            }
            return 0;
        }

        @Override
        public int getMaxEnergyStored(ForgeDirection from){
            if(this.isBoundTileInRage()){
                if(this.getProvider() != null) return this.getProvider().getMaxEnergyStored(from);
                if(this.getReceiver() != null) return this.getReceiver().getMaxEnergyStored(from);
            }
            return 0;
        }

        @Override
        public boolean canConnectEnergy(ForgeDirection from){
            if(this.isBoundTileInRage()){
                if(this.getProvider() != null) return this.getProvider().canConnectEnergy(from);
                if(this.getReceiver() != null) return this.getReceiver().canConnectEnergy(from);
            }
            return false;
        }
    }

    public static class TileEntityPhantomItemface extends TileEntityPhantomface{

        public TileEntityPhantomItemface(){
            super("phantomface");
            this.type = BlockPhantomface.FACE;
        }

        public IInventory getInventory(){
            if(this.boundPosition != null && this.boundPosition.getWorld() != null){
                TileEntity tile = boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
                if(tile instanceof IInventory) return (IInventory)tile;
            }
            return null;
        }

        @Override
        public boolean isBoundTileInRage(){
            return super.isBoundTileInRage() && this.boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IInventory;
        }

        public ISidedInventory getSided(){
            return this.getInventory() instanceof ISidedInventory ? (ISidedInventory)this.getInventory() : null;
        }

        @Override
        public int getInventoryStackLimit(){
            return this.isBoundTileInRage() ? this.getInventory().getInventoryStackLimit() : 0;
        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer player){
            return false;
        }

        @Override
        public boolean isItemValidForSlot(int i, ItemStack stack){
            return this.isBoundTileInRage() && this.getInventory().isItemValidForSlot(i, stack);
        }

        @Override
        public ItemStack getStackInSlotOnClosing(int i){
            return this.isBoundTileInRage() ? this.getInventory().getStackInSlotOnClosing(i) : null;
        }

        @Override
        public void setInventorySlotContents(int i, ItemStack stack){
            if(this.isBoundTileInRage()) this.getInventory().setInventorySlotContents(i, stack);
            this.markDirty();
        }

        @Override
        public int getSizeInventory(){
            return this.isBoundTileInRage() ? this.getInventory().getSizeInventory() : 0;
        }

        @Override
        public ItemStack getStackInSlot(int i){
            return this.isBoundTileInRage() ? this.getInventory().getStackInSlot(i) : null;
        }

        @Override
        public ItemStack decrStackSize(int i, int j){
            return this.isBoundTileInRage() ? this.getInventory().decrStackSize(i, j) : null;
        }

        @Override
        public String getInventoryName(){
            return this.name;
        }

        @Override
        public int[] getAccessibleSlotsFromSide(int side){
            if(this.isBoundTileInRage()){
                if(this.getSided() != null){
                    return this.getSided().getAccessibleSlotsFromSide(side);
                }
                else{
                    int[] theInt = new int[this.getSizeInventory()];
                    for(int i = 0; i < theInt.length; i++){
                        theInt[i] = i;
                    }
                    return theInt;
                }
            }
            return new int[0];
        }

        @Override
        public boolean canInsertItem(int slot, ItemStack stack, int side){
            return this.isBoundTileInRage() && (this.getSided() == null || this.getSided().canInsertItem(slot, stack, side));
        }

        @Override
        public boolean canExtractItem(int slot, ItemStack stack, int side){
            return this.isBoundTileInRage() && (this.getSided() == null || this.getSided().canExtractItem(slot, stack, side));
        }

    }
}
