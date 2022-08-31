/*
 * This file ("LensMining.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.recipe.WeightedOre;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.crafting.MiningLensRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherrackBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LensMining extends Lens {

    @Deprecated
    public static void init() {

        //These need to be moved to datagen conditionals if the appropriate mod is loaded.
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreCopper", 2000);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherCopper", 2000);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreTin", 1800);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherTin", 1800);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreLead", 1500);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherLead", 1500);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreSilver", 1000);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherSilver", 1000);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreNickel", 100);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherNickel", 100);
        ActuallyAdditionsAPI.addMiningLensStoneOre("orePlatinum", 20);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherPlatinum", 20);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreAluminum", 1200);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreAluminium", 1200);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreOsmium", 1500);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreZinc", 1000);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreYellorite", 1200);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreUranium", 400);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreCertusQuartz", 800);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreApatite", 700);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreQuartzBlack", 3000);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreRuby", 40);
        ActuallyAdditionsAPI.addMiningLensStoneOre("orePeridot", 40);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreTopaz", 40);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreTanzanite", 40);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreMalachite", 40);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreSapphire", 40);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreAmber", 150);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreResonating", 50);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreSulfur", 3000);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreSaltpeter", 250);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreFirestone", 30);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreSalt", 2900);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreDraconium", 5);

        ActuallyAdditionsAPI.addMiningLensNetherOre("oreCobalt", 50);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreArdite", 50);
    }

    @Override
    public boolean invoke(BlockState hitState, BlockPos hitPos, IAtomicReconstructor tile) {
        final int energyUse = CommonConfig.Machines.MINER_LENS_ENERGY.get();
        if (!tile.getWorldObject().isEmptyBlock(hitPos)) {
            if (tile.getEnergy() >= energyUse) {
                int adaptedUse = energyUse;

                List<MiningLensRecipe> ores = new ArrayList<>();

                Block hitBlock = hitState.getBlock();
                ItemStack item = new ItemStack(hitBlock.asItem());

                for(MiningLensRecipe r:ActuallyAdditionsAPI.MINING_LENS_RECIPES) {
                    if (r.matches(item))
                        ores.add(r);
                }


/*                if (hitBlock.is(Tags.Blocks.STONE)) { //TODO maybe?
                    ores = ActuallyAdditionsAPI.STONE_ORES;
                } else if (hitBlock instanceof NetherrackBlock) {
                    ores = ActuallyAdditionsAPI.NETHERRACK_ORES;
                    adaptedUse += 10000; //TODO uh oh!?
                }*/

                if (!ores.isEmpty()) {
                    int totalWeight = WeightedRandom.getTotalWeight(ores);
                    MiningLensRecipe ore = WeightedRandom.getRandomItem(tile.getWorldObject().random, ores);

                    ItemStack stack = ore.getResultItem().copy();

                    if (stack.getItem() instanceof BlockItem) {
                        Block toPlace = Block.byItem(stack.getItem());
                        BlockState state2Place = toPlace.defaultBlockState();
                        tile.getWorldObject().setBlock(hitPos, state2Place, 2);
                        tile.getWorldObject().levelEvent(2001, hitPos, Block.getId(state2Place));
                        tile.extractEnergy(adaptedUse);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getColor() {
        return 0x4C4C4C;
    }

    @Override
    public int getDistance() {
        return 10;
    }
}
