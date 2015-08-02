package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.network.sync.IPacketSyncerToClient;
import ellpeck.actuallyadditions.network.sync.PacketSyncerToClient;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

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

    private int maxWorkTimer = ConfigIntValues.ORE_MAGNET_MAX_TIMER.getValue();
    private int range = ConfigIntValues.ORE_MAGNET_RANGE.getValue();
    public static int oilUsePerTick = ConfigIntValues.ORE_MAGNET_OIL_USE.getValue();
    public static int energyUsePerTick = ConfigIntValues.ORE_MAGNET_ENERGY_USE.getValue();

    public TileEntityOreMagnet(){
        super(3, "oreMagnet");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){

            if(this.storage.getEnergyStored() >= energyUsePerTick && this.tank.getFluid() != null && this.tank.getFluid().getFluid() == InitBlocks.fluidOil && this.tank.getFluidAmount() >= oilUsePerTick && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                if(this.currentWorkTimer > 0){
                    currentWorkTimer--;

                    if(currentWorkTimer <= 0){
                        //The possible positions where ores can be mined up in RELATIVE COORDINATES!!
                        ArrayList<WorldPos> possiblePlacingPositions = new ArrayList<WorldPos>();

                        for(int x = -range/2; x <= range/2; x++){
                            for(int z = -range/2; z <= range/2; z++){
                                //Check if there is a casing below the Block to mine
                                if(WorldUtil.hasBlocksInPlacesGiven(new int[][]{{x, -1, z}}, InitBlocks.blockMisc, TheMiscBlocks.LAVA_FACTORY_CASE.ordinal(), worldObj, xCoord, yCoord, zCoord)){
                                    //Can the block at the top be replaced?
                                    for(int toPlaceY = 0; toPlaceY < 5; toPlaceY++){
                                        Block block = worldObj.getBlock(xCoord+x, yCoord+toPlaceY, zCoord+z);
                                        //Check if the Block is okay to be replaced
                                        if(block.isAir(worldObj, xCoord+x, yCoord+toPlaceY, zCoord+z) || block.isReplaceable(worldObj, xCoord+x, yCoord+toPlaceY, zCoord+z)){
                                            //Add it to the possible positions
                                            possiblePlacingPositions.add(new WorldPos(worldObj, x, toPlaceY, z));
                                            //Only add the lowest Block, you don't want to make random floating towers, duh!
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if(!possiblePlacingPositions.isEmpty()){
                            //Get a random placing Position
                            WorldPos randomPlacingPos = possiblePlacingPositions.get(worldObj.rand.nextInt(possiblePlacingPositions.size()));
                            int x = randomPlacingPos.getX();
                            int z = randomPlacingPos.getZ();
                            int toPlaceY = randomPlacingPos.getY();
                            //Find the first available block
                            for(int y = this.yCoord-2; y > 0; y--){
                                Block block = worldObj.getBlock(xCoord+x, y, zCoord+z);
                                int meta = worldObj.getBlockMetadata(xCoord+x, y, zCoord+z);
                                if(block != null && !block.isAir(worldObj, xCoord+x, y, zCoord+z) && !block.hasTileEntity(meta) && block.getBlockHardness(worldObj, xCoord+x, y, zCoord+z) >= 0.0F && ((block.getMaterial() != null && block.getMaterial().isToolNotRequired()) || (block.getHarvestTool(meta) == null || (block.getHarvestTool(meta).equals("pickaxe") && block.getHarvestLevel(meta) <= 3)))){
                                    int[] oreIDs = OreDictionary.getOreIDs(new ItemStack(block, 1, meta));
                                    for(int ID : oreIDs){
                                        String oreName = OreDictionary.getOreName(ID);
                                        //Is the block an ore according to the OreDictionary?
                                        if(oreName.substring(0, 3).equals("ore") && !this.hasException(oreName)){
                                            //Remove the Block
                                            worldObj.setBlockToAir(xCoord+x, y, zCoord+z);
                                            worldObj.playAuxSFX(2001, xCoord+x, y, zCoord+z, Block.getIdFromBlock(block)+(meta << 12));

                                            //Set the Block at the Top again
                                            worldObj.setBlock(xCoord+x, yCoord+toPlaceY, zCoord+z, block, meta, 2);
                                            worldObj.playSoundEffect((double)xCoord+x+0.5D, (double)yCoord+toPlaceY+0.5D, (double)zCoord+z+0.5D, block.stepSound.func_150496_b(), (block.stepSound.getVolume()+1.0F)/2.0F, block.stepSound.getPitch()*0.8F);

                                            //Extract oil
                                            this.tank.drain(oilUsePerTick, true);
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
                this.storage.extractEnergy(energyUsePerTick, false);
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

    private boolean hasException(String name){
        for(String except : ConfigValues.oreMagnetExceptions){
            if(except.equals(name)) return true;
        }
        return false;
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
        if(this.canFill(from, resource.getFluid())) return this.tank.fill(resource, doFill);
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
