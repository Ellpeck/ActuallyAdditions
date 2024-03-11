/*
 * This file ("ItemCrafterOnAStick.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class ItemCrafterOnAStick extends ItemBase {
    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");

    public ItemCrafterOnAStick() {
        super(ActuallyItems.defaultNonStacking());
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (!world.isClientSide) {
            player.openMenu(new SimpleMenuProvider((windowId, playerInventory, playerEntity) -> new CraftingMenu(windowId, playerInventory, ContainerLevelAccess.create(world, player.blockPosition())){
                @Override
                public boolean stillValid(Player playerIn) {
                    return true;
                }
            }, CONTAINER_TITLE));
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
    }
}
