package ellpeck.actuallyadditions.tile;

public interface IPowerAcceptor{

    void setBlockMetadataToOn();

    void setPower(int power);

    void setItemPower(int power);

    int getItemPower();
}
