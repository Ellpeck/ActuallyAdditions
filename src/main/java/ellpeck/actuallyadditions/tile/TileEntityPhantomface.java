package ellpeck.actuallyadditions.tile;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import ellpeck.actuallyadditions.blocks.BlockPhantomface;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldUtil;
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

    public TileEntity boundTile;

    public int type;

    public final int range = ConfigIntValues.PHANTOMFACE_RANGE.getValue();

    public TileEntityPhantomface(String name){
        super(0, name);
    }

    public boolean canConnectTo(TileEntity tile){
        return false;
    }

    public boolean isBoundTileInRage(){
        if(this.hasBoundTile()){
            int xDif = this.boundTile.xCoord-this.xCoord;
            int yDif = this.boundTile.yCoord-this.yCoord;
            int zDif = this.boundTile.zCoord-this.zCoord;

            if(xDif >= -this.range && xDif <= this.range){
                if(yDif >= -this.range && yDif <= this.range){
                    if(zDif >= -this.range && zDif <= this.range) return true;
                }
            }
        }
        return false;
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){

            //TODO Remove after some Updating
            if(this.type == BlockPhantomface.FACE && this.getClass() != TileEntityPhantomItemface.class){
                ItemStack[] theSlots = this.slots.clone();
                worldObj.removeTileEntity(xCoord, yCoord, zCoord);
                worldObj.setTileEntity(xCoord, yCoord, zCoord, new TileEntityPhantomItemface());
                ((TileEntityPhantomItemface)worldObj.getTileEntity(xCoord, yCoord, zCoord)).slots = theSlots.clone();
            }

            if(!this.hasBoundTile()) this.boundTile = null;

            if(this.tempX > 0 || this.tempY > 0 || this.tempZ > 0){
                this.boundTile = tempWorld.getTileEntity(tempX, tempY, tempZ);
                this.tempX = 0;
                this.tempY = 0;
                this.tempZ = 0;
                this.tempWorld = null;
            }
        }
    }

    public boolean hasBoundTile(){
        if(this.boundTile != null){
            if(this.xCoord == this.boundTile.xCoord && this.yCoord == this.boundTile.yCoord && this.zCoord == this.boundTile.zCoord && this.worldObj == this.boundTile.getWorldObj()){
                this.boundTile = null;
                return false;
            }
            return boundTile.getWorldObj().getTileEntity(boundTile.xCoord, boundTile.yCoord, boundTile.zCoord) == boundTile && boundTile.getWorldObj() == this.worldObj;
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        if(this.hasBoundTile()){
            compound.setInteger("XCoordOfTileStored", boundTile.xCoord);
            compound.setInteger("YCoordOfTileStored", boundTile.yCoord);
            compound.setInteger("ZCoordOfTileStored", boundTile.zCoord);
            compound.setInteger("WorldOfTileStored", boundTile.getWorldObj().provider.dimensionId);
        }
    }

    public int tempX;
    public int tempY;
    public int tempZ;
    public World tempWorld;

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.tempX = compound.getInteger("XCoordOfTileStored");
        this.tempY = compound.getInteger("YCoordOfTileStored");
        this.tempZ = compound.getInteger("ZCoordOfTileStored");
        this.tempWorld = DimensionManager.getWorld(compound.getInteger("WorldOfTileStored"));
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
                if(this.isBoundTileInRage() && this.getHandler() != null){
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
            if(tile != null && tile instanceof IFluidHandler){
                for(FluidTankInfo myInfo : this.getTankInfo(side)){
                    for(FluidTankInfo hisInfo : ((IFluidHandler)tile).getTankInfo(side.getOpposite())){
                        if(myInfo != null && hisInfo != null && myInfo.fluid != null && myInfo.fluid.getFluid() != null){
                            if(((IFluidHandler)tile).canFill(side.getOpposite(), myInfo.fluid.getFluid()) && this.canDrain(side, myInfo.fluid.getFluid())){
                                FluidStack receive = this.drain(side, Math.min(hisInfo.capacity-(hisInfo.fluid == null ? 0 : hisInfo.fluid.amount), myInfo.fluid.amount), false);
                                if(receive != null){
                                    int actualReceive = ((IFluidHandler)tile).fill(side.getOpposite(), receive, true);
                                    this.drain(side, new FluidStack(receive.getFluid(), actualReceive), true);
                                    worldObj.markBlockForUpdate(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ);
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public boolean canConnectTo(TileEntity tile){
            return tile instanceof IFluidHandler;
        }

        public IFluidHandler getHandler(){
            TileEntity tile = boundTile.getWorldObj().getTileEntity(boundTile.xCoord, boundTile.yCoord, boundTile.zCoord);
            if(tile != null && tile instanceof IFluidHandler){
                return (IFluidHandler)tile;
            }
            return null;
        }

        @Override
        public boolean hasBoundTile(){
            return super.hasBoundTile() && this.boundTile instanceof IFluidHandler;
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
                    worldObj.markBlockForUpdate(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ);
                }
            }
        }

        @Override
        public boolean canConnectTo(TileEntity tile){
            return tile instanceof IEnergyProvider || tile instanceof IEnergyReceiver;
        }

        public IEnergyProvider getProvider(){
            TileEntity tile = boundTile.getWorldObj().getTileEntity(boundTile.xCoord, boundTile.yCoord, boundTile.zCoord);
            if(tile != null && tile instanceof IEnergyProvider){
                return (IEnergyProvider)tile;
            }
            return null;
        }

        public IEnergyReceiver getReceiver(){
            TileEntity tile = boundTile.getWorldObj().getTileEntity(boundTile.xCoord, boundTile.yCoord, boundTile.zCoord);
            if(tile != null && tile instanceof IEnergyReceiver){
                return (IEnergyReceiver)tile;
            }
            return null;
        }

        @Override
        public boolean hasBoundTile(){
            return super.hasBoundTile() && (this.boundTile instanceof IEnergyReceiver || this.boundTile instanceof IEnergyProvider);
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

        @Override
        public boolean canConnectTo(TileEntity tile){
            return tile instanceof IInventory;
        }

        @Override
        public boolean hasBoundTile(){
            return super.hasBoundTile() && this.boundTile instanceof IInventory;
        }

        public IInventory getInventory(){
            TileEntity tile = boundTile.getWorldObj().getTileEntity(boundTile.xCoord, boundTile.yCoord, boundTile.zCoord);
            if(tile != null && tile instanceof IInventory){
                return (IInventory)tile;
            }
            return null;
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
