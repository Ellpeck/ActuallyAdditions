package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.FakePlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;

import java.util.Random;

public class TileEntityGreenhouseGlass extends TileEntityBase{

    private int timeUntilNextFertToSet = ConfigIntValues.GLASS_TIME_NEEDED.getValue();

    private int timeUntilNextFert;

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && worldObj.isDaytime()){
                ChunkCoordinates blockToFert = this.blockToFertilize();
                if(blockToFert != null){
                    Random rand = new Random();
                    if(this.timeUntilNextFert > 0){
                        this.timeUntilNextFert--;
                        if(timeUntilNextFert <= 0){
                            this.applyBonemealEffect(blockToFert);
                        }
                    }
                    else this.timeUntilNextFert = this.timeUntilNextFertToSet + rand.nextInt(this.timeUntilNextFertToSet/2);
                }
            }
        }
    }

    public ChunkCoordinates blockToFertilize(){
        for(int i = yCoord-1; i > 0; i--){
            Block block = worldObj.getBlock(xCoord, i, zCoord);
            if(block != null && !(block instanceof BlockAir)){
                if(block instanceof IGrowable && !(block instanceof BlockGrass)){
                    return new ChunkCoordinates(xCoord, i, zCoord);
                }
                else return null;
            }
        }
        return null;
    }

    public boolean applyBonemealEffect(ChunkCoordinates coords){
        return ItemDye.applyBonemeal(new ItemStack(Items.dye, 1, 15), worldObj, coords.posX, coords.posY, coords.posZ, FakePlayerUtil.newFakePlayer(worldObj));
    }
}
