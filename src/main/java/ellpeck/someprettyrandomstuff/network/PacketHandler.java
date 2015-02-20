package ellpeck.someprettyrandomstuff.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import ellpeck.someprettyrandomstuff.util.Util;

public class PacketHandler{

    public static SimpleNetworkWrapper theNetwork;

    public static void init(){
        theNetwork = NetworkRegistry.INSTANCE.newSimpleChannel(Util.MOD_ID + "Channel");

        theNetwork.registerMessage(PacketTileEntityFeeder.Handler.class, PacketTileEntityFeeder.class, 0, Side.CLIENT);
    }

}
