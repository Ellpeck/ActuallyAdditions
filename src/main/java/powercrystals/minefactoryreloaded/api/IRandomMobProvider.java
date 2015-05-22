package powercrystals.minefactoryreloaded.api;

import net.minecraft.world.World;

import java.util.List;

public interface IRandomMobProvider {

	/**
	 * Called to provide random entities to be spawned by mystery SafariNets
	 *
	 * @param world
	 *            The world object the entities will be spawned in.
	 * @return A list of RandomMob instances of entities that are all ready to
	 *         be spawned in the world with no additional method calls.
	 */
	public List<RandomMob> getRandomMobs(World world);

}
