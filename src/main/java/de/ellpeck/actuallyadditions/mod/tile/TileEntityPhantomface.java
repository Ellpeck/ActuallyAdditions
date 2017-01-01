/*
 * This file ("TileEntityPhantomface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityPhantomface extends TileEntityInventoryBase implements IPhantomTile{

    public static final int RANGE = 16;
    public BlockPos boundPosition;
    public BlockPhantom.Type type;
    public int range;
    private int rangeBefore;
    private BlockPos boundPosBefore;
    private Block boundBlockBefore;

    public TileEntityPhantomface(String name){
        super(0, name);
    }

    public static int upgradeRange(int defaultRange, World world, BlockPos pos){
        int newRange = defaultRange;
        for(int i = 0; i < 3; i++){
            Block block = world.getBlockState(pos.up(1+i)).getBlock();
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
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("Range", this.range);
            if(this.boundPosition != null){
                compound.setInteger("XCoordOfTileStored", this.boundPosition.getX());
                compound.setInteger("YCoordOfTileStored", this.boundPosition.getY());
                compound.setInteger("ZCoordOfTileStored", this.boundPosition.getZ());
            }
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            int x = compound.getInteger("XCoordOfTileStored");
            int y = compound.getInteger("YCoordOfTileStored");
            int z = compound.getInteger("ZCoordOfTileStored");
            this.range = compound.getInteger("Range");
            if(!(x == 0 && y == 0 && z == 0)){
                this.boundPosition = new BlockPos(x, y, z);
                this.markDirty();
            }
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            this.range = upgradeRange(RANGE, this.world, this.getPos());

            if(!this.hasBoundPosition()){
                this.boundPosition = null;
            }

            if(this.doesNeedUpdateSend()){
                this.onUpdateSent();
            }
        }
        else{
            if(this.boundPosition != null){
                this.renderParticles();
            }
        }
    }

    protected boolean doesNeedUpdateSend(){
        return this.boundPosition != this.boundPosBefore || (this.boundPosition != null && this.world.getBlockState(this.boundPosition).getBlock() != this.boundBlockBefore) || this.rangeBefore != this.range;
    }

    protected void onUpdateSent(){
        this.rangeBefore = this.range;
        this.boundPosBefore = this.boundPosition;
        this.boundBlockBefore = this.boundPosition == null ? null : this.world.getBlockState(this.boundPosition).getBlock();

        if(this.boundPosition != null){
            this.world.notifyNeighborsOfStateChange(this.pos, this.world.getBlockState(this.boundPosition).getBlock(), false);
        }

        this.sendUpdate();
        this.markDirty();
    }

    @Override
    public boolean hasBoundPosition(){
        if(this.boundPosition != null){
            if(this.world.getTileEntity(this.boundPosition) instanceof IPhantomTile || (this.getPos().getX() == this.boundPosition.getX() && this.getPos().getY() == this.boundPosition.getY() && this.getPos().getZ() == this.boundPosition.getZ())){
                this.boundPosition = null;
                return false;
            }
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(this.world.rand.nextInt(2) == 0){
            double d1 = (double)((float)this.boundPosition.getY()+this.world.rand.nextFloat());
            int i1 = this.world.rand.nextInt(2)*2-1;
            int j1 = this.world.rand.nextInt(2)*2-1;
            double d4 = ((double)this.world.rand.nextFloat()-0.5D)*0.125D;
            double d2 = (double)this.boundPosition.getZ()+0.5D+0.25D*(double)j1;
            double d5 = (double)(this.world.rand.nextFloat()*1.0F*(float)j1);
            double d0 = (double)this.boundPosition.getX()+0.5D+0.25D*(double)i1;
            double d3 = (double)(this.world.rand.nextFloat()*1.0F*(float)i1);
            this.world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public boolean isBoundThingInRange(){
        return this.hasBoundPosition() && this.boundPosition.distanceSq(this.getPos()) <= this.range*this.range;
    }

    @Override
    public BlockPos getBoundPosition(){
        return this.boundPosition;
    }

    @Override
    public void setBoundPosition(BlockPos pos){
        this.boundPosition = pos;
    }

    @Override
    public int getGuiID(){
        return -1;
    }

    @Override
    public int getRange(){
        return this.range;
    }

    protected abstract boolean isCapabilitySupported(Capability<?> capability);

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        if(this.isBoundThingInRange() && this.isCapabilitySupported(capability)){
            TileEntity tile = this.world.getTileEntity(this.getBoundPosition());
            if(tile != null){
                return tile.hasCapability(capability, facing);
            }
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if(this.isBoundThingInRange() && this.isCapabilitySupported(capability)){
            TileEntity tile = this.world.getTileEntity(this.getBoundPosition());
            if(tile != null){
                return tile.getCapability(capability, facing);
            }
        }
        return super.getCapability(capability, facing);
    }
}
