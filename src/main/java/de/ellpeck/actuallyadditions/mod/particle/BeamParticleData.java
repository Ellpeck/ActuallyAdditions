package de.ellpeck.actuallyadditions.mod.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public class BeamParticleData extends ParticleType<BeamParticleData> implements ParticleOptions {
	private ParticleType<BeamParticleData> type;
	public static final Codec<BeamParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
					Codec.DOUBLE.fieldOf("endX").forGetter(d -> d.endX),
					Codec.DOUBLE.fieldOf("endY").forGetter(d -> d.endY),
					Codec.DOUBLE.fieldOf("endZ").forGetter(d -> d.endZ),
					Codec.FLOAT.fieldOf("r").forGetter(d -> d.color[0]),
					Codec.FLOAT.fieldOf("g").forGetter(d -> d.color[1]),
					Codec.FLOAT.fieldOf("b").forGetter(d -> d.color[2]),
					Codec.FLOAT.fieldOf("alpha").forGetter(d -> d.alpha),
					Codec.INT.fieldOf("maxAge").forGetter(d -> d.maxAge),
					Codec.DOUBLE.fieldOf("rotationTime").forGetter(d -> d.rotationTime),
					Codec.FLOAT.fieldOf("size").forGetter(d -> d.size)
			)
			.apply(instance, BeamParticleData::new));
	protected final double endX, endY, endZ;
	protected final float[] color = new float[3];
	protected final float alpha;
	protected final int maxAge;
	protected final double rotationTime;
	protected final float size;
	@SuppressWarnings("deprecation")
	static final Deserializer<BeamParticleData> DESERIALIZER = new Deserializer<BeamParticleData>() {

		@Override
		public BeamParticleData fromCommand(ParticleType<BeamParticleData> type, StringReader reader) throws CommandSyntaxException {
			reader.expect(' ');
			double endX = reader.readDouble();
			reader.expect(' ');
			double endY = reader.readDouble();
			reader.expect(' ');
			double endZ = reader.readDouble();
			reader.expect(' ');
			float red = (float) reader.readDouble();
			reader.expect(' ');
			float green = (float) reader.readDouble();
			reader.expect(' ');
			float blue = (float) reader.readDouble();
			reader.expect(' ');
			float alpha = (float) reader.readDouble();
			reader.expect(' ');
			int maxAge = reader.readInt();
			reader.expect(' ');
			double rotationTime = reader.readDouble();
			reader.expect(' ');
			float size = (float) reader.readDouble();
			return new BeamParticleData(type, endX, endY, endZ, red, green, blue, alpha, maxAge, rotationTime, size);
		}

		@Override
		public BeamParticleData fromNetwork(ParticleType<BeamParticleData> type, FriendlyByteBuf buffer) {
			double endX = buffer.readDouble();
			double endY = buffer.readDouble();
			double endZ = buffer.readDouble();
			float red = buffer.readFloat();
			float green = buffer.readFloat();
			float blue = buffer.readFloat();
			float alpha = buffer.readFloat();
			int maxAge = buffer.readInt();
			double rotationTime = buffer.readDouble();
			float size = buffer.readFloat();

			return new BeamParticleData(type, endX, endY, endZ, red, green, blue, alpha, maxAge, rotationTime, size);
		}
	};

	public BeamParticleData(ParticleType<BeamParticleData> particleTypeData,
							double endX, double endY, double endZ, float red, float green, float blue, float alpha,
							int maxAge, double rotationTime, float size) {
		super(false, DESERIALIZER);
		this.type = particleTypeData;
		this.endX = endX;
		this.endY = endY;
		this.endZ = endZ;
		this.color[0] = red;
		this.color[1] = green;
		this.color[2] = blue;
		this.alpha = alpha;
		this.maxAge = maxAge;
		this.rotationTime = rotationTime;
		this.size = size;
	}

	public BeamParticleData(double endX, double endY, double endZ, float red, float green, float blue, float alpha,
							int maxAge, double rotationTime, float size) {
		this(ActuallyParticles.BEAM.get(), endX, endY, endZ, red, green, blue, alpha, maxAge, rotationTime, size);
	}

	public BeamParticleData(double endX, double endY, double endZ, float[] color, float alpha,
							int maxAge, double rotationTime, float size) {
		this(ActuallyParticles.BEAM.get(), endX, endY, endZ, color[0], color[1], color[2], alpha, maxAge, rotationTime, size);
	}

	@Override
	public ParticleType<?> getType() {
		return type;
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf buffer) {
		buffer.writeDouble(this.endX);
		buffer.writeDouble(this.endY);
		buffer.writeDouble(this.endZ);
		buffer.writeFloat(this.color[0]);
		buffer.writeFloat(this.color[1]);
		buffer.writeFloat(this.color[2]);
		buffer.writeFloat(this.alpha);
		buffer.writeInt(this.maxAge);
		buffer.writeDouble(this.rotationTime);
		buffer.writeFloat(this.size);
	}

	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %.2f %.2f %.2f, %.2f %.2f %.2f %.2f %d %.2f %.2f",
				this.endX, this.endY, this.endZ, this.color[0], this.color[1], this.color[2], this.alpha,
				this.maxAge, this.rotationTime, this.size);
	}

	@Override
	public Codec<BeamParticleData> codec() {
		return BeamParticleData.CODEC;
	}
}