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

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.blocks.blockhuds.IBlockHud;
import de.ellpeck.actuallyadditions.mod.blocks.blockhuds.PhantomHud;
import de.ellpeck.actuallyadditions.mod.tile.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;


public class BlockPhantom extends BlockContainerBase implements IHudDisplay {
    private static final IBlockHud HUD = new PhantomHud();

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
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHitResult) {
        if (this.tryToggleRedstone(world, pos, player)) {
            return ItemInteractionResult.SUCCESS;
        }
        if (!world.isClientSide) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof MenuProvider menuProvider) {
                player.openMenu(menuProvider, pos);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public IBlockHud getHud() {
        return HUD;
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
