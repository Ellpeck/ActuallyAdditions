package de.ellpeck.actuallyadditions.common.items.useables;

import de.ellpeck.actuallyadditions.common.items.ActuallyItem;
import de.ellpeck.actuallyadditions.common.items.IUseItem;
import net.minecraft.block.Block;
import net.minecraft.block.BushBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;

import java.util.ArrayList;
import java.util.Collections;

public class LeafBlowerItem extends ActuallyItem implements IUseItem {
    private final boolean isAdvanced;

    public LeafBlowerItem(boolean isAdvanced) {
        super(baseProps().maxStackSize(0));
        this.isAdvanced = isAdvanced;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        World world = player.world;
        BlockPos pos = player.getPosition();

        if (!world.isRemote) {
            if (count <= this.getUseDuration(stack) && (this.isAdvanced || count % 3 == 0)) {
                // Breaks the Blocks
                this.findAndDestroy(world, pos);

                // Plays a Minecart sounds (It really sounds like a Leaf Blower!)
                player.playSound(SoundEvents.ENTITY_MINECART_RIDING, 0.3F, 0.001F);
            }
        }
    }

    private void findAndDestroy(World world, BlockPos pos) {
        ArrayList<BlockPos> breakPositions = new ArrayList<>();

        int rangeSides = 5;
        int rangeUp = 1;
        for (int reachX = -rangeSides; reachX < rangeSides + 1; reachX++) {
            for (int reachZ = -rangeSides; reachZ < rangeSides + 1; reachZ++) {
                for (int reachY = this.isAdvanced ? -rangeSides : -rangeUp; reachY < (this.isAdvanced ? rangeSides : rangeUp) + 1; reachY++) {
                    //The current Block to break
                    BlockPos currentPos = new BlockPos(pos.getX() + reachX, pos.getY() + reachY, pos.getZ() + reachZ);
                    Block block = world.getBlockState(currentPos).getBlock();

                    if ((block instanceof BushBlock || block instanceof IForgeShearable) && (this.isAdvanced || !(world.getBlockState(currentPos).getBlock() instanceof LeavesBlock))) {
                        breakPositions.add(currentPos);
                    }
                }
            }
        }

        if (breakPositions.isEmpty()) {
            return;
        }

        Collections.shuffle(breakPositions);
        BlockPos theCoord = breakPositions.get(0);

        world.destroyBlock(theCoord, true);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean canUse(ItemStack stack) {
        return true; // todo: add energy logic
    }
}
