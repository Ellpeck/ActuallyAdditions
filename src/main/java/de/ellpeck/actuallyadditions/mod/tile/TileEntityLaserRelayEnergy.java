/*
 * This file ("TileEntityLaserRelayEnergy.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileEntityLaserRelayEnergy extends TileEntityLaserRelay implements IEnergyReceiver{

    public final Map<EnumFacing, IEnergyReceiver> receiversAround = new HashMap<EnumFacing, IEnergyReceiver>();

    public TileEntityLaserRelayEnergy(){
        super("laserRelay", false);
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.transmitEnergy(from, maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return 0;
    }

    public int transmitEnergy(EnumFacing from, int maxTransmit, boolean simulate){
        int transmitted = 0;
        if(maxTransmit > 0){
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getNetworkFor(this.pos, this.worldObj);
            if(network != null){
                transmitted = this.transferEnergyToReceiverInNeed(from, network, Math.min(ConfigIntValues.LASER_RELAY_MAX_TRANSFER.getValue(), maxTransmit), simulate);
            }
        }
        return transmitted;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return true;
    }

    @Override
    public void saveAllHandlersAround(){
        this.receiversAround.clear();

        for(EnumFacing side : EnumFacing.values()){
            BlockPos pos = WorldUtil.getCoordsFromSide(side, this.getPos(), 0);
            TileEntity tile = this.worldObj.getTileEntity(pos);
            if(tile instanceof IEnergyReceiver && !(tile instanceof TileEntityLaserRelay)){
                this.receiversAround.put(side, (IEnergyReceiver)tile);
            }
        }
    }

    private int transferEnergyToReceiverInNeed(EnumFacing from, LaserRelayConnectionHandler.Network network, int maxTransfer, boolean simulate){
        int transmitted = 0;
        List<BlockPos> alreadyChecked = new ArrayList<BlockPos>();
        //Go through all of the connections in the network
        for(LaserRelayConnectionHandler.ConnectionPair pair : network.connections){
            //Go through both relays in the connection
            for(BlockPos relay : pair.positions){
                if(relay != null && !alreadyChecked.contains(relay)){
                    alreadyChecked.add(relay);
                    TileEntity relayTile = this.worldObj.getTileEntity(relay);
                    if(relayTile instanceof TileEntityLaserRelayEnergy){
                        for(Map.Entry<EnumFacing, IEnergyReceiver> receiver : ((TileEntityLaserRelayEnergy)relayTile).receiversAround.entrySet()){
                            if(receiver != null && receiver.getKey() != null && receiver.getValue() != null){
                                if(receiver.getKey() != from){
                                    if(receiver.getValue().canConnectEnergy(receiver.getKey().getOpposite())){
                                        //Transfer the energy (with the energy loss!)
                                        int theoreticalReceived = receiver.getValue().receiveEnergy(receiver.getKey().getOpposite(), maxTransfer-transmitted, true);
                                        //The amount of energy lost during a transfer
                                        int deduct = (int)(theoreticalReceived*((double)ConfigIntValues.LASER_RELAY_LOSS.getValue()/100));

                                        transmitted += receiver.getValue().receiveEnergy(receiver.getKey().getOpposite(), theoreticalReceived-deduct, simulate);
                                        transmitted += deduct;

                                        //If everything that could be transmitted was transmitted
                                        if(transmitted >= maxTransfer){
                                            return transmitted;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return transmitted;
    }
}
