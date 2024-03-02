package de.ellpeck.actuallyadditions.mod.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public class LaserItemParticleData extends ParticleType<LaserItemParticleData> implements ParticleOptions {
    private ParticleType<LaserItemParticleData> type;
    public static final Codec<LaserItemParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ItemStack.CODEC.fieldOf("stack").forGetter(d -> d.stack),
                    Codec.DOUBLE.fieldOf("outputX").forGetter(d -> d.outputX),
                    Codec.DOUBLE.fieldOf("outputY").forGetter(d -> d.outputY),
                    Codec.DOUBLE.fieldOf("outputZ").forGetter(d -> d.outputZ)
            )
            .apply(instance, LaserItemParticleData::new));
    protected final ItemStack stack;
    protected final double outputX, outputY, outputZ;
    @SuppressWarnings("deprecation")
    static final ParticleOptions.Deserializer<LaserItemParticleData> DESERIALIZER = new ParticleOptions.Deserializer<LaserItemParticleData>() {

        @Override
        public LaserItemParticleData fromCommand(ParticleType<LaserItemParticleData> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            String itemString = reader.readString();
            ResourceLocation itemLocation = ResourceLocation.tryParse(itemString);
            Item item = itemLocation == null ? null : ForgeRegistries.ITEMS.getValue(itemLocation);
            ItemStack stack = item == null ? ItemStack.EMPTY : new ItemStack(item);
            reader.expect(' ');
            double outputX = reader.readDouble();
            reader.expect(' ');
            double outputY = reader.readDouble();
            reader.expect(' ');
            double outputZ = reader.readDouble();
            return new LaserItemParticleData(type, stack, outputX, outputY, outputZ);
        }

        @Override
        public LaserItemParticleData fromNetwork(ParticleType<LaserItemParticleData> type, FriendlyByteBuf buffer) {
            ItemStack stack = buffer.readItem();
            double outputX = buffer.readDouble();
            double outputY = buffer.readDouble();
            double outputZ = buffer.readDouble();
            return new LaserItemParticleData(type, stack, outputX, outputY, outputZ);
        }
    };

    public LaserItemParticleData(ParticleType<LaserItemParticleData> particleTypeData, ItemStack stack,
                                 double outputX, double outputY, double outputZ) {
        super(false, DESERIALIZER);
        this.type = particleTypeData;
        this.stack = stack;
        this.outputX = outputX;
        this.outputY = outputY;
        this.outputZ = outputZ;
    }

    public LaserItemParticleData(ItemStack stack, double outputX, double outputY, double outputZ) {
        this(ActuallyParticles.LASER_ITEM.get(), stack, outputX, outputY, outputZ);
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeItemStack(this.stack, true);
        buffer.writeDouble(this.outputX);
        buffer.writeDouble(this.outputY);
        buffer.writeDouble(this.outputZ);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %s %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()),
                ForgeRegistries.ITEMS.getKey(this.stack.getItem()),
                this.outputX, this.outputY, this.outputZ);
    }

    @Override
    public Codec<LaserItemParticleData> codec() {
        return LaserItemParticleData.CODEC;
    }
}