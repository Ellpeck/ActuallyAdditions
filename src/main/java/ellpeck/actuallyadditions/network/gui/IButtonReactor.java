package ellpeck.actuallyadditions.network.gui;

import net.minecraft.entity.player.EntityPlayer;

public interface IButtonReactor{

    /**
     * Called when a Button in a GUI is pressed
     * Gets called on the Server, sent from the Client
     * @param buttonID The button's ID
     * @param player The Player pressing it
     */
    void onButtonPressed(int buttonID, EntityPlayer player);
}
