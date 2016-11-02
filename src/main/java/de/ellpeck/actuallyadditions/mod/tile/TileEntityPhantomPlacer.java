/*
 * This file ("TileEntityPhantomPlacer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class TileEntityPhantomPlacer extends TileEntityInventoryBase implements IPhantomTile, IButtonReactor{

    public static final int RANGE = 3;
    public BlockPos boundPosition;
    public int currentTime;
    public int range;
    public boolean isBreaker;
    public int side;
    private int oldRange;

    public TileEntityPhantomPlacer(int slots, String name){
        super(slots, name);
    }

    public TileEntityPhantomPlacer(){
        super(9, "phantomPlacer");
        this.isBreaker = false;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("Range", this.range);
            if(this.boundPosition != null){
                compound.setInteger("XCoordOfTileStored", this.boundPosition.getX());
                compound.setInteger("YCoordOfTileStored", this.boundPosition.getY());
                compound.setInteger("ZCoordOfTileStored", this.boundPosition.getZ());
            }
            if(!this.isBreaker){
                compound.setInteger("Side", this.side);
            }
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            int x = compound.getInteger("XCoordOfTileStored");
            int y = compound.getInteger("YCoordOfTileStored");
            int z = compound.getInteger("ZCoordOfTileStored");
            this.range = compound.getInteger("Range");
            if(!(x == 0 && y == 0 && z == 0)){
                this.boundPosition = new BlockPos(x, y, z);
                this.markDirty();
            }
            if(!this.isBreaker){
                this.side = compound.getInteger("Side");
            }
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.worldObj.isRemote){
            this.range = TileEntityPhantomface.upgradeRange(RANGE, this.worldObj, this.pos);

            if(!this.hasBoundPosition()){
                this.boundPosition = null;
            }

            if(this.isBoundThingInRange()){
                if(!this.isRedstonePowered && !this.isPulseMode){
                    if(this.currentTime > 0){
                        this.currentTime--;
                        if(this.currentTime <= 0){
                            this.doWork();
                        }
                    }
                    else{
                        this.currentTime = 30;
                    }
                }
            }

            if(this.oldRange != this.range){
                this.oldRange = this.range;

                this.sendUpdate();
            }
        }
        else{
            if(this.boundPosition != null){
                this.renderParticles();
            }
        }
    }

    @Override
    public boolean hasBoundPosition(){
        if(this.boundPosition != null){
            if(this.worldObj.getTileEntity(this.boundPosition) instanceof IPhantomTile || (this.getPos().getX() == this.boundPosition.getX() && this.getPos().getY() == this.boundPosition.getY() && this.getPos().getZ() == this.boundPosition.getZ() && this.worldObj.provider.getDimension() == this.worldObj.provider.getDimension())){
                this.boundPosition = null;
                return false;
            }
            return this.worldObj.provider.getDimension() == this.worldObj.provider.getDimension();
        }
        return false;
    }

    private void doWork(){
        if(this.isBoundThingInRange()){
            if(this.isBreaker){
                Block blockToBreak = this.worldObj.getBlockState(this.boundPosition).getBlock();
                if(blockToBreak != null && blockToBreak.getBlockHardness(this.worldObj.getBlockState(this.boundPosition), this.worldObj, this.boundPosition) > -1.0F){
                    ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                    drops.addAll(blockToBreak.getDrops(this.worldObj, this.boundPosition, this.worldObj.getBlockState(this.boundPosition), 0));

                    if(WorldUtil.addToInventory(this, drops, false, true)){
                        if(!ConfigBoolValues.LESS_BLOCK_BREAKING_EFFECTS.isEnabled()){
                            this.worldObj.playEvent(2001, this.boundPosition, Block.getStateId(this.worldObj.getBlockState(this.boundPosition)));
                        }
                        this.worldObj.setBlockToAir(this.boundPosition);
                        WorldUtil.addToInventory(this, drops, true, true);
                        this.markDirty();
                    }
                }
            }
            else{
                int theSlot = WorldUtil.findFirstFilledSlot(this.slots);
                this.setInventorySlotContents(theSlot, WorldUtil.useItemAtSide(WorldUtil.getDirectionBySidesInOrder(this.side), this.worldObj, this.boundPosition, this.slots[theSlot]));
                if(this.slots[theSlot] != null && this.slots[theSlot].stackSize <= 0){
                    this.slots[theSlot] = null;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void renderParticles(){
        if(this.worldObj.rand.nextInt(2) == 0){
            double d1 = (double)((float)this.boundPosition.getY()+this.worldObj.rand.nextFloat());
            int i1 = this.worldObj.rand.nextInt(2)*2-1;
            int j1 = this.worldObj.rand.nextInt(2)*2-1;
            double d4 = ((double)this.worldObj.rand.nextFloat()-0.5D)*0.125D;
            double d2 = (double)this.boundPosition.getZ()+0.5D+0.25D*(double)j1;
            double d5 = (double)(this.worldObj.rand.nextFloat()*1.0F*(float)j1);
            double d0 = (double)this.boundPosition.getX()+0.5D+0.25D*(double)i1;
            double d3 = (double)(this.worldObj.rand.nextFloat()*1.0F*(float)i1);
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }

        if(this.ticksElapsed%80 == 0){
            AssetUtil.renderParticlesFromAToB(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), this.boundPosition.getX(), this.boundPosition.getY(), this.boundPosition.getZ(), 2, 0.35F, TileEntityPhantomface.COLORS, 3);
        }
    }

    @Override
    public boolean isBoundThingInRange(){
        return this.hasBoundPosition() && this.boundPosition.distanceSq(this.pos) <= this.range*this.range;
    }

    @Override
    public BlockPos getBoundPosition(){
        return this.boundPosition;
    }

    @Override
    public void setBoundPosition(BlockPos pos){
        this.boundPosition = pos;
    }

    @Override
    public int getGuiID(){
        return GuiHandler.GuiTypes.PHANTOM_PLACER.ordinal();
    }

    @Override
    public int getRange(){
        return this.range;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return !this.isBreaker;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side){
        return this.isBreaker;
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
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(this.side+1 >= EnumFacing.values().length){
            this.side = 0;
        }
        else{
            this.side++;
        }

        this.sendUpdate();
    }
}
