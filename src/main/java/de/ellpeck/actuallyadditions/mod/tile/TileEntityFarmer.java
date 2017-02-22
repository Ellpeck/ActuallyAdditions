/*
 * This file ("TileEntityFarmer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.farmer.FarmerResult;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TileEntityFarmer extends TileEntityInventoryBase implements IFarmer{

    private static final List<IFarmerBehavior> SORTED_FARMER_BEHAVIORS = new ArrayList<IFarmerBehavior>();
    public final CustomEnergyStorage storage = new CustomEnergyStorage(100000, 1000, 0);

    private int waitTime;
    private int checkX;
    private int checkY;

    private int lastEnergy;

    public TileEntityFarmer(){
        super(12, "farmer");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("WaitTime", this.waitTime);
        }
        if(type == NBTType.SAVE_TILE){
            compound.setInteger("CheckX", this.checkX);
            compound.setInteger("CheckY", this.checkY);
        }
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        if(type != NBTType.SAVE_BLOCK){
            this.waitTime = compound.getInteger("WaitTime");
        }
        if(type == NBTType.SAVE_TILE){
            this.checkX = compound.getInteger("CheckX");
            this.checkY = compound.getInteger("CheckY");
        }
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            if(!this.isRedstonePowered && this.storage.getEnergyStored() > 0){
                if(this.waitTime > 0){
                    this.waitTime--;

                    if(this.waitTime <= 0){
                        int radiusAroundCenter = 4;

                        IBlockState state = this.world.getBlockState(this.pos);
                        int meta = state.getBlock().getMetaFromState(state);
                        BlockPos center = this.pos.offset(EnumFacing.getHorizontal(meta), radiusAroundCenter+1);

                        BlockPos query = center.add(this.checkX, 0, this.checkY);
                        this.checkBehaviors(query);

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

            if(this.lastEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.lastEnergy = this.storage.getEnergyStored();
            }
        }
    }

    private void checkBehaviors(BlockPos query){
        if(SORTED_FARMER_BEHAVIORS.size() != ActuallyAdditionsAPI.FARMER_BEHAVIORS.size()){
            SORTED_FARMER_BEHAVIORS.clear();
            SORTED_FARMER_BEHAVIORS.addAll(ActuallyAdditionsAPI.FARMER_BEHAVIORS);

            Collections.sort(SORTED_FARMER_BEHAVIORS, new Comparator<IFarmerBehavior>(){
                @Override
                public int compare(IFarmerBehavior behavior1, IFarmerBehavior behavior2){
                    Integer prio1 = behavior1.getPriority();
                    Integer prio2 = behavior2.getPriority();
                    return prio2.compareTo(prio1);
                }
            });
        }

        for(IFarmerBehavior behavior : SORTED_FARMER_BEHAVIORS){
            FarmerResult harvestResult = behavior.tryHarvestPlant(this.world, query, this);
            if(harvestResult == FarmerResult.SUCCESS || harvestResult == FarmerResult.STOP_PROCESSING){
                return;
            }
            else{
                for(int i = 0; i < this.slots.getSlots(); i++){
                    ItemStack stack = this.slots.getStackInSlot(i);
                    if(StackUtil.isValid(stack)){
                        FarmerResult plantResult = behavior.tryPlantSeed(stack, this.world, query, this);
                        if(plantResult == FarmerResult.SUCCESS){
                            this.slots.decrStackSize(i, 1);
                            return;
                        }
                        else if(plantResult == FarmerResult.STOP_PROCESSING){
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return i < 6;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack){
        return slot >= 6;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }

    @Override
    public EnumFacing getOrientation(){
        IBlockState state = this.world.getBlockState(this.pos);
        return WorldUtil.getDirectionByPistonRotation(state.getBlock().getMetaFromState(state));
    }

    @Override
    public boolean addToSeedInventory(List<ItemStack> stacks, boolean actuallyDo){
        return WorldUtil.addToInventory(this.slots, 0, 6, stacks, actuallyDo);
    }

    @Override
    public boolean addToOutputInventory(List<ItemStack> stacks, boolean actuallyDo){
        return WorldUtil.addToInventory(this.slots, 6, 12, stacks, actuallyDo);
    }

    @Override
    public BlockPos getPosition(){
        return this.pos;
    }

    @Override
    public int getX(){
        return this.pos.getX();
    }

    @Override
    public int getY(){
        return this.pos.getY();
    }

    @Override
    public int getZ(){
        return this.pos.getZ();
    }

    @Override
    public World getWorldObject(){
        return this.world;
    }

    @Override
    public void extractEnergy(int amount){
        this.storage.extractEnergyInternal(amount, false);
    }

    @Override
    public int getEnergy(){
        return this.storage.getEnergyStored();
    }
}
