package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundHandler{

    public static SoundEvent duhDuhDuhDuuuh;
    public static SoundEvent coffeeMachine;
    public static SoundEvent reconstructor;
    public static SoundEvent crusher;

    private static int size = 0;

    public static void init(){
        size = SoundEvent.REGISTRY.getKeys().size();

        duhDuhDuhDuuuh = registerSound("duhDuhDuhDuuuh");
        coffeeMachine = registerSound("coffeeMachine");
        reconstructor = registerSound("reconstructor");
        crusher = registerSound("crusher");
    }

    private static SoundEvent registerSound(String name){
        ResourceLocation resLoc = new ResourceLocation(ModUtil.MOD_ID, name);

        SoundEvent event = new SoundEvent(resLoc);
        SoundEvent.REGISTRY.register(size, resLoc, event);

        size++;
        return event;
    }
}
