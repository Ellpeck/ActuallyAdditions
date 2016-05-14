package de.ellpeck.actuallyadditions.mod.tile;

import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.misc.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class TileEntityLaserRelayEnergy extends TileEntityLaserRelay implements IEnergyReceiver{

    public TileEntityLaserRelayEnergy(){
        super("laserRelay", false);
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate){
        return this.transmitEnergy(WorldUtil.getCoordsFromSide(from, this.pos, 0), maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from){
        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from){
        return 0;
    }

    public int transmitEnergy(BlockPos blockFrom, int maxTransmit, boolean simulate){
        int transmitted = 0;
        if(maxTransmit > 0){
            LaserRelayConnectionHandler.Network network = LaserRelayConnectionHandler.getInstance().getNetworkFor(this.pos);
            if(network != null){
                transmitted = this.transferEnergyToReceiverInNeed(blockFrom, network, Math.min(ConfigIntValues.LASER_RELAY_MAX_TRANSFER.getValue(), maxTransmit), simulate);
            }
        }
        return transmitted;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from){
        return true;
    }

    private int transferEnergyToReceiverInNeed(BlockPos energyGottenFrom, LaserRelayConnectionHandler.Network network, int maxTransfer, boolean simulate){
        int transmitted = 0;
        List<BlockPos> alreadyChecked = new ArrayList<BlockPos>();
        //Go through all of the connections in the network
        for(LaserRelayConnectionHandler.ConnectionPair pair : network.connections){
            BlockPos[] relays = new BlockPos[]{pair.firstRelay, pair.secondRelay};
            //Go through both relays in the connection
            for(BlockPos relay : relays){
                if(relay != null && !alreadyChecked.contains(relay)){
                    alreadyChecked.add(relay);
                    //Get every side of the relay
                    for(int i = 0; i <= 5; i++){
                        EnumFacing side = WorldUtil.getDirectionBySidesInOrder(i);
                        //Get the Position at the side
                        BlockPos pos = WorldUtil.getCoordsFromSide(side, relay, 0);
                        if(!PosUtil.areSamePos(pos, energyGottenFrom)){
                            TileEntity tile = this.worldObj.getTileEntity(pos);
                            if(tile instanceof IEnergyReceiver && !(tile instanceof TileEntityLaserRelay)){
                                IEnergyReceiver receiver = (IEnergyReceiver)tile;
                                if(receiver.canConnectEnergy(side.getOpposite())){
                                    //Transfer the energy (with the energy loss!)
                                    int theoreticalReceived = ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), maxTransfer-transmitted, true);
                                    //The amount of energy lost during a transfer
                                    int deduct = (int)(theoreticalReceived*((double)ConfigIntValues.LASER_RELAY_LOSS.getValue()/100));

                                    transmitted += ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), theoreticalReceived-deduct, simulate);
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
        return transmitted;
    }
}
