/*
 * This file ("CaveEvents.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.cave;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.PlayerData.PlayerSave;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.gen.WorldGenLushCaves;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class CaveEvents{

    private static final int TRIES_BEFORE_FAILURE = 500;
    private static final int DISTANCE_INBETWEEN = 1000;
    private static final int CAVE_SPAWN_SPREAD = 50000;

    @SubscribeEvent
    public void onPlayerUpdate(PlayerTickEvent event){
        EntityPlayer player = event.player;
        if(!player.world.isRemote){
            if(WorldTypeCave.is(player.world)){
                WorldData data = WorldData.get(player.world);
                if(data != null){
                    BlockPos spawn = data.generatedCaves.get(player.getUniqueID());
                    if(spawn == null){
                        BlockPos cavePos = generateCave(player, data.generatedCaves);

                        data.generatedCaves.put(player.getUniqueID(), cavePos);
                        data.markDirty();
                    }
                    else{
                        if(player.posY >= player.world.getHeight()){
                            putPlayerInCave(player, spawn);
                        }
                    }
                }
            }
        }
    }

    private static BlockPos generateCave(EntityPlayer player, Map<UUID, BlockPos> generatedCaves){
        BlockPos worldSpawn = player.world.getSpawnPoint();
        Random rand = new Random(player.world.getSeed());

        BlockPos spawn = null;
        tries:
        for(int i = 0; i < TRIES_BEFORE_FAILURE; i++){
            int randX = MathHelper.getInt(rand, -CAVE_SPAWN_SPREAD, CAVE_SPAWN_SPREAD);
            int randY = MathHelper.getInt(rand, 56, 200);
            int randZ = MathHelper.getInt(rand, -CAVE_SPAWN_SPREAD, CAVE_SPAWN_SPREAD);

            spawn = new BlockPos(worldSpawn.getX()+randX, randY, worldSpawn.getZ()+randZ);

            for(BlockPos pos : generatedCaves.values()){
                if(pos.distanceSq(spawn) <= DISTANCE_INBETWEEN*DISTANCE_INBETWEEN){
                    continue tries;
                }
            }

            break;
        }

        int chunkX = spawn.getX() >> 4;
        int chunkZ = spawn.getZ() >> 4;
        for(int x = -12; x <= 12; x++){
            for(int z = -12; z <= 12; z++){
                player.world.getChunkProvider().provideChunk(chunkX+x, chunkZ+z);
            }
        }

        StructureBoundingBox box = new StructureBoundingBox(spawn.getX()-7, 0, spawn.getZ()-7, spawn.getX()+7, player.world.getHeight(), spawn.getZ()+7);
        new WorldGenLushCaves().generate(player.world, rand, spawn, box);

        putPlayerInCave(player, spawn);

        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        server.saveAllWorlds(false);

        return spawn;
    }

    private static void putPlayerInCave(EntityPlayer player, BlockPos spawn){
        if(!player.isSpectator() && player instanceof EntityPlayerMP){
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 400, 4));
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 400));
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400));

            PlayerSave save = PlayerData.getDataFromPlayer(player);
            if(save != null && !save.bookGottenAlready){
                player.inventory.addItemStackToInventory(new ItemStack(InitItems.itemBooklet));
                player.inventory.addItemStackToInventory(new ItemStack(InitBlocks.blockTinyTorch, 2));

                save.bookGottenAlready = true;
                WorldData.get(player.world).markDirty();
            }

            ((EntityPlayerMP)player).connection.setPlayerLocation(spawn.getX()+0.5, spawn.getY()+1, spawn.getZ()+0.5, 0F, 0F);
        }
    }
}
