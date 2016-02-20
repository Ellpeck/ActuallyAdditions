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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        if(!worldObj.isRemote){
            if(!Objects.equals(this.name, this.nameBefore) && this.sendUpdateWithInterval()){
                this.nameBefore = this.name;
                this.markDirty();
            }
        }
    }

    @Override
    public void onTextReceived(String text, int textID, EntityPlayer player){
        this.name = text;
    }

    public void setPinkAndFluffy(){
        if(PosUtil.getMetadata(this.pos, this.worldObj) <= 3){
            PosUtil.setMetadata(this.pos, this.worldObj, PosUtil.getMetadata(this.pos, this.worldObj)+4, 2);
        }
    }

    public void setNormalCloud(){
        if(PosUtil.getMetadata(this.pos, this.worldObj) >= 4){
            PosUtil.setMetadata(this.pos, this.worldObj, PosUtil.getMetadata(this.pos, this.worldObj)-4, 2);
        }
    }

}
