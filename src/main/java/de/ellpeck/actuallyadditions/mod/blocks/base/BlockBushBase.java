/*
 * This file ("BlockBushBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;

public class BlockBushBase extends BushBlock {

    private final String name;

    public BlockBushBase(Properties properties, String name) {
        super(properties.sound(SoundType.PLANT));
        this.name = name;
    }

    protected String getBaseName() {
        return this.name;
    }

    protected ItemBlockBase getItemBlock() {
        return new ItemBlockBase(this, new Item.Properties().group(ActuallyAdditions.GROUP));
    }

    public boolean shouldAddCreative() {
        return true;
    }
}
