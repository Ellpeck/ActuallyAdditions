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

import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.entity.EntityWorm;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

public class Worm extends ItemBase {

    public Worm() {
        super();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        Level level = context.getLevel();
        BlockState state = level.getBlockState(pos);

        if (level.isClientSide || !EntityWorm.canWormify(level, context.getClickedPos(), state))
            return super.useOn(context);

        List<EntityWorm> worms = level.getEntitiesOfClass(EntityWorm.class, new AABB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 1, pos.getZ() + 2));
        if (!worms.isEmpty())
            return super.useOn(context);

        EntityWorm worm = new EntityWorm(InitEntities.ENTITY_WORM.get(), level);
        worm.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        worm.setCustomName(stack.getHoverName());
        level.addFreshEntity(worm);
        if (!context.getPlayer().isCreative())
            stack.shrink(1);

        return InteractionResult.SUCCESS;
    }

    public static void onHoe(BlockEvent.BlockToolModificationEvent event) {
        if (event.getItemAbility() == ItemAbilities.HOE_TILL && event.getLevel() instanceof Level level) {
            if (level.isClientSide || !CommonConfig.Other.WORMS.get())
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
}
