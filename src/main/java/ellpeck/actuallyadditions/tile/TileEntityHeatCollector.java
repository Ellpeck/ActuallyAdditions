package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.ArrayList;
import java.util.Random;

public class TileEntityHeatCollector extends TileEntityBase{

    private int randomChance = ConfigValues.heatCollectorRandomChance;
    private int blocksNeeded = ConfigValues.heatCollectorBlocksNeeded;

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            ArrayList<Integer> blocksAround = new ArrayList<Integer>();

            for(int i = 1; i <= 5; i++){
                ChunkCoordinates coords = WorldUtil.getCoordsFromSide(i, xCoord, yCoord, zCoord);
                if(coords != null){
                    Block block = worldObj.getBlock(coords.posX, coords.posY, coords.posZ);
                    if(block != null && block.getMaterial() == Material.lava && worldObj.getBlockMetadata(coords.posX, coords.posY, coords.posZ) == 0){
                        blocksAround.add(i);
                    }
                }
            }

            if(blocksAround.size() >= blocksNeeded){
                TileEntity tileAbove = WorldUtil.getTileEntityFromSide(0, worldObj, xCoord, yCoord, zCoord);

                TileEntityFurnaceSolar.givePowerTo(tileAbove);

                Random rand = new Random();
                if(rand.nextInt(randomChance) == 0){
                    int randomSide = blocksAround.get(rand.nextInt(blocksAround.size()));
                    WorldUtil.breakBlockAtSide(randomSide, worldObj, xCoord, yCoord, zCoord);
                }
            }
        }
    }
}
