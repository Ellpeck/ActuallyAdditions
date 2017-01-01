/*
 * This file ("IButtonReactor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network.gui;

import net.minecraft.entity.player.EntityPlayer;

public interface IButtonReactor{

    /**
     * Called when a Button in a GUI is pressed
     * Gets called on the Server, sent from the Client
     *
     * @param buttonID The button's ID
     * @param player   The Player pressing it
     */
    void onButtonPressed(int buttonID, EntityPlayer player);
}
