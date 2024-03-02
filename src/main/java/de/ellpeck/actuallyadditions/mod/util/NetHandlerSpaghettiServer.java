package de.ellpeck.actuallyadditions.mod.util;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.network.protocol.game.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.Set;

public class NetHandlerSpaghettiServer extends ServerGamePacketListenerImpl {

    public NetHandlerSpaghettiServer(FakePlayer player) {
        super(null, new Connection(PacketFlow.CLIENTBOUND), player);
    }

    @Override
    public void disconnect(Component textComponent) {
    }

    @Override
    public void handlePlayerInput(ServerboundPlayerInputPacket p_147358_1_) {
    }

    @Override
    public void handleMoveVehicle(ServerboundMoveVehiclePacket p_184338_1_) {
    }

    @Override
    public void handleAcceptTeleportPacket(ServerboundAcceptTeleportationPacket p_184339_1_) {
    }

    @Override
    public void handleRecipeBookSeenRecipePacket(ServerboundRecipeBookSeenRecipePacket p_191984_1_) {
    }

    @Override
    public void handleRecipeBookChangeSettingsPacket(ServerboundRecipeBookChangeSettingsPacket p_241831_1_) {
    }

    @Override
    public void handleSeenAdvancements(ServerboundSeenAdvancementsPacket p_194027_1_) {
    }

    @Override
    public void handleCustomCommandSuggestions(ServerboundCommandSuggestionPacket p_195518_1_) {
    }

    @Override
    public void handleSetCommandBlock(ServerboundSetCommandBlockPacket p_210153_1_) {
    }

    @Override
    public void handleSetCommandMinecart(ServerboundSetCommandMinecartPacket p_210158_1_) {
    }

    @Override
    public void handlePickItem(ServerboundPickItemPacket p_210152_1_) {
    }

    @Override
    public void handleRenameItem(ServerboundRenameItemPacket p_210155_1_) {
    }

    @Override
    public void handleSetBeaconPacket(ServerboundSetBeaconPacket p_210154_1_) {
    }

    @Override
    public void handleSetStructureBlock(ServerboundSetStructureBlockPacket p_210157_1_) {
    }

    @Override
    public void handleSetJigsawBlock(ServerboundSetJigsawBlockPacket p_217262_1_) {
    }

    @Override
    public void handleJigsawGenerate(ServerboundJigsawGeneratePacket p_230549_1_) {
    }

    @Override
    public void handleSelectTrade(ServerboundSelectTradePacket p_210159_1_) {
    }

    @Override
    public void handleEditBook(ServerboundEditBookPacket p_210156_1_) {
    }

    @Override
    public void handleEntityTagQuery(ServerboundEntityTagQuery p_211526_1_) {
    }

    @Override
    public void handleBlockEntityTagQuery(ServerboundBlockEntityTagQuery p_211525_1_) {
    }

    @Override
    public void handleMovePlayer(ServerboundMovePlayerPacket p_147347_1_) {
    }

    @Override
    public void teleport(double p_147364_1_, double p_147364_3_, double p_147364_5_, float p_147364_7_, float p_147364_8_) {
    }

    @Override
    public void teleport(double p_175089_1_, double p_175089_3_, double p_175089_5_, float p_175089_7_, float p_175089_8_, Set<ClientboundPlayerPositionPacket.RelativeArgument> p_175089_9_) {
    }

    @Override
    public void handlePlayerAction(ServerboundPlayerActionPacket p_147345_1_) {
    }

    @Override
    public void handleUseItemOn(ServerboundUseItemOnPacket p_184337_1_) {
    }

    @Override
    public void handleUseItem(ServerboundUseItemPacket p_147346_1_) {
    }

    @Override
    public void handleTeleportToEntityPacket(ServerboundTeleportToEntityPacket p_175088_1_) {
    }

    @Override
    public void handleResourcePackResponse(ServerboundResourcePackPacket p_175086_1_) {
    }

    @Override
    public void handlePaddleBoat(ServerboundPaddleBoatPacket p_184340_1_) {
    }

    @Override
    public void onDisconnect(Component p_147231_1_) {
    }

    @Override
    public void send(Packet<?> p_147359_1_) {
    }

    @Override
    public void send(Packet<?> p_211148_1_, @Nullable GenericFutureListener<? extends Future<? super Void>> p_211148_2_) {
    }

    @Override
    public void handleSetCarriedItem(ServerboundSetCarriedItemPacket p_147355_1_) {
    }

    @Override
    public void handleChat(ServerboundChatPacket p_147354_1_) {
    }

    @Override
    public void handleAnimate(ServerboundSwingPacket p_175087_1_) {
    }

    @Override
    public void handlePlayerCommand(ServerboundPlayerCommandPacket p_147357_1_) {
    }

    @Override
    public void handleInteract(ServerboundInteractPacket p_147340_1_) {
    }

    @Override
    public void handleClientCommand(ServerboundClientCommandPacket p_147342_1_) {
    }

    @Override
    public void handleContainerClose(ServerboundContainerClosePacket p_147356_1_) {
    }

    @Override
    public void handleContainerClick(ServerboundContainerClickPacket p_147351_1_) {
    }

    @Override
    public void handlePlaceRecipe(ServerboundPlaceRecipePacket p_194308_1_) {
    }

    @Override
    public void handleContainerButtonClick(ServerboundContainerButtonClickPacket p_147338_1_) {
    }

    @Override
    public void handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket p_147344_1_) {
    }

    @Override
    public void handleSignUpdate(ServerboundSignUpdatePacket p_147343_1_) {
    }

    @Override
    public void handleKeepAlive(ServerboundKeepAlivePacket p_147353_1_) {
    }

    @Override
    public void handlePlayerAbilities(ServerboundPlayerAbilitiesPacket p_147348_1_) {
    }

    @Override
    public void handleClientInformation(ServerboundClientInformationPacket p_147352_1_) {
    }

    @Override
    public void handleCustomPayload(ServerboundCustomPayloadPacket p_147349_1_) {
    }

    @Override
    public void handleChangeDifficulty(ServerboundChangeDifficultyPacket p_217263_1_) {
    }

    @Override
    public void handleLockDifficulty(ServerboundLockDifficultyPacket p_217261_1_) {
    }
}
