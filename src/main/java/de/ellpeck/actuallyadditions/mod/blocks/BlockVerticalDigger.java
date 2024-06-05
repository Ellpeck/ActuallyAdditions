/*
 * This file ("BlockMiner.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityVerticalDigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;


public class BlockVerticalDigger extends DirectionalBlock.Container implements IHudDisplay {

    public BlockVerticalDigger() {
        super(ActuallyBlocks.defaultPickProps(8F, 30F));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return this.openGui(worldIn, player, pos, TileEntityVerticalDigger.class);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityVerticalDigger(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityVerticalDigger::clientTick : TileEntityVerticalDigger::serverTick;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult)) {
            return;
        }
        BlockEntity tile = minecraft.level.getBlockEntity(((BlockHitResult) rayCast).getBlockPos());
        if (tile instanceof TileEntityVerticalDigger miner) {
	        String info = miner.checkY == 0
                ? "Done Mining!"
                : miner.checkY == -1
                ? "Calculating positions..."
                : "Mining at " + (miner.getBlockPos().getX() + miner.checkX) + ", " + miner.checkY + ", " + (miner.getBlockPos().getZ() + miner.checkZ) + ".";
            guiGraphics.drawString(minecraft.font, info, (int) (resolution.getGuiScaledWidth() / 2f + 5), (int) (resolution.getGuiScaledHeight() / 2f - 20), 0xFFFFFF);
        }
    }

/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return VoxelShapes.MinerShapes.SHAPE_N;
            case EAST:
                return VoxelShapes.MinerShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.MinerShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.MinerShapes.SHAPE_W;
            default:
                return VoxelShapes.MinerShapes.SHAPE_N;
        }
    }*/
}
