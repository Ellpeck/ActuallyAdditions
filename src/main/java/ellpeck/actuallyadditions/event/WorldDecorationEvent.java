package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheWildPlants;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.ArrayList;
import java.util.Random;

public class WorldDecorationEvent{

    @SubscribeEvent
    public void onWorldDecoration(DecorateBiomeEvent.Decorate event){
        if((event.getResult() == Event.Result.ALLOW || event.getResult() == Event.Result.DEFAULT)){
            this.generateRice(event);
            this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.CANOLA.ordinal(), ConfigIntValues.CANOLA_AMOUNT.getValue(), ConfigBoolValues.DO_CANOLA_GEN.isEnabled(), Material.grass, event);
            this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.FLAX.ordinal(), ConfigIntValues.FLAX_AMOUNT.getValue(), ConfigBoolValues.DO_FLAX_GEN.isEnabled(), Material.grass, event);
            this.genPlantNormally(InitBlocks.blockWildPlant, TheWildPlants.COFFEE.ordinal(), ConfigIntValues.COFFEE_AMOUNT.getValue(), ConfigBoolValues.DO_COFFEE_GEN.isEnabled(), Material.grass, event);

            //Generate Treasure Chests
            if(ConfigBoolValues.DO_TREASURE_CHEST_GEN.isEnabled()){
                if(new Random().nextInt(ConfigIntValues.TREASURE_CHEST_CHANCE.getValue()) == 0){
                    int genX = event.chunkX+event.rand.nextInt(16)+8;
                    int genZ = event.chunkZ+event.rand.nextInt(16)+8;
                    int genY = event.world.getTopSolidOrLiquidBlock(genX, genZ);

                    if(event.world.getBiomeGenForCoords(genX, genZ) == BiomeGenBase.deepOcean){
                        if(event.world.getBlock(genX, genY, genZ).getMaterial() == Material.water){
                            if(event.world.getBlock(genX, genY-1, genZ).getMaterial().isSolid()){
                                event.world.setBlock(genX, genY, genZ, InitBlocks.blockTreasureChest, 0, 2);
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
                if(new Random().nextInt(ConfigIntValues.NORMAL_PLANT_CHANCE.getValue()) == 0){
                    int genX = event.chunkX+event.rand.nextInt(16)+8;
                    int genZ = event.chunkZ+event.rand.nextInt(16)+8;
                    int genY = event.world.getTopSolidOrLiquidBlock(genX, genZ)-1;

                    if(event.world.getBlock(genX, genY, genZ).getMaterial() == blockBelow){
                        event.world.setBlock(genX, genY+1, genZ, plant, meta, 2);
                    }
                }
            }
        }
    }

    private void generateRice(DecorateBiomeEvent event){
        if(ConfigBoolValues.DO_RICE_GEN.isEnabled()){
            for(int i = 0; i < ConfigIntValues.RICE_AMOUNT.getValue(); i++){
                if(new Random().nextInt(ConfigIntValues.RICE_CHANCE.getValue()) == 0){
                    int genX = event.chunkX+event.rand.nextInt(16)+8;
                    int genZ = event.chunkZ+event.rand.nextInt(16)+8;
                    int genY = event.world.getTopSolidOrLiquidBlock(genX, genZ);

                    if(event.world.getBlock(genX, genY, genZ).getMaterial() == Material.water){
                        ArrayList<Material> blocksAroundBottom = WorldUtil.getMaterialsAround(event.world, genX, genY, genZ);
                        ArrayList<Material> blocksAroundTop = WorldUtil.getMaterialsAround(event.world, genX, genY+1, genZ);
                        if(blocksAroundBottom.contains(Material.grass) || blocksAroundBottom.contains(Material.ground) || blocksAroundBottom.contains(Material.rock) || blocksAroundBottom.contains(Material.sand)){
                            if(!blocksAroundTop.contains(Material.water) && event.world.getBlock(genX, genY+1, genZ).getMaterial() == Material.air){
                                event.world.setBlock(genX, genY+1, genZ, InitBlocks.blockWildPlant, TheWildPlants.RICE.ordinal(), 2);
                            }
                        }
                    }
                }
            }
        }
    }
}
