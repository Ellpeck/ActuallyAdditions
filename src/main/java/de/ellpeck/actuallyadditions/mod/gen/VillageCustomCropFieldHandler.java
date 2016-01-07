/*
 * This file ("VillageCustomCropFieldHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen;

import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.Random;

//TODO Fix village
public abstract class VillageCustomCropFieldHandler implements VillagerRegistry.IVillageCreationHandler{

    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i){
        return new StructureVillagePieces.PieceWeight(VillageComponentCustomCropField.class, 5, 1);
    }

    @Override
    public Class<?> getComponentClass(){
        return VillageComponentCustomCropField.class;
    }

    /*@Override
    public Object buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5){
        return VillageComponentCustomCropField.buildComponent(pieces, p1, p2, p3, p4);
    }*/
}
