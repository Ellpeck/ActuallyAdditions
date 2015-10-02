/*
 * This file ("IButtonReactor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.network.gui;

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
