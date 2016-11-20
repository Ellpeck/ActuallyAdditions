/*
 * This file ("VillageComponentEngineerHouse.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class VillageComponentEngineerHouse extends StructureVillagePieces.House1{

    private static final ResourceLocation STRUCTURE_RES_LOC = new ResourceLocation(ModUtil.MOD_ID, "andrew_house");

    private static final int X_SIZE = 13;
    private static final int Y_SIZE = 11;
    private static final int Z_SIZE = 10;

    private int averageGroundLevel = -1;

    public VillageComponentEngineerHouse(){

    }

    public VillageComponentEngineerHouse(StructureBoundingBox boundingBox, EnumFacing par5){
        this.setCoordBaseMode(par5);
        this.boundingBox = boundingBox;
    }

    public static VillageComponentEngineerHouse buildComponent(List pieces, int p1, int p2, int p3, EnumFacing p4){
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, X_SIZE, Y_SIZE, Z_SIZE, p4);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(pieces, boundingBox) == null ? new VillageComponentEngineerHouse(boundingBox, p4) : null;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb){
        if(this.averageGroundLevel < 0){
            this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);
            if(this.averageGroundLevel < 0){
                return true;
            }
            this.boundingBox.offset(0, this.averageGroundLevel-this.boundingBox.maxY+Y_SIZE-2, 0);
        }

        this.fillWithBlocks(world, sbb, 0, 0, 0, X_SIZE-1, Y_SIZE-1, Z_SIZE-1, Blocks.AIR);
        this.spawnActualHouse(world, rand, sbb);

        for(int i = 0; i < X_SIZE; i++){
            for(int j = 0; j < Z_SIZE; j++){
                this.clearCurrentPositionBlocksUpwards(world, i, Y_SIZE, j, sbb);
                this.replaceAirAndLiquidDownwards(world, Blocks.DIRT.getDefaultState(), i, -1, j, sbb);
            }
        }

        this.spawnVillagers(world, sbb, 3, 1, 3, 1);

        return true;
    }

    public void fillWithBlocks(World world, StructureBoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block){
        this.fillWithBlocks(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, block.getDefaultState(), block.getDefaultState(), false);
    }

    public void spawnActualHouse(World world, Random rand, StructureBoundingBox sbb){
        TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
        MinecraftServer server = world.getMinecraftServer();

        if(manager != null && server != null){
            EnumFacing facing = this.getCoordBaseMode();

            Mirror mirror;
            Rotation rotation;
            if(facing == EnumFacing.SOUTH){
                mirror = Mirror.NONE;
                rotation = Rotation.NONE;
            }
            else if(facing == EnumFacing.WEST){
                mirror = Mirror.NONE;
                rotation = Rotation.CLOCKWISE_90;
            }
            else if(facing == EnumFacing.EAST){
                mirror = Mirror.LEFT_RIGHT;
                rotation = Rotation.CLOCKWISE_90;
            }
            else{
                mirror = Mirror.LEFT_RIGHT;
                rotation = Rotation.NONE;
            }

            PlacementSettings placement = new PlacementSettings().setRotation(rotation).setMirror(mirror).setBoundingBox(sbb);
            Template template = manager.getTemplate(server, STRUCTURE_RES_LOC);

            if(template != null){
                template.addBlocksToWorld(world, new BlockPos(this.getXWithOffset(0, 0), this.getYWithOffset(0), this.getZWithOffset(0, 0)), placement);
            }
        }
    }

    /*@Override
    protected VillagerProfession chooseForgeProfession(int count, VillagerProfession prof){
        return InitVillager.jamProfession;
    }*/
}
