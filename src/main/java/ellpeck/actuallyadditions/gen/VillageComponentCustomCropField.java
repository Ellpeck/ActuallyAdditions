/*
 * This file ("VillageComponentCustomCropField.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.gen;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

public class VillageComponentCustomCropField extends StructureVillagePieces.House1{

    private static final int xSize = 13;
    private static final int ySize = 4;
    private static final int zSize = 9;

    private int averageGroundLevel = -1;

    @SuppressWarnings("unused")
    public VillageComponentCustomCropField(){

    }

    public VillageComponentCustomCropField(StructureBoundingBox boundingBox, int par5){
        this.coordBaseMode = par5;
        this.boundingBox = boundingBox;
    }

    public static VillageComponentCustomCropField buildComponent(List pieces, int p1, int p2, int p3, int p4){
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, xSize, ySize, zSize, p4);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(pieces, boundingBox) == null ? new VillageComponentCustomCropField(boundingBox, p4) : null;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb){
        if(this.averageGroundLevel < 0){
            this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);
            if(this.averageGroundLevel < 0){
                return true;
            }
            this.boundingBox.offset(0, this.averageGroundLevel-this.boundingBox.maxY+ySize-1, 0);
        }

        this.fillWithBlocks(world, sbb, 0, 0, 0, xSize-1, ySize-1, zSize-1, Blocks.air);
        this.spawnActualHouse(world, rand, sbb);

        for(int i = 0; i < xSize; i++){
            for(int j = 0; j < zSize; j++){
                this.clearCurrentPositionBlocksUpwards(world, i, ySize, j, sbb);
                this.func_151554_b(world, Blocks.dirt, 0, i, -1, j, sbb);
            }
        }

        return true;
    }

    public void fillWithBlocks(World world, StructureBoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block){
        this.fillWithBlocks(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, block, block, false);
    }

    public void spawnActualHouse(World world, Random rand, StructureBoundingBox sbb){
        this.fillWithBlocks(world, sbb, 1, 0, 1, 2, 0, 7, Blocks.farmland);
        this.fillWithBlocks(world, sbb, 4, 0, 1, 5, 0, 7, Blocks.farmland);
        this.fillWithBlocks(world, sbb, 7, 0, 1, 8, 0, 7, Blocks.farmland);
        this.fillWithBlocks(world, sbb, 10, 0, 1, 11, 0, 7, Blocks.farmland);
        this.fillWithBlocks(world, sbb, 0, 0, 0, 0, 0, 8, Blocks.log);
        this.fillWithBlocks(world, sbb, 6, 0, 0, 6, 0, 8, Blocks.log);
        this.fillWithBlocks(world, sbb, 12, 0, 0, 12, 0, 8, Blocks.log);
        this.fillWithBlocks(world, sbb, 1, 0, 0, 11, 0, 0, Blocks.log);
        this.fillWithBlocks(world, sbb, 1, 0, 8, 11, 0, 8, Blocks.log);
        this.fillWithBlocks(world, sbb, 3, 0, 1, 3, 0, 7, Blocks.water);
        this.fillWithBlocks(world, sbb, 9, 0, 1, 9, 0, 7, Blocks.water);

        for(int i = 1; i <= 7; ++i){
            this.placeBlockAtCurrentPosition(world, this.getRandomCropType(rand), MathHelper.getRandomIntegerInRange(rand, 1, 7), 1, 1, i, sbb);
            this.placeBlockAtCurrentPosition(world, this.getRandomCropType(rand), MathHelper.getRandomIntegerInRange(rand, 1, 7), 2, 1, i, sbb);
            this.placeBlockAtCurrentPosition(world, this.getRandomCropType(rand), MathHelper.getRandomIntegerInRange(rand, 1, 7), 4, 1, i, sbb);
            this.placeBlockAtCurrentPosition(world, this.getRandomCropType(rand), MathHelper.getRandomIntegerInRange(rand, 1, 7), 5, 1, i, sbb);
            this.placeBlockAtCurrentPosition(world, this.getRandomCropType(rand), MathHelper.getRandomIntegerInRange(rand, 1, 7), 7, 1, i, sbb);
            this.placeBlockAtCurrentPosition(world, this.getRandomCropType(rand), MathHelper.getRandomIntegerInRange(rand, 1, 7), 8, 1, i, sbb);
            this.placeBlockAtCurrentPosition(world, this.getRandomCropType(rand), MathHelper.getRandomIntegerInRange(rand, 1, 7), 10, 1, i, sbb);
            this.placeBlockAtCurrentPosition(world, this.getRandomCropType(rand), MathHelper.getRandomIntegerInRange(rand, 1, 7), 11, 1, i, sbb);
        }
    }

    private Block getRandomCropType(Random rand){
        switch(rand.nextInt(4)){
            case 0:
                return InitBlocks.blockFlax;
            case 1:
                return InitBlocks.blockCoffee;
            case 2:
                return InitBlocks.blockRice;
            default:
                return InitBlocks.blockCanola;
        }
    }
}
