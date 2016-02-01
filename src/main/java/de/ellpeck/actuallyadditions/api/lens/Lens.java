/*
 * This file ("Lens.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.lens;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;

/**
 * This is the base class for a Reconstructor Lens Type (NOT THE ITEM!)
 * You need to call register() to register the type
 */
public abstract class Lens{

    /**
     * The item that belongs to this lens type
     */
    protected Item lensItem;

    /**
     * Invokes the lens type's behavior on a block
     *
     * @param hitBlock The block that was hit
     * @param tile     The tile the lens was invoked from
     * @return If the Reconstructor should stop continuing (return false if you want it to go through blocks)
     */
    public abstract boolean invoke(BlockPos hitBlock, IAtomicReconstructor tile);

    /**
     * Returns the color in an array of 3 float values that are r, g, b
     */
    public abstract float[] getColor();

    /**
     * Gets the maximum distance the beam goes with this lense
     */
    public abstract int getDistance();

    /**
     * Registers the lense type
     */
    public Lens register(){
        ActuallyAdditionsAPI.reconstructorLenses.add(this);
        return this;
    }

    /**
     * Sets the item corresponding to the lense
     */
    public void setLensItem(Item item){
        this.lensItem = item;
    }
}
