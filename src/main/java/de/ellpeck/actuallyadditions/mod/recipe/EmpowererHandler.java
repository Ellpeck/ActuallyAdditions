/*
 * This file ("EmpowererHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.recipe;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import net.minecraft.item.ItemStack;

public final class EmpowererHandler{

    public static void init(){
        ItemStack m = new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal());
        for(int i = 0; i < TheCrystals.values().length; i++){
            float[] color = TheCrystals.values()[i].conversionColorParticles;
            ActuallyAdditionsAPI.addEmpowererRecipe(new ItemStack(InitItems.itemCrystal, 1, i), new ItemStack(InitItems.itemCrystalEmpowered, 1, i), m, m, m, m, 50000, color);
            ActuallyAdditionsAPI.addEmpowererRecipe(new ItemStack(InitBlocks.blockCrystal, 1, i), new ItemStack(InitBlocks.blockCrystalEmpowered, 1, i), m, m, m, m, 500000, color);
        }
    }

}
