/*
 * This file ("CaveWorldType.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.misc.WorldData;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.playerdata.PersistentServerData;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CaveWorldType extends WorldType{

    public CaveWorldType(){
        //Name can't be longer than 16 :'(
        super("actaddcaveworld");

        Util.registerEvent(this);
    }

    public static boolean isCave(World world){
        return ConfigValues.caveWorld && world.getWorldType() instanceof CaveWorldType;
    }

    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions){
        return new ChunkProviderFlat(world, world.getSeed(), false, "3;minecraft:bedrock,254*minecraft:stone,minecraft:bedrock;2;");
    }

    @Override
    public int getSpawnFuzz(WorldServer world, MinecraftServer server){
        return 1;
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
        trees.generate(world, Util.RANDOM, center.add(-1, -4, -8));

        for(int z = 0; z <= 24; z++){
            for(int x = 0; x < 5; x++){
                for(int y = 0; y < 4; y++){
                    BlockPos pos = center.add(x-3, y-4, 11+z);

                    if(z%4 == 0 && (x == 0 || x == 4)){
                        world.setBlockState(pos, Blocks.LOG2.getStateFromMeta(1));
                    }
                    else if((z%4 == 0 || x == 0 || x == 4) && y == 3){
                        world.setBlockState(pos, Blocks.PLANKS.getStateFromMeta(1));
                    }
                    else if(!((y == 0 || y == 3) && Util.RANDOM.nextInt(5) <= 0)){
                        world.setBlockToAir(pos);
                    }
                }
            }
        }

        world.setBlockState(center.down(1), Blocks.GLOWSTONE.getDefaultState());
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

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event){
        if(event.getEntity() != null){
            World world = event.getEntity().getEntityWorld();
            if(world != null && isCave(world) && !world.isRemote){
                BlockPos spawn = world.getSpawnPoint();
                BlockPos center = new BlockPos(spawn.getX(), 100, spawn.getZ());

                NBTTagCompound data = WorldData.additionalData;
                if(!data.getBoolean("GeneratedCave")){

                    ModUtil.LOGGER.info("Starting to generate cave world...");
                    this.generateCave(world, center);
                    ModUtil.LOGGER.info("Generating cave world completed!");

                    data.setBoolean("GeneratedCave", true);
                    WorldData.makeDirty();
                }

                if(event.getEntity() instanceof EntityPlayerMP){
                    EntityPlayerMP player = (EntityPlayerMP)event.getEntity();
                    if(player.posY >= world.getHeight()){
                        player.playerNetServerHandler.setPlayerLocation(center.getX()+0.5, center.getY()+1, center.getZ()+0.5, player.rotationYaw, player.rotationPitch);
                    }

                    NBTTagCompound playerData = PersistentServerData.getDataFromPlayer(player);
                    if(!playerData.getBoolean("SpawnedFirst")){
                        player.inventory.addItemStackToInventory(new ItemStack(InitItems.itemBooklet));

                        playerData.setBoolean("SpawnedFirst", true);
                        WorldData.makeDirty();
                    }
                }
            }
        }
    }
}
