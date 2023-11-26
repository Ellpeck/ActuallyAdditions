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

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.FullyDirectionalBlock;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.items.ItemEngineerGoggles;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserRelayUpgrade;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserWrench;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
        super(ActuallyBlocks.defaultPickProps(0));
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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEntityLaserRelay) {
            TileEntityLaserRelay relay = (TileEntityLaserRelay) tile;

            if (StackUtil.isValid(stack)) {
                if (stack.getItem() instanceof ItemLaserWrench) {
                    return ActionResultType.FAIL;
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

                    return ActionResultType.PASS;
                } else if (stack.getItem() instanceof ItemLaserRelayUpgrade) {
                    ItemStack inRelay = relay.inv.getStackInSlot(0);
                    if (!StackUtil.isValid(inRelay)) {
                        if (!world.isClientSide) {
                            if (!player.isCreative()) {
                                player.setItemInHand(hand, StackUtil.shrink(stack, 1));
                            }

                            ItemStack set = stack.copy();
                            set.setCount(1);
                            relay.inv.setStackInSlot(0, set);
                        }
                        return ActionResultType.PASS;
                    }

                }
            }

            if (player.isShiftKeyDown()) {
                ItemStack inRelay = relay.inv.getStackInSlot(0).copy();
                if (StackUtil.isValid(inRelay)) {
                    if (!world.isClientSide) {
                        relay.inv.setStackInSlot(0, ItemStack.EMPTY);

                        if (!player.inventory.add(inRelay)) {
                            player.spawnAtLocation(inRelay, 0);
                        }
                    }
                    return ActionResultType.PASS;
                }
            }

            if (relay instanceof TileEntityLaserRelayItemAdvanced) {
                return this.openGui(world, player, pos, TileEntityLaserRelayItemAdvanced.class);
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        switch (this.type) {
            case ITEM:
                return new TileEntityLaserRelayItem();
            case ITEM_WHITELIST:
                return new TileEntityLaserRelayItemAdvanced();
            case ENERGY_ADVANCED:
                return new TileEntityLaserRelayEnergyAdvanced();
            case ENERGY_EXTREME:
                return new TileEntityLaserRelayEnergyExtreme();
            case FLUIDS:
                return new TileEntityLaserRelayFluids();
            default:
                return new TileEntityLaserRelayEnergy();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(MatrixStack matrices, Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult rayCast, MainWindow resolution) {
        if (!(rayCast instanceof BlockRayTraceResult)) {
            return;
        }

        BlockPos pos = ((BlockRayTraceResult) rayCast).getBlockPos();
        if (minecraft.level != null) {
            boolean wearing = ItemEngineerGoggles.isWearing(player);
            if (wearing || StackUtil.isValid(stack)) {
                boolean compass = stack.getItem() == CommonConfig.Other.relayConfigureItem;
                if (wearing || compass || stack.getItem() instanceof ItemLaserWrench) {
                    TileEntity tile = minecraft.level.getBlockEntity(pos);
                    if (tile instanceof TileEntityLaserRelay) {
                        TileEntityLaserRelay relay = (TileEntityLaserRelay) tile;

                        String strg = relay.getExtraDisplayString();
                        minecraft.font.drawShadow(matrices, strg, resolution.getGuiScaledWidth() / 2f + 5, resolution.getGuiScaledHeight() / 2f + 5, 0xFFFFFF);

                        String expl;
                        if (compass) {
                            expl = relay.getCompassDisplayString();
                        } else {
                            expl = TextFormatting.GRAY.toString() + TextFormatting.ITALIC + I18n.get("info." + ActuallyAdditions.MODID + ".laserRelay.mode.noCompasss", I18n.get(CommonConfig.Other.relayConfigureItem.getDescriptionId()));
                        }

                        minecraft.font.draw(matrices, expl, resolution.getGuiScaledWidth() / 2f + 5, resolution.getGuiScaledHeight() / 2f + 15, 0xFFFFFF);
                    }
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
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
                TileEntity tile = world.getBlockEntity(relayPos);
                if(tile instanceof TileEntityLaserRelay) {
                    TileEntityLaserRelay relay = (TileEntityLaserRelay) tile;
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case UP:
                return Shapes.LaserRelayShapes.SHAPE_U;
            case DOWN:
                return Shapes.LaserRelayShapes.SHAPE_D;
            case EAST:
                return Shapes.LaserRelayShapes.SHAPE_E;
            case SOUTH:
                return Shapes.LaserRelayShapes.SHAPE_S;
            case WEST:
                return Shapes.LaserRelayShapes.SHAPE_W;
            default:
                return Shapes.LaserRelayShapes.SHAPE_N;
        }
    }
}
