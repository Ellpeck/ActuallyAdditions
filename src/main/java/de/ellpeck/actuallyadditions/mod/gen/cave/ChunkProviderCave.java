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

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ChunkProviderCave implements IChunkGenerator{

    private final World world;
    private final Random rand;

    private final WorldGenerator spawnGenerator;
    private final WorldGenerator lushCaveGenerator;

    private final MapGenBase caveGenerator = new MapGenCustomCaves();

    public ChunkProviderCave(World world){
        this.world = world;
        this.rand = new Random(world.getSeed());
        this.lushCaveGenerator = new WorldGenLushCaves(this.rand);
        this.spawnGenerator = new WorldGenCaveSpawn(this.rand);
    }

    @Nonnull
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
                        if(this.rand.nextInt(5) <= 0){
                            if(this.rand.nextFloat() <= 0.95F){
                                primer.setBlockState(x, y, z, (this.rand.nextFloat() >= 0.85F ? Blocks.MOSSY_COBBLESTONE : Blocks.COBBLESTONE).getDefaultState());
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

        this.caveGenerator.generate(this.world, chunkX, chunkZ, primer);

        Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z){
        BlockPos spawn = this.world.getSpawnPoint();
        Chunk chunk = this.world.getChunkFromBlockCoords(spawn);
        if(chunk.xPosition == x && chunk.zPosition == z){
            this.spawnGenerator.generate(this.world, this.rand, spawn);
            ModUtil.LOGGER.info("Generating spawn cave...");
        }
        else{
            if(this.rand.nextInt(3) <= 0){
                BlockPos pos = new BlockPos(x*16+this.rand.nextInt(16)+8, MathHelper.getRandomIntegerInRange(this.rand, 12, 244), z*16+this.rand.nextInt(16)+8);
                this.lushCaveGenerator.generate(this.world, this.rand, pos);
            }
        }
    }

    @Override
    public boolean generateStructures(@Nonnull Chunk chunkIn, int x, int z){
        return false;
    }

    @Nonnull
    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(@Nonnull EnumCreatureType creatureType, @Nonnull BlockPos pos){
        return Collections.emptyList();
    }

    @Override
    public BlockPos getStrongholdGen(@Nonnull World worldIn, @Nonnull String structureName, @Nonnull BlockPos position){
        return null;
    }

    @Override
    public void recreateStructures(@Nonnull Chunk chunkIn, int x, int z){

    }
}
