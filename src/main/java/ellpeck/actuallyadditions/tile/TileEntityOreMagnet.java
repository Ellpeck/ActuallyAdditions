package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.network.sync.IPacketSyncerToClient;
import ellpeck.actuallyadditions.network.sync.PacketSyncerToClient;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityOreMagnet extends TileEntityInventoryBase implements IEnergyReceiver, IFluidHandler, IPacketSyncerToClient{

    public static final int SLOT_OIL_INPUT = 0;
    public static final int SLOT_OIL_OUTPUT = 1;
    @SuppressWarnings("unused")
    public static final int SLOT_UPGRADE = 2;

    public EnergyStorage storage = new EnergyStorage(2000000);
    private int lastEnergy;

    public FluidTank tank = new FluidTank(16*FluidContainerRegistry.BUCKET_VOLUME);
    private int lastTankAmount;

    private int currentWorkTimer;

    private int maxWorkTimer = 15;
    private int range = 10;
    private int oilUsePerTick = 50;
    private int energyUsePerTick = 400;

    public TileEntityOreMagnet(){
        super(3, "oreMagnet");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){

            if(this.storage.getEnergyStored() >= this.energyUsePerTick && this.tank.getFluid() != null && this.tank.getFluid().getFluid() == InitBlocks.fluidOil && this.tank.getFluidAmount() >= this.oilUsePerTick && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                if(this.currentWorkTimer > 0){
                    currentWorkTimer--;

                    if(currentWorkTimer <= 0){
                        int x = MathHelper.getRandomIntegerInRange(worldObj.rand, -range, range);
                        int z = MathHelper.getRandomIntegerInRange(worldObj.rand, -range, range);
                        //Can the block at the top be replaced?
                        for(int toPlaceY = 0; toPlaceY < 5; toPlaceY++){
                            if(worldObj.isAirBlock(xCoord+x, yCoord+toPlaceY, zCoord+z) || worldObj.getBlock(xCoord+x, yCoord+toPlaceY, zCoord+z).isReplaceable(worldObj, xCoord+x, yCoord+toPlaceY, zCoord+z)){
                                //Find the first available block
                                for(int y = this.yCoord-1; y > 0; y--){
                                    Block block = worldObj.getBlock(xCoord+x, y, zCoord+z);
                                    int meta = worldObj.getBlockMetadata(xCoord+x, y, zCoord+z);

                                    int[] oreIDs = OreDictionary.getOreIDs(new ItemStack(block, 1, meta));
                                    for(int ID : oreIDs){
                                        String oreName = OreDictionary.getOreName(ID);
                                        //Is the block an ore according to the OreDictionary?
                                        if(oreName.substring(0, 3).equals("ore")){
                                            //Remove the Block
                                            worldObj.setBlockToAir(xCoord+x, y, zCoord+z);
                                            worldObj.playAuxSFX(2001, xCoord+x, y, zCoord+z, Block.getIdFromBlock(block)+(meta << 12));

                                            //Set the Block at the Top again
                                            worldObj.setBlock(xCoord+x, yCoord+toPlaceY, zCoord+z, block, meta, 2);
                                            worldObj.playSoundEffect((double)xCoord+x+0.5D, (double)yCoord+toPlaceY+0.5D, (double)zCoord+z+0.5D, block.stepSound.func_150496_b(), (block.stepSound.getVolume()+1.0F)/2.0F, block.stepSound.getPitch()*0.8F);

                                            //Extract oil
                                            this.tank.drain(this.oilUsePerTick, true);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else this.currentWorkTimer = maxWorkTimer+MathHelper.getRandomIntegerInRange(worldObj.rand, 0, maxWorkTimer);

                //Extract energy
                this.storage.extractEnergy(this.energyUsePerTick, false);
            }

            //Update Clients
            if(this.lastEnergy != this.storage.getEnergyStored() || this.lastTankAmount != this.tank.getFluidAmount()){
                this.lastEnergy = this.storage.getEnergyStored();
                this.lastTankAmount = this.tank.getFluidAmount();
                this.sendUpdate();
            }

            //Empty Oil Bucket
            WorldUtil.emptyBucket(this.tank, this.slots, SLOT_OIL_INPUT, SLOT_OIL_OUTPUT);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored() * i / this.storage.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public int getTankScaled(int i){
        return this.tank.getFluidAmount() * i / this.tank.getCapacity();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        compound.setInteger("CurrentWorkTimer", this.currentWorkTimer);
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        this.currentWorkTimer = compound.getInteger("CurrentWorkTimer");
        super.readFromNBT(compound);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return FluidContainerRegistry.containsFluid(stack, new FluidStack(InitBlocks.fluidOil, 1)) && i == SLOT_OIL_INPUT;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OIL_OUTPUT;
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
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
        if(resource.getFluid() == InitBlocks.fluidOil) return this.tank.fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain){
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid){
        return from != ForgeDirection.DOWN && fluid == InitBlocks.fluidOil;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid){
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from){
        return new FluidTankInfo[]{this.tank.getInfo()};
    }

    @Override
    public int[] getValues(){
        return new int[]{this.storage.getEnergyStored(), this.tank.getFluidAmount(), this.tank.getFluid() == null ? -1 : this.tank.getFluid().getFluidID()};
    }

    @Override
    public void setValues(int[] values){
        this.storage.setEnergyStored(values[0]);
        if(values[2] != -1){
            this.tank.setFluid(new FluidStack(FluidRegistry.getFluid(values[2]), values[1]));
        }
        else this.tank.setFluid(null);
    }

    @Override
    public void sendUpdate(){
        PacketSyncerToClient.sendPacket(this);
    }
}
