package de.ellpeck.actuallyadditions.api.lens;

/**
 * This is the base class for a Reconstructor Lens Item
 */
public interface ILensItem {

    /**
     * Returns the lens type that belongs to this lens item
     */
    Lens getLens();
}
