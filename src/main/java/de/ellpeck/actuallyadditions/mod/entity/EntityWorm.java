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

import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.DefaultFarmerBehavior;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class EntityWorm extends Entity {

    public int timer;

    public EntityWorm(World world) {
        super(world);
        this.setEntityBoundingBox(new AxisAlignedBB(0, 0, 0, 0, 0, 0));
    }

    public static boolean canWormify(World world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        boolean rightBlock = block instanceof BlockFarmland || block instanceof BlockDirt || block instanceof BlockGrass;
        if (rightBlock) {
            BlockPos posUp = pos.above();
            BlockState stateUp = world.getBlockState(posUp);
            Block blockUp = stateUp.getBlock();
            return blockUp instanceof IPlantable || blockUp instanceof BlockBush || blockUp.canBeReplaced(world, posUp);
        } else {
            return false;
        }
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(CompoundNBT compound) {
        this.timer = compound.getInt("Timer");
    }

    @Override
    protected void writeEntityToNBT(CompoundNBT compound) {
        compound.putInt("Timer", this.timer);
    }

    @Override
    public void onUpdate() {
        this.onEntityUpdate();
    }

    @Override
    public void onEntityUpdate() {
        if (!this.level.isClientSide) {
            this.timer++;

            if (this.timer % 50 == 0) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos pos = new BlockPos(this.posX + x, this.posY, this.posZ + z);
                        BlockState state = this.level.getBlockState(pos);
                        Block block = state.getBlock();
                        boolean isMiddlePose = x == 0 && z == 0;

                        if (canWormify(this.level, pos, state)) {
                            boolean isFarmland = block instanceof BlockFarmland;

                            if (!isFarmland || state.getValue(BlockFarmland.MOISTURE) < 7) {
                                if (isMiddlePose || this.level.random.nextFloat() >= 0.45F) {

                                    if (!isFarmland) {
                                        DefaultFarmerBehavior.useHoeAt(this.level, pos);
                                    }
                                    state = this.level.getBlockState(pos);
                                    isFarmland = state.getBlock() instanceof BlockFarmland;

                                    if (isFarmland) {
                                        this.level.setBlock(pos, state.withProperty(BlockFarmland.MOISTURE, 7), 2);
                                    }
                                }
                            }

                            if (isFarmland && this.level.random.nextFloat() >= 0.95F) {
                                BlockPos plant = pos.above();
                                if (!this.level.isEmptyBlock(plant)) {
                                    BlockState plantState = this.level.getBlockState(plant);
                                    Block plantBlock = plantState.getBlock();

                                    if ((plantBlock instanceof IGrowable || plantBlock instanceof IPlantable) && !(plantBlock instanceof BlockGrass)) {
                                        plantBlock.updateTick(this.level, plant, plantState, this.level.random);

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

            int dieTime = ConfigIntValues.WORMS_DIE_TIME.getValue();
            if (dieTime > 0 && this.timer >= dieTime) {
                this.removeAfterChangingDimensions();
            }
        }
    }
}
