package de.ellpeck.actuallyadditions.mod.blocks;

import net.minecraft.item.Item;

public interface IActuallyBlock {
    /**
     * Defaults to the default class for mc. Don't run this other than on setup
     *
     * @return this blocks item pair
     */
    AABlockItem createBlockItem();

    /**
     * Defines the Block Item properties for all non-custom block items.
     *
     * @return block item properties for default block item.
     * @see for implementation {@link #createBlockItem()}
     */
    Item.Properties getItemProperties();
}
