/*
 * This file ("LensColor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.items.lens;

import de.ellpeck.actuallyadditions.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.util.Position;
import de.ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;

public class LensColor extends Lens{

    public static final int ENERGY_USE = 200;
    public static final Object[] CONVERTABLE_BLOCKS = new Object[]{
            Items.dye,
            Blocks.wool,
            Blocks.stained_glass,
            Blocks.stained_glass_pane,
            Blocks.stained_hardened_clay,
            Blocks.carpet,
            InitBlocks.blockColoredLamp,
            InitBlocks.blockColoredLampOn
    };
    //Thanks to xdjackiexd for this, as I couldn't be bothered
    private static final float[][] possibleColorLensColors = {
            {158F, 43F, 39F}, //Red
            {234F, 126F, 53F}, //Orange
            {194F, 181F, 28F}, //Yellow
            {57F, 186F, 46F}, //Lime Green
            {54F, 75F, 24F}, //Green
            {99F, 135F, 210F}, //Light Blue
            {38F, 113F, 145F}, //Cyan
            {37F, 49F, 147F}, //Blue
            {126F, 52F, 191F}, //Purple
            {190F, 73F, 201F}, //Magenta
            {217F, 129F, 153F}, //Pink
            {86F, 51F, 28F}, //Brown
    };

    @SuppressWarnings("unchecked")
    @Override
    public boolean invoke(Position hitBlock, TileEntityAtomicReconstructor tile){
        if(hitBlock != null){
            if(Util.arrayContains(CONVERTABLE_BLOCKS, hitBlock.getBlock(tile.getWorldObj())) >= 0 && tile.storage.getEnergyStored() >= ENERGY_USE){
                int meta = hitBlock.getMetadata(tile.getWorldObj());
                if(meta >= 15){
                    hitBlock.setMetadata(tile.getWorldObj(), 0, 2);
                }
                else{
                    hitBlock.setMetadata(tile.getWorldObj(), meta+1, 2);
                }
                tile.storage.extractEnergy(ENERGY_USE, false);
            }

            ArrayList<EntityItem> items = (ArrayList<EntityItem>)tile.getWorldObj().getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), hitBlock.getX()+1, hitBlock.getY()+1, hitBlock.getZ()+1));
            for(EntityItem item : items){
                if(item.getEntityItem() != null && tile.storage.getEnergyStored() >= ENERGY_USE){
                    if(Util.arrayContains(CONVERTABLE_BLOCKS, item.getEntityItem().getItem()) >= 0 || Util.arrayContains(CONVERTABLE_BLOCKS, Block.getBlockFromItem(item.getEntityItem().getItem())) >= 0){
                        int meta = item.getEntityItem().getItemDamage();
                        if(meta >= 15){
                            item.getEntityItem().setItemDamage(0);
                        }
                        else{
                            item.getEntityItem().setItemDamage(meta+1);
                        }
                        tile.storage.extractEnergy(ENERGY_USE, false);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public float[] getColor(){
        float[] colors = possibleColorLensColors[Util.RANDOM.nextInt(possibleColorLensColors.length)];
        return new float[]{colors[0]/255F, colors[1]/255F, colors[2]/255F};
    }

    @Override
    public int getDistance(){
        return 10;
    }
}
