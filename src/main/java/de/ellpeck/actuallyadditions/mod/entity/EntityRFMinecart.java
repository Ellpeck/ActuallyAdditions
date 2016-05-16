/*
 * This file ("EntityRFMinecart.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.entity;

import cofh.api.energy.EnergyStorage;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRFMinecart extends EntityMinecart{

    public EnergyStorage storage;

    public EntityRFMinecart(World world, int rfCap, int maxTransfer){
        super(world);

        this.storage = new EnergyStorage(rfCap, maxTransfer);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound){
        super.writeEntityToNBT(compound);

        this.storage.writeToNBT(compound);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound){
        super.readEntityFromNBT(compound);

        this.storage.readFromNBT(compound);
    }

    @Override
    public Type getType(){
        return Type.CHEST;
    }
}
