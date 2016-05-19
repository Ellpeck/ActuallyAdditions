/*
 * This file ("WorldGeneratorCaveSpawn.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.cave;

import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import net.minecraft.block.BlockLadder;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class WorldGenCaveSpawn extends WorldGenerator{

    private final Random rand;

    public WorldGenCaveSpawn(Random rand){
        this.rand = rand;
    }

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos position){
        this.generateCave(world, position);
        return true;
    }

    private void generateCave(World world, BlockPos center){
        this.makeSphere(world, center, 8);
        this.makeSphere(world, center.add(-3, 4, 3), 4);
        this.makeSphere(world, center.add(4, 6, 1), 4);
        this.makeSphere(world, center.add(3, 4, -3), 6);
        this.makeSphere(world, center.add(4, -2, -3), 2);
        this.makeSphere(world, center.add(5, 0, -3), 4);
        this.makeSphere(world, center.add(1, 4, 3), 6);
        this.makeSphere(world, center.add(-5, 1, 1), 4);
        this.makeSphere(world, center.add(-1, 1, -7), 6);
        this.makeSphere(world, center.add(-2, -1, 8), 3);

        world.setBlockState(center.add(-1, -5, -8), Blocks.DIRT.getStateFromMeta(1));
        WorldGenTrees trees = new WorldGenTrees(true);
        trees.generate(world, this.rand, center.add(-1, -4, -8));

        int length = this.rand.nextInt(20)+20;
        for(int z = 0; z <= length; z++){
            for(int x = 0; x < 5; x++){
                for(int y = 0; y < 4; y++){
                    BlockPos pos = center.add(x-3, y-4, 11+z);

                    if(z%4 == 0 && (x == 0 || x == 4)){
                        world.setBlockState(pos, Blocks.LOG2.getStateFromMeta(1));
                    }
                    else if((z%4 == 0 || x == 0 || x == 4) && y == 3){
                        world.setBlockState(pos, Blocks.PLANKS.getStateFromMeta(1));
                    }
                    else if(!((y == 0 || y == 3) && this.rand.nextInt(5) <= 0)){
                        world.setBlockToAir(pos);
                    }
                }
            }
        }
        BlockPos chestPos = center.add(-1, -4, 11+length);
        world.setBlockState(chestPos, Blocks.CHEST.getDefaultState());
        TileEntity tile = world.getTileEntity(chestPos);
        if(tile instanceof TileEntityChest){
            TileEntityChest chest = (TileEntityChest)tile;
            chest.setInventorySlotContents(12, new ItemStack(InitItems.itemFoods, MathHelper.getRandomIntegerInRange(this.rand, 5, 15), this.rand.nextInt(TheFoods.values().length)));
            chest.setInventorySlotContents(14, new ItemStack(InitItems.itemAxeCrystalBlack));
        }

        for(int x = -2; x <= 2; x++){
            for(int z = -2; z <= 2; z++){
                for(int y = -7; y <= 0; y++){
                    if(x%2 == 0 && z%2 == 0 && x != 0 && z != 0){
                        world.setBlockState(center.add(x, y, z), Blocks.LOG.getDefaultState());
                    }
                    else if(y == 0 && (x == -2 || x == 2 || z == -2 || z == 2)){
                        world.setBlockState(center.add(x, y, z), Blocks.OAK_FENCE.getDefaultState());
                    }
                    else if(y == -1){
                        world.setBlockState(center.add(x, y, z), Blocks.PLANKS.getStateFromMeta(1));
                    }
                }
            }
        }

        for(int y = 3; y <= 12; y++){
            world.setBlockState(center.add(0, y, 0), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.WEST));
            world.setBlockState(center.add(1, y, 0), Blocks.PLANKS.getDefaultState());
        }
        world.setBlockState(center.add(0, 13, 0), Blocks.COBBLESTONE.getDefaultState());
    }

    private void makeSphere(World world, BlockPos center, int radius){
        for(double x = -radius; x < radius; x++){
            for(double y = -radius; y < radius; y++){
                for(double z = -radius; z < radius; z++){
                    if(Math.sqrt((x*x)+(y*y)+(z*z)) < radius){
                        world.setBlockToAir(center.add(x, y, z));
                    }
                }
            }
        }
    }
}
