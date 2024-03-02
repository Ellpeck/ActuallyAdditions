/*
 * This file ("BlockPlayerInterface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPlayerInterface;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;


public class BlockPlayerInterface extends BlockContainerBase implements IHudDisplay {
    public BlockPlayerInterface() {
        super(ActuallyBlocks.defaultPickProps(4.5F, 10.0F));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityPlayerInterface(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityPlayerInterface::clientTick : TileEntityPlayerInterface::serverTick;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity player, ItemStack stack) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityPlayerInterface) {
            TileEntityPlayerInterface face = (TileEntityPlayerInterface) tile;
            if (face.connectedPlayer == null) {
                face.connectedPlayer = player.getUUID();
                face.playerName = player.getName().getString();
                face.setChanged();
                face.sendUpdate();
            }
        }

        super.setPlacedBy(world, pos, state, player, stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(PoseStack matrices, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult)) {
            return;
        }

        BlockEntity tile = minecraft.level.getBlockEntity(((BlockHitResult) rayCast).getBlockPos());
        if (tile != null) {
            if (tile instanceof TileEntityPlayerInterface) {
                TileEntityPlayerInterface face = (TileEntityPlayerInterface) tile;
                String name = face.playerName == null
                    ? "Unknown"
                    : face.playerName;
                minecraft.font.drawShadow(matrices, "Bound to: " + ChatFormatting.RED + name, resolution.getGuiScaledWidth() / 2f + 5, resolution.getGuiScaledHeight() / 2f + 5, 0xFFFFFF);
                minecraft.font.drawShadow(matrices, "UUID: " + ChatFormatting.DARK_GREEN + face.connectedPlayer, resolution.getGuiScaledWidth() / 2f + 5, resolution.getGuiScaledHeight() / 2f + 15, 0xFFFFFF);
            }
        }
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VoxelShapes.PLAYER_INTERFACE_SHAPE;
    }
}
