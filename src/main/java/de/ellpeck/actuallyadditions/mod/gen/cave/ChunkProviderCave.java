/*
 * This file ("ChunkProviderCave.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.cave;

import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;

public class ChunkProviderCave implements IChunkGenerator{

    private boolean generatedSpawn;
    private World world;

    private WorldGenerator spawnGenerator = new WorldGenCaveSpawn();

    public ChunkProviderCave(World world){
        this.world = world;
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ){
        ChunkPrimer primer = new ChunkPrimer();

        for(int x = 0; x < 16; x++){
            for(int z = 0; z < 16; z++){
                for(int y = 0; y < this.world.getHeight(); y++){
                    if(y == this.world.getHeight()-1 || y == 0){
                        primer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
                    }
                    else{
                        if(Util.RANDOM.nextInt(5) <= 0){
                            if(Util.RANDOM.nextFloat() <= 0.95F){
                                primer.setBlockState(x, y, z, (Util.RANDOM.nextFloat() >= 0.85F ? Blocks.MOSSY_COBBLESTONE : Blocks.COBBLESTONE).getDefaultState());
                            }
                            else{
                                primer.setBlockState(x, y, z, Blocks.GLOWSTONE.getDefaultState());
                            }
                        }
                        else{
                            primer.setBlockState(x, y, z, Blocks.STONE.getDefaultState());
                        }
                    }
                }
            }
        }

        Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z){
        if(!this.generatedSpawn){
            BlockPos spawn = this.world.getSpawnPoint();
            Chunk chunk = this.world.getChunkFromBlockCoords(spawn);
            if(chunk.xPosition == x && chunk.zPosition == z){
                this.generatedSpawn = this.spawnGenerator.generate(this.world, this.world.rand, spawn);
            }
        }
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z){
        return false;
    }

    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos){
        return null;
    }

    @Override
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position){
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z){

    }
}
