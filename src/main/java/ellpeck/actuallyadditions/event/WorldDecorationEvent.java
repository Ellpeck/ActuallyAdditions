package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.ArrayList;
import java.util.Random;

public class WorldDecorationEvent{

    @SubscribeEvent
    public void onWorldDecoration(DecorateBiomeEvent event){
        if(ConfigBoolValues.DO_RICE_GEN.isEnabled()){
            for(int i = 0; i < ConfigIntValues.RICE_AMOUNT.getValue(); i++){
                if(new Random().nextInt(10) == 0){
                    int genX = event.chunkX+event.rand.nextInt(16)+8;
                    int genZ = event.chunkZ+event.rand.nextInt(16)+8;
                    int genY = event.world.getTopSolidOrLiquidBlock(genX, genZ);

                    if(event.world.getBlock(genX, genY, genZ).getMaterial() == Material.water){
                        ArrayList<Material> blocksAroundBottom = this.getMaterialsAround(event.world, genX, genY, genZ);
                        ArrayList<Material> blocksAroundTop = this.getMaterialsAround(event.world, genX, genY+1, genZ);
                        if(blocksAroundBottom.contains(Material.grass) || blocksAroundBottom.contains(Material.ground) || blocksAroundBottom.contains(Material.rock) || blocksAroundBottom.contains(Material.sand)){
                            if(!blocksAroundTop.contains(Material.water) && !blocksAroundTop.contains(Material.water) && !blocksAroundTop.contains(Material.water) && event.world.getBlock(genX, genY+1, genZ).getMaterial() == Material.air){
                                event.world.setBlock(genX, genY+1, genZ, InitBlocks.blockRice, event.rand.nextInt(8), 2);
                            }
                        }
                    }
                }
            }
        }

        this.genPlantNormally(InitBlocks.blockCanola, ConfigIntValues.CANOLA_AMOUNT.getValue(), ConfigBoolValues.DO_CANOLA_GEN.isEnabled(), Material.grass, event);
        this.genPlantNormally(InitBlocks.blockFlax, ConfigIntValues.FLAX_AMOUNT.getValue(), ConfigBoolValues.DO_FLAX_GEN.isEnabled(), Material.grass, event);
    }

    public void genPlantNormally(Block plant, int amount, boolean doIt, Material blockBelow, DecorateBiomeEvent event){
        if(doIt){
            for(int i = 0; i < amount; i++){
                if(new Random().nextInt(100) == 0){
                    int genX = event.chunkX+event.rand.nextInt(16)+8;
                    int genZ = event.chunkZ+event.rand.nextInt(16)+8;
                    int genY = event.world.getTopSolidOrLiquidBlock(genX, genZ)-1;

                    if(event.world.getBlock(genX, genY, genZ).getMaterial() == blockBelow){
                        event.world.setBlock(genX, genY+1, genZ, plant, event.rand.nextInt(8), 2);
                    }
                }
            }
        }
    }

    public ArrayList<Material> getMaterialsAround(World world, int x, int y, int z){
        ArrayList<Material> blocks = new ArrayList<Material>();
        blocks.add(world.getBlock(x+1, y, z).getMaterial());
        blocks.add(world.getBlock(x-1, y, z).getMaterial());
        blocks.add(world.getBlock(x, y, z+1).getMaterial());
        blocks.add(world.getBlock(x, y, z-1).getMaterial());

        return blocks;
    }

}
