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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos neighbor) {
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
                    if (base.respondsToPulses()) {
                        // TODO: [port] eval what this does? :D
                        //                        world.scheduleUpdate(pos, this, this.tickRate(world));
                        // Who knows -Flanks
                        base.activateOnPulse();
                    }
                    base.setRedstonePowered(true);
                } else if (!powered && wasPowered) {
                    base.setRedstonePowered(false);
                }
            }
        }
    }

    protected boolean tryUseItemOnTank(Player player, InteractionHand hand, FluidTank tank) {
        ItemStack heldItem = player.getItemInHand(hand);
        return StackUtil.isValid(heldItem) && FluidUtil.interactWithFluidHandler(player, hand, tank);

    }

    @Override
    public void onPlace(@Nonnull BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving) {
        this.updateRedstoneState(worldIn, pos);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasTag()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileEntityBase base) {
                CompoundTag compound = stack.getOrCreateTag().getCompound("Data");
                base.readSyncableNBT(compound, TileEntityBase.NBTType.SAVE_BLOCK);
            }
        }
    }

    @Override
    public BlockState playerWillDestroy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, Player player) {
        BlockState theState = super.playerWillDestroy(world, pos, state, player);
        if (!player.isCreative()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileEntityBase && ((TileEntityBase) tile).stopFromDropping) {
                player.displayClientMessage(Component.translatable("info." + ActuallyAdditions.MODID + ".machineBroke").withStyle(ChatFormatting.RED), false);
            }
        }
        return theState;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityBase) {
            return ((TileEntityBase) tile).getComparatorStrength();
        }
        return 0;
    }

    // TODO: [port]: come back and fix this

    //    @Override
    //    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state, int fortune) {
    //        TileEntity tile = world.getTileEntity(pos);
    //        if (tile instanceof TileEntityBase) {
    //            TileEntityBase base = (TileEntityBase) tile;
    //            if (!base.stopFromDropping) {
    //                CompoundNBT data = new CompoundNBT();
    //                base.writeSyncableNBT(data, TileEntityBase.NBTType.SAVE_BLOCK);
    //
    //                //Remove unnecessarily saved default values to avoid unstackability
    //                List<String> keysToRemove = new ArrayList<>();
    //                for (String key : data.getKeySet()) {
    //                    NBTBase tag = data.getTag(key);
    //                    //Remove only ints because they are the most common ones
    //                    //Add else if below here to remove more types
    //                    if (tag instanceof NBTTagInt) {
    //                        if (((NBTTagInt) tag).getInt() == 0) {
    //                            keysToRemove.add(key);
    //                        }
    //                    }
    //                }
    //                for (String key : keysToRemove) {
    //                    data.removeTag(key);
    //                }
    //
    //                ItemStack stack = new ItemStack(this.getItemDropped(state, tile.getWorld().rand, fortune), 1, this.damageDropped(state));
    //                if (!data.isEmpty()) {
    //                    stack.setTagCompound(new CompoundNBT());
    //                    stack.getTagCompound().setTag("Data", data);
    //                }
    //
    //                drops.add(stack);
    //            }
    //        } else {
    //            super.getDrops(drops, world, pos, state, fortune);
    //        }
    //    }


    // TODO: [port]: eval

    //    @Override
    //    public EnumBlockRenderType getRenderType(BlockState state) {
    //        return EnumBlockRenderType.MODEL;
    //    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
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
