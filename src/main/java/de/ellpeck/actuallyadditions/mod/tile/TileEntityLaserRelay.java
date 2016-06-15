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

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserWrench;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler.ConnectionPair;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

public abstract class TileEntityLaserRelay extends TileEntityBase{

    public static final int MAX_DISTANCE = 15;
    private static final float[] COLOR = new float[]{1F, 0F, 0F};
    private static final float[] COLOR_ITEM = new float[]{139F/255F, 94F/255F, 1F};

    public final boolean isItem;

    private Set<ConnectionPair> tempConnectionStorage;

    private boolean hasCheckedHandlersAround;

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
                ConnectionPair pair = ConnectionPair.readFromNBT(list.getCompoundTagAt(i));
                LaserRelayConnectionHandler.addConnection(pair.positions[0], pair.positions[1], this.worldObj);
            }
        }

        super.receiveSyncCompound(compound);
    }


    @Override
    public NBTTagCompound getUpdateTag(){
        NBTTagCompound compound = super.getUpdateTag();
        NBTTagList list = new NBTTagList();

        ConcurrentSet<ConnectionPair> connections = LaserRelayConnectionHandler.getConnectionsFor(this.pos, this.worldObj);
        if(connections != null && !connections.isEmpty()){
            for(ConnectionPair pair : connections){
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
        else if(!this.hasCheckedHandlersAround){
            this.saveAllHandlersAround();
            this.hasCheckedHandlersAround = true;
        }
    }

    public void saveAllHandlersAround(){

    }

    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(Util.RANDOM.nextInt(ConfigBoolValues.LESS_PARTICLES.isEnabled() ? 16 : 8) == 0){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if(player != null){
                ItemStack stack = player.getHeldItemMainhand();
                if(!ConfigBoolValues.LASER_WRENCH_HOLDING_PARTICLES.isEnabled() || (stack != null && stack.getItem() instanceof ItemLaserWrench)){
                    LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getNetworkFor(this.pos, this.worldObj);
                    if(network != null){
                        for(ConnectionPair aPair : network.connections){
                            if(aPair.contains(this.pos) && PosUtil.areSamePos(this.pos, aPair.positions[0])){
                                AssetUtil.renderParticlesFromAToB(aPair.positions[0].getX(), aPair.positions[0].getY(), aPair.positions[0].getZ(), aPair.positions[1].getX(), aPair.positions[1].getY(), aPair.positions[1].getZ(), ConfigBoolValues.LESS_PARTICLES.isEnabled() ? 1 : Util.RANDOM.nextInt(3)+1, 0.8F, this.isItem ? COLOR_ITEM : COLOR, 1F);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        //This is because Minecraft randomly invalidates tiles on world join and then validates them again
        //We need to compensate for this so that connections don't get broken randomly
        this.tempConnectionStorage = LaserRelayConnectionHandler.getConnectionsFor(this.pos, this.worldObj);

        LaserRelayConnectionHandler.removeRelayFromNetwork(this.pos, this.worldObj);
    }

    @Override
    public void validate(){
        if(this.tempConnectionStorage != null){
            for(ConnectionPair pair : this.tempConnectionStorage){
                LaserRelayConnectionHandler.addConnection(pair.positions[0], pair.positions[1], this.worldObj);
            }
            this.tempConnectionStorage = null;
        }

        super.validate();
    }
}
