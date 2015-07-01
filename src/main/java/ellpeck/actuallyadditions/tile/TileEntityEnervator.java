package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityEnervator extends TileEntityInventoryBase implements IEnergyProvider{

    public EnergyStorage storage = new EnergyStorage(500000);

    public TileEntityEnervator(){
        super(2, "enervator");
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(this.slots[0] != null && this.slots[0].getItem() instanceof IEnergyContainerItem && this.slots[1] == null){
                if(((IEnergyContainerItem)this.slots[0].getItem()).getEnergyStored(this.slots[0]) > 0){
                    int toReceive = ((IEnergyContainerItem)this.slots[0].getItem()).extractEnergy(this.slots[0], this.storage.getMaxEnergyStored()-this.storage.getEnergyStored(), false);
                    this.storage.receiveEnergy(toReceive, false);
                }

                if(((IEnergyContainerItem)this.slots[0].getItem()).getEnergyStored(this.slots[0]) <= 0){
                    this.slots[1] = this.slots[0].copy();
                    this.slots[0].stackSize--;
                    if(this.slots[0].stackSize <= 0) this.slots[0] = null;
                }
            }

            if(this.getEnergyStored(ForgeDirection.UNKNOWN) > 0){
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.DOWN, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.EAST, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.SOUTH, storage);
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.WEST, storage);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        this.storage.writeToNBT(compound);
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.storage.readFromNBT(compound);
        super.readFromNBT(compound);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
        return this.storage.extractEnergy(maxExtract, simulate);
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

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return slot == 0 && stack.getItem() instanceof IEnergyContainerItem;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == 1;
    }
}
