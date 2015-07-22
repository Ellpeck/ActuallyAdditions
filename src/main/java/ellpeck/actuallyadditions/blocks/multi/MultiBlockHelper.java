package ellpeck.actuallyadditions.blocks.multi;

import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.ArrayList;

public class MultiBlockHelper{

    /**
     * Checks if a MultiBlock can be created and creates it
     * Made for rectangular MultiBlocks that don't need special Blocks in certain places
     * @param block The MultiBlock Part the method is getting called from
     * @return The Multiblock if it worked, or null if it didn't
     */
    public static ArrayList<WorldPos> createMultiBlock(IMultiBlock block, World blockWorld, int blockX, int blockY, int blockZ){
        if(blockWorld.isRemote) return null;

        ArrayList<WorldPos> blocks = new ArrayList<WorldPos>();

        int maxX = 0;
        int minX = 0;
        int maxZ = 0;
        int minZ = 0;
        int maxY = 0;
        int minY = 0;

        //Setting Min and Max Boundaries of the MultiBlock

        //Horizontal X+                                 +1/-1 to prevent bigger MultiBlocks from working too
        for(int i = blockX; i < blockX+block.getSizeHor()+1; i++){
            if(!containsBlock(block.getNeededBlocks(), blockWorld.getBlock(i, blockY, blockZ))){
                //TODO To Fix the two MultiBlocks next to each other issue, try to take away the -1, check for more Blocks
                //TODO That could fail the thing and then, later, remove one Block and check if the size is correct still
                maxX = i-1;
                break;
            }
            else maxX = i;
        }

        //Horizontal X-
        for(int i = blockX; i >= blockX-block.getSizeHor()-1; i--){
            if(!containsBlock(block.getNeededBlocks(), blockWorld.getBlock(i, blockY, blockZ))){
                minX = i+1;
                break;
            }
            else minX = i;
        }

        //Horizontal Z+
        for(int i = blockZ; i < blockZ+block.getSizeHor()+1; i++){
            if(!containsBlock(block.getNeededBlocks(), blockWorld.getBlock(blockX, blockY, i))){
                maxZ = i-1;
                break;
            }
            else maxZ = i;
        }

        //Horizontal Z-
        for(int i = blockZ; i >= blockZ-block.getSizeHor()-1; i--){
            if(!containsBlock(block.getNeededBlocks(), blockWorld.getBlock(blockX, blockY, i))){
                minZ = i+1;
                break;
            }
            else minZ = i;
        }

        //Horizontal Y+
        for(int i = blockY; i < blockY+block.getSizeVer()+1; i++){
            if(!containsBlock(block.getNeededBlocks(), blockWorld.getBlock(blockX, i, blockZ))){
                maxY = i-1;
                break;
            }
            else maxY = i;
        }

        //Horizontal Y-
        for(int i = blockY; i >= blockY-block.getSizeVer()-1; i--){
            if(!containsBlock(block.getNeededBlocks(), blockWorld.getBlock(blockX, i, blockZ))){
                minY = i+1;
                break;
            }
            else minY = i;
        }

        boolean failedOnce = false;

        //Actually getting the MultiBlock
        //For all of the coordinates gotten before, get the Blocks and store them in the list
        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                                                                                            //Needs to be the exact size, not too small
                    if(containsBlock(block.getNeededBlocks(), blockWorld.getBlock(x, y, z)) && maxX+1-minX == block.getSizeHor() && maxZ+1-minZ == block.getSizeHor() && maxY+1-minY == block.getSizeVer()){
                        //Add the Block to the List to return
                        blocks.add(new WorldPos(blockWorld, x, y, z));
                        //Set the Block to MultiBlock-"State"
                        blockWorld.setBlockMetadataWithNotify(x, y, z, 1, 2);
                    }
                    else{
                        failedOnce = true;
                        blockWorld.setBlockMetadataWithNotify(x, y, z, 0, 2);
                    }
                }
            }
        }

        //Reset the Blocks if the Check failed at any point
        if(failedOnce){
            for(WorldPos aBlock : blocks){
                if(containsBlock(block.getNeededBlocks(), aBlock.getBlock())){
                    aBlock.getWorld().setBlockMetadataWithNotify(aBlock.getX(), aBlock.getY(), aBlock.getZ(), 0, 2);
                }
            }
        }

        return failedOnce ? null : blocks;
    }

    private static boolean containsBlock(Block[] blocks, Block block){
        for(Block aBlock : blocks){
            if(aBlock.equals(block)) return true;
        }
        return false;
    }
}
