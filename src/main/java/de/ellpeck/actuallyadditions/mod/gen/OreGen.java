/*
 * This file ("OreGen.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheWildPlants;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntListValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Random;

public class OreGen implements IWorldGenerator{

    public static final int QUARTZ_MIN = 0;
    public static final int QUARTZ_MAX = 45;

    private final WorldGenLushCaves caveGen = new WorldGenLushCaves();

    public OreGen(){
        ModUtil.LOGGER.info("Registering World Generator...");
        GameRegistry.registerWorldGenerator(this, 10);
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
        int dimension = world.provider.getDimension();
        if(dimension != -1 && dimension != 1){
            if(world.getWorldType() != WorldType.FLAT && !ArrayUtils.contains(ConfigIntListValues.ORE_GEN_DIMENSION_BLACKLIST.getValue(), world.provider.getDimension())){
                this.generateDefault(world, random, chunkX, chunkZ);
            }
        }
    }

    private void generateDefault(World world, Random random, int x, int z){
        if(ConfigBoolValues.GENERATE_QUARTZ.isEnabled()){
            this.addOreSpawn(InitBlocks.blockMisc, TheMiscBlocks.ORE_QUARTZ.ordinal(), Blocks.STONE, world, random, x*16, z*16, MathHelper.getInt(random, 5, 8), 10, QUARTZ_MIN, QUARTZ_MAX);
        }

        if(ConfigBoolValues.GEN_LUSH_CAVES.isEnabled()){
            StructureBoundingBox box = new StructureBoundingBox(x*16+8, 0, z*16+8, x*16+8+15, 255, z*16+8+15);
            int chunkRadius = 1;
            for(int dx = -chunkRadius; dx <= chunkRadius; dx++){
                for(int dz = -chunkRadius; dz <= chunkRadius; dz++){
                    int chunkX = x+dx;
                    int chunkZ = z+dz;
                    int randConst = 0x969ce69d;//so that it won't generate the same numbers as other mod that does the same thing
                    Random chunkRand = new Random(randConst ^ world.getSeed() ^ (chunkX*29+chunkZ*31));
                    if(chunkRand.nextInt(ConfigIntValues.LUSH_CAVE_CHANCE.getValue()) <= 0){
                        BlockPos randPos = world.getTopSolidOrLiquidBlock(new BlockPos(chunkX*16+chunkRand.nextInt(16)+8, 0, chunkZ*16+chunkRand.nextInt(16)+8));
                        BlockPos pos = randPos.down(MathHelper.getInt(chunkRand, 15, randPos.getY()-15));
                        this.caveGen.generate(world, chunkRand, pos, box);
                    }
                }
            }
        }
    }

    public void addOreSpawn(Block block, int meta, Block blockIn, World world, Random random, int blockXPos, int blockZPos, int maxVeinSize, int chancesToSpawn, int minY, int maxY){
        for(int i = 0; i < chancesToSpawn; i++){
            int posX = blockXPos+random.nextInt(16);
            int posY = minY+random.nextInt(maxY-minY);
            int posZ = blockZPos+random.nextInt(16);
            new WorldGenMinable(block.getStateFromMeta(meta), maxVeinSize, BlockMatcher.forBlock(blockIn)).generate(world, random, new BlockPos(posX, posY, posZ));
        }
    }

    @SubscribeEvent
    public void onWorldDecoration(DecorateBiomeEvent.Decorate event){
        if((event.getResult() == Event.Result.ALLOW || event.getResult() == Event.Result.DEFAULT)){
            if(!ArrayUtils.contains(ConfigIntListValues.PLANT_DIMENSION_BLACKLIST.getValue(), event.getWorld().provider.getDimension())){
                this.generateRice(event);
                this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.CANOLA.ordinal(), ConfigIntValues.CANOLA_AMOUNT.getValue(), ConfigBoolValues.DO_CANOLA_GEN.isEnabled(), Material.GRASS, event);
                this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.FLAX.ordinal(), ConfigIntValues.FLAX_AMOUNT.getValue(), ConfigBoolValues.DO_FLAX_GEN.isEnabled(), Material.GRASS, event);
                this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.COFFEE.ordinal(), ConfigIntValues.COFFEE_AMOUNT.getValue(), ConfigBoolValues.DO_COFFEE_GEN.isEnabled(), Material.GRASS, event);
                this.genPlantNormally(InitBlocks.blockBlackLotus, 0, ConfigIntValues.BLACK_LOTUS_AMOUNT.getValue(), ConfigBoolValues.DO_LOTUS_GEN.isEnabled(), Material.GRASS, event);
            }

            //Generate Treasure Chests
            if(ConfigBoolValues.DO_TREASURE_CHEST_GEN.isEnabled()){
                if(event.getRand().nextInt(300) == 0){
                    BlockPos randomPos = new BlockPos(event.getPos().getX()+event.getRand().nextInt(16)+8, 0, event.getPos().getZ()+event.getRand().nextInt(16)+8);
                    randomPos = event.getWorld().getTopSolidOrLiquidBlock(randomPos);

                    if(event.getWorld().getBiome(randomPos) instanceof BiomeOcean){
                        if(randomPos.getY() >= 25 && randomPos.getY() <= 45){
                            if(event.getWorld().getBlockState(randomPos).getMaterial() == Material.WATER){
                                if(event.getWorld().getBlockState(randomPos.down()).getMaterial().isSolid()){
                                    event.getWorld().setBlockState(randomPos, InitBlocks.blockTreasureChest.getStateFromMeta(event.getRand().nextInt(4)), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateRice(DecorateBiomeEvent event){
        if(ConfigBoolValues.DO_RICE_GEN.isEnabled()){
            for(int i = 0; i < ConfigIntValues.RICE_AMOUNT.getValue(); i++){
                if(event.getRand().nextInt(50) == 0){
                    BlockPos randomPos = new BlockPos(event.getPos().getX()+event.getRand().nextInt(16)+8, 0, event.getPos().getZ()+event.getRand().nextInt(16)+8);
                    randomPos = event.getWorld().getTopSolidOrLiquidBlock(randomPos);
                    if(event.getWorld().getBlockState(randomPos).getMaterial() == Material.WATER){
                        ArrayList<Material> blocksAroundBottom = WorldUtil.getMaterialsAround(event.getWorld(), randomPos);
                        BlockPos posToGenAt = randomPos.up();
                        ArrayList<Material> blocksAroundTop = WorldUtil.getMaterialsAround(event.getWorld(), posToGenAt);
                        if(blocksAroundBottom.contains(Material.GRASS) || blocksAroundBottom.contains(Material.GROUND) || blocksAroundBottom.contains(Material.ROCK) || blocksAroundBottom.contains(Material.SAND)){
                            if(!blocksAroundTop.contains(Material.WATER) && event.getWorld().getBlockState(posToGenAt).getMaterial() == Material.AIR){
                                event.getWorld().setBlockState(posToGenAt, InitBlocks.blockWildPlant.getStateFromMeta(TheWildPlants.RICE.ordinal()), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void genPlantNormally(Block plant, int meta, int amount, boolean doIt, Material blockBelow, DecorateBiomeEvent event){
        if(doIt){
            for(int i = 0; i < amount; i++){
                if(event.getRand().nextInt(400) == 0){
                    BlockPos randomPos = new BlockPos(event.getPos().getX()+event.getRand().nextInt(16)+8, 0, event.getPos().getZ()+event.getRand().nextInt(16)+8);
                    randomPos = event.getWorld().getTopSolidOrLiquidBlock(randomPos);

                    if(event.getWorld().getBlockState(randomPos.down()).getMaterial() == blockBelow){
                        if(plant.canPlaceBlockAt(event.getWorld(), randomPos) && event.getWorld().isAirBlock(randomPos)){
                            event.getWorld().setBlockState(randomPos, plant.getStateFromMeta(meta), 2);
                        }
                    }
                }
            }
        }
    }
}
