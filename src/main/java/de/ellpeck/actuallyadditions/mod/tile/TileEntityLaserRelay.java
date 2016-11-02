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

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserWrench;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserWrench.WrenchMode;
import de.ellpeck.actuallyadditions.mod.misc.ConnectionPair;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
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
    private static final float[] COLOR_ITEM = new float[]{43F/255F, 158F/255F, 39/255F};
    private static final float[] COLOR_FLUIDS = new float[]{139F/255F, 94F/255F, 1F};

    public final LaserType type;

    private Set<IConnectionPair> tempConnectionStorage;

    public TileEntityLaserRelay(String name, LaserType type){
        super(name);
        this.type = type;
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);

        if(type == NBTType.SYNC){
            ActuallyAdditionsAPI.connectionHandler.removeRelayFromNetwork(this.pos, this.worldObj);

            NBTTagList list = compound.getTagList("Connections", 10);
            if(!list.hasNoTags()){
                for(int i = 0; i < list.tagCount(); i++){
                    ConnectionPair pair = new ConnectionPair();
                    pair.readFromNBT(list.getCompoundTagAt(i));
                    ActuallyAdditionsAPI.connectionHandler.addConnection(pair.getPositions()[0], pair.getPositions()[1], this.type, this.worldObj, pair.doesSuppressRender());
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);

        if(type == NBTType.SYNC){
            NBTTagList list = new NBTTagList();

            ConcurrentSet<IConnectionPair> connections = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(this.pos, this.worldObj);
            if(connections != null && !connections.isEmpty()){
                for(IConnectionPair pair : connections){
                    NBTTagCompound tag = new NBTTagCompound();
                    pair.writeToNBT(tag);
                    list.appendTag(tag);
                }
            }

            compound.setTag("Connections", list);
        }
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
        if(this.worldObj.rand.nextInt(ConfigBoolValues.LESS_PARTICLES.isEnabled() ? 16 : 8) == 0){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if(player != null){
                PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
                WrenchMode mode = WrenchMode.values()[data.theCompound.getInteger("LaserWrenchMode")];
                if(mode != WrenchMode.NO_PARTICLES){
                    ItemStack stack = player.getHeldItemMainhand();
                    if(mode == WrenchMode.ALWAYS_PARTICLES || (stack != null && stack.getItem() instanceof ItemLaserWrench)){
                        Network network = ActuallyAdditionsAPI.connectionHandler.getNetworkFor(this.pos, this.worldObj);
                        if(network != null){
                            for(IConnectionPair aPair : network.connections){
                                if(!aPair.doesSuppressRender() && aPair.contains(this.pos) && this.pos.equals(aPair.getPositions()[0])){
                                    AssetUtil.renderParticlesFromAToB(aPair.getPositions()[0].getX(), aPair.getPositions()[0].getY(), aPair.getPositions()[0].getZ(), aPair.getPositions()[1].getX(), aPair.getPositions()[1].getY(), aPair.getPositions()[1].getZ(), ConfigBoolValues.LESS_PARTICLES.isEnabled() ? 1 : this.worldObj.rand.nextInt(3)+1, 0.8F, this.type == LaserType.ITEM ? COLOR_ITEM : (this.type == LaserType.FLUID ? COLOR_FLUIDS : COLOR), 1F);
                                }
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
        this.tempConnectionStorage = ActuallyAdditionsAPI.connectionHandler.getConnectionsFor(this.pos, this.worldObj);

        ActuallyAdditionsAPI.connectionHandler.removeRelayFromNetwork(this.pos, this.worldObj);
    }

    @Override
    public void validate(){
        if(this.tempConnectionStorage != null){
            for(IConnectionPair pair : this.tempConnectionStorage){
                ActuallyAdditionsAPI.connectionHandler.addConnection(pair.getPositions()[0], pair.getPositions()[1], pair.getType(), this.worldObj, pair.doesSuppressRender());
            }
            this.tempConnectionStorage = null;
        }

        super.validate();
    }
}
