/*
 * This file ("ActuallyAdditions.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod;

import de.ellpeck.actuallyadditions.mod.blocks.render.*;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.entity.RenderWorm;
import de.ellpeck.actuallyadditions.mod.event.ClientEvents;
import de.ellpeck.actuallyadditions.mod.inventory.ActuallyContainers;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiBag;
import de.ellpeck.actuallyadditions.mod.misc.special.SpecialRenderInit;
import de.ellpeck.actuallyadditions.mod.tile.ActuallyTiles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ActuallyAdditionsClient {

    public static void setup() {
        ScreenManager.registerFactory(ActuallyContainers.BAG_CONTAINER.get(), GuiBag::new);

        // From old proxy
        InitEntities.initClient();
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        MinecraftForge.EVENT_BUS.register(new ClientRegistryHandler());
        MinecraftForge.EVENT_BUS.register(new SpecialRenderInit());

        setupSpecialRenders();

        RenderWorm.fixItemStack();// todo: remove
    }

    private static void setupSpecialRenders() {
        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.ATOMICRECONSTRUCTOR_TILE.get(), RenderReconstructorLens::new);
        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.DISPLAYSTAND_TILE.get(), RenderDisplayStand::new);
        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.EMPOWERER_TILE.get(), RenderEmpowerer::new);
        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.BATTERYBOX_TILE.get(), RenderBatteryBox::new);

        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.LASERRELAYENERGY_TILE.get(), RenderLaserRelay::new);
        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.LASERRELAYENERGYADVANCED_TILE.get(), RenderLaserRelay::new);
        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.LASERRELAYENERGYEXTREME_TILE.get(), RenderLaserRelay::new);
        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.LASERRELAYITEM_TILE.get(), RenderLaserRelay::new);
        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.LASERRELAYITEMWHITELIST_TILE.get(), RenderLaserRelay::new);
        ClientRegistry.bindTileEntityRenderer(ActuallyTiles.LASERRELAYFLUIDS_TILE.get(), RenderLaserRelay::new);
    }

    // TODO: [port] validate that this works
    public void sendBreakPacket(BlockPos pos) {
        ClientPlayNetHandler connection = Minecraft.getInstance().getConnection();
        assert connection != null;
        assert Minecraft.getInstance().objectMouseOver != null;
        connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, pos, ((BlockRayTraceResult) Minecraft.getInstance().objectMouseOver).getFace()));
    }
}
