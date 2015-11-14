/*
 * This file ("TileEntityAtomicReconstructor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.recipe.AtomicReconstructorRecipeHandler;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAtomicReconstructor extends TileEntityBase{

    private int currentTime;

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!this.worldObj.isRemote){
            if(this.currentTime > 0){
                this.currentTime--;
                if(this.currentTime <= 0){
                    ForgeDirection sideToManipulate = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));

                    for(int i = 0; i < 10; i++){
                        WorldPos coordsBlock = WorldUtil.getCoordsFromSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord, i);
                        if(coordsBlock != null){
                            if(!coordsBlock.getBlock().isAir(coordsBlock.getWorld(), coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ())){
                                //Converting the Blocks
                                int range = 2; //TODO Config
                                for(int reachX = -range; reachX < range+1; reachX++){
                                    for(int reachZ = -range; reachZ < range+1; reachZ++){
                                        for(int reachY = -range; reachY < range+1; reachY++){
                                            WorldPos pos = new WorldPos(worldObj, coordsBlock.getX()+reachX, coordsBlock.getY()+reachY, coordsBlock.getZ()+reachZ);
                                            AtomicReconstructorRecipeHandler.Recipe recipe = AtomicReconstructorRecipeHandler.getRecipe(pos.getBlock(), pos.getMetadata());
                                            if(recipe != null){
                                                pos.setBlock(recipe.output, recipe.outputMeta, 2);
                                                this.worldObj.playAuxSFX(2001, coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ(), Block.getIdFromBlock(pos.getBlock())+(pos.getMetadata() << 12));
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
            else{
                this.currentTime = 40; //TODO Config
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("CurrentTime", this.currentTime);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.currentTime = compound.getInteger("CurrentTime");
    }

}
