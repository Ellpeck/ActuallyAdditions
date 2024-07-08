package de.ellpeck.actuallyadditions.mod;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AASounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, ActuallyAdditions.MODID);

    public static DeferredHolder<SoundEvent, SoundEvent> RECONSTRUCTOR = SOUNDS.register("reconstructor", () -> SoundEvent.createVariableRangeEvent(ActuallyAdditions.modLoc("reconstructor")));
    public static DeferredHolder<SoundEvent, SoundEvent> CRUSHER = SOUNDS.register("crusher", () -> SoundEvent.createVariableRangeEvent(ActuallyAdditions.modLoc("crusher")));
    public static DeferredHolder<SoundEvent, SoundEvent> COFFEE_MACHINE = SOUNDS.register("coffee_machine", () -> SoundEvent.createVariableRangeEvent(ActuallyAdditions.modLoc("coffee_machine")));
    public static DeferredHolder<SoundEvent, SoundEvent> DUH_DUH_DUH_DUUUH = SOUNDS.register("duh_duh_duh_duuuh", () -> SoundEvent.createVariableRangeEvent(ActuallyAdditions.modLoc("duh_duh_duh_duuuh")));
    public static DeferredHolder<SoundEvent, SoundEvent> VILLAGER_WORK_ENGINEER = SOUNDS.register("villager.work_engineer", () -> SoundEvent.createVariableRangeEvent(ActuallyAdditions.modLoc("villager.work_engineer")));


    public static void init(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
