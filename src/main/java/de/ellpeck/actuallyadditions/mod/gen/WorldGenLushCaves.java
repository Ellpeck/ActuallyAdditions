/*
 * This file ("WorldGenLushCaves.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.misc.DungeonLoot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.loot.ILootContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WorldGenLushCaves{

    public boolean generate(World world, Random rand, BlockPos position, StructureBoundingBox blockRegion){
        this.generateCave(world, position, rand, blockRegion);
        return true;
    }

    private void generateCave(World world, BlockPos center, Random rand, StructureBoundingBox chunkRegion){
        int spheres = rand.nextInt(5)+3;
        StructureBoundingBox spheresBox = new StructureBoundingBox(chunkRegion);
        //the region for spheres is larger so that trees can generate in the smaller one
        spheresBox.minX -= 7;
        spheresBox.minZ -= 7;
        spheresBox.maxX += 7;
        spheresBox.maxZ += 7;
        for(int i = 0; i <= spheres; i++){
            //center already is random value within population area
            this.makeSphereWithGrassFloor(world, center.add(rand.nextInt(11)-5, rand.nextInt(7)-3, rand.nextInt(11)-5), rand.nextInt(3)+5, spheresBox);
        }

        this.genTreesAndTallGrass(world, center, 11, spheres*3, rand, chunkRegion);
    }

    private void genTreesAndTallGrass(World world, BlockPos center, int radius, int amount, Random rand, StructureBoundingBox box){
        List<BlockPos> possiblePoses = new ArrayList<BlockPos>();
        for(double x = -radius; x < radius; x++){
            for(double y = -radius; y < radius; y++){
                for(double z = -radius; z < radius; z++){
                    if(rand.nextDouble() >= 0.5D){
                        BlockPos pos = center.add(x, y, z);
                        if(box.isVecInside(pos) && world.getBlockState(pos).getBlock() == Blocks.GRASS){
                            possiblePoses.add(pos);
                        }
                    }
                }
            }
        }

        if(!possiblePoses.isEmpty()){
            boolean chestGenDone = false;

            for(int i = 0; i <= amount; i++){
                Collections.shuffle(possiblePoses);
                BlockPos pos = possiblePoses.get(0);
                if(rand.nextBoolean()){
                    boolean genChest = false;

                    WorldGenAbstractTree trees;
                    if(rand.nextBoolean()){
                        if(rand.nextBoolean()){
                            trees = new WorldGenBigTree(false);
                        }
                        else{
                            trees = new WorldGenShrub(Blocks.LOG.getDefaultState(), Blocks.LEAVES.getDefaultState());
                            genChest = true;
                        }
                    }
                    else{
                        trees = new WorldGenTrees(false);
                    }
                    trees.generate(world, rand, pos.up());

                    if(!chestGenDone && genChest){
                        BlockPos chestPos = pos.add(MathHelper.getRandomIntegerInRange(rand, -2, 2), MathHelper.getRandomIntegerInRange(rand, 3, 8), MathHelper.getRandomIntegerInRange(rand, -2, 2));

                        IBlockState state = world.getBlockState(chestPos);
                        if(state != null && state.getBlock().isLeaves(state, world, chestPos)){
                            world.setBlockState(chestPos, Blocks.CHEST.getDefaultState());

                            TileEntity chest = world.getTileEntity(chestPos);
                            if(chest instanceof TileEntityChest){
                                ((TileEntityChest)chest).setLootTable(DungeonLoot.LUSH_CAVES, rand.nextLong());
                            }
                        }

                        chestGenDone = true;
                    }
                }
                else{
                    Blocks.GRASS.grow(world, rand, pos, world.getBlockState(pos));
                }
            }
        }
    }

    private void makeSphereWithGrassFloor(World world, BlockPos center, int radius, StructureBoundingBox boundingBox){
        for(double x = -radius; x < radius; x++){
            for(double y = -radius; y < radius; y++){
                for(double z = -radius; z < radius; z++){
                    if(Math.sqrt((x*x)+(y*y)+(z*z)) < radius){
                        BlockPos pos = center.add(x, y, z);
                        //Note: order matters, checkIndestructable will generate chunks if order is reversed
                        if(boundingBox.isVecInside(pos) && !this.checkIndestructable(world, pos)){
                            world.setBlockToAir(pos);
                        }
                    }
                }
            }
        }

        for(double x = -radius; x < radius; x++){
            for(double z = -radius; z < radius; z++){
                for(double y = -radius; y <= -3; y++){
                    BlockPos pos = center.add(x, y, z);
                    if(boundingBox.isVecInside(pos)){
                        IBlockState state = world.getBlockState(pos);
                        BlockPos posUp = pos.up();
                        IBlockState stateUp = world.getBlockState(posUp);
                        if(!this.checkIndestructable(world, pos) && !this.checkIndestructable(world, posUp)){
                            if(!state.getBlock().isAir(state, world, pos) && stateUp.getBlock().isAir(stateUp, world, posUp)){
                                world.setBlockState(pos, Blocks.GRASS.getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkIndestructable(World world, BlockPos pos){
        //If this isn't checked, the game crashes because it tries to destroy a chest that doesn't have any loot yet :v
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof ILootContainer){
            return true;
        }

        IBlockState state = world.getBlockState(pos);
        if(state != null){
            Block block = state.getBlock();
            //check if it's tree or grass that is generated here
            if(block == Blocks.LOG || block == Blocks.LEAVES || block == Blocks.TALLGRASS){
                return true;
            }
            if(block != null && (block.isAir(state, world, pos) || block.getHarvestLevel(state) >= 0F)){
                return false;
            }
        }
        return true;
    }
}