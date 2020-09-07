package de.ellpeck.actuallyadditions.api.laser;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

public interface IConnectionPair {

    void writeToNBT(CompoundNBT compound);

    void readFromNBT(CompoundNBT compound);

    BlockPos[] getPositions();

    boolean doesSuppressRender();

    LaserType getType();

    boolean contains(BlockPos pos);
}
