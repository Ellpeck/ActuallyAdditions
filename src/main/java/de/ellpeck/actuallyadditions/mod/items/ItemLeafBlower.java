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
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;

import java.util.ArrayList;
import java.util.Collections;

public class ItemLeafBlower extends ItemBase implements IDisplayStandItem {

    private final boolean isAdvanced;

    public ItemLeafBlower(boolean isAdvanced) {
        super(ActuallyItems.defaultProps().stacksTo(1));
        this.isAdvanced = isAdvanced;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.startUsingItem(hand);
        return ActionResult.success(player.getItemInHand(hand));
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        this.doUpdate(player.level, MathHelper.floor(player.getX()), MathHelper.floor(player.getY()), MathHelper.floor(player.getZ()), count, stack);
    }

    private boolean doUpdate(World world, int x, int y, int z, int time, ItemStack stack) {
        if (!world.isClientSide) {
            if (time <= this.getUseDuration(stack) && (this.isAdvanced || time % 3 == 0)) {
                //Breaks the Blocks
                boolean broke = this.breakStuff(world, x, y, z);
                //Plays a Minecart sounds (It really sounds like a Leaf Blower!)
                world.playSound(null, x, y, z, SoundEvents.MINECART_RIDING, SoundCategory.PLAYERS, 0.3F, 0.001F);
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
    public boolean breakStuff(World world, int x, int y, int z) {
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

                    if ((block instanceof BushBlock || block instanceof IForgeShearable) && (this.isAdvanced || block instanceof LeavesBlock)) {
                        breakPositions.add(pos);
                    }
                }
            }
        }

        if (!breakPositions.isEmpty()) {
            Collections.shuffle(breakPositions);

            BlockPos theCoord = breakPositions.get(0);
            BlockState theState = world.getBlockState(theCoord);

            world.destroyBlock(theCoord, true);
            //            theState.getBlock().dropBlockAsItem(world, theCoord, theState, 0);
            //Plays the Breaking Sound
            world.levelEvent(2001, theCoord, Block.getId(theState));

            //Deletes the Block
            world.setBlockAndUpdate(theCoord, Blocks.AIR.defaultBlockState());

            return true;
        }
        return false;
    }

    @Override
    public boolean update(ItemStack stack, TileEntity tile, int elapsedTicks) {
        return this.doUpdate(tile.getLevel(), tile.getBlockPos().getX(), tile.getBlockPos().getY(), tile.getBlockPos().getZ(), elapsedTicks, stack);
    }

    @Override
    public int getUsePerTick(ItemStack stack, TileEntity tile, int elapsedTicks) {
        return 60;
    }
}
