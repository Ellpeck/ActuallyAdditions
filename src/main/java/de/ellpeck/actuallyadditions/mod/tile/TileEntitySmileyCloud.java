/*
 * This file ("TileEntitySmileyCloud.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.network.gui.IStringReactor;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySmileyCloud extends TileEntityBase implements IStringReactor{

    public String name;
    private String nameBefore;

    public TileEntitySmileyCloud(){
        super("smileyCloud");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        if(this.name != null){
            compound.setString("Name", this.name);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.name = compound.getString("Name");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            boolean nameChanged = this.name != null ? !this.name.equals(this.nameBefore) : this.nameBefore != null;
            if(nameChanged && this.sendUpdateWithInterval()){
                this.nameBefore = this.name;
                this.markDirty();
            }
        }
    }

    @Override
    public void onTextReceived(String text, int textID, EntityPlayer player){
        this.name = text;
    }

    public void setStatus(boolean pinkAndFluffy){
        int meta = PosUtil.getMetadata(this.pos, this.worldObj);
        if(pinkAndFluffy){
            if(meta <= 3){
                PosUtil.setMetadata(this.pos, this.worldObj, meta+4, 2);
            }
        }
        else{
            if(meta >= 4){
                PosUtil.setMetadata(this.pos, this.worldObj, meta-4, 2);
            }
        }
    }
}
