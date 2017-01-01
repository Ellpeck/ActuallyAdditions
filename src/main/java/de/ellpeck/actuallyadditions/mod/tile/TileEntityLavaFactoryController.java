/*
 * This file ("TileEntityLavaFactoryController.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityLavaFactoryController extends TileEntityBase implements IEnergyDisplay{

    public static final int NOT_MULTI = 0;
    public static final int HAS_LAVA = 1;
    public static final int HAS_AIR = 2;
    public static final int ENERGY_USE = 150000;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 2000, 0);
    private int currentWorkTime;
    private int oldEnergy;

    public TileEntityLavaFactoryController(){
        super("lavaFactory");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type){
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        if(type != NBTType.SAVE_BLOCK){
            compound.setInteger("WorkTime", this.currentWorkTime);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type){
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        if(type != NBTType.SAVE_BLOCK){
            this.currentWorkTime = compound.getInteger("WorkTime");
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.world.isRemote){
            if(this.storage.getEnergyStored() >= ENERGY_USE && this.isMultiblock() == HAS_AIR){
                this.currentWorkTime++;
                if(this.currentWorkTime >= 200){
                    this.currentWorkTime = 0;
                    this.world.setBlockState(this.pos.up(), Blocks.LAVA.getDefaultState(), 2);
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
                }
            }
            else{
                this.currentWorkTime = 0;
            }

            if(this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()){
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    public int isMultiblock(){
        BlockPos thisPos = this.pos;
        BlockPos[] positions = new BlockPos[]{
                thisPos.add(1, 1, 0),
                thisPos.add(-1, 1, 0),
                thisPos.add(0, 1, 1),
                thisPos.add(0, 1, -1)
        };

        if(WorldUtil.hasBlocksInPlacesGiven(positions, InitBlocks.blockMisc, TheMiscBlocks.LAVA_FACTORY_CASE.ordinal(), this.world)){
            BlockPos pos = thisPos.up();
            IBlockState state = this.world.getBlockState(pos);
            Block block = state.getBlock();
            if(block == Blocks.LAVA || block == Blocks.FLOWING_LAVA){
                return HAS_LAVA;
            }
            if(block == null || this.world.isAirBlock(pos)){
                return HAS_AIR;
            }
        }
        return NOT_MULTI;
    }

    @Override
    public CustomEnergyStorage getEnergyStorage(){
        return this.storage;
    }

    @Override
    public boolean needsHoldShift(){
        return false;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return this.storage;
    }
}
