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
        else return null;
    }
}
