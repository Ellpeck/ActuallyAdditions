/*
 * This file ("IBookGui.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.booklet.internal;

public interface IBookletGui{

    void renderScaledAsciiString(String text, int x, int y, int color, boolean shadow, float scale);

    void renderSplitScaledAsciiString(String text, int x, int y, int color, boolean shadow, float scale, int length);
}
