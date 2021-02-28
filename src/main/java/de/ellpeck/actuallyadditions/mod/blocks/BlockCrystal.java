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

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;

public class BlockCrystal extends BlockBase {
    private final boolean isEmpowered;

    public BlockCrystal(boolean isEmpowered) {
        super(ActuallyBlocks.defaultPickProps(1));
        this.isEmpowered = isEmpowered;
    }

    //    public static class TheItemBlock extends ItemBlockBase {
    //
    //        public TheItemBlock(Block block) {
    //            super(block);
    //            this.setHasSubtypes(true);
    //            this.setMaxDamage(0);
    //        }
    //
    //        @Override
    //        public String getTranslationKey(ItemStack stack) {
    //            return stack.getItemDamage() >= ALL_CRYSTALS.length
    //                ? StringUtil.BUGGED_ITEM_NAME
    //                : this.getTranslationKey() + "_" + ALL_CRYSTALS[stack.getItemDamage()].name;
    //        }
    //
    //        @Override
    //        public boolean hasEffect(ItemStack stack) {
    //            return this.block instanceof BlockCrystal && ((BlockCrystal) this.block).isEmpowered;
    //        }
    //    }
}
