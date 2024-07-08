/*
 * This file ("ItemPhantomConnector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class ItemPhantomConnector extends ItemBase {

    public ItemPhantomConnector() {
        super(ActuallyItems.defaultNonStacking());
    }

    public static ResourceKey<Level> getStoredWorld(ItemStack stack) {
        return stack.get(ActuallyComponents.LEVEL);
    }

    public static BlockPos getStoredPosition(ItemStack stack) {
        return stack.get(ActuallyComponents.POSITION);
    }

    public static void clearStorage(ItemStack stack, DataComponentType<?>... componentTypes) {
        for (DataComponentType<?> key : componentTypes) {
            stack.remove(key);
        }
    }

    public static void storeConnection(ItemStack stack, int x, int y, int z, Level world) {
        stack.set(ActuallyComponents.POSITION, new BlockPos(x, y, z));
        stack.set(ActuallyComponents.LEVEL, world.dimension());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        if (!context.getLevel().isClientSide) {
            //Passing Data to Phantoms
            BlockPos pos = context.getClickedPos();
            BlockEntity tile = context.getLevel().getBlockEntity(pos);
            if (tile != null) {
                //Passing to Phantom
                if (tile instanceof IPhantomTile) {
                    BlockPos stored = getStoredPosition(stack);
                    if (stored != null && getStoredWorld(stack) == context.getLevel().dimension()) {
                        ((IPhantomTile) tile).setBoundPosition(stored);
                        if (tile instanceof TileEntityBase) {
                            ((TileEntityBase) tile).sendUpdate();
                        }
                        clearStorage(stack, ActuallyComponents.POSITION.get(), ActuallyComponents.LEVEL.get());
                        context.getPlayer().displayClientMessage(Component.translatable("tooltip.actuallyadditions.phantom.connected.desc"), true);
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.FAIL;
                }
            }
            //Storing Connections
            storeConnection(stack, pos.getX(), pos.getY(), pos.getZ(), context.getLevel());
            context.getPlayer().displayClientMessage(Component.translatable("tooltip.actuallyadditions.phantom.stored.desc"), true);
        }
        return InteractionResult.SUCCESS;
    }

//    @Nullable
//    @Override
//    public CompoundTag getShareTag(ItemStack stack) {
//        return new CompoundTag();
//    }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext pContext, List<Component> list, TooltipFlag advanced) {
        BlockPos coords = getStoredPosition(stack);
        if (coords != null) {
            list.add(Component.translatable("tooltip.actuallyadditions.boundTo.desc").append(":"));
            list.add(Component.literal("X: " + coords.getX()));
            list.add(Component.literal("Y: " + coords.getY()));
            list.add(Component.literal("Z: " + coords.getZ()));
            list.add(Component.translatable("tooltip.actuallyadditions.clearStorage.desc").withStyle(ChatFormatting.ITALIC));
        }
    }
}
