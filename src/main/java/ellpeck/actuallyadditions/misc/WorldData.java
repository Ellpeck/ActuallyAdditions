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

import java.util.ArrayList;

public class WorldData extends WorldSavedData{

    public static final String DATA_TAG = ModUtil.MOD_ID+"WorldData";

    public WorldData(String tag){
        super(tag);
    }

    public static WorldData instance;

    @Override
    public void readFromNBT(NBTTagCompound compound){
        int netAmount = compound.getInteger("LaserNetworkAmount");

        LaserRelayConnectionHandler.getInstance().networks.clear();
        for(int i = 0; i < netAmount; i++){
            ArrayList<LaserRelayConnectionHandler.ConnectionPair> network = LaserRelayConnectionHandler.getInstance().readNetworkFromNBT(compound, "LaserNetwork"+i);
            if(network != null){
                LaserRelayConnectionHandler.getInstance().networks.add(network);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        int netAmount = LaserRelayConnectionHandler.getInstance().networks.size();
        compound.setInteger("LaserNetworkAmount", netAmount);

        for(int i = 0; i < netAmount; i++){
            LaserRelayConnectionHandler.getInstance().writeNetworkToNBT(LaserRelayConnectionHandler.getInstance().networks.get(i), compound, "LaserNetwork"+i);
        }
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
                    savedData = new WorldData(WorldData.DATA_TAG);
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
