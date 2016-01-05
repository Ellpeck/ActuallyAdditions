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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.mod.network.gui.IStringReactor;
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
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            if(!Objects.equals(this.name, this.nameBefore) && this.sendUpdateWithInterval()){
                this.nameBefore = this.name;
                this.markDirty();
            }
        }
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
    public void onTextReceived(String text, int textID, EntityPlayer player){
        this.name = text;
    }
}
