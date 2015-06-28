package ellpeck.actuallyadditions.network.gui;

import net.minecraft.entity.player.EntityPlayer;

public interface INumberReactor{

    void onNumberReceived(int text, int textID, EntityPlayer player);
}
