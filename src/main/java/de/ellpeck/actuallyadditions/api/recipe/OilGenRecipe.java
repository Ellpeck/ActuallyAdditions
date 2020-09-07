package de.ellpeck.actuallyadditions.api.recipe;

public class OilGenRecipe {

    public final String fluidName;
    public final int genAmount;
    public final int genTime;

    /**
     * Oil generator recipe
     * @param fluidName The fluid
     * @param genAmount The power generated, in CF/t
     * @param genTime The length the fluid burns for, in seconds
     */
    public OilGenRecipe(String fluidName, int genAmount, int genTime) {
        this.fluidName = fluidName;
        this.genAmount = genAmount;
        this.genTime = genTime;
    }

}
