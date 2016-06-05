/*
 * This file ("TileEntityLaserRelay.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketParticle;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityLaserRelay extends TileEntityBase{

    public static final int MAX_DISTANCE = 15;
    private static final float[] COLOR = new float[]{1F, 0F, 0F};
    private static final float[] COLOR_ITEM = new float[]{139F/255F, 94F/255F, 1F};

    public final boolean isItem;

    public TileEntityLaserRelay(String name, boolean isItem){
        super(name);
        this.isItem = isItem;
    }

    @Override
    public void receiveSyncCompound(NBTTagCompound compound){
        LaserRelayConnectionHandler.removeRelayFromNetwork(this.pos, this.worldObj);

        NBTTagList list = compound.getTagList("Connections", 10);
        if(!list.hasNoTags()){
            for(int i = 0; i < list.tagCount(); i++){
                LaserRelayConnectionHandler.ConnectionPair pair = LaserRelayConnectionHandler.ConnectionPair.readFromNBT(list.getCompoundTagAt(i));
                LaserRelayConnectionHandler.addConnection(pair.firstRelay, pair.secondRelay, this.worldObj);
            }
        }

        super.receiveSyncCompound(compound);
    }


    @Override
    public NBTTagCompound getUpdateTag(){
        NBTTagCompound compound = super.getUpdateTag();
        NBTTagList list = new NBTTagList();

        ConcurrentSet<LaserRelayConnectionHandler.ConnectionPair> connections = LaserRelayConnectionHandler.getConnectionsFor(this.pos, this.worldObj);
        if(connections != null && !connections.isEmpty()){
            for(LaserRelayConnectionHandler.ConnectionPair pair : connections){
                list.appendTag(pair.writeToNBT());
            }
        }

        compound.setTag("Connections", list);
        return compound;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(this.worldObj.isRemote){
            this.renderParticles();
        }
    }

    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(Util.RANDOM.nextInt(ConfigBoolValues.LESS_PARTICLES.isEnabled() ? 16 : 8) == 0){
            BlockPos thisPos = this.pos;
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getNetworkFor(thisPos, this.worldObj);
            if(network != null){
                for(LaserRelayConnectionHandler.ConnectionPair aPair : network.connections){
                    if(aPair.contains(thisPos) && PosUtil.areSamePos(thisPos, aPair.firstRelay)){
                        PacketParticle.renderParticlesFromAToB(aPair.firstRelay.getX(), aPair.firstRelay.getY(), aPair.firstRelay.getZ(), aPair.secondRelay.getX(), aPair.secondRelay.getY(), aPair.secondRelay.getZ(), ConfigBoolValues.LESS_PARTICLES.isEnabled() ? 1 : Util.RANDOM.nextInt(3)+1, 0.8F, this.isItem ? COLOR_ITEM : COLOR, 1F);
                    }
                }
            }
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        LaserRelayConnectionHandler.removeRelayFromNetwork(this.pos, this.worldObj);
    }

}
