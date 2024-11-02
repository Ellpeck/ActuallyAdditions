/*
 * This file ("BlockCrystal.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;

public class BlockCrystal extends ActuallyBlock {
    private final boolean isEmpowered;

    public BlockCrystal(boolean isEmpowered) {
        super(ActuallyBlocks.defaultPickProps().sound(SoundType.AMETHYST));
        this.isEmpowered = isEmpowered;
    }

    @Override
    public AABlockItem createBlockItem() {
        return new AABlockItem(this, getItemProperties()) {
            @Override
            public boolean isFoil(ItemStack stack) {
                return isEmpowered;
            }
        };
    }
}
