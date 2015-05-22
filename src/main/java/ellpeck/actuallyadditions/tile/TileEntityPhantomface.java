package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class TileEntityPhantomface extends TileEntityInventoryBase{

    public TileEntity boundTile;

    public final int range = ConfigIntValues.PHANTOMFACE_RANGE.getValue();

    public TileEntityPhantomface(){
        super(0, "phantomface");
    }

    @Override
    public void updateEntity(){
        if(!this.hasBoundTile()) this.boundTile = null;

        if(this.tempX > 0 || this.tempY > 0 || this.tempZ > 0){
            this.boundTile = tempWorld.getTileEntity(tempX, tempY, tempZ);
            this.tempX = 0;
            this.tempY = 0;
            this.tempZ = 0;
            this.tempWorld = null;
        }
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

    public boolean hasBoundTile(){
        return this.boundTile != null && boundTile.getWorldObj().getTileEntity(boundTile.xCoord, boundTile.yCoord, boundTile.zCoord) != null && boundTile.getWorldObj() == this.getWorldObj() && boundTile instanceof IInventory;
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

    private int tempX;
    private int tempY;
    private int tempZ;
    private World tempWorld;

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.tempX = compound.getInteger("XCoordOfTileStored");
        this.tempY = compound.getInteger("YCoordOfTileStored");
        this.tempZ = compound.getInteger("ZCoordOfTileStored");
        this.tempWorld = DimensionManager.getWorld(compound.getInteger("WorldOfTileStored"));
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
