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

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.data.LootTableGenerator;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.entity.EntityWorm;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.LootTableUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
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
        ItemStack stack = context.getItemInHand();
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

        stack.consume(1, context.getPlayer());

        return InteractionResult.SUCCESS;
    }

    public static void onHoe(BlockEvent.BlockToolModificationEvent event) {
        if (event.getItemAbility() == ItemAbilities.HOE_TILL && event.getLevel() instanceof Level level) {
            if (level.isClientSide || !CommonConfig.Other.WORMS.get())
                return;

            BlockPos pos = event.getContext().getClickedPos();
            if (level.isEmptyBlock(pos.above())) {
                BlockState state = level.getBlockState(pos);
                if (state.is(ActuallyTags.Blocks.WORM_CAN_POP)) {
                    float luck = event.getPlayer() != null ? event.getPlayer().getLuck() : 0;

                    List<ItemStack> loot_worm_items = LootTableUtil.getLootFromTable(level, LootTableGenerator.ItemWorm.WORM_DROP.location())
                            .getRandomItems(new LootParams.Builder((ServerLevel) event.getLevel())
                                    .withLuck(luck)
                                    .create(LootContextParamSets.EMPTY));

                    for (ItemStack item : loot_worm_items) {
                        level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,item));
                    }


                }
            }
        }
    }
}
