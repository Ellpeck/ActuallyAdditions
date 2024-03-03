/*
 * This file ("BlockBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockBase extends Block {
    public BlockBase(Properties properties) {
        super(properties);
    }

    protected ItemBlockBase getItemBlock() {
        return new ItemBlockBase(this, new Item.Properties());
    }

    public boolean shouldAddCreative() {
        return true;
    }
}
