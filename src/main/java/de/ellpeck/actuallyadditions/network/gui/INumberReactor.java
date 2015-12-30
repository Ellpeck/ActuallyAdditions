/*
 * This file ("INumberReactor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.network.gui;

import net.minecraft.entity.player.EntityPlayer;

public interface INumberReactor{

    /**
     * Called when a Number gets received after typing it in in the GUI
     *
     * @param text   The number that was sent (I don't remember why I called it text. Had a reason though.)
     * @param textID The ID (meaning the place in the GUI) of the number typed in
     * @param player The Player doing it
     */
    void onNumberReceived(int text, int textID, EntityPlayer player);
}
