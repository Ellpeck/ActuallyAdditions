/*
 * This file ("TileEntityPhantomBooster.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityPhantomBooster extends TileEntityBase{

    @Override
    public boolean canUpdate(){
        return false;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){

    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){

    }
}
