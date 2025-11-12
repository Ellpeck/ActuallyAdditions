package de.ellpeck.actuallyadditions.mod.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record FilterOptionsComponent(boolean isWhitelist, boolean respectMod, boolean matchDamage, boolean matchNBT) {
    public static final Codec<FilterOptionsComponent> CODEC = RecordCodecBuilder.create($ -> $.group(
            Codec.BOOL.fieldOf("isWhitelist").forGetter(FilterOptionsComponent::isWhitelist),
            Codec.BOOL.fieldOf("respectMod").forGetter(FilterOptionsComponent::respectMod),
            Codec.BOOL.fieldOf("matchDamage").forGetter(FilterOptionsComponent::matchDamage),
            Codec.BOOL.fieldOf("matchNBT").forGetter(FilterOptionsComponent::matchNBT)
    ).apply($, FilterOptionsComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FilterOptionsComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, FilterOptionsComponent::isWhitelist,
            ByteBufCodecs.BOOL, FilterOptionsComponent::respectMod,
            ByteBufCodecs.BOOL, FilterOptionsComponent::matchDamage,
            ByteBufCodecs.BOOL, FilterOptionsComponent::matchNBT,
            FilterOptionsComponent::new
    );

    public int getPackedSettings() {
        return (this.isWhitelist? 1 : 0) | (this.respectMod? 2 : 0) | (this.matchDamage? 4 : 0) | (this.matchNBT? 8 : 0);
    }

    public FilterOptionsComponent(int packed) {
        this((packed & 1) != 0,
            (packed & 2) != 0,
            (packed & 4) != 0,
            (packed & 8) != 0);
    }

    public static final FilterOptionsComponent EMPTY = new FilterOptionsComponent(false, false, false, false);
}
