/*
 * This file ("EntityWorm.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.entity;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.DefaultFarmerBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.FarmlandWaterManager;
import net.neoforged.neoforge.common.IPlantable;
import net.neoforged.neoforge.common.ticket.AABBTicket;

public class EntityWorm extends Entity {

    public int timer;
    private AABBTicket waterTicket;

    public EntityWorm(EntityType<?> type, Level world) {
        super(type, world);
        this.setBoundingBox(new AABB(0, 0, 0, 0, 0, 0));
    }

    public static boolean canWormify(Level world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        boolean rightBlock = block instanceof FarmBlock || block == Blocks.DIRT || block instanceof GrassBlock;
        if (rightBlock) {
            BlockPos posUp = pos.above();
            BlockState stateUp = world.getBlockState(posUp);
            Block blockUp = stateUp.getBlock();
            return blockUp instanceof IPlantable || blockUp instanceof BushBlock || stateUp.canBeReplaced();
        } else {
            return false;
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        if (waterTicket != null)
            waterTicket.invalidate();

        super.remove(reason);
    }

    @Override
    protected void removeAfterChangingDimensions() {
        if (waterTicket != null)
            waterTicket.invalidate();

        super.removeAfterChangingDimensions();
    }

    @Override
    public void setPos(double pX, double pY, double pZ) {
        super.setPos(pX, pY, pZ);

        if (this.level().isClientSide)
            return;

        if (waterTicket != null)
            waterTicket.invalidate();

        AABB aabb = new AABB(getX() - 1.5, getY() - 1.5, getZ() - 1.0, getX() + 1.5, getY() + 0.5, getZ() + 1.5);
        //ActuallyAdditions.LOGGER.info("Worm AABB: " + aabb);
        waterTicket = FarmlandWaterManager.addAABBTicket(this.level(), aabb);
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            this.timer++;

            if (this.timer % 50 == 0) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos pos = BlockPos.containing(this.getX() + x, this.getY(), this.getZ() + z);
                        BlockState state = this.level().getBlockState(pos);
                        Block block = state.getBlock();
                        boolean isMiddlePose = x == 0 && z == 0;

                        if (canWormify(this.level(), pos, state)) {
                            boolean isFarmland = block instanceof FarmBlock;
                            if (!isFarmland) {
                                if (isMiddlePose || this.level().random.nextFloat() >= 0.45F) {
                                    DefaultFarmerBehavior.useHoeAt(this.level(), pos);
                                }
                            }

                            if (isFarmland && this.level().random.nextFloat() >= 0.95F) {
                                BlockPos plant = pos.above();
                                if (!this.level().isEmptyBlock(plant)) {
                                    BlockState plantState = this.level().getBlockState(plant);
                                    Block plantBlock = plantState.getBlock();

                                    if ((plantBlock instanceof BonemealableBlock || plantBlock instanceof IPlantable) && !(plantBlock instanceof GrassBlock)) {
                                        plantBlock.randomTick(plantState, (ServerLevel) this.level(), plant, this.level().random);

                                        BlockState newState = this.level().getBlockState(plant);
                                        if (newState != plantState) {
                                            this.level().levelEvent(2005, plant, 0);
                                        }
                                    }
                                }
                            }
                        } else if (isMiddlePose) {
                            this.removeAfterChangingDimensions();
                        }
                    }
                }
            }

            int dieTime = CommonConfig.Other.WORMS_DIE_TIME.get();
            if (dieTime > 0 && this.timer >= dieTime) {
                this.removeAfterChangingDimensions();
            }
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.timer = compound.getInt("Timer");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("Timer", this.timer);
    }
}
