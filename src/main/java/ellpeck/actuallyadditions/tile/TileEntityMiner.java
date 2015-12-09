/*
 * This file ("TileEntityMiner.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityMiner extends TileEntityBase implements IEnergyReceiver{

    public EnergyStorage storage = new EnergyStorage(1000000);
    public static final int ENERGY_USE_PER_BLOCK = 300;

    public int layerAt;

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(this.ticksElapsed%350 == 0){
                if(this.layerAt <= 0){
                    this.layerAt = this.yCoord-1;
                }

                if(this.mine(3)){
                    this.layerAt--;
                }
            }
        }
    }

    private boolean mine(int range){
        TileEntity tileAbove = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
        boolean mined = false;
        for(int anX = -range; anX <= range; anX++){
            for(int aZ = -range; aZ <= range; aZ++){
                if(this.storage.getEnergyStored() >= ENERGY_USE_PER_BLOCK){
                    int x = this.xCoord+anX;
                    int z = this.zCoord+aZ;
                    int y = this.layerAt;

                    Block block = this.worldObj.getBlock(x, y, z);
                    int meta = this.worldObj.getBlockMetadata(x, y, z);
                    if(block != null && !block.isAir(this.worldObj, x, y, z)){
                        if(block.getHarvestLevel(meta) <= 3F && block.getHarvestLevel(meta) >= 0F){
                            ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                            drops.addAll(block.getDrops(worldObj, x, y, z, meta, 0));

                            if(tileAbove instanceof IInventory){
                                if(WorldUtil.addToInventory((IInventory)tileAbove, drops, ForgeDirection.DOWN, false)){
                                    worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block)+(meta << 12));
                                    worldObj.setBlockToAir(x, y, z);

                                    WorldUtil.addToInventory((IInventory)tileAbove, drops, ForgeDirection.DOWN, true);
                                    tileAbove.markDirty();

                                    this.storage.extractEnergy(ENERGY_USE_PER_BLOCK, false);
                                    mined = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return mined;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        this.storage.writeToNBT(compound);
        compound.setInteger("Layer", this.layerAt);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.storage.readFromNBT(compound);
        this.layerAt = compound.getInteger("Layer");
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return true;
    }
}
