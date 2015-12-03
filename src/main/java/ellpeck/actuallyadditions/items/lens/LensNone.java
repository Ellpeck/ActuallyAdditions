/*
 * This file ("LensNone.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items.lens;

import ellpeck.actuallyadditions.tile.TileEntityAtomicReconstructor;
import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;

public class LensNone extends Lens{

    @SuppressWarnings("unchecked")
    @Override
    public boolean invoke(WorldPos hitBlock, TileEntityAtomicReconstructor tile){
        if(hitBlock != null && !hitBlock.getBlock().isAir(hitBlock.getWorld(), hitBlock.getX(), hitBlock.getY(), hitBlock.getZ())){
            int range = 2;

            //Converting the Blocks
            for(int reachX = -range; reachX < range+1; reachX++){
                for(int reachZ = -range; reachZ < range+1; reachZ++){
                    for(int reachY = -range; reachY < range+1; reachY++){
                        WorldPos pos = new WorldPos(tile.getWorldObj(), hitBlock.getX()+reachX, hitBlock.getY()+reachY, hitBlock.getZ()+reachZ);
                        ArrayList<LensNoneRecipeHandler.Recipe> recipes = LensNoneRecipeHandler.getRecipesFor(new ItemStack(pos.getBlock(), 1, pos.getMetadata()));
                        for(LensNoneRecipeHandler.Recipe recipe : recipes){
                            if(recipe != null && tile.storage.getEnergyStored() >= recipe.energyUse){
                                List<ItemStack> outputs = recipe.getOutputs();
                                if(outputs != null && !outputs.isEmpty()){
                                    ItemStack output = outputs.get(0);
                                    if(output.getItem() instanceof ItemBlock){
                                        tile.getWorldObj().playAuxSFX(2001, pos.getX(), pos.getY(), pos.getZ(), Block.getIdFromBlock(pos.getBlock())+(pos.getMetadata() << 12));
                                        pos.setBlock(Block.getBlockFromItem(output.getItem()), output.getItemDamage(), 2);
                                    }
                                    else{
                                        EntityItem item = new EntityItem(tile.getWorldObj(), pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, output.copy());
                                        tile.getWorldObj().spawnEntityInWorld(item);
                                    }
                                    tile.storage.extractEnergy(recipe.energyUse, false);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            //Converting the Items
            ArrayList<EntityItem> items = (ArrayList<EntityItem>)tile.getWorldObj().getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(hitBlock.getX()-range, hitBlock.getY()-range, hitBlock.getZ()-range, hitBlock.getX()+range, hitBlock.getY()+range, hitBlock.getZ()+range));
            for(EntityItem item : items){
                ItemStack stack = item.getEntityItem();
                if(stack != null){
                    ArrayList<LensNoneRecipeHandler.Recipe> recipes = LensNoneRecipeHandler.getRecipesFor(stack);
                    for(LensNoneRecipeHandler.Recipe recipe : recipes){
                        if(recipe != null && tile.storage.getEnergyStored() >= recipe.energyUse){
                            List<ItemStack> outputs = recipe.getOutputs();
                            if(outputs != null && !outputs.isEmpty()){
                                ItemStack outputCopy = outputs.get(0).copy();
                                outputCopy.stackSize = stack.stackSize;
                                item.setEntityItemStack(outputCopy);

                                tile.storage.extractEnergy(recipe.energyUse, false);
                                break;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public float[] getColor(){
        return new float[]{27F/255F, 109F/255F, 1F};
    }

    @Override
    public int getDistance(){
        return 10;
    }

}
