/*
 * This file ("TileEntityGreenhouseGlass.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.util.Util;
import ellpeck.actuallyadditions.util.WorldPos;
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
            if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && worldObj.isDaytime()){
                if(this.timeUntilNextFert > 0){
                    this.timeUntilNextFert--;
                    if(timeUntilNextFert <= 0){
                        WorldPos blockToFert = this.blockToFertilize();
                        if(blockToFert != null){
                            int metaBefore = blockToFert.getMetadata();
                            worldObj.getBlock(blockToFert.getX(), blockToFert.getY(), blockToFert.getZ()).updateTick(worldObj, blockToFert.getX(), blockToFert.getY(), blockToFert.getZ(), Util.RANDOM);

                            if(blockToFert.getMetadata() != metaBefore){
                                worldObj.playAuxSFX(2005, blockToFert.getX(), blockToFert.getY(), blockToFert.getZ(), 0);
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

    public WorldPos blockToFertilize(){
        for(int i = yCoord-1; i > 0; i--){
            Block block = worldObj.getBlock(xCoord, i, zCoord);
            if(block != null && !(worldObj.isAirBlock(xCoord, i, zCoord))){
                if((block instanceof IGrowable || block instanceof IPlantable) && !(block instanceof BlockGrass)){
                    return new WorldPos(worldObj, xCoord, i, zCoord);
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
