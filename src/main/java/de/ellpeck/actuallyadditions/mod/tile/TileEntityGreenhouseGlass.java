/*
 * This file ("TileEntityGreenhouseGlass.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;

public class TileEntityGreenhouseGlass extends TileEntityBase{

    private int timeUntilNextFert;

    public TileEntityGreenhouseGlass(){
        super("greenhouseGlass");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.timeUntilNextFert = compound.getInteger("Time");
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("Time", this.timeUntilNextFert);
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            if(this.world.canBlockSeeSky(this.getPos()) && this.world.isDaytime()){
                if(this.timeUntilNextFert > 0){
                    this.timeUntilNextFert--;
                    if(this.timeUntilNextFert <= 0){
                        BlockPos blockToFert = this.blockToFertilize();
                        if(blockToFert != null){
                            IBlockState state = this.world.getBlockState(blockToFert);
                            Block block = state.getBlock();
                            int metaBefore = block.getMetaFromState(state);
                            block.updateTick(this.world, blockToFert, this.world.getBlockState(blockToFert), this.world.rand);

                            IBlockState newState = this.world.getBlockState(blockToFert);
                            if(newState.getBlock().getMetaFromState(newState) != metaBefore){
                                this.world.playEvent(2005, blockToFert, 0);
                            }
                        }
                    }
                }
                else{
                    int time = 50;
                    this.timeUntilNextFert = time+this.world.rand.nextInt(time);
                }
            }
        }
    }

    public BlockPos blockToFertilize(){
        for(int i = this.pos.getY()-1; i > 0; i--){
            BlockPos offset = new BlockPos(this.pos.getX(), i, this.pos.getZ());
            Block block = this.world.getBlockState(offset).getBlock();
            if(block != null && !this.world.isAirBlock(offset)){
                if((block instanceof IGrowable || block instanceof IPlantable) && !(block instanceof BlockGrass)){
                    return offset;
                }
                else{
                    return null;
                }
            }
        }
        return null;
    }
}
