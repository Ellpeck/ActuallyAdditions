package ellpeck.actuallyadditions.network.sync;

public interface IPacketSyncerToClient{

    /**
     * @return The values that should be sent to the Client
     */
    int[] getValues();

    /**
     * Sets the Values on the Client
     * @param values The Values
     */
    void setValues(int[] values);

    /**
     * Sends the Update
     */
    void sendUpdate();
}
