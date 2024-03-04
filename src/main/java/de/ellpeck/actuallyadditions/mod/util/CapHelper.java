package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class CapHelper {
    @Nonnull
    public static Optional<IItemHandler> getItemHandler(@Nonnull Level level, @Nonnull BlockPos pos, @Nullable Direction side) {
        BlockState blockState = level.getBlockState(pos);
        if (blockState.hasBlockEntity()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                return Optional.ofNullable(level.getCapability(Capabilities.ItemHandler.BLOCK, pos, level.getBlockState(pos), blockEntity, side));
            }
        }
        return Optional.empty();
    }

    @Nonnull
    public static Optional<IItemHandler> getItemHandler(ItemStack stack) {
        return Optional.ofNullable(stack.getCapability(Capabilities.ItemHandler.ITEM));
    }
}
