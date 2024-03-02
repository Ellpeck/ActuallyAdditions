/*
 * This file ("WeightedOre.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;

public class WeightedOre implements WeightedEntry {

    public final String name;
    public final Weight weight;

    public WeightedOre(String name, int weight) {
        this.weight = Weight.of(weight);
        this.name = name;
    }

    @Override
    public Weight getWeight() {
        return this.weight;
    }
}
