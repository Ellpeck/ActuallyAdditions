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

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.storage.loot.ILootContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WorldGenLushCaves extends WorldGenerator{

    @Override
    public boolean generate(World world, Random rand, BlockPos position){
        this.generateCave(world, position, rand);
        return true;
    }

    private void generateCave(World world, BlockPos center, Random rand){
        int spheres = rand.nextInt(5)+3;
        for(int i = 0; i <= spheres; i++){
            this.makeSphereWithGrassFloor(world, center.add(rand.nextInt(11)-5, rand.nextInt(7)-3, rand.nextInt(11)-5), rand.nextInt(3)+5);
        }

        this.genTreesAndTallGrass(world, center, 10, spheres*3, rand);
    }

    private void genTreesAndTallGrass(World world, BlockPos center, int radius, int amount, Random rand){
        List<BlockPos> possiblePoses = new ArrayList<BlockPos>();
        for(double x = -radius; x < radius; x++){
            for(double y = -radius; y < radius; y++){
                for(double z = -radius; z < radius; z++){
                    if(rand.nextDouble() >= 0.5D){
                        BlockPos pos = center.add(x, y, z);
                        if(world.getBlockState(pos).getBlock() == Blocks.GRASS){
                            possiblePoses.add(pos);
                        }
                    }
                }
            }
        }

        if(!possiblePoses.isEmpty()){
            for(int i = 0; i <= amount; i++){
                Collections.shuffle(possiblePoses);
                BlockPos pos = possiblePoses.get(0);
                if(rand.nextBoolean()){
                    WorldGenAbstractTree trees = rand.nextBoolean() ? (rand.nextBoolean() ? new WorldGenBigTree(false) : new WorldGenShrub(Blocks.LOG.getDefaultState(), Blocks.LEAVES.getDefaultState())) : new WorldGenTrees(false);
                    trees.generate(world, rand, pos.up());
                }
                else{
                    Blocks.GRASS.grow(world, rand, pos, world.getBlockState(pos));
                }
            }
        }
    }

    private void makeSphereWithGrassFloor(World world, BlockPos center, int radius){
        for(double x = -radius; x < radius; x++){
            for(double y = -radius; y < radius; y++){
                for(double z = -radius; z < radius; z++){
                    if(Math.sqrt((x*x)+(y*y)+(z*z)) < radius){
                        BlockPos pos = center.add(x, y, z);
                        if(!this.checkIndestructable(world, pos)){
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

    private boolean checkIndestructable(World world, BlockPos pos){
        IBlockState state = world.getBlockState(pos);
        if(state != null){
            Block block = state.getBlock();
            if(block != null && (block.isAir(state, world, pos) || block.getHarvestLevel(state) >= 0F)){
                return false;
            }

            //If this isn't checked, the game crashes because it tries to destroy a chest that doesn't have any loot yet :v
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof ILootContainer){
                return false;
            }
        }
        return true;
    }
}