package de.ellpeck.actuallyadditions.common.items.lens;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.lens.LensConversion;

public final class Lenses {

    public static void init() {
        ActuallyAdditionsAPI.lensDefaultConversion = new LensConversion();
        ActuallyAdditionsAPI.lensDetonation = new LensDetonation();
        ActuallyAdditionsAPI.lensDeath = new LensDeath();
        ActuallyAdditionsAPI.lensEvenMoarDeath = new LensKiller();
        ActuallyAdditionsAPI.lensColor = new LensColor();
        ActuallyAdditionsAPI.lensDisruption = new LensDisruption();
        ActuallyAdditionsAPI.lensDisenchanting = new LensDisenchanting();
        ActuallyAdditionsAPI.lensMining = new LensMining();
    }
}
