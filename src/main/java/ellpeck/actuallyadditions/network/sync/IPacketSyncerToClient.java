/*
 * This file ("IPacketSyncerToClient.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.network.sync;

public interface IPacketSyncerToClient{

    /**
     * @return The values that should be sent to the Client
     */
    int[] getValues();

    /**
     * Sets the Values on the Client
     *
     * @param values The Values
     */
    void setValues(int[] values);

    /**
     * Sends the Update
     */
    void sendUpdate();
}
