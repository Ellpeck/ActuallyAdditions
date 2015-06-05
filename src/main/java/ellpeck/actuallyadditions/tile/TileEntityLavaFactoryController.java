package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import ellpeck.actuallyadditions.blocks.BlockMisc;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLavaFactoryController extends TileEntityBase implements IEnergyReceiver{

    public EnergyStorage storage = new EnergyStorage(3000000, energyNeededToProduceLava*2);

    public static int energyNeededToProduceLava = ConfigIntValues.LAVA_FACTORY_ENERGY_USED.getValue();

    private final int maxWorkTime = ConfigIntValues.LAVA_FACTORY_TIME.getValue();
    private int currentWorkTime;

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            int isMulti = this.isMultiblock();

            if(isMulti == HAS_AIR && this.storage.getEnergyStored() >= energyNeededToProduceLava){
                this.currentWorkTime++;
                if(this.currentWorkTime >= this.maxWorkTime){
                    this.currentWorkTime = 0;
                    worldObj.setBlock(xCoord, yCoord+1, zCoord, Blocks.lava);
                    this.storage.extractEnergy(energyNeededToProduceLava, false);
                }
            }
            else this.currentWorkTime = 0;
        }
    }

    public int isMultiblock(){
        Block blockNorth = worldObj.getBlock(xCoord+ForgeDirection.NORTH.offsetX, yCoord+1, zCoord+ForgeDirection.NORTH.offsetZ);
        Block blockEast = worldObj.getBlock(xCoord+ForgeDirection.EAST.offsetX, yCoord+1, zCoord+ForgeDirection.EAST.offsetZ);
        Block blockSouth = worldObj.getBlock(xCoord+ForgeDirection.SOUTH.offsetX, yCoord+1, zCoord+ForgeDirection.SOUTH.offsetZ);
        Block blockWest = worldObj.getBlock(xCoord+ForgeDirection.WEST.offsetX, yCoord+1, zCoord+ForgeDirection.WEST.offsetZ);
        int metaNorth = worldObj.getBlockMetadata(xCoord+ForgeDirection.NORTH.offsetX, yCoord+1, zCoord+ForgeDirection.NORTH.offsetZ);
        int metaEast = worldObj.getBlockMetadata(xCoord+ForgeDirection.EAST.offsetX, yCoord+1, zCoord+ForgeDirection.EAST.offsetZ);
        int metaSouth = worldObj.getBlockMetadata(xCoord+ForgeDirection.SOUTH.offsetX, yCoord+1, zCoord+ForgeDirection.SOUTH.offsetZ);
        int metaWest = worldObj.getBlockMetadata(xCoord+ForgeDirection.WEST.offsetX, yCoord+1, zCoord+ForgeDirection.WEST.offsetZ);
        int metaNeeded = TheMiscBlocks.LAVA_FACTORY_CASE.ordinal();

        if(blockNorth instanceof BlockMisc && blockEast instanceof BlockMisc && blockSouth instanceof BlockMisc && blockWest instanceof BlockMisc){
            if(metaNorth == metaNeeded && metaEast == metaNeeded && metaSouth == metaNeeded && metaWest == metaNeeded){
                if(worldObj.getBlock(xCoord, yCoord+1, zCoord) == Blocks.lava || worldObj.getBlock(xCoord, yCoord+1, zCoord) == Blocks.flowing_lava){
                    return HAS_LAVA;
                }
                if(worldObj.getBlock(xCoord, yCoord+1, zCoord) == null || worldObj.getBlock(xCoord, yCoord+1, zCoord) instanceof BlockAir){
                    return HAS_AIR;
                }
            }
        }
        return NOT_MULTI;
    }

    public static final int NOT_MULTI = 0;
    public static final int HAS_LAVA = 1;
    public static final int HAS_AIR = 2;

    @Override
    public void writeToNBT(NBTTagCompound compound){
        this.storage.writeToNBT(compound);
        compound.setInteger("WorkTime", this.currentWorkTime);
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.storage.readFromNBT(compound);
        this.currentWorkTime = compound.getInteger("WorkTime");
        super.readFromNBT(compound);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxExtract, boolean simulate){
        return from != ForgeDirection.UP ? this.storage.receiveEnergy(maxExtract, simulate) : 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return from != ForgeDirection.UP ? this.storage.getEnergyStored() : 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return from != ForgeDirection.UP ? this.storage.getMaxEnergyStored() : 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return from != ForgeDirection.UP;
    }
}
