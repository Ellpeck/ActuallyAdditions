package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class Curios extends CuriosDataProvider {
    public Curios(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(ActuallyAdditions.MODID, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        createEntities("player").addEntities(EntityType.PLAYER).addSlots("charm");
    }
}
