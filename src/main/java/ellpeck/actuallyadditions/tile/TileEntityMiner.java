/*
 * This file ("TileEntityMiner.java") is part of the Actually Additions Mod for Minecraft.
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.network.PacketParticle;
import ellpeck.actuallyadditions.network.gui.IButtonReactor;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class TileEntityMiner extends TileEntityInventoryBase implements IEnergyReceiver, IButtonReactor, IEnergySaver, IEnergyDisplay{

    public static final int ENERGY_USE_PER_BLOCK = 500;
    public static final int DEFAULT_RANGE = 2;
    public EnergyStorage storage = new EnergyStorage(1000000);
    public int layerAt = -1;
    public boolean onlyMineOres = true;
    private int oldLayerAt;
    private int oldEnergy;

    public TileEntityMiner(){
        super(9, "miner");
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(this.layerAt == -1){
                this.layerAt = this.yCoord-1;
            }

            if(!this.isRedstonePowered && this.ticksElapsed%5 == 0){

                if(this.layerAt > 0){
                    if(this.mine(TileEntityPhantomface.upgradeRange(DEFAULT_RANGE, worldObj, xCoord, yCoord, zCoord))){
                        this.layerAt--;
                    }
                }
            }

            if((this.oldEnergy != this.storage.getEnergyStored() || this.oldLayerAt != this.layerAt) && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
                this.oldLayerAt = this.layerAt;
            }
        }
    }

    private boolean mine(int range){
        for(int anX = -range; anX <= range; anX++){
            for(int aZ = -range; aZ <= range; aZ++){
                int actualUse = ENERGY_USE_PER_BLOCK*(this.onlyMineOres ? 3 : 1);
                if(this.storage.getEnergyStored() >= actualUse){
                    int x = this.xCoord+anX;
                    int z = this.zCoord+aZ;
                    int y = this.layerAt;

                    Block block = this.worldObj.getBlock(x, y, z);
                    int meta = this.worldObj.getBlockMetadata(x, y, z);
                    if(block != null && !block.isAir(this.worldObj, x, y, z)){
                        if(block.getHarvestLevel(meta) <= 3F && block.getBlockHardness(this.worldObj, x, y, z) >= 0F && !(block instanceof BlockLiquid) && !(block instanceof IFluidBlock) && this.isMinable(block, meta)){
                            ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                            drops.addAll(block.getDrops(worldObj, x, y, z, meta, 0));

                            if(WorldUtil.addToInventory(this, drops, ForgeDirection.UNKNOWN, false)){
                                worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block)+(meta << 12));
                                worldObj.setBlockToAir(x, y, z);

                                WorldUtil.addToInventory(this, drops, ForgeDirection.UNKNOWN, true);
                                this.markDirty();

                                this.storage.extractEnergy(actualUse, false);
                                this.shootParticles(x, y, z);
                            }
                            return false;
                        }
                    }
                }
                else{
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isMinable(Block block, int meta){
        if(!this.onlyMineOres){
            return true;
        }
        else{
            int[] ids = OreDictionary.getOreIDs(new ItemStack(block, 1, meta));
            for(int id : ids){
                String name = OreDictionary.getOreName(id);
                if(name.startsWith("ore") || name.startsWith("denseore")){
                    return true;
                }
            }
            return false;
        }
    }

    private void shootParticles(int endX, int endY, int endZ){
        PacketHandler.theNetwork.sendToAllAround(new PacketParticle(xCoord, yCoord, zCoord, endX, endY, endZ, new float[]{62F/255F, 163F/255F, 74F/255F}, 5, 1.0F), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 96));
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        this.storage.writeToNBT(compound);
        compound.setInteger("Layer", this.layerAt);
        compound.setBoolean("OnlyOres", this.onlyMineOres);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.storage.readFromNBT(compound);
        this.layerAt = compound.getInteger("Layer");
        this.onlyMineOres = compound.getBoolean("OnlyOres");
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
    public boolean isItemValidForSlot(int slot, ItemStack stack){
        return false;
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID == 0){
            this.onlyMineOres = !this.onlyMineOres;
            this.sendUpdate();
        }
        else if(buttonID == 1){
            this.layerAt = -1;
        }
    }

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy){
        this.storage.setEnergyStored(energy);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getMaxEnergy(){
        return this.storage.getMaxEnergyStored();
    }
}
