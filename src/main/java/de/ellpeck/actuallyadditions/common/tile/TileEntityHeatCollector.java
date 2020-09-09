package de.ellpeck.actuallyadditions.common.tile;

import java.util.ArrayList;

import de.ellpeck.actuallyadditions.common.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMagma;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityHeatCollector extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay {

    public static final int ENERGY_PRODUCE = 40;
    public static final int BLOCKS_NEEDED = 4;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(30000, 0, 80);
    private int oldEnergy;
    private int disappearTime;

    public TileEntityHeatCollector() {
        super("heatCollector");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        this.storage.writeToNBT(compound);
        if (type == NBTType.SAVE_TILE) {
            compound.setInteger("DisappearTime", this.disappearTime);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.storage.readFromNBT(compound);
        if (type == NBTType.SAVE_TILE) {
            this.disappearTime = compound.getInteger("DisappearTime");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            ArrayList<Integer> blocksAround = new ArrayList<>();
            if (ENERGY_PRODUCE <= this.storage.getMaxEnergyStored() - this.storage.getEnergyStored()) {
                for (int i = 1; i <= 5; i++) {
                    BlockPos coords = this.pos.offset(WorldUtil.getDirectionBySidesInOrder(i));
                    IBlockState state = this.world.getBlockState(coords);
                    Block block = state.getBlock();
                    if (block != null && this.world.getBlockState(coords).getMaterial() == Material.LAVA && block.getMetaFromState(state) == 0 || this.world.getBlockState(coords).getBlock() instanceof BlockMagma) {
                        blocksAround.add(i);
                    }
                }

                if (blocksAround.size() >= BLOCKS_NEEDED) {
                    this.storage.receiveEnergyInternal(ENERGY_PRODUCE, false);
                    this.markDirty();

                    this.disappearTime++;
                    if (this.disappearTime >= 1000) {
                        this.disappearTime = 0;

                        if (this.world.rand.nextInt(200) == 0) {
                            int randomSide = blocksAround.get(this.world.rand.nextInt(blocksAround.size()));
                            this.world.setBlockToAir(this.pos.offset(WorldUtil.getDirectionBySidesInOrder(randomSide)));
                        }
                    }
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
