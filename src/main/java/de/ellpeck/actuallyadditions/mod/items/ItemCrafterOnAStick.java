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
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

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
            openCraftingMenu(player);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
    }

    public static void openCraftingMenu(Player player) {
        if (!(player instanceof ServerPlayer))
            return;
        player.openMenu(new SimpleMenuProvider((windowId, playerInventory, playerEntity) -> new CraftingMenu(windowId, playerInventory, ContainerLevelAccess.create(player.level(), player.blockPosition())){
            @Override
            public boolean stillValid(@NotNull Player playerIn) {
                return true;
            }
        }, CONTAINER_TITLE));
    }

    public static boolean hasCrafterOnAStick(@Nonnull Player player) {
        if (player.getMainHandItem().getItem() instanceof ItemCrafterOnAStick || player.getOffhandItem().getItem() instanceof ItemCrafterOnAStick) {
            return true;
        }

        if (Util.curiosLoaded) {
            var curiosInv = CuriosApi.getCuriosInventory(player);
            if (curiosInv.isPresent()) {
                if(curiosInv.get().findFirstCurio(ItemCrafterOnAStick::isCrafterOnAStick).isPresent())
                    return true;
            }
        }

        Inventory inventory = player.getInventory();
        for (int i = 0; i <= 35; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() instanceof ItemCrafterOnAStick)
                return true;
        }

        return false;
    }

    public static boolean isCrafterOnAStick(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof ItemCrafterOnAStick;
    }
}
