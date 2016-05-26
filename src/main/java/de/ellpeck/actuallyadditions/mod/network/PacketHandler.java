/*
 * This file ("PacketHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.mod.network.gui.PacketGuiButton;
import de.ellpeck.actuallyadditions.mod.network.gui.PacketGuiNumber;
import de.ellpeck.actuallyadditions.mod.network.gui.PacketGuiString;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler{

    public static SimpleNetworkWrapper theNetwork;

    public static void init(){
        theNetwork = NetworkRegistry.INSTANCE.newSimpleChannel(ModUtil.MOD_ID);

        theNetwork.registerMessage(PacketGuiButton.Handler.class, PacketGuiButton.class, 0, Side.SERVER);
        theNetwork.registerMessage(PacketGuiNumber.Handler.class, PacketGuiNumber.class, 1, Side.SERVER);
        theNetwork.registerMessage(PacketGuiString.Handler.class, PacketGuiString.class, 2, Side.SERVER);
        theNetwork.registerMessage(PacketParticle.Handler.class, PacketParticle.class, 3, Side.CLIENT);
        theNetwork.registerMessage(PacketUpdateTileEntity.Handler.class, PacketUpdateTileEntity.class, 4, Side.CLIENT);
        theNetwork.registerMessage(PacketBookletStandButton.Handler.class, PacketBookletStandButton.class, 5, Side.SERVER);
    }
}
