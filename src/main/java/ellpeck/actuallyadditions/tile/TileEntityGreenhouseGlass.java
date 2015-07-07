package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;

import java.util.Random;

public class TileEntityGreenhouseGlass extends TileEntityBase{

    private int timeUntilNextFertToSet = ConfigIntValues.GLASS_TIME_NEEDED.getValue();

    private int timeUntilNextFert;

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && worldObj.isDaytime()){
                WorldPos blockToFert = this.blockToFertilize();
                if(blockToFert != null){
                    if(this.timeUntilNextFert > 0){
                        this.timeUntilNextFert--;
                        if(timeUntilNextFert <= 0){
                            worldObj.getBlock(blockToFert.getX(), blockToFert.getY(), blockToFert.getZ()).updateTick(worldObj, blockToFert.getX(), blockToFert.getY(), blockToFert.getZ(), worldObj.rand);
                            worldObj.playAuxSFX(2005, blockToFert.getX(), blockToFert.getY(), blockToFert.getZ(), 0);
                        }
                    }
                    else this.timeUntilNextFert = this.timeUntilNextFertToSet+new Random().nextInt(this.timeUntilNextFertToSet);
                }
            }
        }
    }

    public WorldPos blockToFertilize(){
        for(int i = yCoord-1; i > 0; i--){
            Block block = worldObj.getBlock(xCoord, i, zCoord);
            if(block != null && !(worldObj.isAirBlock(xCoord, i, zCoord))){
                if(block instanceof IGrowable && !(block instanceof BlockGrass)){
                    return new WorldPos(xCoord, i, zCoord);
                }
                else return null;
            }
        }
        return null;
    }

}
