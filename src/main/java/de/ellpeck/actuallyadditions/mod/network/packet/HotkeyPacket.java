package de.ellpeck.actuallyadditions.mod.network.packet;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.ItemCrafterOnAStick;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record HotkeyPacket(HotKey hotKey) implements CustomPacketPayload {
    public static final Type<HotkeyPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "hotkey"));

    public HotkeyPacket(byte type) {
        this(HotKey.values()[type]);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum HotKey {
        OPEN_CRAFTING_STICK
    }

    public static final StreamCodec<FriendlyByteBuf, HotkeyPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE, packet -> ((byte) packet.hotKey.ordinal()),
            HotkeyPacket::new
    );

    public static void handle(final HotkeyPacket packet, IPayloadContext ctx) {
        switch (packet.hotKey) {
            case OPEN_CRAFTING_STICK -> ctx.enqueueWork(() -> open(ctx));
        }
    }

    private static void open(IPayloadContext ctx) {
        Player player = ctx.player();

        if (ItemCrafterOnAStick.hasCrafterOnAStick(player)) {
            ItemCrafterOnAStick.openCraftingMenu(player);
        }
    }
}
