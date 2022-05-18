package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.AASounds;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class SoundsGenerator extends SoundDefinitionsProvider {

    protected SoundsGenerator(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, ActuallyAdditions.MODID, helper);
    }

    @Override
    public void registerSounds() {
        add(AASounds.RECONSTRUCTOR, definition().with(sound(new ResourceLocation(ActuallyAdditions.MODID, "reconstructor"))));
        add(AASounds.CRUSHER, definition().with(sound(new ResourceLocation(ActuallyAdditions.MODID, "crusher"))));
        add(AASounds.COFFEE_MACHINE, definition().with(sound(new ResourceLocation(ActuallyAdditions.MODID, "coffee_machine"))));
        add(AASounds.DUH_DUH_DUH_DUUUH, definition().with(sound(new ResourceLocation(ActuallyAdditions.MODID, "duh_duh_duh_duuuh"))));
    }
}
