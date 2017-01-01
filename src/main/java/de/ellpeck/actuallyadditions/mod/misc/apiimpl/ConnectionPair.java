/*
 * This file ("ConnectionPair.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.apiimpl;

import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class ConnectionPair implements IConnectionPair{

    private final BlockPos[] positions = new BlockPos[2];
    private boolean suppressConnectionRender;
    private LaserType type;

    public ConnectionPair(){

    }

    public ConnectionPair(BlockPos firstRelay, BlockPos secondRelay, LaserType type, boolean suppressConnectionRender){
        this.positions[0] = firstRelay;
        this.positions[1] = secondRelay;
        this.suppressConnectionRender = suppressConnectionRender;
        this.type = type;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        if(compound != null){
            for(int i = 0; i < this.positions.length; i++){
                int anX = compound.getInteger("x"+i);
                int aY = compound.getInteger("y"+i);
                int aZ = compound.getInteger("z"+i);
                this.positions[i] = new BlockPos(anX, aY, aZ);
            }
            this.suppressConnectionRender = compound.getBoolean("SuppressRender");

            String typeStrg = compound.getString("Type");
            if(typeStrg != null && !typeStrg.isEmpty()){
                this.type = LaserType.valueOf(typeStrg);
            }
        }
    }

    @Override
    public BlockPos[] getPositions(){
        return this.positions;
    }

    @Override
    public boolean doesSuppressRender(){
        return this.suppressConnectionRender;
    }

    @Override
    public LaserType getType(){
        return this.type;
    }

    @Override
    public boolean contains(BlockPos relay){
        for(BlockPos position : this.positions){
            if(position != null && position.equals(relay)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return (this.positions[0] == null ? "-" : this.positions[0].toString())+" | "+(this.positions[1] == null ? "-" : this.positions[1].toString());
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        for(int i = 0; i < this.positions.length; i++){
            BlockPos relay = this.positions[i];
            compound.setInteger("x"+i, relay.getX());
            compound.setInteger("y"+i, relay.getY());
            compound.setInteger("z"+i, relay.getZ());
        }
        if(this.type != null){
            compound.setString("Type", this.type.name());
        }
        compound.setBoolean("SuppressRender", this.suppressConnectionRender);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof ConnectionPair){
            ConnectionPair pair = (ConnectionPair)obj;
            for(int i = 0; i < this.positions.length; i++){
                if(this.positions[i] == pair.positions[i] || (this.positions[i] != null && this.positions[i].equals(pair.positions[i]))){
                    return true;
                }
            }
        }
        return super.equals(obj);
    }
}
