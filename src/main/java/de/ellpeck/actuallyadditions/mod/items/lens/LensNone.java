/*
 * This file ("LensNone.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;


import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class LensNone extends Lens{

    @SuppressWarnings("unchecked")
    @Override
    public boolean invoke(BlockPos hitBlock, IAtomicReconstructor tile){
        if(hitBlock != null && !PosUtil.getBlock(hitBlock, tile.getWorldObject()).isAir(tile.getWorldObject(), hitBlock)){
            int range = 2;

            //Converting the Blocks
            for(int reachX = -range; reachX < range+1; reachX++){
                for(int reachZ = -range; reachZ < range+1; reachZ++){
                    for(int reachY = -range; reachY < range+1; reachY++){
                        BlockPos pos = new BlockPos(hitBlock.getX()+reachX, hitBlock.getY()+reachY, hitBlock.getZ()+reachZ);
                        List<LensNoneRecipe> recipes = LensNoneRecipeHandler.getRecipesFor(new ItemStack(PosUtil.getBlock(pos, tile.getWorldObject()), 1, PosUtil.getMetadata(pos, tile.getWorldObject())));
                        for(LensNoneRecipe recipe : recipes){
                            if(recipe != null && tile.getEnergy() >= recipe.energyUse){
                                List<ItemStack> outputs = recipe.getOutputs();
                                if(outputs != null && !outputs.isEmpty()){
                                    ItemStack output = outputs.get(0);
                                    if(output.getItem() instanceof ItemBlock){
                                        if(!ConfigValues.lessBlockBreakingEffects){
                                            tile.getWorldObject().playAuxSFX(2001, pos, Block.getIdFromBlock(PosUtil.getBlock(pos, tile.getWorldObject()))+(PosUtil.getMetadata(pos, tile.getWorldObject()) << 12));
                                        }
                                        PosUtil.setBlock(pos, tile.getWorldObject(), Block.getBlockFromItem(output.getItem()), output.getItemDamage(), 2);
                                    }
                                    else{
                                        EntityItem item = new EntityItem(tile.getWorldObject(), pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, output.copy());
                                        tile.getWorldObject().spawnEntityInWorld(item);
                                    }
                                    tile.extractEnergy(recipe.energyUse);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            //Converting the Items
            ArrayList<EntityItem> items = (ArrayList<EntityItem>)tile.getWorldObject().getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.fromBounds(hitBlock.getX()-range, hitBlock.getY()-range, hitBlock.getZ()-range, hitBlock.getX()+range, hitBlock.getY()+range, hitBlock.getZ()+range));
            for(EntityItem item : items){
                ItemStack stack = item.getEntityItem();
                if(!item.isDead && stack != null){
                    List<LensNoneRecipe> recipes = LensNoneRecipeHandler.getRecipesFor(stack);
                    for(LensNoneRecipe recipe : recipes){
                        if(recipe != null && tile.getEnergy() >= recipe.energyUse){
                            List<ItemStack> outputs = recipe.getOutputs();
                            if(outputs != null && !outputs.isEmpty()){
                                ItemStack outputCopy = outputs.get(0).copy();
                                outputCopy.stackSize = stack.stackSize;

                                item.setDead();

                                EntityItem newItem = new EntityItem(tile.getWorldObject(), item.posX, item.posY, item.posZ, outputCopy);
                                tile.getWorldObject().spawnEntityInWorld(newItem);

                                tile.extractEnergy(recipe.energyUse);
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
