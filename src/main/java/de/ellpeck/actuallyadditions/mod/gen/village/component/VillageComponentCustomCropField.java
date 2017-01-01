/*
 * This file ("VillageComponentCustomCropField.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.village.component;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

public class VillageComponentCustomCropField extends StructureVillagePieces.House1{

    private static final int X_SIZE = 13;
    private static final int Y_SIZE = 4;
    private static final int Z_SIZE = 9;

    private int averageGroundLevel = -1;

    public VillageComponentCustomCropField(){

    }

    public VillageComponentCustomCropField(StructureBoundingBox boundingBox, EnumFacing par5){
        this.setCoordBaseMode(par5);
        this.boundingBox = boundingBox;
    }

    public static VillageComponentCustomCropField buildComponent(List pieces, int p1, int p2, int p3, EnumFacing p4){
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, X_SIZE, Y_SIZE, Z_SIZE, p4);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(pieces, boundingBox) == null ? new VillageComponentCustomCropField(boundingBox, p4) : null;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb){
        if(this.averageGroundLevel < 0){
            this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);
            if(this.averageGroundLevel < 0){
                return true;
            }
            this.boundingBox.offset(0, this.averageGroundLevel-this.boundingBox.maxY+Y_SIZE-1, 0);
        }

        this.fillWithBlocks(world, sbb, 0, 0, 0, X_SIZE-1, Y_SIZE-1, Z_SIZE-1, Blocks.AIR);
        this.spawnActualHouse(world, rand, sbb);

        for(int i = 0; i < X_SIZE; i++){
            for(int j = 0; j < Z_SIZE; j++){
                this.clearCurrentPositionBlocksUpwards(world, i, Y_SIZE, j, sbb);
                this.replaceAirAndLiquidDownwards(world, Blocks.DIRT.getDefaultState(), i, -1, j, sbb);
            }
        }

        return true;
    }

    public void fillWithBlocks(World world, StructureBoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block){
        this.fillWithBlocks(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, block.getDefaultState(), block.getDefaultState(), false);
    }

    public void spawnActualHouse(World world, Random rand, StructureBoundingBox sbb){
        this.fillWithBlocks(world, sbb, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND);
        this.fillWithBlocks(world, sbb, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND);
        this.fillWithBlocks(world, sbb, 7, 0, 1, 8, 0, 7, Blocks.FARMLAND);
        this.fillWithBlocks(world, sbb, 10, 0, 1, 11, 0, 7, Blocks.FARMLAND);
        this.fillWithBlocks(world, sbb, 0, 0, 0, 0, 0, 8, Blocks.LOG);
        this.fillWithBlocks(world, sbb, 6, 0, 0, 6, 0, 8, Blocks.LOG);
        this.fillWithBlocks(world, sbb, 12, 0, 0, 12, 0, 8, Blocks.LOG);
        this.fillWithBlocks(world, sbb, 1, 0, 0, 11, 0, 0, Blocks.LOG);
        this.fillWithBlocks(world, sbb, 1, 0, 8, 11, 0, 8, Blocks.LOG);
        this.fillWithBlocks(world, sbb, 3, 0, 1, 3, 0, 7, Blocks.WATER);
        this.fillWithBlocks(world, sbb, 9, 0, 1, 9, 0, 7, Blocks.WATER);

        for(int i = 1; i <= 7; ++i){
            this.setBlockState(world, this.getRandomCropType(rand), 1, 1, i, sbb);
            this.setBlockState(world, this.getRandomCropType(rand), 2, 1, i, sbb);
            this.setBlockState(world, this.getRandomCropType(rand), 4, 1, i, sbb);
            this.setBlockState(world, this.getRandomCropType(rand), 5, 1, i, sbb);
            this.setBlockState(world, this.getRandomCropType(rand), 7, 1, i, sbb);
            this.setBlockState(world, this.getRandomCropType(rand), 8, 1, i, sbb);
            this.setBlockState(world, this.getRandomCropType(rand), 10, 1, i, sbb);
            this.setBlockState(world, this.getRandomCropType(rand), 11, 1, i, sbb);
        }
    }

    private IBlockState getRandomCropType(Random rand){
        int randomMeta = MathHelper.getInt(rand, 1, 7);
        switch(rand.nextInt(4)){
            case 0:
                return InitBlocks.blockFlax.getStateFromMeta(randomMeta);
            case 1:
                return InitBlocks.blockCoffee.getStateFromMeta(randomMeta);
            case 2:
                return InitBlocks.blockRice.getStateFromMeta(randomMeta);
            default:
                return InitBlocks.blockCanola.getStateFromMeta(randomMeta);
        }
    }
}
