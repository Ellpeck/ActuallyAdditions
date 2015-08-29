/*
 * This file ("ISmileyCloudEasterEgg.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.gadget.cloud;

public interface ISmileyCloudEasterEgg{

    /**
     * Extra rendering function
     */
    void renderExtra(float f);

    /**
     * The name the cloud has to have for this effect to occur
     */
    String[] getTriggerNames();
}
