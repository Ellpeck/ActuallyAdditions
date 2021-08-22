/*
 * This file ("ItemFertilizer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.misc.DispenserHandlerFertilize;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class ItemFertilizer extends ItemBase {

    public ItemFertilizer() {
        super();

        DispenserBlock.registerBehavior(this, new DispenserHandlerFertilize());
    }
    
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        if (BoneMealItem.applyBonemeal(stack, context.getLevel(), context.getClickedPos(), context.getPlayer())) {
            if (!context.getLevel().isClientSide) {
                context.getLevel().levelEvent(2005, context.getClickedPos(), 0);
            }
            return ActionResultType.SUCCESS;
        }
        return super.useOn(context);
    }
}
