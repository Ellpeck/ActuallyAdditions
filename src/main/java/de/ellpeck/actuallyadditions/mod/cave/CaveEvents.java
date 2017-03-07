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
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class CaveEvents{

    private static final int TRIES_BEFORE_FAILURE = 100;
    private static final int DISTANCE_INBETWEEN = 1000;
    private static final int CAVE_SPAWN_SPREAD = 10000;

    @SubscribeEvent
    public void onPlayerUpdate(PlayerTickEvent event){
        if(event.phase == Phase.END){
            EntityPlayer player = event.player;
            if(!player.world.isRemote){
                if(WorldTypeCave.is(player.world)){
                    WorldData worldData = WorldData.get(player.world);
                    if(worldData != null){
                        BlockPos spawn = worldData.generatedCaves.get(player.getUniqueID());
                        if(spawn == null){
                            generateCave(player, worldData.generatedCaves, worldData);
                        }
                        else{
                            if(player.posY >= player.world.getHeight()){
                                putPlayerInCave(player, spawn, worldData);
                            }
                        }

                        PlayerSave playerData = PlayerData.getDataFromPlayer(player);
                        if(playerData != null){
                            if(player.ticksExisted >= 100){
                                if(playerData.receivedCaveMessages < 11){
                                    if(player.world.getTotalWorldTime()%50 == 0){
                                        TextComponentTranslation text = new TextComponentTranslation("info."+ModUtil.MOD_ID+".cave.whisper."+(playerData.receivedCaveMessages+1));
                                        text.getStyle().setColor(TextFormatting.GRAY).setItalic(true);
                                        player.sendMessage(text);

                                        playerData.receivedCaveMessages++;
                                        worldData.markDirty();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static BlockPos generateCave(EntityPlayer player, Map<UUID, BlockPos> generatedCaves, WorldData data){
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        PlayerList list = server.getPlayerList();

        BlockPos worldSpawn = player.world.getSpawnPoint();
        Random rand = new Random(player.world.getSeed());

        TextComponentString prefix = new TextComponentString("["+TextFormatting.GREEN+ModUtil.NAME+TextFormatting.RESET+"] ");
        list.sendChatMsg(prefix.createCopy().appendSibling(new TextComponentTranslation("info."+ModUtil.MOD_ID+".cave.generating", player.getName())));

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

        data.generatedCaves.put(player.getUniqueID(), spawn);
        data.markDirty();

        int chunkX = spawn.getX() >> 4;
        int chunkZ = spawn.getZ() >> 4;
        for(int x = -12; x <= 12; x++){
            for(int z = -12; z <= 12; z++){
                player.world.getChunkProvider().provideChunk(chunkX+x, chunkZ+z);
            }
        }

        StructureBoundingBox box = new StructureBoundingBox(spawn.getX()-7, 0, spawn.getZ()-7, spawn.getX()+7, player.world.getHeight(), spawn.getZ()+7);
        new WorldGenLushCaves(){
            private int crystalCounter;

            @Override
            protected Block getClusterToPlace(Random rand){
                this.crystalCounter++;
                if(this.crystalCounter >= CRYSTAL_CLUSTERS.length){
                    this.crystalCounter = 0;
                }

                return CRYSTAL_CLUSTERS[this.crystalCounter];
            }

            @Override
            protected boolean shouldTryGenCluster(Random rand){
                return rand.nextInt(3) == 0;
            }
        }.generate(player.world, rand, spawn, box);

        putPlayerInCave(player, spawn, data);

        server.saveAllWorlds(false);

        list.sendChatMsg(prefix.appendSibling(new TextComponentTranslation("info."+ModUtil.MOD_ID+".cave.generated", player.getName())));

        return spawn;
    }

    private static void putPlayerInCave(EntityPlayer player, BlockPos spawn, WorldData data){
        if(!player.isSpectator() && player instanceof EntityPlayerMP){
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 400, 4));
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 400));
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400));

            PlayerSave save = PlayerData.getDataFromPlayer(player);
            if(save != null && !save.bookGottenAlready){
                player.inventory.addItemStackToInventory(new ItemStack(InitItems.itemBooklet));
                player.inventory.addItemStackToInventory(new ItemStack(InitBlocks.blockTinyTorch, 2));

                save.bookGottenAlready = true;
                data.markDirty();
            }

            ((EntityPlayerMP)player).connection.setPlayerLocation(spawn.getX()+0.5, spawn.getY()+1, spawn.getZ()+0.5, 0F, 0F);
        }
    }
}
