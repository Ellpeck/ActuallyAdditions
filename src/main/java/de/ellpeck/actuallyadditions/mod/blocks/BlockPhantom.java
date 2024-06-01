/*
 * This file ("BlockPhantom.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;


public class BlockPhantom extends BlockContainerBase implements IHudDisplay {

    public final Type type;

    public BlockPhantom(Type type) {
        super(ActuallyBlocks.defaultPickProps(4.5F, 10.0F));
        this.type = type;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return this.type == Type.REDSTONEFACE;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter world, BlockPos pos, Direction side) {
        if (this.type == Type.REDSTONEFACE) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileEntityPhantomRedstoneface) {
                return ((TileEntityPhantomRedstoneface) tile).providesWeak[side.ordinal()];
            }
        }
        return 0;
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter world, BlockPos pos, Direction side) {
        if (this.type == Type.REDSTONEFACE) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileEntityPhantomRedstoneface) {
                return ((TileEntityPhantomRedstoneface) tile).providesStrong[side.ordinal()];
            }
        }
        return 0;
    }

    @Override
    public boolean shouldDropInventory(Level world, BlockPos pos) {
        return this.type == Type.PLACER || this.type == Type.BREAKER;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        switch (this.type) {
            case PLACER:
                return new TileEntityPhantomPlacer(pos, state);
            case BREAKER:
                return new TileEntityPhantomBreaker(pos, state);
            case LIQUIFACE:
                return new TileEntityPhantomLiquiface(pos, state);
            case ENERGYFACE:
                return new TileEntityPhantomEnergyface(pos, state);
            case REDSTONEFACE:
                return new TileEntityPhantomRedstoneface(pos, state);
            default:
                return new TileEntityPhantomItemface(pos, state);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return switch (this.type) {
            case PLACER, BREAKER ->
                    level.isClientSide ? TileEntityPhantomPlacer::clientTick : TileEntityPhantomPlacer::serverTick;
            case LIQUIFACE ->
                    level.isClientSide ? TileEntityPhantomLiquiface::clientTick : TileEntityPhantomLiquiface::serverTick;
            case ENERGYFACE ->
                    level.isClientSide ? TileEntityPhantomEnergyface::clientTick : TileEntityPhantomEnergyface::serverTick;
            case REDSTONEFACE ->
                    level.isClientSide ? TileEntityPhantomRedstoneface::clientTick : TileEntityPhantomRedstoneface::serverTick;
            default ->
                    level.isClientSide ? TileEntityPhantomItemface::clientTick : TileEntityPhantomItemface::serverTick;
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (this.tryToggleRedstone(world, pos, player)) {
            return InteractionResult.SUCCESS;
        }
        if (!world.isClientSide) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof MenuProvider menuProvider) {
                player.openMenu(menuProvider, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult)) {
            return;
        }
        BlockPos pos = ((BlockHitResult) rayCast).getBlockPos();
        BlockEntity tile = minecraft.level.getBlockEntity(pos);
        if (tile != null) {
            if (tile instanceof IPhantomTile phantom) {
                guiGraphics.drawString(minecraft.font, Component.translatable("tooltip.actuallyadditions.blockPhantomRange.desc").append(": " + phantom.getRange()).withStyle(ChatFormatting.GOLD), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 - 40, ChatFormatting.WHITE.getColor());
                if (phantom.hasBoundPosition()) {
                    int distance = Mth.ceil(new Vec3(pos.getX(), pos.getY(), pos.getZ()).distanceTo(new Vec3(phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ())));
                    BlockState state = minecraft.level.getBlockState(phantom.getBoundPosition());
                    Block block = state.getBlock();
                    Item item = Item.byBlock(block);
                    String name = item.getName(new ItemStack(block)).getString();
                    drawWordWrap(guiGraphics, minecraft.font, Component.translatable("tooltip.actuallyadditions.phantom.blockInfo.desc", name, phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ(), distance), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 - 30, 200, 0xFFFFFF, true);

                    if (phantom.isBoundThingInRange()) {
                        drawWordWrap(guiGraphics, minecraft.font, Component.translatable("tooltip.actuallyadditions.phantom.connectedRange.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 25, 200, 0xFFFFFF, true);
                    } else {
                        drawWordWrap(guiGraphics, minecraft.font, Component.translatable("tooltip.actuallyadditions.phantom.connectedNoRange.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 25, 200, 0xFFFFFF, true);
                    }
                } else {
                    guiGraphics.drawString(minecraft.font, Component.translatable("tooltip.actuallyadditions.phantom.notConnected.desc").withStyle(ChatFormatting.RED), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 25, ChatFormatting.WHITE.getColor());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWordWrap(GuiGraphics gg, Font font, FormattedText text, int x, int y, int width, int color, boolean shadow) {
        for (FormattedCharSequence line : font.split(text, width)) {
            gg.drawString(font, line, x, y, color, shadow);
            y += font.lineHeight;
        }
    }

    public enum Type {
        ITEMFACE,
        PLACER,
        BREAKER,
        LIQUIFACE,
        ENERGYFACE,
        REDSTONEFACE
    }
}
