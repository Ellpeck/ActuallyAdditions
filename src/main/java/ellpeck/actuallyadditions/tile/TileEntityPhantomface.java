/*
 * This file ("TileEntityPhantomface.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.blocks.BlockPhantom;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class TileEntityPhantomface extends TileEntityInventoryBase implements IPhantomTile{

    public WorldPos boundPosition;

    public BlockPhantom.Type type;

    public int range;

    private int rangeBefore;
    private WorldPos boundPosBefore;
    private Block boundBlockBefore;

    public TileEntityPhantomface(String name){
        super(0, name);
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            this.range = upgradeRange(ConfigIntValues.PHANTOMFACE_RANGE.getValue(), worldObj, xCoord, yCoord, zCoord);

            if(!this.hasBoundPosition()){
                this.boundPosition = null;
            }

            if(this.boundPosition != this.boundPosBefore || (this.boundPosition != null && this.boundPosition.getBlock() != this.boundBlockBefore) || this.rangeBefore != this.range){
                this.rangeBefore = this.range;
                this.boundPosBefore = this.boundPosition;
                this.boundBlockBefore = this.boundPosition == null ? null : this.boundPosition.getBlock();
                WorldUtil.updateTileAndTilesAround(this);
            }
        }
    }

    public static int upgradeRange(int defaultRange, World world, int x, int y, int z){
        int newRange = defaultRange;
        for(int i = 0; i < 3; i++){
            Block block = world.getBlock(x, y+1+i, z);
            if(block == InitBlocks.blockPhantomBooster){
                newRange = newRange*2;
            }
            else{
                break;
            }
        }
        return newRange;
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
    public void setBoundPosition(WorldPos pos){
        this.boundPosition = pos == null ? null : pos.copy();
    }

    @Override
    public int getGuiID(){
        return -1;
    }

    @Override
    public int getRange(){
        return this.range;
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
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        if(this.hasBoundPosition()){
            compound.setInteger("XCoordOfTileStored", boundPosition.getX());
            compound.setInteger("YCoordOfTileStored", boundPosition.getY());
            compound.setInteger("ZCoordOfTileStored", boundPosition.getZ());
            compound.setInteger("WorldOfTileStored", boundPosition.getWorld().provider.dimensionId);
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
}
