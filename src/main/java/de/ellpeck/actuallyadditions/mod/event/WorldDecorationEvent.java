/*
 * This file ("WorldDecorationEvent.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;


import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheWildPlants;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class WorldDecorationEvent{

    @SubscribeEvent
    public void onWorldDecoration(DecorateBiomeEvent.Decorate event){
        if((event.getResult() == Event.Result.ALLOW || event.getResult() == Event.Result.DEFAULT)){
            if(Util.arrayContains(ConfigValues.plantDimensionBlacklist, event.getWorld().provider.getDimension()) < 0){
                this.generateRice(event);
                this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.CANOLA.ordinal(), ConfigIntValues.CANOLA_AMOUNT.getValue(), ConfigBoolValues.DO_CANOLA_GEN.isEnabled(), Material.GRASS, event);
                this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.FLAX.ordinal(), ConfigIntValues.FLAX_AMOUNT.getValue(), ConfigBoolValues.DO_FLAX_GEN.isEnabled(), Material.GRASS, event);
                this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.COFFEE.ordinal(), ConfigIntValues.COFFEE_AMOUNT.getValue(), ConfigBoolValues.DO_COFFEE_GEN.isEnabled(), Material.GRASS, event);
                this.genPlantNormally(InitBlocks.blockBlackLotus, 0, ConfigIntValues.BLACK_LOTUS_AMOUNT.getValue(), ConfigBoolValues.DO_LOTUS_GEN.isEnabled(), Material.GRASS, event);
            }

            //Generate Treasure Chests
            if(ConfigBoolValues.DO_TREASURE_CHEST_GEN.isEnabled()){
                if(event.getRand().nextInt(300) == 0){
                    BlockPos randomPos = new BlockPos(event.getPos().getX()+event.getRand().nextInt(16)+8, 0, event.getPos().getZ()+event.getRand().nextInt(16)+8);
                    randomPos = event.getWorld().getTopSolidOrLiquidBlock(randomPos);

                    if(event.getWorld().getBiomeGenForCoords(randomPos) instanceof BiomeGenOcean){
                        if(randomPos.getY() >= 25 && randomPos.getY() <= 45){
                            if(PosUtil.getBlock(randomPos, event.getWorld()).getMaterial(event.getWorld().getBlockState(randomPos)) == Material.WATER){
                                if(PosUtil.getMaterial(PosUtil.offset(randomPos, 0, -1, 0), event.getWorld()).isSolid()){
                                    PosUtil.setBlock(randomPos, event.getWorld(), InitBlocks.blockTreasureChest, event.getRand().nextInt(4), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateRice(DecorateBiomeEvent event){
        if(ConfigBoolValues.DO_RICE_GEN.isEnabled()){
            for(int i = 0; i < ConfigIntValues.RICE_AMOUNT.getValue(); i++){
                if(event.getRand().nextInt(50) == 0){
                    BlockPos randomPos = new BlockPos(event.getPos().getX()+event.getRand().nextInt(16)+8, 0, event.getPos().getZ()+event.getRand().nextInt(16)+8);
                    randomPos = event.getWorld().getTopSolidOrLiquidBlock(randomPos);
                    if(PosUtil.getMaterial(randomPos, event.getWorld()) == Material.WATER){
                        ArrayList<Material> blocksAroundBottom = WorldUtil.getMaterialsAround(event.getWorld(), randomPos);
                        BlockPos posToGenAt = PosUtil.offset(randomPos, 0, 1, 0);
                        ArrayList<Material> blocksAroundTop = WorldUtil.getMaterialsAround(event.getWorld(), posToGenAt);
                        if(blocksAroundBottom.contains(Material.GRASS) || blocksAroundBottom.contains(Material.GROUND) || blocksAroundBottom.contains(Material.ROCK) || blocksAroundBottom.contains(Material.SAND)){
                            if(!blocksAroundTop.contains(Material.WATER) && PosUtil.getMaterial(posToGenAt, event.getWorld()) == Material.AIR){
                                PosUtil.setBlock(posToGenAt, event.getWorld(), InitBlocks.blockWildPlant, TheWildPlants.RICE.ordinal(), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void genPlantNormally(Block plant, int meta, int amount, boolean doIt, Material blockBelow, DecorateBiomeEvent event){
        if(doIt){
            for(int i = 0; i < amount; i++){
                if(event.getRand().nextInt(400) == 0){
                    BlockPos randomPos = new BlockPos(event.getPos().getX()+event.getRand().nextInt(16)+8, 0, event.getPos().getZ()+event.getRand().nextInt(16)+8);
                    randomPos = event.getWorld().getTopSolidOrLiquidBlock(randomPos);

                    if(PosUtil.getMaterial(PosUtil.offset(randomPos, 0, -1, 0), event.getWorld()) == blockBelow){
                        if(plant.canPlaceBlockAt(event.getWorld(), randomPos) && event.getWorld().isAirBlock(randomPos)){
                            PosUtil.setBlock(randomPos, event.getWorld(), plant, meta, 2);
                        }
                    }
                }
            }
        }
    }
}
