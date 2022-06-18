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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class ItemCrafterOnAStick extends ItemBase {
    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.crafting");

    public ItemCrafterOnAStick() {
        super(ActuallyItems.defaultNonStacking());
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        if (!world.isClientSide) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) -> new WorkbenchContainer(windowId, playerInventory), CONTAINER_TITLE));
        }
        return new ActionResult<>(ActionResultType.SUCCESS, player.getItemInHand(hand));
    }
}
