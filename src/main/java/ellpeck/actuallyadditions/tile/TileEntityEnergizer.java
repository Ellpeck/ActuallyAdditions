package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityEnergizer extends TileEntityInventoryBase implements IEnergyReceiver{

    public EnergyStorage storage = new EnergyStorage(500000);

    public TileEntityEnergizer(){
        super(2, "energizer");
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(this.slots[0] != null && this.slots[0].getItem() instanceof IEnergyContainerItem && this.slots[1] == null){
                if(this.storage.getEnergyStored() > 0){
                    int received = ((IEnergyContainerItem)this.slots[0].getItem()).receiveEnergy(this.slots[0], this.storage.getEnergyStored(), false);
                    this.storage.extractEnergy(received, false);
                }

                if(((IEnergyContainerItem)this.slots[0].getItem()).getEnergyStored(this.slots[0]) >= ((IEnergyContainerItem)this.slots[0].getItem()).getMaxEnergyStored(this.slots[0])){
                    this.slots[1] = this.slots[0].copy();
                    this.slots[0].stackSize--;
                    if(this.slots[0].stackSize <= 0) this.slots[0] = null;
                }
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
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return slot == 0 && stack.getItem() instanceof IEnergyContainerItem;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == 1;
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.getEnergyStored(ForgeDirection.UNKNOWN) * i / this.getMaxEnergyStored(ForgeDirection.UNKNOWN);
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
