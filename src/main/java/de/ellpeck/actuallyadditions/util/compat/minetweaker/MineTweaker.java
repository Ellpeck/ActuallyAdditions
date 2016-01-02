/*
 * This file ("MineTweaker.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.util.compat.minetweaker;

import cpw.mods.fml.common.Loader;
import minetweaker.MineTweakerAPI;

public class MineTweaker{

    public static void init(){
        if(Loader.isModLoaded("MineTweaker3")){
            MineTweakerAPI.registerClass(Crusher.class);
        }
    }
}
