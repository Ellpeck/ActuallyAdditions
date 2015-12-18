/*
 * This file ("TileEntityAtomicReconstructor.java") is part of the Actually Additions Mod for Minecraft.
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
import cpw.mods.fml.common.network.NetworkRegistry;
import ellpeck.actuallyadditions.items.lens.ItemLens;
import ellpeck.actuallyadditions.items.lens.Lens;
import ellpeck.actuallyadditions.items.lens.Lenses;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.network.PacketParticle;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAtomicReconstructor extends TileEntityInventoryBase implements IEnergyReceiver, IEnergySaver, IRedstoneToggle{

    public static final int ENERGY_USE = 1000;
    public EnergyStorage storage = new EnergyStorage(3000000);
    private int currentTime;

    public TileEntityAtomicReconstructor(){
        super(1, "reconstructor");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(!this.isRedstonePowered){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        this.doWork();
                    }
                }
                else{
                    this.currentTime = 100;
                }
            }
        }

    }

    private void doWork(){
        if(this.storage.getEnergyStored() >= ENERGY_USE){
            ForgeDirection sideToManipulate = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
            //Extract energy for shooting the laser itself too!
            this.storage.extractEnergy(ENERGY_USE, false);

            //The Lens the Reconstructor currently has installed
            Lens currentLens = this.getCurrentLens();
            int distance = currentLens.getDistance();
            for(int i = 0; i < distance; i++){
                WorldPos hitBlock = WorldUtil.getCoordsFromSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord, i);

                if(currentLens.invoke(hitBlock, this)){
                    this.shootLaser(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), currentLens);
                    break;
                }
                else if(i >= distance-1){
                    this.shootLaser(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), currentLens);
                }
            }
        }
    }

    public Lens getCurrentLens(){
        if(this.slots[0] != null){
            if(this.slots[0].getItem() instanceof ItemLens){
                return ((ItemLens)this.slots[0].getItem()).getLensType();
            }
        }
        return Lenses.LENS_NONE;
    }

    private void shootLaser(int endX, int endY, int endZ, Lens currentLens){
        this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, ModUtil.MOD_ID_LOWER+":reconstructor", 0.35F, 1.0F);
        PacketHandler.theNetwork.sendToAllAround(new PacketParticle(xCoord, yCoord, zCoord, endX, endY, endZ, currentLens.getColor(), 8, 2F), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 64));
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("CurrentTime", this.currentTime);
        this.storage.writeToNBT(compound);
    }

    @Override
    public boolean shouldSyncSlots(){
        return true;
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.currentTime = compound.getInteger("CurrentTime");
        this.storage.readFromNBT(compound);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return stack != null && stack.getItem() instanceof ItemLens;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack){
        super.setInventorySlotContents(i, stack);
        this.sendUpdate();
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        this.sendUpdate();
        return super.decrStackSize(i, j);
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

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return true;
    }

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy){
        this.storage.setEnergyStored(energy);
    }

    private boolean activateOnceWithSignal;

    @Override
    public boolean toggle(){
        return this.activateOnceWithSignal = !this.activateOnceWithSignal;
    }

    @Override
    public boolean isRightMode(){
        return this.activateOnceWithSignal;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }
}
