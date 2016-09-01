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
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.ConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TileEntityLaserRelayEnergy extends TileEntityLaserRelay implements ICustomEnergyReceiver{

    public final ConcurrentHashMap<EnumFacing, TileEntity> receiversAround = new ConcurrentHashMap<EnumFacing, TileEntity>();

    public static final int CAP = 1000;

    public TileEntityLaserRelayEnergy(String name){
        super(name, LaserType.ENERGY);
    }

    public TileEntityLaserRelayEnergy(){
        this("laserRelay");
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
        return this.getEnergyCap();
    }

    public int transmitEnergy(EnumFacing from, int maxTransmit, boolean simulate){
        int transmitted = 0;
        if(maxTransmit > 0){
            Network network = ActuallyAdditionsAPI.connectionHandler.getNetworkFor(this.pos, this.worldObj);
            if(network != null){
                transmitted = this.transferEnergyToReceiverInNeed(from, network, maxTransmit, simulate);
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
            BlockPos pos = this.getPos().offset(side);
            TileEntity tile = this.worldObj.getTileEntity(pos);
            if(tile != null && !(tile instanceof TileEntityLaserRelay)){
                if(tile instanceof IEnergyReceiver || (ActuallyAdditions.teslaLoaded && tile.hasCapability(TeslaUtil.teslaConsumer, side.getOpposite()))){
                    this.receiversAround.put(side, tile);
                }
            }
        }
    }

    private int transferEnergyToReceiverInNeed(EnumFacing from, Network network, int maxTransfer, boolean simulate){
        int transmitted = 0;
        //Keeps track of all the Laser Relays and Energy Acceptors that have been checked already to make nothing run multiple times
        List<BlockPos> alreadyChecked = new ArrayList<BlockPos>();

        List<TileEntityLaserRelayEnergy> relaysThatWork = new ArrayList<TileEntityLaserRelayEnergy>();
        int totalReceiverAmount = 0;

        for(ConnectionPair pair : network.connections){
            for(BlockPos relay : pair.positions){
                if(relay != null && !alreadyChecked.contains(relay)){
                    alreadyChecked.add(relay);
                    TileEntity relayTile = this.worldObj.getTileEntity(relay);
                    if(relayTile instanceof TileEntityLaserRelayEnergy){
                        TileEntityLaserRelayEnergy theRelay = (TileEntityLaserRelayEnergy)relayTile;
                        int amount = theRelay.receiversAround.size();
                        if(amount > 0){
                            relaysThatWork.add(theRelay);
                            totalReceiverAmount += amount;
                        }
                    }
                }
            }
        }

        if(totalReceiverAmount > 0 && !relaysThatWork.isEmpty()){
            int amountPer = maxTransfer/totalReceiverAmount;
            if(amountPer <= 0){
                amountPer = maxTransfer;
            }

            for(TileEntityLaserRelayEnergy theRelay : relaysThatWork){
                double highestLoss = Math.max(theRelay.getLossPercentage(), this.getLossPercentage());
                int lowestCap = Math.min(theRelay.getEnergyCap(), this.getEnergyCap());
                for(Map.Entry<EnumFacing, TileEntity> receiver : theRelay.receiversAround.entrySet()){
                    if(receiver != null){
                        EnumFacing side = receiver.getKey();
                        EnumFacing opp = side.getOpposite();
                        TileEntity tile = receiver.getValue();
                        if(!alreadyChecked.contains(tile.getPos())){
                            alreadyChecked.add(tile.getPos());
                            if(theRelay != this || side != from){
                                if(tile instanceof IEnergyReceiver){
                                    IEnergyReceiver iReceiver = (IEnergyReceiver)tile;
                                    if(iReceiver.canConnectEnergy(opp)){
                                        int theoreticalReceived = iReceiver.receiveEnergy(opp, Math.min(amountPer, lowestCap), true);
                                        int deduct = this.calcDeduction(theoreticalReceived, highestLoss);
                                        transmitted += iReceiver.receiveEnergy(opp, theoreticalReceived-deduct, simulate);
                                        transmitted += deduct;
                                    }
                                }
                                else if(ActuallyAdditions.teslaLoaded && tile.hasCapability(TeslaUtil.teslaConsumer, opp)){
                                    ITeslaConsumer cap = tile.getCapability(TeslaUtil.teslaConsumer, opp);
                                    if(cap != null){
                                        int theoreticalReceived = (int)cap.givePower(Math.min(amountPer, lowestCap), true);
                                        int deduct = this.calcDeduction(theoreticalReceived, highestLoss);
                                        transmitted += cap.givePower(theoreticalReceived-deduct, simulate);
                                        transmitted += deduct;
                                    }
                                }

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

        return transmitted;
    }

    private int calcDeduction(int theoreticalReceived, double highestLoss){
        return ConfigBoolValues.LASER_RELAY_LOSS.isEnabled() ? MathHelper.ceiling_double_int(theoreticalReceived*(highestLoss/100)) : 0;
    }

    public int getEnergyCap(){
        return CAP;
    }

    public double getLossPercentage(){
        return 5;
    }
}
