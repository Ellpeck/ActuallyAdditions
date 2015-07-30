package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.network.sync.IPacketSyncerToClient;
import ellpeck.actuallyadditions.network.sync.PacketSyncerToClient;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityOreMagnet extends TileEntityInventoryBase implements IEnergyReceiver, IFluidHandler, IPacketSyncerToClient{

    public EnergyStorage storage = new EnergyStorage(40000);
    public FluidTank tank = new FluidTank(8*FluidContainerRegistry.BUCKET_VOLUME);
    private int currentWorkTimer;

    private static final int MAX_WORK_TIMER = 30;
    private static final int RANGE = 5;

    public TileEntityOreMagnet(){
        super(3, "oreMagnet");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){

            if(this.currentWorkTimer > 0){
                currentWorkTimer--;

                if(currentWorkTimer <= 0){
                    int x = MathHelper.getRandomIntegerInRange(worldObj.rand, -RANGE, RANGE);
                    int z = MathHelper.getRandomIntegerInRange(worldObj.rand, -RANGE, RANGE);
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

                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else this.currentWorkTimer = MathHelper.getRandomIntegerInRange(worldObj.rand, MAX_WORK_TIMER, MAX_WORK_TIMER*5);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        this.storage.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.storage.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        super.readFromNBT(compound);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return false;
        //TODO
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return false;
        //TODO
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
        //TODO
        return new int[0];
    }

    @Override
    public void setValues(int[] values){
        //TODO
    }

    @Override
    public void sendUpdate(){
        PacketSyncerToClient.sendPacket(this);
    }
}
