package ellpeck.actuallyadditions.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityItemRepairer extends TileEntityInventoryBase implements IPowerAcceptor{

    public static final int SLOT_COAL = 0;
    public static final int SLOT_INPUT = 1;
    public static final int SLOT_OUTPUT = 2;

    private final int speedSlowdown = ConfigIntValues.REPAIRER_SPEED_SLOWDOWN.getValue();

    public int coalTime;
    public int coalTimeLeft;

    public int nextRepairTick;

    public TileEntityItemRepairer(){
        super(3, "repairer");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            boolean theFlag = this.coalTimeLeft > 0;

            if(this.coalTimeLeft > 0) this.coalTimeLeft--;

            if(this.slots[SLOT_OUTPUT] == null){
                if(canBeRepaired(this.slots[SLOT_INPUT])){
                    if(this.slots[SLOT_INPUT].getItemDamage() <= 0){
                        this.slots[SLOT_OUTPUT] = this.slots[SLOT_INPUT].copy();
                        this.slots[SLOT_INPUT] = null;
                    }
                    else{
                        if(this.coalTimeLeft <= 0){
                            this.coalTime = TileEntityFurnace.getItemBurnTime(this.slots[SLOT_COAL]);
                            this.coalTimeLeft = this.coalTime;
                            if(this.coalTime > 0){
                                this.slots[SLOT_COAL].stackSize--;
                                if(this.slots[SLOT_COAL].stackSize <= 0) this.slots[SLOT_COAL] = this.slots[SLOT_COAL].getItem().getContainerItem(this.slots[SLOT_COAL]);
                            }
                        }
                        if(this.coalTimeLeft > 0){
                            this.nextRepairTick++;
                            if(this.nextRepairTick >= this.speedSlowdown){
                                this.nextRepairTick = 0;
                                this.slots[SLOT_INPUT].setItemDamage(this.slots[SLOT_INPUT].getItemDamage() - 1);
                            }
                        }
                    }
                }
            }

            if(theFlag != this.coalTimeLeft > 0){
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, (this.coalTimeLeft > 0 ? 1 : 0), 2);
                this.markDirty();
            }
        }
    }

    public static boolean canBeRepaired(ItemStack stack){
        return stack != null && stack.getItem().isRepairable();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        compound.setInteger("CoalTime", this.coalTime);
        compound.setInteger("CoalTimeLeft", this.coalTimeLeft);
        compound.setInteger("NextRepairTick", this.nextRepairTick);
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.coalTime = compound.getInteger("CoalTime");
        this.coalTimeLeft = compound.getInteger("CoalTimeLeft");
        this.nextRepairTick = compound.getInteger("NextRepairTick");
        super.readFromNBT(compound);
    }

    @SideOnly(Side.CLIENT)
    public int getCoalTimeToScale(int i){
        return this.coalTimeLeft * i / this.coalTime;
    }

    @SideOnly(Side.CLIENT)
    public int getItemDamageToScale(int i){
        if(this.slots[SLOT_INPUT] != null){
            return (this.slots[SLOT_INPUT].getMaxDamage()-this.slots[SLOT_INPUT].getItemDamage()) * i / this.slots[SLOT_INPUT].getMaxDamage();
        }
        return 0;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return i == SLOT_COAL && TileEntityFurnace.getItemBurnTime(stack) > 0 || i == SLOT_INPUT;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OUTPUT || (slot == SLOT_COAL && stack.getItem() == Items.bucket);
    }

    @Override
    public void setBlockMetadataToOn(){
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
    }

    @Override
    public void setPower(int power){
        this.coalTimeLeft = power;
    }

    @Override
    public void setItemPower(int power){
        this.coalTime = power;
    }

    @Override
    public int getItemPower(){
        return this.coalTime;
    }
}
