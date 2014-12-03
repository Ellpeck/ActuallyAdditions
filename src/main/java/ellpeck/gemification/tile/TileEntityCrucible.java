package ellpeck.gemification.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.gemification.GemType;
import ellpeck.gemification.Util;
import ellpeck.gemification.blocks.InitBlocks;
import ellpeck.gemification.crafting.CrucibleCraftingManager;
import ellpeck.gemification.items.ItemGem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrucible extends TileEntity implements ISidedInventory {

    /**
     * 0-3: Inputs
     * 4: Main Input
     * 5-8: Inputs
     * 9: Water
     * 10: Gem
     * 11: Output
     */
    public ItemStack slots[] = new ItemStack[12];
    public final int slotOutput = 11;
    public final int slotMainInput = 4;
    public final int slotWater = 9;
    public final int slotSmeltGem = 10;

    /*
    Variables that need to be saved to and loaded from NBT
     */
    public GemType currentFluid = Util.fluidNone;
    public int currentFluidID;
    public int currentProcessTime;
    public int processTimeNeeded;

    private boolean isCrafting = false;
    public static ItemStack output;

    public void updateEntity(){
        if(!worldObj.isRemote){
            this.craft();
            this.addWaterByWaterSlot();
            this.colorGemWater();
            this.currentFluidID = this.currentFluid.ID;
        }
    }

    @SuppressWarnings("static-access")
    public void craft(){
        if(!this.isCrafting){
            this.output = CrucibleCraftingManager.instance.getCraftingResult(slots, 0, 8, currentFluid);
            if (output != null) {
                this.processTimeNeeded = CrucibleCraftingManager.instance.getProcessTimeNeeded(output);
                for(int i = 0; i <= 8; i++){
                    this.slots[i].stackSize--;
                    if (this.slots[i].stackSize == 0){
                        this.slots[i] = slots[i].getItem().getContainerItem(slots[i]);
                    }
                }
                this.currentFluid = Util.fluidNone;
                this.isCrafting = true;
            }
        }
        if(this.isCrafting){
            this.currentProcessTime++;
            if(this.currentProcessTime >= this.processTimeNeeded){
                if(this.slots[slotOutput] == null) this.slots[slotOutput] = output.copy();
                else if(this.slots[slotOutput].getItem() == output.getItem()) this.slots[slotOutput].stackSize += output.stackSize;
                this.output = null;
                this.currentProcessTime = 0;
                this.processTimeNeeded = 0;
                this.isCrafting = false;
            }
        }
    }

    public void colorGemWater(){
        ItemStack stack = this.slots[slotSmeltGem];
        if(stack != null && stack.getItem() instanceof ItemGem){
            if(this.currentFluid == Util.fluidWater) {
                this.currentFluid = Util.gemList.get(stack.getItemDamage());
                stack.stackSize--;
                if(stack.stackSize == 0) this.slots[slotSmeltGem] = stack.getItem().getContainerItem(stack);
            }
        }
    }

    public void addWaterByWaterSlot(){
        if(this.slots[this.slotWater] != null && this.slots[this.slotWater].getItem() == Items.water_bucket && this.currentFluid == Util.fluidNone){
            this.currentFluid = Util.fluidWater;
            this.slots[this.slotWater] = new ItemStack(Items.bucket);
        }
    }

    public int getSizeInventory() {
        return slots.length;
    }

    public ItemStack getStackInSlot(int i) {
        return slots[i];
    }

    public ItemStack decrStackSize(int i, int j) {
        if (slots[i] != null) {
            ItemStack stackAt;
            if (slots[i].stackSize <= j) {
                stackAt = slots[i];
                slots[i] = null;
                return stackAt;
            } else {
                stackAt = slots[i].splitStack(j);
                if (slots[i].stackSize == 0)
                    slots[i] = null;
                return stackAt;
            }
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(int i) {
        return getStackInSlot(i);
    }

    public void setInventorySlotContents(int i, ItemStack stack){
        this.slots[i] = stack;
    }

    public String getInventoryName() {
        return InitBlocks.blockCrucible.getUnlocalizedName().substring(5);
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
    }

    public void openInventory() {

    }

    public void closeInventory() {

    }

    public boolean isItemValidForSlot(int i, ItemStack stack) {
        return false;
    }

    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        NBTTagList tagList = new NBTTagList();
        for(int currentIndex = 0; currentIndex < slots.length; ++currentIndex){
            if (slots[currentIndex] != null){
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte)currentIndex);
                slots[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        compound.setTag("Items", tagList);

        compound.setInteger("CurrentFluidID", this.currentFluidID);
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound){
        super.readFromNBT(nbtTagCompound);
        NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        for (int i = 0; i < tagList.tagCount(); ++i){
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < slots.length){
                slots[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }

        this.currentFluidID = nbtTagCompound.getInteger("CurrentFluidID");
        if(this.currentFluidID == Util.fluidWater.ID) this.currentFluid = Util.fluidWater;
        else if(this.currentFluidID == Util.fluidNone.ID) this.currentFluid = Util.fluidNone;
        else this.currentFluid = Util.gemList.get(this.currentFluidID);
    }

    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), compound);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        this.readFromNBT(packet.func_148857_g());
    }

    public String getName() {
        return InitBlocks.blockCrucible.getUnlocalizedName().substring(5);
    }

    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[0];
    }

    public boolean canInsertItem(int par1, ItemStack stack, int par3) {
        return false;
    }

    public boolean canExtractItem(int par1, ItemStack stack, int par3) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getCraftProcessScaled(int par1){
        return this.currentProcessTime * par1 / this.processTimeNeeded;
    }
}
