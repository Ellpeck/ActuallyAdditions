package de.ellpeck.actuallyadditions.common.util;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.Set;

public class NetHandlerSpaghettiServer extends ServerPlayNetHandler {
    public NetHandlerSpaghettiServer(FakePlayer player) {
        super(null, new NetworkManager(PacketDirection.CLIENTBOUND), player);
    }

    @Override
    public void tick() {
    }

    @Override
    public void captureCurrentPosition() {
    }

    @Override
    public void disconnect(ITextComponent textComponent) {
    }

    @Override
    public void processInput(CInputPacket packetIn) {
    }

    @Override
    public void processVehicleMove(CMoveVehiclePacket packetIn) {
    }

    @Override
    public void processConfirmTeleport(CConfirmTeleportPacket packetIn) {
    }

    @Override
    public void handleRecipeBookUpdate(CRecipeInfoPacket packetIn) {
    }

    @Override
    public void handleSeenAdvancements(CSeenAdvancementsPacket packetIn) {
    }

    @Override
    public void processTabComplete(CTabCompletePacket packetIn) {
    }

    @Override
    public void processUpdateCommandBlock(CUpdateCommandBlockPacket packetIn) {
    }

    @Override
    public void processUpdateCommandMinecart(CUpdateMinecartCommandBlockPacket packetIn) {
    }

    @Override
    public void processPickItem(CPickItemPacket packetIn) {
    }

    @Override
    public void processRenameItem(CRenameItemPacket packetIn) {
    }

    @Override
    public void processUpdateBeacon(CUpdateBeaconPacket packetIn) {
    }

    @Override
    public void processUpdateStructureBlock(CUpdateStructureBlockPacket packetIn) {
    }

    @Override
    public void func_217262_a(CUpdateJigsawBlockPacket p_217262_1_) {
    }

    @Override
    public void processSelectTrade(CSelectTradePacket packetIn) {
    }

    @Override
    public void processEditBook(CEditBookPacket packetIn) {
    }

    @Override
    public void processNBTQueryEntity(CQueryEntityNBTPacket packetIn) {
    }

    @Override
    public void processNBTQueryBlockEntity(CQueryTileEntityNBTPacket packetIn) {
    }

    @Override
    public void processPlayer(CPlayerPacket packetIn) {
    }

    @Override
    public void setPlayerLocation(double x, double y, double z, float yaw, float pitch) {
    }

    @Override
    public void setPlayerLocation(double x, double y, double z, float yaw, float pitch, Set<SPlayerPositionLookPacket.Flags> relativeSet) {
    }

    @Override
    public void processPlayerDigging(CPlayerDiggingPacket packetIn) {
    }

    @Override
    public void processTryUseItemOnBlock(CPlayerTryUseItemOnBlockPacket packetIn) {
    }

    @Override
    public void processTryUseItem(CPlayerTryUseItemPacket packetIn) {
    }

    @Override
    public void handleSpectate(CSpectatePacket packetIn) {
    }

    @Override
    public void handleResourcePackStatus(CResourcePackStatusPacket packetIn) {
    }

    @Override
    public void processSteerBoat(CSteerBoatPacket packetIn) {
    }

    @Override
    public void onDisconnect(ITextComponent reason) {
    }

    @Override
    public void sendPacket(IPacket<?> packetIn) {
    }

    @Override
    public void sendPacket(IPacket<?> packetIn, @Nullable GenericFutureListener<? extends Future<? super Void>> futureListeners) {
    }

    @Override
    public void processHeldItemChange(CHeldItemChangePacket packetIn) {
    }

    @Override
    public void processChatMessage(CChatMessagePacket packetIn) {
    }

    @Override
    public void handleAnimation(CAnimateHandPacket packetIn) {
    }

    @Override
    public void processEntityAction(CEntityActionPacket packetIn) {
    }

    @Override
    public void processUseEntity(CUseEntityPacket packetIn) {
    }

    @Override
    public void processClientStatus(CClientStatusPacket packetIn) {
    }

    @Override
    public void processCloseWindow(CCloseWindowPacket packetIn) {
    }

    @Override
    public void processClickWindow(CClickWindowPacket packetIn) {
    }

    @Override
    public void processPlaceRecipe(CPlaceRecipePacket packetIn) {
    }

    @Override
    public void processEnchantItem(CEnchantItemPacket packetIn) {
    }

    @Override
    public void processCreativeInventoryAction(CCreativeInventoryActionPacket packetIn) {
    }

    @Override
    public void processConfirmTransaction(CConfirmTransactionPacket packetIn) {
    }

    @Override
    public void processUpdateSign(CUpdateSignPacket packetIn) {
    }

    @Override
    public void processKeepAlive(CKeepAlivePacket packetIn) {
    }

    @Override
    public void processPlayerAbilities(CPlayerAbilitiesPacket packetIn) {
    }

    @Override
    public void processClientSettings(CClientSettingsPacket packetIn) {
    }

    @Override
    public void processCustomPayload(CCustomPayloadPacket packetIn) {
    }

    @Override
    public void func_217263_a(CSetDifficultyPacket p_217263_1_) {
    }

    @Override
    public void func_217261_a(CLockDifficultyPacket p_217261_1_) {
    }
}
