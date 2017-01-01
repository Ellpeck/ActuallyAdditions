/*
 * This file ("Lenses.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.lens.LensConversion;

public final class Lenses{

    public static void init(){
        ActuallyAdditionsAPI.lensDefaultConversion = new LensConversion();
        ActuallyAdditionsAPI.lensDetonation = new LensDetonation();
        ActuallyAdditionsAPI.lensDeath = new LensDeath();
        ActuallyAdditionsAPI.lensEvenMoarDeath = new LensEvenMoarDeath();
        ActuallyAdditionsAPI.lensColor = new LensColor();
        ActuallyAdditionsAPI.lensDisruption = new LensDisruption();
        ActuallyAdditionsAPI.lensDisenchanting = new LensDisenchanting();
        ActuallyAdditionsAPI.lensMining = new LensMining();
    }
}
