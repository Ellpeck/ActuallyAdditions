package de.ellpeck.actuallyadditions.mod.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record LaserItemParticleData(ItemStack stack, double outputX, double outputY, double outputZ) implements ParticleOptions {
    public static final MapCodec<LaserItemParticleData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    ItemStack.CODEC.fieldOf("stack").forGetter(d -> d.stack),
                    Codec.DOUBLE.fieldOf("outputX").forGetter(d -> d.outputX),
                    Codec.DOUBLE.fieldOf("outputY").forGetter(d -> d.outputY),
                    Codec.DOUBLE.fieldOf("outputZ").forGetter(d -> d.outputZ)
            )
            .apply(instance, LaserItemParticleData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, LaserItemParticleData> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, LaserItemParticleData::stack,
            ByteBufCodecs.DOUBLE, LaserItemParticleData::outputX,
            ByteBufCodecs.DOUBLE, LaserItemParticleData::outputY,
            ByteBufCodecs.DOUBLE, LaserItemParticleData::outputZ,
            LaserItemParticleData::new
    );

    @Override
    public ParticleType<?> getType() {
        return ActuallyParticles.LASER_ITEM.get();
    }

//    @Override
//    public void writeToNetwork(FriendlyByteBuf buffer) {
//        buffer.writeItem(this.stack);
//        buffer.writeDouble(this.outputX);
//        buffer.writeDouble(this.outputY);
//        buffer.writeDouble(this.outputZ);
//    }
//
//    @Override
//    public String writeToString() {
//        return String.format(Locale.ROOT, "%s %s %.2f %.2f %.2f", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()),
//                BuiltInRegistries.ITEM.getKey(this.stack.getItem()),
//                this.outputX, this.outputY, this.outputZ);
//    }
}