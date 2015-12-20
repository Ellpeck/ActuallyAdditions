/*
 * This file ("WorldTypeActAddCaves.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;

public class WorldTypeActAddCaves extends WorldType{

    public WorldTypeActAddCaves(){
        super("ActAddCaves");
    }

    public static boolean isActAddCave(World world){
        return world.provider.terrainType instanceof WorldTypeActAddCaves;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslateName(){
        return ModUtil.MOD_ID_LOWER+".generator.caves";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean showWorldInfoNotice(){
        return true;
    }

    @Override
    public float getCloudHeight(){
        return 256F;
    }

    @Override
    public int getSpawnFuzz(){
        return 1;
    }

    @Override
    public int getMinimumSpawnHeight(World world){
        return 20;
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions){
        return new ChunkProviderFlat(world, world.getSeed(), false, "7,253x1,7");
    }
}
