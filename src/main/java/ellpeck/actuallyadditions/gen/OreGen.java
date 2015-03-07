package ellpeck.actuallyadditions.gen;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

@SuppressWarnings("unused")
public class OreGen implements IWorldGenerator{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
        switch (world.provider.dimensionId){
            case -1:
                generateNether(world, random, chunkX*16, chunkZ*16);
            case 0:
                generateSurface(world, random, chunkX*16, chunkZ*16);
            case 1:
                generateEnd(world, random, chunkX*16, chunkZ*16);
        }
    }

    @SuppressWarnings("unused")
    private void generateEnd(World world, Random random, int x, int z){

    }

    private void generateSurface(World world, Random random, int x, int z){

    }

    @SuppressWarnings("unused")
    private void generateNether(World world, Random random, int x, int z){

    }

    public void addOreSpawn(Block block, int meta, int x, int y, int z, Block blockIn, World world, Random random, int blockXPos, int blockZPos, int maxVeinSize, int chancesToSpawn, int minY, int maxY){
        int yDiff = maxY - minY;
        for(int i = 0; i < chancesToSpawn; i++){
            int posX = blockXPos + random.nextInt(16);
            int posY = minY + random.nextInt(yDiff);
            int posZ = blockZPos + random.nextInt(16);
            (new WorldGenMinable(block, meta, maxVeinSize, blockIn)).generate(world, random, posX, posY, posZ);
        }
    }

    public static void init(){
        Util.logInfo("Registering World Generator...");
        GameRegistry.registerWorldGenerator(new OreGen(), 0);
    }
}
