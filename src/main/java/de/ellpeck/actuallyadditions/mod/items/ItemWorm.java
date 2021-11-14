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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ItemWorm extends ItemBase {

    public ItemWorm() {
        super();
        MinecraftForge.EVENT_BUS.register(this);

        // TODO: [port] Not sure what this does
        //        this.addPropertyOverride(new ResourceLocation(ActuallyAdditions.MODID, "snail"), (IItemPropertyGetter) (stack, world, entity) -> "snail mail".equalsIgnoreCase(stack.getDisplayName().getString())
        //            ? 1F
        //            : 0F);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        BlockState state = context.getLevel().getBlockState(pos);
        if (EntityWorm.canWormify(context.getLevel(), context.getClickedPos(), state)) {
            List<EntityWorm> worms = context.getLevel().getEntitiesOfClass(EntityWorm.class, new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 1, pos.getZ() + 2));
            if (worms.isEmpty()) {
                if (!context.getLevel().isClientSide) {
                    EntityWorm worm = new EntityWorm(ActuallyAdditions.ENTITY_WORM.get(), context.getLevel());
                    worm.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                    worm.setCustomName(stack.getHoverName()); // TODO: WHAT DOES THIS EVEN DO?
                    context.getLevel().addFreshEntity(worm);
                    if (!context.getPlayer().isCreative()) {
                        stack.shrink(1);
                    }
                }
                return ActionResultType.SUCCESS;
            }
        }
        return super.useOn(context);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onHoe(UseHoeEvent event) {
        if (CommonConfig.OTHER.WORMS.get() && event.getResult() != Event.Result.DENY) {
            World world = event.getEntity().level;
            if (!world.isClientSide) {
                BlockPos pos = event.getContext().getClickedPos();
                if (world.isEmptyBlock(pos.above())) {
                    BlockState state = world.getBlockState(pos);
                    if (state.getBlock() == Blocks.GRASS && world.random.nextFloat() >= 0.95F) {
                        ItemStack stack = new ItemStack(ActuallyItems.WORM.get(), world.random.nextInt(2) + 1);
                        ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack);
                        world.addFreshEntity(item);
                    }
                }
            }
        }
    }
}
