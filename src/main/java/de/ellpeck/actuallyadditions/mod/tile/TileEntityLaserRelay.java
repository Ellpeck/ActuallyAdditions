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

import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketParticle;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.*;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class TileEntityLaserRelay extends TileEntityBase{

    public static final int MAX_DISTANCE = 15;
    private static final float[] COLOR = new float[]{1F, 0F, 0F};
    private static final float[] COLOR_ITEM = new float[]{139F/255F, 94F/255F, 1F};

    public boolean isItem;

    public TileEntityLaserRelay(String name, boolean isItem){
        super(name);
        this.isItem = isItem;
    }

    @Override
    public void receiveSyncCompound(NBTTagCompound compound){
        BlockPos thisPos = this.pos;
        if(compound != null){
            LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(thisPos);

            NBTTagList list = compound.getTagList("Connections", 10);
            for(int i = 0; i < list.tagCount(); i++){
                LaserRelayConnectionHandler.ConnectionPair pair = LaserRelayConnectionHandler.ConnectionPair.readFromNBT(list.getCompoundTagAt(i));
                LaserRelayConnectionHandler.getInstance().addConnection(pair.firstRelay, pair.secondRelay);
            }
        }
        else{
            LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(thisPos);
        }

        super.receiveSyncCompound(compound);
    }

    @Override
    public NBTTagCompound getSyncCompound(){
        NBTTagCompound compound = super.getSyncCompound();

        BlockPos thisPos = this.pos;
        ConcurrentSet<LaserRelayConnectionHandler.ConnectionPair> connections = LaserRelayConnectionHandler.getInstance().getConnectionsFor(thisPos);

        if(connections != null){
            NBTTagList list = new NBTTagList();
            for(LaserRelayConnectionHandler.ConnectionPair pair : connections){
                list.appendTag(pair.writeToNBT());
            }
            compound.setTag("Connections", list);
        }
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
        if(Util.RANDOM.nextInt(ConfigValues.lessParticles ? 16 : 8) == 0){
            BlockPos thisPos = this.pos;
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(thisPos);
            if(network != null){
                for(LaserRelayConnectionHandler.ConnectionPair aPair : network.connections){
                    if(aPair.contains(thisPos) && PosUtil.areSamePos(thisPos, aPair.firstRelay)){
                        PacketParticle.renderParticlesFromAToB(aPair.firstRelay.getX(), aPair.firstRelay.getY(), aPair.firstRelay.getZ(), aPair.secondRelay.getX(), aPair.secondRelay.getY(), aPair.secondRelay.getZ(), ConfigValues.lessParticles ? 1 : Util.RANDOM.nextInt(3)+1, 0.8F, this.isItem ? COLOR_ITEM : COLOR, 1F);
                    }
                }
            }
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        LaserRelayConnectionHandler.getInstance().removeRelayFromNetwork(this.pos);
    }

}
