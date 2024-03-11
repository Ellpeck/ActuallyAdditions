package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.AASounds;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class SoundsGenerator extends SoundDefinitionsProvider {

    protected SoundsGenerator(PackOutput packOutput, ExistingFileHelper helper) {
        super(packOutput, ActuallyAdditions.MODID, helper);
    }

    @Override
    public void registerSounds() {
        add(AASounds.RECONSTRUCTOR, definition().with(sound(new ResourceLocation(ActuallyAdditions.MODID, "reconstructor"))));
        add(AASounds.CRUSHER, definition().with(sound(new ResourceLocation(ActuallyAdditions.MODID, "crusher"))));
        add(AASounds.COFFEE_MACHINE, definition().with(sound(new ResourceLocation(ActuallyAdditions.MODID, "coffee_machine"))));
        add(AASounds.DUH_DUH_DUH_DUUUH, definition().with(sound(new ResourceLocation(ActuallyAdditions.MODID, "duh_duh_duh_duuuh"))));
        add(AASounds.VILLAGER_WORK_ENGINEER, definition().with(sound(new ResourceLocation(ActuallyAdditions.MODID, "coffee_machine"))));
    }
}
