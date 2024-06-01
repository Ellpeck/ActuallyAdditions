/*
 * This file ("BlockLaserRelay.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.items.ItemEngineerGoggles;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserRelayUpgrade;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserWrench;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayEnergy;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayEnergyAdvanced;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayEnergyExtreme;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayFluids;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItem;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItemAdvanced;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import java.util.ArrayList;
import java.util.List;


public class BlockLaserRelay extends FullyDirectionalBlock.Container implements IHudDisplay {

    //This took way too much fiddling around. I'm not good with numbers.
    //    private static final float F = 1 / 16F;
    //    private static final AxisAlignedBB AABB_UP = new AxisAlignedBB(2 * F, 0, 2 * F, 1 - 2 * F, 10 * F, 1 - 2 * F);
    //    private static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(2 * F, 6 * F, 2 * F, 1 - 2 * F, 1, 1 - 2 * F);
    //    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(2 * F, 2 * F, 6 * F, 1 - 2 * F, 1 - 2 * F, 1);
    //    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0, 2 * F, 2 * F, 1 - 6 * F, 1 - 2 * F, 1 - 2 * F);
    //    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(2 * F, 2 * F, 0, 1 - 2 * F, 1 - 2 * F, 1 - 6 * F);
    //    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(6 * F, 2 * F, 2 * F, 1, 1 - 2 * F, 1 - 2 * F);

    private final Type type;

    public BlockLaserRelay(Type type) {
        super(ActuallyBlocks.defaultPickProps());
        this.type = type;
    }

    //    @SubscribeEvent
    //    public void onBlockRightClick(PlayerInteractEvent.RightClickBlock event) {
    //        PlayerEntity player = event.getEntityPlayer();
    //        World world = event.getWorld();
    //        ItemStack stack = event.getItemStack();
    //        BlockPos pos = event.getPos();
    //
    //        if (player != null && world != null && StackUtil.isValid(stack) && pos != null) {
    //            BlockState state = event.getWorld().getBlockState(pos);
    //            if (state != null && state.getBlock() instanceof BlockLaserRelay) {
    //                if (player.isSneaking()) {
    //                    event.setUseBlock(Event.Result.ALLOW);
    //                }
    //            }
    //        }
    //    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityLaserRelay relay) {

            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemLaserWrench) {
                    return InteractionResult.FAIL;
                } else if (stack.getItem() == CommonConfig.Other.relayConfigureItem) {
                    if (!world.isClientSide) {
                        relay.onCompassAction(player);

                        Network network = relay.getNetwork();
                        if (network != null) {
                            network.changeAmount++;
                        }

                        relay.setChanged();
                        relay.sendUpdate();
                    }

                    return InteractionResult.SUCCESS;
                } else if (stack.getItem() instanceof ItemLaserRelayUpgrade) {
                    ItemStack inRelay = relay.inv.getStackInSlot(0);
                    if (inRelay.isEmpty()) {
                        if (!world.isClientSide) {
                            if (!player.isCreative()) {
                                player.setItemInHand(hand, StackUtil.shrink(stack, 1));
                            }

                            ItemStack set = stack.copy();
                            set.setCount(1);
                            relay.inv.setStackInSlot(0, set);
                        }
                        return InteractionResult.PASS;
                    }

                }
            }

            if (player.isShiftKeyDown()) {
                ItemStack inRelay = relay.inv.getStackInSlot(0).copy();
                if (!inRelay.isEmpty()) {
                    if (!world.isClientSide) {
                        relay.inv.setStackInSlot(0, ItemStack.EMPTY);

                        if (!player.getInventory().add(inRelay)) {
                            player.spawnAtLocation(inRelay, 0);
                        }
                    }
                    return InteractionResult.PASS;
                }
            }

            if (relay instanceof TileEntityLaserRelayItemAdvanced) {
                return this.openGui(world, player, pos, TileEntityLaserRelayItemAdvanced.class);
            }
        }
        return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        switch (this.type) {
            case ITEM:
                return new TileEntityLaserRelayItem(pos, state);
            case ITEM_WHITELIST:
                return new TileEntityLaserRelayItemAdvanced(pos, state);
            case ENERGY_ADVANCED:
                return new TileEntityLaserRelayEnergyAdvanced(pos, state);
            case ENERGY_EXTREME:
                return new TileEntityLaserRelayEnergyExtreme(pos, state);
            case FLUIDS:
                return new TileEntityLaserRelayFluids(pos, state);
            default:
                return new TileEntityLaserRelayEnergy(pos, state);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        switch (this.type) {
            case ITEM:
                return level.isClientSide? TileEntityLaserRelayItem::clientTick : TileEntityLaserRelayItem::serverTick;
            case ITEM_WHITELIST:
                return level.isClientSide? TileEntityLaserRelayItemAdvanced::clientTick : TileEntityLaserRelayItemAdvanced::serverTick;
            case ENERGY_ADVANCED:
                return level.isClientSide? TileEntityLaserRelayEnergyAdvanced::clientTick : TileEntityLaserRelayEnergyAdvanced::serverTick;
            case ENERGY_EXTREME:
                return level.isClientSide? TileEntityLaserRelayEnergyExtreme::clientTick : TileEntityLaserRelayEnergyExtreme::serverTick;
            case FLUIDS:
                return level.isClientSide? TileEntityLaserRelayFluids::clientTick : TileEntityLaserRelayFluids::serverTick;
            default:
                return level.isClientSide? TileEntityLaserRelayEnergy::clientTick : TileEntityLaserRelayEnergy::serverTick;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult)) {
            return;
        }

        BlockPos pos = ((BlockHitResult) rayCast).getBlockPos();
        if (minecraft.level != null) {
            boolean wearing = ItemEngineerGoggles.isWearing(player);
            if (wearing || !stack.isEmpty()) {
                boolean compass = stack.getItem() == CommonConfig.Other.relayConfigureItem;
                if (wearing || compass || stack.getItem() instanceof ItemLaserWrench) {
                    BlockEntity tile = minecraft.level.getBlockEntity(pos);
                    if (tile instanceof TileEntityLaserRelay relay) {

	                    Component strg = relay.getExtraDisplayString();
                        guiGraphics.drawString(minecraft.font, strg, (int) (resolution.getGuiScaledWidth() / 2f + 5), (int) (resolution.getGuiScaledHeight() / 2f + 5), 0xFFFFFF);

                        Component expl;
                        if (compass) {
                            expl = relay.getCompassDisplayString();
                        } else {
                            expl = Component.translatable("info.actuallyadditions.laserRelay.mode.noCompasss", Component.translatable(CommonConfig.Other.relayConfigureItem.getDescriptionId()).getString()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC);
                        }

                        guiGraphics.drawString(minecraft.font, expl, (int) (resolution.getGuiScaledWidth() / 2f + 5), (int) (resolution.getGuiScaledHeight() / 2f + 15), 0xFFFFFF);
                    }
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state != newState) {
            ConcurrentSet<IConnectionPair> connectionPairs = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(pos, world);
            ActuallyAdditionsAPI.connectionHandler.removeRelayFromNetwork(pos, world);
            List<BlockPos> relayPositions = new ArrayList<>();
            connectionPairs.forEach(pair -> {
                for (BlockPos pairPos : pair.getPositions()) {
                    if (!pos.equals(pairPos)) {
                        relayPositions.add(pairPos);
                    }
                }
            });
            //Update the connected relays to sync the changes to the client
            relayPositions.forEach(relayPos -> {
                BlockEntity tile = world.getBlockEntity(relayPos);
                if(tile instanceof TileEntityLaserRelay relay) {
	                relay.sendUpdate();
                }
            });
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }
    //
    //    @Override
    //    public void breakBlock(World world, BlockPos pos, BlockState state) {
    //        super.breakBlock(world, pos, state);
    //
    //        ActuallyAdditionsAPI.connectionHandler.removeRelayFromNetwork(pos, world);
    //    }

    public enum Type {
        ENERGY_BASIC,
        ENERGY_ADVANCED,
        ENERGY_EXTREME,
        FLUIDS,
        ITEM,
        ITEM_WHITELIST
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case UP:
                return VoxelShapes.LaserRelayShapes.SHAPE_U;
            case DOWN:
                return VoxelShapes.LaserRelayShapes.SHAPE_D;
            case EAST:
                return VoxelShapes.LaserRelayShapes.SHAPE_E;
            case SOUTH:
                return VoxelShapes.LaserRelayShapes.SHAPE_S;
            case WEST:
                return VoxelShapes.LaserRelayShapes.SHAPE_W;
            default:
                return VoxelShapes.LaserRelayShapes.SHAPE_N;
        }
    }
}
