/*
 * This file ("LensConversionRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import de.ellpeck.actuallyadditions.api.lens.LensConversion;
import net.minecraft.item.ItemStack;

public class LensConversionRecipe{

    public final int energyUse;
    public final LensConversion type;
    public ItemStack inputStack;
    public ItemStack outputStack;

    public LensConversionRecipe(ItemStack input, ItemStack output, int energyUse, LensConversion type){
        this.inputStack = input;
        this.outputStack = output;
        this.energyUse = energyUse;
        this.type = type;
    }
}
