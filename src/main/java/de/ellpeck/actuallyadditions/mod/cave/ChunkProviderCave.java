/*
 * This file ("ChunkProviderCaves.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.cave;

import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChunkProviderCave implements IChunkGenerator{

    private final World world;
    private final Random rand;

    public ChunkProviderCave(World world){
        this.world = world;
        this.rand = new Random(world.getSeed());
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ){
        ChunkPrimer primer = new ChunkPrimer();

        int height = this.world.getHeight();
        for(int y = 0; y < height; y++){
            boolean isTopOrBottom = y == 0 || y == height-1;

            for(int x = 0; x < 16; ++x){
                for(int z = 0; z < 16; ++z){
                    if(isTopOrBottom){
                        primer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
                    }
                    else{
                        IBlockState state;

                        int rand = this.rand.nextInt(485);
                        if(rand <= 250){
                            state = Blocks.STONE.getDefaultState();
                        }
                        else if(rand <= 350){
                            state = Blocks.COBBLESTONE.getDefaultState();
                        }
                        else if(rand <= 360){
                            state = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, EnumType.ANDESITE);
                        }
                        else if(rand <= 370){
                            state = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, EnumType.DIORITE);
                        }
                        else if(rand <= 380){
                            state = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, EnumType.GRANITE);
                        }
                        else if(rand <= 430){
                            state = Blocks.DIRT.getDefaultState();
                        }
                        else if(rand <= 450){
                            state = Blocks.CLAY.getDefaultState();
                        }
                        else if(rand <= 480){
                            state = Blocks.GRAVEL.getDefaultState();
                        }
                        else{
                            state = Blocks.MOSSY_COBBLESTONE.getDefaultState();
                        }

                        primer.setBlockState(x, y, z, state);
                    }
                }
            }
        }

        Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);

        byte[] biomes = chunk.getBiomeArray();
        Arrays.fill(biomes, (byte)Biome.getIdForBiome(Biomes.FOREST));

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z){

    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z){
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos){
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Override
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean bool){
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z){

    }
}
