/*
 * This file ("TileEntityLaserRelayItem.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemViewer.GenericItemHandlerInfo;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TileEntityLaserRelayItem extends TileEntityLaserRelay{

    public final Map<BlockPos, IItemHandler> handlersAround = new ConcurrentHashMap<BlockPos, IItemHandler>();
    public int priority;

    public TileEntityLaserRelayItem(String name){
        super(name, LaserType.ITEM);
    }

    public TileEntityLaserRelayItem(){
        this("laserRelayItem");
    }

    public int getPriority(){
        return this.priority;
    }

    public boolean isWhitelisted(ItemStack stack, boolean output){
        return true;
    }

    @Override
    public boolean shouldSaveDataOnChangeOrWorldStart(){
        return true;
    }

    @Override
    public void saveDataOnChangeOrWorldStart(){
        Map<BlockPos, IItemHandler> old = new HashMap<BlockPos, IItemHandler>(this.handlersAround);
        boolean change = false;

        this.handlersAround.clear();
        for(int i = 0; i <= 5; i++){
            EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
            BlockPos pos = this.getPos().offset(side);
            TileEntity tile = this.world.getTileEntity(pos);
            if(tile != null && !(tile instanceof TileEntityItemViewer) && !(tile instanceof TileEntityLaserRelay)){
                IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
                if(handler != null){
                    this.handlersAround.put(pos, handler);

                    IItemHandler oldHandler = old.get(pos);
                    if(oldHandler == null || !handler.equals(oldHandler)){
                        change = true;
                    }
                }
            }
        }

        if(change || old.size() != this.handlersAround.size()){
            Network network = ActuallyAdditionsAPI.connectionHandler.getNetworkFor(this.getPos(), this.getWorld());
            if(network != null){
                network.changeAmount++;
            }
        }
    }

    public void getItemHandlersInNetwork(Network network, List<GenericItemHandlerInfo> storeList){
        //Keeps track of all the Laser Relays and Item Handlers that have been checked already to make nothing run multiple times
        List<BlockPos> alreadyChecked = new ArrayList<BlockPos>();

        for(IConnectionPair pair : network.connections){
            for(BlockPos relay : pair.getPositions()){
                if(relay != null && !alreadyChecked.contains(relay)){
                    alreadyChecked.add(relay);
                    TileEntity aRelayTile = this.world.getTileEntity(relay);
                    if(aRelayTile instanceof TileEntityLaserRelayItem){
                        TileEntityLaserRelayItem relayTile = (TileEntityLaserRelayItem)aRelayTile;
                        GenericItemHandlerInfo info = new GenericItemHandlerInfo(relayTile);

                        Map<BlockPos, IItemHandler> handlersAroundTile = relayTile.handlersAround;
                        for(Map.Entry<BlockPos, IItemHandler> handler : handlersAroundTile.entrySet()){
                            if(!alreadyChecked.contains(handler.getKey())){
                                alreadyChecked.add(handler.getKey());

                                info.handlers.add(handler.getValue());
                            }
                        }

                        storeList.add(info);
                    }
                }
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("Priority", this.priority);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getExtraDisplayString(){
        return "Priority: "+TextFormatting.DARK_RED+this.getPriority()+TextFormatting.RESET;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getCompassDisplayString(){
        return TextFormatting.GREEN+"Right-Click to increase! \nSneak-Right-Click to decrease!";
    }

    @Override
    public void onCompassAction(EntityPlayer player){
        if(player.isSneaking()){
            this.priority--;
        }
        else{
            this.priority++;
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.priority = compound.getInteger("Priority");
        }
    }
}
