/*
 * This file ("TheCrystals.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.metalists;

import net.minecraft.util.IStringSerializable;

public enum Crystals implements IStringSerializable {
    REDSTONE("red", 0xFF2F21, 0x9e2b27),
    LAPIS("blue", 0x5171FF, 0x253293),
    DIAMOND("light_blue", 0x35F1FF, 0x6387d2),
    COAL("black", 0x434442, 0x333333),
    EMERALD("green", 0x44E033, 0x354a18),
    IRON("white", 0xCEDDD4, 0xcccccc);

    public final String name;
    public final float[] conversionColorParticles;
    public final int clusterColor;

    Crystals(String name, int clusterColor, float... conversionColorParticles) {
        this.name = name;
        this.conversionColorParticles = conversionColorParticles;
        this.clusterColor = clusterColor;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
