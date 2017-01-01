/*
 * This file ("VillageComponentEngineerHouse.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.village.component;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.gen.village.InitVillager;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.misc.DungeonLoot;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

import java.util.List;
import java.util.Random;

public class VillageComponentEngineerHouse extends StructureVillagePieces.House1{

    private static final ResourceLocation STRUCTURE_RES_LOC = new ResourceLocation(ModUtil.MOD_ID, "andrew_period_house");

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
        this.spawnActualHouse(world, sbb);
        this.fillHouse(world, sbb);

        for(int i = 0; i < X_SIZE; i++){
            for(int j = 0; j < Z_SIZE; j++){
                this.clearCurrentPositionBlocksUpwards(world, i, Y_SIZE, j, sbb);
                this.replaceAirAndLiquidDownwards(world, Blocks.DIRT.getDefaultState(), i, -1, j, sbb);
            }
        }

        this.spawnVillagers(world, sbb, 7, 4, 6, 1);

        return true;
    }

    private void fillWithBlocks(World world, StructureBoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block){
        this.fillWithBlocks(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, block.getDefaultState(), block.getDefaultState(), false);
    }

    private void fillHouse(World world, StructureBoundingBox sbb){
        if(ConfigBoolValues.DUNGEON_LOOT.isEnabled()){
            if(world.rand.nextBoolean()){
                TileEntity compost = this.getTileAtPos(world, 6, 1, 2, sbb);
                if(compost instanceof TileEntityCompost){
                    TileEntityCompost tile = (TileEntityCompost)compost;
                    tile.stopFromDropping = true;
                    tile.slots.setStackInSlot(0, new ItemStack(InitItems.itemFertilizer, 10));
                }
            }

            TileEntity ferment = this.getTileAtPos(world, 11, 1, 0, sbb);
            if(ferment instanceof TileEntityFermentingBarrel){
                TileEntityFermentingBarrel tile = (TileEntityFermentingBarrel)ferment;
                tile.stopFromDropping = true;
                tile.canolaTank.setFluid(new FluidStack(InitFluids.fluidCanolaOil, world.rand.nextInt(1500)+200));
            }

            TileEntity coffee = this.getTileAtPos(world, 4, 2, 6, sbb);
            if(coffee instanceof TileEntityCoffeeMachine){
                TileEntityCoffeeMachine tile = (TileEntityCoffeeMachine)coffee;
                tile.stopFromDropping = true;
                tile.tank.setFluid(new FluidStack(FluidRegistry.WATER, world.rand.nextInt(3000)+500));
                tile.coffeeCacheAmount = world.rand.nextInt(150);
                tile.storage.setEnergyStored(world.rand.nextInt(tile.storage.getMaxEnergyStored()/2));
            }

            TileEntity press = this.getTileAtPos(world, 2, 1, 5, sbb);
            if(press instanceof TileEntityCanolaPress){
                TileEntityCanolaPress tile = (TileEntityCanolaPress)press;
                tile.stopFromDropping = true;
                tile.storage.setEnergyStored(world.rand.nextInt(tile.storage.getMaxEnergyStored()/3));
                tile.slots.setStackInSlot(0, new ItemStack(InitItems.itemMisc, world.rand.nextInt(60)+1, TheMiscItems.CANOLA.ordinal()));
            }

            TileEntity crusher = this.getTileAtPos(world, 2, 1, 6, sbb);
            if(crusher instanceof TileEntityGrinder){
                TileEntityGrinder tile = (TileEntityGrinder)crusher;
                tile.stopFromDropping = true;
                tile.storage.setEnergyStored(world.rand.nextInt(tile.storage.getMaxEnergyStored()/2));
                if(world.rand.nextFloat() >= 0.25F){
                    tile.slots.setStackInSlot(TileEntityGrinder.SLOT_INPUT_1, new ItemStack(InitBlocks.blockMisc, world.rand.nextInt(10)+1, TheMiscBlocks.ORE_QUARTZ.ordinal()));
                }
            }

            TileEntity coal = this.getTileAtPos(world, 5, 5, 6, sbb);
            if(coal instanceof TileEntityCoalGenerator){
                TileEntityCoalGenerator tile = (TileEntityCoalGenerator)coal;
                tile.stopFromDropping = true;
                tile.slots.setStackInSlot(0, new ItemStack(Items.COAL, world.rand.nextInt(25)+3, 1));
            }

            TileEntity reconstructor = this.getTileAtPos(world, 8, 4, 3, sbb);
            if(reconstructor instanceof TileEntityAtomicReconstructor){
                ((TileEntityAtomicReconstructor)reconstructor).stopFromDropping = true;
            }

            VillageComponentJamHouse.generateCrate(world, sbb, this.getXWithOffset(6, 4), this.getYWithOffset(4), this.getZWithOffset(6, 4), DungeonLoot.ENGINEER_HOUSE);
        }

        TileEntity firstRelay = this.getTileAtPos(world, 6, 5, 6, sbb);
        TileEntity secondRelay = this.getTileAtPos(world, 8, 5, 3, sbb);
        if(firstRelay instanceof TileEntityLaserRelayEnergy && secondRelay instanceof TileEntityLaserRelayEnergy){
            ((TileEntityLaserRelayEnergy)firstRelay).stopFromDropping = true;
            ((TileEntityLaserRelayEnergy)secondRelay).stopFromDropping = true;
            ActuallyAdditionsAPI.connectionHandler.addConnection(firstRelay.getPos(), secondRelay.getPos(), LaserType.ENERGY, world);
        }

        int meta = world.rand.nextInt(TheColoredLampColors.values().length);
        this.setBlockState(world, InitBlocks.blockColoredLampOn.getStateFromMeta(meta), 8, 1, 6, sbb);
    }

    private void spawnActualHouse(World world, StructureBoundingBox sbb){
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

    private TileEntity getTileAtPos(World world, int x, int y, int z, StructureBoundingBox sbb){
        BlockPos pos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));
        if(sbb.isVecInside(pos)){
            return world.getTileEntity(pos);
        }
        else{
            return null;
        }
    }

    @Override
    protected VillagerProfession chooseForgeProfession(int count, VillagerProfession prof){
        return InitVillager.engineerProfession;
    }
}
