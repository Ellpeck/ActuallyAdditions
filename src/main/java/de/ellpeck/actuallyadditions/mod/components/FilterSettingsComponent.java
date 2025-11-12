package de.ellpeck.actuallyadditions.mod.components;

import com.mojang.serialization.Codec;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public record FilterSettingsComponent(FilterSettings inner) {
    public static final Codec<FilterSettingsComponent> CODEC = FilterSettings.CODEC.xmap(
        FilterSettingsComponent::new,
        FilterSettingsComponent::getInner
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, FilterSettingsComponent> STREAM_CODEC = FilterSettings.STREAM_CODEC.map(
        FilterSettingsComponent::new,
        FilterSettingsComponent::getInner
    );

    public static final FilterSettingsComponent EMPTY = new FilterSettingsComponent(
        new FilterSettings(0, false, false, false, false)
    );

    public FilterSettingsComponent(int slots, int packedSettings, ItemStackHandlerAA contents) {
        this(FilterSettings.of(slots, packedSettings, contents));
    }

    public FilterSettingsComponent(int slots) {
        this(new FilterSettings(slots, false, false, false, false));
    }

    public FilterSettingsComponent(int slots, boolean isWhitelist, boolean matchNBT, boolean matchMeta, boolean matchMod) {
        this(new FilterSettings(slots, isWhitelist, matchNBT, matchMeta, matchMod));
    }

    public FilterSettingsComponent(FilterSettings inner) {
        this.inner = inner;
    }

    public FilterSettings getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FilterSettingsComponent other) {
            return Objects.equals(this.inner, other.inner);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return inner.hashCode();
    }

    record FilterSlot(int index, ItemStack itemStack) {

    }
}
