/*
 * This file ("BlockContainerBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public abstract class BlockContainerBase extends Block implements EntityBlock {
    public BlockContainerBase(Properties properties) {
        super(properties);
    }

    public InteractionResult openGui(Level world, Player player, BlockPos pos, Class<? extends MenuProvider> expectedInstance) {
        if (!world.isClientSide) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (expectedInstance.isInstance(tile)) {
                player.openMenu((MenuProvider) tile, pos);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    public ItemInteractionResult openGui2(Level world, Player player, BlockPos pos, Class<? extends MenuProvider> expectedInstance) {
        if (!world.isClientSide) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (expectedInstance.isInstance(tile)) {
                player.openMenu((MenuProvider) tile, pos);
            }
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.SUCCESS;
    }

    private void dropInventory(Level world, BlockPos position) {
        if (!world.isClientSide) {
            BlockEntity aTile = world.getBlockEntity(position);
            if (aTile instanceof TileEntityInventoryBase tile) {
                if (tile.inv.getSlots() > 0) {
                    for (int i = 0; i < tile.inv.getSlots(); i++) {
                        this.dropSlotFromInventory(i, tile, world, position);
                    }
                }
            }
        }
    }

    private void dropSlotFromInventory(int i, TileEntityInventoryBase tile, Level world, BlockPos pos) {
        ItemStack stack = tile.inv.getStackInSlot(i);
        if (stack.isEmpty()) {
            return;
        }

        float dX = world.random.nextFloat() * 0.8F + 0.1F;
        float dY = world.random.nextFloat() * 0.8F + 0.1F;
        float dZ = world.random.nextFloat() * 0.8F + 0.1F;
        ItemEntity entityItem = new ItemEntity(world, pos.getX() + dX, pos.getY() + dY, pos.getZ() + dZ, stack.copy());
        float factor = 0.05F;
        entityItem.push(world.random.nextGaussian() * factor, world.random.nextGaussian() * factor + 0.2F, world.random.nextGaussian() * factor);
        world.addFreshEntity(entityItem);
    }

    public boolean tryToggleRedstone(Level world, BlockPos pos, Player player) {
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() == CommonConfig.Other.redstoneConfigureItem) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileEntityBase base) {
                if (!world.isClientSide && base.isRedstoneToggle()) {
                    base.isPulseMode = !base.isPulseMode;
                    base.setChanged();
                    base.sendUpdate();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick(@Nonnull BlockState state, ServerLevel world, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        if (!world.isClientSide) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileEntityBase base) {
                if (base.respondsToPulses()) {
                    base.activateOnPulse();
                }
            }
        }
    }

    public void neighborsChangedCustom(Level world, BlockPos pos) {
        this.updateRedstoneState(world, pos);

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityBase base) {
            if (base.shouldSaveDataOnChangeOrWorldStart()) {
                base.saveDataOnChangeOrWorldStart();
            }
        }
    }

    @Override //TODO do we need this?
    public void neighborChanged(@Nonnull BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos, boolean isMoving) {
        this.neighborsChangedCustom(worldIn, pos);
    }

    @Override
    public void onNeighborChange(@Nonnull BlockState state, @Nonnull LevelReader world, @Nonnull BlockPos pos, @Nonnull BlockPos neighbor) {
        super.onNeighborChange(state, world, pos, neighbor);
        if (world instanceof Level) { //TODO what?
            this.neighborsChangedCustom((Level) world, pos);
        }
    }

    public void updateRedstoneState(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileEntityBase base) {
                boolean powered = world.getBestNeighborSignal(pos) > 0;
                boolean wasPowered = base.isRedstonePowered;
                if (powered && !wasPowered) {
                    base.setRedstonePowered(true);
                    if (base.respondsToPulses()) {
                        base.activateOnPulse();
                    }
                } else if (!powered && wasPowered) {
                    base.setRedstonePowered(false);
                }
            }
        }
    }

    protected boolean tryUseItemOnTank(Player player, InteractionHand hand, FluidTank tank) {
        ItemStack heldItem = player.getItemInHand(hand);
        return !heldItem.isEmpty() && FluidUtil.interactWithFluidHandler(player, hand, tank);

    }

    @Override
    public void onPlace(@Nonnull BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving) {
        this.updateRedstoneState(worldIn, pos);
    }

    @Nonnull
    @Override
    public BlockState playerWillDestroy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
        BlockState theState = super.playerWillDestroy(world, pos, state, player);
        if (!player.isCreative() && world.getBlockEntity(pos) instanceof TileEntityBase tileBase && tileBase.stopFromDropping) {
            if (!world.isClientSide)
                player.displayClientMessage(Component.translatable("info.actuallyadditions.machineBroke").withStyle(ChatFormatting.RED), false);
            return Blocks.AIR.defaultBlockState();
        }
        return theState;
    }

    @Override
    public boolean hasAnalogOutputSignal(@Nonnull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@Nonnull BlockState state, Level world, @Nonnull BlockPos pos) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityBase) {
            return ((TileEntityBase) tile).getComparatorStrength();
        }
        return 0;
    }

    @Nonnull
    @Override
    public RenderShape getRenderShape(@Nonnull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            if (this.shouldDropInventory(world, pos)) {
                this.dropInventory(world, pos);
            }
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    public boolean shouldDropInventory(Level world, BlockPos pos) {
        return true;
    }
}
