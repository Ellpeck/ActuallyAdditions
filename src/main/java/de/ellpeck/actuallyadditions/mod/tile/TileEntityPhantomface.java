/*
 * This file ("TileEntityPhantomface.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.network.PacketParticle;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityPhantomface extends TileEntityInventoryBase implements IPhantomTile{

    public static final int RANGE = 16;
    public static final float[] COLORS = new float[]{93F/255F, 43F/255F, 181F/255F};
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
            Block block = PosUtil.getBlock(PosUtil.offset(pos, 0, 1+i, 0), world);
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
        this.range = compound.getInteger("Range");
        if(!(x == 0 && y == 0 && z == 0)){
            this.boundPosition = new BlockPos(x, y, z);
            this.markDirty();
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            this.range = upgradeRange(RANGE, worldObj, this.getPos());

            if(!this.hasBoundPosition()){
                this.boundPosition = null;
            }

            if(this.boundPosition != this.boundPosBefore || (this.boundPosition != null && PosUtil.getBlock(this.boundPosition, worldObj) != this.boundBlockBefore) || this.rangeBefore != this.range){
                this.rangeBefore = this.range;
                this.boundPosBefore = this.boundPosition;
                this.boundBlockBefore = this.boundPosition == null ? null : PosUtil.getBlock(this.boundPosition, this.worldObj);

                IBlockState myState = this.worldObj.getBlockState(this.pos);
                this.worldObj.notifyBlockUpdate(this.pos, myState, myState, 3);

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
    public boolean isItemValidForSlot(int slot, ItemStack stack){
        return false;
    }

    @Override
    public boolean hasBoundPosition(){
        if(this.boundPosition != null){
            if(worldObj.getTileEntity(boundPosition) instanceof IPhantomTile || (this.getPos().getX() == this.boundPosition.getX() && this.getPos().getY() == this.boundPosition.getY() && this.getPos().getZ() == this.boundPosition.getZ())){
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
            worldObj.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }

        if(this.ticksElapsed%80 == 0){
            PacketParticle.renderParticlesFromAToB(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), boundPosition.getX(), boundPosition.getY(), boundPosition.getZ(), 2, 0.35F, COLORS, 3);
        }
    }

    @Override
    public boolean isBoundThingInRange(){
        return this.hasBoundPosition() && PosUtil.toVec(this.boundPosition).distanceTo(PosUtil.toVec(this.getPos())) <= this.range;
    }

    @Override
    public BlockPos getBoundPosition(){
        return this.boundPosition;
    }

    @Override
    public void setBoundPosition(BlockPos pos){
        this.boundPosition = pos == null ? null : PosUtil.copyPos(pos);
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
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return false;
    }
}
