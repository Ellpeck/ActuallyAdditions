/*
 * This file ("ItemLeafBlower.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.misc.IDisplayStandItem;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;

public class ItemLeafBlower extends ItemBase implements IDisplayStandItem {

    private final boolean isAdvanced;

    public ItemLeafBlower(boolean isAdvanced) {
        super(ActuallyItems.defaultProps().stacksTo(1));
        this.isAdvanced = isAdvanced;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level world, Player player, @Nonnull InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(@Nonnull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        this.doUpdate(player.level, Mth.floor(player.getX()), Mth.floor(player.getY()), Mth.floor(player.getZ()), count, stack);
    }

    private boolean doUpdate(Level world, int x, int y, int z, int time, ItemStack stack) {
        if (!world.isClientSide) {
            if (time <= this.getUseDuration(stack) && (this.isAdvanced || time % 3 == 0)) {
                //Breaks the Blocks
                boolean broke = this.breakStuff(world, x, y, z);
                //Plays a Minecart sounds (It really sounds like a Leaf Blower!)
                world.playSound(null, x, y, z, SoundEvents.MINECART_RIDING, SoundSource.PLAYERS, 0.2F, 0.001F);
                return broke;
            }
        }
        return false;
    }

    /**
     * Breaks (harvests) Grass and Leaves around
     *
     * @param world The World
     * @param x     The X Position of the Player
     * @param y     The Y Position of the Player
     * @param z     The Z Position of the Player
     */
    public boolean breakStuff(Level world, int x, int y, int z) {
        ArrayList<BlockPos> breakPositions = new ArrayList<>();

        int rangeSides = 5;
        int rangeUp = 1;
        for (int reachX = -rangeSides; reachX < rangeSides + 1; reachX++) {
            for (int reachZ = -rangeSides; reachZ < rangeSides + 1; reachZ++) {
                for (int reachY = this.isAdvanced
                    ? -rangeSides
                    : -rangeUp; reachY < (this.isAdvanced
                    ? rangeSides
                    : rangeUp) + 1; reachY++) {
                    //The current Block to break
                    BlockPos pos = new BlockPos(x + reachX, y + reachY, z + reachZ);
                    Block block = world.getBlockState(pos).getBlock();

                    if ((block instanceof BushBlock || block instanceof IForgeShearable) && (this.isAdvanced || !(block instanceof LeavesBlock))) {
                        breakPositions.add(pos);
                    }
                }
            }
        }

        if (!breakPositions.isEmpty()) {
            Collections.shuffle(breakPositions);

            BlockPos pos = breakPositions.get(0);
            BlockState theState = world.getBlockState(pos);

            world.destroyBlock(pos, true);
            //            theState.getBlock().dropBlockAsItem(world, theCoord, theState, 0);
            //Plays the Breaking Sound
            world.levelEvent(2001, pos, Block.getId(theState));

            //Deletes the Block
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

            return true;
        }
        return false;
    }

    @Override
    public boolean update(ItemStack stack, BlockEntity tile, int elapsedTicks) {
        return this.doUpdate(tile.getLevel(), tile.getBlockPos().getX(), tile.getBlockPos().getY(), tile.getBlockPos().getZ(), elapsedTicks, stack);
    }

    @Override
    public int getUsePerTick(ItemStack stack, BlockEntity tile, int elapsedTicks) {
        return 60;
    }
}
