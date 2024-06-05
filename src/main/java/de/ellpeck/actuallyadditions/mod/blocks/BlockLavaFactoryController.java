/*
 * This file ("BlockLavaFactoryController.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.DirectionalBlock;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLavaFactoryController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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


public class BlockLavaFactoryController extends DirectionalBlock.Container implements IHudDisplay {

    public BlockLavaFactoryController() {
        super(ActuallyBlocks.defaultPickProps(4.5F, 20.0F));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityLavaFactoryController(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityLavaFactoryController::clientTick : TileEntityLavaFactoryController::serverTick;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult)) {
            return;
        }

        TileEntityLavaFactoryController factory = (TileEntityLavaFactoryController) minecraft.level.getBlockEntity(((BlockHitResult) rayCast).getBlockPos());
        if (factory != null) {
            int state = factory.isMultiblock();
            if (state == TileEntityLavaFactoryController.NOT_MULTI) {
                guiGraphics.drawWordWrap(minecraft.font, Component.translatable("tooltip.actuallyadditions.factory.notPart.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 5, 200, 0xFFFFFF);
            } else if (state == TileEntityLavaFactoryController.HAS_AIR || state == TileEntityLavaFactoryController.HAS_LAVA) {
                guiGraphics.drawWordWrap(minecraft.font, Component.translatable("tooltip.actuallyadditions.factory.working.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 5, 200, 0xFFFFFF);
            }
        }
    }

/*    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case EAST:
                return VoxelShapes.LavaFactoryShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.LavaFactoryShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.LavaFactoryShapes.SHAPE_W;
            default:
                return VoxelShapes.LavaFactoryShapes.SHAPE_N;
        }
    }*/
}
