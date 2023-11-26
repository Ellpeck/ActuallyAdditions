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

import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ticket.AABBTicket;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityWorm extends Entity {

    public int timer;
    private AABBTicket waterTicket;

    public EntityWorm(EntityType<?> type, World world) {
        super(type, world);
        this.setBoundingBox(new AxisAlignedBB(0, 0, 0, 0, 0, 0));
    }

    public static boolean canWormify(World world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        boolean rightBlock = block instanceof FarmlandBlock || block == Blocks.DIRT || block instanceof GrassBlock;
        if (rightBlock) {
            BlockPos posUp = pos.above();
            BlockState stateUp = world.getBlockState(posUp);
            Block blockUp = stateUp.getBlock();
            return blockUp instanceof IPlantable || blockUp instanceof BushBlock || stateUp.getMaterial().isReplaceable();
        } else {
            return false;
        }
    }

    @Override
    public void remove() {
        if (waterTicket != null)
            waterTicket.invalidate();

        super.remove();
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

        if (level.isClientSide)
            return;

        if (waterTicket != null)
            waterTicket.invalidate();

        waterTicket = FarmlandWaterManager.addAABBTicket(level, new AxisAlignedBB(getX() - 2, getY() - 1.5, getZ() - 2, getX() + 2, getY(), getZ() + 2));
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide) {
            this.timer++;

            if (this.timer % 50 == 0) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos pos = new BlockPos(this.getX() + x, this.getY(), this.getZ() + z);
                        BlockState state = this.level.getBlockState(pos);
                        Block block = state.getBlock();
                        boolean isMiddlePose = x == 0 && z == 0;

                        if (canWormify(this.level, pos, state)) {
                            boolean isFarmland = block instanceof FarmlandBlock;

/*                            if (!isFarmland || state.getValue(FarmlandBlock.MOISTURE) < 7) {
                                if (isMiddlePose || this.level.random.nextFloat() >= 0.45F) {

                                    if (!isFarmland) {
                                        DefaultFarmerBehavior.useHoeAt(this.level, pos);
                                    }
                                    state = this.level.getBlockState(pos);
                                    isFarmland = state.getBlock() instanceof FarmlandBlock;

                                    if (isFarmland) {
                                        this.level.setBlock(pos, state.setValue(FarmlandBlock.MOISTURE, 7), 2);
                                    }
                                }
                            }*/

                            if (isFarmland && this.level.random.nextFloat() >= 0.95F) {
                                BlockPos plant = pos.above();
                                if (!this.level.isEmptyBlock(plant)) {
                                    BlockState plantState = this.level.getBlockState(plant);
                                    Block plantBlock = plantState.getBlock();

                                    if ((plantBlock instanceof IGrowable || plantBlock instanceof IPlantable) && !(plantBlock instanceof GrassBlock)) {
                                        plantBlock.randomTick(plantState, (ServerWorld) this.level, plant, this.level.random);

                                        BlockState newState = this.level.getBlockState(plant);
                                        if (newState != plantState) {
                                            this.level.levelEvent(2005, plant, 0);
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
    protected void readAdditionalSaveData(CompoundNBT compound) {
        this.timer = compound.getInt("Timer");
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        compound.putInt("Timer", this.timer);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
