package ellpeck.actuallyadditions.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import ellpeck.actuallyadditions.network.gui.PacketGuiButton;
import ellpeck.actuallyadditions.network.gui.PacketGuiNumber;
import ellpeck.actuallyadditions.util.ModUtil;

public class PacketHandler{

    public static SimpleNetworkWrapper theNetwork;

    public static void init(){
        theNetwork = NetworkRegistry.INSTANCE.newSimpleChannel(ModUtil.MOD_ID_LOWER);

        theNetwork.registerMessage(PacketTileEntityFeeder.Handler.class, PacketTileEntityFeeder.class, 0, Side.CLIENT);
        theNetwork.registerMessage(PacketGuiButton.Handler.class, PacketGuiButton.class, 1, Side.SERVER);
        theNetwork.registerMessage(PacketFluidCollectorToClient.Handler.class, PacketFluidCollectorToClient.class, 2, Side.CLIENT);
        theNetwork.registerMessage(PacketGuiNumber.Handler.class, PacketGuiNumber.class, 3, Side.SERVER);
    }
}
