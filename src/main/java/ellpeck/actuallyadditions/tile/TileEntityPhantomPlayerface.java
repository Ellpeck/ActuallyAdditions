package ellpeck.actuallyadditions.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class TileEntityPhantomPlayerface extends TileEntityInventoryBase{

    public String boundPlayerUUID;
    private String boundPlayerBefore;

    public TileEntityPhantomPlayerface(){
        super(0, "phantomPlayerface");
    }

    @Override
    public void updateEntity(){
        if(!this.boundPlayerBefore.equals(this.boundPlayerUUID)){
            this.boundPlayerBefore = this.boundPlayerUUID;
        }
    }

    @SuppressWarnings("unchecked")
    public InventoryPlayer getInventory(){
        if(!worldObj.isRemote && this.boundPlayerUUID != null){
            List<EntityPlayer> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
            for(EntityPlayer player : list){
                if(!player.getUniqueID().toString().equals(this.boundPlayerUUID)){
                    return player.inventory;
                }
            }
        }
        return null;
    }

    public boolean hasInventory(){
        return this.getInventory() != null;
    }

    @Override
    public int getInventoryStackLimit(){
        return this.hasInventory() ? this.getInventory().getInventoryStackLimit() : 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return this.hasInventory() && this.getInventory().isItemValidForSlot(i, stack);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i){
        return this.hasInventory() ? this.getInventory().getStackInSlotOnClosing(i) : null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack){
        if(this.hasInventory()) this.getInventory().setInventorySlotContents(i, stack);
        this.markDirty();
    }

    @Override
    public int getSizeInventory(){
        return this.hasInventory() ? this.getInventory().getSizeInventory() : 0;
    }

    @Override
    public ItemStack getStackInSlot(int i){
        return this.hasInventory() ? this.getInventory().getStackInSlot(i) : null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        return this.hasInventory() ? this.getInventory().decrStackSize(i, j) : null;
    }

    @Override
    public String getInventoryName(){
        return this.name;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side){
        if(this.hasInventory()){
            int[] theInt = new int[this.getSizeInventory()];
            for(int i = 0; i < theInt.length; i++){
                theInt[i] = i;
            }
            return theInt;
        }
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.hasInventory();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return this.hasInventory();
    }

}
