package de.ellpeck.actuallyadditions.misc.cloud;

public interface ISmileyCloudEasterEgg {

    /**
     * Extra rendering function
     */
    void renderExtra(float f);

    /**
     * The name the cloud has to have for this effect to occur
     */
    String[] getTriggerNames();
}
