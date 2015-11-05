/*
 * This file ("TileEntitySmileyCloud.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.network.gui.IStringReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public class TileEntitySmileyCloud extends TileEntityBase implements IStringReactor{

    public String name;
    @SideOnly(Side.CLIENT)
    public double lastFlyHeight;
    @SideOnly(Side.CLIENT)
    public int flyHeight;
    private String nameBefore;

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        if(this.name != null){
            compound.setString("Name", this.name);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        this.name = compound.getString("Name");
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(!Objects.equals(this.name, this.nameBefore)){
                this.nameBefore = this.name;
                this.sendUpdate();
                this.markDirty();
            }
        }
    }

    @Override
    public void onTextReceived(String text, int textID, EntityPlayer player){
        this.name = text;
    }
}
