/*
 * This file ("EmpowererRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;

public class EmpowererRecipe{

    public ItemStack input;
    public ItemStack output;

    public ItemStack modifier1;
    public ItemStack modifier2;
    public ItemStack modifier3;
    public ItemStack modifier4;

    public int energyPerStand;
    public float[] particleColor;
    public int time;

    public EmpowererRecipe(ItemStack input, ItemStack output, ItemStack modifier1, ItemStack modifier2, ItemStack modifier3, ItemStack modifier4, int energyPerStand, int time, float[] particleColor){
        this.input = input;
        this.output = output;
        this.modifier1 = modifier1;
        this.modifier2 = modifier2;
        this.modifier3 = modifier3;
        this.modifier4 = modifier4;
        this.energyPerStand = energyPerStand;
        this.particleColor = particleColor;
        this.time = time;
    }
}
