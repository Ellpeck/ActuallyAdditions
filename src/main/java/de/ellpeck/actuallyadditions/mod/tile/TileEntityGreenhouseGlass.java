/*
 * This file ("TileEntityGreenhouseGlass.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IPlantable;

public class TileEntityGreenhouseGlass extends TileEntityBase{

    private int timeUntilNextFert;

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(worldObj.canBlockSeeSky(this.getPos()) && worldObj.isDaytime()){
                if(this.timeUntilNextFert > 0){
                    this.timeUntilNextFert--;
                    if(timeUntilNextFert <= 0){
                        Position blockToFert = this.blockToFertilize();
                        if(blockToFert != null){
                            int metaBefore = blockToFert.getMetadata(worldObj);
                            blockToFert.getBlock(worldObj).updateTick(worldObj, blockToFert, blockToFert.getBlockState(worldObj), Util.RANDOM);

                            if(blockToFert.getMetadata(worldObj) != metaBefore){
                                worldObj.playAuxSFX(2005, blockToFert, 0);
                            }
                        }
                    }
                }
                else{
                    int time = 300;
                    this.timeUntilNextFert = time+Util.RANDOM.nextInt(time);
                }
            }
        }
    }

    public Position blockToFertilize(){
        for(int i = -1; i > 0; i--){
            Position offset = Position.fromBlockPos(pos).getOffsetPosition(0, i, 0);
            Block block = offset.getBlock(worldObj);
            if(block != null && !(worldObj.isAirBlock(offset))){
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

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.writeSyncableNBT(compound, isForSync);
        this.timeUntilNextFert = compound.getInteger("Time");
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.readSyncableNBT(compound, isForSync);
        compound.setInteger("Time", this.timeUntilNextFert);
    }
}
