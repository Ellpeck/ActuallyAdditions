/*
 * This file ("FakePlayerUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

public class FakePlayerUtil{

    private static final String fakeName = "EllpecksActuallyAdditionsFakePlayer";
    private static GameProfile fakeProfile = new GameProfile(UUID.nameUUIDFromBytes(fakeName.getBytes()), fakeName);
    private static FakePlayer theFakePlayer;

    public static FakePlayer newFakePlayer(World world){
        if(world instanceof WorldServer){
            if(theFakePlayer == null){
                theFakePlayer = new FakePlayer((WorldServer)world, fakeProfile);
            }
            return theFakePlayer;
        }
        else{
            return null;
        }
    }
}
