/*
 * This file ("LensMining.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class LensMining extends Lens{

    public static final int ENERGY_USE = 60000;

    private static final List<WeightedOre> STONE_ORES = new ArrayList<WeightedOre>();
    private static final List<WeightedOre> NETHERRACK_ORES = new ArrayList<WeightedOre>();

    static{
        add(STONE_ORES, "oreCoal", 5000);
        add(NETHERRACK_ORES, "oreNetherCoal", 5000);
        add(STONE_ORES, "oreIron", 3000);
        add(NETHERRACK_ORES, "oreNetherIron", 3000);
        add(STONE_ORES, "oreGold", 500);
        add(NETHERRACK_ORES, "oreNetherGold", 500);
        add(STONE_ORES, "oreDiamond", 50);
        add(NETHERRACK_ORES, "oreNetherDiamond", 50);
        add(STONE_ORES, "oreLapis", 250);
        add(NETHERRACK_ORES, "oreNetherLapis", 250);
        add(STONE_ORES, "oreRedstone", 200);
        add(NETHERRACK_ORES, "oreNetherRedstone", 200);
        add(STONE_ORES, "oreEmerald", 30);
        add(NETHERRACK_ORES, "oreQuartz", 30);

        add(STONE_ORES, "oreCopper", 2000);
        add(NETHERRACK_ORES, "oreNetherCopper", 2000);
        add(STONE_ORES, "oreTin", 1800);
        add(NETHERRACK_ORES, "oreNetherTin", 1800);
        add(STONE_ORES, "oreLead", 1500);
        add(NETHERRACK_ORES, "oreNetherLead", 1500);
        add(STONE_ORES, "oreSilver", 1000);
        add(NETHERRACK_ORES, "oreNetherSilver", 1000);
        add(STONE_ORES, "oreNickel", 100);
        add(NETHERRACK_ORES, "oreNetherNickel", 100);
        add(STONE_ORES, "orePlatinum", 20);
        add(NETHERRACK_ORES, "oreNetherPlatinum", 20);
        add(STONE_ORES, "oreAluminum", 1600);
        add(STONE_ORES, "oreOsmium", 1500);
        add(STONE_ORES, "oreZinc", 1000);
        add(STONE_ORES, "oreYellorite", 1200);
        add(STONE_ORES, "oreUranium", 400);
        add(STONE_ORES, "oreCertusQuartz", 800);
        add(STONE_ORES, "oreApatite", 700);
        add(STONE_ORES, "oreQuartzBlack", 3000);

        add(NETHERRACK_ORES, "oreCobalt", 50);
        add(NETHERRACK_ORES, "oreArdite", 50);
    }

    private static void add(List<WeightedOre> list, String name, int weight){
        list.add(new WeightedOre(name, weight));
    }

    @Override
    public boolean invoke(IBlockState hitState, BlockPos hitPos, IAtomicReconstructor tile){
        if(!tile.getWorldObject().isAirBlock(hitPos)){
            if(tile.getEnergy() >= ENERGY_USE){
                int adaptedUse = ENERGY_USE;

                List<WeightedOre> ores = null;
                Block hitBlock = hitState.getBlock();
                if(hitBlock instanceof BlockStone){
                    ores = STONE_ORES;
                }
                else if(hitBlock instanceof BlockNetherrack){
                    ores = NETHERRACK_ORES;
                    adaptedUse += 10000;
                }

                if(ores != null){
                    int totalWeight = WeightedRandom.getTotalWeight(ores);
                    ItemStack stack = null;

                    boolean found = false;
                    while(!found){
                        WeightedOre ore = WeightedRandom.getRandomItem(tile.getWorldObject().rand, ores, totalWeight);
                        if(ore != null){
                            List<ItemStack> stacks = OreDictionary.getOres(ore.name, false);
                            if(stacks != null && !stacks.isEmpty()){
                                for(ItemStack aStack : stacks){
                                    if(aStack != null && aStack.getItem() instanceof ItemBlock){
                                        adaptedUse += (totalWeight-ore.itemWeight)%40000;

                                        stack = aStack;
                                        found = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if(tile.getEnergy() >= adaptedUse){
                        Block block = Block.getBlockFromItem(stack.getItem());
                        if(block != null){
                            IBlockState state = block.getStateFromMeta(stack.getItemDamage());
                            tile.getWorldObject().setBlockState(hitPos, state, 2);

                            if(!ConfigBoolValues.LESS_BLOCK_BREAKING_EFFECTS.isEnabled()){
                                tile.getWorldObject().playEvent(2001, hitPos, Block.getStateId(state));
                            }

                            tile.extractEnergy(adaptedUse);
                        }
                    }
                }
            }

            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public float[] getColor(){
        return new float[]{76F/255F, 76F/255F, 76F/255F};
    }

    @Override
    public int getDistance(){
        return 10;
    }

    private static class WeightedOre extends WeightedRandom.Item{

        public final String name;

        public WeightedOre(String name, int weight){
            super(weight);
            this.name = name;
        }
    }
}
