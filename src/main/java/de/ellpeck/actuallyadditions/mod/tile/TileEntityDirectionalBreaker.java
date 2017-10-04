/*
 * This file ("TileEntityDirectionalBreaker.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityDirectionalBreaker extends TileEntityInventoryBase{

    public final CustomEnergyStorage storage = new CustomEnergyStorage(ConfigIntValues.LONG_RANGE_BREAKER_ENERGY_CAPACITY.getValue(), ConfigIntValues.LONG_RANGE_BREAKER_ENERGY_RECEIVE.getValue(), 0);
    private int lastEnergy;
    private int currentTime;

    public TileEntityDirectionalBreaker(){
        super(9, "directionalBreaker");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("CurrentTime", this.currentTime);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        if(type != NBTType.SAVE_BLOCK){
            this.currentTime = compound.getInteger("CurrentTime");
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            if(!this.isRedstonePowered && !this.isPulseMode){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        this.doWork();
                    }
                }
                else{
                    this.currentTime = 15;
                }
            }

            if(this.storage.getEnergyStored() != this.lastEnergy && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
            }
        }
    }

    private void doWork(){
        int energyUse = ConfigIntValues.LONG_RANGE_BREAKER_ENERGY_USE.getValue();
        int workDistance = ConfigIntValues.LONG_RANGE_BREAKER_WORK_DISTANCE.getValue();
        if(this.storage.getEnergyStored() >= energyUse*workDistance){
            IBlockState state = this.world.getBlockState(this.pos);
            EnumFacing sideToManipulate = WorldUtil.getDirectionByPistonRotation(state.getBlock().getMetaFromState(state));

            for(int i = 0; i < workDistance; i++){
                BlockPos coordsBlock = this.pos.offset(sideToManipulate, i+1);
                IBlockState breakState = world.getBlockState(coordsBlock);
                Block blockToBreak = breakState.getBlock();
                if(blockToBreak != null && !this.world.isAirBlock(coordsBlock) && this.world.getBlockState(coordsBlock).getBlockHardness(this.world, coordsBlock) > -1.0F){
                	NonNullList<ItemStack> drops = NonNullList.create();
                    blockToBreak.getDrops(drops, world, coordsBlock, breakState, 0);
                    float chance = WorldUtil.fireFakeHarvestEventsForDropChance(drops, this.world, coordsBlock);

                    if(chance > 0 && this.world.rand.nextFloat() <= chance){
                        if(WorldUtil.addToInventory(this.slots, drops, false)){
                            this.world.playEvent(2001, coordsBlock, Block.getStateId(this.world.getBlockState(coordsBlock)));
                            this.world.setBlockToAir(coordsBlock);
                            WorldUtil.addToInventory(this.slots, drops, true);
                            this.storage.extractEnergyInternal(energyUse, false);
                            this.markDirty();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int i){
        return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return true;
    }

    @Override
    public boolean isRedstoneToggle(){
        return true;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
