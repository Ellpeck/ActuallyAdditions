package ellpeck.actuallyadditions.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

public class FakePlayerUtil{

    public static final String fakeName = "EllpecksActuallyAdditionsFakePlayer";
    public static GameProfile fakeProfile = new GameProfile(UUID.nameUUIDFromBytes(fakeName.getBytes()), fakeName);

    public static FakePlayer newFakePlayer(World world){
        if(world instanceof WorldServer){
            return new FakePlayer((WorldServer)world, fakeProfile);
        }
        else return null;
    }
}
