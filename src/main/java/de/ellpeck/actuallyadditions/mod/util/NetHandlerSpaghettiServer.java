package de.ellpeck.actuallyadditions.mod.util;

import java.util.Set;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlaceRecipe;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketPlayerPosLook.EnumFlags;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.FakePlayer;

public class NetHandlerSpaghettiServer extends NetHandlerPlayServer {

    public NetHandlerSpaghettiServer(FakePlayer player) {
        super(null, new NetworkManager(EnumPacketDirection.CLIENTBOUND), player);
    }

    @Override
    public void disconnect(ITextComponent textComponent) {
    }

    @Override
    public void handlePlaceRecipe(CPacketPlaceRecipe p_194308_1_) {
    }

    @Override
    public void handleAnimation(CPacketAnimation packetIn) {
    }

    @Override
    public void handleRecipeBookUpdate(CPacketRecipeInfo p_191984_1_) {
    }

    @Override
    public void handleResourcePackStatus(CPacketResourcePackStatus packetIn) {
    }

    @Override
    public void handleSeenAdvancements(CPacketSeenAdvancements p_194027_1_) {
    }

    @Override
    public void handleSpectate(CPacketSpectate packetIn) {
    }

    @Override
    public void onDisconnect(ITextComponent reason) {
    }

    @Override
    public void processChatMessage(CPacketChatMessage packetIn) {
    }

    @Override
    public void processClickWindow(CPacketClickWindow packetIn) {
    }

    @Override
    public void processClientSettings(CPacketClientSettings packetIn) {
    }

    @Override
    public void processClientStatus(CPacketClientStatus packetIn) {
    }

    @Override
    public void processCloseWindow(CPacketCloseWindow packetIn) {
    }

    @Override
    public void processConfirmTeleport(CPacketConfirmTeleport packetIn) {
    }

    @Override
    public void processConfirmTransaction(CPacketConfirmTransaction packetIn) {
    }

    @Override
    public void processCreativeInventoryAction(CPacketCreativeInventoryAction packetIn) {
    }

    @Override
    public void processCustomPayload(CPacketCustomPayload packetIn) {
    }

    @Override
    public void processEnchantItem(CPacketEnchantItem packetIn) {
    }

    @Override
    public void processEntityAction(CPacketEntityAction packetIn) {
    }

    @Override
    public void processHeldItemChange(CPacketHeldItemChange packetIn) {
    }

    @Override
    public void processInput(CPacketInput packetIn) {
    }

    @Override
    public void processKeepAlive(CPacketKeepAlive packetIn) {
    }

    @Override
    public void processPlayer(CPacketPlayer packetIn) {
    }

    @Override
    public void processPlayerAbilities(CPacketPlayerAbilities packetIn) {
    }

    @Override
    public void processPlayerDigging(CPacketPlayerDigging packetIn) {
    }

    @Override
    public void processSteerBoat(CPacketSteerBoat packetIn) {
    }

    @Override
    public void processTabComplete(CPacketTabComplete packetIn) {
    }

    @Override
    public void processTryUseItem(CPacketPlayerTryUseItem packetIn) {
    }

    @Override
    public void processTryUseItemOnBlock(CPacketPlayerTryUseItemOnBlock packetIn) {
    }

    @Override
    public void processUpdateSign(CPacketUpdateSign packetIn) {
    }

    @Override
    public void processUseEntity(CPacketUseEntity packetIn) {
    }

    @Override
    public void processVehicleMove(CPacketVehicleMove packetIn) {
    }

    @Override
    public void sendPacket(Packet<?> packetIn) {
    }

    @Override
    public void setPlayerLocation(double x, double y, double z, float yaw, float pitch) {
    }

    @Override
    public void setPlayerLocation(double x, double y, double z, float yaw, float pitch, Set<EnumFlags> relativeSet) {

    }

}
