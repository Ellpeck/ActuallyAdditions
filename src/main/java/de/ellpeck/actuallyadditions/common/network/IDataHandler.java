package de.ellpeck.actuallyadditions.common.network;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.NetworkEvent;

public interface IDataHandler {

    void handleData(CompoundNBT compound, NetworkEvent.Context context);

}
