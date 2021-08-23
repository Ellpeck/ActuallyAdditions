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
import de.ellpeck.actuallyadditions.mod.inventory.gui.*;
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
        ScreenManager.register(ActuallyContainers.BAG_CONTAINER.get(), GuiBag::new);
        ScreenManager.register(ActuallyContainers.BIO_REACTOR_CONTAINER.get(), GuiBioReactor::new);
        ScreenManager.register(ActuallyContainers.BREAKER_CONTAINER.get(), GuiBreaker::new);
        ScreenManager.register(ActuallyContainers.CANOLA_PRESS_CONTAINER.get(), GuiCanolaPress::new);
        ScreenManager.register(ActuallyContainers.COAL_GENERATOR_CONTAINER.get(), GuiCoalGenerator::new);
        ScreenManager.register(ActuallyContainers.COFFEE_MACHINE_CONTAINER.get(), GuiCoffeeMachine::new);
        ScreenManager.register(ActuallyContainers.DIRECTIONAL_BREAKER_CONTAINER.get(), GuiDirectionalBreaker::new);
        ScreenManager.register(ActuallyContainers.DRILL_CONTAINER.get(), GuiDrill::new);
        ScreenManager.register(ActuallyContainers.DROPPER_CONTAINER.get(), GuiDropper::new);
        ScreenManager.register(ActuallyContainers.ENERVATOR_CONTAINER.get(), GuiEnervator::new);
        ScreenManager.register(ActuallyContainers.ENERGIZER_CONTAINER.get(), GuiEnergizer::new);
        ScreenManager.register(ActuallyContainers.FARMER_CONTAINER.get(), GuiFarmer::new);
        ScreenManager.register(ActuallyContainers.FEEDER_CONTAINER.get(), GuiFeeder::new);
        ScreenManager.register(ActuallyContainers.FERMENTING_BARREL_CONTAINER.get(), GuiFermentingBarrel::new);
        ScreenManager.register(ActuallyContainers.FILTER_CONTAINER.get(), GuiFilter::new);
        ScreenManager.register(ActuallyContainers.FIREWORK_BOX_CONTAINER.get(), GuiFireworkBox::new);
        ScreenManager.register(ActuallyContainers.FLUID_COLLECTOR_CONTAINER.get(), GuiFluidCollector::new);
        ScreenManager.register(ActuallyContainers.FURNACE_DOUBLE_CONTAINER.get(), GuiFurnaceDouble::new);
        ScreenManager.register(ActuallyContainers.GRINDER_CONTAINER.get(), GuiGrinder::new);
        ScreenManager.register(ActuallyContainers.INPUTTER_CONTAINER.get(), GuiInputter::new);
        ScreenManager.register(ActuallyContainers.LASER_RELAY_ITEM_WHITELIST_CONTAINER.get(), GuiLaserRelayItemWhitelist::new);
        ScreenManager.register(ActuallyContainers.MINER_CONTAINER.get(), GuiMiner::new);
        ScreenManager.register(ActuallyContainers.OIL_GENERATOR_CONTAINER.get(), GuiOilGenerator::new);
        ScreenManager.register(ActuallyContainers.PHANTOM_PLACER_CONTAINER.get(), GuiPhantomPlacer::new);
        ScreenManager.register(ActuallyContainers.RANGED_COLLECTOR_CONTAINER.get(), GuiRangedCollector::new);
        ScreenManager.register(ActuallyContainers.XPSOLIDIFIER_CONTAINER.get(), GuiXPSolidifier::new);
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
        assert Minecraft.getInstance().hitResult != null;
        connection.send(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, pos, ((BlockRayTraceResult) Minecraft.getInstance().hitResult).getDirection()));
    }
}