package de.ellpeck.actuallyadditions.common.gen.village.component.handler;

import java.util.List;
import java.util.Random;

import de.ellpeck.actuallyadditions.common.gen.village.component.VillageComponentJamHouse;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class VillageJamHouseHandler implements VillagerRegistry.IVillageCreationHandler {

    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i) {
        return new StructureVillagePieces.PieceWeight(VillageComponentJamHouse.class, 3, 1);
    }

    @Override
    public Class<?> getComponentClass() {
        return VillageComponentJamHouse.class;
    }

    @Override
    public StructureVillagePieces.Village buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int p1, int p2, int p3, EnumFacing facing, int p5) {
        return VillageComponentJamHouse.buildComponent(pieces, p1, p2, p3, facing);
    }
}
