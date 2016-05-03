/*
 * This file ("FakePlayerUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.UUID;

public class FakePlayerUtil{

    private static final String FAKE_NAME = ModUtil.MOD_ID+"FakePlayer";
    private static final GameProfile FAKE_PROFILE = new GameProfile(UUID.nameUUIDFromBytes(FAKE_NAME.getBytes()), FAKE_NAME);
    private static FakePlayer theFakePlayer;

    public static void info(){
        ModUtil.LOGGER.info(ModUtil.NAME+"' Fake Player: '"+FAKE_PROFILE.getName()+"', UUID: "+FAKE_PROFILE.getId());
    }

    public static FakePlayer getFakePlayer(World world){
        if(world instanceof WorldServer){
            if(theFakePlayer == null){
                theFakePlayer = FakePlayerFactory.get((WorldServer)world, FAKE_PROFILE);
            }
            return theFakePlayer;
        }
        else{
            return null;
        }
    }

    public static void unloadFakePlayer(){
        theFakePlayer = null;
    }
}
