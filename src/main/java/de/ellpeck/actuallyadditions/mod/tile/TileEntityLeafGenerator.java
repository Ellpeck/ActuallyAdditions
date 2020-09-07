package de.ellpeck.actuallyadditions.mod.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityLeafGenerator extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay {

    public static final int RANGE = 7;
    public static final int ENERGY_PRODUCED = 300;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(35000, 0, 450);
    private int nextUseCounter;
    private int oldEnergy;

    public TileEntityLeafGenerator() {
        super("leafGenerator");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            if (!this.isRedstonePowered) {

                if (this.nextUseCounter >= 5) {
                    this.nextUseCounter = 0;

                    if (ENERGY_PRODUCED <= this.storage.getMaxEnergyStored() - this.storage.getEnergyStored()) {
                        List<BlockPos> breakPositions = new ArrayList<>();

                        for (int reachX = -RANGE; reachX < RANGE + 1; reachX++) {
                            for (int reachZ = -RANGE; reachZ < RANGE + 1; reachZ++) {
                                for (int reachY = -RANGE; reachY < RANGE + 1; reachY++) {
                                    BlockPos pos = this.pos.add(reachX, reachY, reachZ);
                                    Block block = this.world.getBlockState(pos).getBlock();
                                    if (block != null && block.isLeaves(this.world.getBlockState(pos), this.world, pos)) {
                                        breakPositions.add(pos);
                                    }
                                }
                            }
                        }

                        if (!breakPositions.isEmpty()) {
                            Collections.shuffle(breakPositions);
                            BlockPos theCoord = breakPositions.get(0);

                            this.world.playEvent(2001, theCoord, Block.getStateId(this.world.getBlockState(theCoord)));

                            this.world.setBlockToAir(theCoord);

                            this.storage.receiveEnergyInternal(ENERGY_PRODUCED, false);

                            AssetUtil.spawnLaserWithTimeServer(this.world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), theCoord.getX(), theCoord.getY(), theCoord.getZ(), new float[] { 62F / 255F, 163F / 255F, 74F / 255F }, 25, 0, 0.075F, 0.8F);
                        }
                    }
                } else {
                    this.nextUseCounter++;
                }
            }

            if (this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    @Override
    public CustomEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    @Override
    public boolean needsHoldShift() {
        return false;
    }

    @Override
    public int getEnergyToSplitShare() {
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean doesShareEnergy() {
        return true;
    }

    @Override
    public EnumFacing[] getEnergyShareSides() {
        return EnumFacing.values();
    }

    @Override
    public boolean canShareTo(TileEntity tile) {
        return true;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.storage;
    }
}
