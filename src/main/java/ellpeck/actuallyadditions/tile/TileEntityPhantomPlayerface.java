package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Objects;

public class TileEntityPhantomPlayerface extends TileEntityInventoryBase implements IEnergyReceiver{

    public String boundPlayerUUID;
    private String boundPlayerBefore;

    private EnergyStorage storage = new EnergyStorage(250000);
    private boolean hadEnoughBefore;
    private final int energyUsePerTick = 1000;

    public TileEntityPhantomPlayerface(){
        super(0, "phantomPlayerface");
    }

    @Override
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(!Objects.equals(this.boundPlayerUUID, this.boundPlayerBefore) || this.hadEnoughBefore != this.storage.getEnergyStored() >= energyUsePerTick){
                this.boundPlayerBefore = this.boundPlayerUUID;
                this.hadEnoughBefore = this.storage.getEnergyStored() >= energyUsePerTick;
                WorldUtil.updateTileAndTilesAround(this);
            }

            if(this.hasEnoughEnergy() && this.hasInventory()){
                this.storage.extractEnergy(this.energyUsePerTick, false);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public InventoryPlayer getInventory(){
        if(!worldObj.isRemote && this.boundPlayerUUID != null){
            List<EntityPlayer> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
            for(EntityPlayer player : list){
                if(player.getUniqueID().toString().equals(this.boundPlayerUUID)){
                    return player.inventory;
                }
            }
        }
        return null;
    }

    public boolean hasInventory(){
        return this.getInventory() != null;
    }

    private boolean hasEnoughEnergy(){
        return this.storage.getEnergyStored() >= this.energyUsePerTick;
    }

    @Override
    public int getInventoryStackLimit(){
        if(this.hasInventory() && this.hasEnoughEnergy()){
            return this.getInventory().getInventoryStackLimit();
        }
        return 0;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return this.hasInventory() && this.hasEnoughEnergy() && this.getInventory().isItemValidForSlot(i, stack);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i){
        if(this.hasInventory() && this.hasEnoughEnergy()){
            return this.getInventory().getStackInSlotOnClosing(i);
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack){
        if(this.hasInventory() && this.hasEnoughEnergy()){
            this.getInventory().setInventorySlotContents(i, stack);
            this.markDirty();
        }
    }

    @Override
    public int getSizeInventory(){
        if(this.hasInventory() && this.hasEnoughEnergy()){
            return this.getInventory().getSizeInventory();
        }
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int i){
        if(this.hasInventory() && this.hasEnoughEnergy()){
            return this.getInventory().getStackInSlot(i);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        if(this.hasInventory() && this.hasEnoughEnergy()){
            return this.getInventory().decrStackSize(i, j);
        }
        return null;
    }

    @Override
    public String getInventoryName(){
        return this.name;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side){
        if(this.hasInventory() && this.hasEnoughEnergy()){
            int[] theInt = new int[this.getInventory().getSizeInventory()];
            for(int i = 0; i < theInt.length; i++){
                theInt[i] = i;
            }
            return theInt;
        }
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.hasInventory() && this.hasEnoughEnergy();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return this.hasInventory() && this.hasEnoughEnergy();
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
