/*
 * This file ("OreGen.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class OreGen implements IWorldGenerator{

    public static final int QUARTZ_MIN = 0;
    public static final int QUARTZ_MAX = 45;

    public static void init(){
        ModUtil.LOGGER.info("Registering World Generator...");
        GameRegistry.registerWorldGenerator(new OreGen(), 10);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
        if(world.getWorldType() != WorldType.FLAT && Util.arrayContains(ConfigValues.oreGenDimensionBlacklist, world.provider.getDimensionId()) < 0){
            switch(world.provider.getDimensionId()){
                case -1:
                    generateNether(world, random, chunkX*16, chunkZ*16);
                    //case 0:
                    //   generateSurface(world, random, chunkX*16, chunkZ*16);
                case 1:
                    generateEnd(world, random, chunkX*16, chunkZ*16);
                default:
                    generateSurface(world, random, chunkX*16, chunkZ*16);
            }
        }
    }

    @SuppressWarnings("unused")
    private void generateNether(World world, Random random, int x, int z){

    }

    @SuppressWarnings("unused")
    private void generateEnd(World world, Random random, int x, int z){

    }

    private void generateSurface(World world, Random random, int x, int z){
        if(ConfigBoolValues.GENERATE_QUARTZ.isEnabled()){
            this.addOreSpawn(InitBlocks.blockMisc, TheMiscBlocks.ORE_QUARTZ.ordinal(), Blocks.stone, world, random, x, z, MathHelper.getRandomIntegerInRange(random, 5, 8), 10, QUARTZ_MIN, QUARTZ_MAX);
        }
    }

    public void addOreSpawn(Block block, int meta, Block blockIn, World world, Random random, int blockXPos, int blockZPos, int maxVeinSize, int chancesToSpawn, int minY, int maxY){
        if(maxY > minY){
            int yDiff = maxY-minY;
            for(int i = 0; i < chancesToSpawn; i++){
                int posX = blockXPos+random.nextInt(16);
                int posY = minY+random.nextInt(yDiff);
                int posZ = blockZPos+random.nextInt(16);
                new WorldGenMinable(block.getStateFromMeta(meta), maxVeinSize, BlockHelper.forBlock(blockIn)).generate(world, random, new BlockPos(posX, posY, posZ));
            }
        }
        else{
            ModUtil.LOGGER.fatal("Couldn't generate '"+block.getUnlocalizedName()+"' into the world because the Min Y coordinate is bigger than the Max! This is definitely a Config Error! Check the Files!");
        }
    }
}
