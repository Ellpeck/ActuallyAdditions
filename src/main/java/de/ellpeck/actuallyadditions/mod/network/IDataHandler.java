package de.ellpeck.actuallyadditions.mod.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface IDataHandler {

    void handleData(NBTTagCompound compound, MessageContext context);

}
