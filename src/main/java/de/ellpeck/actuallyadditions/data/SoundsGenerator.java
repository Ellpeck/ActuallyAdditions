package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.AASounds;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class SoundsGenerator extends SoundDefinitionsProvider {

    protected SoundsGenerator(PackOutput packOutput, ExistingFileHelper helper) {
        super(packOutput, ActuallyAdditions.MODID, helper);
    }

    @Override
    public void registerSounds() {
        add(AASounds.RECONSTRUCTOR, definition().with(sound(ActuallyAdditions.modLoc("reconstructor"))));
        add(AASounds.CRUSHER, definition().with(sound(ActuallyAdditions.modLoc("crusher"))));
        add(AASounds.COFFEE_MACHINE, definition().with(sound(ActuallyAdditions.modLoc("coffee_machine"))));
        add(AASounds.DUH_DUH_DUH_DUUUH, definition().with(sound(ActuallyAdditions.modLoc("duh_duh_duh_duuuh"))));
        add(AASounds.VILLAGER_WORK_ENGINEER, definition().with(sound(ActuallyAdditions.modLoc("coffee_machine"))));
    }
}
