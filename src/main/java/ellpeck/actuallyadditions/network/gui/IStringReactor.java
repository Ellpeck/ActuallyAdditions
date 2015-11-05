/*
 * This file ("IStringReactor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.network.gui;

import net.minecraft.entity.player.EntityPlayer;

public interface IStringReactor{

    /**
     * Called when a text gets received after typing it in in the GUI
     *
     * @param text   The text that was sent
     * @param textID The ID (meaning the place in the GUI) of the text typed in
     * @param player The Player doing it
     */
    void onTextReceived(String text, int textID, EntityPlayer player);
}
