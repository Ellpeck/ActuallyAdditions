/*
 * This file ("WorldDecorationEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
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
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class WorldDecorationEvent{

    @SubscribeEvent
    public void onWorldDecoration(DecorateBiomeEvent.Decorate event){
        if((event.getResult() == Event.Result.ALLOW || event.getResult() == Event.Result.DEFAULT)){
            if(Util.arrayContains(ConfigValues.plantDimensionBlacklist, event.world.provider.getDimensionId()) < 0){
                this.generateRice(event);
                this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.CANOLA.ordinal(), ConfigIntValues.CANOLA_AMOUNT.getValue(), ConfigBoolValues.DO_CANOLA_GEN.isEnabled(), Material.grass, event);
                this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.FLAX.ordinal(), ConfigIntValues.FLAX_AMOUNT.getValue(), ConfigBoolValues.DO_FLAX_GEN.isEnabled(), Material.grass, event);
                this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.COFFEE.ordinal(), ConfigIntValues.COFFEE_AMOUNT.getValue(), ConfigBoolValues.DO_COFFEE_GEN.isEnabled(), Material.grass, event);
                this.genPlantNormally(InitBlocks.blockBlackLotus, 0, ConfigIntValues.BLACK_LOTUS_AMOUNT.getValue(), ConfigBoolValues.DO_LOTUS_GEN.isEnabled(), Material.grass, event);
            }

            //Generate Treasure Chests
            if(ConfigBoolValues.DO_TREASURE_CHEST_GEN.isEnabled()){
                if(event.rand.nextInt(300) == 0){
                    BlockPos randomPos = new BlockPos(event.pos.getX()+event.rand.nextInt(16)+8, 0, event.pos.getZ()+event.rand.nextInt(16)+8);
                    randomPos = event.world.getTopSolidOrLiquidBlock(randomPos);

                    if(event.world.getBiomeGenForCoords(randomPos) instanceof BiomeGenOcean){
                        if(randomPos.getY() >= 25 && randomPos.getY() <= 45){
                            if(PosUtil.getBlock(randomPos, event.world).getMaterial() == Material.water){
                                if(PosUtil.getMaterial(PosUtil.offset(randomPos, 0, -1, 0), event.world).isSolid()){
                                    PosUtil.setBlock(randomPos, event.world, InitBlocks.blockTreasureChest, event.rand.nextInt(4), 2);
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
                if(event.rand.nextInt(50) == 0){
                    BlockPos randomPos = new BlockPos(event.pos.getX()+event.rand.nextInt(16)+8, 0, event.pos.getZ()+event.rand.nextInt(16)+8);
                    randomPos = event.world.getTopSolidOrLiquidBlock(randomPos);

                    if(PosUtil.getMaterial(randomPos, event.world) == Material.water){
                        ArrayList<Material> blocksAroundBottom = WorldUtil.getMaterialsAround(event.world, randomPos);
                        ArrayList<Material> blocksAroundTop = WorldUtil.getMaterialsAround(event.world, PosUtil.offset(randomPos, 0, 1, 0));
                        if(blocksAroundBottom.contains(Material.grass) || blocksAroundBottom.contains(Material.ground) || blocksAroundBottom.contains(Material.rock) || blocksAroundBottom.contains(Material.sand)){
                            if(!blocksAroundTop.contains(Material.water) && PosUtil.getMaterial(randomPos, event.world) == Material.air){
                                PosUtil.setBlock(PosUtil.offset(randomPos, 0, 1, 0), event.world, InitBlocks.blockWildPlant, TheWildPlants.RICE.ordinal(), 2);
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
                if(event.rand.nextInt(400) == 0){
                    BlockPos randomPos = new BlockPos(event.pos.getX()+event.rand.nextInt(16)+8, 0, event.pos.getZ()+event.rand.nextInt(16)+8);
                    randomPos = event.world.getTopSolidOrLiquidBlock(randomPos);

                    if(PosUtil.getMaterial(randomPos, event.world) == blockBelow){
                        BlockPos top = PosUtil.offset(randomPos, 0, 1, 0);
                        PosUtil.setBlock(top, event.world, plant, meta, 2);
                        if(plant instanceof BlockBush && !((BlockBush)plant).canBlockStay(event.world, top, event.world.getBlockState(top))){
                            event.world.setBlockToAir(top);
                        }
                    }
                }
            }
        }
    }
}
