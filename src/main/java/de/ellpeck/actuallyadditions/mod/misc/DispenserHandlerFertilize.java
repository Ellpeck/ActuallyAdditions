/*
 * This file ("DispenserHandlerFertilize.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

// TODO: [port][note] might not be needed anymore
public class DispenserHandlerFertilize extends DefaultDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource source, ItemStack stack) {
        Direction facing = source.state().getValue(BlockStateProperties.FACING);
        BlockPos pos = source.pos().relative(facing);

        if (BoneMealItem.growCrop(stack, source.level(), pos)) {
            source.level().levelEvent(2005, pos, 0);
        }

        return stack;
    }

}
