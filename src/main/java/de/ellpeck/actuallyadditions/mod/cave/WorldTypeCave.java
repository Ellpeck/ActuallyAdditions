/*
 * This file ("WorldTypeCave.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.cave;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;

import java.util.Random;

public class WorldTypeCave extends WorldType{

    public WorldTypeCave(){
        super("actaddcaves");
    }

    @Override
    public float getCloudHeight(){
        return 1024F;
    }

    @Override
    public boolean showWorldInfoNotice(){
        return true;
    }

    @Override
    public boolean handleSlimeSpawnReduction(Random random, World world){
        return true;
    }

    @Override
    public int getSpawnFuzz(WorldServer world, MinecraftServer server){
        return 1;
    }

    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions){
        return new ChunkProviderCave(world);
    }

    public static boolean is(World world){
        return ActuallyAdditions.isCaveMode && world.getWorldType() instanceof WorldTypeCave;
    }
}
