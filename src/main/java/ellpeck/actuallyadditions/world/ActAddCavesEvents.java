/*
 * This file ("ActAddCavesEvents.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.world;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.Util;
import ellpeck.actuallyadditions.util.playerdata.PersistentServerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;

public class ActAddCavesEvents{

    public static NBTTagCompound cavesData = new NBTTagCompound();

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event){
        World world = event.entity.worldObj;
        if(!world.isRemote && WorldTypeActAddCaves.isActAddCave(world) && event.entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.entity;

            //Create the caves
            if(!cavesData.getBoolean("CavesCreated")){
                generateCaves(world);
                cavesData.setBoolean("CavesCreated", true);
            }

            //Spawn the player
            NBTTagCompound playerData = PersistentServerData.getDataFromPlayer(player);
            if(!playerData.getBoolean("SpawnedInCaves")){
                int x = cavesData.getInteger("StartX");
                int y = cavesData.getInteger("StartY");
                int z = cavesData.getInteger("StartZ");
                player.setSpawnChunk(new ChunkCoordinates(x, y, z), true);
                player.setPositionAndUpdate(x+0.5, y+1, z+0.5);
                player.inventory.addItemStackToInventory(new ItemStack(InitItems.itemBooklet));

                playerData.setBoolean("SpawnedInCaves", true);
            }
        }
    }

    private static void generateCaves(World world){
        ChunkCoordinates spawn = world.getSpawnPoint();

        int cavesStartX = spawn.posX;
        int cavesStartY = 80;
        int cavesStartZ = spawn.posZ;
        cavesData.setInteger("StartX", cavesStartX);
        cavesData.setInteger("StartY", cavesStartY);
        cavesData.setInteger("StartZ", cavesStartZ);

        //Generate initial box
        for(int x = -7; x <= 7; x++){
            for(int z = -7; z <= 7; z++){
                for(int y = -5; y <= 9; y++){
                    double distance = Vec3.createVectorHelper(x, y, z).distanceTo(Vec3.createVectorHelper(0, 0, 0));
                    if(distance <= MathHelper.getRandomIntegerInRange(Util.RANDOM, 6, 7)){
                        world.setBlockToAir(cavesStartX+x, cavesStartY+y, cavesStartZ+z);
                    }
                }
            }
        }

        //Generate start tower
        for(int y = -5; y < 0; y++){
            world.setBlock(cavesStartX-1, cavesStartY+y, cavesStartZ-1, Blocks.fence, 0, 2);
            world.setBlock(cavesStartX+1, cavesStartY+y, cavesStartZ-1, Blocks.fence, 0, 2);
            world.setBlock(cavesStartX+1, cavesStartY+y, cavesStartZ+1, Blocks.fence, 0, 2);

            world.setBlock(cavesStartX-1, cavesStartY+y, cavesStartZ+1, Blocks.planks, 1, 2);
            world.setBlock(cavesStartX-1, cavesStartY+y, cavesStartZ+2, Blocks.ladder, 3, 2);
        }
        world.setBlock(cavesStartX-1, cavesStartY, cavesStartZ+2, Blocks.ladder, 3, 2);
        for(int x = -1; x <= 1; x++){
            for(int z = -1; z <= 1; z++){
                world.setBlock(cavesStartX+x, cavesStartY, cavesStartZ+z, Blocks.planks, 1, 2);
            }
        }
        world.setBlock(cavesStartX, cavesStartY, cavesStartZ, Blocks.glowstone, 0, 2);

        //Generate Mineshaft
        for(int x = 4; x <= 12; x++){
            for(int z = -1; z <= 1; z++){
                for(int y = -5; y <= -3; y++){
                    if(x%4 == 0 && z != 0){
                        world.setBlock(cavesStartX+x, cavesStartY+y, cavesStartZ+z, Blocks.log, 0, 2);
                    }
                    else if(x%8 == 0 && y == -4){
                        world.setBlock(cavesStartX+x, cavesStartY+y, cavesStartZ+z, Blocks.torch, 3, 2);
                    }
                    else if(y == -3){
                        world.setBlock(cavesStartX+x, cavesStartY+y, cavesStartZ+z, Blocks.wooden_slab, 9, 2);
                    }
                    else{
                        world.setBlockToAir(cavesStartX+x, cavesStartY+y, cavesStartZ+z);
                    }
                }
            }
        }

        //Generate water
        world.setBlock(cavesStartX-7, cavesStartY-3, cavesStartZ-1, Blocks.flowing_water, 0, 2);
        world.setBlockToAir(cavesStartX-6, cavesStartY-3, cavesStartZ-1);
        world.setBlockToAir(cavesStartX-5, cavesStartY-3, cavesStartZ-1);
        world.setBlockToAir(cavesStartX-5, cavesStartY-4, cavesStartZ-1);
        world.setBlockToAir(cavesStartX-5, cavesStartY-5, cavesStartZ-1);
        world.setBlockToAir(cavesStartX-4, cavesStartY-5, cavesStartZ-1);
        world.setBlock(cavesStartX-5, cavesStartY-6, cavesStartZ-1, Blocks.water, 0, 2);
        world.setBlock(cavesStartX-4, cavesStartY-6, cavesStartZ-1, Blocks.water, 0, 2);

        //Generate dirt
        for(int x = -3; x <= 3; x++){
            for(int z = -3; z <= 3; z++){
                if(Util.RANDOM.nextFloat() >= 0.5){
                    world.setBlock(cavesStartX+x, cavesStartY-6, cavesStartZ+z, Blocks.dirt, 0, 2);
                }
            }
        }
    }
}
