/*
 * This file ("ItemWorm.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.entity.EntityWorm;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class Worm extends ItemBase {

    public Worm() {
        super();
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        World level = context.getLevel();
        BlockState state = level.getBlockState(pos);

        if (level.isClientSide || !EntityWorm.canWormify(level, context.getClickedPos(), state))
            return super.useOn(context);

        List<EntityWorm> worms = level.getEntitiesOfClass(EntityWorm.class, new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 1, pos.getZ() + 2));
        if (!worms.isEmpty())
            return super.useOn(context);

        EntityWorm worm = new EntityWorm(ActuallyAdditions.ENTITY_WORM.get(), level);
        worm.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        worm.setCustomName(stack.getHoverName());
        level.addFreshEntity(worm);
        if (!context.getPlayer().isCreative())
            stack.shrink(1);

        return ActionResultType.SUCCESS;
    }

    public static void onHoe(UseHoeEvent event) {
        World level = event.getEntity().level;

        if (level.isClientSide || !CommonConfig.Other.WORMS.get() || event.getResult() == Event.Result.DENY)
            return;

        BlockPos pos = event.getContext().getClickedPos();
        if (level.isEmptyBlock(pos.above())) {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() == Blocks.GRASS_BLOCK && level.random.nextFloat() >= 0.95F) {
                ItemStack stack = new ItemStack(ActuallyItems.WORM.get(), level.random.nextInt(2) + 1);
                ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack);

                level.addFreshEntity(item);
            }
        }
    }
}
