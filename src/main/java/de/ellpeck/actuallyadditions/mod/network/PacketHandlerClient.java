package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.mod.particle.ParticleLaserItem;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class PacketHandlerClient {
	public static void handleTileUpdate(CompoundTag compound, IPayloadContext context) {
		Level world = Minecraft.getInstance().level;
		if (world != null) {
			BlockEntity tile = world.getBlockEntity(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));
			if (tile instanceof TileEntityBase tileBase) {
				tileBase.readSyncableNBT(compound.getCompound("Data"), world.registryAccess(), TileEntityBase.NBTType.SYNC);
			}
		}
	}

	public static void handleLaserParticle(CompoundTag compound, IPayloadContext context) {
		Minecraft mc = Minecraft.getInstance();
		ItemStack stack = ItemStack.parseOptional(context.player().registryAccess(), compound);

		double inX = compound.getDouble("InX") + 0.5;
		double inY = compound.getDouble("InY") + 0.78;
		double inZ = compound.getDouble("InZ") + 0.5;

		double outX = compound.getDouble("OutX") + 0.5;
		double outY = compound.getDouble("OutY") + 0.525;
		double outZ = compound.getDouble("OutZ") + 0.5;

		mc.level.addParticle(ParticleLaserItem.Factory.createData(stack, inX, inY, inZ),
				outX, outY, outZ, 0, 0.025, 0);
	}
}
