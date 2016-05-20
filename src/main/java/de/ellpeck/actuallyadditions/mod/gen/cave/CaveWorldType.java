/*
 * This file ("CaveWorldType.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.cave;

import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.misc.WorldData;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.playerdata.PersistentServerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

public class CaveWorldType extends WorldType{

    public CaveWorldType(){
        //Name can't be longer than 16 :'(
        super("actaddcaveworld");

        Util.registerEvent(this);

        ModUtil.LOGGER.info("Cave World config enabled! Registering cave world type...");
    }

    public static boolean isCave(World world){
        return ConfigValues.caveWorld && world.getWorldType() instanceof CaveWorldType;
    }

    @Nonnull
    @Override
    public IChunkGenerator getChunkGenerator(@Nonnull World world, String generatorOptions){
        return new ChunkProviderCave(world);
    }

    @Override
    public int getSpawnFuzz(@Nonnull WorldServer world, MinecraftServer server){
        return 1;
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event){
        if(event.getEntity() instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP)event.getEntity();
            if(isCave(player.worldObj) && !player.worldObj.isRemote){
                if(player.posY >= player.worldObj.getHeight() && !player.isSpectator()){
                    BlockPos spawn = player.worldObj.getSpawnPoint();
                    player.connection.setPlayerLocation(spawn.getX()+0.5, spawn.getY()+1, spawn.getZ()+0.5, player.rotationYaw, player.rotationPitch);
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
