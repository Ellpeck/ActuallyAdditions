package ellpeck.actuallyadditions.network.sync;

public interface IPacketSyncerToClient{

    int[] getValues();

    void setValues(int[] values);

    void sendUpdate();
}
