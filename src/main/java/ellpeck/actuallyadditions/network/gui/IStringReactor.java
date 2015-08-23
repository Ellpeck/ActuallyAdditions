package ellpeck.actuallyadditions.network.gui;

import net.minecraft.entity.player.EntityPlayer;

public interface IStringReactor{

    /**
     * Called when a text gets received after typing it in in the GUI
     * @param text The text that was sent
     * @param textID The ID (meaning the place in the GUI) of the text typed in
     * @param player The Player doing it
     */
    void onTextReceived(String text, int textID, EntityPlayer player);
}
