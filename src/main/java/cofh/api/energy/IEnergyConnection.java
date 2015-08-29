/*
 * This file ("IEnergyConnection.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package cofh.api.energy;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Implement this interface on TileEntities which should connect to energy transportation blocks. This is intended for blocks which generate energy but do not
 * accept it; otherwise just use IEnergyHandler.
 * <p>
 * Note that {@link IEnergyHandler} is an extension of this.
 * 
 * @author King Lemming
 * 
 */
public interface IEnergyConnection {

	/**
	 * Returns TRUE if the TileEntity can connect on a given side.
	 */
	boolean canConnectEnergy(ForgeDirection from);

}
