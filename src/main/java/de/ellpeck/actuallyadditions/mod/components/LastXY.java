package de.ellpeck.actuallyadditions.mod.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record LastXY(int x, int y) {
	public static final Codec<LastXY> CODEC = RecordCodecBuilder.create(inst -> inst.group(
					Codec.INT.fieldOf("x").forGetter(LastXY::x),
					Codec.INT.fieldOf("y").forGetter(LastXY::y))
			.apply(inst, LastXY::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, LastXY> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, LastXY::x,
			ByteBufCodecs.INT, LastXY::y,
			LastXY::new
	);
}
