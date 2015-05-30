package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityPhantomPlacer extends TileEntityInventoryBase{

    public static class TileEntityPhantomBreaker extends TileEntityPhantomPlacer{

        public TileEntityPhantomBreaker(){
            super(9, "phantomBreaker");
            this.isBreaker = true;
        }

    }

    public ChunkCoordinates boundPosition;
    public World boundWorld;

    public int currentTime;
    public final int timeNeeded = ConfigIntValues.PHANTOM_PLACER_TIME.getValue();

    public final int range = ConfigIntValues.PHANTOM_PLACER_RANGE.getValue();

    public boolean isBreaker;

    public TileEntityPhantomPlacer(int slots, String name){
        super(slots, name);
    }

    public TileEntityPhantomPlacer(){
        super(9, "phantomPlacer");
        this.isBreaker = false;
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){

            if(!this.hasBoundPosition()){
                this.boundPosition = null;
                this.boundWorld = null;
            }

            if(this.isBoundPositionInRange()){
                if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                    if(this.currentTime > 0){
                        this.currentTime--;
                        if(this.currentTime <= 0){
                            if(this.isBreaker){
                                Block blockToBreak = boundWorld.getBlock(boundPosition.posX, boundPosition.posY, boundPosition.posZ);
                                if(blockToBreak != null && blockToBreak.getBlockHardness(boundWorld, boundPosition.posX, boundPosition.posY, boundPosition.posZ) > -1.0F){
                                    ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                                    int meta = boundWorld.getBlockMetadata(boundPosition.posX, boundPosition.posY, boundPosition.posZ);
                                    drops.addAll(blockToBreak.getDrops(boundWorld, boundPosition.posX, boundPosition.posY, boundPosition.posZ, meta, 0));

                                    if(TileEntityBreaker.addToInventory(this.slots, drops, false)){
                                        boundWorld.playAuxSFX(2001, boundPosition.posX, boundPosition.posY, boundPosition.posZ, Block.getIdFromBlock(blockToBreak) + (meta << 12));
                                        WorldUtil.breakBlockAtSide(ForgeDirection.UNKNOWN, boundWorld, boundPosition.posX, boundPosition.posY, boundPosition.posZ);
                                        TileEntityBreaker.addToInventory(this.slots, drops, true);
                                        this.markDirty();
                                    }
                                }
                            }
                            else{
                                if(boundWorld.getBlock(boundPosition.posX, boundPosition.posY, boundPosition.posZ).isReplaceable(boundWorld, boundPosition.posX, boundPosition.posY, boundPosition.posZ)){
                                    int theSlot = TileEntityBreaker.testInventory(this.slots);
                                    this.setInventorySlotContents(theSlot, WorldUtil.placeBlockAtSide(ForgeDirection.UNKNOWN, boundWorld, boundPosition.posX, boundPosition.posY, boundPosition.posZ, this.slots[theSlot]));
                                    if(this.slots[0] != null && this.slots[0].stackSize <= 0) this.slots[0] = null;
                                }
                            }
                        }
                    }
                    else this.currentTime = this.timeNeeded;
                }
            }
        }
    }

    public boolean isBoundPositionInRange(){
        if(this.hasBoundPosition()){
            int xDif = this.boundPosition.posX-this.xCoord;
            int yDif = this.boundPosition.posY-this.yCoord;
            int zDif = this.boundPosition.posZ-this.zCoord;

            if(xDif >= -this.range && xDif <= this.range){
                if(yDif >= -this.range && yDif <= this.range){
                    if(zDif >= -this.range && zDif <= this.range) return true;
                }
            }
        }
        return false;
    }

    public boolean hasBoundPosition(){
        if(this.boundPosition != null && this.boundWorld != null){
            if(this.xCoord == this.boundPosition.posX && this.yCoord == this.boundPosition.posY && this.zCoord == this.boundPosition.posZ && this.worldObj == this.boundWorld){
                this.boundPosition = null;
                this.boundWorld = null;
                return false;
            }
            return this.boundWorld == this.worldObj;
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("Time", currentTime);
        if(this.hasBoundPosition()){
            compound.setInteger("XCoordOfTileStored", boundPosition.posX);
            compound.setInteger("YCoordOfTileStored", boundPosition.posY);
            compound.setInteger("ZCoordOfTileStored", boundPosition.posZ);
            compound.setInteger("WorldOfTileStored", boundWorld.provider.dimensionId);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        int x = compound.getInteger("XCoordOfTileStored");
        int y = compound.getInteger("YCoordOfTileStored");
        int z = compound.getInteger("ZCoordOfTileStored");
        if(x != 0 && y != 0 && z != 0){
            this.boundPosition = new ChunkCoordinates(x, y, z);
            this.boundWorld = DimensionManager.getWorld(compound.getInteger("WorldOfTileStored"));
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return !this.isBreaker;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return this.isBreaker;
    }
}
