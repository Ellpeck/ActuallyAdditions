/*
 * This file ("VillageComponentJamHouse.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.gen;

import de.ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.common.ChestGenHooks;

import java.util.List;
import java.util.Random;

public class VillageComponentJamHouse extends StructureVillagePieces.House1{

    private static final int xSize = 11;
    private static final int ySize = 8;
    private static final int zSize = 12;

    private int averageGroundLevel = -1;

    @SuppressWarnings("unused")
    public VillageComponentJamHouse(){

    }

    public VillageComponentJamHouse(StructureBoundingBox boundingBox, int par5){
        this.coordBaseMode = par5;
        this.boundingBox = boundingBox;
    }

    public static VillageComponentJamHouse buildComponent(List pieces, int p1, int p2, int p3, int p4){
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, xSize, ySize, zSize, p4);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(pieces, boundingBox) == null ? new VillageComponentJamHouse(boundingBox, p4) : null;
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
                this.func_151554_b(world, Blocks.cobblestone, 0, i, -1, j, sbb);
            }
        }

        this.spawnVillagers(world, sbb, 3, 1, 3, 1);

        return true;
    }

    public void fillWithBlocks(World world, StructureBoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block){
        this.fillWithBlocks(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, block, block, false);
    }

    public void spawnActualHouse(World world, Random rand, StructureBoundingBox sbb){
        //Base
        this.fillWithBlocks(world, sbb, 1, 0, 8, 9, 0, 10, Blocks.grass);
        this.fillWithBlocks(world, sbb, 0, 0, 0, 1, 0, 7, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 2, 0, 0, 4, 0, 1, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 9, 0, 0, 10, 0, 7, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 6, 0, 0, 8, 0, 1, Blocks.cobblestone);
        this.placeBlockAtCurrentPosition(world, Blocks.stone_stairs, this.getMetadataWithOffset(Blocks.stone_stairs, 3), 5, 0, 0, sbb);
        this.fillWithBlocks(world, sbb, 2, 0, 7, 3, 0, 7, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 5, 0, 7, 8, 0, 7, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 10, 0, 8, 10, 0, 11, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 0, 0, 8, 0, 0, 11, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 0, 0, 11, 10, 0, 11, Blocks.cobblestone);
        this.fillWithMetadataBlocks(world, sbb, 2, 0, 2, 8, 0, 6, Blocks.planks, 1, Blocks.planks, 1, false);
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 1, 5, 0, 1, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 1, 4, 0, 7, sbb);

        //Garden Fence
        this.fillWithBlocks(world, sbb, 0, 1, 8, 0, 1, 11, Blocks.fence);
        this.fillWithBlocks(world, sbb, 10, 1, 8, 10, 1, 11, Blocks.fence);
        this.fillWithBlocks(world, sbb, 1, 1, 11, 9, 1, 11, Blocks.fence);

        //Side Walls
        for(int i = 0; i < 2; i++){
            this.fillWithBlocks(world, sbb, 1+i*8, 1, 1, 1+i*8, 1, 7, Blocks.cobblestone);
            this.fillWithBlocks(world, sbb, 1+i*8, 1, 1, 1+i*8, 4, 1, Blocks.cobblestone);
            this.fillWithBlocks(world, sbb, 1+i*8, 1, 7, 1+i*8, 4, 7, Blocks.cobblestone);
            this.fillWithBlocks(world, sbb, 1+i*8, 4, 2, 1+i*8, 5, 6, Blocks.cobblestone);
            this.fillWithBlocks(world, sbb, 1+i*8, 3, 2, 1+i*8, 3, 6, Blocks.planks);
            this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 1+i*8, 2, 2, sbb);
            this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 1+i*8, 2, 6, sbb);
            this.fillWithBlocks(world, sbb, 1+i*8, 2, 3, 1+i*8, 2, 5, Blocks.glass_pane);
        }

        //Front Wall
        this.fillWithBlocks(world, sbb, 7, 1, 1, 8, 4, 1, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 2, 1, 1, 3, 4, 1, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 4, 4, 1, 7, 4, 1, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 6, 1, 1, 6, 3, 1, Blocks.planks);
        this.fillWithBlocks(world, sbb, 4, 1, 1, 4, 3, 1, Blocks.planks);
        this.fillWithBlocks(world, sbb, 5, 3, 1, 5, 3, 1, Blocks.planks);
        this.placeDoorAtCurrentPosition(world, sbb, rand, 5, 1, 1, this.getMetadataWithOffset(Blocks.wooden_door, 1));

        //Back Wall
        this.fillWithBlocks(world, sbb, 2, 1, 7, 2, 4, 7, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 3, 1, 7, 3, 3, 7, Blocks.planks);
        this.fillWithBlocks(world, sbb, 4, 3, 7, 8, 3, 7, Blocks.planks);
        this.fillWithBlocks(world, sbb, 3, 4, 7, 8, 4, 7, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 5, 1, 7, 5, 2, 7, Blocks.planks);
        this.fillWithBlocks(world, sbb, 6, 1, 7, 8, 1, 7, Blocks.cobblestone);
        this.fillWithBlocks(world, sbb, 6, 2, 7, 7, 2, 7, Blocks.glass_pane);
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 8, 2, 7, sbb);
        this.placeDoorAtCurrentPosition(world, sbb, rand, 4, 1, 7, this.getMetadataWithOffset(Blocks.wooden_door, 1));

        //Fence Supports
        this.fillWithBlocks(world, sbb, 0, 1, 8, 0, 3, 8, Blocks.fence);
        this.fillWithBlocks(world, sbb, 10, 1, 8, 10, 3, 8, Blocks.fence);
        this.fillWithBlocks(world, sbb, 0, 1, 0, 0, 3, 0, Blocks.fence);
        this.fillWithBlocks(world, sbb, 10, 1, 0, 10, 3, 0, Blocks.fence);

        //Roof
        this.fillWithBlocks(world, sbb, 1, 6, 3, 9, 6, 5, Blocks.planks);
        this.fillWithMetadataBlocks(world, sbb, 0, 4, 0, 10, 4, 0, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 3), Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 3), false);
        this.fillWithMetadataBlocks(world, sbb, 0, 5, 1, 10, 5, 1, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 3), Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 3), false);
        this.fillWithMetadataBlocks(world, sbb, 0, 6, 2, 10, 6, 2, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 3), Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 3), false);
        this.fillWithMetadataBlocks(world, sbb, 0, 4, 8, 10, 4, 8, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 2), Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 2), false);
        this.fillWithMetadataBlocks(world, sbb, 0, 5, 7, 10, 5, 7, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 2), Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 2), false);
        this.fillWithMetadataBlocks(world, sbb, 0, 6, 6, 10, 6, 6, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 2), Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 2), false);
        this.fillWithBlocks(world, sbb, 0, 7, 3, 10, 7, 5, Blocks.wooden_slab);

        //Roof Gadgets
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 0, 4, 1, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 0, 5, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 10, 4, 1, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 10, 5, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 0, 4, 7, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 0, 5, 6, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 10, 4, 7, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 10, 5, 6, sbb);
        this.fillWithBlocks(world, sbb, 0, 6, 3, 0, 6, 5, Blocks.planks);
        this.fillWithBlocks(world, sbb, 10, 6, 3, 10, 6, 5, Blocks.planks);

        //Counter
        this.fillWithMetadataBlocks(world, sbb, 6, 3, 2, 6, 3, 4, Blocks.wooden_slab, 8, Blocks.wooden_slab, 8, false);
        this.fillWithMetadataBlocks(world, sbb, 5, 3, 4, 5, 3, 6, Blocks.wooden_slab, 8, Blocks.wooden_slab, 8, false);
        this.fillWithBlocks(world, sbb, 6, 1, 2, 6, 1, 4, Blocks.planks);
        this.fillWithBlocks(world, sbb, 5, 1, 4, 5, 1, 5, Blocks.planks);
        this.fillWithBlocks(world, sbb, 6, 4, 2, 6, 5, 2, Blocks.fence);
        this.fillWithBlocks(world, sbb, 5, 4, 4, 5, 5, 4, Blocks.fence);
        this.fillWithBlocks(world, sbb, 5, 4, 6, 5, 5, 6, Blocks.fence);

        //Decoration
        this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 2, 1, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.leaves, 0, 2, 2, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 8, 1, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.leaves, 0, 8, 2, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 2, 1, 6, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.leaves, 0, 2, 2, 6, sbb);
        this.fillWithMetadataBlocks(world, sbb, 2, 1, 3, 2, 1, 5, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 1), Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 1), false);
        this.fillWithMetadataBlocks(world, sbb, 3, 1, 2, 5, 1, 3, Blocks.carpet, 10, Blocks.carpet, 10, false);
        this.fillWithMetadataBlocks(world, sbb, 3, 1, 4, 4, 1, 6, Blocks.carpet, 10, Blocks.carpet, 10, false);

        //Loot Chest
        this.placeBlockAtCurrentPosition(world, Blocks.chest, 0, 8, 1, 6, sbb);
        TileEntity chest = world.getTileEntity(this.getXWithOffset(8, 6), this.getYWithOffset(1), this.getZWithOffset(8, 6));
        if(chest != null && chest instanceof TileEntityChest){
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(InitVillager.JAM_HOUSE_CHEST_NAME, rand), (TileEntityChest)chest, ChestGenHooks.getCount(InitVillager.JAM_HOUSE_CHEST_NAME, rand));
        }

        //Torches
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 2, 0, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4, 2, 0, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 2, 8, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 3, 2, 8, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 3, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 3, 6, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 8, 3, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 8, 3, 6, sbb);
    }

    @Override
    protected int getVillagerType(int par1){
        return ConfigIntValues.JAM_VILLAGER_ID.getValue();
    }
}
