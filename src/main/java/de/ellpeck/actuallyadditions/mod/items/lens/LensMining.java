package de.ellpeck.actuallyadditions.mod.items.lens;

import java.util.List;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.recipe.WeightedOre;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.oredict.OreDictionary;

public class LensMining extends Lens {

    public static void init() {
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreCoal", 5000);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherCoal", 5000);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreIron", 3000);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherIron", 3000);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreGold", 500);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherGold", 500);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreDiamond", 50);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherDiamond", 50);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreLapis", 250);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherLapis", 250);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreRedstone", 200);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreNetherRedstone", 200);
        ActuallyAdditionsAPI.addMiningLensStoneOre("oreEmerald", 30);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreQuartz", 3000);

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

        ActuallyAdditionsAPI.addMiningLensStoneOre("orePoorIron", 3000);
        ActuallyAdditionsAPI.addMiningLensStoneOre("orePoorGold", 500);
        ActuallyAdditionsAPI.addMiningLensStoneOre("orePoorCopper", 2000);
        ActuallyAdditionsAPI.addMiningLensStoneOre("orePoorTin", 1800);
        ActuallyAdditionsAPI.addMiningLensStoneOre("orePoorLead", 1500);
        ActuallyAdditionsAPI.addMiningLensStoneOre("orePoorSilver", 1000);

        ActuallyAdditionsAPI.addMiningLensNetherOre("oreCobalt", 50);
        ActuallyAdditionsAPI.addMiningLensNetherOre("oreArdite", 50);

        for (String conf : ConfigStringListValues.MINING_LENS_EXTRA_WHITELIST.getValue()) {
            if (conf.contains("@")) {
                try {
                    String[] split = conf.split("@");

                    String ore = split[0];
                    int weight = Integer.parseInt(split[1]);
                    String dim = split[2];

                    if ("n".equals(dim)) {
                        ActuallyAdditionsAPI.addMiningLensNetherOre(ore, weight);
                    } else if ("s".equals(dim)) {
                        ActuallyAdditionsAPI.addMiningLensStoneOre(ore, weight);
                    }
                } catch (Exception e) {
                    ActuallyAdditions.LOGGER.warn("A config option appears to be incorrect: The entry " + conf + " can't be parsed!");
                }
            }
        }
    }

    @Override
    public boolean invoke(IBlockState hitState, BlockPos hitPos, IAtomicReconstructor tile) {
        if (!tile.getWorldObject().isAirBlock(hitPos)) {
            if (tile.getEnergy() >= ConfigIntValues.MINING_LENS_USE.getValue()) {
                int adaptedUse = ConfigIntValues.MINING_LENS_USE.getValue();

                List<WeightedOre> ores = null;
                Block hitBlock = hitState.getBlock();
                if (hitBlock instanceof BlockStone) {
                    ores = ActuallyAdditionsAPI.STONE_ORES;
                } else if (hitBlock instanceof BlockNetherrack) {
                    ores = ActuallyAdditionsAPI.NETHERRACK_ORES;
                    adaptedUse += 10000;
                }

                if (ores != null) {
                    int totalWeight = WeightedRandom.getTotalWeight(ores);
                    ItemStack stack = null;

                    boolean found = false;
                    while (!found) {
                        WeightedOre ore = WeightedRandom.getRandomItem(tile.getWorldObject().rand, ores, totalWeight);
                        if (ore != null) {
                            List<ItemStack> stacks = OreDictionary.getOres(ore.name, false);
                            if (stacks != null && !stacks.isEmpty()) {
                                for (ItemStack aStack : stacks) {
                                    if (StackUtil.isValid(aStack) && !CrusherRecipeRegistry.hasBlacklistedOutput(aStack, ConfigStringListValues.MINING_LENS_BLACKLIST.getValue()) && aStack.getItem() instanceof ItemBlock) {
                                        if (ConfigBoolValues.MINING_LENS_ADAPTED_USE.isEnabled()) adaptedUse += (totalWeight - ore.itemWeight) % 40000;

                                        stack = aStack;
                                        found = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (tile.getEnergy() >= adaptedUse) {
                        Block block = Block.getBlockFromItem(stack.getItem());
                        if (block != Blocks.AIR) {
                            IBlockState state = block.getStateForPlacement(tile.getWorldObject(), hitPos, EnumFacing.UP, 0, 0, 0, stack.getMetadata(), FakePlayerFactory.getMinecraft((WorldServer) tile.getWorldObject()), EnumHand.MAIN_HAND);
                            tile.getWorldObject().setBlockState(hitPos, state, 2);

                            tile.getWorldObject().playEvent(2001, hitPos, Block.getStateId(state));

                            tile.extractEnergy(adaptedUse);
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public float[] getColor() {
        return new float[] { 76F / 255F, 76F / 255F, 76F / 255F };
    }

    @Override
    public int getDistance() {
        return 10;
    }
}
