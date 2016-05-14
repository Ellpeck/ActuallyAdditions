/*
 * This file ("TileEntityBreaker.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;


import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;

public class TileEntityBreaker extends TileEntityInventoryBase implements IRedstoneToggle{

    public boolean isPlacer;
    private int currentTime;
    private boolean activateOnceWithSignal;

    public TileEntityBreaker(int slots, String name){
        super(slots, name);
    }

    public TileEntityBreaker(){
        super(9, "breaker");
        this.isPlacer = false;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("CurrentTime", this.currentTime);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.currentTime = compound.getInteger("CurrentTime");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            if(!this.isRedstonePowered && !this.activateOnceWithSignal){
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
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return this.isPlacer;
    }

    private void doWork(){
        EnumFacing sideToManipulate = WorldUtil.getDirectionByPistonRotation(PosUtil.getMetadata(this.pos, this.worldObj));

        BlockPos coordsBlock = WorldUtil.getCoordsFromSide(sideToManipulate, this.pos, 0);
        if(coordsBlock != null){
            Block blockToBreak = PosUtil.getBlock(coordsBlock, this.worldObj);
            IBlockState stateToBreak = this.worldObj.getBlockState(coordsBlock);
            if(!this.isPlacer && blockToBreak != null && !(blockToBreak instanceof BlockAir) && blockToBreak.getBlockHardness(stateToBreak, this.worldObj, coordsBlock) > -1.0F){
                List<ItemStack> drops = blockToBreak.getDrops(this.worldObj, coordsBlock, stateToBreak, 0);
                float chance = ForgeEventFactory.fireBlockHarvesting(drops, this.worldObj, coordsBlock, this.worldObj.getBlockState(coordsBlock), 0, 1, false, null);

                if(Util.RANDOM.nextFloat() <= chance){
                    if(WorldUtil.addToInventory(this, drops, false, true)){
                        if(!ConfigValues.lessBlockBreakingEffects){
                            this.worldObj.playAuxSFX(2001, coordsBlock, Block.getStateId(stateToBreak));
                        }
                        WorldUtil.breakBlockAtSide(sideToManipulate, this.worldObj, this.pos);
                        WorldUtil.addToInventory(this, drops, true, true);
                        this.markDirty();
                    }
                }
            }
            else if(this.isPlacer){
                int theSlot = WorldUtil.findFirstFilledSlot(this.slots);
                this.setInventorySlotContents(theSlot, WorldUtil.useItemAtSide(sideToManipulate, this.worldObj, this.pos, this.slots[theSlot]));
                if(this.slots[theSlot] != null && this.slots[theSlot].stackSize <= 0){
                    this.slots[theSlot] = null;
                }
            }
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return true;
    }

    @Override
    public void toggle(boolean to){
        this.activateOnceWithSignal = to;
    }

    @Override
    public boolean isPulseMode(){
        return this.activateOnceWithSignal;
    }

    @Override
    public void activateOnPulse(){
        this.doWork();
    }

}
