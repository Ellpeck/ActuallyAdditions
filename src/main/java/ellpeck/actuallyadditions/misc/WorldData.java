/*
 * This file ("WorldData.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc;

import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class WorldData extends WorldSavedData{

    public static final String DATA_TAG = ModUtil.MOD_ID+"WorldData";

    public WorldData(){
        super(DATA_TAG);
    }

    public static WorldData instance;

    @Override
    public void readFromNBT(NBTTagCompound compound){

    }

    @Override
    public void writeToNBT(NBTTagCompound compound){

    }

    public static void makeDirty(){
        if(instance != null){
            instance.markDirty();
        }
    }

    public static void init(MinecraftServer server){
        if(server != null){
            World world = server.getEntityWorld();
            if(!world.isRemote){
                WorldSavedData savedData = world.loadItemData(WorldData.class, WorldData.DATA_TAG);
                //Generate new SavedData
                if(savedData == null){
                    savedData = new WorldData();
                    world.setItemData(WorldData.DATA_TAG, savedData);
                }
                //Set the current SavedData to the retreived one
                if(savedData instanceof WorldData){
                    WorldData.instance = (WorldData)savedData;
                }
            }
        }
    }
}
