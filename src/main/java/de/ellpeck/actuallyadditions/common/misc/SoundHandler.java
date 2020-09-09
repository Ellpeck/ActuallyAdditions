package de.ellpeck.actuallyadditions.common.misc;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.RegistryHandler;
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
