/*
 * This file ("BlockShockSuppressor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityShockSuppressor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockShockSuppressor extends Block implements EntityBlock {

    public BlockShockSuppressor() {
        super(ActuallyBlocks.defaultPickProps(20F, 2000.0F));
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onExplosion(ExplosionEvent.Detonate event) {
        Level world = event.getLevel();
        if (!world.isClientSide) {
            List<BlockPos> affectedBlocks = event.getAffectedBlocks();
            List<Entity> affectedEntities = event.getAffectedEntities();

            int rangeSq = TileEntityShockSuppressor.RANGE * TileEntityShockSuppressor.RANGE;
            int use = TileEntityShockSuppressor.USE_PER;

            for (TileEntityShockSuppressor suppressor : TileEntityShockSuppressor.SUPPRESSORS) {
                if (!suppressor.isRedstonePowered) {
                    BlockPos supPos = suppressor.getBlockPos();

                    List<Entity> entitiesToRemove = new ArrayList<>();
                    List<BlockPos> posesToRemove = new ArrayList<>();

                    for (BlockPos pos : affectedBlocks) {
                        if (pos.distSqr(supPos) <= rangeSq) {
                            posesToRemove.add(pos);
                        }
                    }
                    for (Entity entity : affectedEntities) {
                        if (entity.position().distanceToSqr(supPos.getX(), supPos.getY(), supPos.getZ()) <= rangeSq) {
                            entitiesToRemove.add(entity);
                        }
                    }

                    Collections.shuffle(entitiesToRemove);
                    Collections.shuffle(posesToRemove);

                    for (BlockPos pos : posesToRemove) {
                        if (suppressor.storage.getEnergyStored() >= use) {
                            suppressor.storage.extractEnergy(use, false);
                            affectedBlocks.remove(pos);
                        } else {
                            break;
                        }
                    }
                    for (Entity entity : entitiesToRemove) {
                        if (suppressor.storage.getEnergyStored() >= use) {
                            suppressor.storage.extractEnergy(use, false);
                            affectedEntities.remove(entity);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityShockSuppressor(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType) {
        return level.isClientSide? TileEntityShockSuppressor::clientTick : TileEntityShockSuppressor::serverTick;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VoxelShapes.SUPPRESSOR_SHAPE;
    }
}
