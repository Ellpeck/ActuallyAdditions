/*
 * This file ("TileEntityFarmer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;

import java.util.List;

public class TileEntityFarmer extends TileEntityInventoryBase{

    private int waitTime;
    private int checkX;
    private int checkY;

    public TileEntityFarmer(){
        super(12, "farmer");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("WaitTime", this.waitTime);
            compound.setInteger("CheckX", this.checkX);
            compound.setInteger("CheckY", this.checkY);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.waitTime = compound.getInteger("WaitTime");
            this.checkX = compound.getInteger("CheckX");
            this.checkY = compound.getInteger("CheckY");
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote && !this.isRedstonePowered){
            if(this.waitTime > 0){
                this.waitTime--;

                if(this.waitTime <= 0){
                    int radiusAroundCenter = 4;

                    IBlockState state = this.worldObj.getBlockState(this.pos);
                    EnumFacing side = WorldUtil.getDirectionByPistonRotation(state.getBlock().getMetaFromState(state));
                    BlockPos center = this.pos.offset(side, radiusAroundCenter+1);

                    BlockPos plant = center.add(this.checkX, 0, this.checkY);
                    IBlockState plantState = this.worldObj.getBlockState(plant);
                    Block plantBlock = plantState.getBlock();

                    if(plantBlock instanceof BlockCrops){
                        if(((BlockCrops)plantBlock).isMaxAge(plantState)){
                            List<ItemStack> drops = plantBlock.getDrops(this.worldObj, plant, plantState, 0);

                            if(WorldUtil.addToInventory(this, 6, 12, drops, EnumFacing.UP, false, true)){
                                WorldUtil.addToInventory(this, 6, 12, drops, EnumFacing.UP, true, true);

                                if(!ConfigBoolValues.LESS_BLOCK_BREAKING_EFFECTS.isEnabled()){
                                    this.worldObj.playEvent(2001, plant, Block.getStateId(plantState));
                                }
                                this.worldObj.setBlockToAir(plant);
                            }
                        }
                    }
                    else if(plantBlock.isReplaceable(this.worldObj, plant)){
                        BlockPos farmland = plant.down();
                        IBlockState farmlandState = this.worldObj.getBlockState(farmland);
                        Block farmlandBlock = farmlandState.getBlock();

                        if(farmlandBlock instanceof BlockFarmland){
                            IBlockState toPlant = this.getFirstPlantFromSlots(plant);
                            if(toPlant != null){
                                this.worldObj.setBlockState(plant, toPlant, 2);
                            }
                        }
                        else if(farmlandBlock instanceof BlockDirt || farmlandBlock instanceof BlockGrass){
                            this.worldObj.setBlockState(farmland, Blocks.FARMLAND.getDefaultState(), 2);
                            this.worldObj.setBlockToAir(plant);
                            this.worldObj.playSound(null, farmland, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        }
                    }

                    this.checkX++;
                    if(this.checkX > radiusAroundCenter){
                        this.checkX = -radiusAroundCenter;
                        this.checkY++;
                        if(this.checkY > radiusAroundCenter){
                            this.checkY = -radiusAroundCenter;
                        }
                    }
                }
            }
            else{
                this.waitTime = 5;
            }
        }
    }

    private IBlockState getFirstPlantFromSlots(BlockPos pos){
        for(int i = 0; i < 6; i++){
            ItemStack stack = this.slots[i];
            if(stack != null){
                Item item = stack.getItem();
                if(item instanceof IPlantable){
                    IBlockState state = ((IPlantable)item).getPlant(this.worldObj, pos);
                    if(state != null){
                        this.decrStackSize(i, 1);
                        return state;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return i < 6 && stack != null && stack.getItem() instanceof IPlantable;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return slot >= 6;
    }
}
