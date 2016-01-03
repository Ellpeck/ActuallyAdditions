/*
 * This file ("TileEntityPhantomface.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.blocks.BlockPhantom;
import de.ellpeck.actuallyadditions.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.network.PacketParticle;
import de.ellpeck.actuallyadditions.util.Position;
import de.ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityPhantomface extends TileEntityInventoryBase implements IPhantomTile{

    public static final int RANGE = 16;
    public static final float[] COLORS = new float[]{93F/255F, 43F/255F, 181F/255F};
    public Position boundPosition;
    public BlockPhantom.Type type;
    public int range;
    private int rangeBefore;
    private Position boundPosBefore;
    private Block boundBlockBefore;

    public TileEntityPhantomface(String name){
        super(0, name);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            this.range = upgradeRange(RANGE, worldObj, xCoord, yCoord, zCoord);

            if(!this.hasBoundPosition()){
                this.boundPosition = null;
            }

            if(this.boundPosition != this.boundPosBefore || (this.boundPosition != null && this.boundPosition.getBlock(worldObj) != this.boundBlockBefore) || this.rangeBefore != this.range){
                this.rangeBefore = this.range;
                this.boundPosBefore = this.boundPosition;
                this.boundBlockBefore = this.boundPosition == null ? null : this.boundPosition.getBlock(worldObj);

                this.getWorldObj().markBlockForUpdate(this.xCoord+1, this.yCoord, this.zCoord);
                this.getWorldObj().markBlockForUpdate(this.xCoord-1, this.yCoord, this.zCoord);
                this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord+1, this.zCoord);
                this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord-1, this.zCoord);
                this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord+1);
                this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord-1);
                this.sendUpdate();
                this.markDirty();
            }
        }
        else{
            if(this.boundPosition != null){
                this.renderParticles();
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("Range", this.range);
        if(this.boundPosition != null){
            compound.setInteger("XCoordOfTileStored", boundPosition.getX());
            compound.setInteger("YCoordOfTileStored", boundPosition.getY());
            compound.setInteger("ZCoordOfTileStored", boundPosition.getZ());
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        int x = compound.getInteger("XCoordOfTileStored");
        int y = compound.getInteger("YCoordOfTileStored");
        int z = compound.getInteger("ZCoordOfTileStored");
        int world = compound.getInteger("WorldOfTileStored");
        this.range = compound.getInteger("Range");
        if(!(x == 0 && y == 0 && z == 0)){
            this.boundPosition = new Position(x, y, z);
            this.markDirty();
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
        if(this.boundPosition != null){
            if(worldObj.getTileEntity(boundPosition.getX(), boundPosition.getY(), boundPosition.getZ()) instanceof IPhantomTile || (this.xCoord == this.boundPosition.getX() && this.yCoord == this.boundPosition.getY() && this.zCoord == this.boundPosition.getZ())){
                this.boundPosition = null;
                return false;
            }
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(Util.RANDOM.nextInt(2) == 0){
            double d1 = (double)((float)this.boundPosition.getY()+Util.RANDOM.nextFloat());
            int i1 = Util.RANDOM.nextInt(2)*2-1;
            int j1 = Util.RANDOM.nextInt(2)*2-1;
            double d4 = ((double)Util.RANDOM.nextFloat()-0.5D)*0.125D;
            double d2 = (double)this.boundPosition.getZ()+0.5D+0.25D*(double)j1;
            double d5 = (double)(Util.RANDOM.nextFloat()*1.0F*(float)j1);
            double d0 = (double)this.boundPosition.getX()+0.5D+0.25D*(double)i1;
            double d3 = (double)(Util.RANDOM.nextFloat()*1.0F*(float)i1);
            worldObj.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
        }

        if(this.ticksElapsed%80 == 0){
            PacketParticle.renderParticlesFromAToB(xCoord, yCoord, zCoord, boundPosition.getX(), boundPosition.getY(), boundPosition.getZ(), 2, 0.35F, COLORS, 3);
        }
    }

    @Override
    public boolean isBoundThingInRange(){
        return this.hasBoundPosition() && this.boundPosition.toVec().distanceTo(Vec3.createVectorHelper(xCoord, yCoord, zCoord)) <= this.range;
    }

    @Override
    public Position getBoundPosition(){
        return this.boundPosition;
    }

    @Override
    public void setBoundPosition(Position pos){
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
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack){
        return false;
    }
}
