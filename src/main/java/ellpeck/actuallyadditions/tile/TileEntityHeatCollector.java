package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.ConfigValues;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

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
                ChunkCoordinates coords = getBlockFromSide(i, xCoord, yCoord, zCoord);
                if(coords != null){
                    Block block = worldObj.getBlock(coords.posX, coords.posY, coords.posZ);
                    if(block != null && block.getMaterial() == Material.lava && worldObj.getBlockMetadata(coords.posX, coords.posY, coords.posZ) == 0){
                        blocksAround.add(i);
                    }
                }
            }

            if(blocksAround.size() >= blocksNeeded){
                TileEntity tileAbove = TileEntityInputter.getTileEntityFromSide(0, worldObj, xCoord, yCoord, zCoord);

                TileEntityFurnaceSolar.givePowerTo(tileAbove);

                Random rand = new Random();
                if(rand.nextInt(randomChance) == 0){
                    int randomSide = blocksAround.get(rand.nextInt(blocksAround.size()));
                    breakBlockAtSide(randomSide, worldObj, xCoord, yCoord, zCoord);
                }
            }
        }
    }

    public static ChunkCoordinates getBlockFromSide(int side, int x, int y, int z){
        if(side == 0) return new ChunkCoordinates(x, y + 1, z);
        if(side == 1) return new ChunkCoordinates(x, y - 1, z);
        if(side == 2) return new ChunkCoordinates(x, y, z - 1);
        if(side == 3) return new ChunkCoordinates(x - 1, y, z);
        if(side == 4) return new ChunkCoordinates(x, y, z + 1);
        if(side == 5) return new ChunkCoordinates(x + 1, y, z);
        else return null;
    }

    public static void breakBlockAtSide(int side, World world, int x, int y, int z){
        if(side == 0) world.setBlockToAir(x, y + 1, z);
        if(side == 1) world.setBlockToAir(x, y - 1, z);
        if(side == 2) world.setBlockToAir(x, y, z - 1);
        if(side == 3) world.setBlockToAir(x - 1, y, z);
        if(side == 4) world.setBlockToAir(x, y, z + 1);
        if(side == 5) world.setBlockToAir(x + 1, y, z);
    }
}
