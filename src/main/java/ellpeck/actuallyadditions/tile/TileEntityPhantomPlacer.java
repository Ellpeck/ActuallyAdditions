package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityPhantomPlacer extends TileEntityInventoryBase implements IPhantomTile{

    public static class TileEntityPhantomBreaker extends TileEntityPhantomPlacer{

        public TileEntityPhantomBreaker(){
            super(9, "phantomBreaker");
            this.isBreaker = true;
        }

    }

    public WorldPos boundPosition;

    public int currentTime;
    public int range;

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
            this.range = TileEntityPhantomface.upgradeRange(ConfigIntValues.PHANTOM_PLACER_RANGE.getValue(), worldObj, xCoord, yCoord, zCoord);

            if(!this.hasBoundPosition()){
                this.boundPosition = null;
            }

            if(this.isBoundThingInRange()){
                if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                    if(this.currentTime > 0){
                        this.currentTime--;
                        if(this.currentTime <= 0){
                            if(this.isBreaker){
                                Block blockToBreak = boundPosition.getWorld().getBlock(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
                                if(blockToBreak != null && blockToBreak.getBlockHardness(boundPosition.getWorld(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) > -1.0F){
                                    ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                                    int meta = boundPosition.getWorld().getBlockMetadata(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
                                    drops.addAll(blockToBreak.getDrops(boundPosition.getWorld(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ(), meta, 0));

                                    if(WorldUtil.addToInventory(this.slots, drops, false)){
                                        boundPosition.getWorld().playAuxSFX(2001, boundPosition.getX(), boundPosition.getY(), boundPosition.getZ(), Block.getIdFromBlock(blockToBreak)+(meta << 12));
                                        WorldUtil.breakBlockAtSide(ForgeDirection.UNKNOWN, boundPosition.getWorld(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ());
                                        WorldUtil.addToInventory(this.slots, drops, true);
                                        this.markDirty();
                                    }
                                }
                            }
                            else{
                                if(boundPosition.getWorld().getBlock(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()).isReplaceable(boundPosition.getWorld(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ())){
                                    int theSlot = WorldUtil.findFirstFilledSlot(this.slots);
                                    this.setInventorySlotContents(theSlot, WorldUtil.placeBlockAtSide(ForgeDirection.UNKNOWN, boundPosition.getWorld(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ(), this.slots[theSlot]));
                                    if(this.slots[theSlot] != null && this.slots[theSlot].stackSize <= 0) this.slots[theSlot] = null;
                                }
                            }
                        }
                    }
                    else this.currentTime = ConfigIntValues.PHANTOM_PLACER_TIME.getValue();
                }
            }
        }
    }

    @Override
    public boolean hasBoundPosition(){
        if(this.boundPosition != null && this.boundPosition.getWorld() != null){
            if(this.boundPosition.getWorld().getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IPhantomTile || (this.xCoord == this.boundPosition.getX() && this.yCoord == this.boundPosition.getY() && this.zCoord == this.boundPosition.getZ() && this.worldObj == this.boundPosition.getWorld())){
                this.boundPosition = null;
                return false;
            }
            return this.boundPosition.getWorld() == this.worldObj;
        }
        return false;
    }

    @Override
    public boolean isBoundThingInRange(){
        if(this.hasBoundPosition()){
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
    public WorldPos getBoundPosition(){
        return this.boundPosition;
    }

    @Override
    public int getGuiID(){
        return GuiHandler.GuiTypes.PHANTOM_PLACER.ordinal();
    }

    @Override
    public int getRange(){
        return this.range;
    }

    @Override
    public void setBoundPosition(WorldPos pos){
        this.boundPosition = pos == null ? null : pos.copy();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("Time", currentTime);
        if(this.hasBoundPosition()){
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
