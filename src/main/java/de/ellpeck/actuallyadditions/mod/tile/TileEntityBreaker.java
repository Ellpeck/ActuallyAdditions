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
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;

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
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
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
    public void writeSyncableNBT(NBTTagCompound compound, boolean sync){
        super.writeSyncableNBT(compound, sync);
        compound.setInteger("CurrentTime", this.currentTime);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean sync){
        super.readSyncableNBT(compound, sync);
        this.currentTime = compound.getInteger("CurrentTime");
    }

    private void doWork(){
        EnumFacing sideToManipulate = WorldUtil.getDirectionByPistonRotation(PosUtil.getMetadata(this.pos, worldObj));

        BlockPos coordsBlock = WorldUtil.getCoordsFromSide(sideToManipulate, this.pos, 0);
        if(coordsBlock != null){
            Block blockToBreak = PosUtil.getBlock(coordsBlock, worldObj);
            if(!this.isPlacer && blockToBreak != null && !(blockToBreak instanceof BlockAir) && blockToBreak.getBlockHardness(worldObj, coordsBlock) > -1.0F){
                ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                int meta = PosUtil.getMetadata(coordsBlock, worldObj);
                drops.addAll(blockToBreak.getDrops(worldObj, coordsBlock, worldObj.getBlockState(coordsBlock), 0));

                if(WorldUtil.addToInventory(this, drops, false, true)){
                    if(!ConfigValues.lessBlockBreakingEffects){
                        worldObj.playAuxSFX(2001, coordsBlock, Block.getIdFromBlock(blockToBreak)+(meta << 12));
                    }
                    WorldUtil.breakBlockAtSide(sideToManipulate, worldObj, this.pos);
                    WorldUtil.addToInventory(this, drops, true, true);
                    this.markDirty();
                }
            }
            else if(this.isPlacer && PosUtil.getBlock(coordsBlock, worldObj).isReplaceable(worldObj, coordsBlock)){
                int theSlot = WorldUtil.findFirstFilledSlot(this.slots);
                this.setInventorySlotContents(theSlot, WorldUtil.placeBlockAtSide(sideToManipulate, worldObj, this.pos, this.slots[theSlot]));
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
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return this.isPlacer;
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

    public static class TileEntityPlacer extends TileEntityBreaker{

        public TileEntityPlacer(){
            super(9, "placer");
            this.isPlacer = true;
        }

    }

}
