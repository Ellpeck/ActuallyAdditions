/*
 * This file ("Lens.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.items.lens;

import de.ellpeck.actuallyadditions.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.util.Position;
import net.minecraft.item.Item;

public abstract class Lens{

    protected Item lensItem;

    public abstract boolean invoke(Position hitBlock, TileEntityAtomicReconstructor tile);

    public abstract float[] getColor();

    public abstract int getDistance();

    public Lens register(){
        Lenses.allLenses.add(this);
        return this;
    }

    public void setLensItem(Item item){
        this.lensItem = item;
    }
}
