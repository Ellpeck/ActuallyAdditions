package powercrystals.minefactoryreloaded.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Defines an object that can display information about a captured mob in a
 * Safari net.
 *
 * @author PowerCrystals
 */
public interface ISafariNetHandler {

	/**
	 * @return The class of mob that this handler applies to.
	 */
	public Class<?> validFor();

	/**
	 * Called to add information regarding a mob contained in a SafariNet.
	 *
	 * @param safariNetStack
	 *            The Safari Net that is requesting information.
	 * @param player
	 *            The player holding the Safari Net.
	 * @param infoList
	 *            The current list of information strings. Add yours to this.
	 * @param advancedTooltips
	 *            True if the advanced tooltips option is on.
	 */
	public void addInformation(ItemStack safariNetStack, EntityPlayer player, List<String> infoList, boolean advancedTooltips);

}
