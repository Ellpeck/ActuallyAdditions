package de.ellpeck.actuallyadditions.common.tile;

import de.ellpeck.actuallyadditions.common.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.common.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;

public class TileEntityLavaFactoryController extends TileEntityBase implements IEnergyDisplay {

    public static final int NOT_MULTI = 0;
    public static final int HAS_LAVA = 1;
    public static final int HAS_AIR = 2;
    public static final int ENERGY_USE = 150000;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 2000, 0);
    private int currentWorkTime;
    private int oldEnergy;

    public TileEntityLavaFactoryController() {
        super("lavaFactory");
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("WorkTime", this.currentWorkTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentWorkTime = compound.getInt("WorkTime");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            if (this.storage.getEnergyStored() >= ENERGY_USE && this.isMultiblock() == HAS_AIR) {
                this.currentWorkTime++;
                if (this.currentWorkTime >= 200) {
                    this.currentWorkTime = 0;
                    this.world.setBlockState(this.pos.up(), Blocks.LAVA.getDefaultState(), 2);
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
                }
            } else {
                this.currentWorkTime = 0;
            }

            if (this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    public int isMultiblock() {
        BlockPos thisPos = this.pos;
        BlockPos[] positions = new BlockPos[] { thisPos.add(1, 1, 0), thisPos.add(-1, 1, 0), thisPos.add(0, 1, 1), thisPos.add(0, 1, -1) };

        if (WorldUtil.hasBlocksInPlacesGiven(positions, InitBlocks.blockMisc.get(), this.world)) {
            BlockPos pos = thisPos.up();
            BlockState state = this.world.getBlockState(pos);
            Block block = state.getBlock();
            if (block == Blocks.LAVA) { return HAS_LAVA; }
            if (this.world.isAirBlock(pos)) { return HAS_AIR; }
        }
        return NOT_MULTI;
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
    public LazyOptional<CustomEnergyStorage> getEnergyStorage(Direction facing) {
        return LazyOptional.of(() -> this.storage);
    }
}
