/*
 * This file ("TileEntityDirectionalBreaker.java") is part of the Actually Additions Mod for Minecraft.
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityDirectionalBreaker extends TileEntityInventoryBase implements IEnergyReceiver{

    public EnergyStorage storage = new EnergyStorage(10000);
    private int lastEnergy;

    private int currentTime;

    public TileEntityDirectionalBreaker(){
        super(9, "directionalBreaker");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                int usagePerBlock = ConfigIntValues.DIRECTIONAL_BREAKER_RF_PER_BLOCK.getValue();
                int range = ConfigIntValues.DIRECTIONAL_BREAKER_RANGE.getValue();
                if(this.storage.getEnergyStored() >= usagePerBlock*range){
                    if(this.currentTime > 0){
                        this.currentTime--;
                        if(this.currentTime <= 0){
                            ForgeDirection sideToManipulate = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));

                            for(int i = 0; i < range; i++){
                                WorldPos coordsBlock = WorldUtil.getCoordsFromSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord, i);
                                if(coordsBlock != null){
                                    Block blockToBreak = worldObj.getBlock(coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ());
                                    if(blockToBreak != null && !(blockToBreak instanceof BlockAir) && blockToBreak.getBlockHardness(worldObj, coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ()) > -1.0F){
                                        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                                        int meta = worldObj.getBlockMetadata(coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ());
                                        drops.addAll(blockToBreak.getDrops(worldObj, coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ(), meta, 0));

                                        if(WorldUtil.addToInventory(this.slots, drops, false)){
                                            worldObj.playAuxSFX(2001, coordsBlock.getX(), coordsBlock.getY(), coordsBlock.getZ(), Block.getIdFromBlock(blockToBreak)+(meta << 12));
                                            WorldUtil.breakBlockAtSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord, i);
                                            WorldUtil.addToInventory(this.slots, drops, true);
                                            this.storage.extractEnergy(usagePerBlock, false);
                                            this.markDirty();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else{
                        this.currentTime = ConfigIntValues.BREAKER_TIME_NEEDED.getValue();
                    }
                }
            }

            if(this.storage.getEnergyStored() != this.lastEnergy){
                this.lastEnergy = this.storage.getEnergyStored();
                this.sendUpdate();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        this.storage.writeToNBT(compound);
        compound.setInteger("CurrentTime", this.currentTime);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.storage.readFromNBT(compound);
        this.currentTime = compound.getInteger("CurrentTime");
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return false;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return true;
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
