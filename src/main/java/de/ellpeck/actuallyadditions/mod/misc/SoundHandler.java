/*
 * This file ("SoundHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.RegistryHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class SoundHandler {

    public static SoundEvent duhDuhDuhDuuuh;
    public static SoundEvent coffeeMachine;
    public static SoundEvent reconstructor;
    public static SoundEvent crusher;

    public static void init() {
        duhDuhDuhDuuuh = registerSound("duh_duh_duh_duuuh");
        coffeeMachine = registerSound("coffee_machine");
        reconstructor = registerSound("reconstructor");
        crusher = registerSound("crusher");
    }

    private static SoundEvent registerSound(String name) {
        ResourceLocation resLoc = new ResourceLocation(ActuallyAdditions.MODID, name);

        SoundEvent event = new SoundEvent(resLoc);
        event.setRegistryName(resLoc);
        RegistryHandler.SOUNDS_TO_REGISTER.add(event);
        return event;
    }
}
