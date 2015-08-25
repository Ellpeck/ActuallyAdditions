package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.network.gui.IStringReactor;
import ellpeck.actuallyadditions.network.sync.IPacketSyncerToClient;
import ellpeck.actuallyadditions.network.sync.PacketSyncerToClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public class TileEntitySmileyCloud extends TileEntityBase implements IPacketSyncerToClient, IStringReactor{

    public String name;
    private String nameBefore;

    @SideOnly(Side.CLIENT)
    public double lastFlyHeight;
    @SideOnly(Side.CLIENT)
    public int flyHeight;

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(!Objects.equals(this.name, this.nameBefore)){
                this.nameBefore = this.name;
                this.sendUpdate();
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        if(this.name != null){
            compound.setString("Name", this.name);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.name = compound.getString("Name");
    }

    @Override
    public int[] getValues(){
        if(this.name != null && !this.name.isEmpty()){
            int[] chars = new int[this.name.length()];
            for(int i = 0; i < this.name.length(); i++){
                char atPlace = this.name.charAt(i);
                chars[i] = (int)atPlace;
            }
            return chars;
        }
        return new int[0];
    }

    @Override
    public void setValues(int[] values){
        if(values != null && values.length > 0){
            String newName = "";
            for(int value : values){
                newName += (char)value;
            }
            this.name = newName;
        }
        else this.name = null;
    }

    @Override
    public void sendUpdate(){
        PacketSyncerToClient.sendPacket(this);
    }

    @Override
    public void onTextReceived(String text, int textID, EntityPlayer player){
        this.name = text;
    }
}
