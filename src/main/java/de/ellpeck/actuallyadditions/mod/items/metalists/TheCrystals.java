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

@Deprecated
public enum TheCrystals implements IStringSerializable {

    REDSTONE("red", 0xFF2F21, 158F / 255F, 43F / 255F, 39F / 255F),
    LAPIS("blue", 0x5171FF, 37F / 255F, 49F / 255F, 147F / 255F),
    DIAMOND("light_blue", 0x35F1FF, 99F / 255F, 135F / 255F, 210F / 255F),
    COAL("black", 0x434442, 0.2F, 0.2F, 0.2F),
    EMERALD("green", 0x44E033, 54F / 255F, 75F / 255F, 24F / 255F),
    IRON("white", 0xCEDDD4, 0.8F, 0.8F, 0.8F);

    public final String name;
    public final float[] conversionColorParticles;
    public final int clusterColor;

    TheCrystals(String name, int clusterColor, float... conversionColorParticles) {
        this.name = name;
        this.conversionColorParticles = conversionColorParticles;
        this.clusterColor = clusterColor;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
