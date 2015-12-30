/*
 * This file ("Lenses.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.items.lens;

import java.util.ArrayList;

public class Lenses{

    public static ArrayList<Lens> allLenses = new ArrayList<Lens>();

    public static final Lens LENS_NONE = new LensNone().register();
    public static final Lens LENS_DETONATION = new LensDetonation().register();
    public static final Lens LENS_DEATH = new LensDeath().register();
    public static final Lens LENS_COLOR = new LensColor().register();
}
