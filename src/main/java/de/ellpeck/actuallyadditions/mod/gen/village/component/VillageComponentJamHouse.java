/*
 * This file ("VillageComponentJamHouse.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.village.component;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.gen.village.InitVillager;
import de.ellpeck.actuallyadditions.mod.misc.DungeonLoot;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChest;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

import java.util.List;
import java.util.Random;

public class VillageComponentJamHouse extends StructureVillagePieces.House1{

    private static final int X_SIZE = 11;
    private static final int Y_SIZE = 8;
    private static final int Z_SIZE = 12;

    private int averageGroundLevel = -1;

    public VillageComponentJamHouse(){

    }

    public VillageComponentJamHouse(StructureBoundingBox boundingBox, EnumFacing par5){
        this.setCoordBaseMode(par5);
        this.boundingBox = boundingBox;
    }

    public static VillageComponentJamHouse buildComponent(List pieces, int p1, int p2, int p3, EnumFacing p4){
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, X_SIZE, Y_SIZE, Z_SIZE, p4);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(pieces, boundingBox) == null ? new VillageComponentJamHouse(boundingBox, p4) : null;
    }

    public static boolean generateCrate(World world, StructureBoundingBox box, int x, int y, int z, ResourceLocation loot){
        BlockPos pos = new BlockPos(x, y, z);

        if(box.isVecInside(pos)){
            world.setBlockState(pos, InitBlocks.blockGiantChest.getDefaultState(), 2);

            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileEntityGiantChest){
                ((TileEntityGiantChest)tile).lootTable = loot;
            }

            return true;
        }
        else{
            return false;
        }
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
                this.replaceAirAndLiquidDownwards(world, Blocks.COBBLESTONE.getDefaultState(), i, -1, j, sbb);
            }
        }

        this.spawnVillagers(world, sbb, 3, 1, 3, 1);

        return true;
    }

    public void fillWithBlocks(World world, StructureBoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block){
        this.fillWithBlocks(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, block.getDefaultState(), block.getDefaultState(), false);
    }

    public void spawnActualHouse(World world, Random rand, StructureBoundingBox sbb){
        //Base
        this.fillWithBlocks(world, sbb, 1, 0, 8, 9, 0, 10, Blocks.GRASS);
        this.fillWithBlocks(world, sbb, 0, 0, 0, 1, 0, 7, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 2, 0, 0, 4, 0, 1, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 9, 0, 0, 10, 0, 7, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 6, 0, 0, 8, 0, 1, Blocks.COBBLESTONE);
        this.setBlockState(world, Blocks.STONE_STAIRS.getStateFromMeta(3), 5, 0, 0, sbb);
        this.fillWithBlocks(world, sbb, 2, 0, 7, 3, 0, 7, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 5, 0, 7, 8, 0, 7, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 10, 0, 8, 10, 0, 11, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 0, 0, 8, 0, 0, 11, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 0, 0, 11, 10, 0, 11, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 2, 0, 2, 8, 0, 6, Blocks.PLANKS.getStateFromMeta(1), Blocks.PLANKS.getStateFromMeta(1), false);
        this.setBlockState(world, Blocks.PLANKS.getStateFromMeta(1), 5, 0, 1, sbb);
        this.setBlockState(world, Blocks.PLANKS.getStateFromMeta(1), 4, 0, 7, sbb);

        //Garden FENCE
        this.fillWithBlocks(world, sbb, 0, 1, 8, 0, 1, 11, Blocks.OAK_FENCE);
        this.fillWithBlocks(world, sbb, 10, 1, 8, 10, 1, 11, Blocks.OAK_FENCE);
        this.fillWithBlocks(world, sbb, 1, 1, 11, 9, 1, 11, Blocks.OAK_FENCE);

        //Side Walls
        for(int i = 0; i < 2; i++){
            this.fillWithBlocks(world, sbb, 1+i*8, 1, 1, 1+i*8, 1, 7, Blocks.COBBLESTONE);
            this.fillWithBlocks(world, sbb, 1+i*8, 1, 1, 1+i*8, 4, 1, Blocks.COBBLESTONE);
            this.fillWithBlocks(world, sbb, 1+i*8, 1, 7, 1+i*8, 4, 7, Blocks.COBBLESTONE);
            this.fillWithBlocks(world, sbb, 1+i*8, 4, 2, 1+i*8, 5, 6, Blocks.COBBLESTONE);
            this.fillWithBlocks(world, sbb, 1+i*8, 3, 2, 1+i*8, 3, 6, Blocks.PLANKS);
            this.setBlockState(world, Blocks.PLANKS.getStateFromMeta(0), 1+i*8, 2, 2, sbb);
            this.setBlockState(world, Blocks.PLANKS.getStateFromMeta(0), 1+i*8, 2, 6, sbb);
            this.fillWithBlocks(world, sbb, 1+i*8, 2, 3, 1+i*8, 2, 5, Blocks.GLASS_PANE);
        }

        //Front Wall
        this.fillWithBlocks(world, sbb, 7, 1, 1, 8, 4, 1, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 2, 1, 1, 3, 4, 1, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 4, 4, 1, 7, 4, 1, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 6, 1, 1, 6, 3, 1, Blocks.PLANKS);
        this.fillWithBlocks(world, sbb, 4, 1, 1, 4, 3, 1, Blocks.PLANKS);
        this.fillWithBlocks(world, sbb, 5, 3, 1, 5, 3, 1, Blocks.PLANKS);
        this.setBlockState(world, Blocks.SPRUCE_DOOR.getDefaultState(), 5, 1, 1, sbb);
        this.setBlockState(world, Blocks.SPRUCE_DOOR.getDefaultState().withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 5, 2, 1, sbb);


        //Back Wall
        this.fillWithBlocks(world, sbb, 2, 1, 7, 2, 4, 7, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 3, 1, 7, 3, 3, 7, Blocks.PLANKS);
        this.fillWithBlocks(world, sbb, 4, 3, 7, 8, 3, 7, Blocks.PLANKS);
        this.fillWithBlocks(world, sbb, 3, 4, 7, 8, 4, 7, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 5, 1, 7, 5, 2, 7, Blocks.PLANKS);
        this.fillWithBlocks(world, sbb, 6, 1, 7, 8, 1, 7, Blocks.COBBLESTONE);
        this.fillWithBlocks(world, sbb, 6, 2, 7, 7, 2, 7, Blocks.GLASS_PANE);
        this.setBlockState(world, Blocks.PLANKS.getStateFromMeta(0), 8, 2, 7, sbb);
        this.setBlockState(world, Blocks.SPRUCE_DOOR.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.SOUTH), 4, 1, 7, sbb);
        this.setBlockState(world, Blocks.SPRUCE_DOOR.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.SOUTH).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 4, 2, 7, sbb);

        //FENCE Supports
        this.fillWithBlocks(world, sbb, 0, 1, 8, 0, 3, 8, Blocks.OAK_FENCE);
        this.fillWithBlocks(world, sbb, 10, 1, 8, 10, 3, 8, Blocks.OAK_FENCE);
        this.fillWithBlocks(world, sbb, 0, 1, 0, 0, 3, 0, Blocks.OAK_FENCE);
        this.fillWithBlocks(world, sbb, 10, 1, 0, 10, 3, 0, Blocks.OAK_FENCE);

        //Roof
        this.fillWithBlocks(world, sbb, 1, 6, 3, 9, 6, 5, Blocks.PLANKS);
        IBlockState stairSouth = Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH);
        IBlockState stairNorth = Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
        this.fillWithBlocks(world, sbb, 0, 4, 0, 10, 4, 0, stairNorth, stairNorth, false);
        this.fillWithBlocks(world, sbb, 0, 5, 1, 10, 5, 1, stairNorth, stairNorth, false);
        this.fillWithBlocks(world, sbb, 0, 6, 2, 10, 6, 2, stairNorth, stairNorth, false);
        this.fillWithBlocks(world, sbb, 0, 4, 8, 10, 4, 8, stairSouth, stairSouth, false);
        this.fillWithBlocks(world, sbb, 0, 5, 7, 10, 5, 7, stairSouth, stairSouth, false);
        this.fillWithBlocks(world, sbb, 0, 6, 6, 10, 6, 6, stairSouth, stairSouth, false);
        this.fillWithBlocks(world, sbb, 0, 7, 3, 10, 7, 5, Blocks.WOODEN_SLAB);

        //Roof Gadgets
        this.setBlockState(world, Blocks.PLANKS.getDefaultState(), 0, 4, 1, sbb);
        this.setBlockState(world, Blocks.PLANKS.getDefaultState(), 0, 5, 2, sbb);
        this.setBlockState(world, Blocks.PLANKS.getDefaultState(), 10, 4, 1, sbb);
        this.setBlockState(world, Blocks.PLANKS.getDefaultState(), 10, 5, 2, sbb);
        this.setBlockState(world, Blocks.PLANKS.getDefaultState(), 0, 4, 7, sbb);
        this.setBlockState(world, Blocks.PLANKS.getDefaultState(), 0, 5, 6, sbb);
        this.setBlockState(world, Blocks.PLANKS.getDefaultState(), 10, 4, 7, sbb);
        this.setBlockState(world, Blocks.PLANKS.getDefaultState(), 10, 5, 6, sbb);
        this.fillWithBlocks(world, sbb, 0, 6, 3, 0, 6, 5, Blocks.PLANKS);
        this.fillWithBlocks(world, sbb, 10, 6, 3, 10, 6, 5, Blocks.PLANKS);

        //Counter
        this.fillWithBlocks(world, sbb, 6, 3, 2, 6, 3, 4, Blocks.WOODEN_SLAB.getStateFromMeta(8), Blocks.WOODEN_SLAB.getStateFromMeta(8), false);
        this.fillWithBlocks(world, sbb, 5, 3, 4, 5, 3, 6, Blocks.WOODEN_SLAB.getStateFromMeta(8), Blocks.WOODEN_SLAB.getStateFromMeta(8), false);
        this.fillWithBlocks(world, sbb, 6, 1, 2, 6, 1, 4, Blocks.PLANKS);
        this.fillWithBlocks(world, sbb, 5, 1, 4, 5, 1, 5, Blocks.PLANKS);
        this.fillWithBlocks(world, sbb, 6, 4, 2, 6, 5, 2, Blocks.OAK_FENCE);
        this.fillWithBlocks(world, sbb, 5, 4, 4, 5, 5, 4, Blocks.OAK_FENCE);
        this.fillWithBlocks(world, sbb, 5, 4, 6, 5, 5, 6, Blocks.OAK_FENCE);

        //Decoration
        this.setBlockState(world, Blocks.OAK_FENCE.getDefaultState(), 2, 1, 2, sbb);
        this.setBlockState(world, Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, false), 2, 2, 2, sbb);
        this.setBlockState(world, Blocks.OAK_FENCE.getDefaultState(), 8, 1, 2, sbb);
        this.setBlockState(world, Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, false), 8, 2, 2, sbb);
        this.setBlockState(world, Blocks.OAK_FENCE.getDefaultState(), 2, 1, 6, sbb);
        this.setBlockState(world, Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, false), 2, 2, 6, sbb);
        IBlockState stairWest = Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST);
        this.fillWithBlocks(world, sbb, 2, 1, 3, 2, 1, 5, stairWest, stairWest, false);
        this.fillWithBlocks(world, sbb, 3, 1, 2, 5, 1, 3, Blocks.CARPET.getStateFromMeta(10), Blocks.CARPET.getStateFromMeta(10), false);
        this.fillWithBlocks(world, sbb, 3, 1, 4, 4, 1, 6, Blocks.CARPET.getStateFromMeta(10), Blocks.CARPET.getStateFromMeta(10), false);

        //Loot Chest
        if(ConfigBoolValues.DUNGEON_LOOT.isEnabled()){
            generateCrate(world, this.boundingBox, this.getXWithOffset(8, 6), this.getYWithOffset(1), this.getZWithOffset(8, 6), DungeonLoot.JAM_HOUSE);
        }

        //Torches
        this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.SOUTH), 6, 2, 0, sbb);
        this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.SOUTH), 4, 2, 0, sbb);
        this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.NORTH), 5, 2, 8, sbb);
        this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.NORTH), 3, 2, 8, sbb);
        this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.EAST), 2, 3, 2, sbb);
        this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.EAST), 2, 3, 6, sbb);
        this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.WEST), 8, 3, 2, sbb);
        this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.WEST), 8, 3, 6, sbb);
    }

    @Override
    protected VillagerProfession chooseForgeProfession(int count, VillagerProfession prof){
        return InitVillager.jamProfession;
    }
}
