/*
 * This file ("PacketHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import ellpeck.actuallyadditions.network.gui.PacketGuiButton;
import ellpeck.actuallyadditions.network.gui.PacketGuiNumber;
import ellpeck.actuallyadditions.network.gui.PacketGuiString;
import ellpeck.actuallyadditions.network.sync.PacketSyncerToClient;
import ellpeck.actuallyadditions.util.ModUtil;

public class PacketHandler{

    public static SimpleNetworkWrapper theNetwork;

    public static void init(){
        theNetwork = NetworkRegistry.INSTANCE.newSimpleChannel(ModUtil.MOD_ID_LOWER);

        theNetwork.registerMessage(PacketGuiButton.Handler.class, PacketGuiButton.class, 0, Side.SERVER);
        theNetwork.registerMessage(PacketSyncerToClient.Handler.class, PacketSyncerToClient.class, 1, Side.CLIENT);
        theNetwork.registerMessage(PacketGuiNumber.Handler.class, PacketGuiNumber.class, 2, Side.SERVER);
        theNetwork.registerMessage(PacketGuiString.Handler.class, PacketGuiString.class, 3, Side.SERVER);
        theNetwork.registerMessage(PacketGiveBook.Handler.class, PacketGiveBook.class, 4, Side.SERVER);
    }
}
